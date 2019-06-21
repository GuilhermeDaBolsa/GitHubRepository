package Biblioteca.BasicObjects.Formas;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.DoublePropertyBase;

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
        this.LEFT = creatorHelper(LEFT);
        this.RIGHT = creatorHelper(RIGHT);
        this.UP = creatorHelper(UP);
        this.BOTTOM = creatorHelper(BOTTOM);
        this.WIDTH = creatorHelper(LEFT + RIGHT);
        this.HEIGHT = creatorHelper(UP + BOTTOM);
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
    
    private DoublePropertyBase creatorHelper(double value){
        return new DoublePropertyBase(value) {
            @Override
            public Object getBean() {
                return YstrokeOcupation.this;
            }
            
            @Override
            public String getName() {
                return "yStrokeProperty";
            }
        };
    }
}
