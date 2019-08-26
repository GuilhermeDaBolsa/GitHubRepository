package Biblioteca.BasicObjects;

import Biblioteca.Interactions.YEventsHandler;

public interface yAbstractInterface {
    
    public YEventsHandler yGetEventsHandler();
    
    public double yGetWidth();
    
    public double yGetHeight();
    
    public double yGetTranslateX(double pivo);
    
    public double yGetTranslateY(double pivo);
    
    public void ySetTranslateX(double X, double pivo);
    
    public void ySetTranslateY(double Y, double pivo);
}
