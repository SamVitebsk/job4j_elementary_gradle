plugins {
    java
    checkstyle
    application
}

group = "ru.job4j"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.3")
    testImplementation("org.junit.vintage:junit-vintage-engine:5.9.3")
    testImplementation("org.assertj:assertj-core:3.24.2")
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

tasks.test {
    useJUnitPlatform()

    testLogging {
        events ("passed", "skipped", "failed")
    }
}

checkstyle {
    config = resources.text.fromFile(file("${project.rootDir}"))
    toolVersion = "10.21.2"
    configFile = file("checkstyle.xml")
    isShowViolations = true
    isIgnoreFailures = false
}

tasks.withType<Checkstyle> {
    reports {
        xml.required = true
        html.required = true
    }
}

sourceSets {
    test {
        compileClasspath += configurations.getByName("testRuntimeClasspath")
        runtimeClasspath += configurations.getByName("testRuntimeClasspath")
    }
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

application {
    mainClass = "ru.job4j.Main"
}

tasks.build {
    dependsOn("checkstyleMain")
}