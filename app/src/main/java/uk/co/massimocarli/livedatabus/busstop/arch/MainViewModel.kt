package uk.co.massimocarli.livedatabus.busstop.arch

import androidx.lifecycle.*
import uk.co.massimocarli.livedatabus.LocationData
import uk.co.massimocarli.livedatabus.LocationEvent
import uk.co.massimocarli.livedatabus.db.BusRepositoryResponse
import uk.co.massimocarli.livedatabus.db.BusViewModel
import uk.co.massimocarli.livedatabus.location.CONF.DISTANCE_DELTA
import uk.co.massimocarli.livedatabus.location.LocationViewModel

sealed class MainViewModelResponse
class LocationResponse(val locationEvent: LocationEvent?) : MainViewModelResponse()
class RepositoryResponse(val repositoryEvent: BusRepositoryResponse?) : MainViewModelResponse()

class MainViewModel(
  val locationViewModel: LocationViewModel,
  val busViewModel: BusViewModel
) : ViewModel() {

  val locationObserver = Observer<LocationEvent> { locationEvent ->
    mainLiveData.postValue(LocationResponse(locationEvent))
    // If we have a location we trigger the busViewModel
    if (locationEvent is LocationData) {
      // We query the Bus Stop close to the location
      val location = locationEvent.location
      location?.run {
        busViewModel?.findByLocation(latitude, longitude, DISTANCE_DELTA)
      }
    }
  }

  val repositoryObserver = Observer<BusRepositoryResponse> { busRepositoryEvent ->
    mainLiveData.postValue(RepositoryResponse(busRepositoryEvent))
  }

  inner class DecoratedMutableLiveData<T> : MutableLiveData<T>() {

    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
      super.observe(owner, observer)
      locationViewModel?.getLocationLiveData()?.observe(owner, locationObserver)
      busViewModel?.getDbLiveData()?.observe(owner, repositoryObserver)
    }

    override fun removeObservers(owner: LifecycleOwner) {
      super.removeObservers(owner)
      locationViewModel?.getLocationLiveData()?.removeObservers(owner)
      busViewModel?.getDbLiveData()?.removeObservers(owner)
    }
  }

  private val mainLiveData = DecoratedMutableLiveData<MainViewModelResponse>()

  fun getMainLiveData(): LiveData<MainViewModelResponse> = mainLiveData
}
