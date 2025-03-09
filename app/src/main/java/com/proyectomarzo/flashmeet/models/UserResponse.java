package com.proyectomarzo.flashmeet.models;

import android.adservices.ondevicepersonalization.UserData;
import android.icu.text.Transliterator;

public class UserResponse {


    private boolean success;
    private String message;
    private UserData data; // Una clase adicional para los datos del usuario
    private String errorCode;
    private int statusCode;
    private String statusDescripcion;

    public UserResponse(boolean success, String message, UserData data, String errorCode, int statusCode, String statusDescripcion) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.errorCode = errorCode;
        this.statusCode = statusCode;
        this.statusDescripcion = statusDescripcion;


    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserData getData() {
        return data;
    }

    public void setData(UserData data) {
        this.data = data;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusDescripcion() {
        return statusDescripcion;
    }

    public void setStatusDescripcion(String statusDescripcion) {
        this.statusDescripcion = statusDescripcion;
    }

    public static class UserData {
        private int id;
        private String idUsuario;
        private String nombre;
        private String email;
        private String imagen1;
        private String imagen2;
        private String imagen3;
        private String imagen4;
        private String imagen5;
        private String imagen6;
        private boolean estaActivo;
        private boolean estaBloqueado;
        private String descripcion;
        private String ultimaInteraccionMs;
        private String fechaNacimiento;
        private String sexo;
        private Transliterator.Position posicion;
        private String version;
        private String createdAt;
        private String updatedAt;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getIdUsuario() {
            return idUsuario;
        }

        public void setIdUsuario(String idUsuario) {
            this.idUsuario = idUsuario;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
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

        public boolean isEstaActivo() {
            return estaActivo;
        }

        public void setEstaActivo(boolean estaActivo) {
            this.estaActivo = estaActivo;
        }

        public boolean isEstaBloqueado() {
            return estaBloqueado;
        }

        public void setEstaBloqueado(boolean estaBloqueado) {
            this.estaBloqueado = estaBloqueado;
        }

        public String getDescripcion() {
            return descripcion;
        }

        public void setDescripcion(String descripcion) {
            this.descripcion = descripcion;
        }

        public String getUltimaInteraccionMs() {
            return ultimaInteraccionMs;
        }

        public void setUltimaInteraccionMs(String ultimaInteraccionMs) {
            this.ultimaInteraccionMs = ultimaInteraccionMs;
        }

        public String getFechaNacimiento() {
            return fechaNacimiento;
        }

        public void setFechaNacimiento(String fechaNacimiento) {
            this.fechaNacimiento = fechaNacimiento;
        }

        public String getSexo() {
            return sexo;
        }

        public void setSexo(String sexo) {
            this.sexo = sexo;
        }

        public Transliterator.Position getPosicion() {
            return posicion;
        }

        public void setPosicion(Transliterator.Position posicion) {
            this.posicion = posicion;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }
    }
}

