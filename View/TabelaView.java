/************************************************************************************************
*Autor............: Gustavo Henrique Oliveira Fernandes
*Matricula........: 202410104
*Inicio...........: 29/05/2026
*Ultima alteracao.: 29/05/2026
*Nome.............: TabelaView
*Funcao...........: exibe os dados do preenchimento mais recente da tabela, 
                    com coluna que exibe a entrada com a saida conjuntamente com o custo do enlace
**************************************************************************************************/

package View;

import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.control.TextArea;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.control.cell.PropertyValueFactory;

public class TabelaView {
      private Pane painelTexto;
      private double largura;
      private double altura;
      private TextArea TextArea;
      private Label labelTexto;
      private String mensagemArea = "";	
      

    /********************************************************************************************
     * Metodo: TabelaView (construtor)
     * Funcao: iniciazliza um compontente em que o componente referente a textArea eh alocado
     * Parametros: largura, altura
     * Retorno: nenhum
     ********************************************************************************************/
     public TabelaView(double largura, double altura) {
           this.painelTexto = new Pane();
           this.largura = largura;
           this.altura = altura;
           this.TextArea = new TextArea();
           configurarCampoPainel();
           configurarTexto();
      }//fim do construtor TabelaView
      
     /********************************************************************************************
     * Metodo: configurarCampoPainel
     * Funcao: configurar Campo do Painel, definindo a cor de fundo e a estetica de borda
     * Parametros: vazio
     * Retorno: nenhum
     ********************************************************************************************/  
      private void configurarCampoPainel() {
            painelTexto.setStyle("-fx-background-color: #000000;" + "-fx-background-radius: 10;");
            double larguraInterna = 150; double alturaInterna = 160;
            painelTexto.setLayoutY(120);
            painelTexto.setLayoutX((largura-larguraInterna)/2);
            painelTexto.setPrefSize(larguraInterna, alturaInterna);
      }//fim do metodo configurarCampoPainel
      
      /********************************************************************************************
      * Metodo: configurarTexto
      * Funcao: configurar Campo do texto em que aparece as informações referentes a tabela de roteamento
      * Parametros: vazio
      * Retorno: nenhum
      ********************************************************************************************/ 
      private void configurarTexto() {
            TextArea = new TextArea();
            TextArea.setWrapText(true);
            TextArea.setEditable(false);
            double larguraInterna = 150; double alturaInterna = 160;
            TextArea.setStyle("-fx-text-fill: green;" + "-fx-background-color: #000000;" + "-fx-control-inner-background: black;");
            TextArea.setPrefSize(larguraInterna,alturaInterna);
            TextArea.setFont(Font.font("Arial", FontWeight.BOLD, 10));	
            
            painelTexto.getChildren().add(TextArea);
      }//fim do metodo configurarTexto

       
      /********************************************************************************************
      * Metodo: configurarTexto
      * Funcao: para cada infomormacao atualizada, essa funcao exibe-a na tela 
      * Parametros: vazio
      * Retorno: nenhum
      ********************************************************************************************/
      public void setText(String mensagem) {
           mensagemArea = mensagem;
           TextArea.setText(mensagem);
      }//fim do metodo setText
      
      /********************************************************************************************
      * Metodo: getMensagem
      * Funcao: retonar mensagemArea  
      * Parametros: vazio
      * Retorno: nenhum
      ********************************************************************************************/
      public String getMensagem() {
          return mensagemArea;
      }//fim do metodo getMensagem

      /********************************************************************************************
      * Metodo: getPainelTabela
      * Funcao: retonar getPainelTabela  
      * Parametros: vazio
      * Retorno: nenhum
      ********************************************************************************************/
      public Pane getPainelTabela() {
             return painelTexto;
      }//fim do metodo getPainelTabela
}
