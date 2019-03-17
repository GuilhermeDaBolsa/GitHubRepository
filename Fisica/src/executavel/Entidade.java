package executavel;

import Biblioteca.OrganizadoresDeNodos.Descricao;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class Entidade extends Pane{
    public static int numero_entidades = 0;
    private Shape forma;
    public double carga;
    private Descricao descricao;

    public Entidade(double tamanho, double carga, Paint cor, String localizacao) {
        this.forma = new Circle(tamanho, cor);
        this.forma.setStrokeWidth(1);
        this.forma.setStroke(Color.BLACK);
        this.carga = carga;
        this.descricao = new Descricao(150, 90, "PONTO", "Localizado em:", "("+localizacao+")");
        this.descricao.setDisable(true);
        this.descricao.setVisible(false);
        this.descricao.setTranslateX(50);
        this.descricao.setTranslateY(30);
        forma.setOnMouseEntered((event) -> {
            this.descricao.setDisable(false);
            this.descricao.setVisible(true);
        });
        
        forma.setOnMouseExited((event) -> {
            this.descricao.setDisable(true);
            this.descricao.setVisible(false);
        });
        
        getChildren().addAll(forma, descricao);
    }
    
}
