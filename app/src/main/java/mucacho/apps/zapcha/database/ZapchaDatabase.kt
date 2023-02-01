package mucacho.apps.zapcha.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

//database entity
@Database(entities = [ZapChaDatabaseProduct::class], version = 1)
abstract class ZapchaDatabase : RoomDatabase() {
//    Dao for accessing DB
    abstract val zapchaDatabaseDao: ZapchaDatabaseDao
//    object for access methods without class instance
    companion object{
//        zajistime vzdy aktualni hodnotu instance
        @Volatile
        private var INSTANCE: ZapchaDatabase? = null

        fun getInstance(context: Context) : ZapchaDatabase {
            synchronized(this){
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ZapchaDatabase::class.java,
                        "zapcha_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}