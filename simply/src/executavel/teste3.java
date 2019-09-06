package executavel;

import Biblioteca.BasicObjects.Formas.Ycircle;
import Biblioteca.BasicObjects.Formas.Yline;
import Biblioteca.BasicObjects.Formas.Ypolygon;
import Biblioteca.BasicObjects.Formas.Yrectangle;
import Biblioteca.BasicObjects.Formas.yText;
import Biblioteca.OrganizadoresDeNodos.YslidingBar;
import Biblioteca.OrganizadoresDeNodos.YBox;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.DoublePropertyBase;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

public class teste3 extends Application {

    @Override
    public void start(Stage primaryStage) {
        double X = 405;
        double Y = 405;
        
        Pane teste = new Pane();
        
        Ycircle p = new Ycircle(3, Color.RED);
        p.ySetTranslateX(X, 0.5);
        p.ySetTranslateY(Y, 0.5);
        
        Ypolygon r = new Ypolygon(
                150, 100,
                200, 200,
                400, 400
        );
        Yline l = new Yline(60, 0, 10, Color.BLUEVIOLET);
        l.ySetTranslateX(X, 0.5);
        l.ySetTranslateY(390, 0);
        
        teste.widthProperty().addListener((observable, oldValue, newValue) -> {
            int inc = 1;
            if(newValue.doubleValue() < oldValue.doubleValue())
                inc = -1;
            
            r.yRotateBy(inc * 3, X, Y);
            l.yRotateBy(inc * 5, X, Y);
        });
        
        
        teste.getChildren().addAll(r, l, p);
        
        Scene scene = new Scene(teste, 1440, 900);
        primaryStage.setTitle("Física 0.65");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
