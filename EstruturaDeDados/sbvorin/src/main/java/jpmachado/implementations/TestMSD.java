package jpmachado.implementations;

import jpmachado.dependents.MSD.MSD;

/**
 * Testes do algoritmo MSD.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class TestMSD {
    
    public static void main(String[] args) {
        
        String[] strings = { 
            "david",
            "aurora",
            "marcela",
            "joana",
            "brendo",
            "maria",
            "juca",
            "carolina",
            "joao Pedro Machado"
        };

        // ordena as strings
        MSD.sort( strings );

        // mostra o resultado
        for ( String s : strings ) {
            System.out.println( s );
        }
        
    }
    
}
