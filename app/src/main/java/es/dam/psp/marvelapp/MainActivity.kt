package es.dam.psp.marvelapp

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import es.dam.psp.marvelapp.core.cache.AppCache
import es.dam.psp.marvelapp.core.cache.AppCacheTTL
import es.dam.psp.marvelapp.core.serialization.GsonJsonSerializer
import es.dam.psp.marvelapp.core.serialization.KotlinJsonSerializier
import es.dam.psp.marvelapp.core.time.LocalAppTime
import es.dam.psp.marvelapp.features.superheroes.data.local.xml.SuperHeroXmlLocalDataSource
import es.dam.psp.marvelapp.features.superheroes.domain.SuperHero
import es.dam.psp.marvelapp.features.superheroes.domain.superHeroLista
import kotlinx.serialization.serializer

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

       // val xml = SuperHeroXmlLocalDataSource(this, KotlinJsonSerializier<List<SuperHero>>(serializer<List<SuperHero>>()))
        val xml = SuperHeroXmlLocalDataSource(this, KotlinJsonSerializier.create<List<SuperHero>>(),
             AppCacheTTL(this, LocalAppTime()))
        //val guardarSH = xml.save(superHeroLista)
        val leerSH = xml.findAll()
        Log.d("superHero",leerSH.toString())
       // xml.deleteAll("superHero")
        val xmlGson = SuperHeroXmlLocalDataSource(this,GsonJsonSerializer.create<List<SuperHero>>(),
            AppCacheTTL(this, LocalAppTime()))
        //xmlGson.deleteAll("superhero")


    }
}