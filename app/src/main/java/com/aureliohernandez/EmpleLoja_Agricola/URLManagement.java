package com.aureliohernandez.EmpleLoja_Agricola;

/**
 * @class Clase que permite almacenar las URL utilizadas en las diferentes llamadas API
 * a la base de datos
 */

public class URLManagement {

    // Editar con la direccion IP del equipo que ejecuta la aplicacion (comando ipconfig a traves de la linea de comandos Windows)
    public static final String IP_ADDRESS = "192.168.0.25";

    // Ruta base (IP del equipo que ejecuta el proyecto + ruta donde se encuentran los archivos PHP de configuracion
    public static final String BASE_URL = "http://" + IP_ADDRESS + "/EmpleLoja_Agricola/php_scripts/";

    public static final String URL_LOGIN = BASE_URL + "login.php";

    public static final String URL_SIGNUP = BASE_URL + "signup.php";

    public static final String URL_USER_DETAILS = BASE_URL + "fetch_user_details.php";
}
