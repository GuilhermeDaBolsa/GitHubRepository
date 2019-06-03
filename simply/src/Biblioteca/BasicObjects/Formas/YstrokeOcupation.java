package Biblioteca.BasicObjects.Formas;

import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

public class YstrokeOcupation {
    public double LEFT = 0;
    public double RIGHT = 0;
    public double UP = 0;
    public double BOTTOM = 0;
    public double WIDTH = 0;
    public double HEIGHT = 0;
    
    /**
     * @param points Pontos que formam a forma.
     */
    public void calculate(Double... points){//AQUI VAI UM BOM METODO
        if(points.length <= 2){
            System.out.println("Number of points is too small.");
            return;
        }
        if(points.length % 2 != 0){
            System.out.println("Number of points must be even.");
            return;
        }
        
        yCircularArray<Double> cPoints = new yCircularArray(points);
        
        Point2D upper = new Point2D(points[0], points[1]);
        Point2D upper_linked = new Point2D(points[0], points[1]);
        Point2D donwer = new Point2D(points[0], points[1]);
        Point2D donwer_linked = new Point2D(points[0], points[1]);
        Point2D lefter = new Point2D(points[0], points[1]);
        Point2D lefter_linked = new Point2D(points[0], points[1]);
        Point2D righter = new Point2D(points[0], points[1]);
        Point2D righter_linked = new Point2D(points[0], points[1]);
        
        for (int i = 2; i < points.length; i+=2) {
            if(cPoints.get(i) < lefter.getX()){
                lefter = new Point2D(cPoints.get(i), cPoints.get(i + 1));//FAZ CERTINHO PRA LINHA (COM TDS TIPOS DE STROKE) E DPS USA AQUI

                
                
            }else if(cPoints.get(i) > righter.getX()){
                righter = new Point2D(cPoints.get(i), cPoints.get(i + 1));
            }
        }
    }
    
    public void calculate(ObservableList<Double> points){
        calculate((Double[]) points.toArray());
    }
}
