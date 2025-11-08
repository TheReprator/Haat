import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.room)
    alias(libs.plugins.kotlin.serialization)
}

fun loadReleaseProperties(): Properties {
    val keystorePropertiesFile = rootProject.file("secrets/keystore.properties")
    val keystoreProperties = Properties()

    if (!keystorePropertiesFile.exists()) {
        println("keystore.properties not found at: ${keystorePropertiesFile.path}")
        return keystoreProperties
    }

    keystoreProperties.load(keystorePropertiesFile.inputStream())
    return keystoreProperties
}

android {
    namespace = "dev.reprator.haat"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "dev.reprator.haat"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        val keystoreProperties = loadReleaseProperties()

        if (keystoreProperties.isNotEmpty()) {
            create("release") {
                storePassword = keystoreProperties["KEYSTORE_PASSWORD"] as String
                keyAlias = keystoreProperties["KEY_ALIAS"] as String
                keyPassword = keystoreProperties["KEY_PASSWORD"] as String
                val storeFileName = keystoreProperties["STORE_FILE"] as String
                storeFile = rootProject.file("secrets/$storeFileName")
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            if (signingConfigs.names.contains("release")) {
                signingConfig = signingConfigs.getByName("release")
            } else {
                println("build unsigned release.")
            }
        }
    }

    packaging {
        resources.excludes.add("META-INF/*")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    composeCompiler {
        stabilityConfigurationFiles.add(rootProject.layout.projectDirectory.file("stability_config.conf"))
    }
    kotlin {
        compilerOptions {
            jvmTarget = JvmTarget.JVM_21
            freeCompilerArgs.addAll(
                "-XXLanguage:+PropertyParamAnnotationDefaultTargetMode",

                "-P", "plugin:androidx.compose.compiler.plugins.kotlin:experimentalStrongSkipping=true",

                "-P", "plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=${layout.buildDirectory.asFile.get()}/compose_reports",
                "-P", "plugin:androidx.compose.compiler.plugins.kotlin:metricsDestination=${layout.buildDirectory.asFile.get()}/compose_metrics"
            )
        }
    }

    buildFeatures {
        compose = true
    }

    room {
        schemaDirectory("$projectDir/schemas")
    }
}

dependencies {
    implementation(libs.external.blurhash)

    implementation(libs.retrofit)
    implementation(libs.retrofit.logger)
    implementation(libs.retrofit.converter.jackson)
    implementation(libs.jackson.module.kotlin)

    implementation(libs.jetbrains.coroutine.core)
    implementation(libs.jetbrains.coroutine.android)

    ksp(libs.hilt.compiler)
    implementation(libs.hilt.core)
    implementation(libs.hilt.android)

    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)

    implementation(libs.coil.kt)
    implementation(libs.coil.kt.compose)

    implementation(libs.androidx.hilt.viewmodel.compose)
    implementation(libs.androidx.hilt.navigation.compose)
    //implementation(libs.androidx.lifecycle.runtimeCompose)
    //implementation(libs.androidx.lifecycle.viewModelCompose)
    //implementation(libs.androidx.navigation.compose)
    //implementation(libs.androidx.lifecycle.viewmodel)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.compose)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.fonts)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    //implementation(libs.androidx.window.core)
    implementation(libs.androidx.compose.material3.navigationSuite)
    implementation(libs.androidx.compose.material3.adaptive)
    implementation(libs.androidx.compose.material3.adaptive.layout)
    implementation(libs.androidx.compose.material3.adaptive.navigation)
    implementation(libs.androidx.compose.material3.windowSizeClass)
}