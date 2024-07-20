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
    implementation(libs.androidx.fragment.testing)
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

//    // Glide for image loading
//    implementation ("com.github.bumptech.glide:glide:4.12.0")
//    kapt ("com.github.bumptech.glide:compiler:4.12.0")

    // Location
    implementation ("com.google.android.gms:play-services-location:18.0.0")

    // glide
    implementation ("com.github.bumptech.glide:glide:4.15.1")
    kapt ("com.github.bumptech.glide:compiler:4.15.1")

    // SQLCipher for Android
//    implementation ("net.zetetic:android-database-sqlcipher:5.0.0")
//
    implementation ("androidx.security:security-crypto:1.1.0-alpha03")


    // Local unit tests
    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.5.1")
    testImplementation ("com.google.truth:truth:1.1.3")
    testImplementation ("com.squareup.okhttp3:mockwebserver:4.9.1")
    testImplementation ("org.amshove.kluent:kluent-android:1.73")


    //Testing
    androidTestImplementation ("androidx.arch.core:core-testing:2.1.0")

    androidTestImplementation ("androidx.test:runner:1.4.0")

    androidTestImplementation ("androidx.test:rules:1.4.0")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.4.0")

    testImplementation ("junit:junit:4.+")
    testImplementation ("io.mockk:mockk:1.12.2")
    testImplementation ("org.mockito:mockito-core:3.12.4")
    androidTestImplementation ("io.mockk:mockk-android:1.12.2")

    androidTestImplementation("org.mockito:mockito-android:3.12.4") {
        exclude(group = "net.bytebuddy", module = "byte-buddy")
    }

    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.1")
    testImplementation ("androidx.arch.core:core-testing:2.1.0")

    testImplementation ("org.robolectric:robolectric:4.6.1")
    testImplementation ("androidx.test:core:1.4.0")
    testImplementation ("androidx.test.ext:junit:1.1.3")

    androidTestImplementation ("com.google.dagger:hilt-android-testing:2.44")
    kaptAndroidTest ("com.google.dagger:hilt-android-compiler:2.44")
    testImplementation ("com.google.dagger:hilt-android-testing:2.44")
    kaptTest ("com.google.dagger:hilt-android-compiler:2.44")

//    implementation ("net.zetetic:sqlcipher-android:4.5.4@aar")
//    implementation ("androidx.sqlite:sqlite:2.4.0")



}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}