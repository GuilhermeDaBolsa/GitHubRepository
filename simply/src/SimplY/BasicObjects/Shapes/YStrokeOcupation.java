package SimplY.BasicObjects.Shapes;

/**
 * Stores the space that a stroke of a shape occupies in the outside of it.
 */
public class YStrokeOcupation{
    public double LEFT;
    public double RIGHT;
    public double UP;
    public double BOTTOM;
    public double WIDTH;
    public double HEIGHT;

    /**
     * Creates a stroke ocupation with 0.
     */
    public YStrokeOcupation() {
        this(0, 0, 0 ,0);
    }

    /**
     * @see #setStrokeOcupation(double, double) 
    */
    public YStrokeOcupation(double WIDTH, double HEIGHT) {
        setStrokeOcupation(WIDTH/2, WIDTH/2, HEIGHT/2, HEIGHT/2);
    }
    
    /**
     * @see #setStrokeOcupation(double, double, double, double) 
     */
    public YStrokeOcupation(double LEFT, double RIGHT, double UP, double BOTTOM) {
        setStrokeOcupation(LEFT, RIGHT, UP, BOTTOM);
    }
    
    /**
     * Creates a stroke ocupation with full information, with WIDTH beeing LEFT + RIGHT
     * and HEIGHT as UP + BOTTOM;
     * @param LEFT The outside left ocupation of the stroke.
     * @param RIGHT The outside right ocupation of the stroke.
     * @param UP The outside up ocupation of the stroke.
     * @param BOTTOM The outside bottom ocupation of the stroke.
     */
    public void setStrokeOcupation(double LEFT, double RIGHT, double UP, double BOTTOM) {
        this.LEFT = LEFT;
        this.RIGHT = RIGHT;
        this.UP = UP;
        this.BOTTOM = BOTTOM;
        this.WIDTH = LEFT + RIGHT;
        this.HEIGHT = UP + BOTTOM;
    }
    
    /**
     * Creates a stroke ocupation with WIDTH and HEIGHT, setting LEFT and RIGHT as WIDTH/2
     * and UP and BOTTOM as HEIGHT/2.
     * @param WIDTH The outside width ocupation of the stroke.
     * @param HEIGHT The outside height ocupation of the stroke.
     */
    public void setStrokeOcupation(double WIDTH, double HEIGHT) {
        setStrokeOcupation(WIDTH/2, WIDTH/2, HEIGHT/2, HEIGHT/2);
    }
    
    /**
     * @return A string that contains the stroke ocupation of all sides of the shape.
     */
    @Override
    public String toString(){
        return "Left stroke: " + LEFT + "\n" +
                "Right stroke: " + RIGHT + "\n" +
                "Up stroke: " + UP + "\n" +
                "Bottom stroke: " + BOTTOM + "\n";
    }
}
