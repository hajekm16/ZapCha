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
    fun insert(product: ZapChaProduct)

    @Update
    fun update(product: ZapChaProduct)

    @Query("SELECT * FROM zapcha_product_table WHERE productId = :key")
    fun get(key:Long): ZapChaProduct

    @Delete
    fun delete(product: ZapChaProduct)

    @Query("DELETE FROM zapcha_product_table")
    fun clear()

    @Query("SELECT * FROM zapcha_product_table ORDER BY productId DESC")
    fun getAllProducts(): LiveData<List<ZapChaProduct>>

    @Query("SELECT * FROM zapcha_product_table ORDER BY productId DESC LIMIT 1")
    fun getLastProduct(): ZapChaProduct?
}