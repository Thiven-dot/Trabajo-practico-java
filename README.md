# Trabajo-practico-java
Integrantes: Agustin Schmidt y Thiago Berg
Nombre del framework: Sistema de Gestion de biblioteca
Proyecto final desarrollado con arquitectura cliente-servidor mediante sockets, enrutamiento de solicitudes, control de acceso basado en roles y validacion de parametros.

este proyecto trata de un sistema de gestion de biblioteca donde simula el funcionamiento de una biblioteca digital que permite a los usuarios con su respectivo rol(cliente o administrador) consultar catalogo,solicitar o devolver prestamos y administar el inventario 

Lista tentativa de recursos y acciones
Libros: libros/agregra
        Libros/quitar
        Libro/listar
        Libro/buscar

Prestamos: Prestamos/registrar
           Prestamos/devolver
           Prestamo/misprestamos

usuarios: usuario/login
Como levantar el servidor:
1: primero tenes que tener el proyecto "proyectofinal" en el ide o ponerte en la carpeta raiz del repositorio
2:despues tenes que ejecutar la clase principal del servidor, en este caso main.java
ahi el servidor va a quedar escuchando las conexiones entrantes en el localhost

2:Como iniciar el cliente de la consola:
  1:con el servidor en ejecucuion, ejecuta la clase cliente con: ClienteConsola.java
  2:escribir las solicitudes por consola respetando el formato y presionar enter

  Protocolo de comunicacion:
  formato de solicitud:las solicitudes se envian como texto plano con la estructura: recurso/accion/parametro
  (si la accion no presisa parametro seria recurso/accion)

  Formato de respuesta 
  el servidor devuelve un mensaje de texto con el resultado o la descripcion del error capturado con las excepcionesdel framework (solicitudinvalidaexception,AccionNoEncontradaException y RecursoNoEncontradoException)

  Ejemplo de uso:
  1:auntenticacion
  solicitud: usuarios/login/admin/1234
  respuesta si es exitosa:< OK Hola, admin
  respuesta si es incorrecta: Error: Usuario o contraseña incorrectos

  2:Listar libros:
solicitud: libros/listar
Respuesta:OK No hay libros
(tenes que agregarlos)

3:Buscar libro
Solicitud: libros/buscar/Dune
Respuesta: Libro encontrado: OK Dune - Herbert (1965) [disponible]

4:Agregar libro (solo los bibliotecarios pueden)
Solicitud: libros/agregar/Dune/Herbert
Respuesta si es exitosa: OK Libro agregado: Dune
Respuesta si es rechazada:Error: Acceso denegado: solo BIBLIOTECARIO puede ejecutar esta acción.    (o tambien te puede decir que informacion te falta por ej:el año)

5:Borrar libro (solo bibliotecarios)
solicitud: libros/quitar/Dune
Respuesta si es valida: OK Libro quitado: Dune
Respuesta si es rechazada: Error: no existe el libro

Como escribir y registar un controlador nuevo:
para sumar un controlador nuevo en el proyecto(por ejemplo de prestamos) hay que hacer esto:
crear la clase usando el paquete app y las utilidades de gestionbiblioteca
despues implementa un metodo procesar para atender cada accion y poder validar los datos con validador, revisar la Sesion y mandar las excepciones que corresponden si es que hay error
y por ultimo lo tenes que registrar en Main.java donde creas la instancia del controlador pasandole la misma sesion y asociandolo en el Router al nombre del recurso(en este caso seria prestamo)

Opcionales Agregados:
no se agregaron opcionales


