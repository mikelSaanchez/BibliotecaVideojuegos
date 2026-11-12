# Biblioteca de videojuegos

Proyecto basado en la estructura que ya tenías: paquetes `DTO`, `DAO`, `repository`, `service` y `controlador`, más vistas Thymeleaf. Se han conservado tus nombres de clases, las rutas y los estilos existentes, y se han añadido las pantallas y capas que faltaban para préstamos.

## Antes de arrancar

1. Ejecuta `biblioteca_videojuegos.sql` en MySQL Workbench para crear y rellenar la base de datos `biblioteca`.
2. El script empieza borrando y creando de nuevo `biblioteca`; guarda antes cualquier dato de esa base que quieras conservar.
3. Si tus credenciales MySQL son distintas, cámbialas en `src/main/resources/application.properties`.

## Arrancar en Windows

Desde la carpeta del proyecto, ejecuta:

```bat
mvnw.cmd spring-boot:run
```

Después abre `http://localhost:8080`.

## Qué incluye

- Inicio con los cuatro contadores.
- Listados y altas de videojuegos y socios, con filtros y validaciones.
- Registro de préstamos con las reglas RN-1 a RN-5 y plazo de 7 o 15 días.
- Listado filtrable de préstamos, estado y días de retraso.
- Devolución, cálculo de multa y actualización transaccional de la copia dañada.
- Mensajes de éxito y error en las vistas, usando el fragmento existente.


