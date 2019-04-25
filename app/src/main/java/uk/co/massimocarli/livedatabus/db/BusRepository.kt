package uk.co.massimocarli.livedatabus.db

import androidx.annotation.WorkerThread

sealed class BusRepositoryResponse
class FindBusStopByLocationResult(val busStopList: List<BusStop>) : BusRepositoryResponse()

/**
 * This is the abstraction for the BusRepository
 */
interface BusRepository {

    /**
     * This is the function which returns the BusStop close to the current location
     */
    fun findByLocation(latitude: Double, longitude: Double, delta: Double): List<BusStop>

    /**
     * Search the bus stop by name
     */
    fun findByName(name: String): List<BusStop>
}

/**
 * The implementation of the BusRepository
 */
class BusRepositoryImpl(val db: LiveBusDB) : BusRepository {

    @WorkerThread
    override fun findByLocation(
        latitude: Double,
        longitude: Double,
        delta: Double
    ): List<BusStop> =
        db.getBusStopDAO().findByLocation(latitude, longitude, delta)

    @WorkerThread
    override fun findByName(name: String): List<BusStop> =
        db.getBusStopDAO().findByName(name)
}

