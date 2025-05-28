plugins {
    kotlin("jvm") version "2.0.21"
}

group = "com.demo"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.apache.poi:poi-ooxml:5.2.3")
    implementation("com.oracle.database.jdbc:ojdbc8:21.9.0.0") // 버전은 필요에 따라 변경

}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(18)
}