# Proyecto 04 — Navegación

> Apuntes: [4. Navegación](../../04-navegacion.md)

La app de películas del apartado 4.6, ampliada para practicar todas las operaciones de la lección. Abajo hay un **panel con el back stack en directo**: muestra la pila de pantallas que guarda el `NavController` y la pantalla actual aparece resaltada.

Este proyecto añade `navigation-compose` y el plugin de **Kotlin Serialization** (necesario para las rutas type-safe).

## Grafo de navegación

```
login ──Entrar (popUpTo login, inclusive)──▶ inicio
inicio ──▶ lista?filtro=X ──▶ detalle/{id} ──Siguiente──▶ detalle/{otro id}
                │
                └──▶ agregar ──Guardar / Cancelar (popBackStack)──▶ lista
perfil ──Cerrar sesión (popUpTo todo el grafo)──▶ login
menú lateral ──▶ Creditos(autor, curso)   ← ruta type-safe
```

## Dónde está cada apartado

Todo el código está en `app/src/main/java/com/pmdm/ud3/navegacion/`.

| Apartado | Fichero | Qué contiene |
|----------|---------|--------------|
| 4.2 Paso 1: rutas | `navegacion/Rutas.kt` | `object Rutas` (strings), `Creditos` (`@Serializable`), `enum SeccionPrincipal` |
| 4.2 Paso 2: NavHost | `navegacion/PeliculasApp.kt` | **Toda la navegación**: `NavHost`, `composable(...)`, `navArgument`, barra inferior y drawer |
| 4.2 Paso 3: Activity | `MainActivity.kt` | `setContent { MiAppTheme { PeliculasApp() } }` |
| 4.3 popUpTo / launchSingleTop | `PeliculasApp.kt` + `pantallas/PantallaLogin.kt`, `PantallaInicio.kt` | Login que se quita de la pila; botones «×3 con / sin launchSingleTop» |
| 4.4 Argumentos | `PeliculasApp.kt` | Obligatorio (`detalle/{peliculaId}`) y opcional (`lista?filtro={filtro}`) |
| 4.5 Barra inferior y drawer | `PeliculasApp.kt` | `NavigationBar` + `ModalNavigationDrawer` |
| 4.6 Pantallas | `pantallas/PantallaLista.kt`, `PantallaDetalle.kt`, `PantallaAgregar.kt` | Las pantallas de los apuntes (stateless: reciben datos y lambdas) |
| Type-safe | `pantallas/PantallaPerfilYCreditos.kt` | `composable<Creditos>` + `toRoute()` |
| (didáctico) | `navegacion/PanelBackStack.kt` | El panel del back stack |

## Experimentos (mirando siempre el panel del back stack)

1. **Login.** Pulsa «Entrar» y después el botón **atrás** del sistema: sales de la app, no vuelves al login.
2. **Argumento opcional.** En Inicio, pulsa `navigate("lista?filtro=Drama")`. Quita el filtro con la ✕ del chip.
3. **Argumento obligatorio.** Abre una película y pulsa «Siguiente película» tres veces: se apilan los `detalle/…`. Prueba «Volver a la lista» frente a la flecha ←.
4. **launchSingleTop.** Usa los dos botones «×3» y pulsa atrás varias veces después de cada uno.
5. **Barra inferior.** Cambia entre Inicio, Películas y Perfil: la pila no crece. Ahora quita `launchSingleTop = true` de `navegarASeccion` y repite.
6. **Cerrar sesión.** Desde Perfil o desde el menú lateral, y después pulsa atrás.
7. **Type-safe.** En `PeliculasApp.kt`, quita el argumento `curso` de `navigate(Creditos(...))` y compila. Haz lo mismo con una ruta string (`"detalle/"`): esta sí compila, pero falla al ejecutarse.
8. Añade una pantalla nueva «Ajustes» al menú lateral, siguiendo los pasos 1–2 del apartado 4.2.
