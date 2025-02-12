import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(kotlin("gradle-plugin", RootDependencies.Versions.kotlin))
        classpath(ClasspathDependencies.kotlinExtensions)
        classpath(ClasspathDependencies.kotlinGradle)
        classpath(ClasspathDependencies.allopen)
        classpath(ClasspathDependencies.androidMaven)
        classpath(ClasspathDependencies.gradle)
        classpath(ClasspathDependencies.hilt)
        classpath(ClasspathDependencies.navigation)
        classpath(ClasspathDependencies.spotless)
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }

    plugins.apply(PluginDependencies.SPOTLESS)

    tasks.withType<KotlinJvmCompile>().configureEach {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }
}
