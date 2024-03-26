## Encryption at Rest

Use `minikube ssh` to connect to the minikube cluster. Then create an encryption configuration file, reference it at `/etc/kubernetes/manifests/kube-apiserver.yaml`

- Create an encryption file inside minikube cluster (using `minikube ssh`)

```yml
---
apiVersion: apiserver.config.k8s.io/v1
kind: EncryptionConfiguration
resources:
  - resources:
      - secrets
    providers:
      - aescbc:
          keys:
            - name: key1
              # See the following text for more details about the secret value
              secret: <base64 encoded secret value>
      - identity: {} # this fallback allows reading unencrypted secrets;
                     # for example, during initial migration
```

- Apply changes to the encryption configuration at `/etc/kubernetes/manifests/kube-apiserver.yaml`

- Use Encryption at Rest to all secrets

```
k get secrets --all-namespaces -o json | k replace -f -
```

- Verify encryption at rest

```
sudo ETCDCTL_API=3 etcdctl \
--cacert=/var/lib/minikube/certs/etcd/ca.crt \
--cert=/var/lib/minikube/certs/apiserver-etcd-client.crt \
--key=/var/lib/minikube/certs/apiserver-etcd-client.key \
get /registry/secrets/$namespace/$secretName | hexdump -C
```
