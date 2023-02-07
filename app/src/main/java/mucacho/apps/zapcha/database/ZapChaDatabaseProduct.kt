package mucacho.apps.zapcha.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import mucacho.apps.zapcha.domain.Product

@Entity(tableName = "zapcha_product_table")
data class ZapChaDatabaseProduct(
    @PrimaryKey(autoGenerate = true)
    var productId: Long = 0L,
    @ColumnInfo(name = "product_name")
    var productName: String = "",
    @ColumnInfo(name = "product_description")
    var productDescr: String = "",
    @ColumnInfo(name = "product_price")
    var productPrice: Long = 0,
    @ColumnInfo(name = "product_stock")
    var productStock: Int = 0
)

fun List<ZapChaDatabaseProduct>.asDomainModel(): List<Product> {
    return map {
        Product(
            id = it.productId,
            name = it.productName,
            description = it.productDescr,
            stock = it.productStock,
            price = it.productPrice)
    }
}

fun ZapChaDatabaseProduct.asDomainModel()= Product(
    id = productId,
    name = productName,
    description = productDescr,
    price = productPrice,
    stock = productStock
)