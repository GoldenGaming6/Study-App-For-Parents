plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}
android {
    namespace = "in.padhai.saathi"
    compileSdk = 35
    defaultConfig {
        applicationId = "in.padhai.saathi"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
    }
    dependencies {
        implementation("androidx.biometric:biometric:1.1.0")
        implementation("androidx.core:core-ktx:1.15.0")
    }
}
