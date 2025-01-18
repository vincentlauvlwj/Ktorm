
plugins {
    id("ktorm.base")
    id("ktorm.publish")
    id("ktorm.source-header-check")
}

dependencies {
    api("com.google.devtools.ksp:symbol-processing-api:2.1.0-1.0.29")
    api("com.squareup:kotlinpoet-ksp:1.11.0")
}
