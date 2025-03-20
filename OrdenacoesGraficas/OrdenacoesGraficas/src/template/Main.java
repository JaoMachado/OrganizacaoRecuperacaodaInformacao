package template;

import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
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

    private int[] array;
    private List<int[]> listaSelection;
    private List<int[]> listaInsertion;
    private List<int[]> listaBubble;
    private List<int[]> listaMerge;
    
    private List<Integer> menorSelection;
    private List<Integer> menorInsertion;
    private List<Integer> menorBubble;
    private List<Integer> menorMerge;
    
    private int posSelection;
    private int posInsertion;
    private int posBubble;
    private int posMerge;

    private double tempoParaMudar;
    private double contadorTempo;

    public Main() {
        super(1200, 800, "Ordenações", 60, true);
    }

    @Override
    public void create() {
        array = new int[]{9, 10, 5, 6, 3, 1, 2, 8};
        
        listaSelection = new ArrayList<>();
        listaInsertion = new ArrayList<>();
        listaBubble = new ArrayList<>();
        listaMerge = new ArrayList<>();
        
        menorSelection = new ArrayList<>();
        menorInsertion = new ArrayList<>();
        menorBubble = new ArrayList<>();
        menorMerge = new ArrayList<>();
        
        tempoParaMudar = 1;
        
        copiarArray(listaSelection, array);
        copiarArray(listaInsertion, array);
        copiarArray(listaBubble, array);
        copiarArray(listaMerge, array);
        
        selectionSort(array.clone());
        insertionSort(array.clone());
        bubbleSort(array.clone());
        mergeSort(array.clone(), 0, array.length - 1);
    }

    @Override
    public void update(double delta) {
        contadorTempo += delta;

        if (contadorTempo > tempoParaMudar) {
            contadorTempo = 0;

            if (posSelection < listaSelection.size() - 1) {
                posSelection++;
            }

            if (posInsertion < listaInsertion.size() - 1) {
                posInsertion++;
            }

            if (posBubble < listaBubble.size() - 1) {
                posBubble++;
            }

            if (posMerge < listaMerge.size() - 1) {
                posMerge++;
            }
        }

    }

    @Override
    public void draw() {
        clearBackground(WHITE);

        drawText("ORDENAÇÕES", getScreenWidth() / 2 - 120, getScreenHeight() / 2 / 2 - 80, 40, BLACK);
        drawText("GRÁFICAS", getScreenWidth() / 2 - 100, getScreenHeight() / 2 / 2 - 30, 40, BLACK);
        
        drawText("SELECTION SORT", 75, 30, 40, BLACK);
        drawText("INSERTION SORT", getScreenWidth() / 2 + 185, 30, 40, BLACK);
        drawText("BUBBLE SORT", 115, 430, 40, BLACK);
        drawText("MERGE SORT", getScreenWidth() / 2 + 242, 430, 40, BLACK);

        desenharArray(listaSelection.get(posSelection), 50, getScreenHeight() / 2 - 50, 40, 10, 10, menorSelection, posSelection);
        desenharArray(listaInsertion.get(posInsertion), getScreenWidth() / 2 + 160, getScreenHeight() / 2 - 50, 40, 10, 10, menorInsertion, posInsertion);
        desenharArray(listaBubble.get(posBubble), 50, getScreenHeight() - 50, 40, 10, 10, menorBubble, posBubble);
        desenharArray(listaMerge.get(posMerge), getScreenWidth() / 2 + 160, getScreenHeight() - 50, 40, 10, 10, menorMerge, posMerge);

        drawRectangle(30, 20, 430, 350, BLACK);
        drawRectangle(getScreenWidth() / 2 + 140, 20, 430, 350, BLACK);
        drawRectangle(30, getScreenHeight() / 2 + 20, 430, 350, BLACK);
        drawRectangle(getScreenWidth() / 2 + 140, getScreenHeight() / 2 + 20, 430, 350, BLACK);
    }

    private void copiarArray(List<int[]> lista, int[] array) {
        int[] novoArray = new int[array.length];
        System.arraycopy(array, 0, novoArray, 0, array.length);
        lista.add(novoArray);
    }

    private void desenharArray(int[] array, int x, int y,int largura, int espacamento, int tamanhoPedaco, List<Integer> menorLista, int posicao) {
        int movido = -1;
    
        if (posicao < menorLista.size()) {
            movido = menorLista.get(posicao);
        }

        for (int i = 0; i < array.length; i++) {
            
            if (i == movido) {
                fillRectangle(
                        x + i * (largura + espacamento),
                        y - array[i] * tamanhoPedaco,
                        largura,
                        array[i] * tamanhoPedaco,
                        YELLOW
                );
            
            } else {
                fillRectangle(
                        x + i * (largura + espacamento),
                        y - array[i] * tamanhoPedaco,
                        largura,
                        array[i] * tamanhoPedaco,
                        BLUE
                );
            }
        }
    }

    private void selectionSort(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            int menor = i;
            
            for (int j = i + 1; j < array.length; j++) {
                
                if (array[j] < array[menor]) {
                    menor = j;
                }
                
            }
            
            int t = array[i];
            array[i] = array[menor];
            array[menor] = t;
            
            copiarArray(listaSelection, array);
            menorSelection.add(menor);
        }

    }
    
    private void insertionSort(int[] array){
        int chave;

        for (int j=1; j<array.length; j++) {
            chave = array[j];
            int i = j-1;
            
            while ( (i>=0) && (array[i] > chave ) ) {
                array[i+1] = array[i];
                i--;
            }
            
            array[i+1] = chave;
            
            copiarArray(listaInsertion, array);
            menorInsertion.add(j);
        }
    }
    
    private void bubbleSort(int[] array){
        int aux;
        int i;
        int j;
        int troca;

        troca = 1;

        for(j = array.length - 1; (j >= 1) && (troca == 1); j--){
            troca = 0;
            
            for(i = 0; i < j; i++){
                
                if(array[i] > array[i+1]){
                    aux = array[i];
                    array[i] = array[i+1];
                    array[i+1] = aux;
                    troca=1;
                    
                    copiarArray(listaBubble, array);
                    menorBubble.add(i + 1);
                }
            }
        }
    }
    
    private void mergeSort(int[] array, int inicio, int fim){
        int meio;
        
        if(inicio < fim){
            meio = (inicio + fim) / 2;
            mergeSort(array, inicio, meio);
            mergeSort(array, meio+1, fim);
            intercalaSemSentinela(array, inicio, meio, fim);
        }
    }
    
    void intercalaSemSentinela(int array[], int inicio, int meio, int fim){
        int i, j;
        int[] arrayB = array.clone();
        
        for(i = inicio; i <= meio; i++) {
            arrayB[i] = array[i];
        }
        
        for(j = meio+1; j <= fim; j++) {
            arrayB[fim + meio + 1 - j] = array[j];
        }
        
        i = inicio;
        j = fim;
        
        for(int k = inicio; k <= fim; k++) {
            
            if(arrayB[i] <= arrayB[j]) {
                array[k] = arrayB[i];
                i++;
            } else {
                array[k] = arrayB[j];
                j--;
            }
            
            copiarArray(listaMerge, array);
            menorMerge.add(k);
        }
    }

    /**
     * Instancia a engine e a inicia.
     *
     * Instantiates the engine and starts it.
     */
    public static void main(String[] args) {
        new Main();
    }

}
