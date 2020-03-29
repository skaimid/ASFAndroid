package tech.saltyfish.asfandroid

import android.os.Build
import android.text.Html
import android.text.Spanned
import androidx.annotation.RequiresApi
import android.util.Base64

fun String.toSpanned(): Spanned {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        return Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
    } else {
        @Suppress("DEPRECATION")
        return Html.fromHtml(this)
    }
}


fun basicAuthorization(userName: String, passWord: String): String {
    return "Basic " + Base64.encodeToString(
        ("$userName:$passWord").toByteArray(),
        Base64.NO_WRAP
    )
}
