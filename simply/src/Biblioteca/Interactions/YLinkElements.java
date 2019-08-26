package Biblioteca.Interactions;

import Biblioteca.OrganizadoresDeNodos.YBox;
import java.util.ArrayList;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.paint.Paint;


//POR ENQUANTO SO FUNCIONA PRA YBox
public class YLinkElements {
    public ArrayList<YBox> elements = new ArrayList();
    private ArrayList<Integer> types = new ArrayList();
    private int whoIsSelected;
    private String eventsName;

    public YLinkElements(YBox... elementos) {
        yAddAll(elementos);
        eventsName = "linked_elements" + this;
    }
    
    public void yAdd(YBox elemento){
        elements.add(elemento);
    }
    
    public void yAddAll(YBox... elementos){
        for (int i = 0; i < elementos.length; i++) {
            yAdd(elementos[i]);
        }
    }
    
    public void yRemove(YBox object){
        if(elements.contains(object))
            yRemove(elements.indexOf(object));
    }
    
    public void yRemove(int index){
        ((YBox) elements.get(index)).yGetEventsHandler().removeFromAll(eventsName);
        elements.remove(index);
        types.remove(index);
    }
    
    /**
     * Possible parameters:
     * 1 - Button (it clicks and releases)
     * 2 - Lever (it switches between on and off)
     * 3 - Tab (the same as a lever with an exception, clicking on itself)
     * 
     * @param type The element types in the same order of adding
     */
    public void yAddElementsTypes(int... type){
        for (int i = 0; i < type.length; i++) {
            if(type[i] < 1)
                type[i] = 1;
            else if(type[i] > 3)
                type[i] = 3;
            
            types.add(type[i]);
        }
    }
    /**
     * @see #yAddElementsTypes(int[])
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
            elemento.yGetEventsHandler().onMousePressed().addHandleble(0, eventsName, (event) -> {
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
    
    public void yDisableSelectedElement(Paint defaultStrokeColor){
        if(whoIsSelected != -1){
            YBox elemento = elements.get(whoIsSelected);
            elemento.yGetEventsHandler().is_selected = false;
            elemento.ySetStroke(null, defaultStrokeColor, null, false);
            elemento.yGetEventsHandler().actionCleaner().run(null);
        }
    }
    
    public void yRemoveVisualEvents(){
        for (int i = 0; i < elements.size(); i++) {
            elements.get(i).yGetEventsHandler().removeFromAll(eventsName);
        }
    }
}