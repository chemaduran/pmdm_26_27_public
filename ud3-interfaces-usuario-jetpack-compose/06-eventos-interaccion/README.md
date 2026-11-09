# Proyecto 06 — Eventos e interacción del usuario

> Apuntes: [6. Eventos e interacción del usuario](../../06-eventos-interaccion.md)

Un menú con una demo por apartado, conectado con Navigation (lección 4). En las demos de clicks y gestos hay un **registro de eventos** que muestra qué lambda se ha disparado (`onClick`, `onLongClick`, `onDoubleTap`…). En los apuntes esto se hacía con `println`.

## Dónde está cada apartado

Todo el código está en `app/src/main/java/com/pmdm/ud3/eventos/`.

| Apartado | Fichero | Qué contiene |
|----------|---------|--------------|
| 6.1 – 6.2 Clicks | `pantallas/PantallaClicks.kt` | `clickable`, sin ripple, `BotonConEstado` (`interactionSource`), `combinedClickable` |
| 6.3 Gestos | `pantallas/PantallaGestos.kt` | `EjemploGestos` (arrastrar), `detectTapGestures`, `ImagenConZoom` |
| 6.4 Swipe | `pantallas/PantallaSwipe.kt` | `ListaConSwipe` con `SwipeToDismissBox` |
| 6.5 Scroll | `pantallas/PantallaScroll.kt` | `ContenidoConScroll`, scroll programático y detección de posición con `derivedStateOf` |
| 6.6 Teclado y foco | `pantallas/PantallaTeclado.kt` | `FormularioConFoco` y un campo por cada `KeyboardType` |
| 6.7 Pull to refresh | `pantallas/PantallaPullToRefresh.kt` | `ListaConPullRefresh` con `PullToRefreshBox` |
| 6.8 Accesibilidad | `pantallas/PantallaAccesibilidad.kt` | `contentDescription`, `semantics`, área táctil de 48dp, contraste |
| 6.9 Ejemplo completo | `pantallas/ListaInteractivaCompleta.kt` | Búsqueda + selección múltiple con *long press* |

## Diferencias con los apuntes

- **Swipe (6.4).** En Material 3 1.4, `rememberSwipeToDismissBoxState(confirmValueChange = …)` está **obsoleto**. El proyecto usa el parámetro `onDismiss` de `SwipeToDismissBox`. Además, `onEliminar` recibe el **elemento** y no su índice, porque los índices cambian al borrar. Los dos motivos están explicados en el código.
- **Lista interactiva (6.9).** El código para alternar la selección se ha extraído a una función `alternar()`, porque en los apuntes se repite tres veces.

## Experimentos

1. **combinedClickable (6.2).** Haz click, mantén pulsado y haz doble tap sobre la caja: mira el registro.
2. **onPress (6.3).** En la zona naranja, mantén el dedo y suelta: aparecen «Presionando…» y «Soltado».
3. **Zoom (6.3).** En el emulador, mantén **Ctrl** y arrastra con el ratón para simular dos dedos.
4. **Scroll (6.5).** Baja en la lista y observa cuándo aparece el botón «subir» (`firstVisibleItemIndex > 3`).
5. **Teclado (6.6).** Recorre el formulario usando solo el botón «→» del teclado. Fíjate en el teclado distinto que abre cada `KeyboardType`.
6. **Accesibilidad (6.8).** Activa **TalkBack** y recorre la pantalla de accesibilidad: compara la valoración agrupada con la no agrupada.
7. **Selección (6.9).** Mantén pulsado un elemento, selecciona varios con clicks normales y bórralos.
