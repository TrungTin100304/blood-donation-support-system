# Sử dụng image JDK chính thức
FROM openjdk:17-jdk-slim

# Đặt thư mục làm việc
WORKDIR /app

# Sao chép file JAR vào container
COPY target/your-app.jar app.jar

# Cổng ứng dụng Spring Boot (mặc định 8080)
EXPOSE 8080

# Lệnh chạy ứng dụng
ENTRYPOINT ["java", "-jar", "app.jar"]