package template;

import java.util.ArrayList;
import java.util.List;



/**
 * Um resolvedor de labirintos que usa backtracking.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class LabyrinthSolver {
    
    private boolean[][] labyrinth;
    private char[][] output;
    public int[][] tentativas;
    
    private boolean hasSolution;
    private List<int[]> path; // Lista para armazenar o caminho na ordem correta
    
    public LabyrinthSolver(boolean[][] labyrinth, int sourceLine, int sourceColumn, int targetLine, int targetColumn) {
        this.labyrinth = labyrinth;
        this.path = new ArrayList<>();
        
        if (!validPosition(sourceLine, sourceColumn)) {
            throw new IllegalArgumentException("Invalid source position.");
        }
        if (!validPosition(targetLine, targetColumn)) {
            throw new IllegalArgumentException("Invalid target position.");
        }
        
        tentativas = new int[labyrinth.length][labyrinth[0].length];
        output = new char[labyrinth.length][labyrinth[0].length];
        
        for (int i = 0; i < output.length; i++) {
            for (int j = 0; j < output[i].length; j++) {
                output[i][j] = labyrinth[i][j] ? 'x' : ' ';
                tentativas[i][j] = 0;
            }
        }
        
        hasSolution = solve(sourceLine, sourceColumn, targetLine, targetColumn);
        output[sourceLine][sourceColumn] = 's';
    }
    
    private boolean solve(int line, int col, int targetLine, int targetColumn) {
        if (validPosition(line, col)) {
            labyrinth[line][col] = true; 
            path.add(new int[]{line, col}); // Armazena posição visitada
            
            if (line == targetLine && col == targetColumn) {
                output[line][col] = 't';
                return true;
            }
            
            // Direções
            tentativas[line][col]++;
            if (solve(line, col + 1, targetLine, targetColumn)) { 
                output[line][col] = 'd'; 
                return true; 
            }
            
            tentativas[line][col]++;
            if (solve(line + 1, col, targetLine, targetColumn)) { 
                output[line][col] = 'b'; 
                return true; 
            }
            
            tentativas[line][col]++;
            if (solve(line, col - 1, targetLine, targetColumn)) { 
                output[line][col] = 'e'; 
                return true; 
            }
            
            tentativas[line][col]++;
            if (solve(line - 1, col, targetLine, targetColumn)) { 
                output[line][col] = 'c'; 
                return true; 
            }
        }
        
        return false;
    }
    
    public List<int[]> getPath() {
        return path;
    }

    public char[][] getOutput() {
        return output;
    }

    
    private boolean validPosition( int line, int column ) {
        return line >= 0 &&
               line < labyrinth.length &&
               column >= 0 &&
               column < labyrinth[line].length &&
               !labyrinth[line][column];
    }

    public boolean[][] getLabyrinth() {
        return labyrinth;
    }
    
    public int[][] getTentativas() {
        return tentativas;
    }

    public boolean hasSolution() {
        return hasSolution;
    }
    
    @Override
    public String toString() {
        
        StringBuilder sb = new StringBuilder();
        
        for ( int i = 0; i < output.length; i++ ) {
            if ( i != 0 ) {
                sb.append( "\n" );
            }
            for ( int j = 0; j < output[i].length; j++ ) {
                sb.append( output[i][j] );
            }
        }
        
        return sb.toString();
        
    }
    
}
