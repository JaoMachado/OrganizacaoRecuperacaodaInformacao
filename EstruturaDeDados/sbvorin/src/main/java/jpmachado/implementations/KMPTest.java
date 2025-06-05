package jpmachado.implementations;

import jpmachado.dependents.KMP.KMP;

/**
 * Teste do algoritmo KMP.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class KMPTest {
    
    public static void main( String[] args ) {
        
        String pat = "JOAOPEDROMACHADOSILVA";
        String txt = "MACHADO";
        char[] pattern = pat.toCharArray();
        char[] text = txt.toCharArray();

        KMP kmp1 = new KMP( pat );
        int offset1 = kmp1.search( txt );

        KMP kmp2 = new KMP( pattern, 256 );
        int offset2 = kmp2.search( text );

        // imprime o resultado
        System.out.println( "text:    " + txt );

        System.out.print( "pattern: " );
        for ( int i = 0; i < offset1; i++ ) {
            System.out.print( " " );
        }
        System.out.println( pat );

        System.out.print( "pattern: " );
        for ( int i = 0; i < offset2; i++ ) {
            System.out.print( " " );
        }
        System.out.println( pat );
        
    }

}
