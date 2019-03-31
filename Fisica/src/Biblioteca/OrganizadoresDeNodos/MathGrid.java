package Biblioteca.OrganizadoresDeNodos;

import Biblioteca.BasicObjects.Formas.Linha;
import static Biblioteca.LogicClasses.Matematicas.modulo;
import Biblioteca.BasicObjects.Formas.Texto;
import Biblioteca.InteractiveObjects.ObjetoInteragivel;
import java.util.ArrayList;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class MathGrid extends ObjetoInteragivel {
    Node nodoPai;
    
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

    private ArrayList<Node> objetos = new ArrayList();
    private ArrayList<Point2D> posicoes_objetos = new ArrayList();

    private final String formatoNumeros = "%.2f";

    /**
     * Construtor para criar a grade matemática (as medidas são baseadas no PANE (PRECISA ESTAR EN YN PANE) cuja grade está inserida).
     * @param grossuraLinhas A grossura das linhas da grade.
     */
    public MathGrid(double grossuraLinhas, double largura, double altura) {
        grossura_linhas = grossuraLinhas;
        final_da_telaX = largura;
        final_da_telaY = altura;
    }
    
    public void mathgridSetedUp(){
        this.nodoPai = this.getParent();
        
        bind_rodinha_mouse();
        bind_tela_movel();
        refresh();
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
        double posNumerosXemY = 20;
        double posNumerosYemX = 15;
                                            //MUDAR ESSES TESTES PQ TAO MEIO BUGADO
        if (modulo(zeroX_escalado()) < final_da_telaX / 2 - 42) {
            posNumerosYemX = meioR_telaX + 3;
        }else if (zeroX_escalado() > 0) {
            posNumerosYemX = (int) final_da_telaX - 42;
        }
        if (modulo(zeroY_escalado()) < final_da_telaY / 2 - 68) {
            posNumerosXemY = meioR_telaY + 3;
        }else if (zeroY_escalado() > 0) {
            posNumerosXemY = (int) final_da_telaY - 68;
        }

        //Desenha as linhas e os numeros do eixo X em pares um antes de um eixo e um depois.
        adicionar_linha(eixoX, 0, 0, final_da_telaY, Color.GAINSBORO);
        adicionar_numero(String.format(formatoNumeros, numeroZeroX * escala_grade), eixoX - tamanho_numeros, posNumerosXemY);
        int cont = 1;
        while (final_da_telaX/2 + cont * distancia_linhas < finalTelaX) {
            adicionar_linha(eixoX + cont * distancia_linhas, 0, 0, final_da_telaY, Color.GAINSBORO);
            adicionar_linha(eixoX - cont * distancia_linhas, 0, 0, final_da_telaY, Color.GAINSBORO);

            adicionar_numero(String.format(formatoNumeros, numeroZeroX * escala_grade + cont * escala_grade), eixoX + cont * distancia_linhas - tamanho_numeros, posNumerosXemY);
            adicionar_numero(String.format(formatoNumeros, numeroZeroX * escala_grade - cont * escala_grade), eixoX - cont * distancia_linhas - tamanho_numeros, posNumerosXemY);

            cont++;
        }
        //Desenha as linhas e os numeros do eixo Y em pares um antes de um eixo e um depois.
        adicionar_linha(0, eixoY, final_da_telaX, 0, Color.GAINSBORO);
        adicionar_numero(String.format(formatoNumeros, -numeroZeroY * escala_grade), posNumerosYemX, eixoY - tamanho_numeros);
        cont = 1;
        while (final_da_telaY/2 + cont * distancia_linhas < finalTelaY) {
            adicionar_linha(0, eixoY + cont * distancia_linhas, final_da_telaX, 0, Color.GAINSBORO);
            adicionar_linha(0, eixoY - cont * distancia_linhas, final_da_telaX, 0, Color.GAINSBORO);

            adicionar_numero(String.format(formatoNumeros, -numeroZeroY * escala_grade + cont * escala_grade), posNumerosYemX, eixoY - cont * distancia_linhas - tamanho_numeros);
            adicionar_numero(String.format(formatoNumeros, -numeroZeroY * escala_grade - cont * escala_grade), posNumerosYemX, eixoY + cont * distancia_linhas - tamanho_numeros);

            cont++;
        }

        //Desenhas as linhas do ponto de origem (0,0) em maior destaque.
        adicionar_linha(meioR_telaX, 0, 0, final_da_telaY, Color.BLACK);
        //adicionar_numero(numeros_x, "0", meioR_telaX + 3, meioR_telaY + tamanho_numeros + 3);
        adicionar_linha(0, meioR_telaY, final_da_telaX, 0, Color.BLACK);
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
        refresh();
    }

    /**
     * Método para criar uma linha e adicioná-la em sua array_list.
     * @param X Coordenada X
     * @param Y Coordenada Y
     * @param largura Largura da linha
     * @param altura Altura da linha
     */
    private void adicionar_linha(double X, double Y, double largura, double altura, Color cor) {
        this.getChildren().add(new Linha(X, Y, X + largura, Y + altura, grossura_linhas, cor));
    }

    /**
     * Método para criar um numeros e adicioná-lo em sua array_list.
     * @param X Coordenada X
     * @param Y Coordenada Y
     */
    private void adicionar_numero(String numero, double X, double Y) {
        Texto texto = new Texto(numero, Font.font(tamanho_numeros), null);
        texto.setTranslateX(X);
        texto.setTranslateY(Y);
        this.getChildren().add(texto);
    }

    /**
     * Coloca uma thread para verificar quando o tamanho da aplicação é alterada
     * para alterar o tamanho da grade.
     */
    public void binda_tamanho() {
        /*nodoPai.widthProperty().addListener((obs, oldVal, newVal) -> {
            final_da_telaX = nodoPai.getWidth();
            refresh();
        });

        nodoPai.heightProperty().addListener((obs, oldVal, newVal) -> {
            final_da_telaY = nodoPai.getHeight();
            refresh();
        });*/
    }

    private void bind_tela_movel() {
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
                refresh();
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
        this.getChildren().clear();

        configurar_linhasEmarcadores();
        
        //reposiciona os objetos
        for (int i = 0; i < objetos.size(); i++) {
            objetos.get(i).setTranslateX(meio_tela_relativoX() + posicoes_objetos.get(i).getX() / escala_grade * distancia_linhas);
            objetos.get(i).setTranslateY(meio_tela_relativoY() + posicoes_objetos.get(i).getY() / escala_grade * distancia_linhas);
        }

        this.getChildren().addAll(objetos);
    }
    
    //SE USAR THREAD, NAO FICAR ATUALIZANDO QUE NEM LOCO, APENAS CHAMAR QUANDO PRECISA, MESMO DENTRO DE UMA TREAD
}