package data.source

import AppSettings
import com.rickclephas.kmp.nativecoroutines.NativeCoroutineScope
import data.source.remote.LucidApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class LucidRepository : KoinComponent {
    private val lucidApi: LucidApi by inject()
    private val appSettings: AppSettings by inject()

    @NativeCoroutineScope
    val coroutineScope = CoroutineScope(Dispatchers.Default)


}