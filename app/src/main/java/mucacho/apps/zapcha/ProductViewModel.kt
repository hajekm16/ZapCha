package mucacho.apps.zapcha

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProductViewModel : ViewModel() {
    var name = ""
    var price = 0
    var stock = 0
    var descr = ""
    val productName: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    init{
//        one time setting
    }
    fun LoadProduct(productId: Int = 0){
        when (productId){
            1 -> {
                name = "Ananas"
                price = 55
                stock = 10
                descr = "Tohle je trosku exotica..."
            }
            2 -> {
                name = "Zazvor"
                price = 55
                stock = 10
                descr = "Tohle nakopne imunitu..."
            }
            3 -> {
                name = "Boruvka"
                price = 55
                stock = 10
                descr = "Tohle je porce antioxidantu..."
            }
        }
    }
}