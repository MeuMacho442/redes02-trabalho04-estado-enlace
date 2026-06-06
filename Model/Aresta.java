package Model;

public class Aresta<T,K> {
     int peso;
     Node<T, K> node;
     private int bitFlag;
     public Aresta(Node<T, K> node, int peso, int bitFlag) {
          this.peso = peso;
          this.node = node;
          this.bitFlag = bitFlag;
     }
     public Aresta(Node<T, K> node, int peso) {
          this.peso = peso;
          this.node = node;
     }
     
     public int getBitFlag() {
          return bitFlag;
     }

     public Node<T, K> getNode() {
        return node;
     }
     
     public int getPeso() {
        return peso;
     }
     
     public void setPeso(int valor) {
         this.peso = valor;
     }
}
