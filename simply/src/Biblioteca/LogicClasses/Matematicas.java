package Biblioteca.LogicClasses;

import javafx.geometry.Point2D;

    public class Matematicas {

    /**
     * Gera um valor aleatório entre 0 (inclusive) e o valor de parametro (not inclusive).
     * @param max - valor máximo
     * @return Qualquer nunmero de [0...max[
     */
    public static double random(double max) {
        return Math.random()*max;
    }

    /**
     * Gera um valor aleatório entre 0 (inclusive) e o valor de parametro.
     * @param min Valor mínimo.
     * @param max Valor máximo.
     * @return Qualquer número de [1...max[
     */
    public static double random(double min, double max) {
        return (Math.random()*max + min) % max;
    }
    
    /**
     * "Rolls" a dice and checks it against the chance percentage
     * @param chance - the chance against which the roll is checked
     *              100 will always return true, 1 - will return true approx. once in 100 rolls
     * @return true if chance succeeds, false otherwise
     */
    public static boolean checkChance(int chance) {
        return (int) (Math.random()*100) + 1 <= chance;
    }

    public static boolean checkChance(float chance) {
        return Math.random() * 100 + 1 <= chance;
    }

    /**
     *
     * @param dmg
     * @return true if dmg dealt was critical
     */
    public static boolean isCritical(int dmg) {
        return ((dmg >> 31) & 0b01) == 1;
    }

    /**
     * Normalizes damage, in other words removes the MSB
     * @param dmg
     * @return
     */
    public static int normalizeDamage(int dmg) {
        return dmg & 0x7FFFFFFF;
    }
    
    /**
     * Cria resistência na movimentação da entidade.
     * 
     * @param velocidade A velocidade da entidade
     * @param quebra_velocidade Diminuirá a velocidade da entidade, se for 1 diminui por completo
     * e quanto maior for, menos será diminuida a velocidade da entidade.
     * @return Velocidade diminuida.
     */
    public static double Lei_1_Newton(double velocidade, double quebra_velocidade){
        return velocidade - velocidade / quebra_velocidade;
    }
    
    public static boolean estao_na_mesma_direcao(double forca1, double forca2) {
        if ((forca1*forca2 > 0)) {
            return true;
        }
        
        return false;
    }

    public static double quem_e_maior(double numero1, double numero2) {
        if (numero1 > numero2) {
            return numero1;
        }
        return numero2;
    }

    public static double quem_e_maior_modulo(double numero1, double numero2) {
        if (modulo(numero1) > modulo(numero2)) {
            return numero1;
        }
        return numero2;
    }
    
    public static double modulo(double numero){
        if(numero < 0){
            return -numero;
        }
        return numero;
    }
    
    public static double hypotenuse(double x, double y){
        return Math.sqrt(x * x + y * y);
    }
    
    public static double calculate_angle(Point2D a, Point2D b, Point2D c){
        double angle = Math.atan2((c.getX() - b.getX()) * (b.getY() - a.getY()) - (c.getY() - b.getY()) * (b.getX() - a.getX()),
                (c.getX() - b.getX()) * (b.getX() - a.getX()) + (c.getY() - b.getY()) * (b.getY() - a.getY()));
        
        return 180 + (angle / Math.PI * 180);
    }
}


