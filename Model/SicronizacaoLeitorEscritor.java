package Model;

import java.util.concurrent.Semaphore;

public class SicronizacaoLeitorEscritor {
    private static  Semaphore fila = new Semaphore(1, true);//fila -> catraca
    private static  Semaphore regicaoCritica = new Semaphore(1, true);
    private static  Semaphore livro = new Semaphore(1, true);
    private static int numeroLeitores = 0;

    private static  Semaphore baseDados;
    private static int numeroLeitoresPrioridade = 0;
    private static  Semaphore mutex;

    static {
          fila = new Semaphore(1, true);
          regicaoCritica = new Semaphore(1, true);
          livro = new Semaphore(1, true);
          mutex = new Semaphore(1);
          baseDados = new Semaphore(1);
     }

     public static void acessarBaseDadosLeitorComPrioridade() {
           try {
             mutex.acquire();
               numeroLeitoresPrioridade++;
               if(numeroLeitoresPrioridade == 1) {
                  baseDados.acquire();
               } 
             
           } catch(Exception ex) {
                ex.printStackTrace();
           } finally {
              mutex.release();
           }
     } 
     
     public static void acessarBaseDadosEscritorSemPrioridade() {
           try {
               baseDados.acquire();
           } catch(Exception ex) {
                ex.printStackTrace();
           }
     }

     public static void deixarBaseDadosEscritorSemPrioridade() {
               baseDados.release();
     } 

     public static void deixarBaseDadosLeitorComPrioridade() {
           try {
             mutex.acquire();
               if(numeroLeitoresPrioridade == 1) {
                  baseDados.release();
               } 
               numeroLeitoresPrioridade--;
           } catch(Exception ex) {
                ex.printStackTrace();
           } finally {
              mutex.release();
           }
     }
     public static void deixarBaseDadosLeitor() {
            downRc();
               numeroLeitores--;
               if(numeroLeitores == 0) {
                     upLivro();
               }
            upRc();
     }

     public static void editarBaseDadosEscritor() {
          downfila();
            downLivro();
     }


     public static void acessarRegiaoCritica() {
          try {
                mutex.acquire();
          } catch(Exception ex) {
                ex.printStackTrace();
          }
     } 
     
     public static void deixarRegiaoRegiaoCritica() {
                mutex.release();
     } 

     public static void deixarBaseDadosEscritor() {
         upLivro(); 
         upfila();
     } 

     public static void acessarBaseDadosLeitor() {
            downfila();
            upfila();
             downRc();
              numeroLeitores++;  
              if(numeroLeitores == 1) {
                 downLivro();
              }
             upRc();
     }

     private static void downRc() {
           try {
                regicaoCritica.acquire();
           } catch(Exception ex) {
                ex.printStackTrace(); 
           }       
     }

     private static void upRc() {
            regicaoCritica.release();
     }
 
     private static void upLivro() {
            livro.release();
     }

     private static void downfila() {
           try {
                fila.acquire();
           } catch(Exception ex) {
                ex.printStackTrace(); 
           }       
     }

     private static void upfila() {
             fila.release();
     }

     private static void downLivro() {
           try {
                livro.acquire();
           } catch(Exception ex) {
                ex.printStackTrace(); 
           }       
     } 
}