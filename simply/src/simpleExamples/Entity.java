package simpleExamples;

import SimplY.BasicObjects.YVisibleScene;
import SimplY.BasicObjects.Shapes.YCircle;
import SimplY.NodeManager.YDescription;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class Entity extends YVisibleScene{
    public static int numero_entidades = 0;
    private YCircle forma;
    public double carga;
    private YDescription descricao;

    public Entity(double tamanho, double carga, Paint cor, String localizacao) {
        this.forma = new YCircle(tamanho, cor, 1.5, Color.CORAL.darker());
        this.forma.setTranslateX(0);
        this.forma.setTranslateY(0);
        this.carga = carga;
        this.descricao = new YDescription(150, 90, "PONTO", "Localizado em:", "("+localizacao+")");
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