/************************************************************************************************
* Autor............: Gustavo Henrique Oliveira Fernandes
* Matricula........: 202410104
* Inicio...........: 12/04/2026
* Ultima alteracao.: 12/04/2026
* Nome.............: ListaIndexada
* Funcao...........: Implementar uma estrutura de dados generica que combina um vetor indexado
*                    com uma lista duplamente encadeada. Cada elemento é armazenado em uma
*                    posição específica do vetor e também encadeado em ordem crescente de índice,
*                    permitindo acesso direto e travessia sequencial eficiente.
**************************************************************************************************/
package Model;

public class ListaIndexada<T> {
      private Node[] vetor; 
      private int tamanho;
      private Node Head;
      private Node tail;
      /********************************************************************************************
      * Metodo: ListaIndexada (Construtor)
      * Funcao: Inicializar a estrutura de dados com um tamanho máximo definido, alocando o vetor
      *          que armazenará as referências para os nós da lista.
      * Parametros:
      *    tamanho (int) - Capacidade máxima da lista indexada.
      * Retorno: Nenhum
      ********************************************************************************************/
      public ListaIndexada(int tamanho) {
             this.tamanho = tamanho;
             vetor = (ListaIndexada<T>.Node[]) new ListaIndexada.Node[tamanho];
             this.Head = null;
      }//fim do construtor ListaIndexada
      
      /********************************************************************************************
      * Classe Interna: Node
      * Funcao: Representar um elemento da lista duplamente encadeada, armazenando o valor genérico,
      *          referências para o próximo e o nó anterior, além da posição associada no vetor.
      ********************************************************************************************/
      public class Node {
         private T valor;
         private Node next; private Node prev;
         private int posicao; 
        /*****************************************************************************************
         * Metodo: Node (Construtor)
         * Funcao: Inicializar um nó com o valor especificado.
         * Parametros:
         *    valor (T) - Valor a ser armazenado no nó.
         * Retorno: Nenhum
         *****************************************************************************************/
         public Node(T valor) {
             this.valor = valor;
             this.next = null;
         }//fim do construtor
          
         /*****************************************************************************************
         * Metodo: defineNext
         * Funcao: Definir o próximo nó da lista.
         * Parametros:
         *    next (Node) - Referência para o próximo nó.
         * Retorno: Nenhum
         *****************************************************************************************/
         public void defineNext(Node next) {
                this.next = next;
         }//fim do metodo defineNext
         
         /*****************************************************************************************
         * Metodo: getNext
         * Funcao: Retornar a referência para o próximo nó.
         * Parametros: Nenhum
         * Retorno:
         *    Node - Próximo nó da lista.
         *****************************************************************************************/ 
         public Node getNext() {
             return next;
         }//fim do metodo getNext
         
         public T getValor() {
              return valor;
         }
      }//fim da classe Node     
      
      /********************************************************************************************
      * Metodo: verificarSeEhNull
      * Funcao: Verificar se a posição especificada do vetor está vazia.
      * Parametros:
      *    indice (int) - indice a ser verificado.
      * Retorno:
      *    boolean - true se a posição estiver vazia; false caso contrário.
      * Excecoes:
      *    Exception - Lançada se o índice estiver fora dos limites do vetor.
      ********************************************************************************************/
      public boolean veririficarSeEhNull(int indice) throws Exception {
              if(indice >= tamanho || indice < 0) {
                 throw new Exception();
              }
              return vetor[indice] == null;
      }//fim do metodo veririficarSeEhNull
      
      /********************************************************************************************
      * Metodo: getHead
      * Funcao: Retornar o primeiro nó da lista encadeada.
      * Parametros: Nenhum
      * Retorno:
      *    Node - Referência para o primeiro nó da lista.
      ********************************************************************************************/
      public Node getHead() {
             return this.Head;
      } //fim do metodo getHead

      /********************************************************************************************
      * Metodo: inserirValor
      * Funcao: Inserir um novo valor na posição especificada do vetor e ajustar a lista
      *          duplamente encadeada para manter a ordem crescente dos índices.
      * Parametros:
      *    valor (T) - Valor a ser inserido.
      *    indice (int) - Posição no vetor onde o valor será armazenado.
      * Retorno: Nenhum
      * Excecoes:
      *    Exception - Lançada se o índice estiver fora dos limites ou se a posição já estiver ocupada.
      ********************************************************************************************/
      public void inserirValor(T valor, int indice) throws Exception {
           if(indice >= tamanho || indice < 0) {
                 throw new Exception();
           }

           Node ptr = new Node(valor);
           if(Head == null) {
              Head = ptr;
              vetor[indice] =  Head;
              Head.posicao = indice;
              tail = ptr;
              return;            
           }
           if(vetor[indice] != null) throw new Exception();
            vetor[indice] = ptr;
           if(indice < Head.posicao) {
               ptr.next = Head;
               Head.prev = ptr;
               Head = ptr;
               Head.posicao = indice;
           } else if(indice > tail.posicao) {
               tail.next = ptr;
               ptr.prev = tail;
               tail = ptr;
           } else {//partemais complicada
               Node valorNode = Head; int variavel_seguranca = 0;
               while(valor != null && !(valorNode.posicao <= indice && indice <= valorNode.next.posicao)) {
                   valorNode = valorNode.next;
                   if(variavel_seguranca > tamanho+200) break; //incerteza de engenheiro 
                   variavel_seguranca++;
               }
               ptr.next = valorNode.next;
               ptr.prev =  valorNode;
               valorNode.next = ptr;
           }
           //vetor[indice] =
      }//fim do metodo inserirValor
      
      /********************************************************************************************
      * Metodo: remover
      * Funcao: Remover o elemento armazenado na posição especificada, ajustando as referências
      *          da lista duplamente encadeada.
      * Parametros:
      *    indice (int) - indice do elemento a ser removido.
      * Retorno: Nenhum
      * Excecoes:
      *    Exception - Lançada se o índice estiver fora dos limites.
      ********************************************************************************************/
      public void remover(int indice) throws Exception {
            if(indice >= tamanho || indice < 0) {
                 throw new Exception();
            }
            
            if(vetor[indice] != null) {
               if(vetor[indice] == Head) {
                  Head = Head.next;
                  if(Head != null) {
                     Head.prev = null;
                  } else {tail = null;} 
               } else if(vetor[indice] == tail) {
                  tail = tail.prev;
                  tail.next = null;
               } else {
                  Node ptr = vetor[indice];
                  ptr.prev.next = ptr.next;
                  ptr.next.prev = ptr.prev;
                  
               }
               vetor[indice] = null;
            }
      }// fim do metodo remover
      
      /********************************************************************************************
      * Metodo: mostrarValor
      * Funcao: Retornar o valor armazenado na posição especificada do vetor.
      * Parametros:
      *    indice (int) - indice do valor a ser exibido.
      * Retorno:
      *    String - Representação textual do valor armazenado.
      ********************************************************************************************/
      public String mostrarValor(int indice) {
          return String.valueOf(vetor[indice].valor);
      }//fim do metodo mostrarValor
      
      /********************************************************************************************
      * Metodo: mostrarLista
      * Funcao: Retornar uma representação textual da lista duplamente encadeada em ordem crescente
      *          de indices.
      * Parametros: Nenhum
      * Retorno:
      *    String - String contendo todos os valores da lista.
      ********************************************************************************************/
      public String mostrarLista() {
          Node ptr = Head;
          String res = "";
          while(ptr != null) {
              res += String.valueOf(ptr.valor) + " -> ";
              ptr = ptr.next; 
          }
          return res;
      }//fim do metodo mostrarLista
}
