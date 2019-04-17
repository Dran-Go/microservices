# !/usr/bin/env python
# -*- coding: utf-8 -*-

from flask import *
from flask_mail import Mail, Message
from manager import *


app = Flask(__name__)

result_success = {'code': 200, 'message': 'ok', 'data': True}
result_fail = {'code': 500, 'message': 'HTTP-Internal Server Error', 'data': True}

# Sidecar状态监控
@app.route("/health")
def health():
    result = {'status': 'UP'}
    return Response(json.dumps(result), mimetype='application/json')

# 发送邮箱地址校验邮件
@app.route("/api/mail/verify", methods=['POST'])
def send_verify_mail():
    request_data = request.get_json(force=True)
    email = request_data['email'].strip()
    username = request_data['username'].strip()
    verify_url = request_data['verifyUrl'].strip()
    try:
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
        result = result_success
    except:
        result = result_fail
    return Response(json.dumps(result), mimetype='application/json')

# 发送通信邮件
@app.route("/api/mail/letter", methods=['POST'])
def send_letter_mail():
    request_data = request.get_json(force=True)
    email = request_data['email'].strip()
    from_username = request_data['fromUsername'].strip()
    text = request_data['text'].strip()
    try:
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
        result = result_success
    except:
        result = result_fail
    return Response(json.dumps(result), mimetype='application/json')


if __name__ == '__main__':
    # 使用consul做为配置中心
    app_consul = Consul("127.0.0.1", 8500, "config/mail-server/configuration")
    try:
        app_config = app_consul.get_configuration()
    except:
        with open('./application.yml', 'r') as f:
            app_config = yaml.safe_load(f)

    log_setting(app_config['logging'])

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

    app_server_config = app_config['server']
    app_consul.register(app_server_config['name'], app_server_config['port'])
    app.run(host=app_server_config['host'], port=app_server_config['port'])
