# REST API de Transferencia de Archivos

Esta API realiza operaciones de tipo CRUD para el manejo de archivos sobre un directorio seleccionado a partir de las peticiones http recibidas. Si no te queda claro, sigue leyendo.

El cliente realiza peticiones http (GET, POST, PUT, DELETE) a la dirección del servidor. Este recoge la petición y actúa en consecuencia, envíando una respuesta al cliente. Esta respuesta contiene un código de estado y un cuerpo, que puede ser o una cadena de texto con un mensaje o, en el caso de una petición get para un recurso específico, dicho recurso.

## Uso de la API:
#### GET /imagenes:
El servidor devuelve un listado con los nombres de los archivos que se encuentran en el directorio utilizado como almacenamiento. 

#### GET /imagenes/{id}:
El servidor devuelve el archivo especificado. Aunque el parámetro se llame id, hace referencia al nombre completo del archivo. Lo devuelve como un string en base64. 

#### POST /subir:
El servidor revisa esta petición en búsqueda de dos cosas:
1. Que contenga un encabezado denominado "filename". El contenido de este encabezado será utilizado para denominar el archivo recibido.
2. Que contenga un parámetro denominado "file". El contenido de este parámetro será un Multipartfile.
Si cumple ambas condiciones, el servidor guardará este archivo en el directorio que se esté utilizando como almacenamiento.

#### DELETE /imagenes/{id}:
El servidor tratará de eliminar el archivo especificado.

## Interfaz:
Este servidor cuenta con una interfaz gráfica para el usuario. Esta interfaz permite modificar el directorio que se está utilizando para el almacenaje de las peticiones (de esta manera, si se desea, se podría utilizar incluso un sistema de almacenamiento externo). También muestra las peticiones que se han realizado desde que el servidor está levantado (tipo de petición, uri a la que se ha realizado, código de estado y fecha y hora de la misma). Por último, permite tanto la exportación de este historial de peticiones, como el abrir y revisar un archivo de historial de peticiones.

Toda esta interfaz ha sido realizada mediante javafx.

## Cifrado:
La exportación del historial de peticiones se hace cifrada, lo que pretende asegurar que no se puedam abrir estos archivos con otros programas. A través del botón de "Abrir historial", se habre una nueva ventana en la que se muestra el contenido del archivo seleccionado descifrado.

## Utilización:
Si quieres descargar este proyecto para poder estudiarlo, modificarlo, usarlo, o, simplemente porque sí, sigue estos pasos (si no utilizas Eclipse como IDE, esto puede variar):
1. Clona este repositorio en tu ordenador: git clone git<span/>@<span/>github.<span/>com:kasimxo/KaS-API-Server.git
2. Importa este proyecto en tu Eclipse (File>Import>General>Projects from Folder or Archive>Selecciona la carpeta base de este proyecto)
3. Actualiza el proyecto maven (Project> Update Maven Project)
4. Limpia el proyecto (Clic derecho sobre el proyecto>Run As>Maven clean)
5. Genera los recursos del proyecto (Clic derecho sobre el proyecto>Run As>Maven generate-sources)
6. Instala el proyecto (Clic derecho sobre el proyecto>Run As>Maven install)
7. En este punto ya debería estar listo para ser utilizado. Para ello haz clic derecho sobre el proyecto, Run As, Maven build. Si aparece una ventana que te pide un objetivo para la build (Goal), basta que introduzcas "spring-boot:run", o, si quieres ver los mensajes de error completos en caso de que surjan (muy recomendable), "spring-boot:run -e".

## Dependencias, framework y demás:
Llegados a este punto, esto bebe de todos lados. Spring-boot, mucho apache, javafx, stackoverflow y github. No te tomes demasiado en serio este código o te dará un parraque. Da gracias a que funcione.

## Cliente:
Para utilizar esta API, puedes crear tu propio cliente. Si quieres ver un cliente ya creado que es compatible con esta API, mira mi versión en git: 
https://github.com/kasimxo/KaS-API-Cliente-Maven


## Créditos:
Creado a partir de la guía de bezkoder, puedes consultar el proyecto base aquí: 
https://github.com/bezkoder/spring-boot-upload-multipart-files

