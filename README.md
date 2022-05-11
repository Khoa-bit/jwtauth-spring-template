# Getting Started

## Description

The template is a Maven project built around [auth0 JWT library](https://github.com/auth0/java-jwt).

[Maven package](https://mvnrepository.com/artifact/com.auth0/java-jwt): `maven: com.auth0 / java-jwt / 3.19.2`

## How to run template

First, have Java 17 installed and selected on your computer.

Second, populate `application.properties` file

```properties
spring.data.mongodb.uri=mongodb+srv://<username>:<pwd>@<cluster>.mongodb.net/<DB_name>
spring.data.mongodb.database=<DB_name>
server.error.include-message=always
jwt.secret="my-secret-key"
```

Third, run SpringBoot server in CLI

```bash
./mvnw spring-boot:run
```

Open [http://localhost:8080](http://localhost:8080) with your browser to see SpringBoot APIs in action.

---

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.6.7/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.6.7/maven-plugin/reference/html/#build-image)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.6.7/reference/htmlsingle/#boot-features-developing-web-applications)
* [Spring Security](https://docs.spring.io/spring-boot/docs/2.6.7/reference/htmlsingle/#boot-features-security)

### Guides
Base code from [Amigoscode](https://www.youtube.com/channel/UC2KfmYEM4KCuA1ZurravgYw)'s video:

* [Spring Boot and Spring Security with JWT including Access and Refresh Tokens 🔑](https://www.youtube.com/watch?v=VVn9OG9nfH0)

The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)
* [Securing a Web Application](https://spring.io/guides/gs/securing-web/)
* [Spring Boot and OAuth2](https://spring.io/guides/tutorials/spring-boot-oauth2/)
* [Authenticating a User with LDAP](https://spring.io/guides/gs/authenticating-ldap/)

