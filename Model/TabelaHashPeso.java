/************************************************************************************************
*Autor............: Gustavo Henrique Oliveira Fernandes
*Matricula........: 202410104
*Inicio...........: 29/08/2026
*Ultima alteracao.: 29/08/2026
*Nome.............: TabelaHashPeso
*Funcao...........: perimitir um mapeamento direto entre dois valores j -> l, seja j um intiro e l tambem um inteiro
                    no entanto usando a estrutura j%tamanho -> l 
***************************************************************************************************/

package Model;

public class TabelaHashPeso {
     private int tamanhoTotal;
     private int[] tabela;

    /********************************************************************************************
    * Metodo: TabelaHashPeso (construtor)
    * Funcao: inicializar a tabela hash com seus produtos basicos
    * Parametros: int tamanhoTotal - identificador do roteador
    * Retorno: nenhum
    ********************************************************************************************/  
     public TabelaHashPeso(int tamanhoTotal) {
            this.tamanhoTotal = tamanhoTotal;
            this.tabela = new int[tamanhoTotal];
     }  //fim do metodo TabelaHashPeso

    /********************************************************************************************
    * Metodo: inserirNaTabela
    * Funcao: realizar a assocaicao ptr%tamanho_tabela --> valor
    * Parametros: int ptr, int valor
    * Retorno: nenhum
    ********************************************************************************************/  
     public void inserirNaTabela(int ptr, int valor) {//mapeamento ptr -> valor
          tabela[ptr%tamanhoTotal] = valor;
     }//fim do metodo inserirNaTabela
     
   /********************************************************************************************
    * Metodo: retornarAcesso
    * Funcao: retornar o acesso da tabela dada uma posicao ptr
    * Parametros: int ptr, int valor
    * Retorno: int posicao acesso
    ********************************************************************************************/
     public int retornarAcesso(int ptr) {
            return tabela[ptr];
     } //fim do metodo retornarAcesso
}