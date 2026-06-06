/************************************************************************************************
*Autor............: Gustavo Henrique Oliveira Fernandes
*Matricula........: 202410104
*Inicio...........: 29/08/2026
*Ultima alteracao.: 29/08/2026
*Nome.............: AnimarDijkstra
*Funcao...........: realizar um registro do conjunto de passos aplicado durante o procedimento do calculo do caminho minimo aplicado em Dikjstra
**************************************************************************************************/


package Model;

import Model.ListaDupla;
import Model.Grafo;
import Model.Node;
import Model.Roteador;
import Controller.AnimacaoControler;
import Model.Rotulo;
import Model.ListaIndexada;
import Model.Aresta;

//2 -> 1 -> 3 -> 5 -> 6 -> 

public class AnimarDijkstra {
       private ListaDupla<Node<Roteador, Posicao>> conjuntoPassos;
       private ListaIndexada<Node<Roteador, Posicao>> conjuntoPassosVerticesNaoMarcados;
       private ListaIndexada<Node<Roteador, Posicao>> conjuntoCaminho;
       private ListaDupla<Node<Roteador, Posicao>> listaCaminhos;
       private Rotulo verticeFinal;
       private AnimacaoControler ponteiro;   
       private Grafo grafo;
       
      /********************************************************************************************
      * Metodo: AnimarDijkstra (construtor)
      * Funcao: aplicar uma inicializacao de memoria indicada conjuntamente com a estrutura de dados lista Dupla e o grafo
      * Parametros: grafo
      * Retorno: nenhum
      ********************************************************************************************/
       public AnimarDijkstra(Grafo grafo) {
             this.conjuntoPassos = new ListaDupla<>();
             this.grafo = grafo;
       } //fim do construtor AnimarDijkstra
       
      /********************************************************************************************
      * Metodo: registrarPassos 
      * Funcao: inserir um No no resgistro de passos aplicado no roteamento no caminho
      * Parametros: r -> no a ser inserido na lista de passos
      * Retorno: nenhum
      ********************************************************************************************/ 
       public void registrarPassos(Node<Roteador, Posicao> r) {
              conjuntoPassos.inserirFim(r);
       }//fim do metodo registrarPassos
     
      /********************************************************************************************
      * Metodo: registrarVerticeFinal 
      * Funcao: indicar qual foi o ultimo vertice registrado pelo conjunto de passos indicado
      * Parametros: r -> no a ser inserido na lista de passos
      * Retorno: nenhum
      ********************************************************************************************/
       public void registrarVerticeFinal(Rotulo verticeFinal) {
           this.verticeFinal = verticeFinal;
       } //fim do metodo registrarVerticeFinal
        
      /********************************************************************************************
      * Metodo: animarDikstra 
      * Funcao: essa etrutura funciona me loop. Seja o conjunto indicado como 1 -> 2 -> 5 -> NULL, entao
                marcamos o vertice 1, visitamos suas arestas adjacentes, visitamos todos os vertices e assim sucessimante
      * Parametros: vazio
      * Retorno: nenhum
      ********************************************************************************************/
       public void animarDikstra() {
         try {
            
            ListaDupla.Element ptr = conjuntoPassos.getHead();
            registrarGrafo();
            marcarEstadoPermanente((Node<Roteador, Posicao>)ptr.getValor());
            animarLinhasAdjacentes((Node<Roteador, Posicao>)ptr.getValor());
            conjuntoPassosVerticesNaoMarcados.remover(((Node<Roteador, Posicao>)ptr.getValor()).getChave());
            aguardarTempo(20); 
            visitarTodosOsPontos();
            aguardarTempo(40);
            
            for(ptr = ptr.getNext(); ptr != null; ptr = ptr.getNext()) { 
                   marcarEstadoPermanente((Node<Roteador, Posicao>)ptr.getValor());
                   conjuntoPassosVerticesNaoMarcados.remover(((Node<Roteador, Posicao>)ptr.getValor()).getChave());
                   animarLinhasAdjacentes((Node<Roteador, Posicao>)ptr.getValor());
                   aguardarTempo(20);
                   visitarTodosOsPontos(); 
                   aguardarTempo(40); 
            }
             
             remarcarVertices();
             criarCaminho();
             ponteiro.reinicializarFila();
          } catch(Exception ex) {
            ex.printStackTrace();
          } 
       }//fim do metodo animarDikstra 
       
       /********************************************************************************************
      * Metodo: criarCaminho 
      * Funcao: registrar o caminho estabelecido entre os nodes r e t 
      * Parametros: vazio
      * Retorno: nenhum
      ********************************************************************************************/
       private void criarCaminho() {
           for(ListaDupla.Element ptr = listaCaminhos.getHead(); ptr.getNext() != null; ptr = ptr.getNext()) {
                 ponteiro.marcarLinhas(((Node<Roteador, Posicao>)ptr.getValor()).getSegundoValor(), ((Node<Roteador, Posicao>)ptr.getNext().getValor()).getSegundoValor(), "#008000");
           } 
       }//fim do metodo criarCaminho
       

       /********************************************************************************************
      * Metodo: remarcarVertices 
      * Funcao: para todo vertice em que nao pertence ao conjunto de caminho minimos, sera remarcado com a cor vermelha, assim
                x eh azul se, e somente se, x nao esta no caminho. Se x nao esta no caminho, entao se x fora marcado como permanente, entao sera remarcado como vermelho. E eh verdade que x eh vermelho se, e somente se, x nao esta no caminho. 
      * Parametros: vazio
      * Retorno: nenhum
      ********************************************************************************************/
       private void remarcarVertices() {
          Node<Roteador, Posicao>[] lista = grafo.getConjuntoVertice();
          
          try {
            for(int i = 0; i < lista.length && i < grafo.getTamanho() && lista[i] != null; i++) {
              if(conjuntoCaminho.veririficarSeEhNull(lista[i].getChave())) {
                 remarcarEstado(lista[i]);
               }   
            }
          } catch(Exception ex) {
            ex.printStackTrace();
          }   
       }//fim do metodo remarcarVertices
       

      /********************************************************************************************
      * Metodo: aguardarTempo 
      * Funcao: simlamos o intervalo de tempo entre operacoes usando espera ocupada conjuntamente com mecanismo de controle de acesso desse processo ao estado de execucao
      * Parametros: int tempo, um inteiro para controlar o tempo medio entre a execucao de transicao entre eventos. Sejam esses evento, marcarVertice, remarcarVertice, visitarLinhasAdjacentes
      * Retorno: nenhum
      ********************************************************************************************/
       private void aguardarTempo(int tempo) {
           while(tempo > 0) {
              tempo--;
              controlarAnimacao();
           }
       } //fim do metodo aguardarTempo
       
       /********************************************************************************************
      * Metodo: registrarGrafo 
      * Funcao: realizar o registro do grafo a partir de uma estrutura de dados chamada de listaDupla indexada, dado um conjunto
                1,2,3,5,6, temos que esse conjunto seria representado como NULL <- 1 <-> 2 <-> 3 <-> 5 <-> 6 -> NULL
                                                                                   0     1     2     3     4
                Nesse caso, temos um acesso constante, e remocao constante tambem com trocas constante, sendo ordenavel sem custo adcional
                Sendo relativamente simples para ordenarmos e aplicarmos o algoritimo de busca binaria. 
                Assim, acesso   O(1)
                       percurso O(k), k o numero de elementos e nao o tamanho do vetor
                       remocao  O(1)
                       insercao O(k)  
      * Parametros: nenhum
      * Retorno: nenhum
      ********************************************************************************************/ 
       public void registrarGrafo() {
              Node<Roteador, Posicao>[] lista  = grafo.getConjuntoVertice();
              conjuntoPassosVerticesNaoMarcados = new ListaIndexada(grafo.getTamanho());
              try {
                for(int i = 0; i < lista.length && i < grafo.getTamanho() && lista[i] != null; i++) {
                    conjuntoPassosVerticesNaoMarcados.inserirValor(lista[i], lista[i].getChave());
                }
              } catch(Exception ex) {
                ex.printStackTrace();
              }
       } //fim do metodo registrarGrafo
       

      /********************************************************************************************
      * Metodo: passar
      * Funcao: Transfere todos os elementos de uma ListaDupla (X) para uma ListaIndexada (Y),
      *         preservando a associação entre o vértice e sua chave identificadora. Esse método
      *         é útil para converter estruturas de dados durante etapas intermediárias do
      *         algoritmo de roteamento ou animação.
      *
      * Parametros:
      *   - Y: ListaIndexada que receberá os elementos.
      *   - X: ListaDupla contendo os elementos a serem transferidos.
      *
      * Retorno: nenhum
     ********************************************************************************************/
       private void passar(ListaIndexada<Node<Roteador, Posicao>> Y,  ListaDupla<Node<Roteador, Posicao>> X) {
           try {
              for(ListaDupla.Element ptr = X.getHead(); ptr != null; ptr = ptr.getNext()) {
                   Y.inserirValor((Node<Roteador, Posicao>)ptr.getValor(), ((Node<Roteador, Posicao>)ptr.getValor()).getValor().getId_identificao());
              } 
           } catch (Exception ex) {
              ex.printStackTrace(); 
           }
       }//fim do metodo passar
 
       /********************************************************************************************
       * Metodo: visitarTodosOsPontos
       * Funcao: Percorre todos os vértices ainda não marcados no conjunto
       *         conjuntoPassosVerticesNaoMarcados, alterando seu estado visual para permanente
       *         (ex.: cor cinza) e posteriormente restaurando o estado original. Esse processo
       *         é utilizado para fins de animação e visualização do algoritmo.
       *
       * Parametros: nenhum
       * Retorno: nenhum
       ********************************************************************************************/
       private void visitarTodosOsPontos() {
              for(ListaIndexada.Node ptr = conjuntoPassosVerticesNaoMarcados.getHead(); ptr != null; ptr = ptr.getNext()) {
                   marcarEstadoPermanente((Node<Roteador, Posicao>)ptr.getValor()); //marca todos os rotulos como cinza
                   aguardarTempo(10);
                   remarcarEstado((Node<Roteador, Posicao>)ptr.getValor());
                   aguardarTempo(10);
              }
       }//fim do metodo visitarTodosOsPontos
       
       /********************************************************************************************
       * Metodo: remarcarEstado
       * Funcao: Restaura o estado visual original de um vértice após a animação de visita,
       *         permitindo a continuidade da simulação.
       *
       * Parametros:
       *   - ptr: Nó do grafo cujo estado visual será restaurado.
       *
       * Retorno: nenhum
       ********************************************************************************************/
       private void marcarEstadoPermanente(Node<Roteador, Posicao> ptr) {
               ponteiro.SubRedeVisitar(ptr.getValor().getId_identificao());
       }//fim do metodo marcarEstadoPermanente

       /********************************************************************************************
       * Metodo: animarLinhasAdjacentes
       * Funcao: Realiza a animação das arestas adjacentes a um determinado vértice, destacando
       *         visualmente as conexões com vértices ainda não visitados. Essa funcionalidade é
       *          essencial para a representação gráfica do processo de exploração do grafo.
       *
       * Parametros:
       *   - ptr: Nó do grafo cujas arestas adjacentes serão animadas.
       *
       * Retorno: nenhum
       ********************************************************************************************/
       private void remarcarEstado(Node<Roteador, Posicao> ptr) {
               ponteiro.SubRedeRemacar(ptr.getValor().getId_identificao());
       }//fim dometodo remarcarEstado


       /********************************************************************************************
        * Metodo: setPonteiro
        * Funcao: Define o controlador responsável pela animação gráfica do grafo.
        *
        * Parametros:
        *   - ponteiro: Instância de AnimacaoControler utilizada para manipular os elementos visuais.
        *
        * Retorno: nenhum
        ********************************************************************************************/
       private void animarLinhasAdjacentes(Node<Roteador, Posicao> ptr) {
          try {
               for(ListaDupla.Element ptx = grafo.getAdjacencia(ptr.getChave()).getHead(); ptx != null; ptx = ptx.getNext()) {
                  if(!conjuntoPassosVerticesNaoMarcados.veririficarSeEhNull(((Aresta<Roteador, Posicao>)ptx.getValor()).getNode().getChave())) {
                      ponteiro.marcarLinhas(ptr.getSegundoValor(), ((Aresta<Roteador, Posicao>)ptx.getValor()).getNode().getSegundoValor(), "#008000");
                      aguardarTempo(20);
                      ponteiro.retirarLinhasMarcadas();
                      aguardarTempo(20);
                  }   
               }
          } catch(Exception ex) {
              ex.printStackTrace();
          }
       }//fim do metodo animarLinhasAdjacentes

       
       /********************************************************************************************
       * Metodo: setPonteiro
       * Funcao: Define o controlador responsável pela animação gráfica do grafo.
       *
       * Parametros:
       *   - ponteiro: Instância de AnimacaoControler utilizada para manipular os elementos visuais.
       *
       * Retorno: nenhum
       ********************************************************************************************/
       public void setPonteiro(AnimacaoControler ponteiro) {
           this.ponteiro = ponteiro;
       }//fim do metodo setPonteiro
       

       /********************************************************************************************
       * Metodo: controlarAnimacao
       * Funcao: Introduz uma pausa na execução da animação, permitindo a visualização gradual
       *         das etapas do algoritmo. Utiliza Thread.sleep para controlar o tempo de espera.
       *
       * Parametros: nenhum
       * Retorno: nenhum
       ********************************************************************************************/
       private void controlarAnimacao() {
           try {
             Thread.sleep(20);     
           } catch(Exception ex) {
              ex.printStackTrace();
           }
       }//fim do metodo controlarAnimacao
    
       /********************************************************************************************
        * Metodo: estruturarCaminho
        * Funcao: Reconstrói o caminho mínimo a partir de um rótulo final (Y), seguindo a cadeia
        *         de predecessores até o vértice de origem. O caminho é armazenado em duas
        *         estruturas:
        *           - conjuntoCaminho: acesso indexado aos vértices do caminho.
        *           - listaCaminhos: representação sequencial para fácil percurso e visualização.
        *
        * Parametros:
        *   - Y: Rótulo final contendo o vértice de destino e seu predecessor.
        *   - conjuntoVerticesRotulados: Vetor com todos os rótulos gerados pelo algoritmo.
        *
        * Retorno: nenhum
        ********************************************************************************************/
       public void estruturarCaminho(Rotulo Y, Rotulo[] conjuntoVerticesRotulados) {
           conjuntoCaminho = new ListaIndexada<>(grafo.getTamanho());
           listaCaminhos = new ListaDupla<>();
           try { 
             conjuntoCaminho.inserirValor(Y.obterVertice(), Y.obterVertice().getChave());
             listaCaminhos.inserirInicio(Y.obterVertice());
             while(Y.getPredecessor() != null) {    
                    Y = conjuntoVerticesRotulados[Y.getPredecessor().getChave()];
                    conjuntoCaminho.inserirValor(Y.obterVertice(), Y.obterVertice().getChave());
                    listaCaminhos.inserirInicio(Y.obterVertice());
             } 
             conjuntoCaminho.mostrarLista();
           } catch(Exception ex) {
              ex.printStackTrace();
           }
      }//fim do metodo estruturarCaminho
}
