package executavel;

import Biblioteca.BasicObjects.YvisibleScene;
import Biblioteca.BasicObjects.Formas.Ycircle;
import Biblioteca.OrganizadoresDeNodos.Ydescription;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class Entidade extends YvisibleScene{
    public static int numero_entidades = 0;
    private Ycircle forma;
    public double carga;
    private Ydescription descricao;

    public Entidade(double tamanho, double carga, Paint cor, String localizacao) {
        this.forma = new Ycircle(tamanho, cor, 1.5, Color.CORAL.darker());
        this.forma.setTranslateX(0);
        this.forma.setTranslateY(0);
        this.carga = carga;
        this.descricao = new Ydescription(150, 90, "PONTO", "Localizado em:", "("+localizacao+")");
        this.descricao.yDeactivate();
        this.descricao.setTranslateX(50);
        this.descricao.setTranslateY(30);
        forma.setOnMouseEntered((event) -> {
            this.descricao.yActivate();
        });
        
        forma.setOnMouseExited((event) -> {
            this.descricao.yDeactivate();
        });
        
        getChildren().addAll(forma, descricao);
    }
}