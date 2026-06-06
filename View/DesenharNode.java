/************************************************************************************************
*Autor............: Gustavo Henrique Oliveira Fernandes
*Matricula........: 202410104
*Inicio...........: 29/08/2026
*Ultima alteracao.: 29/08/2026
*Nome.............: DesenharNode
*Funcao...........: construir a posicao centralizada na representacao dos visual dos Node que representam o vertice do grafo, enquanto instancia de abstracao de um roteador
**************************************************************************************************/


package View;

public class DesenharNode {
    private int quantidadeTotalNode = 0;
    private Janela janela;
    private double larguraJanela;
    private double alturaJanela;
     
    /********************************************************************************************
    * Metodo: DesenharNode (construtor)
    * Funcao: inicializa o objeto responsavel pela renderizacao dos nodes, obtendo dimensoes 
    *         da janela e quantidade total de elementos a serem manipulados
    * Parametros: janela - referencia da interface principal
    *             quantidadeTotalNode - quantidade total de nodes da rede
    * Retorno: nenhum
    ********************************************************************************************/
    public DesenharNode(Janela janela, int quantidadeTotalNode) {
        this.janela = janela;
        this.larguraJanela = janela.getLargura();
        this.alturaJanela = janela.getAltura();
        this.quantidadeTotalNode = quantidadeTotalNode;
    }//fim do metodo DesenharNode
    
    /********************************************************************************************
    * Metodo: alocarNode
    * Funcao: posiciona um node na interface grafica com base em coordenadas relativas ao centro
    *         da janela, realizando o deslocamento para coordenadas absolutas e adicionando o 
    *         vertice ao painel direito
    * Parametros: node - representacao visual do vertice
    *             x - deslocamento horizontal relativo ao centro
    *             y - deslocamento vertical relativo ao centro
    * Retorno: vazio
    ********************************************************************************************/
    public void alocarNode(NodeView node, double x, double y) {
          node.setPosicaoVertice(larguraJanela/2+x, alturaJanela/2+y);
          janela.adicionarNoPainelDireito(node.getVertice());
    }//fim do metodo alocarNode
}
