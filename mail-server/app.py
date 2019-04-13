from flask import *
from flask_mail import Mail, Message

app = Flask(__name__)

app.config.update(
    DEBUG=False,
    # EMAIL SETTINGS
    MAIL_SERVER='smtp.qq.com',
    MAIL_PORT=465,
    MAIL_USE_SSL=True,
    MAIL_DEFAULT_SENDER=('admin', 'Dran-Go@foxmail.com'),
    MAIL_MAX_EMAILS=10,
    MAIL_USERNAME='Dran-Go@foxmail.com',
    MAIL_PASSWORD='tiwkfdfpfecebgja'
)

mail = Mail(app)


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
    with open('./templates/mail/verifyTemplate.html', 'r', encoding='UTF-8') as f:
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
    app.run(host='0.0.0.0', port=8831)
