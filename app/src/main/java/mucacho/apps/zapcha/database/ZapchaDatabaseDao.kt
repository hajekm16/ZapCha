package mucacho.apps.zapcha.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ZapchaDatabaseDao {

    @Insert
    fun insert(product: ZapChaDatabaseProduct)

    @Update
    fun update(product: ZapChaDatabaseProduct)

    @Query("SELECT * FROM zapcha_product_table WHERE productId = :key")
    fun get(key:Long): ZapChaDatabaseProduct

    @Delete
    fun delete(product: ZapChaDatabaseProduct)

    @Query("DELETE FROM zapcha_product_table")
    fun clear()

    @Query("SELECT * FROM zapcha_product_table ORDER BY productId DESC")
    fun getAllProducts(): LiveData<List<ZapChaDatabaseProduct>>

    @Query("SELECT * FROM zapcha_product_table ORDER BY productId DESC LIMIT 1")
    fun getLastProduct(): ZapChaDatabaseProduct?
}