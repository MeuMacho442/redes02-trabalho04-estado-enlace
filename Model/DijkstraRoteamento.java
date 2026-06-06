/************************************************************************************************
*Autor............: Gustavo Henrique Oliveira Fernandes
*Matricula........: 202410104
*Inicio...........: 29/08/2026
*Ultima alteracao.: 29/08/2026
*Nome.............: ConfigurarPainel
*Funcao...........: Seja G um grafo ponderado nao orientado generico do tipo K e T, essa classe tem como objetivo aplicar o calculo do menor caminho entre 
                    a e b, para todo a e b no conjunto de vertices do grafo. Para isso, recorremos ao famoso algoritmo de dikstra, em que usa o conjunto de 
                    vertices rotulados entre descoberto e permanente. Assim, seja um vertice y marcado como permante, se dado o conjunto de vvertices nao 
                    permante, k com predecessor y ser marcado como permante, entao yk esta no caminho ideal entre a origem de y a este. 
               
**************************************************************************************************/

package Model; 



public class DijkstraRoteamento<T, K> {
    private Grafo<T, K> grafo;
    private Rotulo<T,K>[] conjuntoVerticesRotulados;
    private static final int INFINITO = Integer.MAX_VALUE;
    private AnimarDijkstra animar = null;
    /********************************************************************************************
     * Metodo: DijkstraRoteamento (Construtor)
     * Funcao: veja que esse construtor oferece um suporte para registrar o conjunto de vertices que foram marcados como permantes.
               Esse infomracao eh valiosa para podermos aplcarmos uma animcao. Assim temos o registro de passos que permitem a animacao
               do processo de execucao do algoritmo
     * Parametros: grafo - fundamental para conhecer o estado global da rede modelada, animar - registro fundamental para a animcao 
     ********************************************************************************************/
    @SuppressWarnings("unchecked")
    public DijkstraRoteamento(Grafo<T, K> grafo, AnimarDijkstra animar) {
        this.grafo = grafo;
        this.animar = animar; 
        this.conjuntoVerticesRotulados =  new Rotulo[grafo.getTamanho()];
    }//fim do contrutor DijkstraRoteamento
  
    /********************************************************************************************
     * Metodo: DijkstraRoteamento (Construtor)
     * Funcao: Veja que nesse consstrutor nao inicializamos a variavel delegada a fazer registro de execucao. Isso porque
               talvez nem todo mundo que use esse arquivo peça para estruturar uma animacao. Isso garnti a portabilidade desse arquivo
               para qualquer sistema, ja que seu objetivoprincipal eh determinar o menor caminho entre vertices a -> b, para todo a e b no conjunto de
               vertices do grafo 
     * Parametros: grafo - fundamental para conhecer o estado global da rede modelada, animar - registro fundamental para a animcao 
     ********************************************************************************************/
    @SuppressWarnings("unchecked")
    public DijkstraRoteamento(Grafo<T, K> grafo) {
        this.grafo = grafo;
        this.conjuntoVerticesRotulados =  new Rotulo[grafo.getTamanho()];
    }//fim do contrutor DijkstraRoteamento

    
    /********************************************************************************************
     * Metodo: DijkstraRoteamento (Construtor)
     * Funcao: Coracao do arquivo, pretende tomar dois vertices x e y no conjunto de vertices do grafo e aplicar o princippio determinado acima.
               Essa funcao aplica o algoritmo de dijkstra, tornando o vertice inicial x como permantente e descobrindo cada caminho mais curto para 
               cada vertice z no grafo se, e somente se, z eh alcanzavel pelo vertice incial x, no grafo. A condicao de parada eh quando z eh igual a 
               y, o que significa de que y eh o menor vertice alcancavel por x. Para isso, marcamos cada vertice como permanete, significando que 
               descobrimos a menor rota de x ate ao vertice maracado como permante. Para isso precimos usuafruir das propriedades da estrutura de dados
               para pecorremos cada adjacencia da linha de saida para o vertice ativo. Usamos a abordagem do algoritmo gancnioso, seja k com a distancia
               de descoberta estabelecida como r, se um vertice o descobre com um distancia i, tal que i < distancia atual, entao distancia sera atualizada. 
               Para informacao mais detalahada, de uma folheado no livro de Tanebaum :-)  
     * Parametros: Node<T, K> X, Node<T, K> Y 
     * retorno:  ListaDupla<Node<T, K>>
     ********************************************************************************************/
    public ListaDupla<Node<T, K>> menorCaminho(Node<T, K> X, Node<T, K> Y) throws CaminhoMinimoNaoExistenteException, Exception  {//a comparacao dos nodes se dara por referencia, ou seja: eh verdade que, dado o node A e o node B, A = B <=> A e B apontam para o mesmo espaco de memoria
        inicializarConjuntoRotulo();
        conjuntoVerticesRotulados[X.getChave()].setEstado(); 
        conjuntoVerticesRotulados[X.getChave()].setDistancia(0);
        Rotulo<T,K> RotuloPermanteAtual = conjuntoVerticesRotulados[X.getChave()];
        registrarPassos(RotuloPermanteAtual.obterVertice());
        while(RotuloPermanteAtual.obterVertice() != Y) {
           for(ListaDupla.Element ptrNode = grafo.getAdjacencia(RotuloPermanteAtual.obterVertice().getChave()).getHead(); ptrNode != null; ptrNode = ptrNode.getNext()) {//itera sobre a adjacencia do grafo
               if(conjuntoVerticesRotulados[((Aresta<T,K>)(ptrNode.getValor())).getNode().getChave()].getEstadoRotulo() != Rotulo.RotuloVertice.PERMANENTE && conjuntoVerticesRotulados[((Aresta<T,K>)(ptrNode.getValor())).getNode().getChave()].getDistancia() > RotuloPermanteAtual.getDistancia() + ((Aresta<T,K>)(ptrNode.getValor())).getPeso()) {
                   conjuntoVerticesRotulados[((Aresta<T,K>)(ptrNode.getValor())).getNode().getChave()].setDistancia(RotuloPermanteAtual.getDistancia() + ((Aresta<T,K>)(ptrNode.getValor())).getPeso());
                   conjuntoVerticesRotulados[((Aresta<T,K>)(ptrNode.getValor())).getNode().getChave()].definirPredecessor(RotuloPermanteAtual.obterVertice());
               } 
           }
           
           Node<T,K> ptrRotulado = obterMenorDistanciaRotulo();
           if(ptrRotulado == null) {
              throw new CaminhoMinimoNaoExistenteException("caminho inexistente");
           }
           RotuloPermanteAtual =  conjuntoVerticesRotulados[ptrRotulado.getChave()];
           RotuloPermanteAtual.setEstado(); //marca o estado de menor distancia como permanentes
           registrarPassos(RotuloPermanteAtual.obterVertice());
          
           
        } 
        estruturarCaminhoAnimacao(RotuloPermanteAtual);
        return computarCaminho(RotuloPermanteAtual);
    }//fim do metodo menorCaminho


    /********************************************************************************************
     * Metodo: DijkstraRoteamento (Construtor)
     * Funcao:   
     * Parametros: Node<T, K> X, Node<T, K> Y 
     * retorno:  ListaDupla<Node<T, K>>
     ********************************************************************************************/
    public Rotulo<T,K>[] gerarArvoreCaminhoMinimo(Node<T, K> X) throws Exception {//a comparacao dos nodes se dara por referencia, ou seja: eh verdade que, dado o node A e o node B, A = B <=> A e B apontam para o mesmo espaco de memoria
        int numeroRotulosPermanente = 0;
        inicializarConjuntoRotulo();
        
        conjuntoVerticesRotulados[X.getChave()].setEstado(); 
        conjuntoVerticesRotulados[X.getChave()].setDistancia(0);
        Rotulo<T,K> RotuloPermanteAtual = conjuntoVerticesRotulados[X.getChave()];
        numeroRotulosPermanente++;
        registrarPassos(RotuloPermanteAtual.obterVertice());
        while(numeroRotulosPermanente != grafo.getTamanho()) {
           for(ListaDupla.Element ptrNode = grafo.getAdjacencia(RotuloPermanteAtual.obterVertice().getChave()).getHead(); ptrNode != null; ptrNode = ptrNode.getNext()) {//itera sobre a adjacencia do grafo
               if(conjuntoVerticesRotulados[((Aresta<T,K>)(ptrNode.getValor())).getNode().getChave()].getEstadoRotulo() != Rotulo.RotuloVertice.PERMANENTE && conjuntoVerticesRotulados[((Aresta<T,K>)(ptrNode.getValor())).getNode().getChave()].getDistancia() > RotuloPermanteAtual.getDistancia() + ((Aresta<T,K>)(ptrNode.getValor())).getPeso()) {
                   conjuntoVerticesRotulados[((Aresta<T,K>)(ptrNode.getValor())).getNode().getChave()].setDistancia(RotuloPermanteAtual.getDistancia() + ((Aresta<T,K>)(ptrNode.getValor())).getPeso());
                   conjuntoVerticesRotulados[((Aresta<T,K>)(ptrNode.getValor())).getNode().getChave()].definirPredecessor(RotuloPermanteAtual.obterVertice());
               } 
           }
           
           Node<T,K> ptrRotulado = obterMenorDistanciaRotulo();
           RotuloPermanteAtual =  conjuntoVerticesRotulados[ptrRotulado.getChave()];
           RotuloPermanteAtual.setEstado(); //marca o estado de menor distancia como permanentes
           numeroRotulosPermanente++;
           registrarPassos(RotuloPermanteAtual.obterVertice());
        } 
       
        return conjuntoVerticesRotulados;
    }//fim do metodo menorCaminho 
     
    
    /********************************************************************************************
     * Metodo: estruturarCaminhoAnimacao
     * Funcao: registra o conjunto de passos para animacao se, e somente se, o construtor for do tipo
               de animacao. Caso contrario, a instancia sera nulo, i.e, sem local de memoria que a represente, e nada sera feito  
     * Parametros: Rotulo Y 
     ********************************************************************************************/

    private void estruturarCaminhoAnimacao(Rotulo Y) {
            if(animar != null) {
                 animar.estruturarCaminho(Y, conjuntoVerticesRotulados);
            }
    }//fim do metodo estruturarCaminhoAnimacao

    /********************************************************************************************
     * Metodo: obterMenorDistanciaRotulo
     * Funcao: obtem o rotulo de menor distancia. Veja que usamos a maior apacidade de representacao computacional de infinito
               nos inteiros limitados a representacao na estrutura de arquitertura de 32 bits do hardware. Seja o bit de sinal final
               setado como zero, entao infinito tem o valor de 2^32-1. Veja que usamos uma busca linear para o menor destino. 
               No entanto, poderiamos usarmos uma arvore heap, para representramos um fila de prioridde, so que nao fiz nisso
               porque nao pensei nisto :-)  
     * Parametros: vazio
     * retorno: Node<T,K> 
     ********************************************************************************************/
    private Node<T, K> obterMenorDistanciaRotulo() {
            Node<T, K> nodeMenorRotulo = null;
            int menorDistancia = INFINITO;
            Node<T, K> nodeMenorRotuloptrOverhead = null;
            for(int i = 0; i < grafo.getTamanho(); i++) {
                if(menorDistancia > conjuntoVerticesRotulados[i].getDistancia() && conjuntoVerticesRotulados[i].getEstadoRotulo() != Rotulo.RotuloVertice.PERMANENTE) {
                       nodeMenorRotulo = conjuntoVerticesRotulados[i].obterVertice();
                       menorDistancia = conjuntoVerticesRotulados[i].getDistancia();  
                } else if(conjuntoVerticesRotulados[i].getEstadoRotulo() != Rotulo.RotuloVertice.PERMANENTE) {
                       nodeMenorRotuloptrOverhead = conjuntoVerticesRotulados[i].obterVertice();
                }                  
            } 
            if(nodeMenorRotulo == null) {
                  return nodeMenorRotuloptrOverhead;     
            }
            return nodeMenorRotulo;
    }//fim do metodo obterMenorDistanciaRotulo

    
    /********************************************************************************************
    * Metodo: registrarPassos
    * Funcao: registra o conjunto de passos para animacao se, e somente se, o construtor for do tipo
               de animacao. Caso contrario, a instancia sera nulo, i.e, sem local de memoria que a represente, e nada sera feito  
    * Parametros: Node H
    * retorno: vazio 
    ********************************************************************************************/
    private void registrarPassos(Node H) {
         if(animar != null) {
                 animar.registrarPassos(H);
         }
    }//fim do metodo registrarPassos
   
    /********************************************************************************************
    * Metodo: computarCaminho
    * Funcao: estruturar caminho mediante ao rotulo final encontrado Y. Seja a estrutura a abaixo:
              Y | X, ie, Y predeceessor X, X | A, A | B, B | NULL, entao deduzir o caminho eh: 
              R(Y -> X -> A -> B) = B -> A -> X -> Y      
    * Parametros: Rotulo<T,K> Y
    * retorno: ListaDupla 
    ********************************************************************************************/
    private ListaDupla<Node<T,K>> computarCaminho(Rotulo<T,K> Y) {
           ListaDupla<Node<T,K>> listaCaminhos = new ListaDupla<>();
           listaCaminhos.inserirInicio(Y.obterVertice());
           while(Y.getPredecessor() != null) {    
                  Y = conjuntoVerticesRotulados[Y.getPredecessor().getChave()];
                  listaCaminhos.inserirInicio(Y.obterVertice());
           }
           return listaCaminhos;
    }//fim do metodo ListaDupla 

    /********************************************************************************************
    * Metodo:  inicializarConjuntoRotulo
    * Funcao: Dado o grafo original, temos que ele somente representaria o no logico sem informacoes adicionais.
              Essa funcao simplismente faz um conversao entre No -> rotulo. Cada no tem uma chave que varia de 0 a k-1, entao
              cada rotulo sera mapeado para uma posicao num conunto de acessoaleatorio na posicao y, para y correpondente a chave do vertice.
              Eh garantido que nao exista chave fora do intervalo acima, visto que cada chave eh determinada pela ordem de entrada de incremento linear.
              Portanto, dado um vertice u, podemos localizar seu rotulo associado a custo O(1) no conjuntoVerticesRotulados. Aumentando a eficiencia        
    * Parametros: vazio
    * retorno: vazio 
    ********************************************************************************************/
    private void inicializarConjuntoRotulo() {
       
        for(int i = 0; i < grafo.getTamanho(); i++) {
             Rotulo<T,K> verticeRotulado = new Rotulo((grafo.getConjuntoVertice())[i]);
             verticeRotulado.setDistancia(INFINITO);  
             conjuntoVerticesRotulados[(verticeRotulado.obterVertice()).getChave()] = verticeRotulado; //tabela de mapeamento node -> cahve de acesso. Permitindo um acesso constante ao vertice rotulado dado um Node adjacente no grafo    
        }
        
        
    }// fim do metodo inicializarConjuntoRotulo
}
