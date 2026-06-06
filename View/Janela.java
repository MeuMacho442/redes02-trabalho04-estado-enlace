/************************************************************************************************
* Autor............: Gustavo Henrique Oliveira Fernandes
* Matricula........: 202410104
* Inicio...........: 29/03/2026 (entre 22 a 26)
* Ultima alteracao.: 29/03/2026 (entre 22 a 30)
* Nome.............: Janela
* Funcao...........: Estabelecer o alocamento de componentes de butões na tela de interação com o usuário.
**************************************************************************************************/

package View;

//Importações necessarias para a implementaçao funcional
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.Node;
import Controller.funcaoAltaOrdem;
import Controller.funcaoAltaOrdemEditar;
import View.PacoteView;
import javafx.application.Platform;

public class Janela {
    private Stage stage;
    private Pane janelaRaiz;
    private Pane painelEsquerdo; 
    private EstruturarSubRede ponteiro;
    private Pane painelDireito;
    private ConfigurarPainel configurarPainel;
    private ConfigurarPainelDireito configurarPainelDireito;
    private Aviso aviso;
    private double larguraJanela = 1000;
    private double alturaJanela = 650;
    
     /********************************************************************************************
     * Metodo: Construtor da classe Janela
     * Funcao: Instancia e inicializa o alocamento de componentes na tela
     * Parametros: stage - instancia da classe Stage, fundamental para estabelecer o node
     *             raiz da interface  
     * Retorno: retorna a instância da classe  
     ********************************************************************************************/
    public Janela(Stage stage) {
          this.stage = stage;
          this.aviso = new Aviso();
          configurarSceneRaiz();
    }//fim construtor Janela
  
    /********************************************************************************************
    * Metodo: configurarSceneRaiz
    * Funcao: estableece a cena principal e carrega todos os componentes de iteracao da tela, chamando as fucoes auxiliares como:
    *          ->  configurarJanelaPrincipal()
    *          
    * Parametros: vazio  
    * Retorno: vazio  
    ********************************************************************************************/
    public void configurarSceneRaiz() { 
        janelaRaiz = new Pane(); 
        configurarJanelaPrincipal();
        Scene scene = new Scene(janelaRaiz, larguraJanela, alturaJanela); //define o pane "janelaRaiz" como nodo grafico raiz da interface 
        stage.setScene(scene);  
        stage.setResizable(false);
        stage.show();
        stage.setTitle("Simulacao de roteamento");
    } //fim do metodo configurarSceneRaiz
   
    /********************************************************************************************
    * Metodo: adicionarPonto
    * Funcao: adiciona o ponto azul de representacao do pacote na tela         
    * Parametros: (x,y) -> double
    * Retorno: vazio  
    ********************************************************************************************/
   public void adicionarPonto(double x, double y) {
        PacoteView View = new PacoteView(this);
        View.escreverPacoteView(x,y);
   } //fim do metodo adicionarPonto

    /********************************************************************************************
    * Metodo: configurarJanelaPrincipal
    * Funcao: Instancia e inicializa o alocamento de componentes na tela
    * Parametros: (x,y) -> double
    * Retorno: vazio  
    ********************************************************************************************/
    private void configurarJanelaPrincipal() {
          janelaRaiz.setStyle("-fx-background-color: #90ee90;");
          painelEsquerdo = new Pane();
          configurarPainel = new ConfigurarPainel(painelEsquerdo);
          painelDireito = new Pane();
          configurarPainelDireito = new ConfigurarPainelDireito(painelDireito);  
          janelaRaiz.getChildren().add(painelDireito);
          janelaRaiz.getChildren().add(painelEsquerdo);
    } //fim da funcao configurarJanelaPrincipal

    /********************************************************************************************
    * Metodo: getLargurarPanelDireito
    * Funcao: retorna a largura do painel direito da animacao
    * Parametros: vazio
    * Retorno: vazio  
    ********************************************************************************************/
    public double getLargurarPanelDireito() {
           return ConfigurarPainelDireito.getLargurar();
    } //fim-getLargurarPanelDireito

    /********************************************************************************************
    * Metodo: getAlturaPanelDireito
    * Funcao: retorna a altura do painel direito da animacao
    * Parametros: vazio
    * Retorno: vazio  
    ********************************************************************************************/
    public double getAlturaPanelDireito() {
           return ConfigurarPainelDireito.getAltura();
    } //fim do metodo getAlturaPanelDireito


    /********************************************************************************************
    * Metodo: getAlturaPanelDireito
    * Funcao: adiciona um componente herdado da classe Node na scene principal
    * Parametros: node
    * Retorno: vazio  
    ********************************************************************************************/
    public void adicionarNaJanelaRaiz(Node node) {
            janelaRaiz.getChildren().add(node);
    } //fim do metodo adicionarNaJanelaRaiz
    
     /********************************************************************************************
    * Metodo: adicionarNoPainelDireito
    * Funcao: adiciona um componente do tipo Node ao painel direito da interface
    * Parametros: node - componente grafico a ser adicionado
    * Retorno: vazio  
    ********************************************************************************************/
    public void adicionarNoPainelDireito(Node node) {
       Platform.runLater(() -> {
           painelDireito.getChildren().add(node);
       });    
    } //fim do metodo adicionarNoPainelDireito

    /********************************************************************************************
    * Metodo: adicionarNoPainelDireito
    * Funcao: adiciona um componente do tipo Node ao painel direito da interface
    * Parametros: node - componente grafico a ser adicionado
    * Retorno: vazio  
    ********************************************************************************************/
    public void adicionarLinhaNoPainelDireito(Node node) {
       Platform.runLater(() -> {
           painelDireito.getChildren().add(0, node);
       });    
    } //fim do metodo adicionarNoPainelDireito


    
    /********************************************************************************************
    * Metodo: retirarNoPainelDireito
    * Funcao: remove um componente do tipo Node do painel direito da interface
    * Parametros: node - componente grafico a ser removido
    * Retorno: vazio  
    ********************************************************************************************/
    public void retirarNoPainelDireito(Node node) {
       Platform.runLater(() -> {
           if (painelDireito.getChildren().contains(node)) {
              painelDireito.getChildren().remove(node);
           }
       });      
    }//fim do metodo retirarNoPainelDireito
    
    /********************************************************************************************
    * Metodo: definirEvento
    * Funcao: define uma acao de alto nivel associada ao botao do painel de configuracao
    * Parametros: w - funcao de alta ordem que representa o comportamento do evento
    * Retorno: vazio  
    ********************************************************************************************/
    public void definirEvento(funcaoAltaOrdem w) {
        configurarPainel.setAcaoButao(w);
    }//fim do metodo definirEvento
    
    public void definirEventoEditarSubRede(funcaoAltaOrdemEditar r) {
       // configurarPainel.setAcaoButaoEditar(r);
    }//fim do metodo definirEvento

      /*****************************************************************************************
      * metodo: emitirAviso
      * funcao: avisaar de que o caminho entre dois nodes da rede nao foi encontrado
      * parametros: vazio
      * retorno: vazio
      *****************************************************************************************/  
      public void emitirAviso() {
             
         Platform.runLater(() -> {  
            aviso.mostrarAlerta("caminho nao encontrado");
         });
      }

    /********************************************************************************************
    * Metodo: getInputs
    * Funcao: retorna o valor de entrada selecionado com base em uma opcao
    * Parametros: opcao - indice ou identificador da entrada desejada
    * Retorno: String correspondente ao valor da entrada  
    ********************************************************************************************/
    public String getInputs(int opcao) {
         return (configurarPainel.getInput(opcao));
    } //fim do metodo getInputs
    
    /********************************************************************************************
    * Metodo: editarTexto
    * Funcao: altera dinamicamente o texto exibido no painel de configuracao
    * Parametros: contador - valor utilizado na atualizacao do texto
    * Retorno: vazio  
    ********************************************************************************************/
    public void editarTexto(String mensagem) {
         configurarPainelDireito.editarTexto(mensagem);
    } //fim do metodo editarTexto
    
    public void adicionarInformacaoPainel(int indice, String mensagem) {
         configurarPainel.alocarInformacaoJanela(indice, mensagem);
    }
   
    /********************************************************************************************
    * Metodo: versao
    * Funcao: identifica qual versao foi selecionada no ComboBox do painel
    * Parametros: nenhum
    * Retorno: inteiro representando a versao selecionada (1 a 4) ou -1 caso invalido  
    ********************************************************************************************/  
    public Integer versao() {
       
         if(configurarPainel.getInputComboBox().equals("Versao 1")) {
             return 1;
         } else if(configurarPainel.getInputComboBox().equals("Versao 2")) {
            return 2;
         } else if(configurarPainel.getInputComboBox().equals("Versao 3")) {
            return 3;
         } else if(configurarPainel.getInputComboBox().equals("Versao 4")) {
           return 4;
         }
         
         return -1;
    } //fim do metodo versao

    /********************************************************************************************
    * Metodo: adicionarTextoPainelDireito
    * Funcao: adiciona um texto ao painel direito com base em coordenadas e valor
    * Parametros: peso - valor numerico a ser exibido
    *             x - coordenada horizontal
    *             y - coordenada vertical
    * Retorno: vazio  
    ********************************************************************************************/
    public void adicionarTextoPainelDireito(int peso1, int peso2, double x, double y) {
           configurarPainelDireito.adicionarTexto(String.valueOf(peso1) + ";" + String.valueOf(peso2),x,y);
    }//fim do metodo adicionarTextoPainelDireito

    public StackPane estruturarTextoPainel(double x, double y) {
          return configurarPainelDireito.estruturarTextoLabelPesoCusto("--;--", x, y);
    }//fim do metodo adicionarTextoPainelDireito

    public StackPane estruturarTextoPainel(int peso1, int peso2, double x, double y) {
             return configurarPainelDireito.estruturarTextoLabelPesoCusto((String.valueOf(peso1) + ";" + String.valueOf(peso2)),x,y);
    }//fim do metodo adicionarTextoPainelDireito

    
    /********************************************************************************************
    * Metodo: getTTL
    * Funcao: retorna o valor do TTL definido no painel de configuracao
    * Parametros: nenhum
    * Retorno: inteiro representando o TTL  
    ********************************************************************************************/
    public Integer getTTL() {
         return configurarPainel.getInputSpinner();
    }//fim do metodo getTTL
    
    /********************************************************************************************
    * Metodo: getAltura
    * Funcao: retorna a altura atual da janela principal
    * Parametros: nenhum
    * Retorno: valor double correspondente a altura  
    ********************************************************************************************/
    public double getAltura() {
        return alturaJanela;
    }//fim do metodo getAltura
    
    /********************************************************************************************
    * Metodo: getLargura
    * Funcao: retorna a largura atual da janela principal
    * Parametros: nenhum
    * Retorno: valor double correspondente a largura  
    ********************************************************************************************/
    public double getLargura() {
        return larguraJanela;
    }//fim do metodo getLargura

    /********************************************************************************************
    * Metodo: rabilitarButao
    * Funcao: reabilita o botao de interacao no painel de configuracao
    * Parametros: nenhum
    * Retorno: vazio  
    ********************************************************************************************/
    public void rabilitarButao() {
           configurarPainel.reablitarButao();
           
    } //fim do metodo rabilitarButao

    public void fecharButao() {
         configurarPainel.desabilitarButao();
    }
    
    /********************************************************************************************
    * Metodo: emitirAlerta
    * Funcao: exibe uma mensagem de alerta para o usuario
    * Parametros: mensagem - texto a ser exibido no alerta
    * Retorno: vazio  
    ********************************************************************************************/
    public void emitirAlerta(String mensagem) {
          Platform.runLater(() -> {
            aviso.mostrarAlerta(mensagem);
         });
           
    }//fim do metodo emitirAlerta
    
    /********************************************************************************************
    * Metodo: getPainelDireito
    * Funcao: retorna a instancia do painel direito de configuracao
    * Parametros: nenhum
    * Retorno: objeto do tipo ConfigurarPainelDireito  
    ********************************************************************************************/
    protected ConfigurarPainelDireito getPainelDireito() {
         return configurarPainelDireito;
    }//fim do metodo getPainelDireito
    
    public void inicializarTabela(int quantidadeNos) {
         try {
           configurarPainel.estruturaJanela(quantidadeNos);
         } catch(NullPointerException ex) {
           ex.printStackTrace();                           
         }
    }
    
    public void limparComponentesPainelDireito() {
     Platform.runLater(() -> { 
          configurarPainelDireito.excluirNodes();
      });    
    }

   public void setSubRede(EstruturarSubRede subRede) {
          this.ponteiro = subRede;
   }
   
   public EstruturarSubRede getSubRede() {
          return this.ponteiro;
   }
}
