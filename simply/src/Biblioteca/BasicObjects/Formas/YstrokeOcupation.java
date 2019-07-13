package Biblioteca.BasicObjects.Formas;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class YstrokeOcupation{
    public DoubleProperty LEFT;
    public DoubleProperty RIGHT;
    public DoubleProperty UP;
    public DoubleProperty BOTTOM;
    public DoubleProperty WIDTH;
    public DoubleProperty HEIGHT;

    public YstrokeOcupation() {
        this(0, 0, 0 ,0);
    }

    public YstrokeOcupation(double WIDTH, double HEIGHT) {
        this(WIDTH/2, WIDTH/2, HEIGHT/2, HEIGHT/2);
    }
    
    public YstrokeOcupation(double LEFT, double RIGHT, double UP, double BOTTOM) {
        this.LEFT = new SimpleDoubleProperty(LEFT);
        this.RIGHT = new SimpleDoubleProperty(RIGHT);
        this.UP = new SimpleDoubleProperty(UP);
        this.BOTTOM = new SimpleDoubleProperty(BOTTOM);
        this.WIDTH = new SimpleDoubleProperty(LEFT + RIGHT);
        this.HEIGHT = new SimpleDoubleProperty(UP + BOTTOM);
    }
    
    public void setStrokeOcupation(double LEFT, double RIGHT, double UP, double BOTTOM) {
        this.LEFT.setValue(LEFT);
        this.RIGHT.setValue(RIGHT);
        this.UP.setValue(UP);
        this.BOTTOM.setValue(BOTTOM);
        this.WIDTH.setValue(LEFT + RIGHT);
        this.HEIGHT.setValue(UP + BOTTOM);
    }
    
    public void setStrokeOcupation(double WIDTH, double HEIGHT) {
        setStrokeOcupation(WIDTH/2, WIDTH/2, HEIGHT/2, HEIGHT/2);
    }

    @Override
    public String toString(){
        return "Left stroke: " + LEFT.get() + "\n" +
                "Right stroke: " + RIGHT.get() + "\n" +
                "Up stroke: " + UP.get() + "\n" +
                "BOTTOM stroke: " + BOTTOM.get() + "\n";
    }
}
