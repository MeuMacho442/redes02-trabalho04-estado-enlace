package Model;

public class HashDinamico<T> {
     private NodeHash[] Tabela;
     private Operadores math;
     private int bitValue = 0;
     
     public HashDinamico() {
          math = new Operadores();
          Tabela = new NodeHash[(int)(math.potencia(2.0, bitValue))];
     }
     
     public void inserirNodeHash(NodeHash<T> valor) {
         NodeHash[] TabelaAnterior = Tabela;
          
         if(valornaoestarrepetido(TabelaAnterior, valor)) {
                 NodeHash[] novaTabela = null;
                 
                 while(verificarSeHaColisao(TabelaAnterior, valor)) {
                     novaTabela = new NodeHash[2*TabelaAnterior.length];
                     bitValue++;
                     novaTabela = preencherValores(novaTabela, TabelaAnterior);
                     TabelaAnterior = novaTabela;
                 }
                 Tabela = TabelaAnterior;
                 Tabela[valor.chave & (construirFator(bitValue))] = valor;
         } 
     }
     public boolean verificarSeValorExiste(int chave) {
             if(chave < Tabela.length && Tabela[chave & construirFator(bitValue)] != null && Tabela[chave & construirFator(bitValue)].chave == chave) {//essa funcao parti da heuristica de que dado um no X e Y, se X.chave = Y.chave => X = Y
                     return true;    
             }
            return false;
     }
     public NodeHash<T> obterNodeHash(int chave) {
         return Tabela[chave & (construirFator(bitValue))];
     }
     
     public String mostrarTabela() {
         String mensagem = "";
         for(int i = 0; i < Tabela.length; i++) {
            if(Tabela[i] != null) {
                mensagem += i + " (" + Tabela[i].chave + " -> " + String.valueOf(Tabela[i].valor) + ")\n";
            }  else {
                mensagem += i + " - > null\n";
            }
         }
         return mensagem;
     }
     
     private NodeHash[] preencherValores(NodeHash[] novaTabela, NodeHash[] TabelaAnterior) {
         
            for(int i = 0; i < TabelaAnterior.length; i++) {
                if(TabelaAnterior[i] != null) {
                  novaTabela[TabelaAnterior[i].chave & construirFator(bitValue)] = TabelaAnterior[i];
                }  
            }
            return novaTabela;
     }
     
     private boolean verificarSeHaColisao(NodeHash[] TabelaAnterior, NodeHash<T> valor) {
              int fatorDeslocamento = construirFator(bitValue);
              if(TabelaAnterior[valor.chave & fatorDeslocamento] != null || (Tabela[0] != null && TabelaAnterior.length == 1)) {//se esta diferente de null, entao existe um valor, portanto ha colisao
                      return true;
              }
              return false;
     }
     
     private boolean valornaoestarrepetido(NodeHash[] TabelaAnterior, NodeHash<T> valor) {//verifica se nao ha chave duplicada
                int fatorDeslocamento = construirFator(bitValue); 
                if(TabelaAnterior[valor.chave & fatorDeslocamento] != null && TabelaAnterior[valor.chave & fatorDeslocamento].chave == valor.chave) {
                    return false;
                }
                return true;
     }
     private int construirFator(int bitValue) {
           int soma = 0;
           for(int i = 0; i < bitValue; i++) {
               soma += 1<<i;
           }
           return soma;
     }
}
