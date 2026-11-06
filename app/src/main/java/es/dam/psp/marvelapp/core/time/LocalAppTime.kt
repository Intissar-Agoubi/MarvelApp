package es.dam.psp.marvelapp.core.time

class LocalAppTime : AppTime {
    override fun currentTimeToLong(): Long {
      return System.currentTimeMillis()
    }
}