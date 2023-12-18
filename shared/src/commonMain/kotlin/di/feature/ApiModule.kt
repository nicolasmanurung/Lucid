package di.feature

import data.source.local.remote.LucidApi
import data.source.local.remote.response.ApiException
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.headers
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpHeaders
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val apiModule = module {
    single { createJson() }
    single { createHttpClient(get(), get()) }
    singleOf(::LucidApi)
}

fun createJson() = Json {
    isLenient = true
    ignoreUnknownKeys = true
    useAlternativeNames = false
}

fun createHttpClient(httpClientEngine: HttpClientEngine, json: Json) =
    HttpClient(httpClientEngine) {
        install(ContentNegotiation) {
            json(json)
        }
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL
        }

        install(HttpTimeout) {
            this.requestTimeoutMillis = 60000
            this.connectTimeoutMillis = 60000
            this.socketTimeoutMillis = 60000
        }

        defaultRequest {
            url {
                protocol = URLProtocol.HTTPS
                host = "ap-southeast-1.aws.data.mongodb-api.com"

                headers {
                    append(HttpHeaders.Accept, "application/vnd.api+json")
                    append(HttpHeaders.ContentType, "application/vnd.api+json")
                }
            }
        }

        HttpResponseValidator {
            handleResponseExceptionWithRequest { exception, _ ->
                when (exception) {
                    is ServerResponseException -> {
                        val serverResponseResponse = exception.response
                        val serverResponseExceptionText = serverResponseResponse.bodyAsText()
                        val apiException = json.decodeFromString(
                            ApiException.serializer(),
                            serverResponseExceptionText
                        )
                        throw apiException
                    }

                    is ClientRequestException -> {
                        val exceptionResponse = exception.response
                        val exceptionResponseText = exceptionResponse.bodyAsText()
                        val apiException =
                            json.decodeFromString(ApiException.serializer(), exceptionResponseText)
                        throw apiException
                    }

                    else -> {
                        return@handleResponseExceptionWithRequest
                    }
                }
            }
        }
    }
