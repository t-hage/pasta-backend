import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.5.5"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("org.openapi.generator") version "5.1.1"

    kotlin("jvm") version "1.5.31"
    kotlin("plugin.spring") version "1.5.31"
}

group = "com.tom"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib")

    implementation("org.springdoc:springdoc-openapi-ui:1.5.10")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

kotlin {
    sourceSets["main"].apply {
        kotlin.srcDir("src/main/kotlin")
        kotlin.srcDir("$buildDir/generated/src/main/kotlin")
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

openApiGenerate {
    generatorName.set("kotlin-spring")
    inputSpec.set("$rootDir/src/main/resources/spec.yaml")
    outputDir.set("$buildDir/generated")
    apiPackage.set("com.tom.pasta.api")
    invokerPackage.set("com.tom.pasta.invoker")
    modelPackage.set("com.tom.pasta.model")
    modelNameSuffix.set("Dto")
    configOptions.set(
        mapOf(
            Pair("dateLibrary", "java8"),
            Pair("interfaceOnly", "true"),
            Pair("serviceInterface", "false"),
            Pair("serializableModel", "true"),
        )
    )
}

tasks.withType<KotlinCompile> {
    dependsOn("openApiGenerate")
}
