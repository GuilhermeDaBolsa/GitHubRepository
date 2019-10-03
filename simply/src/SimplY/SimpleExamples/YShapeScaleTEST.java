package SimplY.SimpleExamples;

import SimplY.BasicObjects.Shapes.YCircle;
import SimplY.BasicObjects.Shapes.YLine;
import SimplY.BasicObjects.Shapes.YPolygon;
import SimplY.BasicObjects.Shapes.YRectangle;
import SimplY.BasicObjects.Shapes.YText;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeType;
import javafx.stage.Stage;

public class YShapeScaleTEST extends Application  {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane teste = new Pane();
        
        YCircle o = new YCircle(20);
        o.ySetScaleX(2, true);
        o.ySetScaleY(2, true);
        o.ySetTranslateX(1100, 1);
        o.ySetTranslateY(800, 1);
        
        YLine l = new YLine(10, 10, 200, 150, 10, Color.BURLYWOOD);
        l.ySetTranslateX(0, 0);
        l.ySetTranslateY(0, 0);
        l.ySetScaleX(4, true);
        l.ySetScaleY(4, true);
        
        YRectangle r = new YRectangle(100, 50);
        r.ySetStroke(3.0, Color.GREENYELLOW, StrokeType.OUTSIDE, true);
        r.ySetScaleX(3, true);
        r.ySetScaleY(3, true);
        r.ySetTranslateX(1, 0);
        r.ySetTranslateY(799, 1);
        
        YPolygon poly = new YPolygon(
                10, 0,
                50, 70,
                24, 92
        );
        poly.ySetStroke(4.0, Color.RED, StrokeType.OUTSIDE, true);
        poly.ySetScaleX(2, true);
        poly.ySetScaleY(2, true);
        poly.ySetTranslateX(1100, 1);
        poly.ySetTranslateY(0, 0);
        
        YText t = new YText("Olá, eu estou no centro da tela (eu espero :T)");
        t.ySetScaleX(2, false);
        t.ySetScaleY(2, false);
        t.ySetWidth(500, true, true);
        t.ySetTranslateX(1100/2, 0.5);
        t.ySetTranslateY(800/2, 0.5);
        
        teste.getChildren().addAll(o, l, r, poly, t);
        
        Scene scene = new Scene(teste, 1100, 800);
        primaryStage.setTitle("Física 0.65");
        primaryStage.setScene(scene);
        primaryStage.show();  
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
