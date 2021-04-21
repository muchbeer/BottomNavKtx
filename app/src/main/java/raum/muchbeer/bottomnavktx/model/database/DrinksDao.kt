package raum.muchbeer.bottomnavktx.model.database

import androidx.lifecycle.LiveData
import androidx.room.*
import raum.muchbeer.bottomnavktx.model.Drinks

@Dao
interface DrinksDao {

    @Query("SELECT * FROM drinks")
    fun getAll(): LiveData<List<Drinks>>

    @Query("SELECT * FROM drinks WHERE id = :id")
    suspend fun get(id: Long): Drinks

    @Insert
    suspend fun insert(drinks: Drinks): Long

    @Delete
    suspend fun delete(drinks: Drinks)

    @Update
    suspend fun update(drinks: Drinks)
}