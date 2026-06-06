package View;

import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import javafx.scene.Scene;
import javafx.application.Platform;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import Controller.funcaoAltaOrdemEditar;
import Controller.GerenciadorEventos;

public class JanelaEditarSubrede {
      private Pane painelPrincipal;
      private Stage popUpStage;
      private Button butaoEditar;
      private double larguraJanela = 200;
      private double alturaJanela = 230;
      private TextArea campoTexto;
      private Button butao;

       /********************************************************************************************
       * Metodo: JanelaEditarSubrede (Construtor)
       * Funcao: Inicializa a janela de edição da sub-rede e configura suas propriedades básicas.
       * Parametros: nenhum
       ********************************************************************************************/
      public JanelaEditarSubrede() {
               this.popUpStage = new Stage();
               configurarJanela();
      } 
       
      /********************************************************************************************
      * Metodo: configurarJanela
      * Funcao: Define as propriedades da janela modal, como tamanho, título, cena e estilo.
      * Parametros: nenhum
      * Retorno: vazio
      ********************************************************************************************/ 
      private void configurarJanela() {
              this.painelPrincipal = new Pane();
              this.painelPrincipal.setStyle("-fx-background-color: #FFDBBB;");
              this.popUpStage.initModality(Modality.APPLICATION_MODAL);
              Scene scene = new Scene(painelPrincipal, larguraJanela, alturaJanela);
              this.popUpStage.setScene(scene);
              this.popUpStage.setResizable(false);
              this.popUpStage.setTitle("Edite a subRede"); 
      }//fim do metodo configurarJanela

       /********************************************************************************************
       * Metodo: iniciarTela
       * Funcao: Inicializa e organiza os componentes visuais da janela.
       * Parametros: nenhum
       * Retorno: vazio
       ********************************************************************************************/
      public void iniciarTela() {
             conigurarTextArea();
             estruturarButao();
             setAcaoButaoEditar();
      }//fim do metodo iniciarTela

      /********************************************************************************************
      * Metodo: definirEvento
      * Funcao: Associa uma função de alta ordem ao botão de edição, permitindo que o
      *         conteúdo do campo de texto seja processado externamente.
      * Parametros:
      *      - W: função de alta ordem responsável por tratar a edição da sub-rede.
      * Retorno: vazio
      ********************************************************************************************/
      public void definirEvento(funcaoAltaOrdemEditar W) {
              if(butao != null) {
                 butao.setOnAction(e -> {
                    popUpStage.close();
                       W.editarSubRede(campoTexto.getText());
                 });
              } else {
                  System.out.println("erro na edicao da subrede");
              }
      } //fim do metodo definirEvento

      /********************************************************************************************
       * Metodo: start
       * Funcao: Exibe a janela modal após carregar o conteúdo atual da sub-rede a partir
       *         do GerenciadorEventos.
       * Parametros: nenhum
       * Retorno: vazio
       ********************************************************************************************/ 
      public void start() {
             System.out.println(GerenciadorEventos.lerArquivoBackBone());
             String mensagem = GerenciadorEventos.lerArquivoBackBone();
             if(mensagem != null) {
                 campoTexto.setText(GerenciadorEventos.lerArquivoBackBone()); 
             } else {
                 campoTexto.setText("");
             }
             popUpStage.showAndWait();
      } //fim do metdo start 

      /********************************************************************************************
       * Metodo: conigurarTextArea
       * Funcao: Cria e posiciona a área de texto utilizada para a edição da sub-rede,
       *         aplicando estilos visuais e propriedades de layout.
       * Parametros: nenhum
       * Retorno: vazio
       ********************************************************************************************/ 
       private void conigurarTextArea() {
          campoTexto = new TextArea();
          campoTexto.setWrapText(true);
          campoTexto.setStyle("-fx-text-alignment: left; -fx-alignment: top-left;" +
                              "-fx-control-inner-background: black;" +
                              "-fx-text-fill: #00FF00;" +
                              "-fx-font-size: 12px;" +
                              "-fx-prompt-text-fill: #008000;");
          double largura = 180;
          double altura = 180; 	
          campoTexto.setPrefWidth(largura);
          campoTexto.setPrefHeight(altura);
          campoTexto.setLayoutX((larguraJanela-largura)/2);
          campoTexto.setLayoutY(10);
         // campoTexto.setPromptText("A estrutura da rede deve ser do tipo:");
          campoTexto.setText("");
          painelPrincipal.getChildren().add(campoTexto);
      }//fim do metodo conigurarTextArea 

      /********************************************************************************************
     * Metodo: estruturarButao
     * Funcao: cria e posiciona o botao principal de execucao ("comecar") no painel
     * Parametros: nenhum
     * Retorno: vazio
     ********************************************************************************************/
     private void estruturarButao() {
               String cor = "#C0C0C0";
               String cor_fundo = "black";
               String tamanho_fonte = "12";
               String fonte = "'System'";
               String radius = "6";
               double largura = 120;
               double altura = 20;
               double larguraPainel = 200; 
               double posicaoX = (larguraPainel-largura)/2;
               double posicaoY =  198;
               String title = "Editar";
               this.butao = configurarButaoEnvio(cor, cor_fundo, tamanho_fonte, fonte, radius, posicaoX, posicaoY, largura, altura, title);
               painelPrincipal.getChildren().add(butao);
     } //fim do metodo estruturarButao

     /********************************************************************************************
     * Metodo: estruturarButao
     * Funcao: Cria e posiciona o botão principal de execução ("Editar") no painel.
     * Parametros: nenhum
     * Retorno: vazio
     ********************************************************************************************/
     private void setAcaoButaoEditar() {
            butao.setOnMouseEntered(e -> {
                   costumizar(
                      "lightblue", "black", "12", "Arial", "6", butao  
                   );
            }); 

            butao.setOnMouseExited(e -> {
                    costumizar(
                         "#C0C0C0", "black", "12", "ARIAL", "6",  butao
                    );

            });

            butao.setOnMousePressed(e -> {
                   costumizar(
                         "#C0C0C0", "black", "12", "ARIAL", "6",  butao
                   );
                   
            });
            
            butao.setOnMouseReleased(e -> {
                   costumizar(
                         "#C0C0C0", "black", "12", "ARIAL", "6",  butao
                   );
            });
     }  //fim do metodo setAcaoButaoEditar

     /********************************************************************************************
     * Metodo: configurarButaoEnvio
     * Funcao: cria um botao com propriedades visuais customizadas
     * Parametros: propriedades de estilo e dimensoes
     * Retorno: objeto Button configurado
     ********************************************************************************************/
    private Button configurarButaoEnvio(String cor, String cor_fundo, String tamanho_fonte, String fonte, String radius, double x, double y, double largura, double altura, String title) {
          Button butao = new Button(title);
          butao.setLayoutX(x); butao.setLayoutY(y);
          butao.setPrefSize(largura, altura);
          butao.setStyle(
               "-fx-background-color: " + cor + ";" +
               "-fx-text-fill: " +  cor_fundo +  ";" +
               "-fx-font-size: " + tamanho_fonte + ";" +
               "-fx-font-family: " + fonte + ";" +
               "-fx-background-radius: " + radius + ";"  
          );
          return butao;
    }//fim do metodo configurarButaoEnvio

    /********************************************************************************************
     * Metodo: costumizar
     * Funcao: aplica estilo customizado a um botao
     * Parametros: propriedades visuais e botao alvo
     * Retorno: vazio
     ********************************************************************************************/     
    private void costumizar(String x, String y, String z, String w, String r, Button k) {
             k.setStyle(
                      "-fx-background-color: " + x + ";" +
                      "-fx-text-fill: " +  y +  ";" +
                      "-fx-font-size: " + z + ";" +
                      "-fx-font-family: " + w + ";" +
                      "-fx-background-radius: " + r + ";"  
            );
    }//fim do metodo costumizar
}