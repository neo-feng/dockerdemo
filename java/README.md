# build your image 
 - docker build -t hello_{{your_name}} .
 - e.g. docker build -t hello_java_lucas:1.0 .

# check your image
 - docker images

# create container
 - docker run --name hello_java_lucas -d -p 9526:5000 hello_java_lucas:1.0

# verify container
 - docker ps -a
 - docker logs hello_java_lucas 

# verify web api
 - browser - http://localhost:9526/api/
 or curl http://localhost:9526/api/