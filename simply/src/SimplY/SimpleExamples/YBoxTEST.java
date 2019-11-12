package SimplY.SimpleExamples;

import SimplY.BasicObjects.Shapes.YCircle;
import SimplY.BasicObjects.Shapes.YLine;
import SimplY.BasicObjects.Shapes.YRectangle;
import SimplY.BasicObjects.Shapes.YText;
import SimplY.NodeManager.YSlidingBar;
import SimplY.NodeManager.YBox;
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

public class YBoxTEST extends Application {

    @Override
    public void start(Stage primaryStage) {
        Pane teste = new Pane();
        
        YBox caixa = new YBox(Color.WHITE, 6, Color.gray(0.4));
        YText texto = new YText("Olá", new Font(16), Color.gray(0.1));
        
        caixa.yAddContent(texto);
        caixa.ySetBoxSizeWithItsContent(60, 30, false, false, true, false);
        caixa.yAlignContents(0.5, 0.5, 0.5, 0.5);
        
        caixa.ySetTranslateX(100, 0);
        caixa.ySetTranslateY(100, 0);
        
        teste.getChildren().addAll(caixa);
        
        Scene scene = new Scene(teste, 1440, 900);
        primaryStage.setTitle("Física 0.65");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
