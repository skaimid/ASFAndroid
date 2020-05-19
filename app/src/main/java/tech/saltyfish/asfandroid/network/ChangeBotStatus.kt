package tech.saltyfish.asfandroid.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

// General command output
@JsonClass(generateAdapter = true)
data class ExeResult(
    @Json(name = "Message")
    val message: String = "",
    @Json(name = "Success")
    val success: Boolean = false
)

// Multi command output
@JsonClass(generateAdapter = true)
data class MultiExeResult(
    @Json(name = "Result")
    val result: Map<String, Boolean>,
    @Json(name = "Message")
    val message: String = "",
    @Json(name = "Success")
    val success: Boolean = false
)

// General command input
@JsonClass(generateAdapter = true)
data class CommandInfo(
    @Json(name = "Permanent")
    val permanent: Boolean = false,
    @Json(name = "ResumeInSeconds")
    val resumeInSeconds: Int = 0
)


