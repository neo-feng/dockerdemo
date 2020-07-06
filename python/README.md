# build your image 
 - docker build -t hello_{{your_name}} .
 - e.g. docker build -t hello_neo .

# check your image
 - docker images

# create container
 - docker run --name hello_neo -d -p 9527:5000 hello_neo

# verify container
 - docker ps -a
 - docker logs hello_neo 

# verify web api
 - browser - http://localhost:9527/api/#!/Test32Api/post_api_dockertest 