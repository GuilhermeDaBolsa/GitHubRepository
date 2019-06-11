package executavel;

import Biblioteca.BasicObjects.Formas.Circulo;
import Biblioteca.BasicObjects.Formas.Linha;
import Biblioteca.BasicObjects.Formas.Poligono;
import Biblioteca.OrganizadoresDeNodos.Caixa;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeType;
import javafx.stage.Stage;

public class teste2 extends Application {

    @Override
    public void start(Stage primaryStage) {
        Pane teste = new Pane();
        
        Caixa t1 = new Caixa(Color.ALICEBLUE, 2, Color.GREY);//CLONEAAAA
        t1.ySetTranslateX(500, 0);
        t1.ySetTranslateY(500, 0);
        
        Caixa t2 = new Caixa(t1);//AAA
        t2.ySetTranslateX(600, 0);
        t2.ySetTranslateY(600, 0);
        
        
        Poligono a = new Poligono(
                20, 100,
                100, 100,
                100, 100,
                20, 100
        );
        a.setFill(Color.ALICEBLUE);
        a.ySetStroke(20.0, Color.RED, StrokeType.OUTSIDE, true);
        //a.ySetTranslateX(200, 0);
        //a.ySetTranslateY(200, 0);
        
        Linha c = new Linha(50, 150, 40, Color.DARKSEAGREEN);
        c.ySetTranslateX(0, 0);
        c.ySetTranslateY(0, 0);
        
        Circulo b = new Circulo(2);
        
        b.ySetTranslateX(c.yGetTranslateX(1), 0.5);
        b.ySetTranslateY(c.yGetTranslateY(1), 0.5);
        
        Circulo d = new Circulo(2);
        
        d.ySetTranslateX(c.yGetTranslateX(0), 0.5);
        d.ySetTranslateY(c.yGetTranslateY(0), 0.5);
        
        //Circulo clone = d.clone();     //O CLONE FUNCIONOU, TIRANDO O FATO DE QUE O OBJETO ORIGINAL E SEU CLONE NAO EXITEM JUNTOS (OU AINDA É O MESMO PONTEIRO....)
        //clone.setTranslateX(500);
        //clone.setTranslateY(500);
        //Circulo clone = SerializationUtils.clone(d);
        
        teste.getChildren().addAll(a, c, b, d, t2, t1);
        
        Scene scene = new Scene(teste, 1440, 900);
        primaryStage.setTitle("Física 0.65");
        primaryStage.setScene(scene);
        primaryStage.show();  
    }

    public static void main(String[] args) {
        launch(args);
    }
}