FROM java:8-jre
MAINTAINER Neo Feng <neofeng@mzinno.com>

ADD ./target/hello-world.jar /app/
CMD ["java", "-Xmx200m", "-jar", "-Djava.security.egd=file:/dev/./urandom", "/app/hello-world.jar", "--spring.profiles.active=default"]

EXPOSE 5000