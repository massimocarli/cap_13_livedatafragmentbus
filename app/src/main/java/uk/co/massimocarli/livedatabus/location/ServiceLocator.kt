package uk.co.massimocarli.livedatabus.location

import android.content.Context
import androidx.room.Room
import uk.co.massimocarli.livedatabus.db.BusRepository
import uk.co.massimocarli.livedatabus.db.BusRepositoryImpl
import uk.co.massimocarli.livedatabus.db.LiveBusDB
import uk.co.massimocarli.livedatabus.location.CONF.DB_NAME
import uk.co.massimocarli.livedatabus.util.InsertBusStopData

interface ServiceLocator {

  val busRepository: BusRepository
}

class ServiceLocatorImpl(val context: Context) : ServiceLocator {
  override val busRepository: BusRepository =
    lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
      val db = Room.databaseBuilder(
        context,
        LiveBusDB::class.java,
        DB_NAME
      ).addCallback(InsertBusStopData(context))
        .build()
      BusRepositoryImpl(db)
    }.value
}
