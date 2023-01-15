package mucacho.apps.zapcha

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProductViewModel : ViewModel() {
    val productName: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
}