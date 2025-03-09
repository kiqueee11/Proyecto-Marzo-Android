package com.proyectomarzo.flashmeet.models;

public class ImageResponse {
    private byte[] imageData;

    public ImageResponse(byte[] imageData) {
        this.imageData = imageData;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }
}
