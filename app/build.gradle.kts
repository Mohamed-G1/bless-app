plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.nat.couriersapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.nat.couriersapp"
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
            isMinifyEnabled = false
            buildConfigField("String", "BASE_URL", "\"http://mecsmobileapi.natlab.net/\"")
//            buildConfigField(
//                "String",
//                "CLIENT_ID",
//                "\"235851507231-9paiu2lqebsuh2a4ureo1imv4gle50dp.apps.googleusercontent.com\""
//            )

        }
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "BASE_URL", "\"http://mecsmobileapi.natlab.net/\"")
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

}