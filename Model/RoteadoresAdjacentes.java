package Model;


public class RoteadoresAdjacentes {
     private ListaDupla<Aresta<Roteador, Posicao>> interfaceSaida;
     private int[] listaPesos; 
     private int tamanhoGrafo;
     private ListaDupla.Element ponteiro;
     private HashDinamico<Integer> controlerNumeroSequencias;

     public RoteadoresAdjacentes(ListaDupla<Aresta<Roteador, Posicao>> interfaceSaida, int tamanhoGrafo) {
            this.tamanhoGrafo = tamanhoGrafo;
            this.interfaceSaida = interfaceSaida;
            this.listaPesos = new int[tamanhoGrafo];
            this.controlerNumeroSequencias = new HashDinamico<>();
            ponteiro = interfaceSaida.getHead();           
     }

     public boolean next() {
         
          if(ponteiro.getNext() != null) {
               ponteiro = ponteiro.getNext();
               return true; 
          } else {
               ponteiro = interfaceSaida.getHead();
               return false;
          } 
     }

     public void redefirnir() {
           ponteiro = interfaceSaida.getHead();
     }
     
       
     public int getBitFlagAresta(int ID) {
         for(ListaDupla.Element ptr = interfaceSaida.getHead(); ptr != null; ptr = ptr.getNext()) {
                if((((Aresta<Roteador, Posicao>)(ptr.getValor())).getNode()).getValor().getId_identificao() == ID) {
                    return ((Aresta<Roteador, Posicao>)(ptr.getValor())).getBitFlag(); 
                }
         }
         return -1;
     }

     public int contarNumeroInterfacesSaida() {
             int res = 0;
             for(ListaDupla.Element ptr = interfaceSaida.getHead(); ptr != null; ptr = ptr.getNext()) {
                res++;
             }
             return res;
     } 

     public void prencherPesosAdjacentes(int id_roteador, int peso) {//eh garantido que dado um vetor de n-posições, todo o valor de ide do roteador tera um valor variando entre 0 a n-1 posicoes 
           listaPesos[id_roteador] = peso;
     }  
     
     public Roteador getRoteadorAtual(int i) throws Exception {
           if(ponteiro == null) {
             // System.out.println(i);
              throw new Exception("Ponteiro atual nulo");
           }
           
           return (((Aresta<Roteador, Posicao>)(ponteiro.getValor())).getNode()).getValor();
     }

     public int getPesoAtual() {
           return listaPesos[(((Aresta<Roteador, Posicao>)(ponteiro.getValor())).getNode()).getValor().getId_identificao()];
     }
     
     public int getTamanhoGrafo() {
         return tamanhoGrafo;
     }

     public ListaDupla<Aresta<Roteador, Posicao>> getInterface() {
          return interfaceSaida;
     }

     public int tamanhoInterface() {
         return interfaceSaida.getComprimentoLista(); 
     } 
}
