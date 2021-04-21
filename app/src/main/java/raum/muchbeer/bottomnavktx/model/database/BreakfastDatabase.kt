package raum.muchbeer.bottomnavktx.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import raum.muchbeer.bottomnavktx.model.Donut
import raum.muchbeer.bottomnavktx.model.Drinks

@Database(entities = arrayOf(Donut::class, Drinks::class), version = 1)
 abstract class BreakfastDatabase() : RoomDatabase() {
    abstract fun donutDao(): DonutDao

    abstract fun drinksDao(): DrinksDao

    companion object {
        @Volatile private var INSTANCE: BreakfastDatabase? = null


        fun getDatabase(context: Context): BreakfastDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    BreakfastDatabase::class.java,
                    "break_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
 }
