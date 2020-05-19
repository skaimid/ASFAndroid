package tech.saltyfish.asfandroid

import android.os.Build
import android.text.Html
import android.text.Spanned
import android.util.Base64
import java.util.regex.Pattern


/**
 *  for better text view style
 *  example is About fragment
 */
fun String.toSpanned(): Spanned {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        return Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
    } else {
        @Suppress("DEPRECATION")
        return Html.fromHtml(this)
    }
}

/**
 * @param userName basic authorization user name
 * @param password basic authorization password
 *
 * @return basic authorization header
 */
/*
 * why using this? because there is a need!
 * see https://en.wikipedia.org/wiki/Basic_access_authentication
 * and https://github.com/JustArchiNET/ArchiSteamFarm/wiki/IPC-zh-CN
 */
fun basicAuthorization(userName: String?, password: String?): String {
    return if (userName == "" || userName == null || password == "" || password == null) {
        ""
    } else "Basic " + Base64.encodeToString(
        ("$userName:$password").toByteArray(),
        Base64.NO_WRAP
    )
}


/**
 * @param url the url that need to be tested
 * must be start with "http" or "https", end with  "/"
 *
 * @return if url is valid or can be correctly processed by Retorfit
 *
 * Test if url is valid.
 */
fun urlTest(url: String?): Boolean {
    val pattern = "(https?)://[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]"
    return if (url != null && url != "") {
        Pattern.matches(pattern, url) and (url[url.length - 1] == '/')
    } else false
}

/** for display a list to csv style string
 * @param l the input list
 * @return csv style string output
 */
fun listToCsv(l: List<Int>): String {
    if (l.isEmpty())
        return ""
    val sb: StringBuilder = StringBuilder()
    l.forEach {
        sb.append(it)
        sb.append(',')
    }
    return sb.deleteCharAt(sb.lastIndex).toString()
}

/**
 * @param s csv style string
 * @return List <Int> which come from s
 *
 *  DFA algorithms
 */

fun csvToList(s: String): List<Int> {
    val il = mutableListOf<Int>()
    var i: Int = 0
    val ns = "$s,"

    // a very simple DFA algorithms
    ns.forEach { c: Char ->
        if (c == ',') {
            il.add(i)
            i = 0
        } else {
            if (c.toInt() > '0'.toInt() && c.toInt() < '9'.toInt())
                i = i * 10 + c.toInt() - '0'.toInt()
        }
    }
    return il
}


