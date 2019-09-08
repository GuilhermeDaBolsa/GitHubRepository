package SimplY.NodeManager;

import SimplY.BasicObjects.YVisibleScene;
import SimplY.BasicObjects.Shapes.YLine;
import SimplY.BasicObjects.Shapes.YText;
import java.util.ArrayList;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import static SimplY.Math.YMath.yModulo;

//PARAR DE USAR AS COISAS DA MINHA BIBLIOTECA PRA POUPAR MEM ONDE NAO PRECISA TIPO AS LINHAS AQUI
//FAZER UM FORZAO PRA DESENHAR AS LINHAS CERTINHO, PCAUSA DO PROBLEMA DO EIXO

/**
 * Class created to do a math grid (like geogebra), it is not 100% but can be used.
 */
public class YMathGrid extends YVisibleScene {
    public ArrayList<Node> objects = new ArrayList();
    private ArrayList<Point2D> objects_positions = new ArrayList();
    
    public double MIN_LINE_DISTANCE = 30;
    public double MAX_LINE_DISTANCE = 150;
    public double LINE_DISTANCE_INCREMENT = 2.5;
    public double LINE_DISTANCE = 50;
    public double GRID_SCALE = 5;
    
    public double LINE_WIDTH = 1;
    public double NUMBERS_FONT_SIZE = 10;

    private double ZEROX = 0;
    private double ZEROY = 0;
    public double GRID_WIDTH;
    public double GRID_HEIGHT;

    private double deltaZeroX = 0;
    private double deltaZeroY = 0;
    private double mousePositionX = 0;
    private double mousePositionY = 0;

    private final String formatoNumeros = "%.2f";

    /**
     * Construtor para criar a grade matemática (as medidas são baseadas no PANE (PRECISA ESTAR EN YN PANE) cuja grade está inserida).
     */
    public YMathGrid(double largura, double altura) {
        ySetGridSize(largura, altura);
    }
    
    public void ySetGridSize(double width, double height){
        GRID_WIDTH = width;
        GRID_HEIGHT = height;
        yRefresh();
    }

    /**
     * Método para configurar as linhas da grade conforme as váriaveis que
     * indicam os pontos do centro e do fim da aplicação.
     */
    private void createLinesAndNumbers() {
        //Onde esta o ponto 0,0
        double meioR_telaX = meio_tela_relativoX();
        double meioR_telaY = meio_tela_relativoY();
        double finalR_telaX = final_tela_relativoX();
        double finalR_telaY = final_tela_relativoY();

        //Onde é o 'eixo central' agora e o numero q esta nele
        int numeroZeroX = (int) Math.round(-zeroX_escalado() / LINE_DISTANCE);
        int numeroZeroY = (int) Math.round(-zeroY_escalado() / LINE_DISTANCE);
        double eixoX = meioR_telaX + numeroZeroX * LINE_DISTANCE;
        double eixoY = meioR_telaY + numeroZeroY * LINE_DISTANCE;

        /*verifica se a tela está numa localização próxima a origem da grade (o ponto 0,0) em ambos os eixos.
        Muda a localização dos numeros dependendo da onde a tela está.*/
        double posNumerosXemY = NUMBERS_FONT_SIZE*2;
        double posNumerosYemX = NUMBERS_FONT_SIZE*2;
                                            //MUDAR ESSES TESTES PQ TAO MEIO BUGADO
        if (yModulo(zeroX_escalado()) < GRID_WIDTH / 2 - NUMBERS_FONT_SIZE*2) {
            posNumerosYemX = meioR_telaX + 3;
        }else if (zeroX_escalado() > 0) {
            posNumerosYemX = (int) GRID_WIDTH - NUMBERS_FONT_SIZE*2;
        }
        if (yModulo(zeroY_escalado()) < GRID_HEIGHT / 2 - NUMBERS_FONT_SIZE*2) {
            posNumerosXemY = meioR_telaY + 3;
        }else if (zeroY_escalado() > 0) {
            posNumerosXemY = (int) GRID_HEIGHT - NUMBERS_FONT_SIZE*2;
        }

        //Desenha as linhas e os numeros do eixo X em pares um antes de um eixo e um depois.
        createLine(eixoX, 0, 0, GRID_HEIGHT, Color.GAINSBORO);
        createNumber(String.format(formatoNumeros, numeroZeroX * GRID_SCALE), eixoX - NUMBERS_FONT_SIZE, posNumerosXemY);
        int cont = 1;
        while (GRID_WIDTH/2 + cont * LINE_DISTANCE < GRID_WIDTH) {
            createLine(eixoX + cont * LINE_DISTANCE, 0, 0, GRID_HEIGHT, Color.GAINSBORO);
            createNumber(String.format(formatoNumeros, numeroZeroX * GRID_SCALE + cont * GRID_SCALE), eixoX + cont * LINE_DISTANCE - NUMBERS_FONT_SIZE, posNumerosXemY);
            
            createLine(eixoX - cont * LINE_DISTANCE, 0, 0, GRID_HEIGHT, Color.GAINSBORO);
            createNumber(String.format(formatoNumeros, numeroZeroX * GRID_SCALE - cont * GRID_SCALE), eixoX - cont * LINE_DISTANCE - NUMBERS_FONT_SIZE, posNumerosXemY);

            cont++;
        }
        
        //Desenha as linhas e os numeros do eixo Y em pares um antes de um eixo e um depois.
        createLine(0, eixoY, GRID_WIDTH, 0, Color.GAINSBORO);
        createNumber(String.format(formatoNumeros, -numeroZeroY * GRID_SCALE), posNumerosYemX, eixoY - NUMBERS_FONT_SIZE);
        cont = 1;
        while (GRID_HEIGHT/2 + cont * LINE_DISTANCE < GRID_HEIGHT) {
            createLine(0, eixoY + cont * LINE_DISTANCE, GRID_WIDTH, 0, Color.GAINSBORO);
            createLine(0, eixoY - cont * LINE_DISTANCE, GRID_WIDTH, 0, Color.GAINSBORO);

            createNumber(String.format(formatoNumeros, -numeroZeroY * GRID_SCALE + cont * GRID_SCALE), posNumerosYemX, eixoY - cont * LINE_DISTANCE - NUMBERS_FONT_SIZE);
            createNumber(String.format(formatoNumeros, -numeroZeroY * GRID_SCALE - cont * GRID_SCALE), posNumerosYemX, eixoY + cont * LINE_DISTANCE - NUMBERS_FONT_SIZE);

            cont++;
        }
        
        //Desenhas as linhas do ponto de origem (0,0) em maior destaque.
        if(zeroX_escalado() <= GRID_WIDTH/2 && zeroX_escalado() >= -GRID_WIDTH/2)
            createLine(meioR_telaX, 0, 0, GRID_HEIGHT, Color.BLACK);
        //adicionar_numero(numeros_x, "0", meioR_telaX + 3, meioR_telaY + NUMBERS_FONT_SIZE + 3);
        if(zeroY_escalado() <= GRID_HEIGHT/2 && zeroY_escalado() >= -GRID_HEIGHT/2)
            createLine(0, meioR_telaY, GRID_WIDTH, 0, Color.BLACK);
        //adicionar_numero(numeros_y, "0", meioR_telaX - NUMBERS_FONT_SIZE, meioR_telaY - 3);
    }

    /**
     * Método para adicionar um objeto à grade.
     *
     * @param objeto O objeto a ser adicionado.
     * @param X Coordenada X onde o objeto deve ser posicionado.
     * @param Y Coordenada Y onde o objeto deve ser posicionado.
     */
    public void yAdd(Node objeto, double X, double Y) {
        objects.add(objeto);
        double adaptedX = X / GRID_SCALE * LINE_DISTANCE;
        double adaptedY = -Y / GRID_SCALE * LINE_DISTANCE;//MENOS Y POIS QUANTO MAIOR O Y MAIS PARA BAIXO VAI...
        objeto.setTranslateX(meio_tela_relativoX() + adaptedX);
        objeto.setTranslateY(meio_tela_relativoY() + adaptedY);
        objects_positions.add(new Point2D(X, -Y));
        yRefresh();
    }

    /**
     * Método para criar uma linha e adicioná-la em sua array_list.
     * @param X Coordenada X
     * @param Y Coordenada Y
     * @param largura Largura da linha
     * @param altura Altura da linha
     */
    private void createLine(double X, double Y, double largura, double altura, Color cor) {
        YLine linha = new YLine(X, Y, X + largura, Y + altura, LINE_WIDTH, cor);
        this.getChildren().add(linha);
    }

    /**
     * Método para criar um numeros e adicioná-lo em sua array_list.
     * @param X Coordenada X
     * @param Y Coordenada Y
     */
    private void createNumber(String numero, double X, double Y) {
        YText text = new YText(numero, Font.font(NUMBERS_FONT_SIZE), Color.BLACK);
        text.setTranslateX(X);
        text.ySetTranslateY(Y, 0);
        this.getChildren().add(text);
    }

    //JUNTAR OS 3 METODOS ABAIXO SE POSSIVEL PLSSSSS
    
    /**
     * Coloca uma thread para verificar quando o tamanho da aplicação é alterada
     * para alterar o tamanho da grade.
     */
    public void yBindSize(ObservableValue<? extends Number> largura, ObservableValue<? extends Number> altura) {
        GRID_WIDTH = largura.getValue().doubleValue();
        GRID_HEIGHT = altura.getValue().doubleValue();
        largura.addListener((obs, oldVal, newVal) -> {
            GRID_WIDTH = largura.getValue().doubleValue();
            yRefresh();
        });

        altura.addListener((obs, oldVal, newVal) -> {
            GRID_HEIGHT = altura.getValue().doubleValue();
            yRefresh();
        });
        yRefresh();
    }

    public void yBindMouseMoveble() {
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
                ZEROX = (mouseEvent.getSceneX() + deltaZeroX)/LINE_DISTANCE*GRID_SCALE;
                ZEROY = (mouseEvent.getSceneY() + deltaZeroY)/LINE_DISTANCE*GRID_SCALE;
                yRefresh();
            }
        });
    }
    
    //public void yBindMouseMoveble(Bind em um Node pivo){}; TIPO UM PERSONAGEM QUE SE MEXE
   
    public void yBindMouseScrollZoom() {
        this.setOnScroll((ScrollEvent event) -> {
            mousePositionX = (event.getSceneX() - this.localToScene(this.getBoundsInLocal()).getMinX() - meio_tela_relativoX())/LINE_DISTANCE*GRID_SCALE;
            mousePositionY = (event.getSceneY() - this.localToScene(this.getBoundsInLocal()).getMinY() - meio_tela_relativoY())/LINE_DISTANCE*GRID_SCALE;
            
            double deltaY = event.getDeltaY();
            if (deltaY > 0) {
                LINE_DISTANCE += LINE_DISTANCE_INCREMENT;
                ZEROX -= (((mousePositionX + ZEROX)/GRID_SCALE)*LINE_DISTANCE_INCREMENT)/LINE_DISTANCE*GRID_SCALE;
                ZEROY -= (((mousePositionY + ZEROY)/GRID_SCALE)*LINE_DISTANCE_INCREMENT)/LINE_DISTANCE*GRID_SCALE;
            } else {
                LINE_DISTANCE -= LINE_DISTANCE_INCREMENT;
                ZEROX += (((mousePositionX + ZEROX)/GRID_SCALE)*LINE_DISTANCE_INCREMENT)/LINE_DISTANCE*GRID_SCALE;
                ZEROY += (((mousePositionY + ZEROY)/GRID_SCALE)*LINE_DISTANCE_INCREMENT)/LINE_DISTANCE*GRID_SCALE;
            }

            if (LINE_DISTANCE > MAX_LINE_DISTANCE) {
                LINE_DISTANCE = MIN_LINE_DISTANCE;
                GRID_SCALE /= MAX_LINE_DISTANCE / MIN_LINE_DISTANCE;
            } else if (LINE_DISTANCE < MIN_LINE_DISTANCE) {
                LINE_DISTANCE = MAX_LINE_DISTANCE;
                GRID_SCALE *= MAX_LINE_DISTANCE / MIN_LINE_DISTANCE;
            }
            yRefresh();
        });
    }
    
    //public void yBindMouseScrollZoom(Bind em um Node pivo){};

    private double meio_tela_relativoX() {
        return GRID_WIDTH/2 + zeroX_escalado();
    }

    private double meio_tela_relativoY() {
        return GRID_HEIGHT/2 + zeroY_escalado();
    }

    private double final_tela_relativoX() {
        return GRID_WIDTH + zeroX_escalado();
    }

    private double final_tela_relativoY() {
        return GRID_HEIGHT + zeroY_escalado();
    }
    
    /**
     * @return O quanto o ponto 0 foi deslocado no eixo X em pixels.
     */
    private double zeroX_escalado(){      
        return (ZEROX / GRID_SCALE)*LINE_DISTANCE;
    }
    
    /**
     * @return O quanto o ponto 0 foi deslocado no eixo Y em pixels.
     */
    private double zeroY_escalado(){
        return (ZEROY / GRID_SCALE)*LINE_DISTANCE;
    }

    /**
     * Refresh the math and it's objects.
     */
    public void yRefresh() {
        this.getChildren().clear();

        createLinesAndNumbers();
        
        //reposiciona os objects
        for (int i = 0; i < objects.size(); i++) {
            double posX = meio_tela_relativoX() + objects_positions.get(i).getX() / GRID_SCALE * LINE_DISTANCE;
            double posY = meio_tela_relativoY() + objects_positions.get(i).getY() / GRID_SCALE * LINE_DISTANCE;
            if(posX > 0 && posX < this.localToScene(this.getBoundsInLocal()).getMinX() + GRID_WIDTH && 
                    posY > 0 && posY < this.localToScene(this.getBoundsInLocal()).getMinY() + GRID_HEIGHT){
                Node objeto = objects.get(i);
                objeto.setTranslateX(posX);
                objeto.setTranslateY(posY);
                this.getChildren().add(objeto);
            }
        }
    }
    
    //SE USAR THREAD, NAO FICAR ATUALIZANDO QUE NEM LOCO, APENAS CHAMAR QUANDO PRECISA, MESMO DENTRO DE UMA TREAD
    
    //AVERIGUAR ISSO Q ELE TA SEMPRE ESCONDENDO ELEMENTOS, NAO BOTANDO ELES NA TELA POR MOTIVO DE PORRA NENHUM
}