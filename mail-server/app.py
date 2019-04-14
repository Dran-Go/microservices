from flask import *
from flask_mail import Mail, Message
from configuration import *

app = Flask(__name__)

# Sidecar状态监控
@app.route("/health")
def health():
    result = {'status': 'UP'}
    return Response(json.dumps(result), mimetype='application/json')

# 发送邮箱地址校验邮件
@app.route("/api/mail/verify", methods=['POST'])
def send_verify_mail():
    email = request.form['email'].strip()
    username = request.form['username'].strip()
    verify_url = request.form['verifyUrl'].strip()
    subject = '您的帐户－请确认您的电子邮件地址'
    with open('./templates/mail/verify.html', 'r', encoding='UTF-8') as f:
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

# 发送通信邮件
@app.route("/api/mail/letter", methods=['POST'])
def send_letter_mail():
    email = request.form['email'].strip()
    from_username = request.form['from_username'].strip()
    text = request.form['text'].strip()
    subject = str.format('{}给您发来一封新邮件', from_username)
    with open('./templates/mail/letter.html', 'r', encoding='UTF-8') as f:
        message = f.read()
    message = message.replace('{% from_username %}', from_username).replace('{% text %}', text)
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
    try:
        app_config = Consul(8830, "config/mail-server").get_configuration()
    except:
        print("Error: Loading configuration from consul failed, try to load locally.")
        with open('./application.yml', 'r') as f:
            app_config = yaml.safe_load(f)

    # 配置邮箱
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
