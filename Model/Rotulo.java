/************************************************************************************************
*Autor............: Gustavo Henrique Oliveira Fernandes
*Matricula........: 202410104
*Inicio...........: 12/04/2026
*Ultima alteracao.: 12/04/2026
*Nome.............: ControllerDeMover
*Funcao...........: Toma um determinado node h e o estrutura a partir de outras informacao essenciais para a analise de
                    roteamento. Nessa forma, encapsulamos h em informacao H, que contem o seu Estado = {Descoberto, Permante}, a distancia ate a origem X do 
                    grafo, e o predecessor de h, o node que o descobriu
**************************************************************************************************/

package Model;

public class Rotulo<T,K> {
         public enum RotuloVertice {DESCOBERTO, PERMANENTE};
         private RotuloVertice estado;
         private Node<T,K> Predecessor;
         private int distancia;
         private Node<T,K> vertice;
         
        /********************************************************************************************
        * Metodo: Rotulo (construtor)
        * Funcao: No algoritmo de dikjstra, cada vertice eh marcado com os estados de descoberto e o seu predecessor eh marcado como nulo. 
                  A funcao reside portanto em iniciarlizar os estados do rotulo alem de incializar uma posicao na memoria de representacao de
                  uma instancia de classe. 
        * Parametros: vertice
        * Retorno: nenhum
        ********************************************************************************************/
         public Rotulo(Node<T,K> vertice) {
              this.estado = RotuloVertice.DESCOBERTO;
              this.vertice = vertice;
              this.Predecessor = null;
         }//fim do metodo Rotulo
                  
        /********************************************************************************************
        * Metodo: setEstado
        * Funcao: definir o estado atual do rotulo como permanente 
        * Parametros: nenhum
        * Retorno: nenhum
        ********************************************************************************************/
         public void setEstado() {
                 this.estado = RotuloVertice.PERMANENTE; 
         }//fim do metodo setEstado
         
        /********************************************************************************************
        * Metodo: definirPredecessor
        * Funcao: definir o vertice que descobriu o vertice anterior 
        * Parametros: (Node<T,K>) Predecessor
        * Retorno: nenhum
        ********************************************************************************************/
        public void definirPredecessor(Node<T,K> Predecessor) {
               this.Predecessor = Predecessor;
        }//fim do metodo definirPredecessor
        
        /********************************************************************************************
        * Metodo: getPredecessor()
        * Funcao: retornar o predecessor do vertice atual 
        * Parametros: (Node<T,K>) Predecessor
        * Retorno: nenhum
        ********************************************************************************************/  
        public Node<T,K> getPredecessor() {
               return this.Predecessor;
        }//fim do metodo getPredecessor
        
        /********************************************************************************************
        * Metodo: getEstadoRotulo
        * Funcao: etornar o estado atual do vertice rotulado
        * Parametros: nenhum
        * Retorno: nenhum
        ********************************************************************************************/
        public RotuloVertice getEstadoRotulo() {
             return estado;
        } //fim do metodo RotuloVertice

           
        public Node<T,K> obterVertice() {
                  return vertice;
        }
        
        public void setDistancia(int distancia) {
               this.distancia = distancia;
        }

        public int getDistancia() {
             return distancia;
        } 
}
