/************************************************************************************************
*Autor............: Gustavo Henrique Oliveira Fernandes
*Matricula........: 202410104
*Inicio...........: 29/08/2026
*Ultima alteracao.: 29/08/2026
*Nome.............: Roteador
*Funcao...........: representar um roteador em uma simulacao de rede utilizando threads, 
*                   com controle de envio de pacotes por algoritmo de inundacao

numero de sequencia numa rede, cada entrada da rede contem um numero de sequencia associado
**************************************************************************************************/

package Model;//essa classe eh um prototipo, ainda em desenvolvimento

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import Controller.ControllerDeMover;
import java.util.Random;

public class Roteador extends Thread {

   private Fila<Pacote> fila; //fila de pacote  
   private Semaphore mutex;// controle de exclusao mutua
   private Semaphore mutexFila;// controle de sincronizacao da fila
   private RoteadoresAdjacentes interfaceSaida;// roteadores adjacentes
   private Posicao localizacao;// posicao no espaco
   private int id_identificao;// identificador do roteador
   private int contador_arestas_pendentes = 0;//conta quantas interfaces asssociados a aresta deve receber um pacote echo answer para inundar a rede
   private ControllerDeMover ponteiro;// controlador de envio
   private TabelaDistancia tabela;//protocolo de prenchimento de tabela antigo, vetor de distancia
   private int numero_sequncia_atual = 0;//mantem dados atuais recentemente usados na rede  
   private static final int INFINITO = 600000;//veja que a latencia eh gerado entre os intervalos [1,500), portanto podemos usar o menor dos maiores para representar um peso de custo infinito 
   private TabelaEstadoEnlace tabelaEnlace;//protocolo usado neste projeto, que cria uma arvore de caminho minimo como base para compor a tabela de roteamento
   private Mapeamento HashTable;
   private HashDinamico<Integer> controlerSequencia;
   //veja que o inicio modela o envio do ping de estimativa inicial do custo do enlace adjacente, enquanto timeLimmit indica o envio periodico de pacotes de controle para preenchimento da tabela
   /********************************************************************************************
   * Metodo: Roteador
   * Funcao: inicializar o roteador com seus atributos basicos
   * Parametros: int id_identificao - identificador do roteador
   * Retorno: nenhum
   ********************************************************************************************/ 
   public Roteador(int id_identificao) {
        this.mutex = new Semaphore(1);
        this.fila = new Fila();
        this.mutexFila = new Semaphore(0);
        this.id_identificao = id_identificao;
        this.tabelaEnlace = new TabelaEstadoEnlace();
        this.controlerSequencia = new HashDinamico<>();
        this.HashTable = new Mapeamento(8);//define o tamanho da tabela 
        this.preencherMapeamento();
        
    } 
   
   /********************************************************************************************
   * Metodo: run
   * Funcao: iniciar a execucao da thread do roteador
   * Parametros: nenhum
   * Retorno: nenhum
   ********************************************************************************************/
   @Override
   public void run() {
         System.out.println("Roteador " + id_identificao + " inicializado");
         try { 
           roteamentoEstadoEnlace();
         } catch(Exception ex) {
            ex.printStackTrace();
         }
   }//fim do metodo run 

   /********************************************************************************************
   * Metodo: roteamentoVetorDistancia
   * Funcao: gerenciar o roteamento do pacote pela camada de rede. Essa funcao eh composta por varias camada. Primeiro, ela gera varios pacotes de controle em que requri um medicao de custo ate cada um de seus vizinhos. Apos esse processo, o roteador envia peridicamente sua tabela de roteamento para cada um de seus vizinhos. E recebe essa a tabela de controle de cada um de seus vizinho, entao atualiza sua tabela usando o algoritimo de relaxamente de belaman-ford 
   * Parametros: nenhum
   * Retorno: nenhum
   ********************************************************************************************/
   private void roteamentoEstadoEnlace() throws Exception {//essa função trabalha para fazer o prenchimento da tabela
        Pacote pacote = null;
        EstadoDeEventos Evento = EstadoDeEventos.inicio;
        while(true && interfaceSaida.contarNumeroInterfacesSaida() != 0) {
            switch(Evento) {
                 case inicio:
                   pacote = new Pacote(Pacote.TipoPacote.ping_gerado);//inicia o pacote ping a ser enviado
                   pacote.setRoteador(this);//define o roteador atual como emissor de origem   
                   tabelaEnlace.inserirNoRaiz(id_identificao);  
                   enfileirarPacote(pacote);  //enfileira o pacote na fila
                   contadorTempo(1400, 0); //inicia o contador para enviar o pacote que conte dados da tabela, com um tempo suficientimento alto para que as tabeelas estejam preenchidas, caso nao estejam prenchidas, pior cenario, por raao de falhas tecnicas, o algoritmo ainda deve funcionar perfeitamente 
                   contadorRotasPendentes();//temporizador que conta quantas rotas ainda faltam serem confiirmadas para espalhar pacotes de enlace
                   Evento = Evento.pacoteChegou;
                 break;   
                 case ProcessarPacote:
                   processarEnvio(pacote);//processo o pacote recebido com base no seu tipo. ping_enviado, envia um pacote de controle que contem o custo de seus vizinhos. Ping_back, descobre a informacao de cada um de seus vizinhos. Controle de tabela - atualiza sua tabela atual, enviar_tabela - propaga seu custo ate cada um de seus vizinhos. Ping-gerado, manda um pacote para estimar o custo de acesso ao enlance de seus vizinhos
                   Evento = EstadoDeEventos.pacoteChegou;  
                 break;
                 case pacoteChegou:
                    DownMutex(mutexFila);//mutex de controle de acesso ao buffer compattilhado limitado
                    DownMutex(mutex);//mutex de acesso a regiao critica, fundamental para evitar cndicao de corrida e estados incosistentes
                    try {
                      pacote = fila.desenfileirar();             
                    } catch(Exception ex) {
                      ex.printStackTrace();
                    } finally {
                       UpMutex(mutex);
                    }  
                    Evento = Evento.ProcessarPacote;
                 break; 
            }
        }
   }//fim do metodo roteamentoVetorDistancia
   /********************************************************************************************
   * Metodo: processarEnvio
   * Funcao: Para cada pacote recebido, o comportamento variara dependo da informacao que contem o tipo de pacote
   * Parametros: nenhum
   * Retorno: nenhum
   ********************************************************************************************/
   private void processarEnvio(Pacote pacote) throws Exception {
          Pacote.TipoPacote tipoPacote = pacote.getTipo();
          HashTable.getFuncao(tipoPacote.tipoAtual).funcao(pacote);    
   }//fim do metodo processarEnvio

   /********************************************************************************************
   * Metodo: preencherMapeamento
   * Funcao: um hash map de alta ordem manual. Veja como uma funcao de associacao H : {ping_gerado, ping_enviado, pingBack, enviar_Tabela, controleTabela} -> {funcaopropagarParaAdjacentes, funcaoenviarparaorigem, funcaoatualizartabela, funcaoprenchervizinhos}
   * Parametros: nenhum
   * Retorno: nenhum
   ********************************************************************************************/
   private void preencherMapeamento() {
         HashTable.associarFuncaoPosicao(Pacote.TipoPacote.ping_gerado, (pacoteParametro) -> {
                propagarParaAdjacentes(pacoteParametro, pacoteParametro.definirTipo(Pacote.TipoPacote.HELLO));//refere-se a primeira fase do algoritmo de estado de enlace, geracao de pacotes hello na rede
         });
         HashTable.associarFuncaoPosicao(Pacote.TipoPacote.HELLO, (pacoteParametro) -> {//nessa fase, foi enviado o pacote hello pela rede, com o objetivo de descobrir seus vizinhos referentes 
                enviarPacoteParaInterfaceSelecionada(pacoteParametro, pacoteParametro.definirTipo(Pacote.TipoPacote.HELLO_ANSWER));
         });
         
         HashTable.associarFuncaoPosicao(Pacote.TipoPacote.HELLO_ANSWER, (pacoteParametro) -> {//nesse caso, foi recebido o pacote de origem da rede
               int peso = INFINITO; //incialmente o interesse esta focado somente em conhecer os vizinhos da rede
               int vertice = pacoteParametro.getEmissor().getId_identificao(); //um valor logico do pacote eh atribuido ao vertice
               tabelaEnlace.inserirNosAdjacentesNoRaiz(vertice, peso); 
               Pacote pacoteEnvio = new Pacote(Pacote.TipoPacote.ECHO);
               pacoteEnvio.setRoteador(pacoteParametro.roteadorAnterior());
               enviarPacoteParaInterfaceSelecionada(pacoteEnvio, pacoteEnvio.getTipo());
         });
         //o problema parece ser a passagem de referencia do grafo
         HashTable.associarFuncaoPosicao(Pacote.TipoPacote.ECHO, (pacoteParametro) -> {
                  int custo = 1 + new Random().nextInt(480); //gera um valor aleatorio entre os valores de 1 a 500 milesegundos
                  Pacote pacoteEnvio  = new Pacote(Pacote.TipoPacote.ECHO_ANSWER);
                  pacoteEnvio.setRoteador(pacoteParametro.roteadorAnterior());
                  pacoteEnvio.definirCusto(custo);
                  enviarPacoteParaInterfaceSelecionada(pacoteEnvio, pacoteEnvio.definirTipo(Pacote.TipoPacote.ECHO_ANSWER));
         });
         HashTable.associarFuncaoPosicao(Pacote.TipoPacote.ECHO_ANSWER, (pacoteParametro) -> {
                 int idOrigem = pacoteParametro.roteadorAnterior().getId_identificao();
                 int custo = pacoteParametro.obterCusto();
                 ponteiro.atualizarPeso(localizacao, pacoteParametro.roteadorAnterior().getPosicao(),interfaceSaida.getBitFlagAresta(idOrigem), custo);//comando abstrato para atualizar peso no interface graficfica mediante a um interface de intermediação das funcionalidades logicas do programa com a interface grafica
                 tabelaEnlace.inserirPeso(idOrigem, custo);//atualiza o pesos com base nas informacoes recebido pelo pacote do tipo echo, assim descobrindo informcaoes vitais sobre seus vizinhos
                 
                 contador_arestas_pendentes++;
         });
         
         HashTable.associarFuncaoPosicao(Pacote.TipoPacote.LINK_PACKAGE_SEND, (pacoteParametro) -> {//temos de que um pacote de estado de enlace eh criado 
                  Pacote pacoteEnvio  = new Pacote(Pacote.TipoPacote.LINK_PACKAGE);//isso significa que novos dados estao sendo produzidos 
                  pacoteEnvio.setRoteadorOrigem(this);//todo o pacote de enlace criado pelo roteador k, tem-no como origem referente a esse pacote
                  pacoteEnvio.setNumeroSequencia(numero_sequncia_atual);
                  pacoteEnvio.setRoteador(this);
                  pacoteEnvio.setInformacaoVizinhos(tabelaEnlace.obterInformacaoEnlace());
                  propagarParaAdjacentes(pacoteEnvio, pacoteEnvio.definirTipo(Pacote.TipoPacote.LINK_PACKAGE));
                  numero_sequncia_atual++;
         });
         
         HashTable.associarFuncaoPosicao(Pacote.TipoPacote.LINK_PACKAGE, (pacoteParametro) -> {
                  Pacote pacoteEnvio  = new Pacote(Pacote.TipoPacote.LINK_PACKAGE);
                  pacoteEnvio.setRoteadorOrigem(pacoteParametro.getEmissor());
                  pacoteEnvio.setNumeroSequencia(pacoteParametro.getPacoteSequencia());
                  pacoteEnvio.setRoteador(pacoteParametro.roteadorAnterior());
                  pacoteEnvio.setInformacaoVizinhos(pacoteParametro.obterInformacaoVizinhos());
                  int numero_sequencia_pacote = pacoteEnvio.getPacoteSequencia();
                  
                  if(controlerSequencia.verificarSeValorExiste(pacoteEnvio.getEmissor().getId_identificao()) && (controlerSequencia.obterNodeHash(pacoteEnvio.getEmissor().getId_identificao()).valor < numero_sequencia_pacote)) {//verifica se existe uma entrada para o node de origem referente 
                            if(id_identificao == 0) {
                                 System.out.println("o numero de sequencia atual eh: " + numero_sequencia_pacote);
                            }
                            controlerSequencia.obterNodeHash(pacoteEnvio.getEmissor().getId_identificao()).valor = numero_sequencia_pacote;//atualiza os dados mais recentes da rede
                            tabelaEnlace.preencherTabela(pacoteEnvio.obterInformacaoVizinhos()); //atualiza a representacao interna do grafo
                            tabelaEnlace.atualizarTabelaRoteamento();
                            ponteiro.atualizarTabela(tabelaEnlace.mostrarTabela(), id_identificao);
                            propagarParaAdjacentes(pacoteEnvio, pacoteEnvio.definirTipo(Pacote.TipoPacote.LINK_PACKAGE));               
                  } else if(!controlerSequencia.verificarSeValorExiste(pacoteEnvio.getEmissor().getId_identificao())) {//se nao existe entrada na tabela para o pacote, atualiza-a
                           NodeHash<Integer> nodeHah = new NodeHash(numero_sequencia_pacote, pacoteEnvio.getEmissor().getId_identificao());
                           controlerSequencia.inserirNodeHash(nodeHah);
                           tabelaEnlace.preencherTabela(pacoteEnvio.obterInformacaoVizinhos());
                           tabelaEnlace.atualizarTabelaRoteamento();
                           ponteiro.atualizarTabela(tabelaEnlace.mostrarTabela(), id_identificao);
                           propagarParaAdjacentes(pacoteEnvio, pacoteEnvio.definirTipo(Pacote.TipoPacote.LINK_PACKAGE)); 
                  } 
                  
         });
         
   }//fim do metodo preencherMapeamento
   
   
   private  void enviarPacoteParaInterfaceSelecionada(Pacote pacoteParametro, Pacote.TipoPacote tipo) {
       try {
           do {
                SicronizacaoLeitorEscritor.acessarBaseDadosLeitorComPrioridade();
                     if(interfaceSaida.getRoteadorAtual(id_identificao) == pacoteParametro.roteadorAnterior()) {//estrutura de controle que permite o envio para o roteador de origemm a quem solicitou oi pcaote hello
                           Pacote pacoteEnvio = new Pacote(tipo.tipoAtual);//informacao de controle que registra aquele enlace como pacote de retorno da requisicao anteirmente solicitada
                           pacoteEnvio.setRoteador(this); //registra este roteador como o hop anterior
                           pacoteEnvio.setRoteadorOrigem(this); //registra este roteador como o hop de origem
                           pacoteEnvio.definirCusto(pacoteParametro.obterCusto());
                           CordenarEnvio enviar =  new CordenarEnvio(ponteiro, 0); //zero indica que o envio se dara para controle de pacote
                           enviar.enviarPacote(this, interfaceSaida.getRoteadorAtual(id_identificao), pacoteEnvio);
                           enviar.start(); 
                     }
                     controlarEnvio();  
                 SicronizacaoLeitorEscritor.deixarBaseDadosLeitorComPrioridade();
          } while(interfaceSaida.next());
       } catch(Exception ex) {
          ex.printStackTrace();
       }
   }  
   /********************************************************************************************
   * Metodo: encaminhamentoMenorRota
   * Funcao: encaminha o pacote para a proxima rota baseando-se na consulta da tabela, veja que essa funcao independe do protocolo implementado, ja que o objetivo de todos os protocolos da rede eh determinar a arvore de escoamente de caminhos minimos sobre o grafo que modela a rede. Dada essa arvore, entao podemos realizar a composicao da tabela
   * Parametros: pacote
   * Retorno: nenhum
   ********************************************************************************************/
   public void encaminhamentoMenorRota(Pacote pacote) {
      Integer proximoHop = tabelaEnlace.obterSaida(pacote.getIdRecptor());
      boolean controleQuadaEnlace = false;
      try { 
             if(proximoHop == null && pacote.getIdRecptor() == id_identificao) {//se o proximo hop eh nulo, entao o caminho foi encontrado
                 System.out.println("caminho encontrado");
             } else if(proximoHop != null && proximoHop != -1) {//se o proximo hop eh diferente de nulo e -1 entao temos que esse hop objetivo ainda nao foi alcancado pela rede 
                 do {
                       if(interfaceSaida.getRoteadorAtual(id_identificao).getId_identificao() == proximoHop) {
                          
                          Pacote pacoteEnvio = new Pacote(pacote.getTipo().tipoAtual);//determina de que o pacote atual eh um pacote de encaminhamento na rota
                          pacoteEnvio.definirTTL(pacote.getTTL());
                          pacoteEnvio.definirIdemissorReceptor(pacote.getIdEmissor(), pacote.getIdRecptor());
                          pacoteEnvio.setRoteador(pacote.roteadorAnterior()); 
                          pacoteEnvio.setInformacaoVizinhos(pacote.obterInformacaoVizinhos());
                          if(verificarSePacoteEhValido(pacoteEnvio, proximoHop)) {
                             pacoteEnvio.setRoteador(this);
                             CordenarEnvio enviar =  new CordenarEnvio(ponteiro, 1); // 1 indica de que o encaminhamento sera orientado a tabela de roteamento
                             enviar.enviarPacote(this, interfaceSaida.getRoteadorAtual(id_identificao), pacoteEnvio);
                             enviar.start(); 
                             controlarEnvio();
                          } else {
                            System.out.println("caminho nao encontrado");
                            proximoHop = -1;
                            ponteiro.emitirAviso();
                          }
                          controleQuadaEnlace = true;
                       }   
                 } while(interfaceSaida.next());
             } else if(proximoHop == null) {
                System.out.println("caminho nao encontrado");
                ponteiro.emitirAviso();     
             }
      } catch(Exception ex) {  
              proximoHop = null;  
      } finally {
           if(proximoHop == null || proximoHop == -1 || !controleQuadaEnlace) {
              
              SicronizacaoLeitorEscritor.deixarBaseDadosEscritor();//apos o caminho ser enontrado, temos de que o semaforo de acesso ao livro sera liberado  
              ponteiro.reabilitarButao();
           }
      } 
   }//fim do metodo encaminhamentoMenorRota

   /********************************************************************************************
   * Metodo: verificarSePacoteEhValido
   * Funcao: mecainismo formal para evitar loop infinito de pacote na rede
   * Parametros: pacote
   * Retorno: nenhum
   ********************************************************************************************/
   private boolean verificarSePacoteEhValido(Pacote pacote, Integer id_proximo_hop) {
           
           if(pacote.getTTL() <= 0) {
                return false; 
           }
           Integer id_anterior = pacote.roteadorAnterior().getId_identificao();
           
           if(id_anterior == id_proximo_hop) {
                 pacote.decrementarTTL();
           } else {
                 pacote.renovarTTL();  
           }
           return true;
   }//fim do metodo verificarSePacoteEhValido

   /********************************************************************************************
   * Metodo: propagarParaAdjacentes
   * Funcao: propaga os pacotes para os vizinhos adjacentes, menos a interface em que chegou
   * Parametros: pacote, tipo
   * Retorno: nenhum
   ********************************************************************************************/
   private void propagarParaAdjacentes(Pacote pacote, Pacote.TipoPacote tipo) {
      try {   
        do {  
               SicronizacaoLeitorEscritor.acessarBaseDadosLeitorComPrioridade();
                      if(interfaceSaida.getRoteadorAtual(id_identificao).getId_identificao() != pacote.roteadorAnterior().getId_identificao()) {
                          Pacote pacoteEnvio = new Pacote(tipo.tipoAtual);//essa eh uma infomarcao de que esse pacote eh de controle, portanto enviando a informacao de volta que contem o custo de ida deste elance 
                          pacoteEnvio.setRoteador(this);
                          pacoteEnvio.setRoteadorOrigem(pacote.getEmissor());
                          pacoteEnvio.setNumeroSequencia(pacote.getPacoteSequencia());
                          pacoteEnvio.setInformacaoVizinhos(pacote.obterInformacaoVizinhos());
                          CordenarEnvio enviar =  new CordenarEnvio(ponteiro, 0); //zero indica de que o pacote se dara para controle de preenchimento da tabela
                          enviar.enviarPacote(this, interfaceSaida.getRoteadorAtual(id_identificao), pacoteEnvio);
                          enviar.start(); 
                          controlarEnvio();
                      }
               SicronizacaoLeitorEscritor.deixarBaseDadosLeitorComPrioridade();
        } while(interfaceSaida.next());
      } catch(Exception ex) {
         
         ex.printStackTrace();
      }
        
   }//fim do metodo propagarParaAdjacentes
   
   private void checarId(Node<Roteador, Posicao> next_hop) {
         if(next_hop == null) {
              ponteiro.reabilitarButao();
         }
   }
   
 
  /********************************************************************************************
   * Metodo: iniciarEnvio
   * Funcao: iniciar o envio de um pacote com TTL definido
   * Parametros: nenhum
   * Retorno: nenhum
   ********************************************************************************************/
   public void iniciarEnvio(Pacote pacote) {
       enfileirarPacote(pacote);
   }
   
   /********************************************************************************************
   * Metodo: enfileirarPacote
   * Funcao: inserir pacote na fila com controle de concorrencia
   * Parametros: Pacote pacote
   * Retorno: nenhum
   ********************************************************************************************/
   public void enfileirarPacote(Pacote pacote) {    
           try { 
            DownMutex(mutex);
             if(!fila.cheia()) {
                 fila.enfileirar(pacote);
                 UpMutex(mutex);
                 UpMutex(mutexFila);
             } else {
               UpMutex(mutex);
             }
           } catch(Exception ex) {
              ex.printStackTrace();
           }
   }
   
   
   public void sinalizarEventoDeChegada(Pacote pacote) {
           enfileirarPacote(pacote);
   }


   public void definirAdjacencia(RoteadoresAdjacentes interfaceSaida) {
           this.tabela = new TabelaDistancia(id_identificao, interfaceSaida);
           this.interfaceSaida = interfaceSaida;
   }
  
  /********************************************************************************************
   * Metodo: DownMutex
   * Funcao: adquirir o semaforo
   * Parametros: Semaphore semaphore
   * Retorno: nenhum
   ********************************************************************************************/
  private void DownMutex(Semaphore semaphore) {
         try {
               semaphore.acquire();
         } catch(Exception ex) {
               ex.printStackTrace();
         }
   }
   
   /********************************************************************************************
   * Metodo: UpMutex
   * Funcao: liberar o semaforo
   * Parametros: Semaphore semaphore
   * Retorno: nenhum
   ********************************************************************************************/
   private void UpMutex(Semaphore semaphore) {
       try {
              semaphore.release();
         } catch(Exception ex) {
               ex.printStackTrace();
         }
   }

   private void contadorTempo(int tempoparametro, int fase) {
        final int tempoLambda = tempoparametro;
        new Thread(() -> {
         while(true && interfaceSaida.contarNumeroInterfacesSaida() != 0) {
                 int tempo = tempoLambda;
                 
                 while(tempo > 0) {
                    tempo--;
                     try {
                        Thread.sleep(16);
                     } catch (InterruptedException ex) {
                        ex.printStackTrace(); 
                     }
                 }
                 
                 Pacote pacote = new Pacote(Pacote.TipoPacote.ping_gerado);
                 tabelaEnlace.esvaziarEstruturaAdjacenteDaRaiz();//temos de que a topologia devera ser reaadaptada
                 pacote.setRoteador(this);
                 enfileirarPacote(pacote);  
         }
        }).start();
   }

   private void contadorRotasPendentes() {
        new Thread(() -> {
         while(true && interfaceSaida.contarNumeroInterfacesSaida() != 0) {
            SicronizacaoLeitorEscritor.acessarBaseDadosLeitorComPrioridade();
              while(contador_arestas_pendentes != interfaceSaida.contarNumeroInterfacesSaida()) {
                   
                   try {
                       Thread.sleep(200);
                   } catch (InterruptedException ex) {
                       ex.printStackTrace(); 
                   }
              }
          SicronizacaoLeitorEscritor.deixarBaseDadosLeitorComPrioridade();
              contador_arestas_pendentes = 0;//reinicia o contador
              Pacote pacote = new Pacote(Pacote.TipoPacote.LINK_PACKAGE_SEND);
              pacote.setRoteador(this);
              enfileirarPacote(pacote);
         } 
        }).start();
   }

   public int getId_identificao() {
         return id_identificao;
   }

   public void setPosicao(Posicao xs) {
          this.localizacao = xs;
   }

   public Posicao getPosicao() {
          return localizacao;
   }

   public void setControllerDeMover(ControllerDeMover ponteiro) {
          this.ponteiro = ponteiro;
   } 


   public void controlarEnvio() {
        try {
            Thread.sleep(16);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
   }
}
