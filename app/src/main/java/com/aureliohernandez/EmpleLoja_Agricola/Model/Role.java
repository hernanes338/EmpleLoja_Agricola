package com.aureliohernandez.EmpleLoja_Agricola.Model;

/**
 * @class Clase que permite crear entidades de tipo Rol
 * Actualmente sin uso en la aplicacion
 */

public class Role {
    int role_id;
    String role_code, role_name;


    public Role (int role_id, String role_code, String role_name) {
        this.role_id = role_id;
        this.role_code = role_code;
        this.role_name = role_name;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public String getRole_code() {
        return role_code;
    }

    public void setRole_code(String role_code) {
        this.role_code = role_code;
    }

    public String getRole_name() { return role_name; }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }
}
