package mucacho.apps.zapcha.product

import android.app.Application
import android.provider.SyncStateContract.Helpers.insert
import android.view.ViewDebug.IntToString
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import mucacho.apps.zapcha.database.ZapChaProduct
import mucacho.apps.zapcha.database.ZapchaDatabase
import mucacho.apps.zapcha.database.ZapchaDatabaseDao

class ProductViewModel(
    val database: ZapchaDatabaseDao,
    application: Application) : AndroidViewModel(application) {

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private var currentProduct = MutableLiveData<ZapChaProduct?>()

    private val products = database.getAllProducts()

    var name = ""
    var price = 0
    var descr = ""
    private val _stock = MutableLiveData<Int>()
    val stock : LiveData<Int>
        get() = _stock
    val stockString = Transformations.map(stock,{onStock -> stockToString(onStock)})

    init{
//        one time setting
        _stock.value = 0
        initializeCurrentProduct()
    }

    private fun initializeCurrentProduct() {
        uiScope.launch {
            currentProduct.value = getCurrentProductFromDatabase()
        }
    }

    private suspend fun getCurrentProductFromDatabase(): ZapChaProduct? {
        return withContext(Dispatchers.IO) {
            var product = database.getLastProduct()
            product
        }
    }

    fun onSaveProduct(){
        uiScope.launch {
            val newProduct = ZapChaProduct()
            insert(newProduct)
            currentProduct.value = getCurrentProductFromDatabase()
        }
    }

    private suspend fun insert(product: ZapChaProduct){
        withContext(Dispatchers.IO){
            database.insert(product)
        }
    }

    fun onDeleteProduct(){
        uiScope.launch {
            val oldProduct = currentProduct.value ?: return@launch
            delete(oldProduct)
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

    fun stockToString(qty : Int) : String{
        return "Na sklade: " + qty.toString()
    }
    fun newStockQty(newQty: Int){
        _stock.value = newQty
    }

    fun sellOneBottle(){
        _stock.value = _stock.value?.minus(1)
    }

    fun loadProduct(productId: Int = 0){
        when (productId){
            1 -> {
                name = "Ananas"
                price = 55
                _stock.value = 6
                descr = "Tohle je trosku exotica..."
            }
            2 -> {
                name = "Zazvor"
                price = 55
                _stock.value = 10
                descr = "Tohle nakopne imunitu..."
            }
            3 -> {
                name = "Boruvka"
                price = 55
                _stock.value = 8
                descr = "Tohle je porce antioxidantu..."
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}