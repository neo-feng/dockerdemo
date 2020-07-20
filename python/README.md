 - cd /home/sample1/dockerdemo/python
# build your image 
 - sudo docker build -t hello_python_lucas:1.0 .

# check your image
 - sudo docker images

# create container
 - sudo docker run --name hello_python_lucas -d -p 9527:5000 hello_python_lucas:1.0

# verify container
 - sudo docker ps -a
 - sudo docker logs hello_python_lucas 

# verify web api
 - browser - http://localhost:9527/api/
 or curl http://localhost:9527/api/