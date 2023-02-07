package mucacho.apps.zapcha.store

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import mucacho.apps.zapcha.database.ZapChaDatabaseProduct
import mucacho.apps.zapcha.database.ZapchaDatabase
import mucacho.apps.zapcha.database.ZapchaDatabaseDao
import mucacho.apps.zapcha.domain.Product
import mucacho.apps.zapcha.repository.ProductsRepository

//viewmodel for list of products

class StoreViewModel(val database: ZapchaDatabase,
                     application: Application
) : AndroidViewModel(application) {

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val productsRepository = ProductsRepository(database)

    init {
//        TODO refresh data from firebase
        uiScope.launch {
            productsRepository.refreshProducts()
        }
    }

    val products = productsRepository.products

    private val _navigateToProductDetail = MutableLiveData<Long?>()
    val navigateToProductDetail
        get() = _navigateToProductDetail

    fun onZapchaProductClicked(id: Long) {
        _navigateToProductDetail.value = id
    }

    fun onZapchaProductNavigated() {
        _navigateToProductDetail.value = null
    }

    fun onNewProduct() {
        uiScope.launch {
            productsRepository.newProduct(Product(0, "", "", 0, 0))
        }
    }

    fun onDeleteAllProducts() {
        uiScope.launch {
            productsRepository.deleteAllProducts()
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}