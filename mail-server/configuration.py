import consul
import requests
import json
import yaml


class Consul:
    def __init__(self, sidecar_port):
        self.sidecar_port = sidecar_port
        response = requests.get("http://localhost:{}/hosts/consul".format(sidecar_port))
        data = json.loads(response.text)
        self.port = data[0]['host']

    def get_host(self):
        return self.port

    def get_configuration(self, key):
        c = consul.Consul(host=self.port)
        index, response = c.kv.get(key)
        data = yaml.safe_load(response['Value'])
        return data
