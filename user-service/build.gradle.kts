plugins {
	java
	id("org.springframework.boot") version "3.1.5"
	id("io.spring.dependency-management") version "1.1.3"
}

group = "com.forma-nova"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {

	// Spring support
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("org.springframework.boot:spring-boot-starter-validation")
//	implementation("org.springframework.boot:spring-boot-starter-actuator")
//	developmentOnly("org.springframework.boot:spring-boot-devtools")
//	implementation("org.springframework.boot:spring-boot-starter-security")
//	implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
//	implementation("org.springframework.boot:spring-boot-starter-hateoas")
//	implementation("org.springframework.session:spring-session-core")
//	implementation("org.springframework.kafka:spring-kafka")
//	implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")

	// Hibernate-reactive support
	implementation("io.vertx:vertx-pg-client:4.4.6")
	implementation("com.ongres.scram:client:2.1")
	implementation("org.hibernate.reactive:hibernate-reactive-core:2.0.6.Final")
	implementation("io.smallrye.reactive:mutiny-reactor:2.5.1")
	implementation("io.smallrye.reactive:mutiny-zero-flow-adapters:1.0.0")
	compileOnly("org.hibernate.orm:hibernate-jpamodelgen")

//	implementation("org.flywaydb:flyway-core")

	// Lombok
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")

	// Testing support
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.projectreactor:reactor-test")
//	testImplementation("org.springframework.kafka:spring-kafka-test")
//	testImplementation("org.springframework.security:spring-security-test")

}

tasks.withType<Test> {
	useJUnitPlatform()
}