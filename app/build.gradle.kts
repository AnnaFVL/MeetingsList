plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)

    id("com.google.devtools.ksp")

}

android {
    namespace = "com.example.mymeetings"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.mymeetings"
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
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    val nav_version = "2.7.7"
    val lifecycle_version = "2.7.0"
    val retrofit_version = "2.11.0"
    val gson_version = "2.10.1"
    val material3_version = "1.2.1"
    val coil_version = "2.6.0"
    val room_version = "2.6.1"
    val coroutines_version = "1.7.3"

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)


    // For navigation in Compose
    //noinspection UseTomlInstead
    implementation("androidx.navigation:navigation-fragment-ktx:$nav_version")
    //noinspection UseTomlInstead
    implementation("androidx.navigation:navigation-ui-ktx:$nav_version")
    //noinspection UseTomlInstead
    implementation("androidx.navigation:navigation-compose:$nav_version")

    // For ViewModel in Compose
    //noinspection UseTomlInstead
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")
    //noinspection UseTomlInstead
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycle_version")

    // For Rertofit HTTP Client
    //noinspection UseTomlInstead
    implementation ("com.squareup.retrofit2:retrofit:$retrofit_version")
    //noinspection UseTomlInstead
    implementation ("com.google.code.gson:gson:$gson_version")
    //noinspection UseTomlInstead
    implementation ("com.squareup.retrofit2:converter-gson:$retrofit_version")

    // For date and time picker
    //noinspection UseTomlInstead
    implementation("androidx.compose.material3:material3:$material3_version")

    // For image load and display
    //noinspection UseTomlInstead
    implementation("io.coil-kt:coil-compose:$coil_version")

    // For ROOM
    //noinspection UseTomlInstead
    implementation("androidx.room:room-runtime:$room_version")
    //noinspection UseTomlInstead
    annotationProcessor("androidx.room:room-compiler:$room_version")
    //noinspection UseTomlInstead
    ksp("androidx.room:room-compiler:$room_version")

    //noinspection UseTomlInstead
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutines_version")
    //noinspection UseTomlInstead
    implementation("androidx.room:room-ktx:$room_version")

}