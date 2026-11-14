package es.dam.psp.marvelapp

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import coil3.load
import es.dam.psp.marvelapp.core.cache.AppCacheTTL
import es.dam.psp.marvelapp.core.serialization.GsonJsonSerializer
import es.dam.psp.marvelapp.core.serialization.KotlinJsonSerializier
import es.dam.psp.marvelapp.core.time.LocalAppTime
import es.dam.psp.marvelapp.features.superheroes.data.SuperHeroDataRepository
import es.dam.psp.marvelapp.features.superheroes.data.local.xml.SuperHeroXmlLocalDataSource
import es.dam.psp.marvelapp.features.superheroes.domain.GetSuperHeroesUseCase
import es.dam.psp.marvelapp.features.superheroes.domain.SuperHero
import es.dam.psp.marvelapp.features.superheroes.presentation.SuperHeroListViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: SuperHeroListViewModel by viewModels {
        SuperHeroListViewModel.factory(
            GetSuperHeroesUseCase(SuperHeroDataRepository(SuperHeroXmlLocalDataSource(
                this, GsonJsonSerializer.create<List<SuperHero>>(), AppCacheTTL(this, LocalAppTime())))
                        )
            )

    }

    /**
     * Ciclo de vida: Paso 1 OnCreate
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initObserver()
        viewModel.loadSuperHeroes()
    }

    private fun initObserver() {
        viewModel.uiState.observe(this) { uiState ->
            bind(uiState)

        }
    }

    /**
     * Se recoge la información del viewModel.
     */
    private fun bind(uiState: SuperHeroListViewModel.UiState) {

        /** Comprobar los estados de la vista **/

        if (uiState.isLoading) {
            //show loading
        } else {
            //hide loading
        }

        renderErrorUi(uiState.hasError)

        uiState.superHeroes?.let { superHeroes ->
            if (superHeroes.isEmpty()) {
                //muestro pantalla de listado vacío
            } else {
                renderUi(superHeroes.first())
            }
        }
    }

    private fun renderErrorUi(hasError: Boolean) {
        val errorView = findViewById<ConstraintLayout>(R.id.layout_error)
        if (hasError) {
            errorView.visibility = View.VISIBLE
        } else {
            errorView.visibility = View.GONE
        }
    }

    private fun renderUi(superHero: SuperHero) {
        // Ahora superhero_layout SÍ está en activity_main.xml gracias al <include>.
        val superheroLayout = findViewById<ConstraintLayout>(R.id.superhero_layout)

        superheroLayout.findViewById<TextView>(R.id.superhero_name).text = superHero.name
        superheroLayout.findViewById<TextView>(R.id.superhero_slug).text = superHero.slug
        superheroLayout.findViewById<ImageView>(R.id.superhero_image).load(superHero.urlImage)
    }
}