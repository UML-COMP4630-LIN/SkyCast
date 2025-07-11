// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google() // Ensures dependencies are fetched from Google's repository.
        mavenCentral()
    }
    dependencies {
        val nav_version = "2.8.3"
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$nav_version")


    }
}

plugins {
    alias(libs.plugins.android.application) apply false

}