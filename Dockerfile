FROM maven:3.8.4-openjdk-11
COPY . /app
WORKDIR /app
RUN mvn clean package
CMD ["java", "-jar", "target/*.jar"]
