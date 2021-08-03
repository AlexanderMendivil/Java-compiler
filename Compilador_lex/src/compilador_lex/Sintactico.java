
package compilador_lex;

/**
 *
 * @author Alexander
 */
public class Sintactico {
    Nodo p;
    boolean errorEncontrado = false;

    
      String errores[][] =
        {               
                //            0                                                                                               1
            /*0*/     {"Se espera la palabra reservada 'ALGORTIMO'" ,                                                     "504" },
            /*1*/     {"Se espera un identificador" ,                                                                     "505" },
            /*2*/     {"Se espera ( " ,                                                                                   "506" },
            /*3*/     {"Se espera )" ,                                                                                    "507" },
            /*4*/     {"Se espera la palabra reservada 'ES'" ,                                                            "508" },
            /*5*/     {"Se espera : " ,                                                                                   "509" },
            /*7*/     {"Se espera el tipo de variable: ENTERO, DECIMAL, STRING, LOGICO" ,                                 "510" },
            /*8*/     {"Se espera el asignador := " ,                                                                     "511" },
            /*9*/     {"Se espera la palabra reservada 'FIN'",                                                            "512" },
            /*10*/    {"Se espera la palabra reservada 'INICIO'",                                                         "513" },
            /*11*/    {"Se espera la palabra reservada FIN_MIENTRAS",                                                     "514" },
            /*12*/    {"Se espera la palabra reservada ENTONCES",                                                         "515" },
            /*13*/    {"Se espera la palabra reservada FIN_SI",                                                           "516" },
            /*14*/    {"Se espera la palabra reservada HACER",                                                            "517" },
            /*15*/    {"Se espera una accion  ",                                                                          "518" },
            /*16*/    {"Se espera un operador relacional/expresion numerica",                                             "519" }
        };
    
    Sintactico(Nodo cabeza){
           p = cabeza;
           try{
            while (p!=null){
                
                if(p.token == 212){ //ALGORITMO
                p = p.sig;
                    if(p.token == 100){ // id
                    p = p.sig;
                        if(p.token == 115){ // (
                        p = p.sig;
                            if(p.token == 116){ // )
                            p = p.sig;
                                if(p.token == 217){ // ES
                                p = p.sig;    
                                decl_variable();
                                        if(p.token == 213){ // INICIO
                                         Acciones();
                                              if(p.token == 214){ // FIN
                                                  break;
                                                }else{
                                                  imprimirMensajeError(512);
                                                  break;
                                              }
                                        }else{
                                            imprimirMensajeError(513);
                                            break;
                                            }
                                }else{
                                    imprimirMensajeError(508);
                                    break;
                                    }
                            }else{
                                imprimirMensajeError(507);
                                break;
                                }
                        }else{
                            imprimirMensajeError(506);
                            break;
                        }
                    }else{
                        imprimirMensajeError(505);
                        break;
                    }
                 }else{
                    imprimirMensajeError(504);
                    break;
                }
           }
             }catch (Exception e){System.out.println("Fin de archivo inesperado. Error no controlado se espera FIN"); 
             errorEncontrado = true;
             }
    }
    
    private void imprimirMensajeError(int numError){
         errorEncontrado = true;
            
                for(String[] errore : errores){
                    if(numError == Integer.valueOf(errore[1])){
                    System.out.println("El error encontrado es: " + errore[0] + " error "+ errore[1]
                          + " en el renglon " +p.renglon+ "\n" );
                    break;
                    
                }
            }
        }

    private void decl_variable() {
        
        if(p.token == 100){ //ID
            p = p.sig;
                if(p.token== 119){ //:
                        nombre_tipo_simple();
                        p= p.sig;
                            if(p.token== 118){ //;
                                p = p.sig;
                                if(p.token == 213){
                                imprimirMensajeError(518);
                                }else{
                                decl_variable();
                                }
                                
                            }
                    }else{
                       imprimirMensajeError(509);
                    }
        }else{
            imprimirMensajeError(505);
        }
    }

    private void nombre_tipo_simple() {
        p = p.sig;
       if(p.token == 218 || p.token == 219 || p.token == 220 || p.token == 221){
       }else{
           imprimirMensajeError(510); 
    
       }
    }

    private void Acciones() {

        p = p.sig;
        switch(p.token){
 
            case 100: // id
                    asignacion();
                  if(p.token == 118){ //;
                        if(p.sig.token == 214 || p.sig.token == 207 || p.sig.token == 211 || p.sig.token == 208){
                           imprimirMensajeError(518);
                        }else{
                             
                        }
                  }
                    break;
            case 215: // LEER
                    leer();
                    if(p.token == 118){ //;
                        if(p.sig.token == 214 || p.sig.token == 207 || p.sig.token == 211 || p.sig.token == 208){
                           imprimirMensajeError(518);
                        }else{
                             
                        }
                        Acciones();
                    }
                    break;
            case 216: // ESCRIBIR
                    escribir();
                    if(p.token == 118){ //;
                        if(p.sig.token == 214 || p.sig.token == 207 || p.sig.token == 211 || p.sig.token == 208){
                           imprimirMensajeError(518);
                        }else{
                             
                        }
                        Acciones();
                    }
                    break;
            case 205: // SI
                    si();
                    
                    break; 
            case 209: // MIENTRAS
                    mientras();

                    break;         
            default:
                    break;
                
         }
        if(p.token == 118){ //;
            Acciones();
      }
}

 private void escribir() {
        p= p.sig;
        nombre_objeto();
    }
 
 private void nombre_objeto() {
       if(p.token==100){ // ID
          p = p.sig;
           if(p.token == 117){// ,
                p = p.sig;
                nombre_objeto();
       }
     }else{
       imprimirMensajeError(505);
       }
 }
 
  private void leer() {
        p= p.sig;
        nombre_objeto();
    }

 private void asignacion() {
        p= p.sig;
        if(p.token == 120){ // :=
            p= p.sig;
            expresion_numerica();
        }else{
            imprimirMensajeError(511);
        }
 }


private void expresion_numerica() {
        
        switch(p.token){
            
            case 115: // (
                    p = p.sig;
                    expresion_numerica();
                    if(p.token == 116 ) // )
                    {
                    p=p.sig;
                    expresion_numerica1();
                    }else{
                    imprimirMensajeError(507);
                    }
                    break;
            case 106: // -
                    p = p.sig;
                    expresion_numerica();
                    if(p.token != 116 ) // )                
                    { 
                           p=p.sig; 
                    }
                    expresion_numerica1();
                    break;
            case 100: //ID
                    if(p.token != 116 ) // )                
                    { 
                           p=p.sig; 
                           
                    }
                    expresion_numerica1();
                    break;
            case 103: //ND
                    if(p.token != 116 ) // )                
                    { 
                           p=p.sig; 
                           comp_expresion_relacional();
                    }
                    expresion_numerica1();
                    break; 
            case 102: //NE
                    if(p.token != 116 ) // )                
                    { 

                            p=p.sig; 
                            comp_expresion_relacional();

                    }
                    expresion_numerica1();
                    break;
            case 203: //TRUE
                    if(p.token != 116 ) // )                
                    { 
                           p=p.sig; 
                    }
                    expresion_numerica1();
                    break;        
            case 204: //FALSE
                    if(p.token != 116 ) // )                
                    { 
                        
                           p=p.sig; 
                    }
                    expresion_numerica1();
                    break;        
            case 104: //CADENA
                    if(p.token != 116 ) // )                
                    { 
                           p=p.sig; 
                    }
                    expresion_numerica1();
                    break; 
            default:
                    break;
                
         }

}

    private void comp_expresion_relacional(){
        
        if(p.token == 105 || p.token == 106 || p.token == 107 || p.token == 108 || p.token == 109 || p.token == 110 
                || p.token == 112 || p.token == 113 || p.token == 114 
                || p.token == 202 || p.token == 222 || p.token == 201 || p.token == 116 || p.token == 205 || p.token == 207 
                || p.token == 118 || p.token == 209 || p.token == 214 || p.token == 211 || p.token == 208 ){
        
        }else{
        imprimirMensajeError(519);   
        p = p.sig;
        }
    }
    private void expresion_numerica1() { 
        switch (p.token) {
            case 105: // +
                p = p.sig;
                expresion_numerica();
                expresion_numerica1();
                break;
            case 106: // -
                p = p.sig;
                expresion_numerica();
                expresion_numerica1();
                break;
            case 107: // *
                p = p.sig;
                expresion_numerica();
                expresion_numerica1();
                break;
            case 108: // /
                p = p.sig;
                expresion_numerica();
                expresion_numerica1();
                break;
            default:
                break;
           
        }
        
    }

    private void si() {
       p = p.sig;
       expresion_logica();
       if(p.token==206){ // ENTONCES
           Acciones();
            if(p.token == 208){ // SINO
                Acciones();
            }
            if(p.token == 207){ // FIN_SI
                p = p.sig;
                if(p.token == 118){
                    if(p.sig.token == 214 || p.sig.token == 207 || p.sig.token == 211 || p.sig.token == 208){
                           imprimirMensajeError(518);
                        }else{
                             
                        }
                }
            }else{
                imprimirMensajeError(516);
            }
       }else{
           imprimirMensajeError(515);
        
       }
    }

    private void expresion_logica() {
        switch(p.token){
            
            case 115: // (
                    p = p.sig;
                    expresion_logica();
                    if(p.token == 116 ) // )
                    {
                        p=p.sig;
                        
                        expresion_logica1();
                    }else{
                        imprimirMensajeError(507);
                    }
                    break;
            case 201:// NOT
                    p = p.sig;
                    expresion_logica();
                    expresion_logica1();
                break;
            case 100: //ID
                    if(p.sig.token == 109 | p.sig.token == 110 | p.sig.token == 111 | 
                            p.sig.token == 112 | p.sig.token == 113  | p.sig.token == 114 ){
                    expresion_relacional();
                    expresion_logica1();
                    }else{
                        p = p.sig;
                    comp_expresion_relacional();
                    expresion_logica1();
                    }
                    break;
            case 203: // TRUE
                    p =p.sig;
                    expresion_numerica1();
                    break;   
            case 204: //FALSE
                    p =p.sig;
                    expresion_numerica1();
                    break;        
            default:
                    expresion_relacional();
                    expresion_logica1();
                    break;
                
         }
    }

    private void expresion_logica1() {
       switch (p.token) {
            case 202: // AND
                p = p.sig;
                expresion_logica();
                expresion_logica1();
                break;
            case 222: // OR
                p = p.sig;
                expresion_logica();
                expresion_logica1();
                break;
            default:
                
                break;
           
        }
    }

    private void expresion_relacional() {
        expresion_numerica();
        operador_relacional();
         
    }

    private void operador_relacional() {
        switch (p.token) {
            case 109: // >
                p = p.sig;
                expresion_numerica();
                break;
            case 110: // >=
                p = p.sig;
                expresion_numerica();
                break;
            case 111: // <
                p = p.sig;
                expresion_numerica();
                break;
            case 112: // <=
                p = p.sig;
                expresion_numerica();
                break;
            case 113: // =
                p = p.sig;
                expresion_numerica();
                break;
            case 114: // <>
                p = p.sig;
                expresion_numerica();
                break;
            default:
             //   p = p.sig;
                break;
           
        }
    }

    private void mientras() {
        p = p.sig;
        expresion_logica();
        if(p.token == 210){ //HACER
            Acciones();
            if(p.token == 211){ //FIN_MIENTRAS
                 p =p.sig;
                 if(p.token == 118){
                     if(p.sig.token == 214 || p.sig.token == 207 || p.sig.token == 211 || p.sig.token == 208){
                           imprimirMensajeError(518);
                        }else{
                             
                        }
                 }
            }else{
            imprimirMensajeError(514);
            }
        }else{
         imprimirMensajeError(517);
       }
    }
    

}