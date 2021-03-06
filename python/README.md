# practice

## go to work folder

- cd /home/sample1/dockerdemo/python

## start without docker 
- 本地安装python和pip
- pip install -r requirements.txt
- python main.py
- open in browser http://localhost:5000/ 
- (pip3, python3ß)
## build your image

- docker build -t hello_python_lucas:1.0 .

## check your image

- docker images

## create container

- docker run --name hello_python_lucas -d -p 9527:5000 hello_python_lucas:1.0

## verify container

- docker ps -a

## verify web api
- browser - http://localhost:9527
- browser - http://localhost:9527/api/
- curl -X POST --header 'Content-Type: application/json' --header 'Accept: text/html' -d '{"name": "Lucas"}' 'http://localhost:9527/api/dockertest'

## push image to docker hub
- docker tag hello_python_lucas:1.0 neofeng/hello_python_lucas:1.0
- docker login --username neofeng
- docker push neofeng/hello_python_lucas:1.0


## push image to ECR
- docker tag hello_python_lucas:1.0 628006477714.dkr.ecr.us-east-2.amazonaws.com/hello_python_lucas:1.0
- aws ecr get-login-password --region us-east-2 | docker login --username AWS --password-stdin 628006477714.dkr.ecr.us-east-2.amazonaws.com
- docker push 628006477714.dkr.ecr.us-east-2.amazonaws.com/hello_python_lucas:1.0

# remote to EC2 on AWS or any other machine you get
- ssh -i "teststandard.pem" ec2-user@ec2-13-59-164-106.us-east-2.compute.amazonaws.com

## pull image from another environment
- aws ecr get-login-password --region us-east-2 | docker login --username AWS --password-stdin 628006477714.dkr.ecr.us-east-2.amazonaws.com
- docker pull  628006477714.dkr.ecr.us-east-2.amazonaws.com/hello_python_lucas:1.0

## run and verify
- docker run --name hello_python_lucas -d -p 9527:5000 628006477714.dkr.ecr.us-east-2.amazonaws.com/hello_python_lucas:1.0
- curl -X POST --header 'Content-Type: application/json' --header 'Accept: text/html' -d '{"name": "Lucas"}' 'http://localhost:9527/api/dockertest'