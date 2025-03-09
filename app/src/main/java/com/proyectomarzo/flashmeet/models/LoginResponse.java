package com.proyectomarzo.flashmeet.models;

public class LoginResponse {
    private boolean success;
    private String message;
    private UserData data;
    private String errorCode;
    private int statusCode;
    private String statusDescripcion;

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
        private String userId;
        private String token;
        private String refreshToken;
        private String tokenUID;
        private String refreshTokenUId;
        private double tokenExpiration;
        private double refreshTokenExpiration;

        // Getters and Setters
        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getRefreshToken() {
            return refreshToken;
        }

        public void setRefreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
        }

        public String getTokenUID() {
            return tokenUID;
        }

        public void setTokenUID(String tokenUID) {
            this.tokenUID = tokenUID;
        }

        public String getRefreshTokenUId() {
            return refreshTokenUId;
        }

        public void setRefreshTokenUId(String refreshTokenUId) {
            this.refreshTokenUId = refreshTokenUId;
        }

        public double getTokenExpiration() {
            return tokenExpiration;
        }

        public void setTokenExpiration(double tokenExpiration) {
            this.tokenExpiration = tokenExpiration;
        }

        public double getRefreshTokenExpiration() {
            return refreshTokenExpiration;
        }

        public void setRefreshTokenExpiration(double refreshTokenExpiration) {
            this.refreshTokenExpiration = refreshTokenExpiration;
        }
    }
}
