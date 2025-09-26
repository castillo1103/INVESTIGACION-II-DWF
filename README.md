# 🛠️ API REST Ferretería

## 📽️​ Enlaces de videos:

Videos exposición y explicación de la API:

https://drive.google.com/drive/folders/1YiL4SpWn6DkCGDFms_G58Rhh3wzoxp1N


## 📌 Descripción
Esta API REST fue desarrollada en **Java con Spring Boot** para gestionar productos de una ferretería. Su objetivo es demostrar el uso de un **CRUD completo** (crear, leer, actualizar y eliminar productos), el acceso a datos mediante **H2 Database en memoria**, y la aplicación del paradigma **Event-Driven** para la ejecución de eventos internos y tareas en segundo plano.  

Además, la API fue diseñada para ser **contenedorizada en Docker** y desplegada en **Kubernetes**, aplicando buenas prácticas de orquestación (readiness/liveness probes, réplicas, ConfigMaps y Secrets).  

---

## ⚙️ Características principales
- **CRUD de Productos**:
  - Crear, listar, actualizar y eliminar productos.
  - Los datos se almacenan en **H2 Database** en memoria.  

- **Paradigma Event-Driven**:
  - Al crear un producto → se dispara un evento `ProductoCreadoEvent`.  
  - Si el stock baja de un umbral → se dispara un evento `StockBajoEvent`.  
  - Si el stock llega a 0 → se dispara un evento `ProductoAgotadoEvent`.  
  - Los eventos se manejan con **listeners asíncronos** (`@Async + @EventListener`), lo que permite ejecutar tareas en segundo plano (ejemplo: simulación de envío de correos o notificaciones).  

- **Manejo de errores centralizado**:
  - Respuestas estandarizadas para errores comunes ( `404`, `500`).  
  - Mensajes claros y con estructura JSON.  

- **Consumo con Postman**:
  - Endpoints RESTful accesibles en formato JSON.  
  - Ejemplos de pruebas: creación de producto, actualización de stock, validación de errores.  

---

## 🗂️ Endpoints principales

| Método   | Endpoint               | Descripción |
|----------|------------------------|-------------|
| `GET`    | `/api/productos`       | Listar todos los productos |
| `GET`    | `/api/productos/{id}`  | Obtener producto por ID |
| `POST`   | `/api/productos`       | Crear un nuevo producto |
| `PUT`    | `/api/productos/{id}`  | Actualizar un producto existente |
| `DELETE` | `/api/productos/{id}`  | Eliminar un producto |

---

## 📊 Ejemplo de JSON

### Crear producto
```json
POST /api/productos
{
  "nombre": "Martillo",
  "precio": 12.50,
  "stock": 15
}
````
---

## ⚡ Event-Driven en acción

- **Evento `ProductoCreadoEvent`**: se registra en logs y simula notificación al proveedor.  
- **Evento `StockBajoEvent`**: alerta en logs cuando el stock es menor al mínimo definido.  
- **Evento `ProductoAgotadoEvent`**: genera alerta crítica en logs cuando el stock llega a 0.  

Gracias a la anotación `@Async`, estos procesos ocurren **en segundo plano**, sin bloquear la respuesta HTTP al cliente.  

---

## 🗄️ Base de Datos H2

La API utiliza **H2 Database en memoria** para el almacenamiento temporal de datos.  
Al iniciar la aplicación, se genera automáticamente la tabla de **productos**.  

Se puede acceder a la consola de H2 en:  

http://localhost:8080/h2-console

📌 **Configuración de acceso**:
- **JDBC URL:** `jdbc:h2:file:/app/data/ferreteria-db`  
- **User:** `sa`  
- **Password:** *(vacío)* 
