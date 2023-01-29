package mucacho.apps.zapcha.product

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.*
import mucacho.apps.zapcha.database.ZapChaProduct
import mucacho.apps.zapcha.database.ZapchaDatabaseDao

class ProductViewModel(
    val productId: Long,
    val database: ZapchaDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private var currentProduct = MutableLiveData<ZapChaProduct?>()

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

    val stockString = Transformations.map(stock,{onStock -> stockToString(onStock)})

    private var _showSnackbarEvent = MutableLiveData<Boolean>()

    val showSnackBarEvent: LiveData<Boolean>
        get() = _showSnackbarEvent

    fun doneShowingSnackbar(){
        _showSnackbarEvent.value = false
    }

    init{
//        one time setting
        initializeCurrentProduct(productId)
    }

    private fun initializeCurrentProduct(Id : Long) {
        uiScope.launch {
            currentProduct.value = getCurrentProductFromDatabase(Id)
            _name.value = currentProduct.value!!.productName
            _price.value = currentProduct.value!!.productPrice
            _descr.value = currentProduct.value!!.productDescr
            _stock.value = currentProduct.value!!.productStock
        }
    }

    private suspend fun getCurrentProductFromDatabase(Id: Long): ZapChaProduct? {
        return withContext(Dispatchers.IO) {
            val product = database.get(Id)
            product
        }
    }

    fun onSaveProduct(){
        uiScope.launch {
            withContext(Dispatchers.IO){
                val product = database.get(productId) ?: return@withContext
                product.productStock = stock.value!!
                product.productDescr = descr.value!!
                product.productName = name.value!!
                product.productPrice = price.value!!
                database.update(product)
            }
            _navigateToStore.value = true
        }
    }

    fun onDeleteProduct(){
        uiScope.launch {
            val oldProduct = currentProduct.value ?: return@launch
            delete(oldProduct)
            _navigateToStore.value = true
        }
    }

    private suspend fun delete(product: ZapChaProduct) {
        withContext(Dispatchers.IO) {
            database.delete(product)
        }
    }

    fun onClear() {
        uiScope.launch {
            clear()
            currentProduct.value = null
        }
    }

    private suspend fun clear() {
        withContext(Dispatchers.IO) {
            database.clear()
        }
    }

    private fun stockToString(qty : Int) : String{
        return "Na sklade: " + qty.toString()
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