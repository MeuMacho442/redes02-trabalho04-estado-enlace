/************************************************************************************************
*Autor............: Gustavo Henrique Oliveira Fernandes
*Matricula........: 202410104
*Inicio...........: 29/08/2026
*Ultima alteracao.: 29/08/2026
*Nome.............: ControllerDeMover
*Funcao...........: atuar como intermediador entre a logica de movimentacao e a interface
*                   grafica, delegando a execucao da animacao e atualizacao de elementos visuais
**************************************************************************************************/

package Controller;

import View.Janela;
import Model.funcaoMovimentoParametrico;
import View.Animacao;
import Model.Posicao;
import View.EstruturarSubRede;

public class ControllerDeMover {

       private Janela janela;
       private Animacao anima;
       private EstruturarSubRede SubRede;

       /********************************************************************************************
       * Metodo: ControllerDeMover (construtor)
       * Funcao: inicializa o controlador de movimentacao com referencia da janela e do modulo
       *         de animacao
       * Parametros: janela - referencia da interface principal
       * Retorno: nenhum
       ********************************************************************************************/
       public ControllerDeMover(Janela janela) {
              this.janela = janela;
              this.anima = new Animacao(janela);
              this.SubRede = janela.getSubRede(); 
       } 
       
       public void atualizarPeso(Posicao A, Posicao B, int sentido, int valor) {
              SubRede.alterarViewLabel(A.getX(), A.getY(), B.getX(), B.getY(), sentido, valor);
       }
       
       /********************************************************************************************
       * Metodo: Mover
       * Funcao: delega a execucao da animacao de um ponto com base em uma funcao parametrica
       * Parametros: f - funcao que define o movimento
       * Retorno: vazio
       ********************************************************************************************/
       public void Mover(funcaoMovimentoParametrico f) {
             anima.AnimarPonto(f);
       } //fim do metodo Move

       /********************************************************************************************
       * Metodo: atualizarContador
       * Funcao: atualiza o contador de pacotes exibido na interface
       * Parametros: contador - quantidade atual de pacotes
       * Retorno: vazio
       ********************************************************************************************/
       public void atualizarMensagem(String caminho) {
            janela.editarTexto(caminho);
       }//fim do metodo atualizarContador
       
       /********************************************************************************************
       * Metodo: reabilitarButao
       * Funcao: reativa o botao principal da interface apos execucao
       * Parametros: nenhum
       * Retorno: vazio
       ********************************************************************************************/
       public void reabilitarButao() {
           janela.rabilitarButao();
       }//fim do metodo reabilitarButao
       
       public void fecharButao() {
           janela.fecharButao();
       }//fim do metodo reabilitarButao 

      /*****************************************************************************************
      * metodo: emitirAviso
      * funcao: avisaar de que o caminho entre dois nodes da rede nao foi encontrado
      * parametros: vazio
      * retorno: vazio
      *****************************************************************************************/  
      public void emitirAviso() {
              janela.emitirAviso();
      }

       public void atualizarTabela(String mensagem, int indice) {
             janela.adicionarInformacaoPainel(indice, mensagem);
       }
       
       public void desenharLinhas(Posicao X, Posicao Y, String cor) {
         double varCor = 0;
           while(varCor < 1) {
              SubRede.alocarNovaArestaView(X.getX(), X.getY(), Y.getX(), Y.getY(), cor, varCor);
              varCor += 0.01;
              try {
                   Thread.sleep(16);
              } catch(Exception ex) {
                 ex.printStackTrace();
              }
           }  
       }  
}
