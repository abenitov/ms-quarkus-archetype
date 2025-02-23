rest-kotlin-quickstart-gradle Project

Este proyecto utiliza Quarkus, el Supersonic Subatomic Java Framework.
Si deseas aprender más sobre Quarkus, visita su sitio web.

Desde un enfoque DDD y siguiendo los principios de Arquitectura Hexagonal, el proyecto está dividido en cinco módulos:

    infrastructure
    domain
    application
    shared
    sharedKernel

    Nota: Todos los comandos descritos en este documento se deben ejecutar desde el directorio infrastructure.

Configuración del Entorno
Uso de Gradle, GraalVM y SDKMAN

Para generar el ejecutable nativo, es fundamental contar con una distribución de GraalVM compatible (por ejemplo, basada en JDK 21). Si usas SDKMAN, actualiza la lista de SDKs con:

bash
Copiar
sdk update

Luego, instala y configura una versión de GraalVM compatible con Java 21 (por ejemplo, 23.2.0.r17-grl):

bash
Copiar
sdk install java 23.2.0.r17-grl
sdk default java 23.2.0.r17-grl

Verifica la instalación con:

bash
Copiar
java -version

Asegúrate de que la variable JAVA_HOME (o la propiedad org.gradle.java.home en gradle.properties) apunte a tu distribución de GraalVM.
Modo de Desarrollo

Para ejecutar la aplicación en modo de desarrollo (con live coding y la Dev UI en http://localhost:8080/q/dev/), ejecuta:

bash
Copiar
../gradlew quarkusDev

Packaging y Ejecución de la Aplicación
Empaquetado en Modo JVM

Para empaquetar la aplicación en formato tradicional (no es un über-jar):

bash
Copiar
../gradlew build

Se generará el archivo quarkus-run.jar en build/quarkus-app/, y las dependencias en build/quarkus-app/lib/.

Ejecuta la aplicación con:

bash
Copiar
java -jar build/quarkus-app/quarkus-run.jar

Si prefieres un über-jar (con todas las dependencias incluidas):

bash
Copiar
../gradlew build -Dquarkus.package.type=uber-jar

Y la ejecutas con:

bash
Copiar
java -jar build/*-runner.jar

Creación de un Ejecutable Nativo
Build Nativo con Contenedor (en macOS con Apple Chip)

En macOS con chip Apple, para generar el ejecutable nativo es necesario forzar que el builder se ejecute usando la plataforma linux/amd64 (ya que el contenedor de GraalVM se encuentra configurado para esa arquitectura). Ejecuta el siguiente comando desde el directorio infrastructure:

bash
Copiar
DOCKER_DEFAULT_PLATFORM=linux/amd64 quarkus build --native \
-Dquarkus.native.container-build=true \
-Dquarkus.native.builder-image=quay.io/quarkus/ubi-quarkus-graalvmce-builder-image:jdk-21

Este comando configura la variable de entorno DOCKER_DEFAULT_PLATFORM para que Docker utilice linux/amd64, incluso en macOS con Apple chip.

Una vez finalizado, el ejecutable nativo se ubicará en el directorio build/ (usualmente con un nombre similar a infrastructure-unspecified-runner), y se puede ejecutar directamente:

bash
Copiar
./build/infrastructure-unspecified-runner

Para más detalles, consulta la Guía de Quarkus sobre Gradle Tooling.
Containerización de la Aplicación
Creación de la Imagen Docker

Para generar una imagen Docker que soporte múltiples arquitecturas, utiliza docker buildx. Por ejemplo, crea un Dockerfile (por ejemplo, Dockerfile.native-micro) en el directorio src/main/docker/ con contenido similar a:

dockerfile
Copiar
FROM registry.access.redhat.com/ubi8/ubi-minimal:8.4
WORKDIR /work/
COPY build/infrastructure-unspecified-runner /work/application
RUN chmod 775 /work/application
EXPOSE 8080
CMD ["/work/application"]

Luego, desde el directorio infrastructure, ejecuta:

bash
Copiar
docker buildx build --platform linux/amd64,linux/arm64 \
-t europe-west1-docker.pkg.dev/tripsapp-dev/gcf-artifacts/trips51-quarkus-archetype \
-f src/main/docker/Dockerfile.native-micro . --push

Este comando construye la imagen para ambas plataformas (amd64 y arm64) y la sube al registro especificado.
Despliegue en Cloud Run

Para desplegar en Cloud Run:

    Sube la imagen a Artifact Registry
    Asegúrate de que la imagen ya esté en el registro, lo cual se realiza con el comando anterior.

    Despliega en Cloud Run con gcloud:

bash
Copiar
gcloud run deploy trips51-quarkus-archetype \
--image europe-west1-docker.pkg.dev/tripsapp-dev/gcf-artifacts/trips51-quarkus-archetype \
--platform managed \
--region <TU-REGIÓN> \
--allow-unauthenticated

Reemplaza <TU-REGIÓN> por la región deseada (por ejemplo, europe-west1).

Cloud Run creará el servicio y te proporcionará la URL para acceder a la aplicación.
Resumen y Recursos Adicionales

    Modo de desarrollo: ../gradlew quarkusDev
    Empaquetado JVM: ../gradlew build
        Über-jar: ../gradlew build -Dquarkus.package.type=uber-jar
    Build nativo (en contenedor para macOS con Apple chip):

    bash
    Copiar
    DOCKER_DEFAULT_PLATFORM=linux/amd64 quarkus build --native -Dquarkus.native.container-build=true -Dquarkus.native.builder-image=quay.io/quarkus/ubi-quarkus-graalvmce-builder-image:jdk-21

    Containerización multi-arquitectura:

    bash
    Copiar
    docker buildx build --platform linux/amd64,linux/arm64 -t europe-west1-docker.pkg.dev/tripsapp-dev/gcf-artifacts/trips51-quarkus-archetype -f src/main/docker/Dockerfile.native-micro . --push

    Despliegue en Cloud Run: Usa gcloud run deploy para desplegar la imagen.

Guías Relacionadas

    Quarkus Extension for Spring DI API
    Kotlin Guide for Quarkus
    Quarkus Gradle Tooling

Esta guía abarca desde el desarrollo y compilación nativa (usando contenedor en macOS con Apple chip) hasta la containerización y despliegue en Cloud Run.
