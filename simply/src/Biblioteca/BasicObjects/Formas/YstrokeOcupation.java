package Biblioteca.BasicObjects.Formas;

public class YstrokeOcupation {
    public double LEFT;
    public double RIGHT;
    public double UP;
    public double BOTTOM;
    public double WIDTH;
    public double HEIGHT;

    public YstrokeOcupation() {
        double LEFT = 0;
        double RIGHT = 0;
        double UP = 0;
        double BOTTOM = 0;
        double WIDTH = 0;
        double HEIGHT = 0;
    }

    public YstrokeOcupation(double LEFT, double RIGHT, double UP, double BOTTOM) {
        this.LEFT = LEFT;
        this.RIGHT = RIGHT;
        this.UP = UP;
        this.BOTTOM = BOTTOM;
        this.WIDTH = LEFT + RIGHT;
        this.HEIGHT = UP + BOTTOM;
    }

    public YstrokeOcupation(double WIDTH, double HEIGHT) {
        this.LEFT = WIDTH/2;
        this.RIGHT = WIDTH/2;
        this.UP = HEIGHT/2;
        this.BOTTOM = HEIGHT/2;
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
    }
}
