from flask import *
from flask_mail import Mail, Message
from configuration import *

app = Flask(__name__)


@app.route("/health")
def health():
    result = {'status': 'UP'}
    return Response(json.dumps(result), mimetype='application/json')


@app.route("/api/mail/verify", methods=['POST'])
def send_mail():
    email = request.form['email'].strip()
    username = request.form['username'].strip()
    verify_url = request.form['verifyUrl'].strip()
    subject = '您的帐户－请确认您的电子邮件地址'
    with open('./templates/mail/VerifyTemplate.html', 'r', encoding='UTF-8') as f:
        message = f.read()
    message = message.replace('{% username %}', username).replace('{% verify_url %}', verify_url)
    msg = Message(
        subject=subject,
        recipients=[email],
        html=message
    )
    mail.send(msg)
    result = {'code': 200, 'message': 'ok', 'data': True}
    return Response(json.dumps(result), mimetype='application/json')


if __name__ == '__main__':
    # 使用consul做为配置中心
    SIDECAR_PORT = 8830
    CONFIGURATION_KEY = "config/mail-server"
    app_config = Consul(SIDECAR_PORT).get_configuration(CONFIGURATION_KEY)

    # EMAIL SETTINGS
    app_mail_config = app_config['mail']
    app.config.update(
        DEBUG=False,
        MAIL_SERVER=app_mail_config['server'],
        MAIL_PORT=app_mail_config['port'],
        MAIL_USE_SSL=app_mail_config['ssl'],
        MAIL_DEFAULT_SENDER=(app_mail_config['sender']['name'], app_mail_config['sender']['email']),
        MAIL_USERNAME=app_mail_config['username'],
        MAIL_PASSWORD=app_mail_config['password']
    )
    mail = Mail(app)

    app.run(host=app_config['flask']['host'], port=app_config['flask']['port'])
