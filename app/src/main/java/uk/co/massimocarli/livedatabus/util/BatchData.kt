package uk.co.massimocarli.livedatabus.util

import android.content.Context
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import uk.co.massimocarli.livedatabus.location.CONF
import kotlin.random.Random


class InsertBusStopData(val context: Context) : RoomDatabase.Callback() {

  override fun onCreate(db: SupportSQLiteDatabase) {
    super.onCreate(db)
    GlobalScope.launch {
      val statement =
        db.compileStatement(
          "INSERT INTO BusStop " +
              "(stopId, stopName, direction, latitude, longitude) " +
              "VALUES (?,?,?,?,?)"
        )
      db.beginTransaction()
      try {
        (1..50).forEach {
          with(statement) {
            bindString(1, "$it")
            bindString(2, "stop_$it")
            bindString(3, "direction_$it")
            // We calculate the positions based on the central position in Conf
            val randLat = CONF.CENTER_LOCATION.first + Random.nextDouble(0.5)
            val randLong = CONF.CENTER_LOCATION.second + Random.nextDouble(0.5)
            bindDouble(4, randLat)
            bindDouble(5, randLong)
            statement.executeInsert()
          }
        }
        db.setTransactionSuccessful()
      } finally {
        db.endTransaction()
      }
    }
  }

}
