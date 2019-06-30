package Biblioteca.BasicObjects.Formas;

import javafx.scene.text.Text;
import javafx.scene.text.Font;
import Biblioteca.Lists.ySimpleMap;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeType;
import javafx.beans.value.ObservableValue;
import javafx.beans.binding.DoubleBinding;
import com.sun.javafx.tk.FontMetrics;
import com.sun.javafx.tk.Toolkit;
import java.io.File;
import java.net.MalformedURLException;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

//FAZER UM SETMAXWIDTH, que nem o label.
//PRO HEIGHT TAMBPEM..., mas ai n sei se olha os \n ou sla...
//O TEXTO GUARDA SEMPRE UM ESPAÇO PROS ACENTOS (EU ACHO), MESMO QUE ELES NAO EXISTAO, ENTAO O TEXTO PODE SER MAIS ALTO DO QUE REALMENTE APARENTA...
public class Texto extends Text implements Forma {
    public YstrokeOcupation yOutsideStrokeOcupation = new YstrokeOcupation();
    public ySimpleMap<String, ObservableValue<? extends Number>> yWeak_listeners = new ySimpleMap();
    
    protected String texto;
    public String font_path = null;
    public double min_font_size = 8;
    
    public DoubleProperty width = new SimpleDoubleProperty(0);
    public DoubleProperty height = new SimpleDoubleProperty(0);
    public double line_height;
    
    public boolean break_text_allowed = true;
    public boolean break_word_allowed = false;
    public boolean unbreak_text_allowed = true;
    public boolean try_break_in_spaces = true;

    public Texto(String texto) {
        this(texto, null, null);
    }

    public Texto(String texto, Font fonte, Color cor) {
        super(texto);
        ySetMineText(texto);

        this.texto = texto;

        if (fonte != null) {
            ySetFont(fonte);
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
        recalc_values();

        ySetTranslateX(where_wasX, 0);
        ySetTranslateY(where_wasY, 0);
    }
    
    public void ySetText(String text){
        texto = text;
        ySetMineText(text);
    }
    
    private void ySetMineText(String text){
        setText(text);
        recalc_values();
    }
    
    private void recalc_values(){
        double text_size[] = simulateTextSize(getText(), getFont().getSize());
        width.set(text_size[0]);
        height.set(text_size[1]);
        line_height = text_size[1] / text_size[2];
    }

    //----------------------------- SIZE METHODS -----------------------------\\
    @Override
    public double yGetWidth(boolean plusStroke) {
        double width = this.width.get();
        if (!plusStroke) {
            width -= yOutsideStrokeOcupation.WIDTH.get();
        }
        return width;
    }

    @Override
    public double yGetHeight(boolean plusStroke) {
        double height = this.height.get();
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
        return getBoundsInLocal().getHeight();
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
        String new_text = new String(texto);
        if(unbreak_text_allowed)
            new_text = new_text.replace('\n', ' ');

        boolean sucess = false;
        double text_width = simulateTextSize(new_text, getFont().getSize())[0];
        if(change_font_size){
            sucess = ySetWidth(width);
        }
        if (break_word_allowed && !sucess) {
            //sees how much characters are needed to approx the required width
            double char_width = text_width / texto.length();
            int char_quantities = (int) ((width / char_width) < 1 ? 0 : (width / char_width));
            
            //must be less than the lenght or it is already correct
            if (char_quantities < new_text.length()) {
                String breaked = break_text(new_text, char_quantities, break_text);
                double new_width = simulateTextSize(breaked, getFont().getSize())[0];
                
                ySetMineText(breaked);//must have something... even if its wrong
                
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
                            breaked = break_text(new_text, char_quantities, break_text);

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
                        ySetMineText(best_string);
                        ySetFont(font);
                    }
                }
            }else{
                ySetMineText(new_text);
                sucess = true;
            }
        }
        //tries to hide some letters as a final resouce
        if (!sucess) {
            ySetMineText(hide_text(new_text, width));
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
        boolean sucess = true;
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
        else if(new_font_size == min_font_size)
            sucess = false;
        
        Font font;
        if (font_path != null) {
            font = Font.loadFont(font_path, new_font_size);
        } else {
            font = Font.font(getFont().getFamily(), new_font_size);
        }
        ySetFont(font);
        
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

    //----------------------------- TRANSLATE METHODS -----------------------------\\
    @Override
    public double yGetTranslateX(double pivo) {
        return getTranslateX() + yGetWidth(true) * pivo;
    }

    @Override
    public double yGetTranslateY(double pivo) {
        return getTranslateY() - line_height + yGetHeight(true) * pivo;
    }

    @Override
    public void ySetTranslateX(double position, double pivo) {
        YshapeHandler.setTranslateX(this, position, pivo);
    }

    @Override
    public void ySetTranslateY(double position, double pivo) {
        YshapeHandler.setTranslateY(this, position + line_height, pivo);
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

    @Override
    public DoubleBinding yWidthBind(boolean stroke_included) {
        return YshapeHandler.yWidthBind(width.add(0), stroke_included ? yOutsideStrokeOcupation : null);
    }

    @Override
    public DoubleBinding yHeightBind(boolean stroke_included) {
        return YshapeHandler.yHeightBind(height.add(0), stroke_included ? yOutsideStrokeOcupation : null);
    }

    @Override
    public void yBindWidth(String bind_name, ObservableValue<? extends Number> width, boolean change_font_size) {
        width.addListener((observable) -> {
            ySetWidth(width.getValue().doubleValue(), change_font_size, break_text_allowed);
        });
        yWeak_listeners.add(bind_name, width);
    }

    @Override
    public void yBindHeight(String bind_name, ObservableValue<? extends Number> height, boolean change_font_size) {
        YshapeHandler.yBindHeight(this, yWeak_listeners, bind_name, height, change_font_size);
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
        double stringWidth = metrics.computeStringWidth(text.substring(begin, end+1));
        double stringHeight = metrics.getLineHeight() * cont;

        //return 3 informations, witdh, height and number of lines
        double a[] = {stringWidth + yOutsideStrokeOcupation.WIDTH.get(), stringHeight + yOutsideStrokeOcupation.HEIGHT.get(), cont};
        return a;
    }
    
    public String hide_text(String text, double width){
        String new_text = "";
        int actual_font_size = (int) getFont().getSize();
            
        String strings[] = text.split("\n");

        for (int i = 0; i < strings.length; i++) {
            double sub_text_size = simulateTextSize(strings[i], actual_font_size)[0];
            double aprox_char_width = sub_text_size / strings[i].length();
            int char_quantitie = (int) (width / aprox_char_width) + 1;
            
            if(char_quantitie > strings[i].length())
                char_quantitie = strings[i].length()+1;
            else if(char_quantitie < 2)
                char_quantitie = 2;
            
            char string[] = new char[char_quantitie+1];
            boolean not_breaked = false;
            for (int j = 0; j < char_quantitie; j++) {
                if(j >= strings[i].length()){
                    not_breaked = true;
                    break;
                }
                string[j] = strings[i].charAt(j);
            }
            if(!not_breaked){
                for (int j = char_quantitie - 2; j < char_quantitie; j++) {
                    string[j] = '.';
                }
            }
            if(i < strings.length - 1)
                string[char_quantitie] = '\n';
            new_text = new_text.concat(new String(string));
        }
        return new_text;
    }
    
    public String break_text(String new_text, double width, boolean break_word) {
        double text_width = simulateTextSize(new_text.replace('\n', ' '), getFont().getSize())[0];
        double char_width = text_width / new_text.length();
        int char_quantities = (int) (width / char_width);
        
        int to = 0;
        int from = char_quantities;
        char[] array = !break_word ? new char[new_text.length()] : new char[new_text.length() + (int) (new_text.length() / char_quantities)];
        int sintetical_breaks = 0; //breaks that are not part of the original text
        int deixe_me_tentar_UMA_ULTIMA_VEZ = 0; //flag var
        
        if(from >= new_text.length())
            return new_text;

        while(from < new_text.length() || deixe_me_tentar_UMA_ULTIMA_VEZ < 2){
            int break_index = -1;
            int space_index = -1;

            for (int i = to; i < from; i++) {//copy the array from a given begining until +char_quantity or if it find a \n, always storing the last SPACE caracter
                array[i + sintetical_breaks] = new_text.charAt(i);
                if(array[i + sintetical_breaks] == '\n'){
                    break_index = i;
                    break;
                }else if(array[i + sintetical_breaks] == ' ')
                    space_index = i;
            }
            
            if(break_index != -1){//if there is a \n it should see it and start the counting again, begining in the \n's index + 1
                to = break_index + 1;
                from = to + char_quantities;
            }else{
                if(space_index != -1 && deixe_me_tentar_UMA_ULTIMA_VEZ == 0 && try_break_in_spaces){//breaks the text in the last SPACE caracter found
                    array[space_index + sintetical_breaks] = '\n';
                    to = space_index + 1;
                    from = to + char_quantities;
                }else if(break_word && deixe_me_tentar_UMA_ULTIMA_VEZ == 0){//breaks the word if it is allowed and no SPACES were found in this loop (begining -> +char quantities).
                    array[from + sintetical_breaks] = array[from + sintetical_breaks - 1];
                    array[from + sintetical_breaks - 1] = '\n';
                    sintetical_breaks++;
                    to = from;
                    from = to + char_quantities;
                }else{//looks if there is a \n or SPACE beyond the limit to contiue the loops because it wasn't possible to break the text this time.
                    boolean breaked = false;
                    for (int i = from; i < new_text.length(); i++) {
                        if(new_text.charAt(i) == ' ' || new_text.charAt(i) == '\n'){
                            array[i + sintetical_breaks] = '\n';
                            to = i + 1;
                            from = to + char_quantities;
                            breaked = true;
                            break;
                        }
                    }
                    if(!breaked && !break_word){
                        to = from;
                        from = to + char_quantities;
                    }
                }
            }
            if(from >= new_text.length()){//if from crossed the limit from receives the limit and goes to the last loop.
                deixe_me_tentar_UMA_ULTIMA_VEZ++;
                from = new_text.length();
            }
        }
        return new String(array);
    }
    
    public int find_first(String string, char c, int ini, int end){
        for (int i = ini; i < end; i++) {
            if(string.charAt(i) == c){
                return i;
            }
        }
        return -1;
    }
}