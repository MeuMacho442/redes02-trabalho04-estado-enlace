package View;


public class ViewLabel {
    private double x0, x, y0, y;
    private int pesoDireito; private int pesoEsquerdo;
    private javafx.scene.layout.StackPane nodeStackPane;
    public ViewLabel(double x0, double x, double y0, double y, javafx.scene.layout.StackPane nodeStackPane) {
          this.x0 = x0; this.x = x; this.y0 = y0; this.y = y;
          this.nodeStackPane = nodeStackPane; 
    }   
    public void setPesoDireito(int pesoDireito) { this.pesoDireito = pesoDireito; }
    public void setPesoEsquerdo(int pesoEsquerdo) { this.pesoEsquerdo = pesoEsquerdo; } 
    public double getX0() { return x0; }
    public double getX() { return x; }
    public double getY0() { return y0; }
    public double getY() { return y; }
    public void alocarPesoLabel() {
             String texto = pesoDireito + ";" + pesoEsquerdo;   
            javafx.application.Platform.runLater(() -> { 
                  ((javafx.scene.control.Label) nodeStackPane.getChildren().get(0)).setText(texto);
             });
             //return nodeStackPane;
    }
    public javafx.scene.layout.StackPane getPaneStack() { return nodeStackPane; }
} 
