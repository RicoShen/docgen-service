# pull official base image
#ltang: Smallest but may miss out some libs
FROM eclipse-temurin:17.0.3_7-jre
#ltang: Smaller but may miss out some libs
#FROM eclipse-temurin:17.0.3_7-jre-alpine
#ltang: Big and contains all libs
#FROM eclipse-temurin:17.0.3_7-jdk

# set work directory
WORKDIR /app
WORKDIR /gendocs

ARG PROJECT_NAME=docgen-service

ARG JAR_FILE=build/libs/${PROJECT_NAME}-*.jar

# https://vsupalov.com/docker-arg-env-variable-guide/#setting-env-values
ENV APP_JAR=${PROJECT_NAME}-app.jar

COPY ${JAR_FILE} ${APP_JAR}

# entry point
#"-web -webAllowOthers -tcp -tcpAllowOthers -browser"
ENTRYPOINT java -jar ${APP_JAR}
EXPOSE ${CONTAINER_PORT}


