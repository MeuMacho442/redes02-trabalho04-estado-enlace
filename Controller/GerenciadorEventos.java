/************************************************************************************************
*Autor............: Gustavo Henrique Oliveira Fernandes
*Matricula........: 202410104
*Inicio...........: 29/08/2026
*Ultima alteracao.: 29/08/2026
*Nome.............: GerenciadorEventos
*Funcao...........: gerenciar os eventos da interface grafica, controlando o fluxo de execucao
*                   da simulacao, validando entradas e coordenando a inicializacao da sub-rede
**************************************************************************************************/


package Controller;

import View.Janela;
import Model.ListaDinamica;
import Model.Roteador;
import Model.Grafo;
import Model.InicializarSubRede;
import Model.ProcurarElementoGrafo;
import Model.EscreverArquivo;
import Model.LerArquivo;
import Model.Posicao;
import Model.Node;


//trabalho ainda nao pronto, somente um prototipo
public class GerenciadorEventos {
    private Integer idEmissor;
    private Integer idReceptor;
    private static String subRedePadrao = "8;" +
                                           "\n1;2;2;5" +
                                           "\n1;7;6;5" +
                                           "\n2;3;7;5" +
                                           "\n2;5;2;3" +
                                           "\n3;6;3;2" +
                                           "\n3;4;3;1" +
                                           "\n4;8;2;1" +
                                           "\n5;7;1;4" +
                                           "\n5;6;2;4" +
                                           "\n6;8;2;4" +
                                           "\n7;8;4;5";
    private funcaoAltaOrdem funcao;
    private funcaoAltaOrdemLink funcaoEditarLinksLigacao;
    private funcaoAltaOrdemEditar funcaoEditar;
    private Janela janela;
    private InicializarSubRede subRede;
    private enum EstadoPrograma {INICIADO, NAOINICIADO}
    private EstadoPrograma estadoPrograma;
    private AnimacaoControler controlerAnimacaoDijkstra;
     
    /********************************************************************************************
    * Metodo: GerenciadorEventos (construtor)
    * Funcao: inicializa o gerenciador de eventos com estado inicial nao iniciado
    * Parametros: janela - referencia da interface principal
    * Retorno: nenhum
    ********************************************************************************************/
    public GerenciadorEventos(Janela janela) {
           this.estadoPrograma = EstadoPrograma.NAOINICIADO;
           this.janela = janela;
    }
    
    /********************************************************************************************
    * Metodo: start
    * Funcao: associa uma funcao de alta ordem ao evento principal da interface
    * Parametros: alocarRede - responsavel pela configuracao inicial da rede
    * Retorno: vazio
    ********************************************************************************************/
    public void start(AlocarNode alocarRede) {
         
         funcao = () -> {
                         janela.editarTexto("UnderFine");
                         Integer idEmissor =  Integer.parseInt(janela.getInputs(0));
                         Integer idReceptor =  Integer.parseInt(janela.getInputs(1));
                         iniciarEcaminhamento(idEmissor-1, idReceptor-1, alocarRede);
         }; 
         funcaoEditarLinksLigacao = (id_roteador1, id_roteador2) -> {
          
               alocarRede.getGrafo().removerAresta(
                  alocarRede.getGrafo().getNode(id_roteador1),
                  alocarRede.getGrafo().getNode(id_roteador2)    
               );

               alocarRede.getGrafo().removerAresta(
                 alocarRede.getGrafo().getNode(id_roteador2),
                 alocarRede.getGrafo().getNode(id_roteador1)    
               );
           
         };
         iniciarSimulacao(alocarRede);
         alocarRede.getSubRede().interagirAresta(funcaoEditarLinksLigacao);
         janela.definirEvento(funcao);
    }//fim do metodo start
    

    /********************************************************************************************
    * Metodo: iniciarEcaminhamento
    * Funcao: inicia o processo de encaminhamento de um pacote na camada de rede
    * Parametros: idEmissor, idReceptor, alocarRede
    * Retorno: vazio
    ********************************************************************************************/
    private void iniciarEcaminhamento(Integer idEmissor, Integer idReceptor, AlocarNode alocarRede) {
               
      try { 
           
            verificarIntegridade(idEmissor, idReceptor, alocarRede.getGrafo().getConjuntoVertice());
            subRede.iniciarSimulacao(new ControllerDeMover(janela), controlerAnimacaoDijkstra, idEmissor, idReceptor);
       } catch(NumberFormatException kx) { 
             janela.emitirAlerta("formato deve ser numerico");
             janela.rabilitarButao(); 
             System.out.println("fluxo de execuacao interrompido");
         } catch(ExcessaoIncosistenciaEntrada ex) {
              janela.rabilitarButao(); 
              System.out.println("fluxo de execuacao interrompido");
         }
    }//fim do metodo iniciarEcaminhamento
    
    /********************************************************************************************
    * Metodo: iniciarSimulacao
    * Funcao: controla o fluxo de inicializacao e execucao da simulacao, incluindo validacao
    *         de entradas e invocacao da logica da sub-rede
    * Parametros: alocarRede - responsavel pela estrutura da rede
    * Retorno: vazio
    ********************************************************************************************/
    private void iniciarSimulacao(AlocarNode alocarRede)  {
       if(estadoPrograma == EstadoPrograma.INICIADO) { 
          janela.limparComponentesPainelDireito();
          alocarRede.setNode(); alocarRede.setAresta();
       } else {this.estadoPrograma = EstadoPrograma.INICIADO;}
        EmitirAvisoTela emitirAviso = new EmitirAvisoTela(janela);
        subRede = new InicializarSubRede(alocarRede.getGrafo(), emitirAviso);
        controlerAnimacaoDijkstra = new AnimacaoControler(alocarRede.getSubRede());
        
        try {   
          //verificarIntegridade(idEmissor, idReceptor, alocarRede.getGrafo().getConjuntoVertice());
          subRede.iniciarSimulacao(new ControllerDeMover(janela), controlerAnimacaoDijkstra);
       } catch(NumberFormatException kx) { 
          janela.emitirAlerta("formato deve ser numerico");
          janela.rabilitarButao(); 
          System.out.println("fluxo de execuacao interrompido");
       } catch(ExcessaoIncosistenciaEntrada ex) {
            janela.rabilitarButao(); 
          System.out.println("fluxo de execuacao interrompido");
       }
    } //fim do metodo 

     /********************************************************************************************
    * Metodo: verificarIntegridade
    * Funcao: valida os dados de entrada garantindo consistencia logica dos identificadores
    * Parametros: idEmissor, idReceptor - identificadores dos nos
    *             listNode - conjunto de nos do grafo
    * Retorno: vazio (lanca excecao em caso de erro)
    ********************************************************************************************/
    private void verificarIntegridade(Integer idEmissor, Integer idReceptor, Node<Roteador, Posicao>[] listNode) throws ExcessaoIncosistenciaEntrada {
      
         if(idEmissor == idReceptor) {
              janela.emitirAlerta("dois host's iguais"); 
              throw new ExcessaoIncosistenciaEntrada("entrada inconsistente");  //lancar um popup de aviso na versao final
         }
          
         ProcurarElementoGrafo verificarValidadeDeExistencia = new ProcurarElementoGrafo(listNode);
         if(!verificarValidadeDeExistencia.procurarNode(idEmissor) ||  !verificarValidadeDeExistencia.procurarNode(idReceptor)) {
              janela.emitirAlerta("Emissor ou recptor nao existente na rede");
            
              throw new ExcessaoIncosistenciaEntrada("entrada inconsistente"); 
         }
    }//fim do metodo verificarIntegridade

    public static String lerArquivoBackBone() {
          LerArquivo lerArquivoBackBone = new LerArquivo("backbone.txt");
          return lerArquivoBackBone.lerTodoArquivo();
    }
    
}
