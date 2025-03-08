package com.proyectomarzo.flashmeet.models;

public class RegisterRequest {
    private String nombre;
    private String password;
    private String email;
    private String imagen1;
    private String imagen2;
    private String imagen3;
    private String imagen4;
    private String imagen5;
    private String imagen6;
    private String sexo;
    private String posicion;
    private String fechaNacimiento;
    private String descripcion;
    private int distancia;

    public RegisterRequest(String nombre, String password, String email, String imagen1, String imagen2, String imagen3, String imagen4, String imagen5, String imagen6, String sexo, String posicion, String fechaNacimiento, String descripcion, int distancia) {
        this.nombre = nombre;
        this.password = password;
        this.email = email;
        this.imagen1 = imagen1;
        this.imagen2 = imagen2;
        this.imagen3 = imagen3;
        this.imagen4 = imagen4;
        this.imagen5 = imagen5;
        this.imagen6 = imagen6;
        this.sexo = sexo;
        this.posicion = posicion;
        this.fechaNacimiento = fechaNacimiento;
        this.descripcion = descripcion;
        this.distancia = distancia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImagen1() {
        return imagen1;
    }

    public void setImagen1(String imagen1) {
        this.imagen1 = imagen1;
    }

    public String getImagen2() {
        return imagen2;
    }

    public void setImagen2(String imagen2) {
        this.imagen2 = imagen2;
    }

    public String getImagen3() {
        return imagen3;
    }

    public void setImagen3(String imagen3) {
        this.imagen3 = imagen3;
    }

    public String getImagen4() {
        return imagen4;
    }

    public void setImagen4(String imagen4) {
        this.imagen4 = imagen4;
    }

    public String getImagen5() {
        return imagen5;
    }

    public void setImagen5(String imagen5) {
        this.imagen5 = imagen5;
    }

    public String getImagen6() {
        return imagen6;
    }

    public void setImagen6(String imagen6) {
        this.imagen6 = imagen6;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getPosicion() {
        return posicion;
    }

    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getDistancia() {
        return distancia;
    }

    public void setDistancia(int distancia) {
        this.distancia = distancia;
    }
}
