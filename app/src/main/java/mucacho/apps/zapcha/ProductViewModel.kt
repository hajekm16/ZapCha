package mucacho.apps.zapcha

import android.view.ViewDebug.IntToString
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class ProductViewModel : ViewModel() {
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
}