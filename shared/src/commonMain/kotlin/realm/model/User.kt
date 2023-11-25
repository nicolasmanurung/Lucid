package realm.model

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey


/**
 * @author Nicolas Manurung (nicolas.manurung@dana.id)
 * @version User, v 0.1 25/11/23 20.32 by Nicolas Manurung
 */
class User : RealmObject {
    @PrimaryKey
    var _id: String = ""
    var nickname: String = ""
    var fullname: String = ""
}