package uk.co.massimocarli.livedatabus

import android.app.Application
import uk.co.massimocarli.livedatabus.location.ServiceLocator
import uk.co.massimocarli.livedatabus.location.ServiceLocatorImpl

class LiveBusApp : Application() {

  lateinit var serviceLocator: ServiceLocator
    get

  override fun onCreate() {
    super.onCreate()
    serviceLocator = ServiceLocatorImpl(this)
  }
}