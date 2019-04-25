package uk.co.massimocarli.livedatabus.location

object CONF {
  /**
   * This is the delta we use when we search for a BusStop. It's related to
   * a location in latitude and longitude
   */
  const val DISTANCE_DELTA = 0.2

  /**
   * This is the current location we use in order to insert some dummy data into the
   * DB so we can test it
   */
  val CENTER_LOCATION = 51.446116 to -0.219668


  const val DB_NAME = "bus-db"
}