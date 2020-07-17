# build your image 
 - docker build -t hello_{{your_name}} .
 - e.g. docker build -t hello_python_lucas:1.0 .

# check your image
 - docker images

# create container
 - docker run --name hello_python_lucas -d -p 9527:5000 hello_python_lucas:1.0

# verify container
 - docker ps -a
 - docker logs hello_python_lucas 

# verify web api
 - browser - http://localhost:9527/api/#!/Test32Api/post_api_dockertest 