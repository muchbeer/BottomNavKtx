package raum.muchbeer.bottomnavktx.donut

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import raum.muchbeer.bottomnavktx.model.Donut
import raum.muchbeer.bottomnavktx.model.database.DonutDao

class DonutVM(private val donutDao: DonutDao) : ViewModel() {

    // Users of this ViewModel will observe changes to its donuts list to know when
    // to redisplay those changes
    val donuts: LiveData<List<Donut>> = donutDao.getAll()

    fun delete(donut: Donut) = viewModelScope.launch(Dispatchers.IO) {
        donutDao.delete(donut)
    }
}