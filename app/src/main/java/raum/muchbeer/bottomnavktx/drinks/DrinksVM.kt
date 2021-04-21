package raum.muchbeer.bottomnavktx.drinks

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import raum.muchbeer.bottomnavktx.model.Donut
import raum.muchbeer.bottomnavktx.model.Drinks
import raum.muchbeer.bottomnavktx.model.database.DrinksDao

class DrinksVM(private val drinksDao: DrinksDao): ViewModel() {

    // Users of this ViewModel will observe changes to its donuts list to know when
    // to redisplay those changes
    val drinks: LiveData<List<Drinks>> = drinksDao.getAll()

    fun delete(drinks: Drinks) = viewModelScope.launch(Dispatchers.IO) {
        drinksDao.delete(drinks)
    }
}