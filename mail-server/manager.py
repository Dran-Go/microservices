import consul
import yaml


# consul管理
class Consul:
    def __init__(self, host, port, key):
        self.host = host
        self.port = port
        self.key = key
        self.consul = consul.Consul(host=self.host, port=self.port)

    def get_host(self):
        return str.format('{}:{}', self.host, self.port)

    def get_configuration(self):
        index, response = self.consul.kv.get(self.key)
        data = yaml.safe_load(response['Value'])
        return data

    def register(self, name, port):
        self.consul.agent.service.register(name=name, port=port)


# sidecar管理 (SpringCloud)
class Sidecar:
    def __init__(self, port, host='127.0.0.1'):
        self.host = host
        self.port = port
        self.sidecar = str.format('{}:{}', self.host, self.port)

    # 获取其它服务地址，通过sidecar访问
    def get_server(self, name):
        return str.format('{}/{}/', self.sidecar, name)

    def get_user_server(self):
        return self.get_server("user-server")





