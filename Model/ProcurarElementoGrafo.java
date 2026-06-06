/************************************************************************************************
*Autor............: Gustavo Henrique Oliveira Fernandes
*Matricula........: 202410104
*Inicio...........: 29/08/2026
*Ultima alteracao.: 29/08/2026
*Nome.............: ProcurarElementoGrafo
*Funcao...........: procurar um elemento no grafo conforme uma chave de parametro de passagem
**************************************************************************************************/

package Model;



public class ProcurarElementoGrafo {
    private Node<Roteador, Posicao>[] listNode;
    
    /********************************************************************************************
    * Metodo: ProcurarElementoGrafo
    * Funcao: receber a lista de nos que representa o conjunto de vertices do grafo 
    * Parametros: Node<Roteador, Posicao>[] listNode - arranjo de Node<K,T>, para k = Roteador, T = Posicao
    * Retorno: nenhum
    ********************************************************************************************/
    public ProcurarElementoGrafo(Node<Roteador, Posicao>[] listNode) {
            this.listNode = listNode; 
    }//fim do metodo ProcurarElementoGrafo
    
     /********************************************************************************************
    * Metodo: procurarNode
    * Funcao: reebeu um valor para se existe um vertice com valor de identificacao equivalente ao valor que foi repassado 
    * Parametros: Node<Roteador, Posicao>[] listNode - arranjo de Node<K,T>, para k = Roteador, T = Posicao
    * Retorno: nenhum
    ********************************************************************************************/ 
    public boolean procurarNode(int valor) {
          for(int i = 0; i < listNode.length && listNode[i] != null; i++) {
               int k = (listNode[i].getValor()).getId_identificao();
               if(k == valor) {
                     return true;       
               } 
          }
          return false;
    } //fim do metodo procurarNode
}