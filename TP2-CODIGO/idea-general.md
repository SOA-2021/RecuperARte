# Idea general de la aplicacion

Para recuperarse del sedentarismo de la pandemia, decidimos crear una aplicacion
que permita a la gente (ya sean atletas o no) poder recuperarse. Para lograr
este objetivo, vamos a trackear el movimiento que realiza una persona, ya sea
caminando o corriendo, y luego daremos pautas que indiquen si su progreso es
bueno o malo.

## Estadisticas

Vamos a ofrecer dos tipos de estadisticas:

1. Cuantos metros y cuanto tiempo pasa caminando o corriendo por dia.
2. A que hora sale a correr y que dias lo hace.

Estos datos seran persistidos en almacenamiento secundario utilizando el motor
de bases de datos SQLite, el cual ya se encuentra embebido en el dispositivo.

## Sensores

Para obtener los metros que recorrer, vamos a utilizar el *acelerometro*. Con
este mismo podremos saber si la persona esta corriendo o caminando (lo cual sera
indicado por la velocidad).

A su vez, utilizaremos el *sensor de proximidad* para que cuando la aplicacion
este prendida, que verifique que el usuario tenga el smartphone en su bolsillo.
En caso contrario (que no este en su bolsillo), se generara una alerta sonora
para que no se lo olvide.

## Pantallas

Vamos a contar con un sistema de usuario y contraseña. Por lo tanto, en la
primera pantalla de la aplicacion, se debera mostrar al usuario la posibilidad
de iniciar sesion o de registrarse. En el caso de registrarse, pedimos usuario y
contraseña, la guardamos en persistencia, y listo.

En el caso de iniciar sesion, el proceso se realiza de a dos pasos:

- Autenticacion utilizando un factor externo: las dos opciones que considero
	mejores son: enviar un mail con un codigo aleatorio, o leer un codigo QR
	predeterminado.
- Autenticacion interna: una vez que se haya pasado la primer pantalla, se
	debera hacer el inicio de sesion tipico con usuario y contraseña. Este inicio
	de sesion se realizara golpeando a la API desarrollada por la catedra. Esta
	conexion con la API otorga un token que luego debe ser utilizado. Se debe
	poder renovar el token nuevamente llamando a la API.

Luego de iniciar sesion, tendremos una pantalla que muestra la actividad del dia
y (por peticion de las consignas) el estado de la bateria del smartphone.

Finalmente, desde la pantalla anterior se podra acceder al menu de estadisticas,
que mostrara las estadisticas especificadas previamente.

## Tareas background

La aplicacion debe registrar determinadas acciones en el servidor de la catedra.
Lo propuesto es registrar login, posible logout, y actividad de los sensores.

En cada conexion con el servidor, se debe validar el estado de la conexion de
Internet. En caso de que no haya conexion, se debe mostrar una alerta al usuario
indicando que no se pudo conectar con Internet.
En la primer pantalla debera indicar el estado de la bateria.
