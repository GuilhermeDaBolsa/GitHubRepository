package Biblioteca.OrganizadoresDeNodos;

import Biblioteca.BasicObjects.CenaVisivel;
import Biblioteca.InteractiveObjects.Runnables;
import java.util.ArrayList;
import javafx.scene.Node;
import javafx.scene.paint.Paint;

public class TabelaMenu extends CenaVisivel{
    public ArrayList<Node> elementos = new ArrayList();
    public boolean inscreverElementosEmCaixas;
    private double positionPaternX;
    private double positionPaternY;
    private double espacoEntreElementosX;
    private double espacoEntreElementosY;
    
    public int numLimiteAtivador;
    public Runnables acaoLimite;
    
    private Caixa modelo = null;
    
    
    // VER PRA FAZER BASEADO NO NODO PAI, TIPO... QUANTO ESPAÇO TEM, QUANTO MEDe O PROXIMO ELEMENTO... ETC
    //espacoEntreElementosX É o espaço entre os elementos (em X), para eles não ficarem grudados um no outro.
    //espacoEntreElementosY É o espaço entre os elementos (em Y), para eles não ficarem grudados um no outro.
    
    //E MUDAR O POSITION PATTERN PRA TIPO PODE SER 1px e SE O ESPAÇO ENTRE ELEMENTOS FOR MAIOR VAI O ESPAÇO ENTRE ELEMENTOS, EU ACHO
    
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
    public TabelaMenu(double positionPaternX, double positionPaternY, double espacoElementosX, double espacoElementosY,
            int numLimiteAtivador, boolean tryToGessNextPosition, Node... vetElemento) {
        this.numLimiteAtivador = numLimiteAtivador;
        this.positionPaternX = positionPaternX;
        this.positionPaternY = positionPaternY;
        this.espacoEntreElementosX = espacoElementosX;
        this.espacoEntreElementosY = espacoElementosY;
        
        if(vetElemento != null){
            adicionarElemento(tryToGessNextPosition, vetElemento);
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
               
            }else{
                modelo.limpar_caixa();
                modelo.add(elemento);
                nodo = modelo;
            }
        }
        elementos.add(nodo);
        nodo.setTranslateX(positionX);
        nodo.setTranslateY(positionY);
        if(elementos.size()+1 == numLimiteAtivador){
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
     * @param strokeWidth É a grossura da borda da caixa.
     * @see #caixa
     */
    public void colocarProximosObjetoEmCaixa(Paint corFundo, Paint corBorda, double strokeWidth){
        inscreverElementosEmCaixas = true;
        modelo = new Caixa(corFundo, strokeWidth, corBorda);
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
    
    public void bindEspacoEntreElementos(/*BINDE DO CARAIO*/){
        
    }
}