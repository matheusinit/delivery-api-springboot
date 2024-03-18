FROM eclipse-temurin:17-jdk-alpine AS build

WORKDIR /app

COPY ../../gradle gradle

COPY ../../build.gradle ../../settings.gradle ../../gradlew ./

COPY ../../src/ src/

RUN ./gradlew build -x test -x integrationTest

RUN mkdir -p build/libs/dependency && (cd build/libs/dependency; jar -xf ../delivery-api-rest-0.0.1-SNAPSHOT.jar)

FROM build as integration-test

WORKDIR /app

ENTRYPOINT ["./gradlew", "integrationTest"]

FROM eclipse-temurin:17-jdk-alpine as production
VOLUME /tmp

ARG DEPENDENCY=/app/build/libs/dependency

COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app

ENTRYPOINT ["java","-cp","app:app/lib/*", "com.deliveryapirest.DeliveryApiRestApplication"]

