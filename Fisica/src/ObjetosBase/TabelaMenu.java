package ObjetosBase;

import ObjetosBase.logicaEcore.Matematicas;
import ObjetosBase.logicaEcore.actionsEinteractive.InteractiveObject;
import ObjetosBase.logicaEcore.actionsEinteractive.Runnables;
import ObjetosBase.visibleObjects.Caixa;
import static executavel.CriadorMenu.scene;
import java.util.ArrayList;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.paint.Paint;

public class TabelaMenu extends InteractiveObject{
    public ArrayList<Node> elementos = new ArrayList();
    private ArrayList<Integer> numeroDeConexao = new ArrayList();
    public boolean inscreverElementosEmCaixas;
    private double positionPaternX;
    private double positionPaternY;
    private double espacoEntreElementosX;
    private double espacoEntreElementosY;
    
    public int numLimiteAtivador;
    private int numElementos;
    public Runnables acaoLimite;
    
    private Paint corFundoCaixa;
    private Paint corBordaCaixa;
    private Caixa modelo = null;
    
    
    // VER PRA FAZER BASEADO NO NODO PAI, TIPO... QUANTO ESPAÇO TEM, QUANTO MEDe O PROXIMO ELEMENTO... ETC
    //espacoEntreElementosX É o espaço entre os elementos (em X), para eles não ficarem grudados um no outro.
    //espacoEntreElementosY É o espaço entre os elementos (em Y), para eles não ficarem grudados um no outro.
    
    /**
     * Construtor que com base no Nodo pai desse elemento, calcula as medidas dos elementos (faz tudo automático).
     * @param pai  É o Nodo pai ao atual elemento.
     */
    public TabelaMenu(Node pai) {
        //ADIVINHA O QUE TU QUER
        
        this.getChildren().addAll(elementos);
    }
    
    /**
     * Faz na mão, independente de outros objetos.
     * @param positionPaternX É a medida padrão de distância entre os elementos (eixo X).
     * @param positionPaternY É a medida padrão de distância entre os elementos (eixo Y).
     * @param numLimiteAtivador Quando o número de elementos chegar ao numLimiteAtivador, será executada a acaoLimite.
     */
    public TabelaMenu(double positionPaternX, double positionPaternY, int numLimiteAtivador, Node... vetElemento) {
        this.numLimiteAtivador = numLimiteAtivador;
        this.positionPaternX = positionPaternX;
        this.positionPaternY = positionPaternY;
        
        if(vetElemento != null){
            adicionarElemento(false, vetElemento);
        }
        
        this.getChildren().addAll(elementos);
    }
    
    /**
     * Adiciona um novo elemento em uma nova posição desejada e, apartir daí, será seguido o padrão de posicionamento.
     * @param elemento É o novo elemento.
     * @param positionX Posição no eixo X.
     * @param positionY Posição no eixo Y.
     */
    public void adicionarElemento(Node elemento, double positionX, double positionY){
        Node nodo = elemento;
        if(inscreverElementosEmCaixas){
            if(modelo == null){
                nodo = new Caixa(elemento, corFundoCaixa, corBordaCaixa, 4, true);
            }else{
                modelo.limpar_conteudo_caixa();
                modelo.adicionar_conteudo(elemento);
                nodo = modelo;
            }
        }
        elementos.add(nodo);
        nodo.setTranslateX(positionX);
        nodo.setTranslateY(positionY);
        numElementos++;
        if(numElementos == numLimiteAtivador){
            acaoLimite.run();
        }
    }
    
    /**
     * Adiciona um novo elemento seguindo os padrões de posicionamento.
     * @param elemento É o novo elemento
     * @param tryToGessBasedOnLastNode Caso verdadeiro, tentará adivinhar onde 
     * deve se posicionar o próximo objeto, não seguindo o padrão e caso false seguirá o padrão.
     */
    public void adicionarElemento(Node elemento, boolean tryToGessBasedOnLastNode){
        Node ultimoElemento = null;
        double X = 0;
        double Y = 0;
        if(!elementos.isEmpty()){
            ultimoElemento = elementos.get(elementos.size()-1);
            X = ultimoElemento.getTranslateX();
            Y = ultimoElemento.getTranslateY();
            tryToGessBasedOnLastNode = false;
        }
        
        if(tryToGessBasedOnLastNode){
            adicionarElemento(elemento, X + ultimoElemento.getLayoutBounds().getWidth() + espacoEntreElementosX,
                    Y + ultimoElemento.getLayoutBounds().getHeight() + espacoEntreElementosY);//TA FAZENDO EM X E Y... VAI FAZER UMA DIAGONAL -> \
        }else{
            adicionarElemento(elemento, X + positionPaternX + espacoEntreElementosX,
                    Y + positionPaternY + espacoEntreElementosY);
        }
        
    }
    
    /**
     * Adiciona vários elementos seguindo os padrões de posicionamento.
     * @param elemento São os novos elemento.
     * @param tryToGessBasedOnLastNode Caso verdadeiro, tentará adivinhar onde 
     * deve se posicionar o próximo objeto, não seguindo o padrão e caso false seguirá o padrão.
     */
    public void adicionarElemento(boolean tryToGessBasedOnLastNode, Node... elemento){
        for (int i = 0; i < elemento.length; i++) {
            adicionarElemento(elemento[i], tryToGessBasedOnLastNode);
        }
    }
    
    /**
     * Os próximos objetos a serem adicionados serão colocados em uma caixa, sendo esta um quadrado.
     * @param corFundo É a cor do fundo da caixa.
     * @param corBorda É a cor da borda da caixa.
     * @see #caixa
     */
    public void colocarProximosObjetoEmCaixa(Paint corFundo, Paint corBorda){
        inscreverElementosEmCaixas = true;
        this.corBordaCaixa = corBorda;
        this.corFundoCaixa = corFundo;
    }
    
    /**
     * Os próximos objetos a serem adicionados serão colocados em uma caixa, sendo esta passada por parametro.
     * @param modelo É a caixa que será usada para colocar os objetos.
     * @see #caixa
     */
    public void colocarProximosObjetoEmCaixa(Caixa modelo){
        inscreverElementosEmCaixas = true;
        this.modelo = modelo;
    }
    
    /**
     * Para de colocar os objetos dentro de caixas.
     */
    public void pararDeColocarObjetosEmCaixa(){
        inscreverElementosEmCaixas = false;
        modelo = null;
    }
    
    //FAZER COM UM VETOR DIZENDO SE É BOTAO OU ALAVANCA (BOOLEAN)
    //E COM OUTRO DIZENDO COM QUAL ELEMENTOS ESTE SE RELACIONA (UM VETOR PRA CADA ELEMENTO, POIS O MESMO PODE SE RELACIONAR COM MAIS DE 1)
    
    /**
     * Você pode inserir um ID para cada elemento na ordem dos parametros, para interliga-los ou identifica-los.
     * Ao usar numeros negativos, o elemento será do tipo alavanca (use do -2 em diante) e ao usar positivos,
     * será do tipo botão (ao posteriormente inserir eventos), para que ao clicar em um elemento, o outro receba um ActionClear (seja descelecionado)
     * use o mesmo ID para ambos elementos, sendo assim estarão conectados.
     * Se você quer que todos os elementos sejam do tipo botão  e sejam todos independentes um do outro (ao clicar em um,
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
    
    /**
     * Muda o espaço entre os elementos já adicionados e o que virão a ser.
     * (OBS: se o valor de algum dos espaços for Double.NaN, o valor não será alterado)
     * @param espacoEntreElementosX É o espaço entre os elementos (em X), para eles não ficarem grudados um no outro.
     * @param espacoEntreElementosY É o espaço entre os elementos (em Y), para eles não ficarem grudados um no outro.
     */
    public void setEspacoEntreElementos(double espacoEntreElementosX, double espacoEntreElementosY){
        for (int i = 0; i < elementos.size(); i++) {
            Node elementoAtual = elementos.get(i);
            if(espacoEntreElementosX != Double.NaN)
                elementoAtual.setTranslateX(elementoAtual.getTranslateX() + (espacoEntreElementosX - this.espacoEntreElementosX));
            
            if(espacoEntreElementosY != Double.NaN)
                elementoAtual.setTranslateY(elementoAtual.getTranslateY() + (espacoEntreElementosY - this.espacoEntreElementosY));
        }
        setNewEspacoEntreElementos(espacoEntreElementosX, espacoEntreElementosY);
    }
    
    /**
     * Muda o espaço entre os elementos que virão a ser adicionados.
     * (OBS: se o valor de algum dos espaços for Double.NaN, o valor não será alterado)
     * @param espacoEntreElementosX É o espaço entre os elementos (em X), para eles não ficarem grudados um no outro.
     * @param espacoEntreElementosY É o espaço entre os elementos (em Y), para eles não ficarem grudados um no outro.
     */
    public void setNewEspacoEntreElementos(double espacoEntreElementosX, double espacoEntreElementosY){
        if(espacoEntreElementosX != Double.NaN)
            this.espacoEntreElementosX = espacoEntreElementosX;
        if(espacoEntreElementosY != Double.NaN)
            this.espacoEntreElementosY = espacoEntreElementosY;
    }
    
    public void bindEspacoEntreElementos(/*BINDE DO CARAIO*/){
        
    }
    
    /**
     * Muda a medida padrão de distância entre os elementos já adicionados e os que virão a ser.
     * (OBS: se o valor de algum dos espaços for Double.NaN, o valor não será alterado)
     * @param positionPaternX É a medida padrão de distância entre os elementos (eixo X).
     * @param positionPaternY É a medida padrão de distância entre os elementos (eixo Y).
     */
    public void setPositionPatern(double positionPaternX, double positionPaternY){
        for (int i = 0; i < elementos.size(); i++) {
            Node elementoAtual = elementos.get(i);
            if(positionPaternX != Double.NaN)
                elementoAtual.setTranslateX(elementoAtual.getTranslateX() + (positionPaternX - this.positionPaternX));
            
            if(positionPaternY != Double.NaN)
                elementoAtual.setTranslateY(elementoAtual.getTranslateY() + (positionPaternY - this.positionPaternY));
        }
        setNewPositionPatern(positionPaternX, positionPaternY);
    }
    
    /**
     * Muda a medida padrão de distância entre os elementos que virão a ser adicionados.
     * (OBS: se o valor de algum dos espaços for Double.NaN, o valor não será alterado)
     * @param positionPaternX É a medida padrão de distância entre os elementos (eixo X).
     * @param positionPaternY É a medida padrão de distância entre os elementos (eixo Y).
     */
    public void setNewPositionPatern(double positionPaternX, double positionPaternY){
        if(positionPaternX != Double.NaN)
            this.positionPaternX = positionPaternX;
        if(positionPaternY != Double.NaN)
            this.positionPaternY = positionPaternY;
    }
    

    //ver tbm como usar o action cleaner no outoffocus e release, ja que é bem plausivel
    //O RELEASE E O PRESSED TAO BUGANDO, MESMO SE TU NAO TIVER COM O MOUSE NO ELEMENTO ELES VAO FUNCIONAR AAAAAAAAAAAAAAAAAAAAAAAAA
    public void setEventosVisuais(Paint corBordaPadrao, Paint corBordaFoco, Paint corBordaClick){
        for (int i = 0; i < elementos.size(); i++) {
            if(elementos.get(i) instanceof Caixa){
                Caixa elemento = (Caixa) elementos.get(i);
                
                elemento.mudar_cor_borda(corBordaPadrao);
                int num = numeroDeConexao.get(i);
                if(num < 0){
                    elemento.focus.addRunnable(0, () -> {
                        if(!elemento.is_selected)
                            elemento.mudar_cor_borda(corBordaFoco);
                        scene.setCursor(Cursor.HAND);
                    });
                    elemento.outFocus.addRunnable(0, () -> {
                        if(!elemento.is_selected)
                            elemento.mudar_cor_borda(corBordaPadrao);
                        scene.setCursor(Cursor.DEFAULT);
                    });
                    elemento.actionPressed.addRunnable(0, () -> {
                        if(!elemento.is_selected){
                            elemento.mudar_cor_borda(corBordaClick);
                        }else{
                            elemento.mudar_cor_borda(corBordaFoco);
                        }
                    });
                    elemento.actionRelease.addRunnable(0, () -> {
                        if(!elemento.is_selected){
                            if(num < -1){
                                descelecionaOutrosElementos(corBordaPadrao, num);
                            }
                            elemento.mudar_cor_borda(corBordaClick);
                        }else{
                            elemento.mudar_cor_borda(corBordaPadrao);//ÉEEÈÈÈÈÈ``E
                        }
                        elemento.is_selected = !elemento.is_selected;
                    });
                }else{
                    elemento.focus.addRunnable(0, () -> {
                        elemento.mudar_cor_borda(corBordaFoco);
                        scene.setCursor(Cursor.HAND);
                    });
                    elemento.outFocus.addRunnable(0, () -> {
                        elemento.mudar_cor_borda(corBordaPadrao);
                        scene.setCursor(Cursor.DEFAULT);
                    });
                    elemento.actionPressed.addRunnable(0, () -> {
                        elemento.mudar_cor_borda(corBordaClick);
                    });
                    elemento.actionRelease.addRunnable(0, () -> {
                        if(num > 1){
                            descelecionaOutrosElementos(corBordaPadrao, num);
                        }
                        elemento.mudar_cor_borda(corBordaPadrao);
                    });
                }
            }
        }  
    }
    
    private void descelecionaOutrosElementos(Paint corBordaPadrao, int ID){
        for (int i = 0; i < elementos.size(); i++) {
            if(elementos.get(i) instanceof Caixa && Matematicas.modulo(numeroDeConexao.get(i)) == Matematicas.modulo(ID)){
                Caixa elemento = (Caixa) elementos.get(i);
                elemento.is_selected = false;
                elemento.mudar_cor_borda(corBordaPadrao);
                elemento.actionCleaner.run();
            }
        }
    }
    
    public void removerRecursosVisuais(){
        
    }
}