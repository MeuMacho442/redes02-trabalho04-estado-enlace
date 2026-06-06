package Model;
import Controller.ControllerDeMover;

public class Mapeamento {
     private int valor;
     private int ptr = 0;
     interface funcaoAltaOrdem {
           public void funcao(Pacote pacoteParametro) throws Exception;
     }
     private funcaoAltaOrdem[] tabelaFuncao;
     public Mapeamento(int tamanho) {
          tabelaFuncao = new funcaoAltaOrdem[tamanho];
     }

     public void associarFuncaoPosicao(int ptrEntrada, funcaoAltaOrdem w) {
           if(ptrEntrada >= tabelaFuncao.length) {
               throw new RuntimeException("Valor nao encontrado."); 
           }
           tabelaFuncao[ptrEntrada] = w;
     }

    public funcaoAltaOrdem getFuncao(int i) {
          return tabelaFuncao[i];
    }
}