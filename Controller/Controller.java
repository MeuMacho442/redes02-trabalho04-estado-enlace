/************************************************************************************************
*Autor............: Gustavo Henrique Oliveira Fernandes
*Matricula........: 202410104
*Inicio...........: 29/08/2026
*Ultima alteracao.: 29/08/2026
*Nome.............: Controller
*Funcao...........: coordenar a inicializacao do sistema, integrando a configuracao da rede,
*                   gerenciamento de eventos e controle de animacoes
**************************************************************************************************/


package Controller;

import View.Janela;
import Model.Operadores;
import Model.LerArquivo;
import Model.Operadores;
import Model.ListaIndexada;
import Model.EscreverArquivo;
import Model.Grafo;
import Model.Node;
import Model.NodeHash;
import Model.HashDinamico;
import Model.ArvoreCaminhoMinimo;

public class Controller {
      private AlocarNode alocarNode;
      private GerenciadorEventos eventos;
      private ControllerDeMover controleMover;
        
      private Janela janela; 
      private Grafo grafo;
      
      /********************************************************************************************
      * Metodo: Controller (construtor)
      * Funcao: inicializa os componentes principais de controle do sistema
      * Parametros: janela - referencia da interface principal
      * Retorno: nenhum
      ********************************************************************************************/
      public Controller(Janela janela) {
          this.janela = janela;
          this.eventos = new GerenciadorEventos(this.janela);
          this.alocarNode = new AlocarNode(this.janela);
          this.controleMover = new ControllerDeMover(this.janela);
      }//fim do metodo Controller
      
      /********************************************************************************************
      * Metodo: iniciar
      * Funcao: realiza a inicializacao da rede e registra os eventos principais do sistema
      * Parametros: nenhum
      * Retorno: vazio
      ********************************************************************************************/
      public void iniciar() { 
        
          /**int tamanho = 5;
          Grafo<Integer, Integer> grafoTeste = new Grafo<>(tamanho);
          try {
             Node<Integer, Integer> noA = new Node(0);
             Node<Integer, Integer> noB = new Node(1);
             Node<Integer, Integer> noE = new Node(2);
             Node<Integer, Integer> noD = new Node(3);
             grafoTeste.inserirVertice(noA); grafoTeste.inserirVertice(noB); grafoTeste.inserirVertice(noE); grafoTeste.inserirVertice(noD);
             grafoTeste.inserirAresta(noA, noB, 1); grafoTeste.inserirAresta(noA, noE, 3); grafoTeste.inserirAresta(noE, noD, 2); 
             grafoTeste.inserirAresta(noB, noE, 4);  
             System.out.println(grafoTeste.mostrarGrafo());
             System.out.println(grafoTeste.copyGrafo().mostrarGrafo());
          } catch(Exception ex) {
               ex.printStackTrace();
             //System.out.println(ex.getMessage());
          }**/

          try { 
              alocarNode.setNode();
              alocarNode.setAresta();
          } catch(ExcessaoIncosistenciaArquivo ex) {
              System.out.println(ex.getMessage());
          } catch(Exception ex) {
              ex.printStackTrace();
          } finally {
              eventos.start(alocarNode);
          }
          
          //System.out.println(hashMap.mostrarTabela());
      } //fim do metodo inicia
}
