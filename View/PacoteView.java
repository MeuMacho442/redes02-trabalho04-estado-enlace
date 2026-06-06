/************************************************************************************************
*Autor............: Gustavo Henrique Oliveira Fernandes
*Matricula........: 202410104
*Inicio...........: 29/08/2026
*Ultima alteracao.: 29/08/2026
*Nome.............: PacoteView
*Funcao...........: representar visualmente um pacote na rede, permitindo sua exibicao e
*                   movimentacao no painel grafico durante a simulacao
**************************************************************************************************/

package View;

import javafx.scene.layout.Pane;
import javafx.application.Platform;
import View.Janela;

public class PacoteView {
       private Pane Ponto;
       private final int r = 25;
       private double x, y;
       private double largura = 15;
       private double altura = 15;
       private Janela janela;
       
       /********************************************************************************************
       * Metodo: PacoteView (construtor)
       * Funcao: inicializa o componente visual que representa o pacote e o adiciona ao painel
       * Parametros: janela - referencia da janela principal
       * Retorno: nenhum
       ********************************************************************************************/
       public PacoteView(Janela janela) {
            this.Ponto = new Pane();
            this.janela = janela;
            this.estruturarDesenho(); 
       } //fim do metodo PacoteView

       /********************************************************************************************
       * Metodo: estruturarDesenho
       * Funcao: define propriedades visuais do pacote e o adiciona ao painel direito
       * Parametros: nenhum
       * Retorno: vazio
       ********************************************************************************************/
       private void estruturarDesenho() {
            Ponto.setPrefSize(largura, altura);
            Ponto.setStyle("-fx-background-color: #0000FF;" + "-fx-background-radius: " + r + ";");
          Platform.runLater(() -> {
            janela.adicionarNoPainelDireito(Ponto);
           });
       } //fim do metodo estruturarDesenho
       
       /********************************************************************************************
       * Metodo: removerNodePonto
       * Funcao: remove o pacote do painel grafico
       * Parametros: nenhum
       * Retorno: vazio
       ********************************************************************************************/
       public void removerNodePonto() {
          Platform.runLater(() -> {
             janela.retirarNoPainelDireito(Ponto);
          });
       } //fim do metodo removerNodePonto

       /********************************************************************************************
       * Metodo: escreverPacoteView
       * Funcao: atualiza a posicao do pacote na tela com base em coordenadas relativas
       *         ao centro do painel
       * Parametros: x - deslocamento horizontal
       *             y - deslocamento vertical
       * Retorno: vazio
       ********************************************************************************************/  
       public void escreverPacoteView(double x, double y) {
          final double xn = janela.getLargurarPanelDireito()/2+x-largura/2;;
          final double yn = janela.getAlturaPanelDireito()/2-y-altura/2;
          Platform.runLater(() -> { 
             Ponto.setLayoutX(xn); Ponto.setLayoutY(yn);
          });
       } //fim do metodo escreverPacoteView

       /********************************************************************************************
       * Metodo: getPonto
       * Funcao: retorna o componente visual que representa o pacote
       * Parametros: nenhum
       * Retorno: objeto Pane
       ********************************************************************************************/
       public Pane getPonto() {
            return this.Ponto;
       }//fim do metodo getPonto
}
