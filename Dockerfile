FROM adoptopenjdk/openjdk11
WORKDIR /app/
COPY . .
RUN ./mvnw clean package -DskipTests

FROM adoptopenjdk/openjdk11
WORKDIR /deploy/
COPY --from=0 /app/target/kitpa-server-0.1.jar /deploy/kitpa-server-0.1.jar
COPY --from=0 /app/apm /deploy/apm

ARG WHATAP
ENV WHATAP ${WHATAP}

RUN sed -i "1s/.*/${WHATAP}/g" /deploy/apm/whatap/whatap.conf

ENV SPRING_OPTION="-Dspring.profiles.active=local"
ENTRYPOINT exec java -javaagent:/deploy/apm/whatap/whatap.agent-2.2.16.jar -jar ${SPRING_OPTION} kitpa-server-0.1.jar