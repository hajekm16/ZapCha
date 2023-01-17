package mucacho.apps.zapcha

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProductViewModel : ViewModel() {
    var name = ""
    var price = 0
    var descr = ""
    private val _stock = MutableLiveData<Int>()
    val stock : LiveData<Int>
        get() = _stock
    val productName: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    init{
//        one time setting
        _stock.value = 0
    }
    fun newStockQty(Qty:Int){
        _stock.value = Qty
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
}