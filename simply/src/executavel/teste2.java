package executavel;

import Biblioteca.BasicObjects.Formas.Circulo;
import Biblioteca.BasicObjects.Formas.Linha;
import Biblioteca.BasicObjects.Formas.Poligono;
import Biblioteca.BasicObjects.Formas.Texto;
import Biblioteca.OrganizadoresDeNodos.Caixa;
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
import javafx.stage.Stage;

public class teste2 extends Application {
    double paX;
    double paY;
    double pbX;
    double pbY;
    double pcX;
    double pcY;

    @Override
    public void start(Stage primaryStage) {
        Pane teste = new Pane();
        
        Poligono p = new Poligono(
                0, 0,
                100, 0,
                100, 100
        );
        p.setFill(Color.ALICEBLUE);
        p.ySetStroke(20.0, Color.RED, StrokeType.OUTSIDE, true);
        
        Circulo a = new Circulo(4);
        Circulo b = new Circulo(4);
        Circulo c = new Circulo(4);
        
        a.ySetPosition(100 + p.getTranslateX(), 100 + p.getTranslateY(), 0.5, 0.5);
        b.ySetPosition(200 + p.getTranslateX(), 100 + p.getTranslateY(), 0.5, 0.5);
        c.ySetPosition(200 + p.getTranslateX(), 200 + p.getTranslateY(), 0.5, 0.5);
        
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
            
        a.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                a.ySetTranslateX(mouseEvent.getSceneX() + paX, 0.5);
                a.ySetTranslateY(mouseEvent.getSceneY() + paY, 0.5);
                
                p.getPoints().clear();
                p.getPoints().addAll(a.yGetTranslateX(0.5) - p.getTranslateX(), a.yGetTranslateY(0.5) - p.getTranslateY(), b.yGetTranslateX(0.5) - p.getTranslateX(), b.yGetTranslateY(0.5) - p.getTranslateY(), c.yGetTranslateX(0.5) - p.getTranslateX(), c.yGetTranslateY(0.5) - p.getTranslateY());
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
                p.getPoints().addAll(a.yGetTranslateX(0.5) - p.getTranslateX(), a.yGetTranslateY(0.5) - p.getTranslateY(), b.yGetTranslateX(0.5) - p.getTranslateX(), b.yGetTranslateY(0.5) - p.getTranslateY(), c.yGetTranslateX(0.5) - p.getTranslateX(), c.yGetTranslateY(0.5) - p.getTranslateY());
                p.change_in_points();
            }
        });
        c.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                c.ySetTranslateX(mouseEvent.getSceneX() + pcX, 0.5);
                c.ySetTranslateY(mouseEvent.getSceneY() + pcY, 0.5);
                
                p.getPoints().clear();
                p.getPoints().addAll(a.yGetTranslateX(0.5) - p.getTranslateX(), a.yGetTranslateY(0.5) - p.getTranslateY(), b.yGetTranslateX(0.5) - p.getTranslateX(), b.yGetTranslateY(0.5) - p.getTranslateY(), c.yGetTranslateX(0.5) - p.getTranslateX(), c.yGetTranslateY(0.5) - p.getTranslateY());
                p.change_in_points();
            }
        });
            
        teste.setOnMousePressed((event) -> {
            if(event.isSecondaryButtonDown()){
                p.ySetTranslateX(0, 0);
                p.ySetTranslateY(0, 0);
            }
                
            //System.gc();
        });
        //TEXTO:
        //height ainda ta erradinha, os 70% (um poco mais) é real????
        //falta os setMaxWidth e height e mudar o codigo pra aguenta eles.
        //os reajusta tamanho automatico da caixa ta meio quebrado
        
        teste.getChildren().addAll(p, a, b, c);
        
        Scene scene = new Scene(teste, 1440, 900);
        primaryStage.setTitle("Física 0.65");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
