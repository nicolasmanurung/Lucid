package di.feature

import data.source.remote.LucidApi
import data.source.remote.LucidApiClient
import org.koin.dsl.module

val lucidModule = module {
    single<LucidApiClient> { LucidApi(get()) }
}