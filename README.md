# DDI_Tarea_7_UI

### **Objetivo**
El objetivo de esta tarea es la realización de una serie de interfaces para practicar 
con distintos componentes y coger soltura a la hora de identificar, distribuir 
y añadir funcionalidades a los elementos presentes en las UI.

---

### **Desarrollo de la tarea**
En esta tarea se adjunta un video (*DDI_Tarea_7*) de la interfaz que tendréis que replicar.

---

### **Componentes**
La mayoría de componentes que aparecen en la UI los hemos visto o utilizado en UI anteriores. 
Si existe alguno que no conozcáis, podéis buscarlos a través de la documentación oficial 
de [Android Developers](https://developer.android.com/docs) y de [Material Design](https://m3.material.io/).

#### **Fotos parte superior**
- Utilizad **4 fotos de zapatillas** que elijáis libremente cada uno de vosotros.
- Se tienen que desplazar **horizontalmente**, para lo cual:
  - Podéis hacer uso de **desplazamiento lateral**.
  - O podéis intentar algo más complejo como el componente **Carousel** de Material 3.
- La UI se desplaza **verticalmente** y debe implementarse con `ScrollView` o `NestedScrollView`.

---

### **Funcionalidad de la aplicación**
- **Barra superior:** 
  - Debe mostrar las opciones que aparecen en el video.
  - Incluir el **botón de navegación** y replicar su comportamiento (el título aparece al desplazar verticalmente el scroll).
  
- **Botón favoritos (de la imagen):**
  - Al pulsar el icono de favoritos:
    - Activar/desactivar el icono.
    - Aumentar/disminuir el número de favoritos.

- **Otros botones:** 
  - Al pulsar cada uno debe aparecer un **toast** que diga:
    - "Botón Enviar pulsado"
    - "Botón Hacer una oferta pulsado"
    - "Botón Comprar pulsado"
  
- **Botón "Más" en descripción:**
  - En la descripción de las zapatillas, la palabra **"Más"** debe funcionar como botón.
  - Al pulsarlo, debe mostrar un **toast** con el mensaje: "Botón Más pulsado".

---

### **Temas, estilos, values, etc.**
1. **Archivos adjuntos:**
   - Se adjunta archivo `strings.xml` con algunos textos de la aplicación.
   - No se adjunta `dimen.xml`. Debéis definir las medidas utilizadas para que la UI sea lo más parecida a la del video.

2. **Recomendaciones:**
   - Usar archivos XML separados para organizar los distintos atributos: `dimen`, `styles`, `strings`, `menu`, `font`, etc.
   - Elegir una gama de colores armoniosa utilizando [Material Theme Builder](https://m3.material.io/theme-builder).
   - Seleccionar fuentes que sean:
     - Similares a las de la UI del video.
     - Legibles y adecuadas para una UI móvil.

---

## **Resumen**
- Replicar la interfaz mostrada en el video adjunto.
- Implementar las funcionalidades indicadas.
- Utilizar buenas prácticas en la organización de recursos XML.

https://github.com/user-attachments/assets/5c076725-ab2b-46b2-bf9f-3a830bcf0138

<resources>
  
    <string name="app_name">DDI_Tarea_7</string>
    <string name="zapatillas_title">Zapatillas El Ganso Verdes Talla 43 Nuevas!!</string>
    <string name="protection_text">68,95 € Protección al comprador incluidas</string>
    <string name="description_text">Zapatillas casual de la marca El Ganso en color verde disponibles en la talla 43.Se</string>
    <string name="armario_title">Distintivo Armario estrella</string>
    <string name="armario_text">Concedido a vendedores que suben artículos con frecuencia.</string>
    <string name="tasa_title">Distintivo Armario estrella</string>
    <string name="tasa_text">Nuestra protección al comprador implica una tasa que se añade a las compras realizadas a través del botón "Comprar".Esta protección incluye la política de reembolso.</string>

</resources>

![Características_dispositivo](https://github.com/user-attachments/assets/4d77f983-6dcd-4d75-b2e6-636de54103e6)
