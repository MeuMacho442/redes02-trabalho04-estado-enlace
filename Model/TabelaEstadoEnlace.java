/************************************************************************************************
*Autor............: Gustavo Henrique Oliveira Fernandes
*Matricula........: 202410104
*Inicio...........: 29/08/2026
*Ultima alteracao.: 29/08/2026
*Nome.............: TabelaEstadoEnlace
*Funcao...........: representar a tabela de estado de enlace do roteador na rede. Realizando o seu prenchimento para cada tabela de distancia recebida
**************************************************************************************************/

package Model;

public class TabelaEstadoEnlace {
      private Grafo<Integer, Integer> grafo; //grafo da sub-rede em que foi gerado
      private static final int tamanhoTotal = 300;//tamanho total de nos do grafo
      private LinhaTabela[] vetorTabelaDistanciaEnlace;
      private HashDinamico<Node<Integer, Integer>> hashGrafo;
      private ListaDinamica<Integer> listaNos;//mantem uma lista de nos descobertos com base na topologia estabelecida  
     //private int ptr_quantidade = 0; //aponta para o numero de nos totais registrados na rede
      private Node<Integer, Integer> raiz; //representacao do id da tabela de roteamento
      private ArvoreCaminhoMinimo arvore;
      public class InformacaoEnlace {
            Integer raiz;
            ListaDupla<Aresta<Integer, Integer>> lista;
            public InformacaoEnlace(Integer raiz, ListaDupla<Aresta<Integer, Integer>> lista) {
                   this.raiz = raiz;
                   this.lista = lista;
            }
      }
      public TabelaEstadoEnlace() {
             this.vetorTabelaDistanciaEnlace = new LinhaTabela[tamanhoTotal];
             this.grafo = new Grafo(tamanhoTotal); 
             this.hashGrafo = new HashDinamico<>();
             this.listaNos = new ListaDinamica<>();
      }
      public  Grafo<Integer, Integer> getArvore() {
               return arvore.getArvore();
      }

      public void atualizarTabelaRoteamento() {
         try {
            
             Grafo<Integer, Integer> grafoCopy = grafo.copyGrafo();
             Node<Integer, Integer>[] lista =  grafoCopy.getConjuntoVertice();
             arvore = new ArvoreCaminhoMinimo(raiz);
             arvore.gerarArvoredeEscoamento(grafoCopy);//gera a arvore de escoamento com base na topologia atual estabelecida no grafo
             
             for(int i = 0; i < listaNos.getTamanho(); i++) {
                   vetorTabelaDistanciaEnlace[i] = new LinhaTabela();  
                   ArvoreCaminhoMinimo.saidaCusto saida_custo = arvore.encontrarInterfaceSaida(listaNos.get(i));
                   if(saida_custo == null) {
                       vetorTabelaDistanciaEnlace[i].setCusto(null);
                       vetorTabelaDistanciaEnlace[i].setEntrada(listaNos.get(i));
                       vetorTabelaDistanciaEnlace[i].setSaida(null);
                   } else {
                       Double custo = (double)saida_custo.custo;
                       vetorTabelaDistanciaEnlace[i].setCusto(custo);
                       vetorTabelaDistanciaEnlace[i].setEntrada(listaNos.get(i));
                       vetorTabelaDistanciaEnlace[i].setSaida(saida_custo.no);
                   }
             }
         } catch(Exception ex) {
             ex.getMessage();
             ex.printStackTrace();
         }
      }
      
      /********************************************************************************************
      * Metodo: mostrarTabela
      * Funcao: formular a informacao de saida da tabela
      * Parametros: int entrada, int custo  
      * Retorno: vazio  
      ********************************************************************************************/ 
      public String mostrarTabela() {
           String res = "";
           
           for(int i = 0; i < listaNos.getTamanho(); i++) {
            if((vetorTabelaDistanciaEnlace[i].getEntrada()+1)/10 == 0) {
                  if((vetorTabelaDistanciaEnlace[i].getSaida()) == null) { res += "        " + (vetorTabelaDistanciaEnlace[i].getEntrada()+1) + "      |     --     |  " + vetorTabelaDistanciaEnlace[i].getCusto() + "\n"; } else {
                       res += "        " + (vetorTabelaDistanciaEnlace[i].getEntrada()+1) + "      |     " + (vetorTabelaDistanciaEnlace[i].getSaida()+1) + "     |  " + vetorTabelaDistanciaEnlace[i].getCusto() + "\n";
                  }
            } else if((vetorTabelaDistanciaEnlace[i].getEntrada()+1)/10 >= 1 && (vetorTabelaDistanciaEnlace[i].getEntrada()+1)/10 <= 9) {
                  if((vetorTabelaDistanciaEnlace[i].getSaida()) == null) { res += "       " + (vetorTabelaDistanciaEnlace[i].getEntrada()+1) + "      |     --     |  " + vetorTabelaDistanciaEnlace[i].getCusto() + "\n"; } else {
                       res += "       " + (vetorTabelaDistanciaEnlace[i].getEntrada()+1) + "     |     " + (vetorTabelaDistanciaEnlace[i].getSaida()+1) + "     |  " + vetorTabelaDistanciaEnlace[i].getCusto() + "\n";
                  }
            }
           }   
           return res;
      }// fim do metodo mostrarTabela
      
      public void inserirNoRaiz(int Vertice) throws Exception {//registra o nodo raiz da tabela
         
            raiz = new Node(Vertice);//define o nodo principal do grafo de representação da subrede
            grafo.inserirVertice(raiz);
            listaNos.add(Vertice);
            NodeHash<Node<Integer, Integer>> hashNode = new NodeHash(raiz, Vertice);
            hashGrafo.inserirNodeHash(hashNode);
         
      }
      
      public Grafo<Integer, Integer> getGrafo() {
           return grafo;
      }
      public void inserirNosAdjacentesNoRaiz(int noVertice, int peso) { //repare que estamos construindo um grafo logico da rede
          try {
                Node<Integer, Integer> no = new Node<>(noVertice);
                NodeHash<Node<Integer, Integer>> hashNode = new NodeHash(no, noVertice);
                if(hashGrafo.obterNodeHash(noVertice) == null || hashGrafo.obterNodeHash(noVertice).chave != noVertice) {//para o caso em que o vertice nao esta registrado na representacao da subrede pelo grafo 
                    hashGrafo.inserirNodeHash(hashNode);
                    grafo.inserirVertice(no);
                    grafo.inserirAresta(raiz, no, peso);
                    listaNos.add(noVertice);
                } else {
                    grafo.inserirAresta(raiz, hashGrafo.obterNodeHash(noVertice).valor, peso); //raiz -> no, lista adjacencia do grafo
                } 
          } catch(Exception ex) {
             ex.printStackTrace();
          }
      }

      public void esvaziarEstruturaAdjacenteDaRaiz() {//essa funcao tem como objetivo reestabelcer novas rotas e invalidar as rotas anteriores, para permitir uma readaptacao interna da topologia
         try {
             grafo.removerEstruturaAdjacencia(hashGrafo.obterNodeHash(raiz.getValor()).valor);
         } catch(Exception ex) {
             ex.printStackTrace();
         }
      }
      
     

      public void preencherTabela(InformacaoEnlace listaTabela) {
          
           
           try {
              
               if(hashGrafo.obterNodeHash(listaTabela.raiz) == null || hashGrafo.obterNodeHash(listaTabela.raiz).chave != listaTabela.raiz) {//verifica se o no original esta registrado no grafo
                         Node<Integer, Integer> no = new Node<>(listaTabela.raiz);
                         NodeHash<Node<Integer, Integer>> hashNode = new NodeHash(no, listaTabela.raiz);//cria uma associacao com o no conjuntamente com o seu valor
                         hashGrafo.inserirNodeHash(hashNode);//insere o node hash no grafo
                         grafo.inserirVertice(no);
                         listaNos.add(no.getValor());
              } else if(hashGrafo.obterNodeHash(listaTabela.raiz) != null && hashGrafo.obterNodeHash(listaTabela.raiz).chave == listaTabela.raiz) {//caso o valor ja esteja na tebela, provavelmente esse novo dado se trata de uma informacao atualiizada, o dado antigo deve ser descartado. Evidentimente, pode ser um dado desatualizado, a responsabilidade de verificar a validade do dado mediante ao numero de sequencia esta delegado ao arquivo do roteador 
                         grafo.removerEstruturaAdjacencia(hashGrafo.obterNodeHash(listaTabela.raiz).valor);
              }          
              
         
              for(ListaDupla.Element ptr = listaTabela.lista.getHead(); ptr != null; ptr = ptr.getNext()) {
                            int vertice = ((Aresta<Integer, Integer>)ptr.getValor()).getNode().getValor();
                            int custo = ((Aresta<Integer, Integer>)ptr.getValor()).getPeso();
                            if(hashGrafo.obterNodeHash(vertice) == null || hashGrafo.obterNodeHash(vertice).chave != vertice)  {//veja que caso nao haja a existencia do node a que se deseja representar no grafo
                                 Node<Integer, Integer> no = new Node<>(vertice);
                                 NodeHash<Node<Integer, Integer>> hashNode = new NodeHash(no, vertice);//cria uma associacao com o no conjuntamente com o seu valor
                                 hashGrafo.inserirNodeHash(hashNode);
                                 grafo.inserirVertice(no);
                                 listaNos.add(no.getValor());
                            }
                            grafo.inserirAresta(hashGrafo.obterNodeHash(listaTabela.raiz).valor, hashGrafo.obterNodeHash(vertice).valor, custo);
                             
                                  System.out.println(grafo.mostrarGrafo());
                             
              }
             
          } catch(Exception ex) {
             ex.printStackTrace();
          }  
      }
      
      public Integer obterSaida(int entrada) {
                for(int i = 0; i < listaNos.getTamanho(); i++) {
                    
                    if(entrada == vetorTabelaDistanciaEnlace[i].getEntrada() && vetorTabelaDistanciaEnlace[i].getCusto() != null) {
                        return vetorTabelaDistanciaEnlace[i].getSaida();
                    } else if(vetorTabelaDistanciaEnlace[i].getCusto() == null && entrada == vetorTabelaDistanciaEnlace[i].getEntrada()) {
                        return null;
                    }
                }
                return null;
      }
      
      public void inserirPeso(int noVertice, int peso) {//essa funcao tem como responsabilidade inserir um peso associado ao grafo, seja a raiz k, e o vertice adjacente y, entao a insercao k->y devera ter o peso definido r. Eh verade que sua responsabilidade h soemnte laterar peso da aresta que liga a raiz de seu adjacente
           if(raiz == null) {
                 throw new RuntimeException("arvore nula");
           }
                grafo.alterarAresta(raiz, hashGrafo.obterNodeHash(noVertice).valor, peso);
      }
      
      public InformacaoEnlace obterInformacaoEnlace()  {
         InformacaoEnlace informacaoPacote = null;
         try { 
             if(raiz != null) {
                  ListaDupla<Aresta<Integer, Integer>> lista =  grafo.getAdjacencia(raiz.getChave());
                  informacaoPacote = new InformacaoEnlace(raiz.getValor(), lista);
             }
         } catch(Exception ex) {
              ex.printStackTrace();
         } finally {
             return informacaoPacote;
         } 
      }
}
