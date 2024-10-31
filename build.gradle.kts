plugins {
    id("java")
    id("application")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.beust:jcommander:1.69")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.register<JavaExec>("decrypter") {
    mainClass.set("org.example.Decrypter")
    classpath = sourceSets.main.get().runtimeClasspath
}

tasks.register<JavaExec>("encrypter") {
    mainClass.set("org.example.Encrypter")
    classpath = sourceSets.main.get().runtimeClasspath
}
