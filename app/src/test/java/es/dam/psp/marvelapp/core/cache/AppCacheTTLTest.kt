package es.dam.psp.marvelapp.core.cache

import android.content.Context
import android.content.SharedPreferences
import es.dam.psp.marvelapp.core.time.AppTime
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class AppCacheTTLTest {

    private val context: Context = mockk()
    private val sharedPreferences: SharedPreferences = mockk()
    private val editor: SharedPreferences.Editor = mockk(relaxed = true)
    private val appTime: AppTime = mockk()

    private lateinit var appCache: AppCacheTTL

    @Before
    fun setUp() {
        // sharedPref se crea en el constructor → estos mocks van ANTES de crear AppCacheTTL
        every { context.getString(any()) } returns "app_cache"
        every { context.getSharedPreferences("app_cache", Context.MODE_PRIVATE) } returns sharedPreferences
        every { sharedPreferences.edit() } returns editor
        every { editor.putLong(any(), any()) } returns editor

        appCache = AppCacheTTL(context, appTime)
    }

    @Test
    fun `updateTime guarda la hora actual con la clave indicada`() {
        // Given
        every { appTime.currentTimeToLong() } returns 1_000L

        // When
        appCache.updateTime("heroes")

        // Then
        verify(exactly = 1) { editor.putLong("heroes", 1_000L) }
        verify(exactly = 1) { editor.apply() }
    }

    @Test
    fun `isExpired devuelve false si no ha pasado el ttl`() {
        // Given
        every { sharedPreferences.getLong("heroes", -1) } returns 1_000L
        every { appTime.currentTimeToLong() } returns 1_500L

        // When
        val result = appCache.isExpired("heroes", ttl = 1_000L)

        // Then
        assertFalse(result)          // 1500 − 1000 = 500 < 1000
    }

    @Test
    fun `isExpired devuelve true si ha pasado justo el ttl`() {
        // Given
        every { sharedPreferences.getLong("heroes", -1) } returns 1_000L
        every { appTime.currentTimeToLong() } returns 2_000L

        // When
        val result = appCache.isExpired("heroes", ttl = 1_000L)

        // Then
        assertTrue(result)           // 2000 − 1000 = 1000 >= 1000
    }

    @Test
    fun `isExpired devuelve true si ha pasado mas del ttl`() {
        // Given
        every { sharedPreferences.getLong("heroes", -1) } returns 1_000L
        every { appTime.currentTimeToLong() } returns 5_000L

        // When
        val result = appCache.isExpired("heroes", ttl = 1_000L)

        // Then
        assertTrue(result)           // 5000 − 1000 = 4000 >= 1000
    }

    @Test
    fun `isExpired devuelve true si la clave nunca se ha guardado`() {
        // Given
        every { sharedPreferences.getLong("heroes", -1) } returns -1L
        every { appTime.currentTimeToLong() } returns 5_000L

        // When
        val result = appCache.isExpired("heroes", ttl = 1_000L)

        // Then
        assertTrue(result)           // 5000 − (−1) = 5001 >= 1000
    }
}