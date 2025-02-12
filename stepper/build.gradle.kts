import BuildConfiguration.versionCode
import BuildConfiguration.versionName
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("plugin.allopen")
    kotlin("kapt")
}

group = "com.github.acefalobi"

android {
    compileSdk = BuildConfiguration.compileSdkVersion

    defaultConfig {
        minSdk = BuildConfiguration.minSdkVersion
        testOptions.targetSdk  = BuildConfiguration.targetSdkVersion

        versionCode = BuildConfiguration.versionCode
        versionName = BuildConfiguration.versionName

        setProperty("archivesBaseName", "android-stepper-$versionName")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        vectorDrawables.useSupportLibrary = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    tasks.withType<KotlinJvmCompile>().configureEach {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    @Suppress("UnstableApiUsage")
    testOptions {
        unitTests.isIncludeAndroidResources = true
        unitTests.isReturnDefaultValues = true
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    namespace = "com.aceinteract.android.stepper"
}

kotlin {
    explicitApi()
}

dependencies {
    implementation(RootDependencies.kotlin)

    implementation(AndroidXDependencies.appCompat)
    implementation(AndroidXDependencies.constraintLayout)
    implementation(AndroidXDependencies.coreKtx)
    implementation(AndroidXDependencies.navigationFragmentKtx)
    implementation(AndroidXDependencies.navigationUiKtx)

    implementation(UtilityDependencies.androidUtils)

    implementation(ViewDependencies.materialComponent)

    testImplementation(TestingDependencies.androidTest)
    testImplementation(TestingDependencies.jUnit)
    testImplementation(TestingDependencies.mockitoCore)
    testImplementation(TestingDependencies.roboelectric)

    androidTestImplementation(TestingDependencies.androidJUnit)
    androidTestImplementation(TestingDependencies.androidTestRunner)
}
