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

    // Rutas a los archivos PHP de configuracion relcionados con operaciones del paquete Users
    public static final String URL_LOGIN = BASE_URL + "login.php";

    public static final String URL_SIGNUP = BASE_URL + "signup.php";

    public static final String URL_USER_DETAILS = BASE_URL + "fetch_user_details.php";

    // Rutas a los archivos PHP de configuracion relcionados con operaciones de la clase MainActivity
    public static final String URL_FETCH_JOB_OFFERS = "http://192.168.0.25/EmpleLoja_Agricola/php_scripts/fetch_job_offers.php";

    public static final String URL_FETCH_JOB_DEMANDS = "http://192.168.0.25/EmpleLoja_Agricola/php_scripts/fetch_job_demands.php";

    // Rutas a los archivos PHP de configuracion relcionados con operaciones del paquete Jobs
    public static final String URL_ADD_JOB_OFFER = "http://192.168.0.25/EmpleLoja_Agricola/php_scripts/add_job_offers.php";

    public static final String URL_ADD_JOB_DEMAND = "http://192.168.0.25/EmpleLoja_Agricola/php_scripts/add_job_demands.php";

    public static final String URL_FETCH_MY_JOB_OFFERS = "http://192.168.0.25/EmpleLoja_Agricola/php_scripts/fetch_my_job_offers.php";

    public static final String URL_FETCH_MY_JOB_DEMANDS = "http://192.168.0.25/EmpleLoja_Agricola/php_scripts/fetch_my_job_demands.php";

    public static final String URL_EDIT_JOB_OFFER = "http://192.168.0.25/EmpleLoja_Agricola/php_scripts/edit_job_offers.php";

    public static final String URL_EDIT_JOB_DEMAND = "http://192.168.0.25/EmpleLoja_Agricola/php_scripts/edit_job_demands.php";
}
