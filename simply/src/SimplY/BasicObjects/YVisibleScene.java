package SimplY.BasicObjects;

import SimplY.Interactions.YEventsHandler;
import javafx.scene.layout.Pane;

/**
 * It would be nice if every visual element that have multiple visiblue object extended this class :D.
 */
public class YVisibleScene extends Pane implements YAbstractInterface{
    private YEventsHandler yEvents_Handler = new YEventsHandler(this);
    
    @Override
    public YEventsHandler yGetEventsHandler(){
        return yEvents_Handler;
    }
    
    @Override
    public double yGetWidth(){
        return YVisibleObjectHandler.yGetWidth(this);
    }
    
    @Override
    public double yGetHeight(){
        return YVisibleObjectHandler.yGetHeight(this);
    }
    
    @Override
    public double yGetTranslateX(double pivo){
        return YVisibleObjectHandler.yGetTranslateX(this, pivo);
    }
    
    @Override
    public double yGetTranslateY(double pivo){
        return YVisibleObjectHandler.yGetTranslateY(this, pivo);
    }
    
    @Override
    public void ySetTranslateX(double X, double pivo){
        YVisibleObjectHandler.ySetTranslateX(this, X, pivo);
    }
    
    @Override
    public void ySetTranslateY(double Y, double pivo){
        YVisibleObjectHandler.ySetTranslateY(this, Y, pivo);
    }

    /**
     * Hides and makes this object uninteractive.
     */
    public void yDeactivate(){
        YVisibleObjectHandler.yDeactivate(this);
    }
    
    /**
     * Makes this object visible and interactive.
     */
    public void yActivate(){
        YVisibleObjectHandler.yActivate(this);
    }
    
    /**
     * Switch between activated and deactivated mode.
     * @see #yActivate() 
     * @see #yDeactivate() 
     */
    public void ySwitchOnOff(){
        YVisibleObjectHandler.ySwitchOnOff(this);
    }
}
