# 빌드 단계
FROM openjdk:11 AS build

WORKDIR /app

# Gradle Wrapper와 소스 코드를 복사
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src

# Gradle Wrapper 실행 권한 부여
RUN chmod +x ./gradlew

# 의존성 다운로드 및 애플리케이션 빌드
RUN ./gradlew build -x test

# 실행 단계
FROM openjdk:11-jre-slim

# 빌드 결과물을 애플리케이션으로 복사
COPY --from=build /app/build/libs/*.jar app.jar

# 애플리케이션 실행
CMD ["java", "-jar", "/app.jar"]
