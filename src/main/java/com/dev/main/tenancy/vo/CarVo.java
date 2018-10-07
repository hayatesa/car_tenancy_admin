package com.dev.main.tenancy.vo;

public class CarVo {
    private String brand;
    private String series;
    private String nub;
    private String path;
    private Long carId;

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getNub() {
        return nub;
    }

    public void setNub(String nub) {
        this.nub = nub;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "CarVo{" +
                "brand='" + brand + '\'' +
                ", series='" + series + '\'' +
                ", nub='" + nub + '\'' +
                ", path='" + path + '\'' +
                ", carId=" + carId +
                '}';
    }
}
