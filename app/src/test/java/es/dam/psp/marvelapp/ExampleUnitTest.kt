package es.dam.psp.marvelapp

import es.dam.psp.marvelapp.core.cache.AppCache
import es.dam.psp.marvelapp.core.time.AppTime
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
    @Test
    fun sumar_2_mas_3_da_5 (){
        val a = 2
        val b = 3
        val resultado = sumar(a,b)
        assertEquals(5,resultado)
    }
    @Test
    fun no_caducado_si_no_pasa_ttl(){
        val guardado = 1000L
        val ahora = 1500L
        val ttl = 1000L
        val resultado = estacaducado(guardado,ahora,ttl)
        assertFalse(resultado)
    }
    @Test
    fun caducida_no_caduca_con_Hora_falsa(){
        val guardado = 1300L
        val appTime: AppTime = mockk()
        every { appTime.currentTimeToLong() } returns 1500L
        val caducidad = Caducidad(appTime)
        val ttl = 1000L
        val resultado = caducidad.estacaducado(guardado,ttl)
        assertFalse(resultado)

    }
    @Test
    fun testear_guardar(){
        val appCache: AppCache = mockk(relaxed = true)
        val save = Guardar(appCache)
        save.guardar()
        verify { appCache.updateTime("heroes") }

    }

    
}
fun sumar(a: Int , b: Int) = a + b
fun estacaducado(guardado: Long, ahora: Long, ttl: Long): Boolean{
    return ahora - guardado>=ttl
}
class Caducidad( private  val appTime: AppTime){
    fun estacaducado(guardado: Long, ttl: Long): Boolean{
        return appTime.currentTimeToLong() - guardado>=ttl
    }
}
class Guardar( private val appCache: AppCache){
    fun guardar(){
        appCache.updateTime("heroes")
    }
}
class OrdenTest{
    @Before
    fun setUp(){
        print("1,setUp,preparo todo ")
    }
    @Test
    fun testA() {
        println("2. testA se ejecuta")
    }

    @Test
    fun testB() {
        println("2. testB se ejecuta")
    }

}
class OrdenTest2{
    private val  appTime: AppTime = mockk()
     private lateinit var caducidad: Caducidad
    @Before
    fun setUp(){
        caducidad = Caducidad(appTime)
        every { appTime.currentTimeToLong() }returns 5000L

    }
    @Test
    fun testA(){
        val resultado = caducidad.estacaducado(1000L,500L)
        assertTrue(resultado)
    }


}


