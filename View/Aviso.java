/************************************************************************************************
*Autor............: Gustavo Henrique Oliveira Fernandes
*Matricula........: 202410104
*Inicio...........: 29/08/2026
*Ultima alteracao.: 29/08/2026
*Nome.............: Aviso
*Funcao...........: encapsular a exibicao de mensagens de alerta e informacao ao usuario por
*                   meio de dialogos graficos
**************************************************************************************************/

package View;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.application.Platform;

public class Aviso {
      Alert alerta;
      Alert info;
      
      /********************************************************************************************
      * Metodo: Aviso (construtor)
      * Funcao: inicializa os objetos de alerta para diferentes tipos de mensagens
      * Parametros: nenhum
      * Retorno: nenhum
      ********************************************************************************************/
      public Aviso() {
          this.alerta = new Alert(AlertType.WARNING);
          this.info = new Alert(AlertType.INFORMATION);
      }

      /********************************************************************************************
      * Metodo: mostrarAlerta
      * Funcao: exibe uma mensagem de alerta ao usuario
      * Parametros: mensagem - conteudo textual a ser exibido
      * Retorno: vazio
      ********************************************************************************************/
      public void mostrarAlerta(String mensagem) {
             alerta.setTitle(mensagem);
             alerta.setHeaderText(null);
             alerta.setContentText(mensagem);
             alerta.setResizable(false); 
             alerta.showAndWait();
      }
      
      /********************************************************************************************
      * Metodo: mostrarAviso
      * Funcao: exibe uma mensagem informativa ao usuario
      * Parametros: mensagem - conteudo textual a ser exibido
      * Retorno: vazio
      ********************************************************************************************/ 
      public void mostrarAviso(String mensagem) {
             info.setTitle("Sobre as diferentes versoes: ");
             info.setHeaderText(null);
             info.setContentText(mensagem);
             info.showAndWait();
      } //fim do metodo mostrarAviso
}