
package compilador_lex;
import java.io.RandomAccessFile;
/**
 *
 * @author alexander
 */
public class lexico {
   public static Nodo cabeza = null, p;
    int estado = 0, columna, valorMT, numRenglon = 1;
    int caracter = 0;
    String lexema ="";
    boolean errorEncontrado = false;
    
    String archivo = "C:\\Users\\alexa\\OneDrive\\Escritorio\\Compi_java\\programa_fuente.txt";
    
     int matriz[][] ={


                  // L       d      _       .       '       +       -       *       /       >       <       =       (       )       ,       ;       :       {       }       eb      tab     nl      r       eof     oc 
                  // 0       1      2       3       4       5       6       7       8       9       10      11      12      13      14      15      16      17      18      19      20      21      22      23      24

        /*0*/      { 1,      2,     500,    500,    5,      105,    106,    107,    108,    6,      7,      113,    115,    116,    117,    118,    8,      9,      500,    0,      0,      0,      0,      0,      500},
        /*1*/      { 1,      1,     1,      100,    100,    100,    100,    100,    100,    100,    100,    100,    100,    100,    100,    100,    100,    100,    100,    100,    100,    100,    100,    100,    100},
        /*2*/      { 102,    2,     102,    3,      102,    102,    102,    102,    102,    102,    102,    102,    102,    102,    102,    102,    102,    102,    102,    102,    102,    102,    102,    102,    102},
        /*3*/      { 501,    4,     501,    501,    501,    501,    501,    501,    501,    501,    501,    501,    501,    501,    501,    501,    501,    501,    501,    501,    501,    501,    501,    501,    501},
        /*4*/      { 103,    4,     103,    103,    103,    105,    103,    103,    103,    103,    103,    103,    103,    103,    103,    103,    103,    103,    103,    103,    103,    103,    103,    103,    103},
        /*5*/      { 5,      5,     5,      5,      104,    5,      5,      5,      5,      5,      5,      5,      5,      5,      5,      5,      5,      5,      5,      5,      5,      503,    503,    5,      5},
        /*6*/      { 109,    109,   109,    109,    109,    109,    109,    109,    109,    109,    109,    110,    109,    109,    109,    109,    109,    109,    109,    109,    109,    109,    109,    109,    109},
        /*7*/      { 111,    111,   111,    111,    111,    111,    111,    111,    111,    114,    111,    112,    111,    111,    111,    111,    111,    111,    111,    111,    111,    111,    111,    111,    111},
        /*8*/      { 119,    119,   119,    119,    119,    119,    119,    119,    119,    119,    119,    120,    119,    119,    119,    119,    119,    119,    119,    119,    119,    119,    119,    119,    119},
        /*9*/      { 9,      9,     9,      9,      9,      9,      9,      9,      9,      9,      9,      9,      9,      9,      9,      9,      9,      9,      0,      9,      9,      9,      9,      502,    9}
        };
     
     String palReservadas[][] =
        {
                // 0                1    
        /*0*/    {"NOT",         "201"},
        /*1*/    {"AND",         "202"},
        /*2*/    {"TRUE",        "203"},
        /*3*/    {"FALSE",       "204"},
        /*4*/    {"SI",          "205"},
        /*5*/    {"ENTONCES",    "206"},
        /*6*/    {"FIN_SI",      "207"},
        /*7*/    {"SINO",        "208"},
        /*8*/    {"MIENTRAS",    "209"},
        /*9*/    {"HACER",       "210"},
        /*10*/   {"FIN_MIENTRAS","211"},
        /*11*/   {"ALGORITMO",   "212"},
        /*12*/   {"INICIO",      "213"},
        /*13*/   {"FIN",         "214"},
        /*14*/   {"LEER",        "215"},
        /*15*/   {"ESCRIBIR",    "216"},
        /*16*/   {"ES",          "217"},
        /*17*/   {"ENTERO",      "218"},
        /*18*/   {"DECIMAL",     "219"},
        /*19*/   {"STRING",      "220"},
        /*20*/   {"LOGICO",      "221"},
        /*20*/   {"OR",          "222"}
        };

        String errores[][] =
        {
                 // 0                               1    
        /*0*/    {"Caracter no valido",           "500"},
        /*1*/    {"Numero erroneo",               "501"},
        /*2*/    {"No se cerro el comentario",    "502"},
        /*3*/    {"No se cerro la cadena",        "503"},
        };
        
        RandomAccessFile file = null;
        
        public lexico(){
            try{
                file = new RandomAccessFile(archivo, "r");
                
                while (caracter != -1)
                {
                    caracter = file.read();

                    if (Character.isLetter((char) caracter))
                    {
                        columna = 0;
                    }else if (Character.isDigit((char) caracter)) {
                        columna = 1;
                    }
                    else
                    {
                        switch ((char) caracter)
                        {

                            case '_': columna = 2;
                                break;

                            case '.': columna = 3;
                                break;

                            case 39: columna = 4;
                                break;

                            case '+': columna = 5;
                                break;
                            case '-':
                                columna = 6;
                                break;
                            case '*':
                                columna = 7;
                                break;
                            case '/':
                                columna = 8;
                                break;

                            case '>': columna = 9;
                                break;

                            case '<':
                                columna = 10;
                                break;

                            case '=':
                                columna = 11;
                                break;

                            case '(':
                                columna = 12;
                                break;

                            case ')':
                                columna = 13;
                                break;

                            case ',':
                                columna = 14;
                                break;

                            case ';':
                                columna = 15;
                                break;

                            case ':':
                                columna = 16;
                                break;

                            case '{':
                                columna = 17;
                                break;

                            case '}':
                                columna = 18;
                                break;

                            case 10: {
                                    columna = 21;
                                    numRenglon = numRenglon + 1;
                                }
                                break;

                            case 9:
                                {
                                    columna = 20;
                                }
                                break;
                                
                            case 13:{
                            columna = 22;
                            }
                            break;
                            
                            
                            case 32:{columna = 21;}
                            break;
                            
                            case 3:{
                                columna = 23;
                            }
                            break;

                            default:{
                               
                            columna = 24;
                            } 
                                break;
                        }
                        
                        if(caracter == -1){
                            columna = 23;
                        }
                    }

                    valorMT = matriz[estado][columna];

                    if (valorMT < 100)
                    {
                        estado = valorMT;

                        if (estado == 0)
                        {
                            lexema = "";
                        }
                        else
                        {
                            lexema = lexema + (char)caracter;
                        }
                    }else if (valorMT >=100 && valorMT < 500)
                    {
                        if (valorMT == 100)
                        {
                            validarSiEsPalabraReservada();
                        }

                        if (valorMT ==100 || valorMT ==102 || valorMT == 103 || valorMT == 109 || valorMT == 111 || 
                            valorMT == 119 )
                        {
                            file.seek(file.getFilePointer() -1);
                            
                        }
                        else
                        {
                            lexema = lexema + (char)caracter;
                        }

                        insertarNodo();
                        estado = 0;
                        lexema = "";
                    }
                    else
                    {
                        imprimirMensajeError();
                        break;
                    }
                }
                imprimirNodo();
            }catch(Exception e){
                System.out.println(e.getMessage());
        } finally{
            
                try{
                    if(file != null){
                        file.close();
                    }
                }catch(Exception e){
                    System.out.println(e.getMessage());
                }
            }
        }
            
         public void imprimirNodo()
        {
            p = cabeza;
            while (p != null)
            {
                System.out.println(p.lexema + " " + p.token + " " + p.renglon);
                p = p.sig;
            }
        }

        public void imprimirMensajeError()
        {
            if ( /*caracter != -1 &&*/ valorMT >= 500)
            {
                for(String[] errore: errores)
                {
                    if (valorMT == Integer.valueOf(errore[1]))
                    {
                        System.out.println("El error encontrda es: " + errore[0] + " error: " +valorMT+ " caracter: "+caracter+" en el renglon: "+numRenglon);
                    }
                }

                errorEncontrado = true;
                
            }
        }

        public void insertarNodo()
        {
            Nodo nodo = new Nodo(lexema, valorMT, numRenglon);

            if (cabeza == null)
            {
                cabeza = nodo;
                p = cabeza;
            }
            else
            {
                p.sig = nodo;
                p = nodo;
            }
        }

        public void validarSiEsPalabraReservada()
        {
            for(String[] palReservada: palReservadas)
            {
                if (lexema.equals(palReservada[0]))
                {
                    valorMT = Integer.valueOf(palReservada[1]);
                }


            }
           
        }

}

