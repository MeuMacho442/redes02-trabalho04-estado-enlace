package Model;

//import Model.

public class Operadores {

   
   private static final double π = 3.14159265358979323846264338327950288;


   public double[][] rotacionarPosicao(double x, double y, double angulo) {
        double[][] Matriz = new double[2][1];
        double[][] MatrizRotacao = new double[2][2];
        Matriz[0][0] = x; Matriz[1][0] = y;
        MatrizRotacao[0][0] = calcularCosseno(angulo); MatrizRotacao[0][1] = (-1)*calcularSeno(angulo);
        MatrizRotacao[1][0] = calcularSeno(angulo); MatrizRotacao[1][1] = calcularCosseno(angulo);
        
        return MultiplicarMatrizes(MatrizRotacao, Matriz);
   }
   
   public Posicao rotacaoPolar(double raio, double angulo) {
        
         return new Posicao(raio*calcularCosseno(angulo), raio*calcularSeno(angulo));
   }
//(0-k(y))/(t-y) = 2y => -k(y) = 2.t.y-2y^2 => t.(2y) = (2y^2-k(y)) => t = (2y^2-k(y))/2y
   public double calcularRaiz(double entrada) {
        funcaoAltaOrdem f = x -> {
              double a =  entrada;
              return x*x-a;
        };
        double y = entrada+1; int controlerParada = 10000;
        while(f.funcao(y) > 1e-14 && controlerParada > 0) {
           controlerParada--;
            y = (2*y*y-f.funcao(y))/(2*y);
        } 
        return y;
   }
   
   public double modulo(double valor) {
        if(valor < 0) {
            return (-1)*valor;
        }
        
        return valor;
   }
   
   public double calcularSeno(double angulo) {
          angulo = angulo%360;
          if(angulo <= 90 && angulo >= 0) {
             return calcularTaylor(angulo*π/180);
          } else if(angulo > 90 && angulo <= 180) {
             return calcularTaylor((180-angulo)*π/180);
          } else if(angulo > 180 && angulo <= 270) {
             return (-1)*calcularTaylor((angulo-180)*π/180);
          } else if(angulo > 270) {
             return (-1)*calcularTaylor((360-angulo)*π/180);
          }
          return (-1)*calcularSeno(angulo*(-1));
   }
   
   public double calcularCosseno(double angulo) {
          return calcularSeno(90-angulo);
   }
   
   public double calcularTaylor(double angulo) {
          return auxiliar(0, 10, angulo, 0);
   }
   
   private double auxiliar(double acumulador, int precisao, double angulo, int termo) {
            if(precisao == 0) {
                return acumulador;
            }
            return auxiliar(acumulador+potencia(-1, termo)*(potencia(angulo, 2*termo+1))/fatorial(2*termo+1), precisao-1, angulo, termo+1);
   }
   
   public double potencia(double valor, int indice) {
         if(indice == 0) {
             return 1;
         }
         return valor*potencia(valor, indice-1);
   }
   
   public double fatorial(int termo) {
         if(termo == 0) {
            return 1;
         }
         if(termo == 1) {
            return 1;
         }
         return termo*fatorial(termo-1);
   }
   
   public double[][] MultiplicarMatrizes(double[][] X, double[][] Y) {
         double[][] res = new double[X.length][Y[0].length];
         for(int i = 0; i < X.length; i++) {
               for(int j = 0; j < Y[0].length; j++) {
                     res[i][j] = 0;
                     for(int k = 0; k < X[0].length; k++) {
                        res[i][j] += X[i][k]*Y[k][j];
                     }
               } 
         }
         
         return res;
   }
}
