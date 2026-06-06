package Model;

public class ListaDinamica<T> {
    private T[] vetor;
    private int tamanho;
    private int tamanhoVetor;
 
    @SuppressWarnings("unchecked")
    public ListaDinamica() {
       this.vetor = (T[]) new Object[1];
       this.tamanhoVetor = 1;
       this.tamanho = 0;
    }

    @SuppressWarnings("unchecked")
    public void add(T valor) {
         if(tamanho >= tamanhoVetor) {
           tamanhoVetor = 2*tamanhoVetor;
           T[] novoVetor = (T[]) new Object[tamanhoVetor];
           for(int i = 0; i < tamanhoVetor/2; i++) {
              novoVetor[i] = vetor[i];
           }
           this.vetor = novoVetor; 
         }
         vetor[tamanho] = valor;
         tamanho++;
    }

    public T get(int i) {
       return vetor[i];
    }

    public int getTamanho() {
       return tamanho;
    }

}