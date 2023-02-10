package mucacho.apps.zapcha.network

import com.google.firebase.database.IgnoreExtraProperties
import mucacho.apps.zapcha.database.ZapChaDatabaseProduct
import mucacho.apps.zapcha.domain.Product

data class ZapchaFirebaseList(val products : List<ZapchaFirebase>){
    fun asDatabaseModel(): Array<ZapChaDatabaseProduct>{
        return products.map {
            ZapChaDatabaseProduct(
                productId = it.id!!,
                productName = it.name!!,
                productDescr = it.description!!,
                productStock = it.stock!!,
                productPrice = it.price!!)
        }.toTypedArray()
    }
    fun asDomainModel(): Array<Product>{
        return products.map {
            Product(
                id = it.id!!,
                name = it.name!!,
                description = it.description!!,
                stock = it.stock!!,
                price = it.price!!)
        }.toTypedArray()
    }
}
@IgnoreExtraProperties
data class ZapchaFirebase(
    val id: Long? = null,
    val name: String? = null,
    val description: String? = null,
    val stock: Int? = null,
    val price: Long? = null) {

    /*@Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "id" to uid,
            "name" to author,
            "description" to title,
            "body" to body,
            "starCount" to starCount,
            "stars" to stars
        )
    }*/
}