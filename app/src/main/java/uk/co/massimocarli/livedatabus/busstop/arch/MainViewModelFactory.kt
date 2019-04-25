package uk.co.massimocarli.livedatabus.busstop.arch

import android.app.Application
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import uk.co.massimocarli.livedatabus.LiveBusApp
import uk.co.massimocarli.livedatabus.db.BusRepository
import uk.co.massimocarli.livedatabus.db.BusViewModel
import uk.co.massimocarli.livedatabus.location.LocationViewModel

class MainViewModelFactory(
  val app: Application,
  val owner: Fragment
) : ViewModelProvider.Factory {
  override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    val locationViewModel = owner?.run {
      ViewModelProviders.of(
        this,
        ViewModelProvider.AndroidViewModelFactory.getInstance(app)
      ).get(LocationViewModel::class.java)
    }
    val repositoryFactory = RepositoryModelFactory(app)
    val busViewModel = owner?.run {
      ViewModelProviders.of(
        this,
        repositoryFactory
      ).get(BusViewModel::class.java)
    }

    val instance = modelClass.getConstructor(
      LocationViewModel::class.java,
      BusViewModel::class.java
    ).newInstance(
      locationViewModel,
      busViewModel
    )

    return instance as T
  }
}


class RepositoryModelFactory(val app: Application) : ViewModelProvider.Factory {
  override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    val serviceLocator = (app as LiveBusApp).serviceLocator
    return modelClass.getConstructor(BusRepository::class.java)
      .newInstance(serviceLocator.busRepository)
  }
}