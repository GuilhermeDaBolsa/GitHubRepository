package Biblioteca.BasicObjects.Formas;

import Biblioteca.BasicObjects.VisibleObjectHandler;
import java.io.File;
import java.net.MalformedURLException;
import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

//FAZER UM SETMAXWIDTH, que nem o label, que dai quando o texto chega no limite, ele é cortado e aparece reticencias pequenas (ex: estratos...)
//O TEXTO GUARDA SEMPRE UM ESPAÇO PROS ACENTOS, MESMO QUE ELES NAO EXISTAO, ENTAO O TEXTO PODE SER MAIS ALTO DO QUE REALMENTE APARENTA...

public class Texto extends Text implements Forma{
    
    public static Font carregar_fonte(String caminho_fonte, double tamanho){
        try {
            return Font.loadFont(new File(caminho_fonte).toURI().toURL().toExternalForm(), tamanho);
        } catch (MalformedURLException ex) {
            System.out.println("Deu Pauuuu");
        }
        return Font.font("Arial", tamanho);
    }
    
    public Texto(String texto, Font fonte, Color cor){
        super(texto);
        
        if (fonte != null)
            setFont(fonte);
        
        setFill(cor == null ? Color.BLACK : cor);
        
        ySetTranslateY(0, 0);
    }

    public Texto(String texto) {
        this(texto, null, null);
    }
    
    public void ySetFont(Font font){
        double where_wasX = yGetTranslateX(0);
        double where_wasY = yGetTranslateY(0);
        
        setFont(font);
                
        ySetTranslateX(where_wasX, 0);
        ySetTranslateY(where_wasY, 0);
    }

    /**
     * @return Retorna a largura do espaço que o texto ocupa.
     */
    @Override
    public double yGetWidth() {
        return getLayoutBounds().getWidth();
    }

    /**
     * @return Retorna a altura do espaço que o texto ocupa.
     */
    @Override
    public double yGetHeight() {
        return getLayoutBounds().getHeight()*0.7;
    }
    
    @Override
    public double yGetTranslateX(double pivo) {
        return getTranslateX() + yGetWidth()*pivo;
    }

    @Override
    public double yGetTranslateY(double pivo) {
        return getTranslateY() + yGetHeight()*(pivo - 1);
    }

    @Override
    public void ySetTranslateX(double position, double pivo) {
        VisibleObjectHandler.setTranslateX(this, position, pivo);
    }

    @Override
    public void ySetTranslateY(double position, double pivo) {
        VisibleObjectHandler.setTranslateY(this, position, pivo - 0.7);
    }

    @Override
    public void ySetTranslateZ(double position, double pivo) {
        VisibleObjectHandler.setTranslateZ(this, position, pivo);
    }

    /**
     * 
     * @param stroke_width
     * @param stroke_color
     * @param stroke_type 
     * @param move_with_new_stroke_width If a new stroke_width is defined, it will "grow from inside" keeping the object where it was, unless this parameter is true.
     * @see #setStrokeType(javafx.scene.shape.StrokeType) 
     */
    @Override
    public void ySetStroke(Double stroke_width, Paint stroke_color, StrokeType stroke_type, boolean move_with_new_stroke_width) {
        double where_wasX = yGetTranslateX(0);
        double where_wasY = yGetTranslateY(0);
        
        if(stroke_color != null)
            setStroke(stroke_color);
        if(stroke_width != null)
            setStrokeWidth(stroke_width);
        if(stroke_type != null)
            setStrokeType(stroke_type);
            
        if(move_with_new_stroke_width){
            ySetTranslateX(where_wasX, 0);
            ySetTranslateY(where_wasY, 0);
        }
    }
    
    public void aparecer_texto(double duracao, Point2D ponto_relativo) {
        Animation animacao = new Transition() {
            {
                setCycleDuration(Duration.seconds(duracao));
            }

            @Override
            protected void interpolate(double frac) {
                int tamanho_texto = getText().length();
                int n = Math.round(tamanho_texto * (float) frac);
                setText(getText().substring(0, n));
                setTranslateX(ponto_relativo.getX() - getLayoutBounds().getWidth()/2);
                setTranslateY(ponto_relativo.getY());
            }
        };
        animacao.play();
    }
    
    public void aparecer_texto_fixo(double duracao_por_caractere) {
        Animation animacao = new Transition() {
            {
                setCycleDuration(Duration.seconds(duracao_por_caractere * getText().length()));
            }

            @Override
            protected void interpolate(double frac) {
                int tamanho_texto = getText().length();
                int n = Math.round(tamanho_texto * (float) frac);
                setText(getText().substring(0, n));
            }
        };
        animacao.play();
    }
    
    public void reorganiza_texto_conforme_espaco(double espacoX){
        espacoX *= 0.78; //???????? FUNCIONA//
        int tamanhoDeUmDigito = (int) (yGetWidth()/ getText().length());
        int digitos_por_linha = (int) (espacoX/tamanhoDeUmDigito);
        String novo_texto = "";
        
        for (int i = 0; i < getText().length(); i++) {
            novo_texto += getText().charAt(i);
            if((i+1) % digitos_por_linha == 0 && i > 0){
                novo_texto += "\n";
            }
        }
        setText(novo_texto);
    }
}
