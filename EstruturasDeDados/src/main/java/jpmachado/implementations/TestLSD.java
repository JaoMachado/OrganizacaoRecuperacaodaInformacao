package jpmachado.implementations;

import jpmachado.dependents.LSD.LSD;

/**
 * Testes do algoritmo LSD.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class TestLSD {
    
    public static void main( String[] args ) {
        
        // as strings precisam ter o mesmo comprimento
        String[] strings = {
            "aba", 
            "bac", 
            "caa", 
            "acb", 
            "bab", 
            "cca", 
            "aac", 
            "bba",
            "jpms"
        };

        // ordena as strings
        LSD.sort( strings, strings[0].length() );

        // mostra o resultado
        for ( String s : strings ) {
            System.out.println( s );
        }
        
    }
    
}
