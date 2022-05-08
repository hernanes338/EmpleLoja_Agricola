package com.aureliohernandez.EmpleLoja_Agricola;

/**
 * @class Clase que permite almacenar las URL utilizadas en las diferentes llamadas
 * a la base de datos
 */

public class URLManagement {

    // Ruta base (IP del equipo que ejecuta el proyecto + ruta donde se encuentran los archivos PHP de configuracion
    public static final String BASE_URL = "http://192.168.0.25/EmpleLoja_Agricola/php_scripts/";

    public static final String URL_LOGIN = BASE_URL + "login.php";

    public static final String URL_SIGNUP = BASE_URL + "signup.php";

    public static final String URL_USER_DETAILS = BASE_URL + "fetch_user_details.php";
}
