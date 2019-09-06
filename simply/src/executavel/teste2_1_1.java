package executavel;

import Biblioteca.BasicObjects.Formas.Ycircle;
import Biblioteca.BasicObjects.Formas.Yline;
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

public class teste2_1_1 extends Application {

    @Override
    public void start(Stage primaryStage) {
        Pane teste = new Pane();
        
        Circle a = new Circle(2, Color.RED);
        a.setDisable(true);
        yText b = new yText("AAA q saco mano");
        
        YslidingBar k = new YslidingBar(new Circle(50), new YBox(10, 10, Color.WHITE, 2, Color.BLACK), 50, 0, 250, true);
        
        k.ySetTranslateX(400, 0);
        k.ySetTranslateY(400, 0);
        
        
        //ver p faz esse baguiu do bind text 
        k.yDisplayValue(false);
        k.text.yBindTranslateX("SliderTextTranslationX", k.sliderXposition, 0);
        k.text.yBindTranslateY("SliderTextTranslationY", k.sliderYposition, 0);
        k.text.setDisable(true);
        a.layoutXProperty().bind(k.sliderXposition.add(400));
        a.layoutYProperty().bind(k.sliderYposition.add(400));
        
        b.ySetTranslateX(0, 0);
        b.ySetTranslateY(0, 0);
        b.layoutXProperty().bind(k.sliderXposition);
        b.layoutYProperty().bind(k.sliderYposition);

        
        teste.getChildren().addAll(k, a, b);
        
        Scene scene = new Scene(teste, 1440, 900);
        primaryStage.setTitle("Física 0.65");
        primaryStage.setScene(scene);
        primaryStage.show();
        k.ySetValue(100);
        
    }

    public static void main(String[] args) {
        launch(args);
    }
}
