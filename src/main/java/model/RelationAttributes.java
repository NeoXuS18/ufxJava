package model;

public class RelationAttributes {

    private Double x1 ;

    private Double y1;

    private Double xLast;

    private Double yLast;

    public RelationAttributes() {
    }

    public RelationAttributes(Double x1, Double y1, Double xLast, Double yLast) {
        this.x1 = x1;
        this.y1 = y1;
        this.xLast = xLast;
        this.yLast = yLast;
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

    public Double getxLast() {
        return xLast;
    }

    public void setxLast(Double xLast) {
        this.xLast = xLast;
    }

    public Double getyLast() {
        return yLast;
    }

    public void setyLast(Double yLast) {
        this.yLast = yLast;
    }
}
