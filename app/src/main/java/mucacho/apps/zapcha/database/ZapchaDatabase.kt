package mucacho.apps.zapcha.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

//jakou pouzit entitu
@Database(entities = [ZapChaProduct::class], version = 1)
abstract class ZapchaDatabase : RoomDatabase() {
//    jake pouzit Dao
    abstract val zapchaDatabaseDao: ZapchaDatabaseDao
//    object pro pristup k metodam bez instance tridy
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