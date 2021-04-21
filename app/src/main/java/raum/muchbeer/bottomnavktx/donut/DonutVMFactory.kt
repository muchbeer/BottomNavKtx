package raum.muchbeer.bottomnavktx.donut

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import raum.muchbeer.bottomnavktx.model.database.DonutDao
import java.lang.IllegalArgumentException

class DonutVMFactory(private val donutDao: DonutDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DonutVM::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DonutVM(donutDao) as T
        } else if (modelClass.isAssignableFrom(DonutEntryVM::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DonutEntryVM(donutDao) as T
        }
            throw IllegalArgumentException("Unknown VM")
    }
}