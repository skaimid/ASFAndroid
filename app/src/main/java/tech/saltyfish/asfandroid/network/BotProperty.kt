package tech.saltyfish.asfandroid.network

import com.squareup.moshi.JsonClass

import com.squareup.moshi.Json

@JsonClass(generateAdapter = true)
data class BotProperty(
    @Json(name = "Message")
    val message: String = "",
    @Json(name = "Result")
    val result: Map<String, Bot>,
    @Json(name = "Success")
    val success: Boolean = false
)

@JsonClass(generateAdapter = true)
data class Bot(
    @Json(name = "AccountFlags")
    val accountFlags: Int = 0,
    @Json(name = "AvatarHash")
    val avatarHash: String = "",
    @Json(name = "BotConfig")
    val botConfig: BotConfig = BotConfig(),
    @Json(name = "BotName")
    val botName: String = "",
    @Json(name = "CardsFarmer")
    val cardsFarmer: CardsFarmer = CardsFarmer(),
    @Json(name = "GamesToRedeemInBackgroundCount")
    val gamesToRedeemInBackgroundCount: Int = 0,
    @Json(name = "IsConnectedAndLoggedOn")
    val isConnectedAndLoggedOn: Boolean = false,
    @Json(name = "IsPlayingPossible")
    val isPlayingPossible: Boolean = false,
    @Json(name = "KeepRunning")
    val keepRunning: Boolean = false,
    @Json(name = "Nickname")
    val nickname: String = "",
    @Json(name = "s_SteamID")
    val sSteamID: String = "",
    @Json(name = "SteamID")
    val steamID: Long = 0,
    @Json(name = "WalletBalance")
    val walletBalance: Int = 0,
    @Json(name = "WalletCurrency")
    val walletCurrency: Int = 0
)

@JsonClass(generateAdapter = true)
data class BotConfig(
    @Json(name = "AcceptGifts")
    val acceptGifts: Boolean = false,
    @Json(name = "AutoSteamSaleEvent")
    val autoSteamSaleEvent: Boolean = false,
    @Json(name = "BotBehaviour")
    val botBehaviour: Int = 0,
    @Json(name = "CustomGamePlayedWhileFarming")
    val customGamePlayedWhileFarming: String = "",
    @Json(name = "CustomGamePlayedWhileIdle")
    val customGamePlayedWhileIdle: String = "",
    @Json(name = "Enabled")
    val enabled: Boolean = false,
    @Json(name = "FarmingOrders")
    val farmingOrders: List<Int> = listOf(),
    @Json(name = "GamesPlayedWhileIdle")
    val gamesPlayedWhileIdle: List<Int> = listOf(),
    @Json(name = "HoursUntilCardDrops")
    val hoursUntilCardDrops: Int = 0,
    @Json(name = "IdlePriorityQueueOnly")
    val idlePriorityQueueOnly: Boolean = false,
    @Json(name = "IdleRefundableGames")
    val idleRefundableGames: Boolean = false,
    @Json(name = "LootableTypes")
    val lootableTypes: List<Int> = listOf(),
    @Json(name = "MatchableTypes")
    val matchableTypes: List<Int> = listOf(),
    @Json(name = "OnlineStatus")
    val onlineStatus: Int = 0,
    @Json(name = "PasswordFormat")
    val passwordFormat: Int = 0,
    @Json(name = "Paused")
    val paused: Boolean = false,
    @Json(name = "RedeemingPreferences")
    val redeemingPreferences: Int = 0,
    @Json(name = "s_SteamMasterClanID")
    val sSteamMasterClanID: String = "",
    @Json(name = "SendOnFarmingFinished")
    val sendOnFarmingFinished: Boolean = false,
    @Json(name = "SendTradePeriod")
    val sendTradePeriod: Int = 0,
    @Json(name = "ShutdownOnFarmingFinished")
    val shutdownOnFarmingFinished: Boolean = false,
    @Json(name = "SteamLogin")
    val steamLogin: String = "",
    @Json(name = "SteamMasterClanID")
    val steamMasterClanID: Int = 0,
    @Json(name = "SteamParentalCode")
    val steamParentalCode: String = "",
    @Json(name = "SteamPassword")
    val steamPassword: String = "",
    @Json(name = "SteamTradeToken")
    val steamTradeToken: String = "",
    @Json(name = "SteamUserPermissions")
    val steamUserPermissions: SteamUserPermissions = SteamUserPermissions(),
    @Json(name = "TradingPreferences")
    val tradingPreferences: Int = 0,
    @Json(name = "TransferableTypes")
    val transferableTypes: List<Int> = listOf(),
    @Json(name = "UseLoginKeys")
    val useLoginKeys: Boolean = false
)

@JsonClass(generateAdapter = true)
data class SteamUserPermissions(
    @Json(name = "additionalProp1")
    val additionalProp1: Int = 0,
    @Json(name = "additionalProp2")
    val additionalProp2: Int = 0,
    @Json(name = "additionalProp3")
    val additionalProp3: Int = 0
)

@JsonClass(generateAdapter = true)
data class CardsFarmer(
    @Json(name = "CurrentGamesFarming")
    val currentGamesFarming: List<CurrentGamesFarming> = listOf(),
    @Json(name = "GamesToFarm")
    val gamesToFarm: List<GamesToFarm> = listOf(),
    @Json(name = "Paused")
    val paused: Boolean = false,
    @Json(name = "TimeRemaining")
    val timeRemaining: String = ""
)

@JsonClass(generateAdapter = true)
data class CurrentGamesFarming(
    @Json(name = "AppID")
    val appID: Int = 0,
    @Json(name = "CardsRemaining")
    val cardsRemaining: Int = 0,
    @Json(name = "GameName")
    val gameName: String = "",
    @Json(name = "HoursPlayed")
    val hoursPlayed: Double = 0.0
)

@JsonClass(generateAdapter = true)
data class GamesToFarm(
    @Json(name = "AppID")
    val appID: Int = 0,
    @Json(name = "CardsRemaining")
    val cardsRemaining: Int = 0,
    @Json(name = "GameName")
    val gameName: String = "",
    @Json(name = "HoursPlayed")
    val hoursPlayed: Double = 0.0
)