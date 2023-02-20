package mucacho.apps.zapcha.domain

import mucacho.apps.zapcha.database.ZapChaDatabaseProduct

data class ProductList(val products : List<Product>) {
    fun asDatabaseModel(): Array<ZapChaDatabaseProduct> {
        return products.map {
            ZapChaDatabaseProduct(
                productId = it.id,
                productName = it.name,
                productDescr = it.description,
                productStock = it.stock,
                productPrice = it.price
            )
        }.toTypedArray()
    }
}
data class Product(val id: Long,
                 var name: String,
                 var description: String,
                 var stock: Int,
                 var price: Long) {

    fun asDatabaseModel()= ZapChaDatabaseProduct(
        productId = id,
        productName = name,
        productDescr = description,
        productPrice = price,
        productStock = stock
    )
}