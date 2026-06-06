package Controller;

import View.Janela;

public class EmitirAvisoTela {
       private Janela janela;
       public  EmitirAvisoTela(Janela janela) {
              this.janela = janela;
       }

       public void emitirAvisoTela() {
             janela.emitirAlerta("Erro, nao existe um caminho entre os nodes expecificados");
             janela.rabilitarButao();
       } 
}