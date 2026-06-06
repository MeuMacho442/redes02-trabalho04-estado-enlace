/************************************************************************************************
* Autor............: Gustavo Henrique Oliveira Fernandes
* Matricula........: 202410104
* Inicio...........: 12/04/2026 (entre 22 a 26)
* Ultima alteracao.: 12/04/2026 (entre 22 a 30)
* Nome.............: EscreverArquivo
* Funcao...........: estaelce uma escrita sobre o arquivo backbone.txt
**************************************************************************************************/

package Model;

import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;

public class EscreverArquivo {
      private String caminho;
      private BufferedWriter writer;
       
      /********************************************************************************************
      * Metodo: EscreverArquivo (construtor)
      * Funcao: inicializar o local de memoria para qualquer instancia pertencente a essa classe e definir a oreintacao do caminho de endereco do arquivo
      * Parametros: janela - referencia da interface principal
      * Retorno: nenhum
      ********************************************************************************************/ 
      public EscreverArquivo(String caminho) {
         this.caminho = caminho;
      }//fim do metodo EscreverArquivo

      /********************************************************************************************
      * Metodo: EscreverArquivo 
      * Funcao: escrever um conteudo em String sobreo camainho referenciado pelo endereco 
      * Parametros: conteudo
      * Retorno: nenhum
      ********************************************************************************************/
      public void EscreverArquivo(String conteudo) {
             try {
                 writer = new BufferedWriter(new FileWriter(caminho, false));
                 writer.write(conteudo);
                 writer.newLine();
             } catch(IOException ex) {
                 ex.printStackTrace();
             } finally {
                if (writer != null) {
                   try {
                      writer.close(); // Libera o recurso
                   } catch (IOException e) {
                     e.printStackTrace();
                   }
                }
             }
    }// fim do metodo EscreverArquivo     
      
}//fim da classe EscreverArquivo