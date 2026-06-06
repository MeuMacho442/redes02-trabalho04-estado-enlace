/************************************************************************************************
*Autor............: Gustavo Henrique Oliveira Fernandes
*Matricula........: 202410104
*Inicio...........: 29/08/2026
*Ultima alteracao.: 29/08/2026
*Nome.............: ArestaView
*Funcao...........: representar visualmente uma aresta de um grafo por meio de uma linha
*                   definida por coordenadas iniciais e finais
**************************************************************************************************/

package View;

import javafx.scene.shape.Line;
import javafx.scene.paint.Color;
import javafx.scene.Node;
public class ArestaView {

     private double X0, Y0;
     private double X, Y;
     private int id1, id2; //identifica uma referencia com o id dos roteadores associados
     private Line linha;
     private Line hitbox; 
     private Node ptr;
     /********************************************************************************************
     * Metodo: ArestaView (construtor)
     * Funcao: inicializa a estrutura da aresta visual
     * Parametros: nenhum
     * Retorno: nenhum
     ********************************************************************************************/
     public ArestaView() {
     }

     /********************************************************************************************
     * Metodo: criarAresta
     * Funcao: instancia uma linha representando uma aresta entre dois pontos no plano
     * Parametros: X0, Y0 - coordenadas iniciais
     *             X, Y   - coordenadas finais
     * Retorno: objeto Line representando a aresta
     ********************************************************************************************/
     public Line criarAresta(double X0, double Y0, double X, double Y) {
            linha = new Line(X0, Y0, X, Y);
            linha.toFront();  
            linha.setStroke(Color.BLACK);
            linha.setStyle("-fx-stroke-width: 3.4px;");
            hitbox = new Line(X0, Y0, X, Y);
            hitbox.setStrokeWidth(35);
            hitbox.setOpacity(0);     
            return linha;
     } //fim do metodo criarAresta
     
     public void criarAresta(double X0, double Y0, double X, double Y, String cor) {
            linha = new Line(X0, Y0, X, Y);
            Line hitbox = new Line(X0, Y0, X, Y);
            hitbox.setStrokeWidth(35);
             
            linha.setStyle("-fx-stroke: " + cor + ";" + " -fx-stroke-width: 3px;");
            hitbox.setStyle("-fx-stroke: " + "RED" + ";" + " -fx-stroke-width: 100px;");
     }
     
     public void mudarEstadoLinha() {
            linha.setStyle("-fx-stroke-width: 5.6px;");
     }

     
     
     public void retornarEstado() {
          linha.setStyle("-fx-stroke-width: 3.4px;");
     }

     public void setPtr(Node ptr) {
           this.ptr = ptr;
     }

     public Node getPtr() {
         return ptr;
     }

     public void setStroke() {
         linha.setStyle("-fx-stroke-width: 3px;");
     }
     
     public void setOpacidade(double valor) {
        linha.setOpacity(valor);
        hitbox.setOpacity(0);
     }
     
     public Line obterArestaView() {
          return  linha;
     }

     public Line obterArestaViewLine() {
          return  hitbox;
     }

     public void definirId(int id1, int id2) {
                this.id1 = id1;
                this.id2 = id2;
     }

     public int getId1() {
         return id1;
     }

     public int getId2() {
         return  id2;
     } 
}
