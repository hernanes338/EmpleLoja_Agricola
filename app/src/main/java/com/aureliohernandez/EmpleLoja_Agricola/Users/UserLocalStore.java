package com.aureliohernandez.EmpleLoja_Agricola.Users;

import android.content.Context;
import android.content.SharedPreferences;

import com.aureliohernandez.EmpleLoja_Agricola.Model.User;

/**
 * @class Clase que permite realizar distitnas operaciones de escritura y/o lectura sobre un
 * archivo SharedPreferences
 */

public class UserLocalStore {
    // Nombre del archivo SharedPreferences
    public static final String SP_NAME = "userDetails";
    SharedPreferences userLocalDatabase;

    public UserLocalStore(Context context) {
        userLocalDatabase = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }

    /**
     * @param user recibe un objeto de tipo User
     * Recoge los valores de todos los atributos del objeto user
     * y los almacena en el archivo SharedPreferences
     */
    public void storeUserData(User user) {
        SharedPreferences.Editor sharedPreferencesEditor = userLocalDatabase.edit();
        sharedPreferencesEditor.putInt("user_id", user.getUser_id());
        sharedPreferencesEditor.putString("name", user.getName());
        sharedPreferencesEditor.putString("surname", user.getSurname());
        sharedPreferencesEditor.putInt("phone", user.getPhone());
        sharedPreferencesEditor.putString("email", user.getEmail());
        sharedPreferencesEditor.putString("password", user.getPassword());
        sharedPreferencesEditor.putInt("role_id", user.getRole_id());
        sharedPreferencesEditor.commit();
    }

    /**
     * @return un objeto de tipo User
     * Recoge los valores almacenados en el archivo SharedPreferences
     * y los almacena en el objeto storedUser
     */
    public User getLoggedInUser() {
        int user_id = userLocalDatabase.getInt("user_id", 0);
        String name = userLocalDatabase.getString("name", "");
        String surname = userLocalDatabase.getString("surname", "");
        int phone = userLocalDatabase.getInt("phone", 0);
        String email = userLocalDatabase.getString("email", "");
        String password = userLocalDatabase.getString("password", "");
        int role_id = userLocalDatabase.getInt("role_id", 0);

        User storedUser = new User(user_id, name, surname, phone, email, password, role_id);

        return storedUser;
    }

    /**
     * @param loggedIn valor de la clave loggedIn del archivo SharedPreferences
     * Escribe true/false en la clave loggedIn para determinar si el usuario esta logueado o no
     */
    public void setUserLoggedIn(boolean loggedIn) {
        SharedPreferences.Editor sharedPreferencesEditor = userLocalDatabase.edit();
        sharedPreferencesEditor.putBoolean("loggedIn", loggedIn);
        sharedPreferencesEditor.commit();
    }


    /**
     * @return true si el usuario esta logueado. false si el usuario no esta logueado
     */
    public boolean getUserLogInStatus() {
        if (userLocalDatabase.getBoolean("loggedIn", false)) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * Metodo que elimina cualquier dato guardado en el archivo SharedPreferences
     */
    public void clearUserData() {
        SharedPreferences.Editor sharedPreferencesEditor = userLocalDatabase.edit();
        sharedPreferencesEditor.clear();
        sharedPreferencesEditor.commit();
    }
}
