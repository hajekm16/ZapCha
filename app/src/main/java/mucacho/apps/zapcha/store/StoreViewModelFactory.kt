package mucacho.apps.zapcha.store

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import mucacho.apps.zapcha.database.ZapchaDatabaseDao

class StoreViewModelFactory(
    private val dataSource: ZapchaDatabaseDao,
    private val application: Application) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StoreViewModel::class.java)){
            return StoreViewModel(dataSource,application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}