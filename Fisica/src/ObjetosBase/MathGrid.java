package ObjetosBase;

import ObjetosBase.visibleObjects.BarraDeslisanteGOD;
import ObjetosBase.visibleObjects.Linha;
import static ObjetosBase.logicaEcore.Matematicas.modulo;
import ObjetosBase.visibleObjects.Texto;
import java.util.ArrayList;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MathGrid extends Pane {
    Stage primarystage;
    BarraDeslisanteGOD barra_escala;
    
    private final double MIN_DISTANCIA_LINHAS = 30;
    private final double MAX_DISTANCIA_LINHAS = 150;
    private final double INCREMENTO_DIST_LINHAS = 2.5;
    private double distancia_linhas = 50;
    private double escala_grade = 5;
    
    private double grossura_linhas;
    private double tamanho_numeros = 10;

    private double zeroX = 0;
    private double zeroY = 0;
    private double final_da_telaX;
    private double final_da_telaY;

    private double deltaZeroX = 0;
    private double deltaZeroY = 0;
    private double mousePositionX = 0;
    private double mousePositionY = 0;

    private boolean desenhar_grade = true;
    private boolean desenhar_numeros = true;
    private boolean desenhar_slider = true;
    private boolean desenhar_objetos = true;

    private ArrayList<Linha> linhas_x = new ArrayList();
    private ArrayList<Texto> numeros_x = new ArrayList();
    private ArrayList<Linha> linhas_y = new ArrayList();
    private ArrayList<Texto> numeros_y = new ArrayList();
    private ArrayList<Node> objetos = new ArrayList();
    private ArrayList<Point2D> posicoes_objetos = new ArrayList();

    private final String formatoNumeros = "%.2f";

    /**
     * Construtor para criar a grade matemática.
     * @param primarystage O stage da aplicação.
     * @param grossura A grossura das linhas da grade.
     */
    public MathGrid(Stage primarystage, double largura_inicial, double altura_inicial, double grossura) {
        this.primarystage = primarystage;
        grossura_linhas = grossura;

        final_da_telaX = largura_inicial;
        final_da_telaY = altura_inicial;

        configurar_linhasEmarcadores();

        criar_slider();
        bind_rodinha_mouse();
        binda_tamanho();
        bind_tela_movel();

        this.getChildren().addAll(linhas_x);
        this.getChildren().addAll(linhas_y);
        this.getChildren().addAll(numeros_x);
        this.getChildren().addAll(numeros_y);
        this.getChildren().add(barra_escala);
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

        //Onde é o 'eixo central' agora
        int numeroZeroX = (int) (-zeroX_escalado() / distancia_linhas);
        int numeroZeroY = (int) (-zeroY_escalado() / distancia_linhas);
        double eixoX = meioR_telaX + numeroZeroX * distancia_linhas;
        double eixoY = meioR_telaY + numeroZeroY * distancia_linhas;

        double finalTelaX = final_da_telaX*1.2;
        double finalTelaY = final_da_telaY*1.2;

        /*verifica se a tela está numa localização próxima a origem da grade (o ponto 0,0) em ambos os eixos.
        Muda a localização dos numeros dependendo da onde a tela está.*/
        double bordaYdoeixoX = 40;
        double bordaXdoeixoY = 20;

        if (modulo(zeroX_escalado()) < final_da_telaX / 2 - 25) {
            bordaXdoeixoY = meioR_telaX + 3;
        } else if (zeroX_escalado() > 0) {
            bordaXdoeixoY = (int) final_da_telaX - 40;
        }
        if (modulo(zeroY_escalado()) < final_da_telaY / 2 - 40) {
            bordaYdoeixoX = meioR_telaY + tamanho_numeros + 3;
        } else if (zeroY_escalado() > 0) {
            bordaYdoeixoX = (int) final_da_telaY - 30;
        }

        //Desenha as linhas e os numeros do eixo X em pares um antes de um eixo e um depois.
        adicionar_linha(linhas_x, eixoX, 0, 0, final_da_telaY, Color.GAINSBORO);
        adicionar_numero(numeros_x, String.format(formatoNumeros, numeroZeroX * escala_grade), eixoX - tamanho_numeros, bordaYdoeixoX);
        int cont = 1;
        while (final_da_telaX/2 + cont * distancia_linhas < finalTelaX) {
            adicionar_linha(linhas_x, eixoX + cont * distancia_linhas, 0, 0, final_da_telaY, Color.GAINSBORO);
            adicionar_linha(linhas_x, eixoX - cont * distancia_linhas, 0, 0, final_da_telaY, Color.GAINSBORO);

            adicionar_numero(numeros_x, String.format(formatoNumeros, numeroZeroX * escala_grade + cont * escala_grade), eixoX + cont * distancia_linhas - tamanho_numeros, bordaYdoeixoX);
            adicionar_numero(numeros_x, String.format(formatoNumeros, numeroZeroX * escala_grade - cont * escala_grade), eixoX - cont * distancia_linhas - tamanho_numeros, bordaYdoeixoX);

            cont++;
        }
        //Desenha as linhas e os numeros do eixo Y em pares um antes de um eixo e um depois.
        adicionar_linha(linhas_y, 0, eixoY, final_da_telaX, 0, Color.GAINSBORO);
        adicionar_numero(numeros_y, String.format(formatoNumeros, -numeroZeroY * escala_grade), bordaXdoeixoY, eixoY + tamanho_numeros / 2 - 2);
        cont = 1;
        while (final_da_telaY/2 + cont * distancia_linhas < finalTelaY) {
            adicionar_linha(linhas_y, 0, eixoY + cont * distancia_linhas, final_da_telaX, 0, Color.GAINSBORO);
            adicionar_linha(linhas_y, 0, eixoY - cont * distancia_linhas, final_da_telaX, 0, Color.GAINSBORO);

            adicionar_numero(numeros_y, String.format(formatoNumeros, -numeroZeroY * escala_grade + cont * escala_grade), bordaXdoeixoY, eixoY - cont * distancia_linhas + 4);
            adicionar_numero(numeros_y, String.format(formatoNumeros, -numeroZeroY * escala_grade - cont * escala_grade), bordaXdoeixoY, eixoY + cont * distancia_linhas + tamanho_numeros / 2 - 2);

            cont++;
        }

        //Desenhas as linhas do ponto de origem (0,0) em maior destaque.
        adicionar_linha(linhas_x, meioR_telaX, 0, 0, final_da_telaY, Color.BLACK);
        //adicionar_numero(numeros_x, "0", meioR_telaX + 3, meioR_telaY + tamanho_numeros + 3);
        adicionar_linha(linhas_y, 0, meioR_telaY, final_da_telaX, 0, Color.BLACK);
        //adicionar_numero(numeros_y, "0", meioR_telaX - tamanho_numeros, meioR_telaY - 3);
    }

    /**
     * Apaga todas as linhas da grade.
     */
    private void apaga_linhas() {
        this.getChildren().removeAll(linhas_x);
        this.getChildren().removeAll(linhas_y);
        linhas_x.clear();
        linhas_y.clear();
    }

    /**
     * Apaga todos os numeros da grade.
     */
    private void apaga_numeros() {
        this.getChildren().removeAll(numeros_x);
        this.getChildren().removeAll(numeros_y);
        numeros_x.clear();
        numeros_y.clear();
    }

    private void apaga_objetos() {
        this.getChildren().removeAll(objetos);
    }

    private void apaga_slider() {
        this.getChildren().remove(barra_escala);
    }

    private void reposiciona_objetos() {
        for (int i = 0; i < objetos.size(); i++) {
            objetos.get(i).setTranslateX(meio_tela_relativoX() + posicoes_objetos.get(i).getX() / escala_grade * distancia_linhas);
            objetos.get(i).setTranslateY(meio_tela_relativoY() + posicoes_objetos.get(i).getY() / escala_grade * distancia_linhas);
        }
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
        posicoes_objetos.add(new Point2D(X, -Y));//MENOS Y POIS QUANTO MAIOR O Y MAIS PARA BAIXO VAI...
        refresh();
    }

    /**
     * Método para criar uma linha e adicioná-la em sua array_list.
     *
     * @param lista A lista de linhas (array_list)
     * @param X Coordenada X
     * @param Y Coordenada Y
     * @param largura Largura da linha
     * @param altura Altura da linha
     */
    private void adicionar_linha(ArrayList<Linha> lista, double X, double Y, double largura, double altura, Color cor) {
        if (desenhar_grade) {
            lista.add(new Linha(X, Y, X + largura, Y + altura, grossura_linhas, cor));
        }
    }

    /**
     * Método para criar um numeros e adicioná-lo em sua array_list.
     *
     * @param lista A lista de numeros (array_list)
     * @param X Coordenada X
     * @param Y Coordenada Y
     */
    private void adicionar_numero(ArrayList<Texto> lista, String numero, double X, double Y) {
        if (desenhar_numeros) {
            Texto texto = new Texto(numero, Font.font(tamanho_numeros), null);
            texto.setTranslateX(X);
            texto.setTranslateY(Y);

            lista.add(texto);
        }
    }

    /**
     * Cria o slider que altera o valor da escala.
     */
    private void criar_slider() {
        if (desenhar_slider) {
            barra_escala = new BarraDeslisanteGOD(160, 12, 1, 100, 50, 1, null);
            barra_escala.translateXProperty().bind(this.primarystage.widthProperty().subtract(30));
            barra_escala.translateYProperty().bind(this.primarystage.heightProperty().subtract(196));
        }
    }

    /**
     * Coloca uma thread para verificar quando o tamanho da aplicação é alterada
     * para alterar o tamanho da grade.
     */
    public void binda_tamanho() {
        primarystage.widthProperty().addListener((obs, oldVal, newVal) -> {
            final_da_telaX = primarystage.getWidth();
            refresh();
        });

        primarystage.heightProperty().addListener((obs, oldVal, newVal) -> {
            final_da_telaY = primarystage.getHeight();
            refresh();
        });
    }

    public void bind_tela_movel() {
        this.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                // record a delta distance for the drag and drop operation.
                if (!barra_escala.isPressed()) {
                    deltaZeroX = zeroX_escalado() - mouseEvent.getSceneX();
                    deltaZeroY = zeroY_escalado() - mouseEvent.getSceneY();
                }
            }
        });
        this.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (!barra_escala.isPressed()) {
                    zeroX = (mouseEvent.getSceneX() + deltaZeroX)/distancia_linhas*escala_grade;
                    zeroY = (mouseEvent.getSceneY() + deltaZeroY)/distancia_linhas*escala_grade;
                    refresh();
                }
            }
        });
    }
    
    //public void bind_tela_movel(Bind em um Node pivo){};
   
    private void bind_rodinha_mouse() {
        this.setOnScroll((ScrollEvent event) -> {
            mousePositionX = (event.getSceneX() - meio_tela_relativoX())/distancia_linhas*escala_grade;
            mousePositionY = (event.getSceneY() - meio_tela_relativoY())/distancia_linhas*escala_grade;
            
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
            refresh();
        });
    }
    
    //public void bind_rodinha_mouse(Bind em um Node pivo){};

    private double meio_tela_relativoX() {
        return final_da_telaX/2 + zeroX_escalado();
    }

    private double meio_tela_relativoY() {
        return final_da_telaY/2 + zeroY_escalado();
    }

    private double final_tela_relativoX() {
        return final_da_telaX + zeroX_escalado();
    }

    private double final_tela_relativoY() {
        return final_da_telaY + zeroY_escalado();
    }
    
    private double zeroX_escalado(){      
        return (zeroX / escala_grade)*distancia_linhas;
    }
    
    private double zeroY_escalado(){
        return (zeroY / escala_grade)*distancia_linhas;
    }

    /**
     * Método para atualizar a grade e tudo nela.
     */
    public void refresh() {
        apaga_linhas();
        apaga_numeros();
        apaga_objetos();

        configurar_linhasEmarcadores();
        reposiciona_objetos();

        this.getChildren().addAll(0, numeros_x);
        this.getChildren().addAll(1, numeros_y);
        this.getChildren().addAll(2, linhas_x);
        this.getChildren().addAll(3, linhas_y);
        this.getChildren().addAll(this.getChildren().size()-1, objetos);
    }
    
    //SE USAR THREAD, NAO FICAR ATUALIZANDO QUE NEM LOCO, APENAS CHAMAR QUANDO PRECISA, MESMO DENTRO DE UMA TREAD
}