package mucacho.apps.zapcha.product

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import mucacho.apps.zapcha.database.ZapChaDatabaseProduct
import mucacho.apps.zapcha.database.ZapchaDatabase
import mucacho.apps.zapcha.database.ZapchaDatabaseDao
import mucacho.apps.zapcha.database.asDomainModel
import mucacho.apps.zapcha.domain.Product
import mucacho.apps.zapcha.repository.ProductsRepository

// viewmodel for single product
class ProductViewModel(
    val productId: Long,
    val database: ZapchaDatabase,
    application: Application
) : AndroidViewModel(application) {

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private var selectedProduct = MutableLiveData<Product?>()

    private val productsRepository = ProductsRepository(database)

    private val _navigateToStore = MutableLiveData<Boolean?>()

    val navigateToStore : LiveData<Boolean?>
        get() = _navigateToStore

    fun doneNavigating() {
        _navigateToStore.value = null
    }

    private val _name = MutableLiveData<String>()
    val name : LiveData<String>
        get() = _name

    private val _descr = MutableLiveData<String>()
    val descr : LiveData<String>
        get() = _descr

    private val _price = MutableLiveData<Long>()
    val price : LiveData<Long>
        get() = _price

    private val _stock = MutableLiveData<Int>()
    val stock : LiveData<Int>
        get() = _stock

    private var _showSnackbarEvent = MutableLiveData<Boolean>()

    val showSnackBarEvent: LiveData<Boolean>
        get() = _showSnackbarEvent

    fun doneShowingSnackbar(){
        _showSnackbarEvent.value = false
    }

    init{
//        one time setting
        initializeSelectedProduct(productId)
    }

    private fun initializeSelectedProduct(Id : Long) {
        uiScope.launch {
            selectedProduct.value = productsRepository.getSelectedProduct(Id)
            _name.value = selectedProduct.value!!.name
            _price.value = selectedProduct.value!!.price
            _descr.value = selectedProduct.value!!.description
            _stock.value = selectedProduct.value!!.stock
        }
    }

    fun onSaveProduct(){
        uiScope.launch {
            productsRepository.saveProduct(Product(productId,name.value!!,descr.value!!,stock.value!!,price.value!!))
        }
        _navigateToStore.value = true
    }

    fun onDeleteProduct(){
        uiScope.launch {
            val oldProduct = selectedProduct.value ?: return@launch
            productsRepository.deleteSelectedProduct(oldProduct)
            _navigateToStore.value = true
        }
    }

    fun newStockQty(newQty: String){
        if ((newQty != "null") && (newQty != "")) {
            _stock.value = newQty.toInt()
        }
    }

    fun newPrice(newPrice: String){
        if ((newPrice != "null") && (newPrice != "")) {
            _price.value = newPrice.toLong()
        }
    }

    fun newProductName(newName: String){
        _name.value = newName
    }

    fun newProductDescr(newDescr: String){
        _descr.value = newDescr
    }

    fun sellOneBottle(){
        _stock.value = _stock.value?.minus(1)
        _showSnackbarEvent.value = true
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}