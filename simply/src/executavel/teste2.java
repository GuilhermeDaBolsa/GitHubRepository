package executavel;

import Biblioteca.BasicObjects.Formas.Circulo;
import Biblioteca.BasicObjects.Formas.Linha;
import Biblioteca.BasicObjects.Formas.Poligono;
import Biblioteca.BasicObjects.Formas.Texto;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.DoublePropertyBase;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class teste2 extends Application {

    @Override
    public void start(Stage primaryStage) {
        Pane teste = new Pane();

        /*Caixa t1 = new Caixa(1000, 1000, Color.ALICEBLUE, 5, Color.GREY);//CLONEAAAA
        t1.ySetTranslateX(500, 0);
        t1.ySetTranslateY(500, 0);
        
        Caixa t2 = new Caixa(t1);//AAA
        t2.ySetTranslateX(600, 0);
        t2.ySetTranslateY(600, 0);*/
        /*Poligono a = new Poligono(
                0, 0,
                100, 0,
                50, 100
        );

        a.setFill(Color.ALICEBLUE);
        a.ySetStroke(5.0, Color.RED, StrokeType.OUTSIDE, true);
        a.reta_paralela(new Point2D(0, 0), new Point2D(3, 4));
        a.ySetPosition(600, 600, 0, 0);

        Linha c = new Linha(50, 150, 40, Color.DARKSEAGREEN);
        c.ySetPosition(0, 0, 0, 0);

        Circulo b = new Circulo(2);
        b.ySetPosition(c.yGetTranslateX(1), c.yGetTranslateY(1), 0.5, 0.5);

        Circulo d = new Circulo(2);
        d.ySetPosition(c.yGetTranslateX(0), c.yGetTranslateY(0), 0.5, 0.5);

        Circulo la = new Circulo(20);
        la.ySetPosition(0, 0, 0, 0);
        la.ySetStroke(5.0, null, null, true);

        Circulo lu = new Circulo(200);
        lu.ySetPosition(0, 0, 0, 0);

        la.yBindWidth("width", teste.widthProperty().divide(4), true);
        
        lu.yBindWidth("height", la.yWidthBind(true).multiply(2), true);
        
        lu.yBindTranslateX("x", la.yTranslateXbind(0).subtract(la.yTranslateXbind(0)).add(400), 0.5);
        lu.yBindTranslateY("y", la.yTranslateYbind(0).subtract(la.yTranslateYbind(0)).add(400), 0.5);
        
        lu.yUnbind("y");
        
        a.rotateProperty().bind(lu.yTranslateXbind(0));

        teste.getChildren().addAll(a, c, b, d, lu, la);
        
        teste.setOnMousePressed( event -> System.gc());*/
        double p = 0;
        
        //Texto t = new Texto("VAmo \nTestÁR\n esse\n Necocio.!");
        Texto t = new Texto("VAmo TestÁR esse Necocio.!", Font.font(20), Color.ROSYBROWN);
        Linha l = new Linha(0, 100, 2, Color.BLACK);
        Linha l2 = new Linha(100, 0, 2, Color.BLACK);
        
        t.yBindWidth("width", teste.widthProperty(), true);

        //MUDAR O HIDE TEXT PQ ELE TA ADICIONANDO CARACTERES, NAO PODE, TEM Q SUBSTITUIR NO MAX
        //VER O WIDTH Q TA MEIO ERRADINHO, TIPO UM CARACTERE
        //height ainda ta erradinha, os 70% (um poco mais) é real????
        //fazer la os set height q ainda falta e os setMaxWidth e height e mudar o codigo pra aguenta eles.
        
        teste.getChildren().addAll(t, l, l2);
        
        Scene scene = new Scene(teste, 1440, 900);
        primaryStage.setTitle("Física 0.65");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
