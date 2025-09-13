plugins {
    id("java")
    id ("org.graalvm.buildtools.native" ) version("0.9.23")
}

group = "com.andmal"
version = "0.0.3"

repositories {
    mavenCentral()
}

// https://graalvm.github.io/native-build-tools/latest/gradle-plugin.html
graalvmNative {
    binaries {
        named("main") {
            javaLauncher.set(javaToolchains.launcherFor {
                languageVersion.set(JavaLanguageVersion.of(19))
            })
            mainClass.set("com.andmal.Main")
            verbose.set(true)
        }
    }
}

java {
    toolchain {
        version = JavaVersion.VERSION_24
    }
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "com.andmal.Main"
    }
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    from(sourceSets.main.get().output)
    dependsOn(configurations.runtimeClasspath)
    from({
        configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
    })
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}