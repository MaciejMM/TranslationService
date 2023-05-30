plugins {
    java
    id("org.springframework.boot") version "3.0.5"
    id("io.spring.dependency-management") version "1.1.0"
    id("io.swagger.core.v3.swagger-gradle-plugin") version "2.2.9"
    kotlin("jvm") version "1.8.20"

}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation ("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.1.0")
	implementation ("org.springframework.boot:spring-boot-starter-validation")
    compileOnly ("org.projectlombok:lombok:1.18.26")
    annotationProcessor ("org.projectlombok:lombok:1.18.26")

    runtimeOnly("org.postgresql:postgresql")

    testCompileOnly ("org.projectlombok:lombok:1.18.26")

    testAnnotationProcessor ("org.projectlombok:lombok:1.18.26")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.mockk:mockk:1.13.5")
    testImplementation("com.h2database:h2:1.3.148")
    testImplementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")

}

tasks.withType<Test> {
    useJUnitPlatform()
}
