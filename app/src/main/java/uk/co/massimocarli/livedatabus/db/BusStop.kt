package uk.co.massimocarli.livedatabus.db

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * This is the entity for the BusStop
 */
@Entity
data class BusStop(
    @PrimaryKey
    val stopId: String,
    val stopName: String,
    val direction: String,

    @Embedded
    val location: Location
)

data class Location(
    val latitude: Double,
    val longitude: Double
)