package com.aureliohernandez.EmpleLoja_Agricola.Model;

/**
 * @class Clase que permite crear objetos de tipo Usuario
 */

public class User {
    String name, surname, email, password;
    int user_id, phone, role_id; // role_id deberia ser un objeto de tipo Role

    /**
     * Constructor para almacenar los detalles de un User en/desde un archivo SharedPreferences.
     * @param user_id
     * @param name
     * @param surname
     * @param phone
     * @param email
     * @param password
     * @param role_id
     */
    public User(int user_id, String name, String surname, int phone, String email, String password, int role_id) {
        this.user_id = user_id;
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.role_id = role_id;
    }

    /**
     * Constructor para crear una cuenta de usuario insertandola en la base de datos
     * @param name
     * @param surname
     * @param phone
     * @param email
     * @param password
     * @param role_id
     */
    public User(String name, String surname, int phone, String email, String password, int role_id) {
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.role_id = role_id;
    }

    /**
     * Constructor para realizar el login del usuario verificando los parametros en la base de datos
     *
     * @param email
     * @param password
     */
    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }
}
