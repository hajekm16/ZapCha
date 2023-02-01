package mucacho.apps.zapcha.store

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import mucacho.apps.zapcha.database.ZapChaDatabaseProduct
import mucacho.apps.zapcha.database.ZapchaDatabaseDao

//viewmodel for list of products

class StoreViewModel(val database: ZapchaDatabaseDao,
                     application: Application
) : AndroidViewModel(application) {

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private var selectedProduct = MutableLiveData<ZapChaDatabaseProduct?>()

    val products = database.getAllProducts()

    private val _navigateToProduct = MutableLiveData<ZapChaDatabaseProduct?>()
    val navigateToProduct: LiveData<ZapChaDatabaseProduct?>
        get() = _navigateToProduct

    fun doneNavigating() {
        _navigateToProduct.value = null
    }

    private suspend fun getCurrentProductFromDatabase(Id: Long): ZapChaDatabaseProduct {
        return withContext(Dispatchers.IO) {
            val product = database.get(Id)
            product
        }
    }

    private suspend fun insert(product: ZapChaDatabaseProduct): ZapChaDatabaseProduct {
        return withContext(Dispatchers.IO){
            database.insert(product)
            val productout = database.get(product.productId)
            productout
        }
    }

    fun onSelectProduct(productId : Long) {
//        TODO opravit otevreni detailu pri zalozeni noveho produktu
        uiScope.launch {
            selectedProduct.value = getCurrentProductFromDatabase(productId)
            if (selectedProduct.value == null) {
                selectedProduct.value = insert(ZapChaDatabaseProduct(0,"","",0,0))
                }
            }
        _navigateToProductDetail.value = selectedProduct.value?.productId
    }

    private val _navigateToProductDetail = MutableLiveData<Long?>()
    val navigateToProductDetail
    get() = _navigateToProductDetail

    fun onZapchaProductClicked(id: Long) {
        _navigateToProductDetail.value = id
    }

    fun onZapchaProductNavigated() {
        _navigateToProductDetail.value = null
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun onClear() {
        uiScope.launch {
            clear()
            selectedProduct.value = null
        }
    }

    private suspend fun clear() {
        withContext(Dispatchers.IO) {
            database.clear()
        }
    }
}