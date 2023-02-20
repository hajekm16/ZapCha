package mucacho.apps.zapcha.product

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import mucacho.apps.zapcha.database.ZapchaDatabase
import mucacho.apps.zapcha.domain.Product
import mucacho.apps.zapcha.network.Service
import mucacho.apps.zapcha.repository.ProductsRepository

// view model for single product
class ProductViewModel(
    val productId: Long,
    val database: ZapchaDatabase,
    application: Application
) : AndroidViewModel(application) {

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    var selectedProduct = MutableLiveData<Product?>()

    var productEdited by mutableStateOf(false)

    private val productsRepository = ProductsRepository(database)

    private val _navigateToStore = MutableLiveData<Boolean?>()

    private val service = Service()

    val navigateToStore : LiveData<Boolean?>
        get() = _navigateToStore

    fun doneNavigating() {
        _navigateToStore.value = null
    }

    private var _showSnackbarEvent = MutableLiveData<Boolean>()

    val showSnackBarEvent: LiveData<Boolean>
        get() = _showSnackbarEvent

    fun doneShowingSnackbar(){
        _showSnackbarEvent.value = false
    }

    init{
//        one time setting
        initializeSelectedProduct(productId)
        service.initializeDbRef()
    }

    private fun initializeSelectedProduct(Id : Long) {
        uiScope.launch {
            selectedProduct.value = productsRepository.getSelectedProduct(Id)
        }
    }

    fun onSaveProduct(){
        if (service.writeProduct(
                productId,
                selectedProduct.value?.name ?: "",
                selectedProduct.value?.description ?: "",
                selectedProduct.value?.stock ?: 0,
                selectedProduct.value?.price ?: 0
            )) {
            uiScope.launch {
                productsRepository.saveProduct(Product(productId,
                    selectedProduct.value!!.name,
                    selectedProduct.value!!.description,
                    selectedProduct.value!!.stock,
                    selectedProduct.value!!.price))
            }
            _navigateToStore.value = true
        }
    }

    fun onDeleteProduct(){
        if (service.deleteProduct(productId)) {
            uiScope.launch {
                val oldProduct = selectedProduct.value ?: return@launch
                productsRepository.deleteSelectedProduct(oldProduct)
                _navigateToStore.value = true
            }
        }
    }

    fun newStockQty(newQty: String){
        if ((newQty != "null") && (newQty != "")) {
            productEdited = true
            selectedProduct.value!!.stock = newQty.toInt()
            productEdited = false
        }
    }

    fun newPrice(newPrice: String){
        if ((newPrice != "null") && (newPrice != "")) {
            productEdited = true
            selectedProduct.value!!.price = newPrice.toLong()
            productEdited = false
        }
    }

    fun newProductName(newName: String){
        productEdited = true
        selectedProduct.value!!.name = newName
        productEdited = false
    }

    fun newProductDescr(newDescr: String){
        productEdited = true
        selectedProduct.value!!.description = newDescr
        productEdited = false
    }

    fun sellOneBottle(){
        if (selectedProduct.value!!.stock > 0) {
            productEdited = true
            selectedProduct.value!!.stock = selectedProduct.value!!.stock.minus(1)
            _showSnackbarEvent.value = true
            productEdited = false
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}