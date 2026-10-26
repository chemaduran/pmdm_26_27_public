# Proyecto 03 — Estado y gestión de datos en la UI

> Apuntes: [3. Estado y gestión de datos en la UI](../../03-estado-gestion-datos.md)

Cinco pestañas, una por bloque de la lección. Este proyecto añade las dependencias de **ViewModel** (`lifecycle-viewmodel-compose`) y de `collectAsStateWithLifecycle` (`lifecycle-runtime-compose`).

## Dónde está cada apartado

Todo el código está en `app/src/main/java/com/pmdm/ud3/estado/`.

| Apartado | Pestaña | Fichero | Qué contiene |
|----------|---------|---------|--------------|
| 3.1 – 3.2 | remember | `pantallas/PantallaRemember.kt` | `ContadorRoto` ❌, `Contador` ✅ y las tres formas de declarar estado |
| 3.3 – 3.4 | Saveable | `pantallas/PantallaSaveableDerivado.kt` | `remember` vs. `rememberSaveable` (gira el móvil), formulario y `ListaFiltrada` con `derivedStateOf` |
| 3.5 | Hoisting | `pantallas/PantallaHoisting.kt` | `CampoNombre` antes y después, `PantallaRegistro` + `FormularioRegistro` (stateless) |
| 3.6 | ViewModel | `tareas/TareasViewModel.kt`, `tareas/PantallaTareas.kt` | `Tarea`, `TareasUiState`, `TareasViewModel` y la pantalla de tareas |
| 3.7 | UiState | `peliculas/UiState.kt`, `PeliculasViewModel.kt`, `RepositorioPeliculas.kt`, `PantallaPeliculas.kt` | `sealed interface UiState` con Loading / Success / Error y un repositorio que simula la red |

## Experimentos

1. **El contador roto (3.1).** Pulsa `ContadorRoto`: no cambia. Explica por qué con tus palabras.
2. **Rotación (3.3).** En *Saveable*, sube los dos contadores y **gira el móvil** (en el emulador, con los botones de rotar). Solo sobrevive el de `rememberSaveable`.
3. **Salir de la composición.** Sube el contador de la pestaña *remember*, cambia de pestaña y vuelve: vale 0. Haz lo mismo con las tareas de *ViewModel*: siguen ahí. ¿Por qué?
4. **Hoisting (3.5).** En `Formulario`, el saludo «Hola, …» solo es posible porque el estado está en el padre. Intenta hacerlo con `CampoNombreConEstadoInterno`.
5. **Previews sin ViewModel.** Abre `PantallaTareas.kt` en vista Split: la preview funciona porque `PantallaTareasContenido` es *stateless*.
6. **UiState (3.7).** Activa «Simular fallo de red» y recarga: Loading → Error. Pulsa «Reintentar» con el interruptor apagado.
7. **sealed (3.7).** Añade `data object Vacio : UiState<Nothing>` a `UiState.kt` y compila: el `when` de `PantallaPeliculas` te obliga a tratarlo.
8. Repasa el diagrama de flujo *«¿Dónde pongo el estado?»* (3.8) y sitúa en él cada pestaña.
