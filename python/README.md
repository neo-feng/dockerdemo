# practice

## go to work folder

- cd /home/sample1/dockerdemo/python

## start without docker
- python main.py

## build your image

- sudo docker build -t hello_python_lucas:1.0 .

## check your image

- sudo docker images

## create container

- sudo docker run --name hello_python_lucas -d -p 9527:5000 hello_python_lucas:1.0

## verify container

- sudo docker ps -a

## verify web api

- browser - http://localhost:9527/api/
- curl -X POST --header 'Content-Type: application/json' --header 'Accept: text/html' -d '{"name": "Lucas"}' 'http://localhost:9527/api/dockertest'

## repo
- https://dev.azure.com/FENGNX/testproject/_git/dockerdemo