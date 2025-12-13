plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.nat.greco"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.nat.greco"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

    }

    buildFeatures.buildConfig = true

    buildTypes {
        debug {
//            https://adelsita1-greko-eg-main-25915325.dev.odoo.com/odoo/users?debug=1
            isMinifyEnabled = false
            buildConfigField("String", "BASE_URL", "\"https://adelsita1-greko-eg-main-25915325.dev.odoo.com/\"")
//            buildConfigField(
//                "String",
//                "CLIENT_ID",
//                "\"235851507231-9paiu2lqebsuh2a4ureo1imv4gle50dp.apps.googleusercontent.com\""
//            )
//            https://elwarda-test.odoo.com/
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "BASE_URL", "\" https://elwarda-test.odoo.com/\"")
//            buildConfigField(
//                "String",
//                "CLIENT_ID",
//                "\"235851507231-9paiu2lqebsuh2a4ureo1imv4gle50dp.apps.googleusercontent.com\""
//            )
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

    //Retrofit
    implementation(libs.squareup.retrofit)
    implementation(libs.squareup.retrofit.gson.converter)
    implementation(libs.squareup.logger)

    //Koin
    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.koin.compose)

    //Navigation
    implementation(libs.androidx.navigation)
    implementation(libs.kotlinx.serialization.json)

    //Facebook
    implementation(libs.facebook.login)

    //Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.messaging.ktx)
    implementation(libs.firebase.auth)

    //Maps
    implementation(libs.maps.compose)


    //Credential Manager
    implementation (libs.androidx.credentials)
    implementation(libs.googleid)
    implementation( libs.androidx.credentials.play.services.auth)

    //DataStore
    implementation(libs.androidx.datastore)

    //Coil
    implementation(libs.coil)
    implementation(libs.coil.gif)
    implementation(libs.coil.compose)

    //Location
    implementation(libs.play.services.location)


    implementation("androidx.camera:camera-camera2:1.5.0-alpha06")
    implementation("androidx.camera:camera-lifecycle:1.5.0-alpha06")
    implementation("androidx.camera:camera-view:1.5.0-alpha06")
    implementation("com.google.zxing:core:3.3.3")
    implementation ("com.google.mlkit:barcode-scanning:17.1.0")

    implementation("com.journeyapps:zxing-android-embedded:4.3.0")

    implementation  ("com.google.firebase:firebase-bom:32.7.1")
    implementation ("com.google.firebase:firebase-messaging")


}

