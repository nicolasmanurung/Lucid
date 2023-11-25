package realm

import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.internal.platform.runBlocking
import io.realm.kotlin.log.LogLevel
import io.realm.kotlin.mongodb.App
import io.realm.kotlin.mongodb.AppConfiguration
import io.realm.kotlin.mongodb.Credentials
import io.realm.kotlin.mongodb.sync.SyncConfiguration
import realm.model.User


/**
 * @author Nicolas Manurung (nicolas.manurung@dana.id)
 * @version RealmRepository, v 0.1 25/11/23 19.25 by Nicolas Manurung
 */
class RealmRepository {
    lateinit var realm: Realm
    private val appServiceInstance by lazy {
        // If logs are on app level then it set for everything ..
        val configuration = AppConfiguration.Builder("devicesync-bloka")
            .log(LogLevel.ALL)
            .build()
        App.Companion.create(configuration)
    }

    init {
        setupRealmSync()
    }

    private fun setupRealmSync() {
        val user = runBlocking { appServiceInstance.login(Credentials.anonymous()) }
        val configuration = SyncConfiguration
            .Builder(user, setOf(User::class))
            .initialSubscriptions { realm ->
                // only can write data, which cover in initialSubscriptions
                add(
                    query = realm.query<User>(),
                    name = "",
                    updateExisting = true
                )
            }.build()
        realm = Realm.open(configuration)
    }
}