package raum.muchbeer.bottomnavktx.drinks

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import raum.muchbeer.bottomnavktx.model.Donut
import raum.muchbeer.bottomnavktx.model.Drinks
import raum.muchbeer.bottomnavktx.model.database.DrinksDao

class DrinksEntryVM(private val drinksDao: DrinksDao) : ViewModel() {

    private var drinksLiveData: LiveData<Drinks>? = null

    fun get(id: Long): LiveData<Drinks> {
        return drinksLiveData ?: liveData {
            emit(drinksDao.get(id))
        }.also {
            drinksLiveData = it
        }
    }

    fun addData(
        id: Long,
        name: String,
        description: String,
        rating: Int,
        setupNotification: (Long) -> Unit
    ) {
        val drinks = Drinks(id, name, description, rating)

        viewModelScope.launch {
            var actualId = id

            if (id > 0) {
                drinksDao.update(drinks)
            } else {
                actualId = insert(drinks)
            }

            setupNotification(actualId)
        }

    }

    private suspend fun insert(drinks: Drinks): Long {
        return drinksDao.insert(drinks)
    }
}