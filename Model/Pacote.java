/************************************************************************************************
*Autor............: Gustavo Henrique Oliveira Fernandes
*Matricula........: 202410104
*Inicio...........: 29/08/2026
*Ultima alteracao.: 29/08/2026
*Nome.............: Pacote
*Funcao...........: representar um pacote trafegando na rede, contendo informacoes sobre
*                   emissor, receptor, roteador anterior e controle de tempo de vida (TTL)
**************************************************************************************************/


package Model;


public class Pacote {
     private Roteador emissor;
     private Roteador receptor;
     private int id_emissor; 
     private int id_receptor;
     private Roteador roteadorAnterior;
     private int numero_sequencia;
     private ListaDupla<Node<Roteador, Posicao>> listaCaminhos; 
     private TabelaEstadoEnlace.InformacaoEnlace tabelaEnlace;
     private int TTL= 5;
     class TipoPacote {
           public static final int  ping_gerado = 0, HELLO = 1, ECHO_ANSWER = 4, mensagem = 5, HELLO_ANSWER = 2, ECHO = 3, LINK_PACKAGE = 6, LINK_PACKAGE_SEND = 7; 
           public int tipoAtual;  
     }  
     TipoPacote tipoPacote; 
     private int custo;
     private TabelaDistancia tabela; 
     /********************************************************************************************
     * Metodo: Pacote (construtor)
     * Funcao: inicializa o pacote com valores padrao
     * Parametros: nenhum
     * Retorno: nenhum
     ********************************************************************************************/
     public Pacote(int tipoPacote) {
          this.tipoPacote = new TipoPacote();
          this.tipoPacote.tipoAtual = tipoPacote;
     }

     public void definirIdemissorReceptor(int id_emissor, int id_receptor) {
           this.id_emissor = id_emissor; this.id_receptor = id_receptor;
     }
     public TipoPacote definirTipo(int tipoPacote) {
          this.tipoPacote.tipoAtual = tipoPacote;
          return this.tipoPacote;
     }
     
     public void setCaminho(ListaDupla<Node<Roteador, Posicao>> listaCaminhos) {
         this.listaCaminhos = listaCaminhos;
     }
     
     public Node<Roteador, Posicao> obterProximoHop() {
            listaCaminhos.retirar(); //retira o hop atual, estamos interessados no proximo
            Node<Roteador, Posicao> ProximoHop = listaCaminhos.getHeadValue(); //obtem o proximo hop;
            
          return ProximoHop;
     }

     public void setRoteadorOrigem(Roteador roteador) {
          this.emissor = roteador;
     } 

     public void setTabela(TabelaDistancia tabela) {
            this.tabela = tabela;
     }
     
     public TabelaDistancia getTabela() {
           return tabela;
     }
     
     public void setInformacaoVizinhos(TabelaEstadoEnlace.InformacaoEnlace tabelaEnlace) {
         this.tabelaEnlace = tabelaEnlace;
     }
     
     public TabelaEstadoEnlace.InformacaoEnlace obterInformacaoVizinhos() {
         return tabelaEnlace;
     }
     /********************************************************************************************
     * Metodo: setRoteador
     * Funcao: define o roteador anterior pelo qual o pacote passou
     * Parametros: roteador - ultimo roteador visitado
     * Retorno: vazio
     ********************************************************************************************/
     public void setRoteador(Roteador roteador) {
         this.roteadorAnterior = roteador;
     }  

     /********************************************************************************************
     * Metodo: setRoteador
     * Funcao: define o roteador anterior pelo qual o pacote passou
     * Parametros: roteador - ultimo roteador visitado
     * Retorno: vazio
     ********************************************************************************************/
     public void setTTL(int x) {
         this.TTL = x;
     }
     
     /********************************************************************************************
     * Metodo: getEmissor
     * Funcao: retorna o roteador emissor do pacote
     * Parametros: nenhum
     * Retorno: objeto Roteador
     ********************************************************************************************/
     public Roteador getEmissor() {
          return emissor;
     }
     public void setNumeroSequencia(int numero_sequencia) {
          this.numero_sequencia = numero_sequencia;
     }
     /********************************************************************************************
     * Metodo: getRecptor
     * Funcao: retorna o roteador receptor do pacote
     * Parametros: nenhum
     * Retorno: objeto Roteador
     ********************************************************************************************/
     public Roteador getRecptor() {
          return receptor;
     }
       
     /********************************************************************************************
     * Metodo: decrementarTTL
     * Funcao: reduz o valor do TTL em uma unidade
     * Parametros: nenhum
     * Retorno: vazio
     ********************************************************************************************/
     public void decrementarTTL() {
             this.TTL--;
     } 
      
     /********************************************************************************************
     * Metodo: roteadorAnterior
     * Funcao: retorna o ultimo roteador visitado pelo pacote
     * Parametros: nenhum
     * Retorno: objeto Roteador
     ********************************************************************************************/ 
     public Roteador roteadorAnterior() {
         return this.roteadorAnterior;
     }
     
     public void definirTTL(int TTL) {
           this.TTL = TTL;
     }

     /********************************************************************************************
     * Metodo: getTTL
     * Funcao: retorna o valor atual do TTL do pacote
     * Parametros: nenhum
     * Retorno: inteiro
     ********************************************************************************************/
     public int getTTL() {
            return this.TTL;
     }

     public void renovarTTL() {
           this.TTL = 5;
     }

     public void definirCusto(int custo) {
          this.custo = custo;
     }
    
     public int obterCusto() {
          return custo;
     }
     public int getPacoteSequencia() {
          return numero_sequencia;
     }
     /********************************************************************************************
     * Metodo: getTipo
     * Funcao: determinar qual tipo de pacote esta sendo processado duarante o processamento do roteamento
     * Parametros: nenhum
     * Retorno: enum
     ********************************************************************************************/
     public TipoPacote getTipo() {
          return tipoPacote;
     } 

     public void setTipoRoteador(TipoPacote tipoPacote) {
           this.tipoPacote = tipoPacote; 
     }
     
     public int getIdEmissor() {
         return id_emissor;
     }
     
     public int getIdRecptor() {
        return id_receptor;
     }  
}
