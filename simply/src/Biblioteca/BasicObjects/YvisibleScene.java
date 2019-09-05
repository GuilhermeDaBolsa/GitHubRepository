package Biblioteca.BasicObjects;

import Biblioteca.Interactions.YEventsHandler;
import javafx.scene.layout.Pane;

/**
 * It would be nice if every visual element that have multiple visiblue object extended this class :D.
 */
public class YvisibleScene extends Pane implements YabstractInterfacee{
    private YEventsHandler yEvents_Handler = new YEventsHandler(this);
    
    @Override
    public YEventsHandler yGetEventsHandler(){
        return yEvents_Handler;
    }
    
    @Override
    public double yGetWidth(){
        return YvisibleObjectHandler.yGetWidth(this);
    }
    
    @Override
    public double yGetHeight(){
        return YvisibleObjectHandler.yGetHeight(this);
    }
    
    @Override
    public double yGetTranslateX(double pivo){
        return YvisibleObjectHandler.yGetTranslateX(this, pivo);
    }
    
    @Override
    public double yGetTranslateY(double pivo){
        return YvisibleObjectHandler.yGetTranslateY(this, pivo);
    }
    
    @Override
    public void ySetTranslateX(double X, double pivo){
        YvisibleObjectHandler.ySetTranslateX(this, X, pivo);
    }
    
    @Override
    public void ySetTranslateY(double Y, double pivo){
        YvisibleObjectHandler.ySetTranslateY(this, Y, pivo);
    }

    /**
     * Hides and makes this object uninteractive.
     */
    public void yDeactivate(){
        YvisibleObjectHandler.yDeactivate(this);
    }
    
    /**
     * Makes this object visible and interactive.
     */
    public void yActivate(){
        YvisibleObjectHandler.yActivate(this);
    }
    
    /**
     * Switch between activated and deactivated mode.
     * @see #yActivate() 
     * @see #yDeactivate() 
     */
    public void ySwitchOnOff(){
        YvisibleObjectHandler.ySwitchOnOff(this);
    }
}
