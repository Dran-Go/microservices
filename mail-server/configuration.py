import consul

import yaml


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

#
# class Sidecar:
#     def __init__(self, port):
#
