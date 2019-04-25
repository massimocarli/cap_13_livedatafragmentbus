package uk.co.massimocarli.livedatabus.db

import androidx.room.Dao
import androidx.room.Query

@Dao
interface BusStopDAO {

    /**
     * This is the function which returns the BusStop close to the current location
     */
    @Query(
        "SELECT * FROM busstop " +
                "WHERE " +
                "ABS(:latitude - busstop.latitude) < :delta " +
                "AND  " +
                "ABS(:longitude - busstop.longitude) < :delta"
    )
    fun findByLocation(latitude: Double, longitude: Double, delta: Double): List<BusStop>

    /**
     * This is the function which returns the BusStop close to the current location
     */
    @Query(
        "SELECT * FROM busstop WHERE busstop.stopName LIKE :name"
    )
    fun findByName(name: String): List<BusStop>
}