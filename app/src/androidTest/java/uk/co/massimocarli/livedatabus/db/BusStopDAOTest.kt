package uk.co.massimocarli.livedatabus.db

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class BusStopDAOTest {

    private lateinit var busDao: BusStopDAO
    private lateinit var db: LiveBusDB

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, LiveBusDB::class.java
        ).build()
        // We insert data
        val statement =
            db.compileStatement("INSERT INTO BusStop (stopId, stopName, direction, latitude, longitude) VALUES (?,?,?,?,?)")
        db.runInTransaction {
            (1..10).forEach {
                statement.bindString(1, "$it")
                statement.bindString(2, "stop_$it")
                statement.bindString(3, "direction_$it")
                statement.bindDouble(4, it.toDouble())
                statement.bindDouble(5, it.toDouble())
                statement.executeInsert()
            }
        }
        busDao = db.getBusStopDAO()
    }

    @Test
    fun findByLocation_busStopNotPresent_returnsEmptyList() {
        val result = busDao.findByLocation(100.0, 100.0, 1.0);
        assertThat(result).isEmpty()
    }

    @Test
    fun findByLocation_exacttBusStop_returnsListWithSingleBusStop() {
        val result = busDao.findByLocation(1.0, 1.0, 0.01);
        assertThat(result.size).isEqualTo(1)
    }

    @Test
    fun findByLocation_bigDelta_returnsListWithAllBusStop() {
        val result = busDao.findByLocation(1.0, 1.0, 100.0);
        assertThat(result.size).isEqualTo(10)
    }

    @Test
    fun findByName_busStopNotPresent_returnsEmptyList() {
        val result = busDao.findByName("%missing%");
        assertThat(result).isEmpty()
    }

    @Test
    fun findByName_exactName_returnsListWithSingleBusStop() {
        val result = busDao.findByName("%stop_5%");
        assertThat(result.size).isEqualTo(1)
        assertThat(result[0].stopName).isEqualTo("stop_5")
    }

    @Test
    fun findByName_likeName_returnsListWithAllBusStop() {
        val result = busDao.findByName("%stop%");
        assertThat(result.size).isEqualTo(10)
    }

    @After
    @Throws(IOException::class)
    fun tearDown() {
        db.close()
    }
}