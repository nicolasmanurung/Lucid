package di.feature

import data.source.local.remote.LucidApi
import data.source.local.remote.LucidApiClient
import org.koin.dsl.module

val lucidModule = module {
    single<LucidApiClient> { LucidApi(get()) }
}