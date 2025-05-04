plugins {
    id("net.orekyuu.fansite.java-base")
    alias(libs.plugins.spring)
}

apply(plugin = "io.spring.dependency-management")

dependencies {
    implementation(project(":domain"))
    implementation(libs.spring.starter.web)
    testImplementation(libs.spring.starter.test)
}