/************************************************************************************************
* Autor............: Gustavo Henrique Oliveira Fernandes
* Matricula........: 202410104
* Inicio...........: 12/04/2026 (entre 22 a 26)
* Ultima alteracao.: 12/04/2026 (entre 22 a 30)
* Nome.............: LerArquivo
* Funcao...........: Realiza uma leitura sobre o arquivo que estabelece o backbone
**************************************************************************************************/


package Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class LerArquivo {
      private String caminho;
      private File arquivo;
       
    /********************************************************************************************
    * Metodo:  LerArquivo (construtor)
    * Funcao: inicializa o caminho para o arquivo referenciado
    * Parametros: caminho - referencia o caminho do arquivo
    * Retorno: nenhum
    ********************************************************************************************/
      public LerArquivo(String caminho) {
          this.caminho = caminho;
          this.arquivo = new File(caminho);
      } //fim do metodo LerArquivo
      
     /********************************************************************************************
     * Metodo:  LerArquivo (construtor)
     * Funcao: ler o arquivo em funcao da linha referenciada
     * Parametros: linha
     * Retorno: String
     ********************************************************************************************/
      public String lerArquivo(int linha) {
           String res = "";
           try {
               int acc = 0;
               Scanner sc = new Scanner(arquivo);
               while(sc.hasNextLine()) {
                   if(acc == linha) {
                       res = sc.nextLine();
                       break;
                   }
                   sc.nextLine();
                   acc++;
               }
               sc.close();
           } catch(FileNotFoundException ex) {
                System.out.println("arquivo nao encontrado");
           }
           return res; 
      } //fim do metodo lerArquivo
      
      /********************************************************************************************
      * Metodo:  LerTodoArquivo (construtor)
      * Funcao: ler o arquivo interamente composto no arquivo.txt apontado
      * Parametros: vazio
      * Retorno: String
      ********************************************************************************************/
      public String lerTodoArquivo() {
           String res = "";
           try {
               int acc = 0;
               Scanner sc = new Scanner(arquivo);
               while(sc.hasNextLine()) {
                   if(acc == 0) {
                         res += sc.nextLine(); acc = 1;
                   } else {
                         res += "\n" + sc.nextLine();
                   }
                   
               }
               sc.close();
           } catch(FileNotFoundException ex) {
                System.out.println("arquivo nao encontrado");
           }
           return res;
      }//fim do metodo lerTodoArquivo
      
      /********************************************************************************************
      * Metodo: extrairNumeroDocumentoSubrede
      * Funcao: extrai um inteiro em funcao de uma linha e dada uma coluna. Seja o arquivo 2;
                                                                                           1;2;4
        podemos entender num modelo logico como: {[2]}
                                                 {[1], [2], [4]}
        Entao a funcao W(1,2) = [4], W(0,0) = [2], W(1,1) = [2], W(1,0) = [1]     
      * Parametros: vazio
      * Retorno: String
      ********************************************************************************************/
      public Integer extrairNumeroDocumentoSubrede(int linha, int coluna) {//funciona somente para o documento backbone.txt
           String res = lerArquivo(linha);         
           return Integer.parseInt(res.split(";")[coluna].split(" ")[0]);
      }//fim do metodo extrairNumeroDocumentoSubrede
      
      /********************************************************************************************
      * Metodo: tamanhoArquivo
      * Funcao: retornar uma linha do arquivo.txt     
      * Parametros: vazio
      * Retorno: Integer
      ********************************************************************************************/
      public Integer tamanhoArquivo() {
         int tamanho = 0;
         try {
            Scanner sc = new Scanner(arquivo);
            while(sc.hasNextLine()) {
                if(sc.nextLine() == "") {
                   break;
                }
                tamanho++;
            }
            sc.close();
         } catch(FileNotFoundException ex) {
            System.out.println("arquivo nao encontrado");
         }
         return tamanho;
      }//fim do metodo tamanhoArquivo
}//fm da classe LerArquivo
