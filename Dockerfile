FROM openjdk:17

COPY BootThymeleafCRUD2/target/spring-boot-aws-exe.jar /usr/app/

WORKDIR /usr/app/

ENTRYPOINT ["java", "-jar", "spring-boot-aws-exe.jar"]