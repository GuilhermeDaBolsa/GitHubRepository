package Biblioteca.BasicObjects.Formas;

import java.io.Serializable;

public class YstrokeOcupation implements Serializable{
    public double LEFT;
    public double RIGHT;
    public double UP;
    public double BOTTOM;
    public double WIDTH;
    public double HEIGHT;

    public YstrokeOcupation() {
        setStrokeOcupation(0, 0);
    }

    public YstrokeOcupation(double LEFT, double RIGHT, double UP, double BOTTOM) {
        setStrokeOcupation(LEFT, RIGHT, UP, BOTTOM);
    }

    public YstrokeOcupation(double WIDTH, double HEIGHT) {
        setStrokeOcupation(WIDTH, HEIGHT);
    }
    
    public void setStrokeOcupation(double LEFT, double RIGHT, double UP, double BOTTOM) {
        this.LEFT = LEFT;
        this.RIGHT = RIGHT;
        this.UP = UP;
        this.BOTTOM = BOTTOM;
        this.WIDTH = LEFT + RIGHT;
        this.HEIGHT = UP + BOTTOM;
    }
    
    public void setStrokeOcupation(double WIDTH, double HEIGHT) {
        this.LEFT = WIDTH/2;
        this.RIGHT = WIDTH/2;
        this.UP = HEIGHT/2;
        this.BOTTOM = HEIGHT/2;
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
    }
}
