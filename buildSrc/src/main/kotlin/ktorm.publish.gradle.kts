
plugins {
    id("kotlin")
    id("signing")
    id("maven-publish")
    id("org.jetbrains.dokka")
}

val jarSources by tasks.registering(Jar::class) {
    dependsOn("codegen")
    from(sourceSets.main.map { it.allSource })
    archiveClassifier = "sources"
}

val jarJavadoc by tasks.registering(Jar::class) {
    dependsOn(tasks.dokkaJavadoc)
    from(tasks.dokkaJavadoc.flatMap { it.outputDirectory })
    archiveClassifier = "javadoc"
}

publishing {
    publications {
        create<MavenPublication>("dist") {
            from(components["java"])
            artifact(jarSources)
            artifact(jarJavadoc)

            groupId = project.group.toString()
            artifactId = project.name
            version = project.version.toString()

            pom {
                name = "${project.group}:${project.name}"
                description = "A lightweight ORM Framework for Kotlin with strong typed SQL DSL and sequence APIs."
                url = "https://www.ktorm.org"
                licenses {
                    license {
                        name = "The Apache Software License, Version 2.0"
                        url = "http://www.apache.org/licenses/LICENSE-2.0.txt"
                    }
                }
                scm {
                    url = "https://github.com/kotlin-orm/ktorm"
                    connection = "scm:git:https://github.com/kotlin-orm/ktorm.git"
                    developerConnection = "scm:git:ssh://git@github.com/kotlin-orm/ktorm.git"
                }
                developers {
                    developer {
                        id = "vincentlauvlwj"
                        name = "vince"
                        email = "me@liuwj.me"
                    }
                    developer {
                        id = "waluo"
                        name = "waluo"
                        email = "1b79349b@gmail.com"
                    }
                    developer {
                        id = "clydebarrow"
                        name = "Clyde"
                        email = "clyde@control-j.com"
                    }
                    developer {
                        id = "Ray-Eldath"
                        name = "Ray Eldath"
                        email = "ray.eldath@outlook.com"
                    }
                    developer {
                        id = "hangingman"
                        name = "hiroyuki.nagata"
                        email = "idiotpanzer@gmail.com"
                    }
                    developer {
                        id = "onXoot"
                        name = "beetlerx"
                        email = "beetlerx@gmail.com"
                    }
                    developer {
                        id = "arustleund"
                        name = "Andrew Rustleund"
                        email = "andrew@rustleund.com"
                    }
                    developer {
                        id = "afezeria"
                        name = "afezeria"
                        email = "zodal@outlook.com"
                    }
                    developer {
                        id = "scorsi"
                        name = "Sylvain Corsini"
                        email = "sylvain.corsini@protonmail.com"
                    }
                    developer {
                        id = "lyndsysimon"
                        name = "Lyndsy Simon"
                        email = "lyndsy@lyndsysimon.com"
                    }
                    developer {
                        id = "antonydenyer"
                        name = "Antony Denyer"
                        email = "git@antonydenyer.co.uk"
                    }
                    developer {
                        id = "mik629"
                        name = "Mikhail Erkhov"
                        email = "mikhail.erkhov@gmail.com"
                    }
                    developer {
                        id = "sinzed"
                        name = "Saeed Zahedi"
                        email = "saeedzhd@gmail.com"
                    }
                    developer {
                        id = "smn-dv"
                        name = "Simon Schoof"
                        email = "simon.schoof@hey.com"
                    }
                    developer {
                        id = "pedrod"
                        name = "Pedro Domingues"
                        email = "pedro.domingues.pt@gmail.com"
                    }
                    developer {
                        id = "efenderbosch"
                        name = "Eric Fenderbosch"
                        email = "eric@fender.net"
                    }
                    developer {
                        id = "kocproz"
                        name = "Kacper Stasiuk"
                        email = "kocproz@pm.me"
                    }
                    developer {
                        id = "2938137849"
                        name = "ccr"
                        email = "2938137849@qq.com"
                    }
                    developer {
                        id = "zuisong"
                        name = "zuisong"
                        email = "com.me@foxmail.com"
                    }
                    developer {
                        id = "svenallers"
                        name = "Sven Allers"
                        email = "sven.allers@gmx.de"
                    }
                    developer {
                        id = "lookup-cat"
                        name = "夜里的向日葵"
                        email = "641571835@qq.com"
                    }
                    developer {
                        id = "michaelfyc"
                        name = "michaelfyc"
                        email = "michael.fyc@outlook.com"
                    }
                    developer {
                        id = "brohacz"
                        name = "Michal Brosig"
                    }
                    developer {
                        id = "hc224"
                        name = "hc224"
                        email = "hc224@pm.me"
                    }
                }
            }
        }

        repositories {
            maven {
                name = "central"
                url = uri("https://oss.sonatype.org/service/local/staging/deploy/maven2")
                credentials {
                    username = System.getenv("OSSRH_USER")
                    password = System.getenv("OSSRH_PASSWORD")
                }
            }
            maven {
                name = "snapshot"
                url = uri("https://oss.sonatype.org/content/repositories/snapshots")
                credentials {
                    username = System.getenv("OSSRH_USER")
                    password = System.getenv("OSSRH_PASSWORD")
                }
            }
        }
    }
}

signing {
    val keyId = System.getenv("GPG_KEY_ID")
    val secretKey = System.getenv("GPG_SECRET_KEY")
    val password = System.getenv("GPG_PASSWORD")

    setRequired {
        !project.version.toString().endsWith("SNAPSHOT")
    }

    useInMemoryPgpKeys(keyId, secretKey, password)
    sign(publishing.publications["dist"])
}
