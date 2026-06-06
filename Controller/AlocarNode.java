/************************************************************************************************
*Autor............: Gustavo Henrique Oliveira Fernandes
*Matricula........: 202410104
*Inicio...........: 29/08/2026
*Ultima alteracao.: 29/08/2026
*Nome.............: AlocarNode
*Funcao...........: construir a estrutura da rede a partir de um arquivo, criando os vertices
*                   e arestas do grafo e suas respectivas representacoes visuais
**************************************************************************************************/

package Controller;

import View.EstruturarSubRede;
import View.Janela;
import Model.Node;
import Model.Roteador;
import Model.Grafo;
import Model.LerArquivo;
import Model.Posicao;// R = {(x,y) / x em lR e y em lR}


public class AlocarNode {
     private EstruturarSubRede subRede;
     private Grafo<Roteador, Posicao> grafo;
     private Janela janela;
     
     /********************************************************************************************
     * Metodo: AlocarNode (construtor)
     * Funcao: inicializa o componente responsavel pela construcao da sub-rede
     * Parametros: janela - referencia da interface principal
     * Retorno: nenhum
     ********************************************************************************************/
     public AlocarNode(Janela janela) {
          this.janela = janela;
          this.subRede = new EstruturarSubRede(janela);
     }

     /********************************************************************************************
     * Metodo: setAresta
     * Funcao: cria as arestas do grafo a partir dos dados do arquivo e as representa
     *         visualmente no painel
     * Parametros: nenhum
     * Retorno: vazio
     ********************************************************************************************/ 
     public void setNode() {
           LerArquivo arquivoSubRede = new LerArquivo("backbone.txt");
           int tamanho = arquivoSubRede.extrairNumeroDocumentoSubrede(0, 0);
           double raioDaTopologia = 200; 
           grafo = new Grafo<>(tamanho);
           subRede.alocarConjuntoNodeView(tamanho);
           janela.inicializarTabela(tamanho);
         try {  
             Roteador roteador;
             for(int i = 0; i < tamanho; i++) {
                   roteador = new Roteador(i);
                   roteador.setControllerDeMover(new ControllerDeMover(janela));
                   Node<Roteador, Posicao> node =  new Node(roteador, new Posicao(0,0).calcularNovaPosicao(raioDaTopologia, (360.0/(double)tamanho)*i));
                   grafo.inserirVertice(node);
                   addNode((node.getSegundoValor()).getX(), (node.getSegundoValor()).getY(), (i+1), node);
             }
         } catch(Exception ex) {
               ex.printStackTrace();
         }
     }

     /********************************************************************************************
     * Metodo: addNode
     * Funcao: delega a criacao da representacao visual de um vertice
     * Parametros: x, y - coordenadas do vertice
     *             valor - identificador do node
     * Retorno: vazio
     ********************************************************************************************/
     public void setAresta() throws ExcessaoIncosistenciaArquivo {
         LerArquivo arquivoSubRede = new LerArquivo("backbone.txt");
         int tamanho = arquivoSubRede.tamanhoArquivo();
         for(int i = 1; i < tamanho; i++) {//comecamos por um porquer as informacoes relevantes para estruturar o grafo esta no arquivo backbone.txt
            
            Node<Roteador, Posicao> w = grafo.getNode(arquivoSubRede.extrairNumeroDocumentoSubrede(i, 0)-1);
            Node<Roteador, Posicao> r = grafo.getNode(arquivoSubRede.extrairNumeroDocumentoSubrede(i, 1)-1);
            int peso1 = Integer.MAX_VALUE;
            int peso2 = Integer.MAX_VALUE;
            if(w == null || r == null) {
                throw new ExcessaoIncosistenciaArquivo("Arquivo Inconsistente");
            }
            addAresta(w,r, peso1, peso2);
         }
     }
     
     /********************************************************************************************
     * Metodo: addNode
     * Funcao: delega a criacao da representacao visual de um vertice
     * Parametros: x, y - coordenadas do vertice
     *             valor - identificador do node
     * Retorno: vazio
     ********************************************************************************************/ 
     private void addNode(double x, double y, Integer valor, Node<Roteador, Posicao> node) {
         //subRede.adicionarNodeView(node.getChave());
         subRede.alocarNodeView(valor, x, y, node.getChave());
     } 
     
     /********************************************************************************************
     * Metodo: addAresta
     * Funcao: adiciona uma aresta ao grafo e cria sua representacao visual
     * Parametros: w, r - vertices conectados
     *             peso - peso da aresta
     * Retorno: vazio
     ********************************************************************************************/
     private void addAresta(Node<Roteador, Posicao> w, Node<Roteador, Posicao> r, int peso1, int peso2) {
         try {   
            grafo.inserirAresta(w,r,peso1, 0); //a relacao de sentido w -> r, f(w,r) = 0  
            grafo.inserirAresta(r,w,peso2, 1); //a relacao de sentido r -> w, dado que f(w,r) = 0 => f(r,w)
            subRede.alocarArestaView(peso1, peso2, w.getChave(), r.getChave(), (w.getSegundoValor()).getX(), (w.getSegundoValor()).getY(), (r.getSegundoValor()).getX(), (r.getSegundoValor()).getY());
         } catch(Exception ex) {
            ex.printStackTrace();
         } 
     } //fim do metodo addAresta

     public EstruturarSubRede getSubRede() {
           return subRede;
     }
     
     /********************************************************************************************
     * Metodo: getGrafo
     * Funcao: retorna a estrutura do grafo construida
     * Parametros: nenhum
     * Retorno: objeto Grafo
     ********************************************************************************************/
     public Grafo<Roteador, Posicao> getGrafo() {
          return grafo;
     } //fim do metodo getGrafo
}
