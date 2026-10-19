# Proyecto 02 — Layouts y componentes básicos

> Apuntes: [2. Layouts y componentes básicos](../../02-layouts-componentes.md)

La app es un `Scaffold` con la `BarraNavegacionInferior` de los apuntes y cuatro pestañas.

## Dónde está cada apartado

Todo el código está en `app/src/main/java/com/pmdm/ud3/layouts/`.

| Apartado | Pestaña | Fichero | Qué contiene |
|----------|---------|---------|--------------|
| 2.1 Layouts | Layouts | `pantallas/PantallaLayouts.kt` | `EjemploColumn`, `EjemploRow`, `EjemploBox`, `EjemploWeight` y dos **exploradores** interactivos de `Arrangement` y `Alignment` |
| 2.2 Listas Lazy | Listas | `pantallas/PantallaListas.kt` | `ListaDeContactos` (100 elementos), `ListaConSecciones`, `CarruselDePeliculas`, `GaleriaDeImagenes` (Fixed / Adaptive) |
| 2.3 Componentes Material 3 | Componentes | `pantallas/PantallaComponentes.kt` | Textos, todos los botones, `TextField`, `Card`, `TopAppBar`, FAB, `AlertDialog`, Checkbox, Switch, Slider, progreso y chips |
| 2.3 NavigationBar · 2.4 Scaffold | (toda la app) | `AppLayouts.kt` | `Scaffold` + `BarraNavegacionInferior` + `when` para cambiar de pestaña |
| 2.5 Lista de películas | Películas | `pantallas/PantallaListaPeliculas.kt` | `PantallaListaPeliculas` + `PeliculaItem`, con el estado vacío |
| Datos | — | `datos/Peliculas.kt` | `data class Pelicula` y los datos de ejemplo |

## Experimentos

1. **Arrangement.** En la pestaña *Layouts*, prueba todas las opciones del explorador. ¿En qué se diferencian `SpaceEvenly` y `SpaceAround`?
2. **Lazy solo dibuja lo visible.** En *Listas › LazyColumn*, abre Logcat (filtro `UD3`) y haz scroll: los contactos se componen a medida que aparecen, no los 100 de golpe.
3. **GridCells.** En *Listas › LazyVerticalGrid*, activa *Adaptive* y gira el móvil.
4. **Estado vacío.** En *Películas*, pulsa la papelera: se ve la rama `if (peliculas.isEmpty())`. Después pulsa «Añadir».
5. **paddingValues.** En `PantallaListaPeliculas`, quita `.padding(paddingValues)` del `LazyColumn`. ¿Qué le pasa a la primera película?
6. **weight.** En `PeliculaItem`, quita `Modifier.weight(1f)` de la `Column` de texto y observa el botón ⋮.
7. **TextField.** En *Componentes*, escribe en un campo y fíjate en que el otro cambia también: comparten el mismo estado.
