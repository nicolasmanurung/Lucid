package data.source.local.remote

import io.ktor.client.HttpClient
import io.ktor.client.request.get

class LucidApi(private val apiClient: HttpClient) : LucidApiClient {
}