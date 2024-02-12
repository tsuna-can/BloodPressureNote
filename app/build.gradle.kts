import io.gitlab.arturbosch.detekt.Detekt

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.ksp)
    alias(libs.plugins.detekt)
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.example.bloodpressurenote"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.bloodpressurenote"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        debug {
            isDebuggable = true
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.7"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtimeKtx)
    implementation(libs.androidx.lifecycle.runtimeCompose)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.toolingPreview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.compose.material.iconsExtended)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.hilt.navigationCompose)
    implementation(libs.hilt.android)
    implementation(libs.vico.compose)
    implementation(libs.vico.compose.m3)
    implementation(libs.vico.core)
    implementation("org.jetbrains.kotlinx:kotlinx-collections-immutable:0.3.6")
    implementation("com.kizitonwose.calendar:compose:2.5.0")
    ksp(libs.androidx.room.compiler)
    kapt(libs.hilt.android.compiler)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.testManifest)

    detektPlugins(libs.detekt.formatting)
    detektPlugins (libs.detekt.compose.rules)
}

detekt{
    config.setFrom("${rootProject.projectDir}/config/detekt/detekt.yml")
    buildUponDefaultConfig = true
    autoCorrect = true
}

tasks {
    withType<Detekt> {
        jvmTarget = JavaVersion.VERSION_17.toString()

        reports {
            html.required.set(true)
            html.outputLocation.set(file("${buildDir}/reports/detekt.html"))
            xml.required.set(false)
            txt.required.set(true)
            sarif.required.set(true)
        }
    }

}
