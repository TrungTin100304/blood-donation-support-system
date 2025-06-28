# Base image với JDK 24 từ Adoptium (Temurin)
FROM eclipse-temurin:24-jdk

# Đặt thư mục làm việc
WORKDIR /app

# Copy file jar vào image
COPY target/*.jar app.jar

# Mở cổng (tuỳ ứng dụng)
EXPOSE 8080

# Lệnh chạy ứng dụng
ENTRYPOINT ["java", "-jar", "app.jar"]
