package com.pmdm.ud3.temas

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.pmdm.ud3.temas.pantallas.PantallaFormas
import com.pmdm.ud3.temas.pantallas.PantallaInicio
import com.pmdm.ud3.temas.pantallas.PantallaNaturaleza
import com.pmdm.ud3.temas.pantallas.PantallaPaleta
import com.pmdm.ud3.temas.pantallas.PantallaResponsiva
import com.pmdm.ud3.temas.pantallas.PantallaTipografia
import com.pmdm.ud3.temas.pantallas.PantallaUsoColores
import com.pmdm.ud3.temas.ui.theme.MiAppTheme
import com.pmdm.ud3.temas.ui.theme.NaturalezaTheme
import com.pmdm.ud3.temas.ui.theme.Paleta

/** Rutas de la app (enfoque con strings de la lección 4). */
object Rutas {
    const val INICIO = "inicio"
    const val PALETA = "paleta"
    const val TIPOGRAFIA = "tipografia"
    const val FORMAS = "formas"
    const val USO_COLORES = "uso_colores"
    const val RESPONSIVO = "responsivo"
    const val NATURALEZA = "naturaleza"
}

/** Título que muestra la TopAppBar en cada ruta. */
private val titulos = mapOf(
    Rutas.INICIO to "Lección 5 · Temas",
    Rutas.PALETA to "5.2 · Roles de color",
    Rutas.TIPOGRAFIA to "5.3 · Tipografía",
    Rutas.FORMAS to "5.4 · Formas",
    Rutas.USO_COLORES to "5.6 · Usar el tema bien",
    Rutas.RESPONSIVO to "5.7 · Diseño responsivo",
    Rutas.NATURALEZA to "5.9 · Tema Naturaleza"
)

/**
 * Raíz de la app.
 *
 * El estado de los ajustes del tema vive AQUÍ, por encima del tema, porque el
 * propio tema depende de él (state hoisting, lección 3). Las pantallas solo
 * reciben los valores y avisan con lambdas cuando el usuario los cambia.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTemas() {
    val sistemaEnOscuro = isSystemInDarkTheme()
    // rememberSaveable: al girar el móvil no se pierden los ajustes
    var modoOscuro by rememberSaveable { mutableStateOf(sistemaEnOscuro) }
    var coloresDinamicos by rememberSaveable { mutableStateOf(false) }
    var paleta by rememberSaveable { mutableStateOf(Paleta.AZUL) }

    IconosBarraDeEstado(modoOscuro)

    TemaSeleccionado(paleta = paleta, modoOscuro = modoOscuro, coloresDinamicos = coloresDinamicos) {
        val navController = rememberNavController()
        val entradaActual by navController.currentBackStackEntryAsState()
        val rutaActual = entradaActual?.destination?.route ?: Rutas.INICIO

        Scaffold(
            topBar = {
                // Es el "Toggle manual de tema" del apartado 5.5 (AppConToggleTema)
                TopAppBar(
                    title = { Text(titulos[rutaActual] ?: "") },
                    navigationIcon = {
                        if (rutaActual != Rutas.INICIO) {
                            IconButton(onClick = { navController.popBackStack() }) {
                                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                            }
                        }
                    },
                    actions = {
                        IconButton(onClick = { modoOscuro = !modoOscuro }) {
                            Icon(
                                imageVector = if (modoOscuro) Icons.Default.LightMode
                                              else Icons.Default.DarkMode,
                                contentDescription = "Cambiar tema"
                            )
                        }
                    }
                )
            }
        ) { paddingValues ->
            NavHost(
                navController = navController,
                startDestination = Rutas.INICIO,
                modifier = Modifier
                    .padding(paddingValues)
                    .consumeWindowInsets(paddingValues)
            ) {
                composable(Rutas.INICIO) {
                    PantallaInicio(
                        modoOscuro = modoOscuro,
                        onModoOscuroChange = { modoOscuro = it },
                        coloresDinamicos = coloresDinamicos,
                        onColoresDinamicosChange = { coloresDinamicos = it },
                        paleta = paleta,
                        onPaletaChange = { paleta = it },
                        onAbrir = { ruta -> navController.navigate(ruta) }
                    )
                }
                composable(Rutas.PALETA) { PantallaPaleta() }
                composable(Rutas.TIPOGRAFIA) { PantallaTipografia() }
                composable(Rutas.FORMAS) { PantallaFormas() }
                composable(Rutas.USO_COLORES) { PantallaUsoColores() }
                composable(Rutas.RESPONSIVO) { PantallaResponsiva() }
                composable(Rutas.NATURALEZA) { PantallaNaturaleza(modoOscuro = modoOscuro) }
            }
        }
    }
}

/** Aplica uno u otro tema según la paleta elegida. */
@Composable
private fun TemaSeleccionado(
    paleta: Paleta,
    modoOscuro: Boolean,
    coloresDinamicos: Boolean,
    content: @Composable () -> Unit
) {
    when (paleta) {
        Paleta.AZUL -> MiAppTheme(
            modoOscuro = modoOscuro,
            coloresDinamicos = coloresDinamicos,
            content = content
        )
        Paleta.NATURALEZA -> NaturalezaTheme(darkTheme = modoOscuro, content = content)
    }
}

/**
 * Detalle "fino": si el usuario fuerza el modo claro con el sistema en oscuro (o al revés),
 * los iconos de la barra de estado (hora, batería) tienen que cambiar de color para verse.
 * SideEffect: se ejecuta tras cada composición; es la forma correcta de tocar algo
 * que NO es Compose (aquí, la ventana de Android).
 */
@Composable
private fun IconosBarraDeEstado(modoOscuro: Boolean) {
    val view = LocalView.current
    if (!view.isInEditMode) {   // en las @Preview no hay ventana real
        SideEffect {
            val window = (view.context as Activity).window
            WindowCompat.getInsetsController(window, view).apply {
                isAppearanceLightStatusBars = !modoOscuro
                isAppearanceLightNavigationBars = !modoOscuro
            }
        }
    }
}
