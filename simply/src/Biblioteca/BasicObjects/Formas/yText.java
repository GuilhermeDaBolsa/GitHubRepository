package Biblioteca.BasicObjects.Formas;

import Biblioteca.BasicObjects.YcoolBindings;
import Biblioteca.Interactions.YEventsHandler;
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
import javafx.scene.transform.Rotate;

//QUANDO VEM COM UM \n NO FIM ELE SE PERDEU (pelo menos parece q é isso)
//O TEXTO GUARDA SEMPRE UM ESPAÇO PROS ACENTOS (E PRA SEPARA AS LINHAS), dai a primeira linha é mais alta;

public class yText extends Text implements Yshape, YcoolBindings{
    private YEventsHandler yEvents_Handler = new YEventsHandler(this);
    public ySimpleMap<String, ObservableValue> yWeak_listeners = new ySimpleMap();
    
    private YstrokeOcupation yOutsideStrokeOcupation = new YstrokeOcupation();
    private Rotate yRotation = new Rotate(0);
    
    public DoubleProperty yMax_width = new SimpleDoubleProperty(-1);
    public DoubleProperty yMax_height = new SimpleDoubleProperty(-1);
    
    protected String texto;
    public String font_path = null;
    public double min_font_size = 8;
    
    private DoubleProperty width = new SimpleDoubleProperty(0);
    private DoubleProperty height = new SimpleDoubleProperty(0);
    public double line_height;
    
    public yText(String texto) {
        this(texto, null, null);
    }

    public yText(String texto, Font fonte, Color cor) {
        super(texto);
        this.texto = texto;
        ySetMineText(texto);

        if (fonte != null) 
            ySetFont(fonte);

        setFill(cor == null ? Color.BLACK : cor);

        ySetTranslateY(0, 0);
    }

    /**
     * Sets a new font to the text.
     * @param font The new font.
     */
    public void ySetFont(Font font) {
        double where_wasX = yGetTranslateX(0);
        double where_wasY = yGetTranslateY(0);

        setFont(font);
        recalc_values();

        ySetTranslateX(where_wasX, 0);
        ySetTranslateY(where_wasY, 0);
    }
    
    /**
     * Sets a new text to the text :).
     * @param text The new text.
     */
    public void ySetText(String text){
        texto = text;
        ySetMineText(text);
    }
    
    private void ySetMineText(String text){
        if(text == null || text == "")
            text = " ";
        
        setText(text);
        recalc_values();
    }
    
    private void recalc_values(){
        double text_size[] = simulateTextSize(getText(), getFont().getSize(), true);
        width.set(text_size[0]);
        height.set(text_size[1]);
        line_height = text_size[1] / text_size[2];
    }

    
    
    //----------------------------- SIZE METHODS -----------------------------\\
    
    @Override
    public double yGetWidth(boolean plusStroke) {
        double width = this.width.get();
        if (!plusStroke) {
            width -= yOutsideStrokeOcupation.WIDTH;
        }
        return width;
    }

    @Override
    public double yGetHeight(boolean plusStroke) {
        double height = this.height.get();
        if (!plusStroke) {
            height -= yOutsideStrokeOcupation.HEIGHT;
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
     * Sets a new width changing the FONT SIZE.
     * @param width New width
     */
    @Override
    public void ySetWidth(double width, boolean stroke_included, boolean correct_location) {
        double where_wasX = yGetTranslateX(0);
        double where_wasY = yGetTranslateY(0);
        
        width = YshapeHandler.ySizeControler(width, stroke_included, yOutsideStrokeOcupation.WIDTH, yMax_width.get());
        
        ySetSize(width, stroke_included, 0);
        
        if(correct_location){
            ySetTranslateX(where_wasX, 0);
            ySetTranslateY(where_wasY, 0);
        }
    }

    /**
     * Sets a new width changing the FONT SIZE.
     * @param height New width
     */
    @Override
    public void ySetHeight(double height, boolean stroke_included, boolean correct_location) {
        double where_wasX = yGetTranslateX(0);
        double where_wasY = yGetTranslateY(0);
        
        height = YshapeHandler.ySizeControler(height, stroke_included, yOutsideStrokeOcupation.HEIGHT, yMax_height.get());
        
        ySetSize(height, stroke_included, 1);
        
        if(correct_location){
            ySetTranslateX(where_wasX, 0);
            ySetTranslateY(where_wasY, 0);
        }
    }
    
    private void ySetSize(double space_ocupation, boolean stroke_included, int widthORheight){
        int actual_font_size = (int) getFont().getSize();
        String new_text = getText();
        int new_font_size = actual_font_size;
        int changed = 0;
        
        while(changed < 2){
            double size = simulateTextSize(new_text, new_font_size, stroke_included)[widthORheight];
            changed = 0;
            
            if (size < space_ocupation) {
                changed++;
                new_font_size++;
                size = simulateTextSize(new_text, new_font_size, stroke_included)[widthORheight];
            }
            if(size > space_ocupation){
                changed++;
                new_font_size--;
            }
            if(size == space_ocupation)
                break;
        }
        
        if(new_font_size < min_font_size)
            new_font_size = (int) min_font_size;
        
        Font font;
        if (font_path != null) 
            font = Font.loadFont(font_path, new_font_size);
        else 
            font = Font.font(getFont().getFamily(), new_font_size);
        
        ySetFont(font);
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
        YshapeHandler.ySetStroke(this, stroke_width, stroke_color, stroke_type, correct_location);
    }

    @Override
    public YstrokeOcupation yGetStrokeOcupation(){
        return yOutsideStrokeOcupation;
    }
    
    
    
    //----------------------------- ROTATE METHODS -----------------------------\\
    
    @Override
    public Rotate yGetRotate(){
        return yRotation;
    }
    
    @Override
    public void ySetRotate(double angle, double pivoX, double pivoY){
        YshapeHandler.ySetRotate(this, yRotation, angle, pivoX, pivoY);
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
    
    
    
    //----------------------------- EVENTS METHODS -----------------------------\\
    
    @Override
    public YEventsHandler yGetEventsHandler(){
        return yEvents_Handler;
    }
    
    
    
    //----------------------------- BIND/LISTENER METHODS -----------------------------\\
    
    @Override
    public void yAddBind(String bind_name, ObservableValue<? extends Number> bind){
        YshapeHandler.yAddBind(yWeak_listeners, bind_name, bind);
    }
    
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
    public void yBindWidth(String bind_name, ObservableValue<? extends Number> width, boolean correct_location) {
        YshapeHandler.yBindWidth(this, yWeak_listeners, bind_name, width, correct_location);
    }

    @Override
    public void yBindHeight(String bind_name, ObservableValue<? extends Number> height, boolean correct_location) {
        YshapeHandler.yBindHeight(this, yWeak_listeners, bind_name, height, correct_location);
    }

    @Override
    public void yUnbind(String bind_name) {
        YshapeHandler.yUnbind(yWeak_listeners, bind_name);
    }
    
    //ORGANIZAR OS METODOS ABAIXO EM ALGUM OUTRO LUGAR OU SLA... PENSA BEM
    
    /**
     * Sets the width that the text will ocuppie in the scene. (not ready for effects/can be a little wrong)
     * This method calculates the average width of a char based on the number of chars in the text.
     * It may not give the results you expect.
     * @param width The width that the text should ocuppie.
     * @param change_font_size Mark true if you allow the method to change the font size.
     * @param break_text Mark true if you allow the method to break the text in more lines.
     */
    public void ySetWidth(double width, boolean stroke_included, boolean change_font_size_allowed, boolean break_text_allowed,
     boolean break_word_allowed, boolean unbreak_text_allowed, boolean try_break_in_spaces, boolean correct_location) {//rever
        double where_wasX = yGetTranslateX(0);
        double where_wasY = yGetTranslateY(0);
        
        width = YshapeHandler.ySizeControler(width, stroke_included, yOutsideStrokeOcupation.WIDTH, yMax_width.get());
        
        String new_text = new String(texto);
        if(unbreak_text_allowed)
            new_text = new_text.replace('\n', ' ');

        boolean sucess = false;
        double text_width = simulateTextSize(new_text, getFont().getSize(), stroke_included)[0];
        if(change_font_size_allowed){//acho que da pra tirar :P, talvez fique melhor MAS DA PRA TESTAR UMA HORA DESSAAAAAAAAASSSSSSSSSSSSSSSSSS
            int actual_font_size = (int) getFont().getSize();
            ySetWidth(width, stroke_included, true);
            int new_font_size = (int) getFont().getSize();
            sucess = new_font_size != actual_font_size;
        }
        if (break_text_allowed && !sucess) {
            //sees how much characters are needed to approx the required width
            double char_width = text_width / texto.length();
            int char_quantities = (int) ((width / char_width) < 1 ? 0 : (width / char_width));
            
            //must be less than the lenght or it is already correct
            if (char_quantities < new_text.length()) {
                String breaked = break_text(new_text, char_quantities, break_word_allowed, try_break_in_spaces);//tries to break the text
                double new_width = simulateTextSize(breaked, getFont().getSize(), stroke_included)[0];
                
                ySetMineText(breaked);//must have something... even if its wrong
                
                if (new_width <= width) {
                    sucess = true;
                }else {
                    //failed in breaking the text
                    if (change_font_size_allowed) {
                        int actual_font_size = (int) getFont().getSize();
                        int readable_font_size = (int) (actual_font_size > min_font_size-1 ? min_font_size : 0);
                        Font font;
                        String best_string = breaked;
                        double best_width = new_width;
                        int best_font_size = actual_font_size;

                        //tring to break the text with smaller font sizes, and storing the best result.
                        for (int i = actual_font_size; i > readable_font_size; i--) {
                            text_width = simulateTextSize(new_text, i, stroke_included)[0];
                            char_width = text_width / texto.length();
                            char_quantities = (int) (width / char_width);
                            breaked = break_text(new_text, char_quantities, break_word_allowed, try_break_in_spaces);

                            if (simulateTextSize(breaked, i, stroke_included)[0] < best_width) {
                                best_string = breaked;
                                best_width = simulateTextSize(breaked, i, stroke_included)[0];
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
            ySetMineText(hide_text(new_text, width, stroke_included));
        }
        
        if(correct_location){
            ySetTranslateX(where_wasX, 0);
            ySetTranslateY(where_wasY, 0);
        }
    }
    
    /**
     * @param height
     */
    public void ySetHeight(double height, boolean stroke_included, boolean change_font_size_allowed, boolean break_text_allowed,
     boolean break_word_allowed, boolean unbreak_text_allowed, boolean try_break_in_spaces, boolean correct_location) {
        double where_wasX = yGetTranslateX(0);
        double where_wasY = yGetTranslateY(0);
        boolean sucess = false;
        
        height = YshapeHandler.ySizeControler(height, stroke_included, yOutsideStrokeOcupation.HEIGHT, yMax_height.get());
        
        String new_text = new String(texto);
        int actual_font_size = (int) getFont().getSize();
        
        if(change_font_size_allowed){
            ySetHeight(height, stroke_included, true);
            int new_font_size = (int) getFont().getSize();
            sucess = new_font_size != actual_font_size;
        }
            
        if(!sucess){
            double textPropreties[] = simulateTextSize(getText(), getFont().getSize(), stroke_included);
            int line_quantity_desired = (int) (height / line_height);
            int line_quantity = (int) textPropreties[2];
            
            if(textPropreties[1] > height){
                if(unbreak_text_allowed){
                    int break_quantities = line_quantity_desired - 1;
                    new_text = new_text.replace('\n', ' ');
                    int char_quantities = (new_text.length()-1) / (break_quantities);
                    new_text = break_text(new_text, char_quantities, break_word_allowed, try_break_in_spaces);
                }else{
                    int remove_breaks = line_quantity - line_quantity_desired;
                    String substrings[] = new_text.split("\n");
                    new_text = "";
                    
                    for (int i = 0; i < substrings.length - remove_breaks; i++) {
                        new_text = new_text.concat(substrings[i]);
                    }
                    new_text = new_text.concat("..");
                }
            }else if(break_text_allowed){
                int break_quantities = line_quantity_desired - 1;
                if(unbreak_text_allowed)
                        new_text = new_text.replace('\n', ' ');
                
                int char_quantities = (new_text.length()-1) / (break_quantities);
                new_text = break_text(new_text, char_quantities, break_word_allowed, try_break_in_spaces);
            }
        }
        
        ySetMineText(new_text);
        
        if(correct_location){
            ySetTranslateX(where_wasX, 0);
            ySetTranslateY(where_wasY, 0);
        }
    }
    
    
    
    //--------------------------------------------------\\achar um lugar melhor pra esses metodos :T
    
    protected double[] simulateTextSize(String text, double font_size, boolean stroke_included) {
        Font font;
        if (font_path != null) {
            font = Font.loadFont(font_path, font_size);
        } else {
            font = Font.font(getFont().getFamily(), font_size);
        }

        int begin = 0;
        int end = text.length()-1;
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
        double a[] = {stringWidth + (stroke_included ? yOutsideStrokeOcupation.WIDTH : 0), stringHeight + (stroke_included ? yOutsideStrokeOcupation.HEIGHT : 0), cont};
        return a;
    }
    
    public String hide_text(String text, double width, boolean stroke_included){
        String new_text = "";
        int actual_font_size = (int) getFont().getSize();
            
        String strings[] = text.split("\n");//crates one string for each line

        for (int i = 0; i < strings.length; i++) {
            double sub_text_size = simulateTextSize(strings[i], actual_font_size, stroke_included)[0];
            double aprox_char_width = sub_text_size / strings[i].length();
            int char_quantitie = (int) (width / aprox_char_width) + 1;//how much is the maximum char number for this line
            
            if(char_quantitie > strings[i].length())//validates it
                char_quantitie = strings[i].length()+1;
            else if(char_quantitie < 2)
                char_quantitie = 2;
            
            char string[] = new char[char_quantitie+1];//empty string with more space than needed for a '\n'
            boolean not_breaked = false;
            for (int j = 0; j < char_quantitie; j++) {
                if(j >= strings[i].length()){//sees if the substring fill the space
                    not_breaked = true;
                    break;
                }
                string[j] = strings[i].charAt(j);//copy the sub string
            }
            if(!not_breaked){//if it does not fill add ".."
                for (int j = char_quantitie - 2; j < char_quantitie; j++) {
                    string[j] = '.';
                }
            }
            if(i < strings.length - 1)//if it is not the last substring add a '\n'
                string[char_quantitie] = '\n';
            new_text = new_text.concat(new String(string));
        }
        return new_text;
    }
    
    public String break_text(String new_text, int char_quantities, boolean break_word, boolean try_break_in_spaces) {
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
                        array[i + sintetical_breaks] = new_text.charAt(i);
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
    
    public Font carregar_fonte(String caminho_fonte, double tamanho) {
        try {
            font_path = caminho_fonte;
            return Font.loadFont(new File(caminho_fonte).toURI().toURL().toExternalForm(), tamanho);
        } catch (MalformedURLException ex) {
            System.out.println("Deu Pauuuu");
        }
        return Font.font("Arial", tamanho);
    }
}