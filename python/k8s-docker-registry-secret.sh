# refresh k8s docker-registry secret with ecr password which expire every 12 hours
#!/bin/bash

set +x #disable logs to prevent printing password
PASS=$(aws ecr get-login-password --region us-east-2)
kubectl delete secret aws-ecr-secret
kubectl create secret docker-registry aws-ecr-secret \
    --docker-server=628006477714.dkr.ecr.us-east-2.amazonaws.com \
    --docker-username=AWS \
    --docker-password=$PASS \ 
