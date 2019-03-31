package executavel;

import Biblioteca.InteractiveObjects.InterligaElementos;
import Biblioteca.Animacoes;
import Biblioteca.OrganizadoresDeNodos.TabelaMenu;
import Biblioteca.OrganizadoresDeNodos.Caixa;
import static executavel.Acoes_Botoes.*;
import java.util.ArrayList;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class CriadorMenu {
    public static Scene scene;
    static double largura_menu = 75;
    static double espacinho = 20;
    static double porcentagem_btn = 0.5;
    static double tempo_animacao_menu = 0.3;
    public static ArrayList<Caixa> botoes = new ArrayList();

    /**
     * Cria a barra de menu com os botões de ações.
     *
     * @return O menu.
     */
    public static Caixa cria_menu() {
        Caixa btn_add = new Caixa(largura_menu * porcentagem_btn, largura_menu * porcentagem_btn, 2, Color.CADETBLUE, Color.BLACK);
        btn_add.actionRelease.addRunnable(() -> {
            acao_btn_adicionar(btn_add.is_selected);
        }); 

        btn_add.actionCleaner.addRunnable(() -> {
            acao_btn_adicionar(btn_add.is_selected);
        });
        botoes.add(btn_add);
        
        Caixa btn_config = new Caixa(largura_menu * porcentagem_btn, largura_menu * porcentagem_btn, 2, Color.DARKGRAY, Color.BLACK);
        btn_config.actionRelease.addRunnable(() -> {
            acao_btn_configuracao(true);
        });
        botoes.add(btn_config);
        
        Caixa btn_remove = new Caixa(largura_menu * porcentagem_btn, largura_menu * porcentagem_btn, 2, Color.FIREBRICK, Color.BLACK);
        botoes.add(btn_remove);
        btn_remove.actionRelease.addRunnable(() -> {
            acao_btn_remover(true);
        });
        
        TabelaMenu tabela = new TabelaMenu(0, 50, 0, 40, 1, false, btn_add, btn_config, btn_remove);
        InterligaElementos junta = new InterligaElementos(btn_add, btn_config, btn_remove);
        junta.setButtonTypes(-2,2,-2);
        junta.setEventosVisuais(Color.BLACK, Color.WHITE, Color.CHARTREUSE);
        Caixa menu = new Caixa(largura_menu, 100, 2, Color.BLACK, Color.BLACK);
        menu.adicionar_conteudo(tabela);
        menu.alinhar_conteudos_centro();
        menu.mover_conteudos(-2, espacinho * 2);
        menu.setTranslateX(-largura_menu);
        menu.setTranslateY(0);

        return menu;
    }

    /**
     * Cria o botão para mostrar/esconder o menu.
     *
     * @param menu O menu a ser mostrado/escondido.
     * @return O botão.
     */
    public static Caixa cria_botao_menu(Caixa menu) {
        Caixa btn_menu = new Caixa(largura_menu * 0.8, largura_menu * 0.8, 2, Color.WHITE, Color.BLACK);
        btn_menu.setTranslateX(0);
        btn_menu.setTranslateY(0);
        btn_menu.setOnMouseEntered((event) -> {
            scene.setCursor(Cursor.HAND);
        });
        btn_menu.setOnMouseExited((event) -> {
            scene.setCursor(Cursor.DEFAULT);
        });

        btn_menu.setOnMouseClicked((event) -> {
            if (btn_menu.getTranslateX() == 0 + btn_menu.caixa.getStrokeWidth()) {
                btn_menu.setStrokeColor(Color.WHITE);
                btn_menu.setBackgroundColor(Color.BLACK);
                Animacoes.animacaoMovimento(btn_menu, tempo_animacao_menu, largura_menu + btn_menu.caixa.getStrokeWidth(), Double.NaN);
                Animacoes.animacaoMovimento(menu, tempo_animacao_menu, 0, Double.NaN);
            } else {
                btn_menu.setStrokeColor(Color.BLACK);
                btn_menu.setBackgroundColor(Color.WHITE);
                Animacoes.animacaoMovimento(btn_menu, tempo_animacao_menu, 0 + btn_menu.caixa.getStrokeWidth(), Double.NaN);
                Animacoes.animacaoMovimento(menu, tempo_animacao_menu, -largura_menu, Double.NaN);
            }
        });
        return btn_menu;
    }

    /**
     * Cria a caixa para informar os valores de posição e força das cargas
     * elétricas.
     *
     * @return A caixa.
     */
    public static Caixa criar_caixa_adicionar() {
        Caixa adicionar = new Caixa(largura_menu * 2 - 30, largura_menu + 80, 2, Color.WHITE, Color.CADETBLUE);

        ColorPicker cor = new ColorPicker(Color.WHITE);
        //cor.setMinSize(espacinho, espacinho); cor.setMaxSize(espacinho, espacinho);
        
        TextField nome = new TextField("Nome");
        nome.setAlignment(Pos.CENTER);
        
        TextField posicao = new TextField("X, Y");
        posicao.setAlignment(Pos.CENTER);
        TextField forca = new TextField("Potência da carga");
        forca.setAlignment(Pos.CENTER);
        Caixa btn_confirm = new Caixa(adicionar.getAltura_caixa() / 10, adicionar.caixa.getStrokeWidth(), Color.CADETBLUE, Color.CADETBLUE.darker());
        btn_confirm.setOnMouseClicked((event) -> {
            int posicao_procura[] = new int[1];
            posicao_procura[0] = 0;
            char string[] = posicao.getText().toCharArray();

            double X = encontrar_numero(string, posicao_procura, string.length, posicao_procura);
            double Y = encontrar_numero(string, posicao_procura, string.length, posicao_procura);

            string = forca.getText().toCharArray();
            posicao_procura[0] = 0;
            double carga = encontrar_numero(string, posicao_procura, string.length, posicao_procura);

            Entidade corpo_eletrico = new Entidade(6, carga, cor.getValue(), X+", "+Y); // TEM QUE COLORIR MELHOR ISSO AQUI...//
            Entidade.numero_entidades++;

            Fisica2.grade.adicionar_objeto(corpo_eletrico, X, Y);
        });

        adicionar.adicionar_conteudo(nome);
        adicionar.adicionar_conteudo(posicao);
        adicionar.adicionar_conteudo(forca);
        adicionar.adicionar_conteudo(btn_confirm);
        adicionar.adicionar_conteudo(cor);

        adicionar.realocar_conteudos(0, 0);
        adicionar.conteudo_caixa.get(0).setTranslateX(10);
        adicionar.conteudo_caixa.get(0).setTranslateY(10);
        
        adicionar.conteudo_caixa.get(1).setTranslateX(10);
        adicionar.conteudo_caixa.get(1).setTranslateY(40 + 10); //o 40 teve q ser no olho...//
        
        adicionar.conteudo_caixa.get(2).setTranslateX(10);
        adicionar.conteudo_caixa.get(2).setTranslateY(80 + 10); //o 80 teve q ser no olho...//
        
        adicionar.conteudo_caixa.get(3).setTranslateX(adicionar.getLargura_caixa() / 6*2);
        adicionar.conteudo_caixa.get(3).setTranslateY(btn_confirm.getAltura_caixa()/2 + 120);
        
        adicionar.conteudo_caixa.get(4).setTranslateX(adicionar.getLargura_caixa() / 6 * 4);
        adicionar.conteudo_caixa.get(4).setTranslateY(120);

        return adicionar;
    }

    private static double encontrar_numero(char string[], int comeco[], int fim, int posicao[]) {
        int posicao_inicio_numero = -1;
        int posicao_fim_numero = -1;
        boolean tem_ponto = false;

        try {
            for (int i = comeco[0]; i < fim; i++) {
                if (string[i] > 47 && string[i] < 58) {
                    if (posicao_inicio_numero == -1) {
                        posicao_inicio_numero = i;
                    }
                } else {
                    if (string[i] == 45 && posicao_inicio_numero == -1 && string[i + 1] > 47 && string[i + 1] < 58) {
                        posicao_inicio_numero = i;
                    } else if (string[i] == 46 && posicao_inicio_numero != -1 && !tem_ponto && string[i + 1] > 47 && string[i + 1] < 58) {
                        //ESSE IF É SO PRA DEIXAR PASSAR O PONTO DO NUMERO COM VIRGULA...//
                    } else if (posicao_inicio_numero != -1 && posicao_fim_numero == -1) {
                        posicao_fim_numero = i - 1;
                        posicao[0] = i;
                        break;
                    }
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
        }

        if (posicao_inicio_numero != -1 && posicao_fim_numero == -1) {
            posicao_fim_numero = fim - 1;
        }
        if (posicao_inicio_numero != -1 && posicao_fim_numero != -1) {
            return Double.parseDouble(new String(string, posicao_inicio_numero, posicao_fim_numero - posicao_inicio_numero + 1));
        }

        return 0;
    }
}