# RecuperARte
------------------------------------------------------------------------------------------------------
Enunciado del Trabajo Práctico N°2 (Android)
------------------------------------------------------------------------------------------------------
El grupo de trabajo deberá diseñar e implementar una aplicación que funcione en un Smartphone con
Sistema Operativo Android, cuya temática este orientada al post Covid. En otras palabras, que presente
alguna funcionalidad que ayuden a las personas relacionadas con la post pandemia. Dicha aplicación
deberá hacer uso de servicios web provistos por un servidor que fue implementado por la catedra.
Contenidos del TP2
Para que el alumno cumpla con los contenidos mínimos y extendidos del TP2, se detallarán los dos puntos
que se basará para la evaluación. En el primero, se describe los puntos requeridos que deberá contener el
informe del TP. En el segundo se describen los ítems que deben cumplir la aplicación móvil para que
funcione en un Smartphone con el Sistema Operativo Android.
------------------------------------------------------------------------------------------------------
Con respecto al informe
------------------------------------------------------------------------------------------------------
Desarrollar el informe en formato paper. Agregando las secciones de encabezado, introducción, desarrollo,
conclusiones y bibliografía (en formato IEEE), formato CACIC. El formato paper solicitado se muestra en el
siguiente enlace:
https://www.dropbox.com/s/0hb8xp9a1phbskp/00_EstructuraPaper_cacic.doc?dl=0
El contenido que deberán tener dichas secciones se detalla a continuación
En el encabezado
1. Debe indicarse el nombre de la aplicación como título del paper
2. Indicar Nombres, Apellido y DNI de cada integrante del grupo. Así cómo también debe indicarse
el día de cursada y el número de grupo
3. Agregar un resumen de hasta 150 palabras cómo máximo
En Introducción
1. Introducción de la funcionalidad de la aplicación. En este punto se debe describir cual es utilidad
de la aplicación y que es lo que hace.
En desarrollo
1. Deben indicar la dirección web del repositorio GitHub
2. Deberá contener un diagrama funcional/navegación de las Activities.
3. Describir cómo se realizó la ejecución concurrentemente del programa. ¿Tuvo que utilizar algún
mecanismo de sincronización?
4. Describir cómo realizó la comunicación entre los componentes (Activites, Servicios, etc.).
5. ¿Qué técnica utilizó para la comunicación con el servidor (HttpConnection, Retrofit, etc) ?. Detalle
como lo implementó desde la generación de la solicitud al servidor hasta la recepción de la
respuesta.
6. Describir cómo se realizó la persistencia de los datos en la aplicación. sobre este punto, ¿qué pasa
cuando se cierra la aplicación?
7. Escribir un manual de usuario, en donde se describa como se utiliza la aplicación.
Conclusión
1. Comente acerca de los recaudos que tuvo que realizar, para que la aplicación sea tolerante a
fallos.
2. Durante el desarrollo ¿Surgieron problemas? ¿Cómo fueron resueltos? Detallen las lecciones
aprendidas durante el desarrollo.
Bibliografía
1. La bibliografía debe ser referenciada en formato IEEE
------------------------------------------------------------------------------------------------------
Con respecto a la implementación de la Aplicación Android
------------------------------------------------------------------------------------------------------
La entrega de la aplicación móvil debe cumplir con los siguientes requisitos:
1. El TP consistirá en desarrollar una aplicación para Android que involucre los diferentes conceptos
vistos en clase, utilizando el IDE de Android Studio y en lenguaje Java. No se aceptan otros tipos
de lenguaje, ni herramientas.
Versiones de Android Studio recomendadas con las que se puede trabajar son 3.5 o superior
2. Se debe generar un repositorio público en Github para la entrega del TP. El repositorio deberá
estar compuesto por los siguientes directorios:
• TP2-CODIGO: Se deberá colocar el proyecto de la aplicación de Android Studio, sin ningún
binario.
• TP2-EJECUTABLE: Se deberá guardar el apk de la aplicación (archivo ejecutable del
programa).
3. La aplicación deberá utilizar la autenticación de doble factor, a través de 2 Activities
• PRIMERA ACTIVITY: se debe aplicar uno de los siguientes métodos de autenticación, para
permitir pasar a la segunda Activity
▪ Autoenviar un código aleatorio por SMS al mismo Smartphone y que luego el
usuario ingrese este código en la primer Activity
▪ Enviar un código aleatorio a un mail del usuario y que luego el usuario ingrese
este código en la primer Activity
▪ Que el usuario deba ingresar un patrón de bloqueo
▪ Que desde otro smartphone se le envíe un SMS y luego cuando la aplicación
detecte que se recibió un SMS del otro Smartphone, entonces se pase a la otra
segunda Activity
▪ Que se escanee un código QR predeterminado
• SEGUNDA ACTIVTY: Se deberá implementar un sistema con Registro de usuario y Login
haciendo peticiones a un servidor, a través de la API desarrollada por la cátedra. Aclaración:
Se puede implementar el Registro de usuario en una Activity y el Login de usuario en otra.
4. Se deberá validar el estado de la conexión de internet, antes de enviar cada mensaje al servidor.
En caso de que no haya conexión, se deberá mostrar mensaje de error al usuario.
5. En el momento en que el usuario ingrese a la aplicación, se deberá informar por pantalla el estado
de carga de la batería del Smartphone.
6. Se deberá implementar alguna de las ejecuciones en Background vistas en clase.
7. La aplicación deberá utilizar un mecanismo de sincronización vistos en clase.
8. La aplicación deberá usar los datos del sensor acelerómetro o giroscopio, además de utilizar otro
sensor más de la clase SensorManager. En total deberá utilizar al menos dos sensores propios
del dispositivo móvil. Únicamente se aceptan sensores que pertenezcan a la clase
SensorManager, por lo que no se aceptan otros tipos de sensores.
9. Deberá registrar al menos dos tipos de métricas distintas y armar un listado por cada métrica. Por
ejemplo, la cantidad de los inicios de sesión en un rango de horas. La lista debe tener el total de
inicios de sesión, para este ejemplo.
Nombre métrica Valor Franja horaria
Cantidad de inicio de sesión 18 de 8 a 17 hrs
Cantidad de inicio de sesión 7 de 17 a 23 hrs
Esta información deberá ser guardada en forma persistente, usando por ejemplo
SharedPreferences o SQL Lite . Posteriormente los datos mostrados deberán volverse a cargar
en los listados, en cada inicio de sesión.
10. La aplicación deberá registrar en el servidor diferentes eventos ocurridos durante su ejecución,
como, por ejemplo: Actividad de sensores, Login, Ejecuciones en Background, Broadcast, etc.
Para ello se deberá utilizar la API desarrollada.
11. Al loguear un usuario el servidor de la cátedra le asigna un Token de acceso para su sesión. Ese
Token tiene una duración de 15 minutos. Pasado ese tiempo la sesión expira. En ese sentido, se
pide que implemente una estrategia en caso de que el Token expire, para que un usuario pueda
continuar usando la aplicación cuando supere dicho tiempo.
12. La aplicación deberá tolerar diferentes fallos:
• Pruebas de respuesta fluida.
• Liberación de recursos.
• Cambios de estado del ciclo de vida
• Errores de conexión.
13. Este punto es opcional. Si el grupo lo desea podrán implementar servicios externos como
• Firebase
• Servicios para saber el clima determinado lugar
• Estado del transito
• Cotización del dólar
• Desarrollar uno propio
