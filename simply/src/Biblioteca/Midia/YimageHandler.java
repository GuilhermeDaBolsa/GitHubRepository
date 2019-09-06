package Biblioteca.Midia;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javax.imageio.ImageIO;


//NA DOCUMENTAÇÃO DIZER PRO CARA USAR O IMAGEVIEW OU BUFFERED IMAGE DO JAVA MESMO PRA MOSTRAR


public class YimageHandler {
    private static final int TAXA_DE_REDUCAO = 2;
    
    public static Image carregar_imagem(String caminho_imagem) {
        Image imagem = null;
        try {
            imagem = new Image(new File(caminho_imagem).toURI().toURL().toExternalForm());
        } catch (MalformedURLException ex) {
            System.out.println("PAU");
        }
        
        return imagem;
    }
    
    /**
     * <div align="justify">
     * Função que recebe uma imagem por url como BufferedImage , redimensiona a
     * imagem para o tamanho da constante TAMANHO_IMAGEM1 e transforma a
     * BufferedImage para uma WritableImage, que a partir desta gera e retorna
     * um ImageView para poder visualizar a imagem.
     *
     * @param tamanho Um objeto do tipo <b>TuplaXY</b> que representa as
     * dimensões da imagem. <br>
     * @param caminho_imagem Um objeto do tipo <b>String</b> que representa o diretório da
     * imagem. <br>
     * @return Um objeto do tipo <b>ImageView</b> que representa a imagem
     * redimensionada. <br>
     * @see #redimencionar_imagem_multiplos_passos(java.awt.image.BufferedImage)
     * @see #converter_para_WritableImage(java.awt.image.BufferedImage,
     * javafx.scene.image.WritableImage)
     * </div>
     */
    public static BufferedImage carregar_diminuir_imagem(String caminho_imagem, Point2D tamanho) {
        BufferedImage imagem = null;

        try {
            imagem = ImageIO.read(new File(caminho_imagem));
        } catch (IOException ex) {
            System.out.println("Imagem não encontrada!");
        }
        imagem = redimencionar_imagem(imagem, tamanho);
        return imagem;
    }
    
    /**
     * <div align="justify">
     * Método que recebe um objeto do tipo <b>BufferedImage</b> que representa a
     * imagem a ser convertida para um objeto do tipo <b>ImageView</b>.
     *
     * @param imagem Um objeto do tipo <b>BufferedImage</b> que representa a
     * imagem a ser convertida.
     * @return Um objeto do tipo <b>ImageView</b> que representa o objeto
     * convertido.
     * </div>
     */
    public static ImageView converter_para_ImageView(BufferedImage imagem) {
        WritableImage imagem_editavel = null;

        if (imagem != null) {
            imagem_editavel = converter_para_WritableImage(imagem);
        }
       
        
       
        return new ImageView(imagem_editavel);
    }
    
    /**
     * <div align="justify">
     * Método para converter um objeto do tipo <b>BufferedImage</b> para um
     * objeto do tipo <b>WritableImage</b>.
     *
     * @param imagem Um objeto do tipo <b>BufferedImage</b> que representa a
     * imagem original.
     * @return Um objeto do tipo <b>WritableImage</b> que representa a imagem
     * convertida.
     * </div>
     */
    public static WritableImage converter_para_WritableImage(BufferedImage imagem) {
        WritableImage imagem_editavel = new WritableImage(
                imagem.getWidth(), imagem.getHeight());
        PixelWriter editor_de_pixel = imagem_editavel.getPixelWriter();

        for (int x = 0; x < imagem.getWidth(); x++) {
            for (int y = 0; y < imagem.getHeight(); y++) {
                editor_de_pixel.setArgb(x, y, imagem.getRGB(x, y));
            }
        }
        return imagem_editavel;
    }
    
    /**
     * <div align="justify">
     * Método para redimensionar uma imagem mantendo uma boa resolução.
     *
     * @param imagem Um objeto do tipo <b>BufferedImage</b> que representa a
     * imagem a ser redimensionada.<br>
     * @param tamanho Um objeto do tipo <b>TuplaXY</b> que representa as novas
     * dimensões da imagem.
     * @return Um objeto do tipo <b>BufferedImage</b> que representa a imagem
     * redimensionada.
     * @see #redimensionar_imagem(java.awt.image.BufferedImage, int, int)
     * </div>
     */
    private static BufferedImage redimencionar_imagem(BufferedImage imagem, Point2D tamanho) {
        while (imagem.getHeight() / TAXA_DE_REDUCAO > tamanho.getY()
                && imagem.getWidth() / TAXA_DE_REDUCAO > tamanho.getX()) {
            imagem = redimensionar_imagem(imagem,
                    imagem.getHeight() / TAXA_DE_REDUCAO,
                    imagem.getWidth() / TAXA_DE_REDUCAO);
        }
        return redimensionar_imagem(imagem, (int) tamanho.getX(), (int) tamanho.getY());
    }
    
    /**
     * <div align="justify">
     * Método para redimensionar a imagem para o tamanho desejado. É
     * recomendado, para não perder resolução, redimensionar a imagem para, no
     * máximo, metade do seu tamanho, repetindo o processo até atingir o tamanho
     * ideal.
     *
     * @param imagem Um objeto do tipo <b>BufferedImage</b> que representa a
     * imagem a ser redimensionada. <br>
     * @param largura_final Um inteiro que represnta a largura final. <br>
     * @param altura_final Um inteiro que representa a altura final. <br>
     * @return Um objeto do tipo <b>BufferedImage</b> que representa a imagem
     * redimensionada.
     * </div>
     */
    private static BufferedImage redimensionar_imagem(BufferedImage imagem,
            int largura_final, int altura_final) {
        BufferedImage imagem_final = new BufferedImage(largura_final,
                altura_final, BufferedImage.TYPE_INT_ARGB);
        Graphics2D grafico_da_imagem = imagem_final.createGraphics();

        grafico_da_imagem.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        grafico_da_imagem.drawImage(imagem, 0, 0, largura_final, altura_final, null);
        grafico_da_imagem.dispose();

        return imagem_final;
    }
    /*public void exibirFoto(){
		ImagensPersonagem regDao = new ImagensPersonagem();
		ImagensPersonagem reg = new ImagensPersonagem();		
		byte[] foto = null;
		//LENDO E COPIANDO IMAGEM ##############################################
		BufferedImage img = null; 
		try {
			img = ImageIO.read(new ByteArrayInputStream(reg.getNomeImagem()));
			lblFoto.setIcon(new ImageIcon(img));	
			ImageIO.write(img, "PNG", new File("C:/Downloads/h.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
}*/
}
