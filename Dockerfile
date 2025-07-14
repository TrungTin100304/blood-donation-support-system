# # Sử dụng image JDK chính thức
# FROM openjdk:17-jdk-slim

# # Đặt thư mục làm việc
# WORKDIR /app

# # Sao chép file JAR vào container
# COPY target/blood-donation-support-system-0.0.1-SNAPSHOT.jar app.jar

# # Cổng ứng dụng Spring Boot (mặc định 8080)
# EXPOSE 8080

# # Lệnh chạy ứng dụng
# ENTRYPOINT ["java", "-jar", "app.jar"]
# Build stage
FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Run stage
FROM openjdk:17
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
