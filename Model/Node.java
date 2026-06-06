/************************************************************************************************
*Autor............: Gustavo Henrique Oliveira Fernandes
*Matricula........: 202410104
*Inicio...........: 29/08/2026
*Ultima alteracao.: 29/08/2026
*Nome.............: Node
*Funcao...........: representar um vertice generico contendo um valor principal e um valor
*                   auxiliar, alem de uma chave identificadora
**************************************************************************************************/


package Model;

public class Node<T, K> {
     private T valor;
     private K segundoValor;
     private int chave;
     
     public Node(T valor) {
          this.valor = valor; 
          this.segundoValor = null;
     }

     public Node<T, K> copy(T valor) {
         Node<T, K> no = new Node(valor);
         return no;
     } 
     
     /********************************************************************************************
     * Metodo: Node (construtor)
     * Funcao: inicializa o node com valor principal e valor auxiliar
     * Parametros: valor - dado principal
     *             segundoValor - dado auxiliar
     * Retorno: nenhum
     ********************************************************************************************/
     public Node(T valor, K segundoValor) {
          this.valor = valor; 
          this.segundoValor = segundoValor;
     }
     
    /********************************************************************************************
     * Metodo: setChave
     * Funcao: define a chave identificadora do node
     * Parametros: chave - valor inteiro de identificacao
     * Retorno: vazio
     ********************************************************************************************/
    protected void setChave(int chave) {
         this.chave = chave;
    }    
    
    /********************************************************************************************
     * Metodo: setChave
     * Funcao: define a chave identificadora do node
     * Parametros: chave - valor inteiro de identificacao
     * Retorno: vazio
     ********************************************************************************************/
    public K getSegundoValor() {
       return segundoValor;
    } 

    /********************************************************************************************
     * Metodo: getChave
     * Funcao: retorna a chave identificadora do node
     * Parametros: nenhum
     * Retorno: inteiro
     ********************************************************************************************/
    public int getChave() {
       return  chave;
    }
    
    /********************************************************************************************
     * Metodo: getValor
     * Funcao: retorna o valor principal armazenado no node
     * Parametros: nenhum
     * Retorno: objeto generico T
     ********************************************************************************************/
    public T getValor() {
       return valor;
    } 
}
