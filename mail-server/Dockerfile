FROM python:3.6.8
MAINTAINER "DranGo <103636933@qq.com>"

VOLUME /var/log/microservices
ADD . /app
WORKDIR /app

RUN pip install -r requirements.txt
EXPOSE 8820
CMD ["gunicorn", "-b", "0.0.0.0:8820", "app:app"]