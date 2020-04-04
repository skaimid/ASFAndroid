package tech.saltyfish.asfandroid

import android.os.Build
import android.text.Html
import android.text.Spanned
import android.util.Base64
import android.util.Log
import retrofit2.Call
import tech.saltyfish.asfandroid.network.AsfApi
import tech.saltyfish.asfandroid.network.AsfProperty
import java.util.regex.Pattern
import retrofit2.Callback
import retrofit2.Response


fun String.toSpanned(): Spanned {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        return Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
    } else {
        @Suppress("DEPRECATION")
        return Html.fromHtml(this)
    }
}


fun basicAuthorization(userName: String?, passWord: String?): String {
    return if (userName == "" || userName == null || passWord == "" || passWord == null) {
        ""
    } else "Basic " + Base64.encodeToString(
        ("$userName:$passWord").toByteArray(),
        Base64.NO_WRAP
    )
}


// Test if url is valid.
fun urlTest(url: String?): Boolean {
    val pattern = "(https?|ftp|file)://[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]"
    return if (url != null) {
        Pattern.matches(pattern, url) and (url[url.length - 1] == '/')
    } else false

}

fun connTest(
    url: String?,
    ipcPass: String?,
    basicAuthUsername: String?,
    basicAuthPass: String?
): Boolean {
    if (urlTest(url)) {
        var isSuccess = false
        AsfApi.retrofitService.testApi(
            ipcPass,
            basicAuthorization(basicAuthUsername, basicAuthPass)
        ).enqueue(object : Callback<AsfProperty> {
            override fun onFailure(call: Call<AsfProperty>, t: Throwable) {
                isSuccess = false
            }

            override fun onResponse(call: Call<AsfProperty>, response: Response<AsfProperty>) {
                isSuccess = if (response.body() == null) {
                    false
                } else response.body()!!.success
                Log.d("a",isSuccess.toString())
            }

        })

    }
    return false
}