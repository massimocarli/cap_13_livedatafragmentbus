package uk.co.massimocarli.livedatabus.db

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * This is the Database class for LiveBus
 */
@Database(entities = arrayOf(BusStop::class), version = 1)
abstract class LiveBusDB : RoomDatabase() {

    abstract fun getBusStopDAO(): BusStopDAO
}