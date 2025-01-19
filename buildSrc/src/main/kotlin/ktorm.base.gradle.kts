
group = rootProject.group
version = rootProject.version

plugins {
    id("kotlin")
    id("org.gradle.jacoco")
    id("io.gitlab.arturbosch.detekt")
}

repositories {
    mavenCentral()
}

dependencies {
    api(kotlin("stdlib"))
    api(kotlin("reflect"))
    testImplementation(kotlin("test-junit"))
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:${detekt.toolVersion}")
}

detekt {
    source.setFrom("src/main/kotlin")
    config.setFrom("${project.rootDir}/detekt.yml")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

tasks {
    // Lifecycle task for code generation.
    val codegen by registering { /* do nothing */ }

    compileKotlin {
        dependsOn(codegen)

        compilerOptions {
            jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_1_8
            allWarningsAsErrors = true
            freeCompilerArgs = listOf("-Xexplicit-api=strict")
        }
    }

    jacocoTestReport {
        reports {
            csv.required = true
            xml.required = true
            html.required = true
        }
    }
}
