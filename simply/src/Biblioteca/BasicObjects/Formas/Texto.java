package Biblioteca.BasicObjects.Formas;

import Biblioteca.BasicObjects.VisibleObjectHandler;
import java.io.File;
import java.net.MalformedURLException;
import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class Texto extends Text implements Forma{
    
    public Texto(String texto, Font fonte, Color cor){
        super(texto);
        
        if (fonte != null) {
            setFont(fonte);
        }
        if(cor == null){
            setFill(Color.BLACK);
        }else{
            setFill(cor);
        }
        
        setTranslateY(getHeight());
    }

    public Texto(String texto) {
        this(texto, null, null);
        setTranslateY(getHeight());
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
        int tamanhoDeUmDigito = (int) (getWidth()/ getText().length());
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
    
    public static Font carregar_fonte(String caminho_fonte, double tamanho){
        try {
            return Font.loadFont(new File(caminho_fonte).toURI().toURL().toExternalForm(), tamanho);
        } catch (MalformedURLException ex) {
            System.out.println("Deu Pauuuu");
        }
        return Font.font("Arial", tamanho);
    }
    
    /**
     * @return Retorna a largura do espaço que o texto ocupa.
     */
    @Override
    public double getWidth() {
        return getLayoutBounds().getWidth();
    }

    /**
     * @return Retorna a altura do espaço que o texto ocupa.
     */
    @Override
    public double getHeight() {
        return getLayoutBounds().getHeight();
    }

    @Override
    public void setTranslateX(double position, double pivo) {
        VisibleObjectHandler.setTranslateX(this, pivo, position);
    }

    @Override
    public void setTranslateY(double position, double pivo) {
        VisibleObjectHandler.setTranslateY(this, pivo-1, position);
    }

    @Override
    public void setTranslateZ(double position, double pivo) {
        VisibleObjectHandler.setTranslateZ(this, pivo, position);
    }

    @Override
    public void setStroke(Double grossura, Paint cor) {
        if(cor != null)
            setStroke(cor);
        if(grossura != null){
            setStrokeWidth(grossura);
            setTranslateX(getTranslateX() + grossura/2);///grossura borda?????????
            setTranslateY(getTranslateY() + grossura/2);///grossura borda?????????
        }
    }
}
