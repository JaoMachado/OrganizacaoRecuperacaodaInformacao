package template;

import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.util.ArrayList;
import java.util.List;

/**
 * Modelo de projeto básico da JSGE.
 *
 * JSGE basic project template.
 *
 * @author Prof. Dr. David Buzatto
 */
public class Main extends EngineFrame {

    private double tempoParaMudar;
    private double contadorTempo;
    private LabyrinthSolver ls;
    private List<int[]> caminhoRevelado;
    private int indexCaminho;
    private int indexSeta;

    public Main() {

        super(
                1100, // largura                      / width
                900, // algura                       / height
                "Labirinto", // título                       / title
                60, // quadros por segundo desejado / target FPS
                true, // suavização                   / antialiasing
                false, // redimensionável              / resizable
                false, // tela cheia                   / full screen
                false, // sem decoração                / undecorated
                false, // sempre no topo               / always on top
                false // fundo invisível              / invisible background
        );

    }

    /**
     * Cria o mundo do jogo. Esse método executa apenas uma vez durante a
     * inicialização da engine.
     *
     * Creates the game world. This method runs just one time during engine
     * initialization.
     */
    @Override
    public void create() {
        boolean[][] labyrinth = new boolean[][]{
            {false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false},
            {false, false, true, true, false, false, true, false, false, false, true, true, true, true, true, true},
            {false, false, false, true, false, false, true, false, false, false, false, false, false, false, false, false},
            {false, true, true, true, false, false, true, false, false, false, false, false, true, true, true, false},
            {false, false, true, false, true, false, true, false, false, false, true, false, true, false, false, false},
            {false, false, true, false, true, false, true, false, true, false, true, false, true, false, true, true},
            {false, false, true, false, true, false, true, false, true, false, true, false, true, false, false, false},
            {false, true, true, false, true, true, false, false, true, false, true, false, true, true, true, false},
            {false, false, false, false, false, true, false, false, true, false, true, false, true, false, false, false},
            {false, false, false, false, false, true, false, false, true, false, true, false, true, false, true, true},
            {false, false, true, true, true, true, false, false, true, false, true, false, true, false, false, false},
            {false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false},};

        ls = new LabyrinthSolver(labyrinth, 0, 0, 4, 5);
        caminhoRevelado = new ArrayList<>();
        indexCaminho = 0;
        indexSeta = 0;
        tempoParaMudar = 0.2;
    }

    /**
     * Lê a entrada do usuário e atualiza o mundo do jogo. Os métodos de entrada
     * devem ser usados aqui. Atenção: Você NÃO DEVE usar nenhum dos métodos de
     * desenho da engine aqui.
     *
     *
     * Reads user input and update game world. Input methods should be used
     * here. Warning: You MUST NOT use any of the engine drawing methods here.
     *
     * @param delta O tempo passado, em segundos, de um quadro para o outro.
     * Time passed, in seconds, between frames.
     */
    @Override
    public void update(double delta) {
        contadorTempo += delta;

        if (contadorTempo > tempoParaMudar) {
            contadorTempo = 0;
            if (indexCaminho < ls.getPath().size()) {
                caminhoRevelado.add(ls.getPath().get(indexCaminho));
                indexCaminho++;
                indexSeta = 0;
            } else if (indexSeta < 4) {
                indexSeta++;
            }
        }
    }

    /**
     * Desenha o mundo do jogo. Todas as operações de desenho DEVEM ser feitas
     * aqui.
     *
     * Draws the game world. All drawing related operations MUST be performed
     * here.
     */
    @Override
    public void draw() {
        desenharLabirinto(ls);
    }

    public void desenharLabirinto(LabyrinthSolver ls) {
        final int tamanhoCelula = 60;
        final Paint corCaminhoPercorrido = new Color(209, 231, 246);
        final Paint corFlecha = new Color(35, 107, 136);
        final Paint corPosicaoInicial = new Color(127, 219, 245);
        final Paint corPosicaoFinal = new Color(0, 176, 80);

        char[][] output = ls.getOutput();
        int[][] visitCount = ls.getTentativas();

        for (int i = 0; i < output.length; i++) {
            int y = tamanhoCelula + (i * tamanhoCelula);

            for (int j = 0; j < output[i].length; j++) {
                int x = tamanhoCelula + (j * tamanhoCelula);

                switch (output[i][j]) {
                    case 'x' ->
                        fillRectangle(x, y, tamanhoCelula, tamanhoCelula, BLACK);
                    case 's' ->
                        fillRectangle(x, y, tamanhoCelula, tamanhoCelula, corPosicaoInicial);
                    case 't' ->
                        fillRectangle(x, y, tamanhoCelula, tamanhoCelula, corPosicaoFinal);
                    default ->
                        fillRectangle(x, y, tamanhoCelula, tamanhoCelula, WHITE);
                }
            }
        }

        for (int[] pos : caminhoRevelado) {
            int i = pos[0];
            int j = pos[1];

            int x = tamanhoCelula + (j * tamanhoCelula);
            int y = tamanhoCelula + (i * tamanhoCelula);

            char cell = output[i][j];
            if (cell != 's' && cell != 't') {
                fillRectangle(x, y, tamanhoCelula, tamanhoCelula, corCaminhoPercorrido);
            }

            int visitas = visitCount[i][j];
            if (visitas > 0) {
                if (visitas == 1) {
                    drawArrow(x, y, 'd', corFlecha);
                } else if (visitas == 2) {
                    drawArrow(x, y, 'd', RED);
                    drawArrow(x, y, 'b', corFlecha);
                } else if (visitas == 3) {
                    drawArrow(x, y, 'd', RED);
                    drawArrow(x, y, 'b', RED);
                    drawArrow(x, y, 'e', corFlecha);
                } else if (visitas >= 4) {
                    drawArrow(x, y, 'd', RED);
                    drawArrow(x, y, 'b', RED);
                    drawArrow(x, y, 'e', RED);
                    drawArrow(x, y, 'c', corFlecha);
                }
                drawText(String.valueOf(visitas), x + tamanhoCelula / 2 + 17, y + tamanhoCelula / 2 + 17, BLACK);
            }
        }

        for (int i = 0; i <= output.length; i++) {
            drawLine(tamanhoCelula,
                    tamanhoCelula + (i * tamanhoCelula),
                    tamanhoCelula + tamanhoCelula * output[0].length,
                    tamanhoCelula + (i * tamanhoCelula), BLACK);
        }

        for (int i = 0; i <= output[0].length; i++) {
            drawLine(tamanhoCelula + (i * tamanhoCelula),
                    tamanhoCelula,
                    tamanhoCelula + (i * tamanhoCelula),
                    tamanhoCelula + tamanhoCelula * output.length, BLACK);
        }
    }

    private void drawArrow(int x, int y, char direction, Paint corFlecha) {
        Graphics2D g2d = getGraphics2D();
        int arrowTip = x + 60 / 2 + (int) (60 * 0.3);
        int xArrow = x + 60 / 2;
        int yArrow = y + 60 / 2;

        switch (direction) {
            case 'd' -> {
                g2d.rotate(Math.toRadians(0), xArrow, yArrow);

                drawLine(xArrow, yArrow, arrowTip, yArrow, corFlecha);
                drawLine(arrowTip, yArrow, arrowTip - 5, yArrow - 5, corFlecha);
                drawLine(arrowTip, yArrow, arrowTip - 5, yArrow + 5, corFlecha);

                g2d.rotate(Math.toRadians(-0), xArrow, yArrow);
            }
            case 'b' -> {
                g2d.rotate(Math.toRadians(90), xArrow, yArrow);

                drawLine(xArrow, yArrow, arrowTip, yArrow, corFlecha);
                drawLine(arrowTip, yArrow, arrowTip - 5, yArrow - 5, corFlecha);
                drawLine(arrowTip, yArrow, arrowTip - 5, yArrow + 5, corFlecha);

                g2d.rotate(Math.toRadians(-90), xArrow, yArrow);
            }
            case 'e' -> {
                g2d.rotate(Math.toRadians(180), xArrow, yArrow);

                drawLine(xArrow, yArrow, arrowTip, yArrow, corFlecha);
                drawLine(arrowTip, yArrow, arrowTip - 5, yArrow - 5, corFlecha);
                drawLine(arrowTip, yArrow, arrowTip - 5, yArrow + 5, corFlecha);

                g2d.rotate(Math.toRadians(-180), xArrow, yArrow);
            }
            case 'c' -> {
                g2d.rotate(Math.toRadians(270), xArrow, yArrow);

                drawLine(xArrow, yArrow, arrowTip, yArrow, corFlecha);
                drawLine(arrowTip, yArrow, arrowTip - 5, yArrow - 5, corFlecha);
                drawLine(arrowTip, yArrow, arrowTip - 5, yArrow + 5, corFlecha);

                g2d.rotate(Math.toRadians(-270), xArrow, yArrow);
            }
        }
    }

    /**
     * Instancia a engine e a inicia.Instantiates the engine and starts it.
     *
     * @param args
     */
    public static void main(String[] args) {
        Main main = new Main();
    }

}
