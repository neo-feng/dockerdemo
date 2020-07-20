 - cd /home/sample1/dockerdemo/java
# build your image 
 - sudo docker build -t hello_java_lucas:1.0 .

# check your image
 - sudo docker images

# create container
 - sudo docker run --name hello_java_lucas -d -p 9526:5000 hello_java_lucas:1.0

# verify container
 - sudo docker ps -a
 - sudo docker logs hello_java_lucas 

# verify web api
 - browser - http://localhost:9526/api/dockertest
 or curl http://localhost:9526/api/dockertest