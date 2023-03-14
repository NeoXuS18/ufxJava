package model;

public class Coordinates {
    private Double x1;
    private Double y1;
    private Double width;
    private Double heigth;

    public Coordinates(Double x1, Double y1, Double width, Double heigth) {
        this.x1 = x1;
        this.y1 = y1;
        this.width = width;
        this.heigth = heigth;
    }

    public Double getX1() {
        return x1;
    }

    public void setX1(Double x1) {
        this.x1 = x1;
    }

    public Double getY1() {
        return y1;
    }

    public void setY1(Double y1) {
        this.y1 = y1;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getHeigth() {
        return heigth;
    }

    public void setHeigth(Double heigth) {
        this.heigth = heigth;
    }
}
