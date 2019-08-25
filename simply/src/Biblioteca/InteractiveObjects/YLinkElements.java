package Biblioteca.InteractiveObjects;

import static Biblioteca.LogicClasses.Matematicas.modulo;
import Biblioteca.OrganizadoresDeNodos.YBox;
import java.util.ArrayList;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.paint.Paint;
import javafx.scene.shape.StrokeType;


//POR ENQUANTO SO FUNCIONA PRA CAIXAS
public class YLinkElements {
    public ArrayList<Node> elementos = new ArrayList();
    private ArrayList<Integer> numeroDeConexao = new ArrayList();
    private String eventsName;

    public YLinkElements(Node... elementos) {
        yAddAll(elementos);
        eventsName = "linked_elements" + this;
    }
    
    public void yAdd(Node elemento){
        elementos.add(elemento);
    }
    
    public void yAddAll(Node... elementos){
        for (int i = 0; i < elementos.length; i++) {
            yAdd(elementos[i]);
        }
    }
    
    public void yRemove(Node object){
        if(elementos.contains(object))
            yRemove(elementos.indexOf(object));
    }
    
    public void yRemove(int index){
        
    }
    
    /**
     * Você pode inserir um ID para cada elemento na ordem dos parametros, para interliga-los ou identifica-los.
     * Ao usar numeros negativos, o elemento será do tipo alavanca (use do -2 em diante) e ao usar positivos,
     * será do tipo botão (use do 2 em diante) (ao posteriormente inserir eventos), para que ao clicar em um elemento,
     * o outro receba um ActionClear (seja descelecionado) use o mesmo ID para ambos elementos, sendo assim estarão conectados.
     * Se você quer que todos os elementos sejam do tipo botão e sejam todos independentes um do outro (ao clicar em um,
     * nenhum outro será influenciado por meio desta classe), digite 1.
     * Caso queira que todos sejam alavancas e independentes como o caso acima, digite -1.
     * (OBS: NUMEROS INVERSOS TAMBÈM ESTAO CONECTADOS... EX: -3 e 3
     * @param type 
     */
    //fazer apenas com 1, 2, 3... nao precisa do mesmo id pra estar conectados se eles estao nessa classe
    // 1 - btn, 2- alavanca, 3- aba
    //ajeitar o remove pra tirar os eventos que adicionanou8
    public void setButtonTypes(int... type){
        if(type.length == 1 && type[0] < 2 && type[0] > -2){
            for (int i = 0; i < elementos.size(); i++) {
                numeroDeConexao.add(type[0]);
            }
        }else{
            for (int i = 0; i < type.length; i++) {
                numeroDeConexao.add(type[i]);
            }
        }
    }
    /**
     * @see #setButtonTypes(int...)
     * @param index
     * @param type 
     */
    public void setEspecificButtonType(int index, int type){
        numeroDeConexao.set(index, type);
    }
    
    //ver tbm como usar o action cleaner no outoffocus e release, ja que é bem plausivel
    //O RELEASE E O PRESSED TAO BUGANDO, MESMO SE TU NAO TIVER COM O MOUSE NO ELEMENTO ELES VAO FUNCIONAR AAAAAAAAAAAAAAAAAAAAAAAAA, se pa com o click no lugar do release funfa, pq clic é a ação inteira
    public void setEventosVisuais(Paint corBordaPadrao, Paint corBordaFoco, Paint corBordaClick){
        for (int i = 0; i < elementos.size(); i++) {
            if(elementos.get(i) instanceof YBox){
                YBox elemento = (YBox) elementos.get(i);
                
                elemento.ySetStroke(null, corBordaPadrao, StrokeType.CENTERED, true);
                int num = numeroDeConexao.get(i);
                if(num < 0){
                    elemento.yGetEventsHandler().onMouseEntered().addHandleble(eventsName, (event) -> {
                        if(!elemento.yGetEventsHandler().is_selected)
                            elemento.ySetStroke(null, corBordaFoco, StrokeType.CENTERED, true);
                        elemento.setCursor(Cursor.HAND);
                    });
                    elemento.yGetEventsHandler().onMouseExited().addHandleble(eventsName, (event) -> {
                        if(!elemento.yGetEventsHandler().is_selected)
                            elemento.ySetStroke(null, corBordaPadrao, StrokeType.CENTERED, true);
                        elemento.setCursor(Cursor.DEFAULT);
                    });
                    elemento.yGetEventsHandler().onMousePressed().addHandleble(eventsName, (event) -> {
                        if(!elemento.yGetEventsHandler().is_selected){
                            elemento.ySetStroke(null, corBordaClick, StrokeType.CENTERED, true);
                        }else{
                            elemento.ySetStroke(null, corBordaFoco, StrokeType.CENTERED, true);
                        }
                    });
                    elemento.yGetEventsHandler().onMouseReleased().addHandleble(eventsName, (event) -> {
                        if(!elemento.yGetEventsHandler().is_selected){
                            if(num < -1){
                                descelecionaOutrosElementos(corBordaPadrao, num);
                            }
                            elemento.ySetStroke(null, corBordaClick, StrokeType.CENTERED, true);
                        }else{
                            elemento.ySetStroke(null, corBordaPadrao, StrokeType.CENTERED, true);//ÉEEÈÈÈÈÈ``E
                        }
                        elemento.yGetEventsHandler().is_selected = !elemento.yGetEventsHandler().is_selected;
                    });
                }else{
                    elemento.yGetEventsHandler().onMouseEntered().addHandleble(eventsName, (event) -> {
                        elemento.ySetStroke(null, corBordaFoco, StrokeType.CENTERED, true);
                        elemento.setCursor(Cursor.HAND);
                    });
                    elemento.yGetEventsHandler().onMouseExited().addHandleble(eventsName, (event) -> {
                        elemento.ySetStroke(null, corBordaPadrao, StrokeType.CENTERED, true);
                        elemento.setCursor(Cursor.DEFAULT);
                    });
                    elemento.yGetEventsHandler().onMousePressed().addHandleble(eventsName, (event) -> {
                        elemento.ySetStroke(null, corBordaClick, StrokeType.CENTERED, true);
                    });
                    elemento.yGetEventsHandler().onMouseReleased().addHandleble(eventsName, (event) -> {
                        if(num > 1){
                            descelecionaOutrosElementos(corBordaPadrao, num);
                        }
                        elemento.ySetStroke(null, corBordaPadrao, StrokeType.CENTERED, true);
                    });
                }
            }
        }  
    }
    
    private void descelecionaOutrosElementos(Paint corBordaPadrao, int ID){
        for (int i = 0; i < elementos.size(); i++) {
            if(elementos.get(i) instanceof YBox && modulo(numeroDeConexao.get(i)) == modulo(ID)){
                YBox elemento = (YBox) elementos.get(i);
                elemento.yGetEventsHandler().is_selected = false;
                elemento.ySetStroke(null, corBordaPadrao, StrokeType.CENTERED, true);
                elemento.yGetEventsHandler().actionCleaner().run(null);
            }
        }
    }
    
    public void removerRecursosVisuais(){
        
    }
}