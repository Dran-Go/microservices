import consul
import requests
import json
import yaml


class Consul:
    def __init__(self, sidecar_port, key):
        self.sidecar_port = sidecar_port
        self.key = key
        response = requests.get("http://localhost:{}/hosts/consul".format(sidecar_port))
        data = json.loads(response.text)
        self.port = data[0]['host']
        self.consul = consul.Consul(host=self.port)

    def get_host(self):
        return self.port

    def get_configuration(self):
        index, response = self.consul.kv.get(self.key)
        data = yaml.safe_load(response['Value'])
        return data
