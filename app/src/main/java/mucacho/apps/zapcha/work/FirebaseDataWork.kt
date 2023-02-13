package mucacho.apps.zapcha.work

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import mucacho.apps.zapcha.database.ZapchaDatabase.Companion.getInstance
import mucacho.apps.zapcha.domain.Product
import mucacho.apps.zapcha.repository.ProductsRepository

class FirebaseDataWorker(appContext: Context, params: WorkerParameters):
    CoroutineWorker(appContext, params) {

    companion object {
        const val WORK_NAME = "FirebaseDataWorker"
    }

    override suspend fun doWork(): Result {
        val database = getInstance(applicationContext)
        val repository = ProductsRepository(database)
        val products = MutableLiveData<List<Product>>()
        return try {
            repository.refreshProducts(products)
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}