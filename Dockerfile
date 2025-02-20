FROM docker.io/library/gradle:8.11.1-jdk21 AS mod
WORKDIR /app
# Only copy dependency-related files
COPY build.gradle gradle.properties settings.gradle /app/
# Only download dependencies
# Eat the expected build failure since no source code has been copied yet
RUN gradle clean build --no-daemon > /dev/null 2>&1 || true
# Copy all files
COPY . /app
# Do the actual build
RUN gradle clean build --no-daemon

FROM docker.io/itzg/minecraft-server:java21
ENV VERSION="1.21.4"
ENV TYPE="FABRIC"
ENV FABRIC_LAUNCHER_VERSION="1.0.1"
ENV FABRIC_LOADER_VERSION="0.16.10"
ENV MODRINTH_DOWNLOAD_DEPENDENCIES="required"
ENV MODRINTH_PROJECTS="fabric-api,fabric-language-kotlin"

EXPOSE 44533

COPY --from=mod /app/build/libs/potion-mod-1.0.0.jar /mods/potion-mod-1.0.0.jar

