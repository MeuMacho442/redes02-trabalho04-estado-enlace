/************************************************************************************************
*Autor............: Gustavo Henrique Oliveira Fernandes
*Matricula........: 202410104
*Inicio...........: 29/08/2026
*Ultima alteracao.: 29/08/2026
*Nome.............: EstruturarSubRede
*Funcao...........: construir a representacao visual da rede mediante a composicao atomica de tecnicas para representar a rede visualmente, que eh um grafo, arestas, vertices
**************************************************************************************************/


package View;

import Model.Operadores;
import java.lang.Math;
import Model.ListaDinamica;
import Model.Fila;
import javafx.scene.Node;
import Model.ListaDinamica;
import Model.funcaoAltaOrdem;
import Controller.funcaoAltaOrdemLink;
import java.util.concurrent.TimeUnit;

public class EstruturarSubRede {
    private Janela janela;
    private static final int dimensaoX = 40;
    private static final int dimensaoY = 40;
    private double largura;
    private double altura;
    private NodeView<Integer>[] listNodesViews;
    private ListaDinamica<ViewLabel> listaLabel;   //manter uma lista de labels associados a arestas   
    private Fila<ArestaView> listArestaView;
    private ListaDinamica<ArestaView> listaArestaView; 
    
    /********************************************************************************************
    * Metodo: EstruturarSubRede
    * Funcao: cria um objeto de referencia para o local de armazenamento dos componetes visuais, como paineis, textfields, etc.
    * Parametros: janela  
    * Retorno: vaio  
    ********************************************************************************************/
    public EstruturarSubRede(Janela janela) {
           this.janela = janela;
           this.largura = janela.getPainelDireito().getLargurar();
           this.altura = janela.getPainelDireito().getAltura();
           this.listArestaView = new Fila<>();
           this.listaArestaView = new ListaDinamica<>();
           this.listaLabel = new ListaDinamica<ViewLabel>();
           this.janela.setSubRede(this);
    } //fim do metodo EstruturarSubRede
    
    public void alocarConjuntoNodeView(Integer size) {
            listNodesViews = new NodeView[size];
    }

    private void adicionarNodeView(NodeView<Integer> K, int key) {
             listNodesViews[key] = K;
    }

    /********************************************************************************************
    * Metodo: alocarNodeView
    * Funcao: criar uma representação visual do vertice de composicao do grafo
    * Parametros: Integer Valor, double posicaoX, double posicaoY   
    * Retorno: vaio  
    ********************************************************************************************/
    public void alocarNodeView(Integer Valor, double posicaoX, double posicaoY, int key) {
            NodeView<Integer> nodeView = new NodeView<>(Valor);
            nodeView.definirDimensao(dimensaoX, dimensaoY);
            posicaoX = posicaoX+largura/2;//normalizando para o centro
            posicaoY = altura/2-posicaoY; //normalizando para o centro
            nodeView.setPosicaoVertice(posicaoX-(dimensaoX/2), posicaoY-(dimensaoY/2));
            adicionarNodeView(nodeView, key);
            janela.adicionarNoPainelDireito(nodeView.getVertice());
    }// fim do metodo alocarNodeView
    
    public void alocarNovaArestaView(double posicaoX0, double posicaoY0, double posicaoX, double posicaoY, String cor) {
         try {  
           ArestaView arestaView = new ArestaView();
           double NewPosicaoX0 = posicaoX0+largura/2;
           double NewPosicaoY0 = altura/2-posicaoY0;
           double NewPosicaoX = posicaoX+largura/2;
           double NewPosicaoY = altura/2-posicaoY;
           arestaView.criarAresta(NewPosicaoX0, NewPosicaoY0, NewPosicaoX, NewPosicaoY, cor);
           listArestaView.enfileirar(arestaView);
           janela.adicionarNoPainelDireito(arestaView.obterArestaViewLine());
           janela.adicionarNoPainelDireito(arestaView.obterArestaView());
         } catch(Exception ex) {
            ex.printStackTrace();
         }  
           
    }// fim do metodo alocarNodeView
    
    public void reiniciliarFila() {
           this.listArestaView = new Fila<>();
    }

    public void alocarNovaArestaView(double posicaoX0, double posicaoY0, double posicaoX, double posicaoY, String cor, double opacidade) {
         try {  
           ArestaView arestaView = new ArestaView();
           double NewPosicaoX0 = posicaoX0+largura/2;
           double NewPosicaoY0 = altura/2-posicaoY0;
           double NewPosicaoX = posicaoX+largura/2;
           double NewPosicaoY = altura/2-posicaoY;
           arestaView.criarAresta(NewPosicaoX0, NewPosicaoY0, NewPosicaoX, NewPosicaoY, cor);
           arestaView.setOpacidade(opacidade);
           janela.adicionarNoPainelDireito(arestaView.obterArestaViewLine());
           janela.adicionarNoPainelDireito(arestaView.obterArestaView());
           
         } catch(Exception ex) {
            ex.printStackTrace();
         }  
           
    }// fim do metodo alocarNodeView
    
    
    public void eliminarArestaView() {
         try {  
           janela.retirarNoPainelDireito(listArestaView.desenfileirar().obterArestaView());
           //janela.adicionarNoPainelDireito(arestaView.criarAresta(NewPosicaoX0, NewPosicaoY0, NewPosicaoX, NewPosicaoY, cor));
         } catch(Exception ex) {
            ex.printStackTrace();
         }  
           
    }
    
    
    /********************************************************************************************
    * Metodo: alocarArestaView
    * Funcao: criar uma representação visual da aresta da composicao do grafo, usando tecnicas de geometria analitica para alocar valores na logo acima da posicao media de separacao dos dois nodes. Exemplo: (x,y), entao o peso sera: (-x,y)*(distancia)/raiz((proecaoX(x)-projecaoX(y))*(proecaoX(x)-projecaoX(y)) + ((proecaoY(x)-projecaoY(y))*(proecaoY(x)-projecaoY(y)) sera o ponto de alocacao do peso)
    * Parametros: Integer Valor, double posicaoX, double posicaoY   
    * Retorno: vaio  
    ********************************************************************************************/
    public void alocarArestaView(int peso1, int peso2, int id1, int id2, double posicaoX0, double posicaoY0, double posicaoX, double posicaoY) {
           ArestaView arestaView = new ArestaView();
           double NewPosicaoX0 = posicaoX0+largura/2;
           double NewPosicaoY0 = altura/2-posicaoY0;
           double NewPosicaoX = posicaoX+largura/2;
           double NewPosicaoY = altura/2-posicaoY;
           arestaView.definirId(id1, id2);
           janela.adicionarLinhaNoPainelDireito(arestaView.criarAresta(NewPosicaoX0, NewPosicaoY0, NewPosicaoX, NewPosicaoY));
           janela.adicionarLinhaNoPainelDireito(arestaView.obterArestaViewLine());
           double distancia = 10; 
           double t = distancia/new Operadores().calcularRaiz((posicaoY-posicaoY0)*(posicaoY-posicaoY0) + (posicaoX-posicaoX0)*(posicaoX-posicaoX0));
           javafx.scene.layout.StackPane nodeStackPane = null;
           if((posicaoY-posicaoY0) >= 0 && (posicaoX-posicaoX0) >= 0) { 
             nodeStackPane = janela.estruturarTextoPainel(((-t)*(posicaoY-posicaoY0)+(posicaoX0+posicaoX)/2)+largura/2, altura/2+(-1)*((posicaoY+posicaoY0)/2 + (t)*(posicaoX-posicaoX0))); 
           } else {
             nodeStackPane = janela.estruturarTextoPainel(((t)*(posicaoY-posicaoY0)+(posicaoX0+posicaoX)/2)+largura/2, altura/2+(-1)*((posicaoY+posicaoY0)/2 + (-t)*(posicaoX-posicaoX0))); 
           } 
           if(nodeStackPane != null) {
                 ViewLabel labelPane = new ViewLabel(posicaoX0, posicaoX, posicaoY0, posicaoY, nodeStackPane);
                  
                 listaLabel.add(labelPane);
           } 
           arestaView.setPtr(nodeStackPane);
           janela.adicionarNoPainelDireito(nodeStackPane);
           listaArestaView.add(arestaView);
    } //fim do metodo alocarArestaView

    public void alocarPesoArestaView(int peso1, int peso2, int id1, int id2, double posicaoX0, double posicaoY0, double posicaoX, double posicaoY) {
           double distancia = 10; 
           double t = distancia/new Operadores().calcularRaiz((posicaoY-posicaoY0)*(posicaoY-posicaoY0) + (posicaoX-posicaoX0)*(posicaoX-posicaoX0));
           javafx.scene.layout.StackPane nodeStackPane = null;
           if((posicaoY-posicaoY0) >= 0 && (posicaoX-posicaoX0) >= 0) { 
             nodeStackPane = janela.estruturarTextoPainel(peso1, peso2, ((-t)*(posicaoY-posicaoY0)+(posicaoX0+posicaoX)/2)+largura/2, altura/2+(-1)*((posicaoY+posicaoY0)/2 + (t)*(posicaoX-posicaoX0))); 
           } else {
             nodeStackPane = janela.estruturarTextoPainel(peso1, peso2, ((t)*(posicaoY-posicaoY0)+(posicaoX0+posicaoX)/2)+largura/2, altura/2+(-1)*((posicaoY+posicaoY0)/2 + (-t)*(posicaoX-posicaoX0))); 
           } 
           if(nodeStackPane != null) {
                 ViewLabel labelPane = new ViewLabel(posicaoX0, posicaoX, posicaoY0, posicaoY, nodeStackPane);
                 listaLabel.add(labelPane);
                 
           }
           janela.adicionarNoPainelDireito(nodeStackPane);
    } //fim do metodo alocarArestaView
   
    public void alterarViewLabel(double posicaoX0, double posicaoY0, double posicaoX, double posicaoY, int bitFlag, int peso) { //eu sei, uma funcao que mapeia: (X0, Y0, X, Y) -> Z, seja Z inteiro, garantiria acesso constante, mas eh muita coisa, e nao quero importar hash, seria humilhante
          
           for(int i = 0; i < listaLabel.getTamanho(); i++) {
               //System.out.println(posicaoX0 + "\n" + posicaoY0 + "\n" + posicaoX + "\n" + posicaoY + "\n--------------\n" +
               //listaLabel.get(i).getX0() + "\n" + listaLabel.get(i).getY0() + "\n" + listaLabel.get(i).getX() + "\n" + listaLabel.get(i).getY());
               if((posicaoX0 == listaLabel.get(i).getX0() && posicaoY0 == listaLabel.get(i).getY0() && posicaoX == listaLabel.get(i).getX() && posicaoY == listaLabel.get(i).getY()) || (posicaoX == listaLabel.get(i).getX0() && posicaoY == listaLabel.get(i).getY0() && posicaoX0 == listaLabel.get(i).getX() && posicaoY0 == listaLabel.get(i).getY())) {
                       if(bitFlag == 1) { listaLabel.get(i).setPesoDireito(peso); } else if(bitFlag == 0) {listaLabel.get(i).setPesoEsquerdo(peso);} 
                       listaLabel.get(i).alocarPesoLabel();
                       
               }
           }         
    }
    
    public void interagirAresta(funcaoAltaOrdemLink f) {
        for(int i = 0; i < listaArestaView.getTamanho(); i++) {
            final int a = i;
            final int ID1 = listaArestaView.get(i).getId1();
            final int ID2 = listaArestaView.get(i).getId2();
            
            listaArestaView.get(i).obterArestaView().setOnMouseClicked(event -> {
                  f.implementarAcao(ID1, ID2);
                  listaArestaView.get(a).setOpacidade(0.3);
                  new Thread(() -> {
                    esperar();
                    janela.retirarNoPainelDireito(listaArestaView.get(a).getPtr());
                    janela.retirarNoPainelDireito(listaArestaView.get(a).obterArestaView());
                  }).start();  
            });
            listaArestaView.get(i).obterArestaViewLine().setOnMouseClicked(event -> {
                    Thread Processo = new Thread(() -> {
                         Model.SicronizacaoLeitorEscritor.acessarBaseDadosEscritorSemPrioridade();
                                 f.implementarAcao(ID1, ID2);
                                 listaArestaView.get(a).setOpacidade(0.3);
                                 esperar();
                                 janela.retirarNoPainelDireito(listaArestaView.get(a).getPtr());
                                 janela.retirarNoPainelDireito(listaArestaView.get(a).obterArestaView());
                         Model.SicronizacaoLeitorEscritor.deixarBaseDadosEscritorSemPrioridade();
                    });
                  Processo.start();
            });
            
            
             listaArestaView.get(i).obterArestaView().setOnMouseEntered(event -> {
                 listaArestaView.get(a).mudarEstadoLinha();
             }); 
             listaArestaView.get(i).obterArestaViewLine().setOnMouseEntered(event -> {
                 listaArestaView.get(a).mudarEstadoLinha();
             }); 
             
             listaArestaView.get(i).obterArestaView().setOnMouseExited(event -> {
                 listaArestaView.get(a).retornarEstado();
             });
             listaArestaView.get(i).obterArestaViewLine().setOnMouseExited(event -> {
                 listaArestaView.get(a).retornarEstado();
             });
        }
    }
    
    private void esperar() {
         int tempo = 400;
        long startTime = System.currentTimeMillis();
        while(tempo > 0) {
            long elapsedTime = System.currentTimeMillis() - startTime;
            if (elapsedTime >= 16) {
                tempo--;
                startTime = System.currentTimeMillis();
            }
            try {
                TimeUnit.MILLISECONDS.sleep(1);
            } catch (InterruptedException ex) {
                ex.printStackTrace(); 
            }
        }
    }

    public void marcarCinza(int indice) {
           listNodesViews[indice].mudarCor("#000080");   //cinza = #808080,          
    }  

    public void marcarVermelho(int indice) {
           listNodesViews[indice].mudarCor("#FF0000");          
    }
}
