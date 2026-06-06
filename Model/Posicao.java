package Model;

public class Posicao {
     private double x; private double y;
     private Operadores operar;
     
     public Posicao(double x, double y) {
            this.x = x;
            this.y = y;
            this.operar = new Operadores();
     }
      
     public Posicao calcularNovaPosicao(double raio, double angulo) {
            return operar.rotacaoPolar(raio, angulo);
     }
     
     public double getX() {
           return x;
     }
     
     public double getY() {
           return y;
     }
}

