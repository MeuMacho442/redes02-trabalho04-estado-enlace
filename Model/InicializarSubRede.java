/************************************************************************************************
*Autor............: Gustavo Henrique Oliveira Fernandes
*Matricula........: 202410104
*Inicio...........: 12/04/2026
*Ultima alteracao.: 12/04/2026
*Nome.............: InicializarSubRede
*Funcao...........: Essa classe sera reponsavel por inicializar a simulação do roteamento pelo algoritmo de dijkstra e pela
                    cordenacao de inicializacao de cada maquina de estados que representa o roteador. Veja que cada abstracao do roteador
                    conhecera seus roteadores adjacentes, mediante a estrutura de representacao logica do grafo. Essa representacao logica do grafo
                    eh fundamental para que o modelo de abstracao dos roteadores conheca seus visinhos e estabeleca um enlace de comunicacao entre eles.
                    O grafo tambem oferece uma representacao global da subrede que sera fundamental para aplicar o roteamento por menor distacia.  
**************************************************************************************************/


package Model;


import Model.ListaDupla.Element;
import Model.ListaDupla;
import Controller.ControllerDeMover;
import Controller.AnimacaoControler;
import Controller.EmitirAvisoTela;
import java.util.concurrent.Semaphore;
import Model.CaminhoMinimoNaoExistenteException;

public class InicializarSubRede {
      private Grafo<Roteador, Posicao> grafo;
      private Node<Roteador, Posicao>[] listaNode;
      private DijkstraRoteamento<Roteador, Posicao> roteamentoMenorDistancia; 
      private int idEmissor, idReceptor;
      private AnimarDijkstra registroParaAnimcao;
      private static Semaphore semaforo;
      private EmitirAvisoTela emitirAviso;

      /********************************************************************************************
      * Metodo: InicializarSubRede 
      * Funcao: permitir que o arquivo que realiza o rotemanto tenha acesso ao modelo logico da rede, o grafo. E 
                tambem incializa os componentes necessarios para o adequado funcionamento deste programa
      * Parametros: grafo - permitindo uma referencia global a estrutura da rede 
      * Retorno: nenhum
      ********************************************************************************************/
      public InicializarSubRede(Grafo<Roteador, Posicao> grafo, EmitirAvisoTela emitirAviso) {
            this.grafo = grafo;
            this.semaforo = new Semaphore(0);
            this.registroParaAnimcao = new AnimarDijkstra(grafo);	
            this.roteamentoMenorDistancia = new DijkstraRoteamento(grafo, registroParaAnimcao);
            this.listaNode = grafo.getConjuntoVertice();
            this.emitirAviso = emitirAviso;
      }//fim do metodo InicializarSubRede
      
      
      public void iniciarSimulacao(ControllerDeMover controlerTela, AnimacaoControler Animacao, int id_emissor, int id_recptor) {
              //System.out.println("entrou na regiao critica");
              SicronizacaoLeitorEscritor.editarBaseDadosEscritor(); //essa regiao critica somente sera liberada apos um caminho ser encontrado   
                 for(int i = 0; i < listaNode.length; i++) {
                      if(id_emissor == listaNode[i].getChave()) {
                           Pacote pacote = new Pacote(Pacote.TipoPacote.mensagem);
                           pacote.setRoteador(listaNode[i].getValor());
                           pacote.definirIdemissorReceptor(id_emissor, id_recptor);
                          (listaNode[i].getValor()).encaminhamentoMenorRota(pacote);//veja que essa aqui eh a regiao critica
                      }
                 }
      }
      
      /********************************************************************************************
      * Metodo: iniciarSimulacao 
      * Funcao: Permite a execucao da computacao do menor caminho a partir de nodes iniciais, sejam X e Y. 
                Também inicializa o processo de animacao do algoritmo de dijkstra a partir da observacao do registro de animacao
                dos conjuntos de eventos que estruturou o menor caminho. Veja tambem que essa funcao estabelce para cada roteador as suas arestas de 
                conexao adjacentes. Como cada node representante do rotador conhecera seus visinhos adjacnetes, teremos uma espelho perfeito da representacao 
                logica do grafo mediante a estrutura de dados e esses dispositivos de abstração distribuida
      * Parametros: int  idEmissor, int idReceptor, ControllerDeMover controlerTela, AnimacaoControler controlerAnimacaoDijkstra
      * Retorno: nenhum
      ********************************************************************************************/
      public void iniciarSimulacao(ControllerDeMover controlerTela, AnimacaoControler controlerAnimacaoDijkstra) { 
         try { 
            controlerTela.atualizarMensagem("Underfine");
            for(int i = 0; i < listaNode.length; i++) {
                (listaNode[i].getValor()).setPosicao(listaNode[i].getSegundoValor());
                RoteadoresAdjacentes interfaceSaida = new RoteadoresAdjacentes(grafo.getAdjacencia(listaNode[i].getChave()), grafo.getTamanho());
                (listaNode[i].getValor()).definirAdjacencia(interfaceSaida);
                for(ListaDupla.Element ponteiro = grafo.getAdjacencia(listaNode[i].getChave()).getHead(); ponteiro != null; ponteiro = ponteiro.getNext()) {
                   int id = (((Aresta<Roteador, Posicao>)(ponteiro.getValor())).getNode()).getValor().getId_identificao();
                   int peso = ((Aresta<Roteador, Posicao>)(ponteiro.getValor())).getPeso();
                   (interfaceSaida).prencherPesosAdjacentes(id, peso);  
                }  
                (listaNode[i].getValor()).start();                       
            }

         } catch(CaminhoMinimoNaoExistenteException e) {
             System.out.println("Caminho nao encontrado");
             emitirAviso.emitirAvisoTela();
         } catch(Exception ex) {
            ex.printStackTrace();
         }
      }//fim do metodo iniciarSimulacao

      /********************************************************************************************
      * Metodo: mostrarCaminho
      * Funcao: constroi uma representacao do caminho usando de variaveis String para representar o conjunto de caminhos minimos
                que compoem a trajetoria dado dois nodes X e Y 
      * Parametros: int  idEmissor, int idReceptor, ControllerDeMover controlerTela, AnimacaoControler controlerAnimacaoDijkstra
      * Retorno: nenhum
      ********************************************************************************************/ 
      public String mostrarCaminho(ListaDupla<Node<Roteador, Posicao>> lista) {
           String res = "";
           for(ListaDupla.Element ptr = lista.getHead(); ptr != null; ptr = ptr.getNext()) {
                res = res + (((Node<Roteador, Posicao>)ptr.getValor()).getChave() + 1) + " -> ";
                
           }    
             return res;
      } //fim do metodo mostrarCaminho
      
      /********************************************************************************************
      * Metodo: criarThread 
      * Funcao: funcao auxiliar a funcao iniciarSimulacao 
      * Parametros: AnimacaoControler controlerAnimacaoDijkstra
      * Retorno: nenhum
      ********************************************************************************************/ 
      private void criarThread(AnimacaoControler controlerAnimacaoDijkstra) {
            registroParaAnimcao.setPonteiro(controlerAnimacaoDijkstra); 
            registroParaAnimcao.animarDikstra();
      }//fim do metodo criarThread
}
