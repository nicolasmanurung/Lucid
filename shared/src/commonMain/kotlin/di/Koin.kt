package di

import di.feature.apiModule
import di.feature.lucidModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration


/**
 * @author Nicolas Manurung (nicolas.manurung@dana.id)
 * @version Koin, v 0.1 25/11/23 22.48 by Nicolas Manurung
 */
fun initKoin(appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(
            apiModule,
            lucidModule,
        )
    }