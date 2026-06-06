/************************************************************************************************
*Autor............: Gustavo Henrique Oliveira Fernandes
*Matricula........: 202410104
*Inicio...........: 29/03/2026
*Ultima alteracao.: 29/03/2026 
*Nome.............: NodeView
*Funcao...........: criar uma representação visual do no na aplicacao grafica
**************************************************************************************************/
package View;

import javafx.scene.layout.StackPane;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class NodeView<K> {
   private int altura, largura;
   private double x, y;
   private final int r = 25; //as cordenadas x, y e r representam, respectivamente, as posicoes x, y do vertice e o raio
   private K valor; //representa o valor de representação do vertice
   private StackPane vertice;
   
  /********************************************************************************************
  * Método: NodeView
  * Função: Definir o valor central do node
  * Parâmetros: k -> generics
  * Retorno: vazio  
  ********************************************************************************************/
   public NodeView(K valor) {
       this.vertice = new StackPane();
       this.valor = valor;
       this.escreverVertice();
   }//fim construtor
    
  /********************************************************************************************
  * Método: definirDimensao
  * Função: Definir a dimencao da representacao do vertice
  * Parâmetros: k -> generics
  * Retorno: vazio  
  ********************************************************************************************/
   public void definirDimensao(int altura, int largura) {
        vertice.setPrefSize(altura, largura);
   } //fim do metodo definirDimensao
  
  /********************************************************************************************
  * Método: setPosicaoVertice
  * Função: configurar o vertice na tela
  * Parâmetros: k -> generics
  * Retorno: vazio  
  ********************************************************************************************/
   public void setPosicaoVertice(double x, double y) {
        vertice.setLayoutX(x);
        vertice.setLayoutY(y);
        vertice.setStyle("-fx-background-color: #FF0000;" + "-fx-background-radius: " + r + ";");
   } //fim do metodo setPosicaoVertice
   
  /********************************************************************************************
  * Método: escreverVertice
  * Função: configurar o vertice na tela
  * Parâmetros: vazio
  * Retorno: vazio  
  ********************************************************************************************/
   private void escreverVertice() {
         Label texto = new Label(String.valueOf(valor));
         texto.setTextFill(Color.WHITE);
         texto.setFont(Font.font("BOLD", 16));
         vertice.getChildren().add(texto);
   }//fim do metodo escreverVertice

   public void mudarCor(String cor) {
         vertice.setStyle("-fx-background-color: " + cor + ";" + "-fx-background-radius: " + r + ";");
   }

   /********************************************************************************************
   * Método: getVertice
   * Função: retornar o ponteiro de referencia do componente stack pane que contem o NodeView
   * Parâmetros: vazio
   * Retorno: StackPane  
   ********************************************************************************************/
   public StackPane getVertice() {
        return vertice;
   } //fim metodo getVertice
}
