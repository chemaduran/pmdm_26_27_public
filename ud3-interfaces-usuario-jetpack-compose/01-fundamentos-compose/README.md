# Proyecto 01 — Fundamentos de Jetpack Compose

> Apuntes: [1. Fundamentos de Jetpack Compose](../../01-fundamentos-compose.md)

Una sola pantalla con scroll. Cada **tarjeta** es un apartado de la lección con su código funcionando.

## Dónde está cada apartado

Todo el código está en `app/src/main/java/com/pmdm/ud3/fundamentos/`.

| Apartado | Fichero | Qué contiene |
|----------|---------|--------------|
| 1.1 Declarativo vs. imperativo | `apartados/A1_1_Declarativo.kt` | `Saludo(nombre)` y un botón que cambia el estado (no el texto) |
| 1.2 Funciones `@Composable` | `apartados/A1_2_Composables.kt` | `PantallaInicio` → `Cabecera`, `ListaDeElementos`, `PieDeSeccion`: el árbol de la sección 1.3 |
| 1.3 Recomposición | `apartados/A1_3_Recomposicion.kt` | El `Contador` de los apuntes, que muestra **cuántas veces se ejecuta cada composable** |
| 1.4 Estructura mínima | `MainActivity.kt` + `apartados/A1_4_EstructuraMinima.kt` | `ComponentActivity` → `setContent` → `MiAppTheme` → `Surface` |
| 1.5 Preview | `apartados/A1_5_Preview.kt` | Todas las variantes de `@Preview` (claro, oscuro, ancho fijo, pantalla completa) |
| 1.6 Modificadores | `apartados/A1_6_Modificadores.kt` | La cadena de los apuntes, «el orden importa» (intercambiable) y una galería |
| 1.7 dp y sp | `apartados/A1_7_Unidades.kt` | Texto en sp junto a una caja en dp, y la densidad real del dispositivo |
| 1.8 Tarjeta de perfil | `apartados/A1_8_TarjetaPerfil.kt` | `TarjetaPerfil` reutilizada con tres personas |

## Experimentos

1. **Recomposición (1.3).** Pulsa «Pulsar» varias veces. Solo sube el contador de ejecuciones de «Has pulsado X veces»; los otros se quedan en 1. Abre **Logcat** y filtra por `UD3` para ver el registro.
2. **Las reglas de @Composable (1.2).** Escribe una función normal (sin `@Composable`) que llame a `Cabecera()`. ¿Compila?
3. **Preview (1.5).** Abre `A1_5_Preview.kt` en vista **Split**. Activa el *Interactive mode* en una preview.
4. **Orden de modificadores (1.6).** Pulsa «Intercambiar el orden» y explica con tus palabras qué ha cambiado.
5. **sp vs. dp (1.7).** En el móvil, ve a *Ajustes › Pantalla › Tamaño de fuente*, súbelo al máximo y vuelve a la app.
6. **Layout Inspector.** Con la app en marcha, abre *Tools › Layout Inspector* y busca el árbol `PantallaInicio › Column › Cabecera…`.
7. **Reutilización (1.8).** Añade una cuarta `TarjetaPerfil` con tu nombre.
