package uk.co.massimocarli.livedatabus.db

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * This is the class which defines the ViewModel for the Bus application
 */
class BusViewModel(val repository: BusRepository) : ViewModel() {

  val job: Job = Job()
  val coroutineScope = CoroutineScope(Dispatchers.IO + job)

  private val dbLiveData: MutableLiveData<BusRepositoryResponse> = lazy {
    MutableLiveData<BusRepositoryResponse>()
  }.value


  override fun onCleared() {
    super.onCleared()
    job.cancel()
  }

  fun getDbLiveData(): LiveData<BusRepositoryResponse> = dbLiveData

  /**
   * Here we need to introduce the asynchronous component. There are many options but we
   * can use coroutines
   */
  fun findByLocation(latitude: Double, longitude: Double, delta: Double) {
    coroutineScope.launch {
      val busStopList = repository.findByLocation(latitude, longitude, delta)
      dbLiveData.postValue(FindBusStopByLocationResult(busStopList))
    }
  }
}
