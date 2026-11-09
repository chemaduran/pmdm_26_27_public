package com.pmdm.ud3.navegacion.navegacion

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.toRoute
import com.pmdm.ud3.navegacion.datos.Pelicula
import com.pmdm.ud3.navegacion.datos.peliculasIniciales
import com.pmdm.ud3.navegacion.pantallas.PantallaAgregar
import com.pmdm.ud3.navegacion.pantallas.PantallaCreditos
import com.pmdm.ud3.navegacion.pantallas.PantallaDetalle
import com.pmdm.ud3.navegacion.pantallas.PantallaInicio
import com.pmdm.ud3.navegacion.pantallas.PantallaLista
import com.pmdm.ud3.navegacion.pantallas.PantallaLogin
import com.pmdm.ud3.navegacion.pantallas.PantallaPerfil
import kotlinx.coroutines.launch

/**
 * EL CORAZÓN DE LA LECCIÓN 4: aquí está TODA la navegación de la app.
 *
 * Mapa de pantallas (grafo de navegación):
 *
 *   login ──entrar (popUpTo login, inclusive)──▶ inicio
 *                                                 │
 *        ┌──────────── barra inferior / menú lateral ───────────┐
 *        ▼                        ▼                              ▼
 *     inicio ──▶ lista?filtro=X ──▶ detalle/{id} ──▶ detalle/{otro id}
 *                    │
 *                    └──▶ agregar ──guardar/cancelar (popBackStack)──▶ lista
 *
 *     perfil ──cerrar sesión (popUpTo todo el grafo)──▶ login
 *     menú lateral ──▶ Creditos(autor, curso)   ← ruta type-safe
 *
 * Las pantallas NO conocen el NavController: reciben lambdas (onVolver, onPeliculaClick...)
 * y es AQUÍ donde decidimos a dónde lleva cada una. Así las pantallas son reutilizables
 * y se pueden previsualizar sin navegación.
 */
@Composable
fun PeliculasApp() {
    // Apartado 4.2, paso 2: el NavController gestiona la pila de pantallas (back stack)
    val navController = rememberNavController()

    // Datos de ejemplo (en una app real, vendrían del ViewModel).
    // mutableStateListOf: una lista observable; al añadir, Compose recompone.
    val peliculas = remember { mutableStateListOf(*peliculasIniciales.toTypedArray()) }

    // Observar la ruta actual para resaltar el item correcto de la barra y del menú.
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    // "lista?filtro={filtro}" → "lista" (nos quedamos con la parte anterior a "?")
    val rutaActual = navBackStackEntry?.destination?.route?.substringBefore("?")
    val esPantallaPrincipal = SeccionPrincipal.entries.any { it.ruta.substringBefore("?") == rutaActual }

    // Apartado 4.5 — Menú lateral (Drawer)
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val abrirMenu: () -> Unit = { scope.launch { drawerState.open() } }

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = esPantallaPrincipal,   // en login/detalle no se puede abrir deslizando
        drawerContent = {
            ModalDrawerSheet {
                Text(
                    "🎬 Películas App",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.headlineSmall
                )
                HorizontalDivider()
                SeccionPrincipal.entries.forEach { seccion ->
                    NavigationDrawerItem(
                        label = { Text(seccion.titulo) },
                        icon = { Icon(seccion.icono, contentDescription = null) },
                        selected = rutaActual == seccion.ruta.substringBefore("?"),
                        onClick = {
                            navController.navegarASeccion(seccion.ruta)
                            scope.launch { drawerState.close() }
                        },
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )
                }
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                NavigationDrawerItem(
                    label = { Text("Créditos (ruta type-safe)") },
                    icon = { Icon(Icons.Default.Info, contentDescription = null) },
                    selected = false,
                    onClick = {
                        // Type-safe: navegamos con un OBJETO, no con un String
                        navController.navigate(Creditos(autor = "Profesorado de PMDM", curso = "2026-27"))
                        scope.launch { drawerState.close() }
                    },
                    modifier = Modifier.padding(horizontal = 12.dp)
                )
                NavigationDrawerItem(
                    label = { Text("Cerrar sesión") },
                    icon = { Icon(Icons.AutoMirrored.Filled.Logout, contentDescription = null) },
                    selected = false,
                    onClick = {
                        navController.cerrarSesion()
                        scope.launch { drawerState.close() }
                    },
                    modifier = Modifier.padding(horizontal = 12.dp)
                )
            }
        }
    ) {
        Scaffold(
            bottomBar = {
                Column {
                    // Panel didáctico: muestra en directo el contenido del back stack
                    PanelBackStack(
                        navController = navController,
                        modifier = if (esPantallaPrincipal) Modifier else Modifier.navigationBarsPadding()
                    )
                    // La barra inferior solo aparece en las pantallas principales
                    if (esPantallaPrincipal) {
                        NavigationBar {
                            SeccionPrincipal.entries.forEach { seccion ->
                                NavigationBarItem(
                                    icon = { Icon(seccion.icono, contentDescription = seccion.titulo) },
                                    label = { Text(seccion.titulo) },
                                    selected = rutaActual == seccion.ruta.substringBefore("?"),
                                    onClick = { navController.navegarASeccion(seccion.ruta) }
                                )
                            }
                        }
                    }
                }
            }
        ) { paddingValues ->
            NavHost(
                navController = navController,
                startDestination = Rutas.LOGIN,     // Pantalla inicial
                modifier = Modifier
                    .padding(paddingValues)
                    .consumeWindowInsets(paddingValues)
            ) {
                // ---------- LOGIN ----------
                composable(Rutas.LOGIN) {
                    PantallaLogin(
                        onEntrar = {
                            // Típico login → pantalla principal: quitamos el login de la pila
                            // para que el botón "atrás" no vuelva a él (sale de la app).
                            navController.navigate(Rutas.INICIO) {
                                popUpTo(Rutas.LOGIN) { inclusive = true }
                            }
                        }
                    )
                }

                // ---------- INICIO ----------
                composable(Rutas.INICIO) {
                    PantallaInicio(
                        onAbrirMenu = abrirMenu,
                        onVerLista = { filtro -> navController.navigate(Rutas.lista(filtro)) },
                        onNavegarTresVeces = { usarSingleTop ->
                            // Simula a un usuario que pulsa 3 veces muy rápido el mismo botón
                            repeat(3) {
                                navController.navigate(Rutas.lista()) {
                                    launchSingleTop = usarSingleTop
                                }
                            }
                        }
                    )
                }

                // ---------- LISTA (argumento opcional) ----------
                composable(
                    route = Rutas.LISTA,
                    arguments = listOf(
                        navArgument("filtro") {
                            type = NavType.StringType
                            defaultValue = ""            // Valor por defecto
                            nullable = true
                        }
                    )
                ) { backStackEntry ->
                    val filtro = backStackEntry.arguments?.getString("filtro") ?: ""
                    val peliculasMostradas =
                        if (filtro.isEmpty()) peliculas else peliculas.filter { it.genero == filtro }

                    PantallaLista(
                        peliculas = peliculasMostradas,
                        filtro = filtro,
                        onPeliculaClick = { id -> navController.navigate(Rutas.detalle(id)) },
                        onAgregar = { navController.navigate(Rutas.AGREGAR) },
                        onQuitarFiltro = {
                            // Sustituimos esta lista filtrada por la lista sin filtro
                            navController.navigate(Rutas.lista()) {
                                popUpTo(Rutas.LISTA) { inclusive = true }
                            }
                        },
                        onAbrirMenu = abrirMenu
                    )
                }

                // ---------- DETALLE (argumento obligatorio) ----------
                composable(
                    route = Rutas.DETALLE,
                    arguments = listOf(navArgument("peliculaId") { type = NavType.IntType })
                ) { backStackEntry ->
                    val id = backStackEntry.arguments?.getInt("peliculaId") ?: 0
                    val pelicula = peliculas.find { it.id == id }

                    if (pelicula != null) {
                        PantallaDetalle(
                            pelicula = pelicula,
                            onVolver = { navController.popBackStack() },
                            onSiguiente = {
                                navController.navigate(Rutas.detalle(siguienteId(peliculas, id)))
                            },
                            onVolverALista = {
                                // Volver a un destino específico, quitando todos los detalles de encima
                                navController.popBackStack(Rutas.LISTA, inclusive = false)
                            }
                        )
                    }
                }

                // ---------- AGREGAR ----------
                composable(Rutas.AGREGAR) {
                    PantallaAgregar(
                        onGuardar = { titulo, anio, sinopsis ->
                            val nuevoId = (peliculas.maxOfOrNull { it.id } ?: 0) + 1
                            peliculas.add(Pelicula(nuevoId, titulo, anio, sinopsis))
                            navController.popBackStack()
                        },
                        onCancelar = { navController.popBackStack() }
                    )
                }

                // ---------- PERFIL ----------
                composable(Rutas.PERFIL) {
                    PantallaPerfil(
                        onAbrirMenu = abrirMenu,
                        onCerrarSesion = { navController.cerrarSesion() }
                    )
                }

                // ---------- CRÉDITOS (ruta type-safe) ----------
                // composable<Tipo> en vez de composable("string"), y toRoute() en vez de arguments
                composable<Creditos> { backStackEntry ->
                    val creditos: Creditos = backStackEntry.toRoute()
                    PantallaCreditos(
                        creditos = creditos,
                        onVolver = { navController.popBackStack() }
                    )
                }
            }
        }
    }
}

/**
 * Navegación de la barra inferior / menú lateral (apartado 4.5).
 *
 * En los apuntes se usa popUpTo(navController.graph.findStartDestination().id).
 * Aquí la "start destination" es el LOGIN, que ya no está en la pila (lo quitamos al entrar),
 * así que hacemos popUpTo(INICIO): la pantalla raíz real tras iniciar sesión.
 */
private fun NavController.navegarASeccion(ruta: String) {
    navigate(ruta) {
        // Evitar acumular destinos en el back stack
        popUpTo(Rutas.INICIO) {
            saveState = true      // guarda el estado de las pantallas que quitamos...
        }
        launchSingleTop = true    // no duplicar si ya estamos en esa sección
        restoreState = true       // ...y lo recupera al volver a esa sección
    }
}

/** Vuelve al login vaciando TODA la pila: tras cerrar sesión, "atrás" no debe volver a la app. */
private fun NavController.cerrarSesion() {
    navigate(Rutas.LOGIN) {
        popUpTo(graph.id) { inclusive = true }
    }
}

/** Id de la siguiente película de la lista (vuelve a la primera al llegar al final). */
private fun siguienteId(peliculas: List<Pelicula>, idActual: Int): Int {
    val indice = peliculas.indexOfFirst { it.id == idActual }
    return peliculas[(indice + 1) % peliculas.size].id
}
