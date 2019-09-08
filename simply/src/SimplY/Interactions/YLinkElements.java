package SimplY.Interactions;

import SimplY.NodeManager.YBox;
import java.util.ArrayList;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.paint.Paint;


/**
 * Class created to link elements in their events (principaly visual events, like stroke and click).
 * (ONLY WORKS WITH YBOX).
 */
public class YLinkElements {
    public ArrayList<YBox> elements = new ArrayList();
    private ArrayList<Integer> types = new ArrayList();
    private int whoIsSelected;
    private String eventsName;

    public YLinkElements() {
        eventsName = "linked_elements" + this;
    }
    
    public YLinkElements(YBox... elementos) {
        yAddAll(elementos);
        eventsName = "linked_elements" + this;
    }
    
    /**
     * Add an element to be linked to the others
     * @param elemento The element.
     */
    public void yAdd(YBox elemento){
        elements.add(elemento);
        types.add(1);
    }
    
    /**
     * Add several elments to be linked all to the others.
     * @param elementos The elements
     */
    public void yAddAll(YBox... elementos){
        for (int i = 0; i < elementos.length; i++) {
            yAdd(elementos[i]);
        }
    }
    
    /**
     * Remove an element and unlink it.
     * @param object The element to be removed.
     */
    public void yRemove(YBox object){
        if(elements.contains(object))
            yRemove(elements.indexOf(object));
    }
    
    /**
     * Remove an element by its index and unlink it.
     * @param index The index of the element.
     */
    public void yRemove(int index){
        ((YBox) elements.get(index)).yGetEventsHandler().removeFromAll(eventsName);
        elements.remove(index);
        types.remove(index);
    }
    
    /**
     * Sets element types (caracterizes its events)
     * Possible parameters:
     * 1 - Button (it clicks and releases)
     * 2 - Lever (it switches between on and off)
     * 3 - Tab (the same as a lever with an exception, clicking on itself)
     * 
     * @param type The element types in the same order of adding
     */
    public void ySetElementsTypes(int... type){
        for (int i = 0; i < elements.size(); i++) {
            ySetElementType(i, type[i]);
        }
    }
    /**
     * @see #ySetElementsTypes(int[])
     * @param index
     * @param type 
     */
    public void ySetElementType(int index, int type){
        if(type < 1)
            type = 1;
        else if(type > 3)
            type = 3;
        
        types.set(index, type);
    }
    
    /**
     * Create a visual efects pattern with all elements in this object.
     * @param defaultStrokeColor The default stroke.
     * @param onFocusStrokeColor The stroke when mouse entered.
     * @param onClickStrokeColor The stroke when mouse clicked.
     */
    public void ySetVisualEvents(Paint defaultStrokeColor, Paint onFocusStrokeColor, Paint onClickStrokeColor){
        yRemoveVisualEvents();
        
        for (int i = 0; i < elements.size(); i++) {
            YBox elemento = elements.get(i);
            elemento.ySetStroke(null, defaultStrokeColor, null, false);
            int num = types.get(i);

            elemento.yGetEventsHandler().onMouseEntered().addHandleble(0, eventsName, (event) -> {
                if(!elemento.yGetEventsHandler().is_selected)
                    elemento.ySetStroke(null, onFocusStrokeColor, null, false);
                ((Node) elemento).setCursor(Cursor.HAND);
            });
            elemento.yGetEventsHandler().onMouseExited().addHandleble(0, eventsName, (event) -> {
                if(!elemento.yGetEventsHandler().is_selected)
                    elemento.ySetStroke(null, defaultStrokeColor, null, false);
                ((Node) elemento).setCursor(Cursor.DEFAULT);
            });
            elemento.yGetEventsHandler().onMousePressed().addHandleble(0, eventsName, (event) -> {//see this cases better another time :)
                elemento.ySetStroke(null, onClickStrokeColor, null, false);
            });

            switch (num) {
                case 1:
                    elemento.yGetEventsHandler().onMouseClicked().addHandleble(0, eventsName, (event) -> {
                        yDisableSelectedElement(defaultStrokeColor);
                        whoIsSelected = -1;
                        elemento.ySetStroke(null, onFocusStrokeColor, null, false);
                    });
                    break;
                case 2:
                    elemento.yGetEventsHandler().onMouseClicked().addHandleble(0, eventsName, (event) -> {
                        
                        if(!elemento.yGetEventsHandler().is_selected){
                            yDisableSelectedElement(defaultStrokeColor);
                            elemento.yGetEventsHandler().is_selected = true;
                            elemento.ySetStroke(null, onClickStrokeColor, null, false);
                            whoIsSelected = elements.indexOf(elemento);
                        }else{
                            yDisableSelectedElement(defaultStrokeColor);
                            whoIsSelected = -1;
                            elemento.ySetStroke(null, onFocusStrokeColor, null, false);
                            elemento.yGetEventsHandler().onMouseClicked().stopEventPropagation();//REMEMBER THAT IT ONLY STOPS PROPAGATION ON: MOUSE CLICKED!!!
                        }
                    });
                    break;
                case 3:
                    elemento.yGetEventsHandler().onMouseClicked().addHandleble(0, eventsName, (event) -> {
                        if(!elemento.yGetEventsHandler().is_selected){
                            yDisableSelectedElement(defaultStrokeColor);
                            elemento.yGetEventsHandler().is_selected = true;
                            elemento.ySetStroke(null, onClickStrokeColor, null, false);
                            whoIsSelected = elements.indexOf(elemento);
                        }else{
                            elemento.yGetEventsHandler().onMouseClicked().stopEventPropagation();
                        }
                    });
                    break;
            }
        }  
    }
    
    /**
     * Disable the selected element.
     * @param defaultStrokeColor The color the stroke will have after deactivation.
     */
    public void yDisableSelectedElement(Paint defaultStrokeColor){
        if(whoIsSelected != -1){
            YBox elemento = elements.get(whoIsSelected);
            elemento.yGetEventsHandler().is_selected = false;
            elemento.ySetStroke(null, defaultStrokeColor, null, false);
            elemento.yGetEventsHandler().actionCleaner().run(null);
        }
    }
    
    /**
     * Remove the visual events from all elements in this object.
     */
    public void yRemoveVisualEvents(){
        for (int i = 0; i < elements.size(); i++) {
            elements.get(i).yGetEventsHandler().removeFromAll(eventsName);
        }
    }
}