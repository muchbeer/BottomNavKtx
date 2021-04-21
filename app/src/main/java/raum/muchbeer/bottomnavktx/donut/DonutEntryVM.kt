package raum.muchbeer.bottomnavktx.donut

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import raum.muchbeer.bottomnavktx.model.Donut
import raum.muchbeer.bottomnavktx.model.database.DonutDao

class DonutEntryVM(val donutDao: DonutDao) : ViewModel() {

    private var donutLiveData: LiveData<Donut>? = null

    fun get(id: Long): LiveData<Donut> {
        return donutLiveData ?: liveData {
            emit(donutDao.get(id))
        }.also {
            donutLiveData = it
        }
    }

    fun addData(
        id: Long,
        name: String,
        description: String,
        rating: Int,
        setupNotification: (Long) -> Unit
    ) {
        val donut = Donut(id, name, description, rating)

        viewModelScope.launch {
            var actualId = id

            if (id > 0) {
                donutDao.update(donut)
            } else {
                actualId = insert(donut)
            }

            setupNotification(actualId)
        }

    }

    private suspend fun insert(donut: Donut): Long {
        return donutDao.insert(donut)
    }

}