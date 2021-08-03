
package compilador_lex;
import java.io.IOException;

public class Compilador_lex {

    public static void main(String[] args) {
        
        lexico lexico = new lexico();
        Sintactico sintactico = new Sintactico(lexico.cabeza);
       if(!lexico.errorEncontrado){
       System.out.println("Lexico terminado");
    
 
       if(!sintactico.errorEncontrado){
           System.out.println("Sintactico terminado");
       }
   }
    }
}
