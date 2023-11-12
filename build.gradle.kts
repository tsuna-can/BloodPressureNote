// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    extra.apply {
        set("room_version", "2.5.2")
        set("hilt_version", "2.44")
    }
    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:${rootProject.extra["hilt_version"]}")
    }
}

plugins {
    id("com.android.application") version "8.1.1" apply false
    id("org.jetbrains.kotlin.android") version "1.8.21" apply false
    id("io.gitlab.arturbosch.detekt") version "1.23.1"
}
