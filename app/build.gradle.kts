plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.codersarena.revision"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.codersarena.revision"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    buildFeatures{
        dataBinding = true
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    //Firebase Bom
    implementation(platform("com.google.firebase:firebase-bom:32.7.1"))
    //Firebase analytics
    implementation("com.google.firebase:firebase-analytics")
    //Firebase Authentication
    implementation("com.google.firebase:firebase-auth")
    //Firebase Database
    implementation("com.google.firebase:firebase-database")
    //Firebase Storge
    implementation("com.google.firebase:firebase-storage")
    //Firebase Firestore
    implementation("com.google.firebase:firebase-firestore")
    //Glide
    //Glide
    annotationProcessor("com.github.bumptech.glide:compiler:4.14.2")
    implementation("com.github.bumptech.glide:glide:4.16.0")
}