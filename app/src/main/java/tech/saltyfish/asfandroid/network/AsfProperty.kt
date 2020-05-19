package tech.saltyfish.asfandroid.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class AsfProperty(
    @Json(name = "Message")
    val message: String = "",
    @Json(name = "Result")
    val result: Result = Result(),
    @Json(name = "Success")
    val success: Boolean = false
)

@JsonClass(generateAdapter = true)
data class Result(
    @Json(name = "BuildVariant")
    val buildVariant: String = "",
    @Json(name = "GlobalConfig")
    val globalConfig: GlobalConfig = GlobalConfig(),
    @Json(name = "MemoryUsage")
    val memoryUsage: Int = 0,
    @Json(name = "ProcessStartTime")
    val processStartTime: String = "",
    @Json(name = "Version")
    val version: String = ""
)

@JsonClass(generateAdapter = true)
data class GlobalConfig(
    @Json(name = "AutoRestart")
    val autoRestart: Boolean = false,
    @Json(name = "Blacklist")
    val blacklist: List<Int> = listOf(),
    @Json(name = "CommandPrefix")
    val commandPrefix: String = "",
    @Json(name = "ConfirmationsLimiterDelay")
    val confirmationsLimiterDelay: Int = 0,
    @Json(name = "ConnectionTimeout")
    val connectionTimeout: Int = 0,
    @Json(name = "CurrentCulture")
    val currentCulture: String = "",
    @Json(name = "Debug")
    val debug: Boolean = false,
    @Json(name = "FarmingDelay")
    val farmingDelay: Int = 0,
    @Json(name = "GiftsLimiterDelay")
    val giftsLimiterDelay: Int = 0,
    @Json(name = "Headless")
    val headless: Boolean = false,
    @Json(name = "IPC")
    val iPC: Boolean = false,
    @Json(name = "IPCPassword")
    val iPCPassword: String = "",
    @Json(name = "IdleFarmingPeriod")
    val idleFarmingPeriod: Int = 0,
    @Json(name = "InventoryLimiterDelay")
    val inventoryLimiterDelay: Int = 0,
    @Json(name = "LoginLimiterDelay")
    val loginLimiterDelay: Int = 0,
    @Json(name = "MaxFarmingTime")
    val maxFarmingTime: Int = 0,
    @Json(name = "MaxTradeHoldDuration")
    val maxTradeHoldDuration: Int = 0,
    @Json(name = "OptimizationMode")
    val optimizationMode: Int = 0,
    @Json(name = "s_SteamOwnerID")
    val sSteamOwnerID: String = "",
    @Json(name = "Statistics")
    val statistics: Boolean = false,
    @Json(name = "SteamMessagePrefix")
    val steamMessagePrefix: String = "",
    @Json(name = "SteamOwnerID")
    val steamOwnerID: Long = 0,
    @Json(name = "SteamProtocols")
    val steamProtocols: Int = 0,
    @Json(name = "UpdateChannel")
    val updateChannel: Int = 0,
    @Json(name = "UpdatePeriod")
    val updatePeriod: Int = 0,
    @Json(name = "WebLimiterDelay")
    val webLimiterDelay: Int = 0,
    @Json(name = "WebProxy")
    val webProxy: String = "",
    @Json(name = "WebProxyPassword")
    val webProxyPassword: String = "",
    @Json(name = "WebProxyUsername")
    val webProxyUsername: String = ""
)