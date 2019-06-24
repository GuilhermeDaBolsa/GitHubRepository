package Biblioteca.BasicObjects.Formas;

import javafx.scene.text.Text;
import javafx.scene.text.Font;
import java.util.HashMap;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeType;
import javafx.beans.value.ObservableValue;
import javafx.beans.binding.DoubleBinding;
import com.sun.javafx.tk.FontMetrics;
import com.sun.javafx.tk.Toolkit;
import java.io.File;
import java.net.MalformedURLException;

//FAZER UM SETMAXWIDTH, que nem o label.
//PRO HEIGHT TAMBPEM..., mas ai n sei se olha os \n ou sla...
//O TEXTO GUARDA SEMPRE UM ESPAÇO PROS ACENTOS (EU ACHO), MESMO QUE ELES NAO EXISTAO, ENTAO O TEXTO PODE SER MAIS ALTO DO QUE REALMENTE APARENTA...
public class Texto extends Text implements Forma {

    public YstrokeOcupation yOutsideStrokeOcupation = new YstrokeOcupation();
    public HashMap<String, ObservableValue<? extends Number>> yWeak_listeners = new HashMap();
    protected String texto;
    protected String font_path = null;
    public double min_font_size = 8;
    public boolean break_word_allowed = false;

    public Texto(String texto) {
        this(texto, null, null);
    }

    public Texto(String texto, Font fonte, Color cor) {
        super(texto);

        this.texto = texto;

        if (fonte != null) {
            setFont(fonte);
        }

        setFill(cor == null ? Color.BLACK : cor);

        ySetTranslateY(0, 0);
    }

    public Font carregar_fonte(String caminho_fonte, double tamanho) {
        try {
            font_path = caminho_fonte;
            return Font.loadFont(new File(caminho_fonte).toURI().toURL().toExternalForm(), tamanho);
        } catch (MalformedURLException ex) {
            System.out.println("Deu Pauuuu");
        }
        return Font.font("Arial", tamanho);
    }

    public void ySetFont(Font font) {
        double where_wasX = yGetTranslateX(0);
        double where_wasY = yGetTranslateY(0);

        setFont(font);

        ySetTranslateX(where_wasX, 0);
        ySetTranslateY(where_wasY, 0);
    }

    //----------------------------- SIZE METHODS -----------------------------\\
    @Override
    public double yGetWidth(boolean plusStroke) {
        double width = simulateTextSize(getText(), getFont().getSize())[0];
        if (!plusStroke) {
            width -= yOutsideStrokeOcupation.WIDTH.get();
        }
        return width;
    }

    @Override
    public double yGetHeight(boolean plusStroke) {
        double height = simulateTextSize(getText(), getFont().getSize())[1];
        if (!plusStroke) {
            height -= yOutsideStrokeOcupation.HEIGHT.get();
        }
        return height;
    }

    @Override
    public double yGetWidth() {
        return getBoundsInLocal().getWidth();
    }

    @Override
    public double yGetHeight() {
        return getBoundsInLocal().getHeight() * 0.7;
    }

    /**
     * Sets the width that the text will ocuppie in the scene. (not ready for effects/can be a little wrong)
     * This method calculates the average width of a char based on the number of chars in the text.
     * It may not give the resoults you expect.
     * @param width The width that the text should ocuppie.
     * @param change_font_size Mark true if you allow the method to change the font size.
     * @param break_text Mark true if you allow the method to break the text in more lines.
     */
    @Override
    public void ySetWidth(double width, boolean change_font_size, boolean break_text) {
        double where_wasX = yGetTranslateX(0);
        double where_wasY = yGetTranslateY(0);

        boolean sucess = false;
        String new_text = new String(texto);
        double text_width = simulateTextSize(new_text, getFont().getSize())[0];
        
        if(text_width <= width){
            change_font_size = true;
            break_text = false;
        }
        
        if (break_text) {
            new_text.replace("\n", " ");

            //sees how much characters would approx to the width
            double char_width = text_width / texto.length();
            int char_quantities = (int) ((width / char_width) > new_text.length() ? new_text.length() : (width / char_width));

            //must be a valid value
            if (char_quantities < new_text.length() && char_quantities > 0) {
                String breaked = break_text(new_text, char_quantities, break_word_allowed);
                double new_width = simulateTextSize(breaked, getFont().getSize())[0];
                
                setText(breaked);//must have something... even if its wrong
                
                if (new_width <= width) {
                    sucess = true;
                }else {
                    //failed in breaking the text
                    if (change_font_size) {
                        int actual_font_size = (int) getFont().getSize();
                        int readable_font_size = (int) (actual_font_size > min_font_size-1 ? min_font_size : 0);
                        Font font;
                        String best_string = breaked;
                        double best_width = new_width;
                        int best_font_size = actual_font_size;

                        //tring to break the text with smaller font sizes, and storing the best result.
                        for (int i = actual_font_size; i > readable_font_size; i--) {
                            text_width = simulateTextSize(new_text, i)[0];
                            char_width = text_width / texto.length();
                            char_quantities = (int) (width / char_width);
                            breaked = break_text(new_text, char_quantities, break_word_allowed);

                            if (simulateTextSize(breaked, i)[0] < best_width) {
                                best_string = breaked;
                                best_width = simulateTextSize(breaked, i)[0];
                                best_font_size = i;
                                if (best_width <= width) {
                                    sucess = true;
                                    break;
                                }
                            }
                        }
                        //sets the bests results
                        if (font_path != null) {
                            font = Font.loadFont(font_path, best_font_size);
                        } else {
                            font = Font.font(getFont().getFamily(), best_font_size);
                        }
                        setText(best_string);
                        setFont(font);
                    }
                }
            }
        } else if (change_font_size) {
            sucess = ySetWidth(width);
        }
        //tries to hide some letters as a last resouce
        if (!sucess) {
            hide_text(width);
        }
        ySetTranslateX(where_wasX, 0);
        ySetTranslateY(where_wasY, 0);
    }

    /**
     * Sets a new width changing the font size.
     * @param width New width
     * @return If sucess return true.
     */
    public boolean ySetWidth(double width) {
        boolean sucess = false;
        int actual_font_size = (int) getFont().getSize();
        String new_text = getText();
        int new_font_size = actual_font_size;
        int changed = 0;
        
        while(changed < 2){
            double size = simulateTextSize(new_text, new_font_size)[0];
            changed = 0;
            
            if (size < width) {
                changed++;
                new_font_size++;
                size = simulateTextSize(new_text, new_font_size)[0];
            }
            if(size > width){
                changed++;
                new_font_size--;
            }
            if(size == width)
                break;
        }
        
        if(new_font_size < min_font_size)
            new_font_size = (int) min_font_size;
        
        Font font;
        if (font_path != null) {
            font = Font.loadFont(font_path, new_font_size);
        } else {
            font = Font.font(getFont().getFamily(), new_font_size);
        }
        setFont(font);
        
        return sucess;
    }

    /**
     * FAZER COM BASE NOS \n E TAMBÉM NO WRAPING (ALGO ASSIM)
     *
     * @param height
     * @param stroke_included
     * @param correct_location
     */
    @Override
    public void ySetHeight(double height, boolean stroke_included, boolean correct_location) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.//ESSE VAI SE BRABO
    }

    protected double chooseFontSize() {
        return 0;
    }

    //----------------------------- TRANSLATE METHODS -----------------------------\\
    @Override
    public double yGetTranslateX(double pivo) {
        return getTranslateX() + yGetWidth(true) * pivo;
    }

    @Override
    public double yGetTranslateY(double pivo) {
        return getTranslateY() + yGetHeight(true) * (pivo - 1);
    }

    @Override
    public void ySetTranslateX(double position, double pivo) {
        YshapeHandler.setTranslateX(this, position, pivo);
    }

    @Override
    public void ySetTranslateY(double position, double pivo) {
        YshapeHandler.setTranslateY(this, position, pivo - 1);
    }

    @Override
    public void ySetPosition(double X, double Y, double pivoX, double pivoY) {
        ySetTranslateX(X, pivoX);
        ySetTranslateY(Y, pivoY);
    }

    //----------------------------- STROKE METHODS -----------------------------\\
    @Override
    public void ySetStroke(Double stroke_width, Paint stroke_color, StrokeType stroke_type, boolean correct_location) {
        YshapeHandler.ySetStroke(this, stroke_width, stroke_color, stroke_type, yOutsideStrokeOcupation, correct_location);//DEVE TA ERRADO ISSO JSUS
    }

    //----------------------------- SCALE METHODS -----------------------------\\
    @Override
    public void ySetScaleX(double scale, boolean correct_location) {
        YshapeHandler.ySetScaleX(this, scale, correct_location);
    }

    @Override
    public void ySetScaleY(double scale, boolean correct_location) {
        YshapeHandler.ySetScaleY(this, scale, correct_location);
    }

    @Override
    public void yScaleXby(double multiplier, boolean correct_location) {
        YshapeHandler.yScaleXby(this, multiplier, correct_location);
    }

    @Override
    public void yScaleYby(double multiplier, boolean correct_location) {
        YshapeHandler.yScaleYby(this, multiplier, correct_location);
    }

    @Override
    public void ySetWidthWithScale(double width, boolean stroke_included, boolean correct_location) {
        YshapeHandler.ySetWidthWithScale(this, width, stroke_included, correct_location);
    }

    @Override
    public void ySetHeigthWithScale(double height, boolean stroke_included, boolean correct_location) {
        YshapeHandler.ySetHeigthWithScale(this, height, stroke_included, correct_location);
    }

    //----------------------------- BIND/LISTENER METHODS -----------------------------\\
    @Override
    public DoubleBinding yTranslateXbind(double pivo) {
        return YshapeHandler.yTranslateXbind(this, pivo);
    }

    @Override
    public DoubleBinding yTranslateYbind(double pivo) {
        return YshapeHandler.yTranslateYbind(this, pivo);
    }

    @Override
    public void yBindTranslateX(String bind_name, ObservableValue<? extends Number> X, double pivo) {
        YshapeHandler.yBindTranslateX(this, yWeak_listeners, bind_name, X, pivo);
    }

    @Override
    public void yBindTranslateY(String bind_name, ObservableValue<? extends Number> Y, double pivo) {
        YshapeHandler.yBindTranslateY(this, yWeak_listeners, bind_name, Y, pivo);
    }

    //ERRADASSO ESSES SCALE AI PARCERO
    @Override
    public DoubleBinding yWidthBind(boolean stroke_included) {
        return YshapeHandler.yWidthBind(scaleXProperty().add(0), stroke_included ? yOutsideStrokeOcupation : null);
    }

    @Override
    public DoubleBinding yHeightBind(boolean stroke_included) {
        return YshapeHandler.yHeightBind(scaleXProperty().add(0), stroke_included ? yOutsideStrokeOcupation : null);
    }

    @Override
    public void yBindWidth(String bind_name, ObservableValue<? extends Number> width, boolean stroke_included) {
        YshapeHandler.yBindWidth(this, yWeak_listeners, bind_name, width, stroke_included);
    }

    @Override
    public void yBindHeight(String bind_name, ObservableValue<? extends Number> height, boolean stroke_included) {
        YshapeHandler.yBindHeight(this, yWeak_listeners, bind_name, height, stroke_included);
    }

    @Override
    public void yUnbind(String bind_name) {
        YshapeHandler.yUnbind(yWeak_listeners, bind_name);
    }
    
    //ORGANIZAR OS METODOS ABAIXO EM ALGUM OUTRO LUGAR OU SLA... PENSA BEM
    
    protected double[] simulateTextSize(String text, double font_size) {
        Font font;
        if (font_path != null) {
            font = Font.loadFont(font_path, font_size);
        } else {
            font = Font.font(getFont().getFamily(), font_size);
        }

        int begin = 0;
        int end = text.length();
        int size = 0;
        int sizeH = 0;
        int cont = 0;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == '\n' || i == text.length()-1) {
                cont++;
                if (sizeH > size) {
                    size = sizeH;
                    end = i;
                    begin = end - sizeH;
                }
                sizeH = 0;
            } else {
                sizeH++;
            }
        }

        FontMetrics metrics = Toolkit.getToolkit().getFontLoader().getFontMetrics(font);
        double stringWidth = metrics.computeStringWidth(text.substring(begin, end));
        double stringHeight = metrics.getLineHeight() * cont;

        double a[] = {stringWidth + yOutsideStrokeOcupation.WIDTH.get(), stringHeight + yOutsideStrokeOcupation.HEIGHT.get()};
        return a;
    }
    
    public void hide_text(double width){
        String text = getText();
        String new_text = "";
        int n = 0;
        int cont = 0;
        int actual_font_size = (int) getFont().getSize();
            
        String strings[] = text.split("\n");
        
        for (int i = 0; i < strings.length; i++) {
            double sub_text_size = simulateTextSize(strings[i], actual_font_size)[0];
            double aprox_char_width = sub_text_size / strings[i].length();
            int remaining_chars = (int) ((width / aprox_char_width) > text.length() ? text.length() : (width / aprox_char_width));
            
            char string[] = new char[remaining_chars + 3];
            for (int j = 0; j < remaining_chars-1; j++) {
                string[j] = strings[i].charAt(j);
            }
            for (int j = remaining_chars - 1; j < remaining_chars + 1; j++) {
                string[j] = '.';
            }
            if(i < strings.length - 1)
                string[remaining_chars + 1] = '\n';
            new_text = new_text.concat(new String(string));
        }
        setText(new_text);
    }
    
    public String break_text(String new_text, int char_quantities, boolean break_word) {
        int to = 0;
        int from = char_quantities;
        char[] array;

        if (break_word) {
            array = new char[new_text.length() + (new_text.length() / char_quantities) - 1];
            for (int i = 0; i < new_text.length(); i++) {
                array[i] = new_text.charAt(i);
            }
        } else {
            array = new_text.toCharArray();
        }

        boolean found = false;
        while (from < new_text.length()) {
            for (int i = from; i > to; i--) {
                if (!break_word) {
                    if (array[i] == ' ') {
                        array[i] = '\n';
                        to = i;
                        from += i;
                        found = true;
                        break;
                    }
                } else {
                    array[i] = '\n';
                    for (int j = i; j < new_text.length(); j++) {
                        array[j + 1] = new_text.charAt(j);
                    }
                    found = true;
                }
            }
            if (!found) {
                for (int i = from; i < new_text.length(); i++) {
                    if (array[i] == ' ') {
                        array[i] = '\n';
                        to = i;
                        from = i + (i + char_quantities < new_text.length() ? char_quantities : new_text.length() - i);
                        break;
                    }
                }
            }
            found = false;
        }
        return new String(array);
    }
}