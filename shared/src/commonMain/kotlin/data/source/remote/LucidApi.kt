package data.source.remote

import io.ktor.client.HttpClient

class LucidApi(private val apiClient: HttpClient) : LucidApiClient {
}