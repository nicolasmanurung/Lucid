package data.source.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiException(
    @SerialName("error")
    val error: String,
    @SerialName("error_code")
    val errorCode: String,
    @SerialName("link")
    val link: String
) : Exception()