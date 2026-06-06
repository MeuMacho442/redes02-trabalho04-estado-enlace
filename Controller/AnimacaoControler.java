/************************************************************************************************
*Autor............: Gustavo Henrique Oliveira Fernandes
*Matricula........: 202410104
*Inicio...........: 12/04/2026
*Ultima alteracao.: 12/04/2026
*Nome.............: ControllerDeMover
*Funcao...........: Definir um meio termo de comunicaco entre a adminstracao logica e a representacao de seu estado 
                    na interce grafica ao usuario GUI
**************************************************************************************************/

package Controller;

import View.EstruturarSubRede;
import Model.Posicao;
import View.ArestaView;
import View.Janela; 

public class AnimacaoControler {
       private EstruturarSubRede subRede;

       /********************************************************************************************
       * Metodo: AnimacaoControler (construtor)
       * Funcao: concetar as classes doo pacote view a classes do pacote controller
       * Parametros: EstruturarSubRede subRede - estrutura do pacote view que permite a atualizacao do movimento
       * Retorno: nenhum
       ********************************************************************************************/
       public AnimacaoControler(EstruturarSubRede subRede) {
              this.subRede = subRede;
       } //fim do metodo AnimacaoControler
        
       /********************************************************************************************
       * Metodo:  SubRedeVisitar
       * Funcao: conectar as classes doo pacote view a classes do pacote controller
       * Parametros: EstruturarSubRede subRede - estrutura do pacote view que permite a atualizacao do movimento
       * Retorno: nenhum
       ********************************************************************************************/
       public void SubRedeVisitar(int indice) {
               subRede.marcarCinza(indice);
       }// fim do metodo SubRedeVisitar
  
       /********************************************************************************************
       * Metodo:  SubRedeRemacar
       * Funcao: macar o rotulo referenciado por uma chave "indice" com a cor vermelha
       * parametros: int indice   
       * Retorno: nenhum
       ********************************************************************************************/
       public void SubRedeRemacar(int indice) {
               subRede.marcarVermelho(indice);
       }// fim do metodo SubRedeRemacar
 
       // X = (a,b) in {(T,W)/ T $ lR, W $ lR}
       public void marcarLinhas(Posicao X, Posicao Y, String cor) {
             subRede.alocarNovaArestaView(X.getX(), X.getY(), Y.getX(), Y.getY(), cor);
       }//fim do metodo marcarLinhas
       
       /********************************************************************************************
       * Metodo: retirarLinhasMarcadas
       * Funcao: retira as linhas marcadas como verde que reppresenta a conexao entre dois nos
       * parametros: int indice   
       * Retorno: nenhum
       ********************************************************************************************/
       public void retirarLinhasMarcadas() {
            subRede.eliminarArestaView();
       }//fim do metodo retirarLinhasMarcadas
       
       /********************************************************************************************
       * Metodo: reinicializarFila
       * Funcao: Permiite que a fila de armazenamento de aresta seja reinicializada, permitindo que nao haja instancia compartilhas entre
                 diferentes estados deste programa 
       * parametros: nenhum   
       * Retorno: nenhum
       ********************************************************************************************/
       public void reinicializarFila() {
           subRede.reiniciliarFila();
       }//fim do metodo reinicializarFila
} 
