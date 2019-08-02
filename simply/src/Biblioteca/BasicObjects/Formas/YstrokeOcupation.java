package Biblioteca.BasicObjects.Formas;

public class YstrokeOcupation{
    public double LEFT;
    public double RIGHT;
    public double UP;
    public double BOTTOM;
    public double WIDTH;
    public double HEIGHT;

    public YstrokeOcupation() {
        this(0, 0, 0 ,0);
    }

    public YstrokeOcupation(double WIDTH, double HEIGHT) {
        this(WIDTH/2, WIDTH/2, HEIGHT/2, HEIGHT/2);
    }
    
    public YstrokeOcupation(double LEFT, double RIGHT, double UP, double BOTTOM) {
        this.LEFT = LEFT;
        this.RIGHT = RIGHT;
        this.UP = UP;
        this.BOTTOM = BOTTOM;
        this.WIDTH = LEFT + RIGHT;
        this.HEIGHT = UP + BOTTOM;
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
        setStrokeOcupation(WIDTH/2, WIDTH/2, HEIGHT/2, HEIGHT/2);
    }

    @Override
    public String toString(){
        return "Left stroke: " + LEFT + "\n" +
                "Right stroke: " + RIGHT + "\n" +
                "Up stroke: " + UP + "\n" +
                "Bottom stroke: " + BOTTOM + "\n";
    }
}
