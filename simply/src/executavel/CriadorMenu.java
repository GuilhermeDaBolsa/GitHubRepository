package executavel;

import Biblioteca.InteractiveObjects.InterligaElementos;
import Biblioteca.Animacoes;
import Biblioteca.OrganizadoresDeNodos.TabelaMenu;
import Biblioteca.OrganizadoresDeNodos.Caixa;
import java.util.ArrayList;
import javafx.geometry.Pos;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeType;

public class CriadorMenu {
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
        Caixa btn_add = new Caixa(largura_menu * porcentagem_btn, largura_menu * porcentagem_btn, Color.CADETBLUE, 1, Color.BLACK);
        btn_add.events_handler.onMouseButtonReleased().addRunnable(() -> {
            Fisica2.caixa_adicionar_carga.switchAtivarDesativar();
        }); 

        btn_add.events_handler.actionCleaner().addRunnable(() -> {
            Fisica2.caixa_adicionar_carga.desativar();
        });
        botoes.add(btn_add);
        
        Caixa btn_config = new Caixa(largura_menu * porcentagem_btn, largura_menu * porcentagem_btn, Color.DARKGRAY, 1, Color.BLACK);
        btn_config.events_handler.onMouseButtonReleased().addRunnable(() -> {
            for (int i = -150; i < 151; i++) {
                Fisica2.grade.adicionar_objeto(new Entidade(6, 12, Color.AQUA, i + ", " + i), i, i);
            }
        });
        botoes.add(btn_config);
        
        Caixa btn_remove = new Caixa(largura_menu * porcentagem_btn, largura_menu * porcentagem_btn, Color.FIREBRICK, 1, Color.BLACK);
        botoes.add(btn_remove);
        btn_remove.events_handler.onMouseButtonReleased().addRunnable(() -> {
            Runtime runtime = Runtime.getRuntime();
            //runtime.gc();
            long memory = runtime.totalMemory() - runtime.freeMemory();
            System.out.println("Used memory in MB: " + memory / 1024 / 1024);
        });
        
        TabelaMenu tabela = new TabelaMenu(0, 50, 0, 40, 1, false, btn_add, btn_config, btn_remove);
        InterligaElementos junta = new InterligaElementos(btn_add, btn_config, btn_remove);
        junta.setButtonTypes(-2,2,-2);
        junta.setEventosVisuais(Color.BLACK, Color.WHITE, Color.CHARTREUSE);
        Caixa menu = new Caixa(largura_menu, 100, Color.BLACK, 2, Color.BLACK);
        menu.add(tabela);
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
        Caixa btn_menu = new Caixa(largura_menu * 0.8, largura_menu * 0.8, Color.WHITE, 2, Color.BLACK);
        btn_menu.setTranslateX(0);
        btn_menu.setTranslateY(0);

        btn_menu.setOnMouseClicked((event) -> {
            if (btn_menu.getTranslateX() == 0 + btn_menu.caixa.getStrokeWidth()) {
                btn_menu.ySetStroke(null, Color.WHITE, StrokeType.CENTERED, true);
                btn_menu.caixa.setFill(Color.BLACK);
                Animacoes.animacaoMovimento(btn_menu, tempo_animacao_menu, largura_menu + btn_menu.caixa.getStrokeWidth(), null);
                Animacoes.animacaoMovimento(menu, tempo_animacao_menu, 0.0, null);
            } else {
                btn_menu.ySetStroke(null, Color.BLACK, StrokeType.CENTERED, true);
                btn_menu.caixa.setFill(Color.WHITE);
                Animacoes.animacaoMovimento(btn_menu, tempo_animacao_menu, 0 + btn_menu.caixa.getStrokeWidth(), null);
                Animacoes.animacaoMovimento(menu, tempo_animacao_menu, -largura_menu, null);
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
        Caixa adicionar = new Caixa(largura_menu * 2 - 30, largura_menu + 80, Color.WHITE, 0, Color.BLACK);

        ColorPicker cor = new ColorPicker(Color.WHITE);
        //cor.setMinSize(espacinho, espacinho); cor.setMaxSize(espacinho, espacinho);
        
        TextField nome = new TextField("Nome");
        nome.setAlignment(Pos.CENTER);
        
        TextField posicao = new TextField("X, Y");
        posicao.setAlignment(Pos.CENTER);
        TextField forca = new TextField("Potência da carga");
        forca.setAlignment(Pos.CENTER);
        Caixa btn_confirm = new Caixa(adicionar.getAlturaCaixa()/ 10, Color.CADETBLUE, adicionar.caixa.getStrokeWidth(), Color.CADETBLUE.darker());
        btn_confirm.caixa.setTranslateX(0);
        btn_confirm.caixa.setTranslateY(0);
        btn_confirm.setOnMouseClicked((event) -> {
            int posicao_procura[] = {0};
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

        adicionar.add(nome);
        adicionar.add(posicao);
        adicionar.add(forca);
        adicionar.add(btn_confirm);
        adicionar.add(cor);

        adicionar.realocar_conteudos(0.0, 0.0);
        adicionar.conteudo_caixa.get(0).setTranslateX(10);
        adicionar.conteudo_caixa.get(0).setTranslateY(10);
        
        adicionar.conteudo_caixa.get(1).setTranslateX(10);
        adicionar.conteudo_caixa.get(1).setTranslateY(40 + 10); //o 40 teve q ser no olho...//
        
        adicionar.conteudo_caixa.get(2).setTranslateX(10);
        adicionar.conteudo_caixa.get(2).setTranslateY(80 + 10); //o 80 teve q ser no olho...//
        
        adicionar.conteudo_caixa.get(3).setTranslateX(adicionar.getLarguraCaixa()/ 6*2);
        adicionar.conteudo_caixa.get(3).setTranslateY(btn_confirm.getAlturaCaixa()/2 + 120);
        
        adicionar.conteudo_caixa.get(4).setTranslateX(adicionar.getLarguraCaixa() / 6 * 4);
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