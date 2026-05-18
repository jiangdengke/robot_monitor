FROM node:22-alpine AS frontend-build
WORKDIR /app/frontend
COPY frontend/package*.json ./
RUN npm ci
COPY frontend/ ./
RUN npm run build

FROM eclipse-temurin:17-jdk-jammy AS backend-build
WORKDIR /app/backend
COPY backend/gradle ./gradle
COPY backend/gradlew backend/gradle.properties backend/settings.gradle.kts backend/build.gradle.kts ./
RUN chmod +x ./gradlew
COPY backend/src ./src
RUN ./gradlew bootJar --no-daemon

FROM eclipse-temurin:17-jre-jammy
RUN apt-get update \
    && apt-get install -y --no-install-recommends nginx ca-certificates bash \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /app
COPY --from=backend-build /app/backend/build/libs/jiangdk.jar /app/jiangdk.jar
COPY --from=frontend-build /app/frontend/dist/ /usr/share/nginx/html/
COPY docker/nginx.conf /etc/nginx/nginx.conf
COPY docker/entrypoint.sh /entrypoint.sh

RUN chmod +x /entrypoint.sh \
    && mkdir -p /run/nginx /var/cache/nginx /var/log/nginx

EXPOSE 7777
ENTRYPOINT ["/entrypoint.sh"]
