package tech.saltyfish.asfandroid.network
import com.squareup.moshi.JsonClass

import com.squareup.moshi.Json


@JsonClass(generateAdapter = true)
data class ExeResult(
    @Json(name = "Message")
    val message: String = "",
    @Json(name = "Success")
    val success: Boolean = false
)
@JsonClass(generateAdapter = true)
data class CommandInfo(
    @Json(name = "Permanent")
    val permanent: Boolean = false,
    @Json(name = "ResumeInSeconds")
    val resumeInSeconds: Int = 0
)
