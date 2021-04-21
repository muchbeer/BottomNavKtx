package raum.muchbeer.bottomnavktx.drinks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import raum.muchbeer.bottomnavktx.model.database.DrinksDao
import java.lang.IllegalArgumentException

class DrinksVMFactory(private val drinksDao: DrinksDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DrinksVM::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DrinksVM(drinksDao) as T
        } else if (modelClass.isAssignableFrom(DrinksEntryVM::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DrinksEntryVM(drinksDao) as T
        }

        throw IllegalArgumentException("Unknown VM")
    }
}