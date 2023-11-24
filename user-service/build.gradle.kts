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

dependencyManagement {
	imports {
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:2022.0.3")
	}
}

dependencies {

	implementation(project(":common"))
//	implementation(files("../common/build/libs/common-0.0.1-SNAPSHOT.jar"))

	// Spring support
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.cloud:spring-cloud-starter-stream-kafka")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
//	developmentOnly("org.springframework.boot:spring-boot-devtools")
//	implementation("org.springframework.boot:spring-boot-starter-hateoas")
//	implementation("org.springframework.boot:spring-boot-starter-security")
//	implementation("org.springframework.session:spring-session-core")

	// Hibernate-reactive support
	implementation("io.vertx:vertx-pg-client:4.4.6")
	implementation("com.ongres.scram:client:2.1")
	implementation("org.hibernate.reactive:hibernate-reactive-core:2.0.6.Final")
	implementation("io.smallrye.reactive:mutiny-reactor:2.5.1")
	implementation("io.smallrye.reactive:mutiny-zero-flow-adapters:1.0.0")
	compileOnly("org.hibernate.orm:hibernate-jpamodelgen")

	// Lombok
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")

//	implementation("org.flywaydb:flyway-core")

	// Testing support
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.projectreactor:reactor-test")
	testImplementation("org.springframework.cloud:spring-cloud-stream-test-binder")
//	testImplementation("org.springframework.kafka:spring-kafka-test")
//	testImplementation("org.springframework.security:spring-security-test")

}

tasks.withType<Test> {
	useJUnitPlatform()
}
