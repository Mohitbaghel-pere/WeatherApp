plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.hiltAndroid)
//    kotlin("kapt")

}

android {
    namespace = "com.system.weatherapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.system.weatherapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        buildConfigField ("String", "PASS_CODE", "\"${"passCode"}\"")
        buildConfigField ("String", "OPEN_WEATHER_API_KEY", "\"${"48a68b91f23e976a9078e42ad0cea974"}\"")
        buildConfigField("String", "BASE_URL", "\"https://api.openweathermap.org/data/2.5/\"")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        buildConfig = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.hilt.common)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.3.1")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1")

    val hilt_version = "2.51.1"
    // Hilt
    implementation ("com.google.dagger:hilt-android:$hilt_version")
    kapt ("com.google.dagger:hilt-android-compiler:$hilt_version")

    // Retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.9.2")

    // Room
    val room_version = "2.6.1"
    implementation ("androidx.room:room-runtime:$room_version")
    kapt ("androidx.room:room-compiler:$room_version")
    implementation("androidx.room:room-ktx:$room_version")

    // Coroutines
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.1")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.1")

    // Glide for image loading
    implementation ("com.github.bumptech.glide:glide:4.12.0")
    kapt ("com.github.bumptech.glide:compiler:4.12.0")

    // Location
    implementation ("com.google.android.gms:play-services-location:18.0.0")

    // SQLCipher for Android
//    implementation ("net.zetetic:android-database-sqlcipher:5.0.0")

    implementation ("androidx.security:security-crypto:1.1.0-alpha03")

    val mockitoVersion = "4.8.0"
    val mockitoKotlinVersion = "4.1.0"
    val mockkVersion = "1.13.5"
    val coroutines_version = "1.6.4"

    // Optional -- Mockito framework
    testImplementation ("org.mockito:mockito-core:$mockitoVersion")
    // Optional -- mockito-kotlin
    testImplementation ("org.mockito.kotlin:mockito-kotlin:$mockitoKotlinVersion")
    // Optional -- Mockk framework
    testImplementation ("io.mockk:mockk:$mockkVersion")

    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutines_version")



}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}