/************************************************************************************************
*Autor............: Gustavo Henrique Oliveira Fernandes
*Matricula........: 202410104
*Inicio...........: 29/08/2026
*Ultima alteracao.: 29/08/2026
*Nome.............: CordenarEnvio
*Funcao...........: coordenar o envio de pacotes entre roteadores, controlando animacao,
*                   contadores globais e sincronizacao de envio em ambiente concorrente
**************************************************************************************************/

package Model;

import Controller.ControllerDeMover;

import java.util.concurrent.Semaphore;


public class CordenarEnvio extends Thread {
     private ControllerDeMover controllerMover;
     private Posicao posicaoInicial; private Posicao posicaoFinal;
     private Roteador Origem; private Roteador Destino;
     private static int IDRotadorEnvio;
     private static int quantidadePacotesRede = 0;
     private Pacote pacote;  
     private int tipoPacote;
     /********************************************************************************************
     * Metodo: CordenarEnvio
     * Funcao: inicializar o controlador de envio com referencia ao controller de movimento
     * Parametros: ControllerDeMover controllerMover
     * Retorno: nenhum
     ********************************************************************************************/ 
     public CordenarEnvio(ControllerDeMover controllerMover, int tipoPacote) {
          this.controllerMover = controllerMover;
          this.tipoPacote = tipoPacote;
     }
     
     /********************************************************************************************
     * Metodo: run
     * Funcao: executar o fluxo completo de envio do pacote (controle, animacao e entrega)
     * Parametros: nenhum
     * Retorno: nenhum
     ********************************************************************************************/
     @Override
     public void run() {
          
          if(tipoPacote == 0) {//caso seja pacote de controle
            SicronizacaoLeitorEscritor.acessarBaseDadosLeitor();//caso seja um pacote de controle, entao deveremos acessar a base de dados para leitura
            SicronizacaoLeitorEscritor.acessarRegiaoCritica();
              quantidadePacotesRede++;
              if(quantidadePacotesRede == 1) {
                  controllerMover.fecharButao();
              } 
            SicronizacaoLeitorEscritor.deixarRegiaoRegiaoCritica();
              animarEnviarPacote(posicaoInicial, posicaoFinal);  
              sinalizarChegada();
            SicronizacaoLeitorEscritor.acessarRegiaoCritica();
              quantidadePacotesRede--;
              if(quantidadePacotesRede == 0) {
                  controllerMover.reabilitarButao();
              } 
            SicronizacaoLeitorEscritor.deixarRegiaoRegiaoCritica();
            SicronizacaoLeitorEscritor.deixarBaseDadosLeitor();//caso seja um pacote de controle, entao deveremos acessar a base de dados para leitura
          } else { //para caso de pacote de encaminhamento, temos de que o pacote devera ser ecaminhado sem acessar uma base de dados, o emissor representa o escritor portanto
                     animarEnviarPacote(posicaoInicial, posicaoFinal);
                     Destino.encaminhamentoMenorRota(pacote);
          }
     }
     
     /********************************************************************************************
     * Metodo: enviarPacote
     * Funcao: configurar os dados necessarios para envio do pacote entre roteadores
     * Parametros: Roteador Origem, Roteador Destino, Pacote pacote
     * Retorno: nenhum
     ********************************************************************************************/
     public void enviarPacote(Roteador Origem, Roteador Destino, Pacote pacote) {
               posicaoInicial = Origem.getPosicao();
               posicaoFinal = Destino.getPosicao();
               this.Origem = Origem; this.Destino = Destino; 
               this.pacote = pacote; 
     }
     
     
     /********************************************************************************************
     * Metodo: administrarPacotes
     * Funcao: verificar se nao ha mais pacotes pendentes e liberar interface
     * Parametros: nenhum
     * Retorno: nenhum
     ********************************************************************************************/
     private void reabilitarButao() {
                 controllerMover.reabilitarButao();   
     }
     
     
     
     /********************************************************************************************
     * Metodo: animarEnviarPacote
     * Funcao: animar o movimento do pacote entre duas posicoes usando interpolacao
     * Parametros: Posicao p0, Posicao p
     * Retorno: nenhum
     ********************************************************************************************/
     private void animarEnviarPacote(Posicao p0, Posicao p) {
            
            funcaoMovimentoParametrico f = t -> { 
               if(p0 == null || p == null) {
                  System.out.println("Esta nulo");
               } 
               double x0 = Projecao(0,p0); double x = Projecao(0,p);
               double y0 = Projecao(1,p0); double y = Projecao(1,p);
               return new Posicao(x0 + t*(x-x0), y0 + t*(y-y0));
            };
            controllerMover.Mover(f);
     }    
      
     /********************************************************************************************
     * Metodo: sinalizarChegada
     * Funcao: informar ao roteador destino que o pacote chegou
     * Parametros: nenhum
     * Retorno: nenhum
     ********************************************************************************************/
     private void sinalizarChegada() {
            Destino.sinalizarEventoDeChegada(pacote);
     }
     
     /********************************************************************************************
     * Metodo: setIDEnvio
     * Funcao: definir identificador de envio global
     * Parametros: int x
     * Retorno: nenhum
     ********************************************************************************************/
     public static void setIDEnvio(int x) {
            IDRotadorEnvio = x;
     }
     
     /********************************************************************************************
     * Metodo: Projecao
     * Funcao: obter coordenada X ou Y de uma posicao
     * Parametros: int tipo, Posicao p
     * Retorno: double
     ********************************************************************************************/
     public double Projecao(int tipo, Posicao p) {
           
           if(tipo == 0) {
               return p.getX();
           }
               return p.getY();
     }

     /*****************************************************************************************
      * metodo: emitirAviso
      * funcao: avisaar de que o caminho entre dois nodes da rede nao foi encontrado
      * parametros: vazio
      * retorno: vazio
      *****************************************************************************************/  
      private void emitirAviso() {
            
      }
 
     /********************************************************************************************
     * Metodo: DownMutex
     * Funcao: adquirir semaforo
     * Parametros: Semaphore semaphore
     * Retorno: nenhum
     ********************************************************************************************/
     private void DownMutex(Semaphore semaphore) {
         try {
               semaphore.acquire();
         } catch(Exception ex) {
               ex.printStackTrace();
         }
     }
     
     /********************************************************************************************
     * Metodo: UpMutex
     * Funcao: liberar semaforo
     * Parametros: Semaphore semaphore
     * Retorno: nenhum
     ********************************************************************************************/
     private void UpMutex(Semaphore semaphore) {
         try {
              semaphore.release();
         } catch(Exception ex) {
               ex.printStackTrace();
         }
     }
}
