# Sử dụng openjdk 21 làm base image
FROM openjdk:21-jdk-slim
# Chỉ định thư mục làm việc trong container cho ứng dụng
WORKDIR /app

# Sao chép file JAR vào thư mục làm việc
COPY target/be-0.0.1-SNAPSHOT.jar app.jar

# Mở cổng cho ứng dụng (giả sử ứng dụng chạy trên port 8080)
EXPOSE 8080

# Lệnh để chạy ứng dụng
ENTRYPOINT ["java", "-jar", "app.jar"]