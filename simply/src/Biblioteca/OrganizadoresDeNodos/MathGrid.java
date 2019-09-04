package Biblioteca.OrganizadoresDeNodos;

import static Biblioteca.LogicClasses.Matematicas.modulo;
import Biblioteca.BasicObjects.YvisibleScene;
import Biblioteca.BasicObjects.Formas.Linha;
import Biblioteca.BasicObjects.Formas.Texto;
import java.util.ArrayList;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

//PARAR DE USAR AS COISAS DA MINHA BIBLIOTECA PRA POUPAR MEM ONDE NAO PRECISA TIPO AS LINHAS AQUI
//FAZER UM FORZAO PRA DESENHAR AS LINHAS CERTINHO, PCAUSA DO PROBLEMA DO EIXO

public class MathGrid extends YvisibleScene {
    public ArrayList<Node> objetos = new ArrayList();
    private ArrayList<Point2D> posicoes_objetos = new ArrayList();
    
    public double MIN_DISTANCIA_LINHAS = 30;
    public double MAX_DISTANCIA_LINHAS = 150;
    public double INCREMENTO_DIST_LINHAS = 2.5;
    public double distancia_linhas = 50;
    public double escala_grade = 5;
    
    public double grossura_linhas = 1;
    public double tamanho_numeros = 10;

    private double zeroX = 0;
    private double zeroY = 0;
    public double largura_grade;
    public double altura_grade;

    private double deltaZeroX = 0;
    private double deltaZeroY = 0;
    private double mousePositionX = 0;
    private double mousePositionY = 0;

    private final String formatoNumeros = "%.2f";

    /**
     * Construtor para criar a grade matemática (as medidas são baseadas no PANE (PRECISA ESTAR EN YN PANE) cuja grade está inserida).
     */
    public MathGrid(double largura, double altura) {
        ySetGridSize(largura, altura);
    }
    
    public void ySetGridSize(double width, double height){
        largura_grade = width;
        altura_grade = height;
        atualizar();
    }

    /**
     * Método para configurar as linhas da grade conforme as váriaveis que
     * indicam os pontos do centro e do fim da aplicação.
     */
    private void configurar_linhasEmarcadores() {
        //Onde esta o ponto 0,0
        double meioR_telaX = meio_tela_relativoX();
        double meioR_telaY = meio_tela_relativoY();
        double finalR_telaX = final_tela_relativoX();
        double finalR_telaY = final_tela_relativoY();

        //Onde é o 'eixo central' agora e o numero q esta nele
        int numeroZeroX = (int) Math.round(-zeroX_escalado() / distancia_linhas);
        int numeroZeroY = (int) Math.round(-zeroY_escalado() / distancia_linhas);
        double eixoX = meioR_telaX + numeroZeroX * distancia_linhas;
        double eixoY = meioR_telaY + numeroZeroY * distancia_linhas;

        /*verifica se a tela está numa localização próxima a origem da grade (o ponto 0,0) em ambos os eixos.
        Muda a localização dos numeros dependendo da onde a tela está.*/
        double posNumerosXemY = tamanho_numeros*2;
        double posNumerosYemX = tamanho_numeros*2;
                                            //MUDAR ESSES TESTES PQ TAO MEIO BUGADO
        if (modulo(zeroX_escalado()) < largura_grade / 2 - tamanho_numeros*2) {
            posNumerosYemX = meioR_telaX + 3;
        }else if (zeroX_escalado() > 0) {
            posNumerosYemX = (int) largura_grade - tamanho_numeros*2;
        }
        if (modulo(zeroY_escalado()) < altura_grade / 2 - tamanho_numeros*2) {
            posNumerosXemY = meioR_telaY + 3;
        }else if (zeroY_escalado() > 0) {
            posNumerosXemY = (int) altura_grade - tamanho_numeros*2;
        }

        //Desenha as linhas e os numeros do eixo X em pares um antes de um eixo e um depois.
        adicionar_linha(eixoX, 0, 0, altura_grade, Color.GAINSBORO);
        adicionar_numero(String.format(formatoNumeros, numeroZeroX * escala_grade), eixoX - tamanho_numeros, posNumerosXemY);
        int cont = 1;
        while (largura_grade/2 + cont * distancia_linhas < largura_grade) {
            adicionar_linha(eixoX + cont * distancia_linhas, 0, 0, altura_grade, Color.GAINSBORO);
            adicionar_numero(String.format(formatoNumeros, numeroZeroX * escala_grade + cont * escala_grade), eixoX + cont * distancia_linhas - tamanho_numeros, posNumerosXemY);
            
            adicionar_linha(eixoX - cont * distancia_linhas, 0, 0, altura_grade, Color.GAINSBORO);
            adicionar_numero(String.format(formatoNumeros, numeroZeroX * escala_grade - cont * escala_grade), eixoX - cont * distancia_linhas - tamanho_numeros, posNumerosXemY);

            cont++;
        }
        
        //Desenha as linhas e os numeros do eixo Y em pares um antes de um eixo e um depois.
        adicionar_linha(0, eixoY, largura_grade, 0, Color.GAINSBORO);
        adicionar_numero(String.format(formatoNumeros, -numeroZeroY * escala_grade), posNumerosYemX, eixoY - tamanho_numeros);
        cont = 1;
        while (altura_grade/2 + cont * distancia_linhas < altura_grade) {
            adicionar_linha(0, eixoY + cont * distancia_linhas, largura_grade, 0, Color.GAINSBORO);
            adicionar_linha(0, eixoY - cont * distancia_linhas, largura_grade, 0, Color.GAINSBORO);

            adicionar_numero(String.format(formatoNumeros, -numeroZeroY * escala_grade + cont * escala_grade), posNumerosYemX, eixoY - cont * distancia_linhas - tamanho_numeros);
            adicionar_numero(String.format(formatoNumeros, -numeroZeroY * escala_grade - cont * escala_grade), posNumerosYemX, eixoY + cont * distancia_linhas - tamanho_numeros);

            cont++;
        }
        
        //Desenhas as linhas do ponto de origem (0,0) em maior destaque.
        if(zeroX_escalado() <= largura_grade/2 && zeroX_escalado() >= -largura_grade/2)
            adicionar_linha(meioR_telaX, 0, 0, altura_grade, Color.BLACK);
        //adicionar_numero(numeros_x, "0", meioR_telaX + 3, meioR_telaY + tamanho_numeros + 3);
        if(zeroY_escalado() <= altura_grade/2 && zeroY_escalado() >= -altura_grade/2)
            adicionar_linha(0, meioR_telaY, largura_grade, 0, Color.BLACK);
        //adicionar_numero(numeros_y, "0", meioR_telaX - tamanho_numeros, meioR_telaY - 3);
    }

    /**
     * Método para adicionar um objeto à grade.
     *
     * @param objeto O objeto a ser adicionado.
     * @param X Coordenada X onde o objeto deve ser posicionado.
     * @param Y Coordenada Y onde o objeto deve ser posicionado.
     */
    public void adicionar_objeto(Node objeto, double X, double Y) {
        objetos.add(objeto);
        double adaptedX = X / escala_grade * distancia_linhas;
        double adaptedY = -Y / escala_grade * distancia_linhas;//MENOS Y POIS QUANTO MAIOR O Y MAIS PARA BAIXO VAI...
        objeto.setTranslateX(meio_tela_relativoX() + adaptedX);
        objeto.setTranslateY(meio_tela_relativoY() + adaptedY);
        posicoes_objetos.add(new Point2D(X, -Y));
        atualizar();
    }

    /**
     * Método para criar uma linha e adicioná-la em sua array_list.
     * @param X Coordenada X
     * @param Y Coordenada Y
     * @param largura Largura da linha
     * @param altura Altura da linha
     */
    private void adicionar_linha(double X, double Y, double largura, double altura, Color cor) {
        Linha linha = new Linha(X, Y, X + largura, Y + altura, grossura_linhas, cor);
        this.getChildren().add(linha);
    }

    /**
     * Método para criar um numeros e adicioná-lo em sua array_list.
     * @param X Coordenada X
     * @param Y Coordenada Y
     */
    private void adicionar_numero(String numero, double X, double Y) {
        Texto text = new Texto(numero, Font.font(tamanho_numeros), Color.BLACK);
        text.setTranslateX(X);
        text.ySetTranslateY(Y, 0);
        this.getChildren().add(text);
    }

    //JUNTAR OS 3 METODOS ABAIXO SE POSSIVEL PLSSSSS
    
    /**
     * Coloca uma thread para verificar quando o tamanho da aplicação é alterada
     * para alterar o tamanho da grade.
     */
    public void binda_tamanho(ObservableValue<? extends Number> largura, ObservableValue<? extends Number> altura) {
        largura_grade = largura.getValue().doubleValue();
        altura_grade = altura.getValue().doubleValue();
        largura.addListener((obs, oldVal, newVal) -> {
            largura_grade = largura.getValue().doubleValue();
            atualizar();
        });

        altura.addListener((obs, oldVal, newVal) -> {
            altura_grade = altura.getValue().doubleValue();
            atualizar();
        });
        atualizar();
    }

    public void bind_tela_movel() {
        this.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                // record a delta distance for the drag and drop operation.
                deltaZeroX = zeroX_escalado() - mouseEvent.getSceneX();
                deltaZeroY = zeroY_escalado() - mouseEvent.getSceneY();
            }
        });
        this.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                zeroX = (mouseEvent.getSceneX() + deltaZeroX)/distancia_linhas*escala_grade;
                zeroY = (mouseEvent.getSceneY() + deltaZeroY)/distancia_linhas*escala_grade;
                atualizar();
            }
        });
    }
    
    //public void bind_tela_movel(Bind em um Node pivo){}; TIPO UM PERSONAGEM QUE SE MEXE
   
    public void bind_rodinha_mouse() {
        this.setOnScroll((ScrollEvent event) -> {
            mousePositionX = (event.getSceneX() - this.localToScene(this.getBoundsInLocal()).getMinX() - meio_tela_relativoX())/distancia_linhas*escala_grade;
            mousePositionY = (event.getSceneY() - this.localToScene(this.getBoundsInLocal()).getMinY() - meio_tela_relativoY())/distancia_linhas*escala_grade;
            
            double deltaY = event.getDeltaY();
            if (deltaY > 0) {
                distancia_linhas += INCREMENTO_DIST_LINHAS;
                zeroX -= (((mousePositionX + zeroX)/escala_grade)*INCREMENTO_DIST_LINHAS)/distancia_linhas*escala_grade;
                zeroY -= (((mousePositionY + zeroY)/escala_grade)*INCREMENTO_DIST_LINHAS)/distancia_linhas*escala_grade;
            } else {
                distancia_linhas -= INCREMENTO_DIST_LINHAS;
                zeroX += (((mousePositionX + zeroX)/escala_grade)*INCREMENTO_DIST_LINHAS)/distancia_linhas*escala_grade;
                zeroY += (((mousePositionY + zeroY)/escala_grade)*INCREMENTO_DIST_LINHAS)/distancia_linhas*escala_grade;
            }

            if (distancia_linhas > MAX_DISTANCIA_LINHAS) {
                distancia_linhas = MIN_DISTANCIA_LINHAS;
                escala_grade /= MAX_DISTANCIA_LINHAS / MIN_DISTANCIA_LINHAS;
            } else if (distancia_linhas < MIN_DISTANCIA_LINHAS) {
                distancia_linhas = MAX_DISTANCIA_LINHAS;
                escala_grade *= MAX_DISTANCIA_LINHAS / MIN_DISTANCIA_LINHAS;
            }
            atualizar();
        });
    }
    
    //public void bind_rodinha_mouse(Bind em um Node pivo){};

    private double meio_tela_relativoX() {
        return largura_grade/2 + zeroX_escalado();
    }

    private double meio_tela_relativoY() {
        return altura_grade/2 + zeroY_escalado();
    }

    private double final_tela_relativoX() {
        return largura_grade + zeroX_escalado();
    }

    private double final_tela_relativoY() {
        return altura_grade + zeroY_escalado();
    }
    
    /**
     * @return O quanto o ponto 0 foi deslocado no eixo X em pixels.
     */
    private double zeroX_escalado(){      
        return (zeroX / escala_grade)*distancia_linhas;
    }
    
    /**
     * @return O quanto o ponto 0 foi deslocado no eixo Y em pixels.
     */
    private double zeroY_escalado(){
        return (zeroY / escala_grade)*distancia_linhas;
    }

    /**
     * Método para atualizar a grade e tudo nela.
     */
    public void atualizar() {
        this.getChildren().clear();

        configurar_linhasEmarcadores();
        
        //reposiciona os objetos
        for (int i = 0; i < objetos.size(); i++) {
            double posX = meio_tela_relativoX() + posicoes_objetos.get(i).getX() / escala_grade * distancia_linhas;
            double posY = meio_tela_relativoY() + posicoes_objetos.get(i).getY() / escala_grade * distancia_linhas;
            if(posX > 0 && posX < this.localToScene(this.getBoundsInLocal()).getMinX() + largura_grade && 
                    posY > 0 && posY < this.localToScene(this.getBoundsInLocal()).getMinY() + altura_grade){
                Node objeto = objetos.get(i);
                objeto.setTranslateX(posX);
                objeto.setTranslateY(posY);
                this.getChildren().add(objeto);
            }
        }
    }
    
    //SE USAR THREAD, NAO FICAR ATUALIZANDO QUE NEM LOCO, APENAS CHAMAR QUANDO PRECISA, MESMO DENTRO DE UMA TREAD
    
    //AVERIGUAR ISSO Q ELE TA SEMPRE ESCONDENDO ELEMENTOS, NAO BOTANDO ELES NA TELA POR MOTIVO DE PORRA NENHUM
}