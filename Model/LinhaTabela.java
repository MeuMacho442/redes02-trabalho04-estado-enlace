package Model;

public class LinhaTabela {
    private Integer Entrada; 
    private Integer Saida;
    private Double custo;
    private static final int tempo_limite = 550;
    private Integer tempo_vida;
   
    
    public LinhaTabela() {
           this.tempo_vida = tempo_limite;
    }


    
    public void setCusto(Double custo) {
          this.custo = custo;
    }  
    public void setSaida(Integer Saida) {
          this.Saida = Saida;
    }  
    public void setEntrada(Integer Entrada) {
          this.Entrada = Entrada;
    }

    public boolean validade() { 
           return tempo_vida > 0;
    } 

    public void renovarTempoVida() {
         this.tempo_vida = tempo_limite;
    }  
    
    public void decrementarTempoVida() {
         tempo_vida--;
    }  

    public Double getCusto() {
         return custo;
    }

    public Integer getSaida() {
         return Saida;
    }
   
    public Integer getEntrada() {
        return Entrada;
    }
}
