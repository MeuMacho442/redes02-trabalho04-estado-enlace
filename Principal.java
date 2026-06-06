/************************************************************************************************
*Autor............: Gustavo Henrique Oliveira Fernandes
*Matricula........: 202410104
*Inicio...........: 29/03/2026
*Ultima alteracao.: 29/03/2025 //descolhecido
*Nome.............: Principal
*Funcao...........: instanciar a classe Janela e o Controler. Fundamentais para, respectivamente, 
                      - construir a interface de iteracao com o usuario
                      - Comunicar a classe do diretorio View com o diretorio Model 
**************************************************************************************************/

import javafx.application.Application;
import javafx.stage.Stage;
import View.Janela;
import Controller.Controller;

public class Principal extends Application {
    /********************************************************************************************
    * Método: start
    * Função: Estabelece a cena principal, inicializa a instancia da classe Controle, permitindo 
    *          uma coesao entre a interface de iteracao com o usuario e a logica de processamento de dados
    * Parâmetros: primaryStage - instancia da classe Stage, relizando uma passagem por ponteiro para manipula-la
    *             diretamente da classe janela
    * Retorno: vazio  
    ********************************************************************************************/
     @Override
     public void start(Stage primaryStage) {
            Janela janela = new Janela(primaryStage);
            (new Controller(janela)).iniciar();
     } 
      
    /********************************************************************************************
    * Método: main
    * Função: Estrutura todo o fluxo de execucao do projeto
    * Parâmetros: args
    * Retorno: vazio  
    ********************************************************************************************/

     public static void main(String[] args) {
            launch(args);
     }
}