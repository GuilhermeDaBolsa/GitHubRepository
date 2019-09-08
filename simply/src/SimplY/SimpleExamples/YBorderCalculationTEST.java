package SimplY.SimpleExamples;

import SimplY.BasicObjects.Shapes.YCircle;
import SimplY.BasicObjects.Shapes.YLine;
import SimplY.BasicObjects.Shapes.YPolygon;
import SimplY.BasicObjects.Shapes.YRectangle;
import SimplY.BasicObjects.Shapes.YText;
import SimplY.NodeManager.YBox;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.DoublePropertyBase;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

public class YBorderCalculationTEST extends Application {
    double paX = 300;
    double paY = 300;
    double pbX = 290;
    double pbY = 290;
    double pcX = 500;
    double pcY = 300;
    double pdX = 290;
    double pdY = 310;
    
    @Override
    public void start(Stage primaryStage) {
        Pane teste = new Pane();
        
        YRectangle as = new YRectangle(200, 150);
        as.setFill(Color.RED);
        as.ySetTranslateX(0, 0.5);
        as.ySetTranslateY(0, 0.5);
        
        YPolygon p = new YPolygon(
                paX, paY,
                pbX, pbY,
                pcX, pcY,
                pdX, pdY
        );
        p.setFill(Color.ALICEBLUE);
        p.ySetStroke(10.0, Color.BLACK, StrokeType.OUTSIDE, true);
        
        YCircle a = new YCircle(4);
        YCircle b = new YCircle(4);
        YCircle c = new YCircle(4);
        YCircle d = new YCircle(4);
        YCircle o = new YCircle(3, Color.RED);
        
        o.ySetTranslateX(p.yGetTranslateX(0.5), 0.5);
        o.ySetTranslateY(p.yGetTranslateY(0.5), 0.5);
        
        a.ySetPosition(paX + p.getTranslateX(), paY + p.getTranslateY(), 0.5, 0.5);
        b.ySetPosition(pbX + p.getTranslateX(), pbY + p.getTranslateY(), 0.5, 0.5);
        c.ySetPosition(pcX + p.getTranslateX(), pcY + p.getTranslateY(), 0.5, 0.5);
        d.ySetPosition(pdX + p.getTranslateX(), pdY + p.getTranslateY(), 0.5, 0.5);
        
        a.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                // record a delta distance for the drag and drop operation.
                paX = a.yGetTranslateX(0.5) - mouseEvent.getSceneX();
                paY = a.yGetTranslateY(0.5) - mouseEvent.getSceneY();
            }
        });
        b.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                // record a delta distance for the drag and drop operation.
                pbX = b.yGetTranslateX(0.5) - mouseEvent.getSceneX();
                pbY = b.yGetTranslateY(0.5) - mouseEvent.getSceneY();
            }
        });
        c.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                // record a delta distance for the drag and drop operation.
                pcX = c.yGetTranslateX(0.5) - mouseEvent.getSceneX();
                pcY = c.yGetTranslateY(0.5) - mouseEvent.getSceneY();
            }
        });
        d.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                // record a delta distance for the drag and drop operation.
                pdX = d.yGetTranslateX(0.5) - mouseEvent.getSceneX();
                pdY = d.yGetTranslateY(0.5) - mouseEvent.getSceneY();
            }
        });
            
        a.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                a.ySetTranslateX(mouseEvent.getSceneX() + paX, 0.5);
                a.ySetTranslateY(mouseEvent.getSceneY() + paY, 0.5);
                
                p.getPoints().clear();
                p.getPoints().addAll(a.yGetTranslateX(0.5) - p.getTranslateX(), a.yGetTranslateY(0.5) - p.getTranslateY(),
                        b.yGetTranslateX(0.5) - p.getTranslateX(), b.yGetTranslateY(0.5) - p.getTranslateY(),
                        c.yGetTranslateX(0.5) - p.getTranslateX(), c.yGetTranslateY(0.5) - p.getTranslateY(),
                        d.yGetTranslateX(0.5) - p.getTranslateX(), d.yGetTranslateY(0.5) - p.getTranslateY());
                p.change_in_points();
                //t.resizeBoxWithItsContent(false, false, true, false);
            }
        });
        b.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                b.ySetTranslateX(mouseEvent.getSceneX() + pbX, 0.5);
                b.ySetTranslateY(mouseEvent.getSceneY() + pbY, 0.5);
                
                p.getPoints().clear();
                p.getPoints().addAll(a.yGetTranslateX(0.5) - p.getTranslateX(), a.yGetTranslateY(0.5) - p.getTranslateY(),
                        b.yGetTranslateX(0.5) - p.getTranslateX(), b.yGetTranslateY(0.5) - p.getTranslateY(),
                        c.yGetTranslateX(0.5) - p.getTranslateX(), c.yGetTranslateY(0.5) - p.getTranslateY(),
                        d.yGetTranslateX(0.5) - p.getTranslateX(), d.yGetTranslateY(0.5) - p.getTranslateY());
                p.change_in_points();
            }
        });
        c.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                c.ySetTranslateX(mouseEvent.getSceneX() + pcX, 0.5);
                c.ySetTranslateY(mouseEvent.getSceneY() + pcY, 0.5);
                
                p.getPoints().clear();
                p.getPoints().addAll(a.yGetTranslateX(0.5) - p.getTranslateX(), a.yGetTranslateY(0.5) - p.getTranslateY(),
                        b.yGetTranslateX(0.5) - p.getTranslateX(), b.yGetTranslateY(0.5) - p.getTranslateY(),
                        c.yGetTranslateX(0.5) - p.getTranslateX(), c.yGetTranslateY(0.5) - p.getTranslateY(),
                        d.yGetTranslateX(0.5) - p.getTranslateX(), d.yGetTranslateY(0.5) - p.getTranslateY());
                p.change_in_points();
            }
        });
        d.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                d.ySetTranslateX(mouseEvent.getSceneX() + pdX, 0.5);
                d.ySetTranslateY(mouseEvent.getSceneY() + pdY, 0.5);
                
                p.getPoints().clear();
                p.getPoints().addAll(a.yGetTranslateX(0.5) - p.getTranslateX(), a.yGetTranslateY(0.5) - p.getTranslateY(),
                        b.yGetTranslateX(0.5) - p.getTranslateX(), b.yGetTranslateY(0.5) - p.getTranslateY(),
                        c.yGetTranslateX(0.5) - p.getTranslateX(), c.yGetTranslateY(0.5) - p.getTranslateY(),
                        d.yGetTranslateX(0.5) - p.getTranslateX(), d.yGetTranslateY(0.5) - p.getTranslateY());
                p.change_in_points();
            }
        });
            
        teste.setOnMousePressed((event) -> {
            if(event.isSecondaryButtonDown()){
                p.ySetTranslateX(0, 0);
                p.ySetTranslateY(0, 0);
                //o.ySetTranslateX(p.yGetTranslateX(0), 0.5);
                //o.ySetTranslateY(p.yGetTranslateY(0), 0.5);
                //as.ySetRotate(as.yRotation.getAngle()-10, 1, 1);
            }else{
                //as.ySetRotate(as.yRotation.getAngle() + 10, 0.5, 0.5);
            }
                
            //System.gc();
        });
        
        //TEXTO:
        //height ainda ta erradinha, os 70% (um poco mais) é real????
        //falta os setMaxWidth e height e mudar o codigo pra aguenta eles.
        //os reajusta tamanho automatico da caixa ta meio quebrado
        
        teste.getChildren().addAll(as, p, a, b, c, d, o);
        
        Scene scene = new Scene(teste, 1440, 900);
        primaryStage.setTitle("Física 0.65");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
