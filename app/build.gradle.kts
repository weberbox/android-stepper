import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("plugin.allopen")
    kotlin("kapt")
    id(PluginDependencies.NAVIGATION)
}

android {
    compileSdk = BuildConfiguration.compileSdkVersion

    defaultConfig {
        applicationId = BuildConfiguration.applicationId
        minSdk = BuildConfiguration.minSdkVersion
        targetSdk = BuildConfiguration.targetSdkVersion

        versionCode = BuildConfiguration.versionCode
        versionName = BuildConfiguration.versionName

        vectorDrawables.useSupportLibrary = true

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        setProperty("archivesBaseName", "$applicationId-app-$versionName")
    }

    buildFeatures {
        viewBinding = true
        dataBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
        android.buildFeatures.buildConfig = true
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
    namespace = "com.aceinteract.android.stepper"
}

dependencies {
    implementation(RootDependencies.kotlin)

    implementation(project(ProjectDependencies.stepper))

    implementation(AndroidXDependencies.appCompat)
    implementation(AndroidXDependencies.constraintLayout)
    implementation(AndroidXDependencies.coreKtx)
    implementation(AndroidXDependencies.navigationFragmentKtx)
    implementation(AndroidXDependencies.navigationUiKtx)

    implementation(ViewDependencies.materialComponent)

    implementation(AsyncDependencies.coroutines)
    implementation(AsyncDependencies.coroutinesAndroid)

    implementation(UtilityDependencies.androidUtils)

    testImplementation(TestingDependencies.jUnit)
    testImplementation(TestingDependencies.mockitoCore)
    testImplementation(TestingDependencies.roboelectric)
    androidTestImplementation(TestingDependencies.androidJUnit)
    testImplementation(TestingDependencies.androidTest)
    androidTestImplementation(TestingDependencies.androidTestRunner)
}
