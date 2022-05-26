# Proyecto EmpleLoja Agrícola

## Nota Importante

Se puede encontrar el archivo .apk resultado del proyecto en la ruta EmpleLoja_Agricola\app\build\outputs\apk\debug. Sin embargo la aplicacion no podra ser utilizada ya que es necesario introducir via codigo la direccion IP de la maquina que ejecutara la aplicacion.

Se recomienda seguir los siguientes pasos para generar el archivo .apk con la IP que permita el correcto funcionamiento de la aplicacion:

1. Editar el archivo URLManagement.java en la ruta EmpleLoja_Agricola\app\src\main\java\com\aureliohernandez\EmpleLoja_Agricola sustituyendo en la linea 11 el valor de la IP por la direccion IP propia
2. En el IDE Android Studio, en la barra superior hacer clic en Build -> Make Project
3. Todavia en el IDE Android Studio, Build -> Build Bundle(s) / APK (s) -> Build APK(s)
4. Una vez generada la APK, en la parte inferior derecha del IDE hacer clic en Locate para abrir la carpeta donde se ha generado el archivo .apk

## Introduccion

Se trata de un proyecto pensado para solucionar la problemática de la desconexión entre ofertantes y demandantes de empleo en el sector agrario dentro de la comarca de Loja.

El resultado final del proyecto será una aplicación que permitirá por una parte a los ofertantes de empleo publicar los detalles y condiciones de sus ofertas de trabajo, y por otra, a los demandantes de empleo la posibilidad de contactar con los ofertantes de empleo para llegar a un acuerdo con ellos acerca del puesto de trabajo en concreto.

## Objetivos del proyecto

* Diseñar una interfaz gráfica de usuario para dispositivos móviles
* Implementar un sistema de creación de usuarios basado en roles (demandante/ofertante)
* Configurar el sistema de acceso a la aplicación por parte de los usuarios
* Implementar la conexión con una base de datos centralizada para registrar ofertas/demandas de trabajo
* Implementar un sistema de comunicación entre usuarios

## Manuales

A continuacion se presentan los diferentes manuales necesarios para poder instalar, configurar y utilizar la aplicacion movil a traves de un emulador Android en un entorno de red local.

### Instalación
[Manual de instalación](https://github.com/hernanes338/EmpleLoja_Agricola/blob/master/materials/manuals/Manual%20de%20Instalacion.pdf)

### Configuración
[Manual de configuración](https://github.com/hernanes338/EmpleLoja_Agricola/blob/master/materials/manuals/Manual%20de%20Configuracion%20y%20Administracion.pdf)

### Uso de la aplicación
[Manual de usuario](https://github.com/hernanes338/EmpleLoja_Agricola/blob/master/materials/manuals/Manual%20de%20Usuario.pdf)