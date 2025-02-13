package com.siad.stayksa.utils

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.util.LayoutDirection
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.core.text.layoutDirection
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavHostController
import com.siad.stayksa.R
import retrofit2.HttpException
import java.net.HttpURLConnection
import java.text.SimpleDateFormat
import java.util.Base64
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import java.util.concurrent.TimeUnit


// Extension function to convert String date to "X years ago" format
fun String.toTimeAgo(dateFormat: String = "dd-MM-yyyy HH:mm"): String {
    // Define the date format (can be passed as a parameter, default is "yyyy-MM-dd")
    val sdf = SimpleDateFormat(dateFormat, Locale.getDefault())

    // Parse the string into a Date object
    val date: Date = sdf.parse(this) ?: return "Unknown date"

    // Get the current time and the time of the parsed date
    val currentTime = System.currentTimeMillis()
    val timeDiff = currentTime - date.time

    // Convert the time difference into human-readable units
    val years = TimeUnit.MILLISECONDS.toDays(timeDiff) / 365
    val months = TimeUnit.MILLISECONDS.toDays(timeDiff) / 30
    val days = TimeUnit.MILLISECONDS.toDays(timeDiff)
    val hours = TimeUnit.MILLISECONDS.toHours(timeDiff)
    val minutes = TimeUnit.MILLISECONDS.toMinutes(timeDiff)
    val seconds = TimeUnit.MILLISECONDS.toSeconds(timeDiff)

    return when {
        years > 0 -> "$years years ago"
        months > 0 -> "$months months ago"
        days > 0 -> "$days days ago"
        hours > 0 -> "$hours hours ago"
        minutes > 0 -> "$minutes minutes ago"
        seconds > 0 -> "$seconds seconds ago"
        else -> "Just now"
    }
}


fun Long.formattedDateFromMillis(): String {
    val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    date.timeZone = TimeZone.getTimeZone("UTC") // Set this according to your timezone needs
    return date.format(this)
}

fun String?.formatDateMonthsNameAndDays(): String? {
    if (this.isNullOrEmpty()) return null

    val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
    val outputFormat = SimpleDateFormat("MMM d", Locale.ENGLISH) // Desired output format (e.g., "Sep 4")

    return try {
        val date = inputFormat.parse(this) // Parse the string to Date
        outputFormat.format(date) // Format the Date to the desired output
    } catch (e: Exception) {
        null // Return null if parsing fails
    }
}


fun String.convertDateStringToMillis(): Long? {
    return try {
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        formatter.timeZone = TimeZone.getTimeZone("UTC") // Set this according to your timezone needs
        val date = formatter.parse(this)
        date?.time // returns the time in milliseconds
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}


fun String.share(context: Context) {
    val sendIntent = Intent()
    sendIntent.action = Intent.ACTION_SEND
    sendIntent.putExtra(Intent.EXTRA_TEXT, this)
    sendIntent.putExtra(
        Intent.EXTRA_SUBJECT,
        context.getString(R.string.app_name)
    )
    sendIntent.type = "text/plain"
    val shareIntent = Intent.createChooser(sendIntent, null)
    context.startActivity(shareIntent)
}

fun String.copy(context: Context) {
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("label", this)
    clipboard.setPrimaryClip(clip)
}


fun Exception.shouldLogout(): Boolean =
    this is HttpException && this.code() == HttpURLConnection.HTTP_UNAUTHORIZED


fun String.isValidEmail(): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun String.isValidPasswordLength(): Boolean {
    return this.length >= 8
}

// Preventing compose from create two instances when click twice on any item
val NavHostController.canNavigate: Boolean
    get() = currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED


// Mirror the icon based on RTL
@Stable
fun Modifier.mirror(): Modifier {
    return if (Locale.getDefault().layoutDirection == LayoutDirection.LTR) this.scale(scaleX = 1f, scaleY = -1f)
    else this
}


//@RequiresApi(Build.VERSION_CODES.O)
//fun String.fromBase64ToBitmap(): Bitmap {
//    val decodedString: ByteArray =  Base64.getDecoder().decode(this)
//    val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
//    return decodedByte
//}

fun String.cleanBase64(): String {
    return this.split("data:image/png;base64,")[1]
}

fun String.fromBase64ToBitmap(): Bitmap {
    val cleanedBase64String = this.cleanBase64()
    val decodedString: ByteArray = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        // Use the java.util.Base64 for API 26 and above
        Base64.getDecoder().decode(cleanedBase64String)
    } else {
        // Use android.util.Base64 for older versions
        android.util.Base64.decode(cleanedBase64String, android.util.Base64.DEFAULT)
    }
    // Decode the byte array to a bitmap
    return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
}
