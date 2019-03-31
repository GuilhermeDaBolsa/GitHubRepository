package executavel;

import javafx.scene.paint.Color;

public class Acoes_Botoes {

    public static void acao_btn_adicionar(boolean selected) {
        if (selected) {
            Fisica2.caixa_adicionar_carga.setVisible(true);
            Fisica2.caixa_adicionar_carga.setDisable(false);
        } else {
            Fisica2.caixa_adicionar_carga.setVisible(false);
            Fisica2.caixa_adicionar_carga.setDisable(true);
        }
    }

    public static void acao_btn_configuracao(boolean selected) {
        for (int i = -150; i < 151; i++) {
            Fisica2.grade.adicionar_objeto(new Entidade(6, 12, Color.AQUA, i + ", " + i), i, i);
        }
    }

    public static void acao_btn_remover(boolean selected) {
        Runtime runtime = Runtime.getRuntime();
        //runtime.gc();
        long memory = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("Used memory in MB: " + memory/1024/1024);
    }
}