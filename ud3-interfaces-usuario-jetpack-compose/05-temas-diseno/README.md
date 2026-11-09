# Proyecto 05 — Temas y diseño visual

> Apuntes: [5. Temas y diseño visual](../../05-temas-diseno.md)

En la pantalla de inicio cambias los **ajustes del tema** (modo oscuro, colores dinámicos y paleta). Después, entra en cada demo: todo se adapta, porque las pantallas leen `MaterialTheme` en lugar de usar colores fijos. El icono ☀/🌙 de la barra superior es el «toggle manual de tema» del apartado 5.5. La navegación entre demos usa lo aprendido en la lección 4.

## Dónde está cada apartado

Todo el código está en `app/src/main/java/com/pmdm/ud3/temas/`.

| Apartado | Fichero | Qué contiene |
|----------|---------|--------------|
| 5.2 Colores | `ui/theme/Color.kt` | Las paletas «Azul y naranja» y «Naturaleza» |
| 5.2 Tema y colores dinámicos | `ui/theme/Theme.kt` | `EsquemaClaro` / `EsquemaOscuro`, `MiAppTheme(modoOscuro, coloresDinamicos)`, `NaturalezaTheme` |
| 5.3 Tipografía | `ui/theme/Type.kt` + `res/font/*.ttf` | `Montserrat`, `SourceCodePro`, `MiTipografia` |
| 5.4 Formas | `ui/theme/Shape.kt` | `MisFormas` |
| 5.5 Modo oscuro | `AppTemas.kt` | Estado de los ajustes elevado por encima del tema + toggle en la `TopAppBar` |
| 5.2 Roles de color | `pantallas/PantallaPaleta.kt` | Cada rol con su par `on…` y su valor hexadecimal |
| 5.3 | `pantallas/PantallaTipografia.kt` | La escala tipográfica: ★ = estilo redefinido con Montserrat |
| 5.4 | `pantallas/PantallaFormas.kt` | Formas del tema, `CircleShape`, `CutCornerShape` |
| 5.6 | `pantallas/PantallaUsoColores.kt` | `TarjetaCorrecta` ✅ vs. `TarjetaIncorrecta` ❌ y las previews claro/oscuro |
| 5.7 Responsivo | `pantallas/PantallaResponsiva.kt` | `BoxWithConstraints`, `PantallaAdaptativa`, `ContenidoConPaddingAdaptativo` |
| 5.9 Ejemplo completo | `pantallas/PantallaNaturaleza.kt` | `AppNaturaleza` dentro de `NaturalezaTheme` (temas anidados) |

## Experimentos

1. **Roles de color.** Abre «Roles de color» y cambia el modo oscuro y la paleta. Los nombres no cambian; los colores, sí.
2. **Colores dinámicos (Android 12+).** Cambia el fondo de pantalla del móvil, activa «Colores dinámicos» y vuelve a «Roles de color».
3. **Colores a fuego (5.6).** Entra en «Usar el tema bien» y activa el modo oscuro: la tarjeta incorrecta se queda blanca.
4. **Formas (5.4).** En `Shape.kt`, cambia `medium` a `RoundedCornerShape(0.dp)`: todas las `Card` de la app pasan a tener esquinas rectas.
5. **Tipografía (5.3).** En `Type.kt`, cambia `bodyLarge` a `FontFamily.Serif` y fíjate en qué textos cambian.
6. **Responsivo (5.7).** Entra en «Diseño responsivo» y **gira el móvil**: en horizontal hay más de 840dp de ancho y aparecen lista y detalle. Mira también las dos previews (móvil y tablet).
7. **Material Theme Builder (5.8).** Genera una paleta en <https://m3.material.io/theme-builder>, cópiala en `Theme.kt` como tercera opción de `Paleta` y añádela a `TemaSeleccionado`.

> Las fuentes Montserrat y Source Code Pro tienen licencia **SIL Open Font License**: se pueden incluir en apps.
