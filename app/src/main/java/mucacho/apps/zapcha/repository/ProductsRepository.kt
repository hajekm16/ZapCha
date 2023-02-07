package mucacho.apps.zapcha.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import mucacho.apps.zapcha.database.ZapChaDatabaseProduct
import mucacho.apps.zapcha.database.ZapchaDatabase
import mucacho.apps.zapcha.database.asDomainModel
import mucacho.apps.zapcha.domain.Product

class ProductsRepository(private val database: ZapchaDatabase) {

    val products: LiveData<List<Product>> = Transformations.map(database.zapchaDatabaseDao.getAllProducts()) {
        it.asDomainModel()
    }

    suspend fun saveProduct(product: Product){
        withContext(Dispatchers.IO) {
            database.zapchaDatabaseDao.update(product.asDatabaseModel())
        }
    }

    suspend fun newProduct(product: Product){
        withContext(Dispatchers.IO) {
            database.zapchaDatabaseDao.insert(product.asDatabaseModel())
        }
    }

    suspend fun deleteSelectedProduct(product: Product){
        withContext(Dispatchers.IO) {
            database.zapchaDatabaseDao.delete(product.asDatabaseModel())
        }
    }

    suspend fun deleteAllProducts(){
        withContext(Dispatchers.IO) {
            database.zapchaDatabaseDao.clear()
        }
    }

    suspend fun getSelectedProduct(Id: Long): Product {
        return withContext(Dispatchers.IO) {
            val product = database.zapchaDatabaseDao.get(Id).asDomainModel()
            product
        }
    }

    suspend fun refreshProducts(){

    }
}