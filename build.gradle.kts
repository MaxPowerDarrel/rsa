plugins {
    kotlin("jvm") version "2.1.21"
}

group = "io.darrel"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    testImplementation(kotlin("test-junit"))
}

kotlin {
    jvmToolchain(21)
}

tasks.test {
    useJUnit()
}

sourceSets {
    main {
        kotlin.srcDir("src/main/kotlin")
    }
    test {
        kotlin.srcDir("src/test/kotlin")
    }
}
