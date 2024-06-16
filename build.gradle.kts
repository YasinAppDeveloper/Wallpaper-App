buildscript {
    dependencies {
        classpath(libs.google.services)
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.32")
    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
}