#FROM eclipse-temurin:17-jre-alpine as builder
#WORKDIR extracted
#ADD ./target/*.jar app.jar
#RUN java -Djarmode=layertools -jar app.jar extract
#
#FROM eclipse-temurin:17-jre-alpine
#WORKDIR application
#COPY --from=builder extracted/dependencies/ ./
#COPY --from=builder extracted/spring-boot-loader/ ./
#COPY --from=builder extracted/snapshot-dependencies/ ./
#COPY --from=builder extracted/application/ ./
#EXPOSE 8080
#ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]

# Source guide https://blog.monosoul.dev/2022/04/25/reduce-java-docker-image-size/

# Used to analyze dependencies bacause jdeps from JDK 17 has a bug
# https://stackoverflow.com/questions/69943899/jdeps-cant-print-module-deps-due-to-a-multireleaseexception
FROM amazoncorretto:18-alpine as deps

# Identify dependencies
ADD ./build/libs/*.jar /app/app.jar
RUN mkdir /app/unpacked && \
    cd /app/unpacked && \
    unzip -q ../app.jar && \
    cd .. && \
    $JAVA_HOME/bin/jdeps \
    --ignore-missing-deps \
    --print-module-deps \
    -q \
    --recursive \
    --multi-release 17 \
    --class-path="./unpacked/BOOT-INF/lib/*" \
    --module-path="./unpacked/BOOT-INF/lib/*" \
    ./app.jar > /deps.info

#RUN cd /app && \
#    $JAVA_HOME/bin/jdeps \
#        --ignore-missing-deps \
#        --print-module-deps \
#        -q \
#        --recursive \
#        --multi-release 17 \
#        ./app.jar > /deps.info


# base image to build a JRE
FROM amazoncorretto:17-alpine as corretto-jdk

# required for strip-debug to work
RUN apk add --no-cache binutils

# copy module dependencies info
COPY --from=deps /deps.info /deps.info

# Build small JRE image
RUN $JAVA_HOME/bin/jlink \
    --verbose \
    --add-modules $(cat /deps.info) \
    --strip-debug \
    --no-man-pages \
    --no-header-files \
    --compress=2 \
    --output /customjre

# main app image
FROM alpine:latest
ENV JAVA_HOME=/jre
ENV PATH="${JAVA_HOME}/bin:${PATH}"

# copy JRE from the base image
COPY --from=corretto-jdk /customjre $JAVA_HOME

# Add app user
ARG APPLICATION_USER=appuser
RUN adduser --no-create-home -u 1000 -D $APPLICATION_USER

# Configure working directory
RUN mkdir /app && \
    chown -R $APPLICATION_USER /app

USER 1000

COPY --chown=1000:1000 ./build/libs/*.jar /app/app.jar
#WORKDIR /app

EXPOSE 8080
ENTRYPOINT [ "/jre/bin/java", "-jar", "/app/app.jar" ]

