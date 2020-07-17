FROM java:8-jre
MAINTAINER Neo Feng <neofeng@mzinno.com>

ADD ./target/service-city.jar /app/
CMD ["java", "-Xmx200m", "-jar", "-Djava.security.egd=file:/dev/./urandom", "/app/service-city.jar", "--spring.profiles.active=${SPRING_PROFILES_ACTIVE}"]

EXPOSE 5000