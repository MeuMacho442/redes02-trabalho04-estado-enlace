package Model;

public class Fila<T> {
     private final int tamanho = 50;
     private T[] vetor;
     private int inicio = 0;
     private int topo = 0;
     private int quantidade = 0;
     
     @SuppressWarnings("unchecked")
     public Fila() {
           this.vetor = (T[]) (new Object[tamanho]); 
     }
     
     public void enfileirar(T valor) throws Exception {
          if(quantidade >= tamanho) {
             throw new Exception("fila cheia");
          }
          vetor[topo] = valor;
          quantidade++;
          topo = (topo+1)%tamanho;
     }

     public T desenfileirar() throws Exception {
          if(quantidade == 0) {
              throw new Exception("fila vazia");
          }
          quantidade--;
          inicio = (inicio+1)%tamanho;
          return vetor[(inicio-1+tamanho)%tamanho];
     }
     
    public int getTamanhoMaximo() {
        return tamanho;
    }

     public boolean vazia() {
           return (quantidade == 0);
     }

     public boolean cheia() {
            return quantidade == tamanho;
     }
     
     public int getTamanho() {
         return quantidade;
     }
     
     
     
}
