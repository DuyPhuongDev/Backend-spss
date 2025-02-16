# Sử dụng openjdk 21 làm base image
FROM openjdk:21-jdk-slim

# Chỉ định thư mục làm việc trong container
WORKDIR /app

# Copy source code vào container
COPY . .

# Chạy Maven để build ứng dụng
RUN ./mvnw clean package -DskipTests

# Mở cổng cho ứng dụng
EXPOSE 8080

# Lệnh chạy ứng dụng (chạy file JAR trong target/)
ENTRYPOINT ["java", "-jar", "target/be-0.0.1-SNAPSHOT.jar"]
