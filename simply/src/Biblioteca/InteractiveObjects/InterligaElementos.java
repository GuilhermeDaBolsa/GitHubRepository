package Biblioteca.InteractiveObjects;

import static Biblioteca.LogicClasses.Matematicas.modulo;
import Biblioteca.OrganizadoresDeNodos.Caixa;
import java.util.ArrayList;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.paint.Paint;
import javafx.scene.shape.StrokeType;


//POR ENQUANTO SO FUNCIONA PRA CAIXAS
public class InterligaElementos {
    public ArrayList<Node> elementos = new ArrayList();
    private ArrayList<Integer> numeroDeConexao = new ArrayList();

    public InterligaElementos(Node... elementos) {
        addAll(elementos);
    }
    
    public void add(Node elemento){
        elementos.add(elemento);
    }
    
    public void addAll(Node... elementos){
        for (int i = 0; i < elementos.length; i++) {
            add(elementos[i]);
        }
    }
    
    //FAZER COM UM VETOR DIZENDO SE É BOTAO OU ALAVANCA (BOOLEAN)
    //E COM OUTRO DIZENDO COM QUAL ELEMENTOS ESTE SE RELACIONA (UM VETOR PRA CADA ELEMENTO, POIS O MESMO PODE SE RELACIONAR COM MAIS DE 1)
    
    /**
     * Você pode inserir um ID para cada elemento na ordem dos parametros, para interliga-los ou identifica-los.
     * Ao usar numeros negativos, o elemento será do tipo alavanca (use do -2 em diante) e ao usar positivos,
     * será do tipo botão (use do 2 em diante) (ao posteriormente inserir eventos), para que ao clicar em um elemento,
     * o outro receba um ActionClear (seja descelecionado) use o mesmo ID para ambos elementos, sendo assim estarão conectados.
     * Se você quer que todos os elementos sejam do tipo botão e sejam todos independentes um do outro (ao clicar em um,
     * nenhum outro será influenciado por meio desta classe), digite 1.
     * Caso queira que todos sejam alavancas e independentes como o caso acima, digite -1.
     * (OBS: NUMEROS INVERSOS TAMBÈM ESTAO CONECTADOS... EX: -3 e 3
     * @param typeAndID 
     */
    public void setButtonTypes(int... typeAndID){
        if(typeAndID.length == 1 && typeAndID[0] < 2 && typeAndID[0] > -2){
            for (int i = 0; i < elementos.size(); i++) {
                numeroDeConexao.add(typeAndID[0]);
            }
        }else{
            for (int i = 0; i < typeAndID.length; i++) {
                numeroDeConexao.add(typeAndID[i]);
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
    //O RELEASE E O PRESSED TAO BUGANDO, MESMO SE TU NAO TIVER COM O MOUSE NO ELEMENTO ELES VAO FUNCIONAR AAAAAAAAAAAAAAAAAAAAAAAAA
    public void setEventosVisuais(Paint corBordaPadrao, Paint corBordaFoco, Paint corBordaClick){
        for (int i = 0; i < elementos.size(); i++) {
            if(elementos.get(i) instanceof Caixa){
                Caixa elemento = (Caixa) elementos.get(i);
                
                elemento.ySetStroke(null, corBordaPadrao, StrokeType.CENTERED, true);
                int num = numeroDeConexao.get(i);
                String eventsName = "linked_elements";
                if(num < 0){
                    elemento.yEvents_Handler.onMouseEntered().addHandleble(eventsName, (event) -> {
                        if(!elemento.yEvents_Handler.is_selected)
                            elemento.ySetStroke(null, corBordaFoco, StrokeType.CENTERED, true);
                        elemento.setCursor(Cursor.HAND);
                    });
                    elemento.yEvents_Handler.onMouseExited().addHandleble(eventsName, (event) -> {
                        if(!elemento.yEvents_Handler.is_selected)
                            elemento.ySetStroke(null, corBordaPadrao, StrokeType.CENTERED, true);
                        elemento.setCursor(Cursor.DEFAULT);
                    });
                    elemento.yEvents_Handler.onMousePressed().addHandleble(eventsName, (event) -> {
                        if(!elemento.yEvents_Handler.is_selected){
                            elemento.ySetStroke(null, corBordaClick, StrokeType.CENTERED, true);
                        }else{
                            elemento.ySetStroke(null, corBordaFoco, StrokeType.CENTERED, true);
                        }
                    });
                    elemento.yEvents_Handler.onMouseReleased().addHandleble(eventsName, (event) -> {
                        if(!elemento.yEvents_Handler.is_selected){
                            if(num < -1){
                                descelecionaOutrosElementos(corBordaPadrao, num);
                            }
                            elemento.ySetStroke(null, corBordaClick, StrokeType.CENTERED, true);
                        }else{
                            elemento.ySetStroke(null, corBordaPadrao, StrokeType.CENTERED, true);//ÉEEÈÈÈÈÈ``E
                        }
                        elemento.yEvents_Handler.is_selected = !elemento.yEvents_Handler.is_selected;
                    });
                }else{
                    elemento.yEvents_Handler.onMouseEntered().addHandleble(eventsName, (event) -> {
                        elemento.ySetStroke(null, corBordaFoco, StrokeType.CENTERED, true);
                        elemento.setCursor(Cursor.HAND);
                    });
                    elemento.yEvents_Handler.onMouseExited().addHandleble(eventsName, (event) -> {
                        elemento.ySetStroke(null, corBordaPadrao, StrokeType.CENTERED, true);
                        elemento.setCursor(Cursor.DEFAULT);
                    });
                    elemento.yEvents_Handler.onMousePressed().addHandleble(eventsName, (event) -> {
                        elemento.ySetStroke(null, corBordaClick, StrokeType.CENTERED, true);
                    });
                    elemento.yEvents_Handler.onMouseReleased().addHandleble(eventsName, (event) -> {
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
            if(elementos.get(i) instanceof Caixa && modulo(numeroDeConexao.get(i)) == modulo(ID)){
                Caixa elemento = (Caixa) elementos.get(i);
                elemento.yEvents_Handler.is_selected = false;
                elemento.ySetStroke(null, corBordaPadrao, StrokeType.CENTERED, true);
                elemento.yEvents_Handler.actionCleaner().run(null);
            }
        }
    }
    
    public void removerRecursosVisuais(){
        
    }
}