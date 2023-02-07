package mucacho.apps.zapcha.domain

import androidx.lifecycle.Transformations.map
import mucacho.apps.zapcha.database.ZapChaDatabaseProduct

data class Product(val id: Long,
                 val name: String,
                 val description: String,
                 val stock: Int,
                 val price: Long) {

    fun asDatabaseModel()= ZapChaDatabaseProduct(
        productId = id,
        productName = name,
        productDescr = description,
        productPrice = price,
        productStock = stock
    )
}