package Model;

public class ArvoreCaminhoMinimo {
      private Grafo<Integer, Integer> Arvore;
      private Node<Integer, Integer> raiz;
      private DijkstraRoteamento dijkstra;
      private HashDinamico<Node<Integer, Integer>> hashGrafo;//associa cada valor do node do grafo com sua chave de acesso, assim, seja o no v em V, tal que valor(v) = 23, e chave(v) = 2, entao hash(23) = 2
      Rotulo<Integer, Integer>[] conjuntoVerticesRotulados;
      public class saidaCusto {
          public Integer no; public int custo;
          public saidaCusto(Integer no, int custo) {
               this.no = no;
               this.custo = custo;
          }
      }
      public ArvoreCaminhoMinimo(Node<Integer, Integer> raiz) {
             this.raiz = raiz;
             this.hashGrafo = new HashDinamico<>();
      }
 
      public void gerarArvoredeEscoamento(Grafo<Integer, Integer> Grafo) {
           conjuntoVerticesRotulados = new Rotulo[Grafo.getTamanho()];
           Arvore = new Grafo<Integer, Integer>(Grafo.getTamanho());
           
           try {
             dijkstra = new DijkstraRoteamento(Grafo);
             conjuntoVerticesRotulados = dijkstra.gerarArvoreCaminhoMinimo(raiz);
             for(int i = 0; i < conjuntoVerticesRotulados.length; i++) {
                   inserirVerticeArvore(conjuntoVerticesRotulados[i].obterVertice());
                   if(conjuntoVerticesRotulados[i].getPredecessor() != null && !Arvore.procurarNode(conjuntoVerticesRotulados[i].getPredecessor())) {
                       Arvore.inserirVertice(conjuntoVerticesRotulados[i].getPredecessor());
                       NodeHash<Node<Integer, Integer>> nodeHashGrafo = new NodeHash(conjuntoVerticesRotulados[i].getPredecessor(), (Integer)conjuntoVerticesRotulados[i].getPredecessor().getValor());//estamos criando uma indexacao sobre o campo do valor do no, para termos acesso O(1), evidentimente, temos que partir da suposicao que esse campo de rerpesentacao do no eh unico, o que nao eh uma verdade necessario, portanto precisamos estar certos sobre o campo indexaxao criado
                       hashGrafo.inserirNodeHash(nodeHashGrafo);
                   }

                   if(conjuntoVerticesRotulados[i].getPredecessor() != null) {
                          Arvore.inserirAresta(conjuntoVerticesRotulados[i].getPredecessor(), conjuntoVerticesRotulados[i].obterVertice(), conjuntoVerticesRotulados[i].getDistancia()); //dessa maneira, devemos gerar uma arvore de caminho minimos. Veja que a aresta  A -> B | 56, nao significa de que B tem custo 56 em relacao a A, porem custo 56 em relacao a raiz
                   }
             }
           } catch(Exception ex) {
             ex.printStackTrace();
           }
               
      }

      public Grafo<Integer, Integer> getArvore() {
              return Arvore;
      }

      private void inserirVerticeArvore(Node<Integer, Integer> vertice) throws Exception {
              if(!Arvore.procurarNode(vertice)) {
                  Arvore.inserirVertice(vertice);
                  NodeHash<Node<Integer, Integer>> nodeHashGrafo = new NodeHash(vertice, (Integer)vertice.getValor());//estamos criando uma indexacao sobre o campo do valor do no, para termos acesso O(1), evidentimente, temos que partir da suposicao que esse campo de rerpesentacao do no eh unico, o que nao eh uma verdade necessario, portanto precisamos estar certos sobre o campo indexaxao criado
                   hashGrafo.inserirNodeHash(nodeHashGrafo);
              }   
      }
     
      public saidaCusto encontrarInterfaceSaida(int no) throws Exception {//implementar o algoritmo de busca em profundidade
             return funcaoBuscaProfundidade(no, 0, (Integer)raiz.getValor(), 0, null);
      }
      
      private saidaCusto funcaoBuscaProfundidade(int no, int nivel, int no_atual, int custo, Integer saida) throws Exception {
              if(no_atual == no && nivel == 0) {//caso o no atual for igual a no, e o nivel for zero, entao a propria raiz eh o no de procura, nessse caso a saida deve ser nula, conforme a logica do estado de enlace
                   return new saidaCusto(null, custo);
              } else if(no_atual == no) {//caso o no atual for igual a no, e nivel diferente de zero, entao a saida eh igual a valor de saida indicado com o custo
                   return new saidaCusto(saida, custo);//saida atual mais custo total eh o valor de retorno
              } 
              Node<Integer, Integer> noA = (Node<Integer, Integer>)hashGrafo.obterNodeHash(no_atual).valor;//temos de que, um vez dado o 
              if(no_atual != (Integer)noA.getValor()) {
                   throw new RuntimeException("arvore inconsistente inconsistente"); 
              }
              for(ListaDupla.Element ptrNode = Arvore.getAdjacencia(noA.getChave()).getHead();  ptrNode != null; ptrNode = ptrNode.getNext()) {	
                        int no_atual_ptr = ((Aresta<Integer, Integer>)(ptrNode.getValor())).getNode().getValor();
                        int saida_ptr = ((Aresta<Integer, Integer>)(ptrNode.getValor())).getNode().getValor();
                        int custo_ptr = ((Aresta<Integer, Integer>)(ptrNode.getValor())).getPeso();
                        saidaCusto valor = null;
                        if(nivel == 0) {
                               valor = funcaoBuscaProfundidade(no, nivel+1,  no_atual_ptr, custo_ptr, saida_ptr);                           
                        } else {
                               valor = funcaoBuscaProfundidade(no, nivel+1,  no_atual_ptr, custo_ptr, saida); 
                        }   
                        if(valor != null) {
                            return valor;
                        }
              }
              return null;//caso o no nao seja encontrado, deveremos retornar um valor nulo
      }
      
      /**
      
         Funcao funcaoBuscaProfundidade(Parametro: no, nivel, no_atual, custo, saida):
             se no_atual = no e nivel = 0
                 retornar (null, 0) //retornar nulo com o custo atual
             senao-se no_atual = no
                 retornar (saida, custo)
             senao
                 para cada vertice i em adj(no_atual)
                 faca
                    se nivel = 0
                       retornar funcaoBuscaProfundidade(no, (nivel+1)%2, i, custo(no_atual, i), i)
                    senao
                       retornar funcaoBuscaProfundidade(no, (nivel+1)%2, i, custo(no_atual, i), saida)
                 fim-para      
             fim-se
         fim-funcaoBuscaProfundidade
      **/
}
