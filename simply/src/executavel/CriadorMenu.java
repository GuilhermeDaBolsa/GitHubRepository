package executavel;

import Biblioteca.InteractiveObjects.YLinkElements;
import Biblioteca.Animacoes;
import Biblioteca.OrganizadoresDeNodos.TabelaMenu;
import Biblioteca.OrganizadoresDeNodos.YBox;
import java.util.ArrayList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeType;

public class CriadorMenu {
    static double largura_menu = 75;
    static double porcentagem_btn = 0.5;
    static double tempo_animacao_menu = 0.3;
    public static ArrayList<YBox> botoes = new ArrayList();

    /**
     * Cria a barra de menu com os botões de ações.
     *
     * @return O menu.
     */
    public static YBox cria_menu() {
        YBox btn_add = new YBox(largura_menu * porcentagem_btn, largura_menu * porcentagem_btn, Color.CADETBLUE, 1, Color.BLACK);
        btn_add.yGetEventsHandler().onMouseClicked().addHandleble((event) -> {
            Fisica2.caixa_adicionar_carga.switchAtivarDesativar();
        }); 

        btn_add.yGetEventsHandler().actionCleaner().addHandleble((event) -> {
            Fisica2.caixa_adicionar_carga.desativar();
        });
        botoes.add(btn_add);
        
        YBox btn_config = new YBox(largura_menu * porcentagem_btn, largura_menu * porcentagem_btn, Color.DARKGRAY, 1, Color.BLACK);
        btn_config.yGetEventsHandler().onMouseClicked().addHandleble((event) -> {
            for (int i = -150; i < 151; i++) {
                Fisica2.grade.adicionar_objeto(new Entidade(6, 12, Color.AQUA, i + ", " + i), i, i);
            }
        });
        botoes.add(btn_config);
        
        YBox btn_remove = new YBox(largura_menu * porcentagem_btn, largura_menu * porcentagem_btn, Color.FIREBRICK, 1, Color.BLACK);
        botoes.add(btn_remove);
        btn_remove.yGetEventsHandler().onMouseClicked().addHandleble((event) -> {
            Runtime runtime = Runtime.getRuntime();
            //runtime.gc();
            long memory = runtime.totalMemory() - runtime.freeMemory();
            System.out.println("Used memory in MB: " + memory / 1024 / 1024);
        });
        
        TabelaMenu tabela = new TabelaMenu(0, 50, 0, 40, 1, false, btn_add, btn_config, btn_remove);
        
        YLinkElements junta = new YLinkElements(btn_add, btn_config, btn_remove);
        junta.yAddElementsTypes(2, 2, 2);
        junta.setEventosVisuais(Color.BLACK, Color.WHITE, Color.CHARTREUSE);
        
        YBox menu = new YBox(largura_menu, 200, Color.BLACK, 2, Color.BLACK);
        menu.yAddContent(tabela);
        //menu.yMoveContents(-2, espacinho * 2);
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
    public static YBox cria_botao_menu(YBox menu) {
        YBox btn_menu = new YBox(largura_menu * 0.8, largura_menu * 0.8, Color.WHITE, 2, Color.BLACK);
        btn_menu.setTranslateX(0);
        btn_menu.setTranslateY(0);

        btn_menu.setOnMouseClicked((event) -> {
            if (btn_menu.getTranslateX() == 0 + btn_menu.box.getStrokeWidth()) {
                btn_menu.ySetStroke(null, Color.WHITE, StrokeType.CENTERED, true);
                btn_menu.box.setFill(Color.BLACK);
                Animacoes.animacaoMovimento(btn_menu, tempo_animacao_menu, largura_menu + btn_menu.box.getStrokeWidth(), null);
                Animacoes.animacaoMovimento(menu, tempo_animacao_menu, 0.0, null);
            } else {
                btn_menu.ySetStroke(null, Color.BLACK, StrokeType.CENTERED, true);
                btn_menu.box.setFill(Color.WHITE);
                Animacoes.animacaoMovimento(btn_menu, tempo_animacao_menu, 0 + btn_menu.box.getStrokeWidth(), null);
                Animacoes.animacaoMovimento(menu, tempo_animacao_menu, -largura_menu, null);
            }
        });
        return btn_menu;
    }

    /**
     * Cria a box para informar os valores de posição e força das cargas
 elétricas.
     *
     * @return A box.
     */
    public static YBox criar_caixa_adicionar() {
        YBox adicionar = new YBox(largura_menu * 2 - 30, largura_menu + 80, Color.WHITE, 0, Color.BLACK);

        ColorPicker cor = new ColorPicker(Color.WHITE);
        //cor.setMinSize(espacinho, espacinho); cor.setMaxSize(espacinho, espacinho);
        
        TextField nome = new TextField("Nome");
        nome.setAlignment(Pos.CENTER);
        
        TextField posicao = new TextField("X, Y");
        posicao.setAlignment(Pos.CENTER);
        TextField forca = new TextField("Potência da carga");
        forca.setAlignment(Pos.CENTER);
        YBox btn_confirm = new YBox(adicionar.yGetBoxHeight()/ 10, Color.CADETBLUE, adicionar.box.getStrokeWidth(), Color.CADETBLUE.darker());
        btn_confirm.box.setTranslateX(0);
        btn_confirm.box.setTranslateY(0);
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

        adicionar.yAddContent(nome);
        adicionar.yAddContent(posicao);
        adicionar.yAddContent(forca);
        adicionar.yAddContent(btn_confirm);
        adicionar.yAddContent(cor);

        adicionar.realocar_conteudos(0.0, 0.0);
        Node content[] = adicionar.yGetContent();
        
        content[0].setTranslateX(10);
        content[0].setTranslateY(10);
        
        content[1].setTranslateX(10);
        content[1].setTranslateY(40 + 10); //o 40 teve q ser no olho...//
        
        content[2].setTranslateX(10);
        content[2].setTranslateY(80 + 10); //o 80 teve q ser no olho...//
        
        content[3].setTranslateX(adicionar.yGetBoxWidth()/ 6*2);
        content[3].setTranslateY(btn_confirm.yGetBoxHeight()/2 + 120);
        
        content[4].setTranslateX(adicionar.yGetBoxWidth() / 6 * 4);
        content[4].setTranslateY(120);

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