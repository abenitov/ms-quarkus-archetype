# rest-kotlin-quickstart-gradle Project

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

From a DDD approach and Hexagonal architecture consideration, the project is divided into three modules:

- infrastructure
- domain
- application
- shared
- sharedKernel

All the command described in this document should be run from the infrastructure folder.

## Running the application in dev mode

You can run your application in dev mode that enables live coding, using:
```shell script
../gradlew quarkusDev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

## Packaging and running the application

The application can be packaged using:
```shell script
../gradlew build
```
It produces the `quarkus-run.jar` file in the `build/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `build/quarkus-app/lib/` directory.

The application is now runnable using `java -jar build/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:
```shell script
../gradlew build -Dquarkus.package.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar build/*-runner.jar`.

## Creating a native executable

You can create a native executable using: 
```shell script
../gradlew build -Dquarkus.package.type=native
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
../gradlew build -Dquarkus.package.type=native -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./build/rest-kotlin-quickstart-gradle-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/gradle-tooling.

## Containerizing the application

You can create a containerized version of the application using:
```shell script
docker build -f src/main/docker/Dockerfile.native-micro -t quarkus/archetype-app .
```

## Related Guides

- Quarkus Extension for Spring DI API ([guide](https://quarkus.io/guides/spring-di)): Define your dependency injection with Spring DI
- Kotlin ([guide](https://quarkus.io/guides/kotlin)): Write your services in Kotlin

## Provided Code

### RESTEasy Reactive

Easily start your Reactive RESTful Web Services

[Related guide section...](https://quarkus.io/guides/getting-started-reactive#reactive-jax-rs-resources)
