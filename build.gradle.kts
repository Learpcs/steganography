plugins {
    id("java")
    id("application")
}


repositories {
    mavenCentral()
}

//application {
//    mainClass.set("org.example.Encrypter")
//    applicationDefaultJvmArgs = listOf("-ea")
//}

dependencies {
    implementation("com.beust:jcommander:1.69")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

//tasks.jar {
//    manifest.attributes["Main-Class"] = "com.example.Encrypter"
//    val dependencies = configurations
//        .runtimeClasspath
//        .get()
//        .map(::zipTree) // OR .map { zipTree(it) }
//    from(dependencies)
//    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
//}

// tasks.jar {
// archiveBaseName.set("encrypter")
//     manifest {
//         attributes["Main-Class"] = "com.example.Encrypter"
//     }
// }

// tasks.register("encrypt", JavaExec) {
//     group = ApplicationPlugin.APPLICATION_GROUP
//     classpath = sourceSets.main.runtimeClasspath
//     mainClass = 'com.example.decrypter'
// }
//
// tasks.register("decrypt", JavaExec) {
//     group = ApplicationPlugin.APPLICATION_GROUP
//     classpath = sourceSets.main.runtimeClasspath
//     mainClass = 'com.example.decrypter'
// }

// tasks.decrypter {
//     archiveBaseName.set("decrypter")
//     manifest {
//         attributes["Main-Class"] = "com.example.Decrypter"
//     }
// }


