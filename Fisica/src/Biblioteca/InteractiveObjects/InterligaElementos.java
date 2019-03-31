package Biblioteca.InteractiveObjects;

import static Biblioteca.LogicClasses.Matematicas.modulo;
import Biblioteca.OrganizadoresDeNodos.Caixa;
import static executavel.CriadorMenu.scene;
import java.util.ArrayList;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.paint.Paint;


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
                
                elemento.setStrokeColor(corBordaPadrao);
                int num = numeroDeConexao.get(i);
                if(num < 0){
                    elemento.focus.addRunnable(0, () -> {
                        if(!elemento.is_selected)
                            elemento.setStrokeColor(corBordaFoco);
                        scene.setCursor(Cursor.HAND);
                    });
                    elemento.outFocus.addRunnable(0, () -> {
                        if(!elemento.is_selected)
                            elemento.setStrokeColor(corBordaPadrao);
                        scene.setCursor(Cursor.DEFAULT);
                    });
                    elemento.actionPressed.addRunnable(0, () -> {
                        if(!elemento.is_selected){
                            elemento.setStrokeColor(corBordaClick);
                        }else{
                            elemento.setStrokeColor(corBordaFoco);
                        }
                    });
                    elemento.actionRelease.addRunnable(0, () -> {
                        if(!elemento.is_selected){
                            if(num < -1){
                                descelecionaOutrosElementos(corBordaPadrao, num);
                            }
                            elemento.setStrokeColor(corBordaClick);
                        }else{
                            elemento.setStrokeColor(corBordaPadrao);//ÉEEÈÈÈÈÈ``E
                        }
                        elemento.is_selected = !elemento.is_selected;
                    });
                }else{
                    elemento.focus.addRunnable(0, () -> {
                        elemento.setStrokeColor(corBordaFoco);
                        scene.setCursor(Cursor.HAND);
                    });
                    elemento.outFocus.addRunnable(0, () -> {
                        elemento.setStrokeColor(corBordaPadrao);
                        scene.setCursor(Cursor.DEFAULT);
                    });
                    elemento.actionPressed.addRunnable(0, () -> {
                        elemento.setStrokeColor(corBordaClick);
                    });
                    elemento.actionRelease.addRunnable(0, () -> {
                        if(num > 1){
                            descelecionaOutrosElementos(corBordaPadrao, num);
                        }
                        elemento.setStrokeColor(corBordaPadrao);
                    });
                }
            }
        }  
    }
    
    private void descelecionaOutrosElementos(Paint corBordaPadrao, int ID){
        for (int i = 0; i < elementos.size(); i++) {
            if(elementos.get(i) instanceof Caixa && modulo(numeroDeConexao.get(i)) == modulo(ID)){
                Caixa elemento = (Caixa) elementos.get(i);
                elemento.is_selected = false;
                elemento.setStrokeColor(corBordaPadrao);
                elemento.actionCleaner.run();
            }
        }
    }
    
    public void removerRecursosVisuais(){
        
    }
}
