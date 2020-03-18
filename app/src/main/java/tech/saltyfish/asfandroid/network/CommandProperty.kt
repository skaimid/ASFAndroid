package tech.saltyfish.asfandroid.network
import com.squareup.moshi.JsonClass

import com.squareup.moshi.Json


@JsonClass(generateAdapter = true)
data class CommandPostProperty(
    @Json(name = "Command")
    val command: String = ""
)


@JsonClass(generateAdapter = true)
data class CommandProperty(
    @Json(name = "Message")
    val message: String = "",
    @Json(name = "Result")
    val result: String = "",
    @Json(name = "Success")
    val success: Boolean = false
)