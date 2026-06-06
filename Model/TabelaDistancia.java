/************************************************************************************************
*Autor............: Gustavo Henrique Oliveira Fernandes
*Matricula........: 202410104
*Inicio...........: 29/08/2026
*Ultima alteracao.: 29/08/2026
*Nome.............: TabelaDistancia
*Funcao...........: representar a tabela de distancia do roteador na rede. Realizando o seu prenchimento para cada tabela de distancia recebida
**************************************************************************************************/

package Model;

public class TabelaDistancia {
      private static final int tamanhoTotal = 200;//tamanho total da tabela de roteamento da rede
      private LinhaTabela[] vetorDistancia;//vetor simples que representa aestrutura de dados da tabela
      private int id;//identificacao da tabela, geralmete o id de identificao da tabela eh igualo id de identificacao do roteador
      private int ptr = 0; //aponta para posicao atual  
      private RoteadoresAdjacentes InterfaceSaida;
      private TabelaHashPeso Hash;//uma tabela hash simples que realizar indexacao
      
     /********************************************************************************************
      * Metodo: TabelaDistancia
      * Funcao: inicializar os atributos basicos
      * Parametros: int id, RoteadoresAdjacentes InterfaceSaida  
      * Retorno: vaio  
      ********************************************************************************************/ 
      public TabelaDistancia(int id, RoteadoresAdjacentes InterfaceSaida) {
            this.id = id;
            this.Hash = new TabelaHashPeso(tamanhoTotal);
            this.InterfaceSaida = new RoteadoresAdjacentes(InterfaceSaida.getInterface(), InterfaceSaida.tamanhoInterface());
            vetorDistancia = new LinhaTabela[tamanhoTotal];
      }//fim do construtor TabelaDistancia

       /********************************************************************************************
      * Metodo: inserirLinha
      * Funcao: inserir uma informacao como roteador de destino e de saida conjuntamente com o custo de acesso na tabela
      * Parametros: int id, RoteadoresAdjacentes InterfaceSaida  
      * Retorno: vaio  
      ********************************************************************************************/
      public void inserirLinha(Integer idEntrada, Integer idSaida, double custo) {
           if(ptr < 200) {
              LinhaTabela linhaTabela = new LinhaTabela();
              linhaTabela.setEntrada(idEntrada); linhaTabela.setSaida(idSaida); linhaTabela.setCusto(custo);
              vetorDistancia[ptr] = linhaTabela;
              ptr++;
           }  
      }//fim do metodo inserirLinha
      
      /********************************************************************************************
      * Metodo: definirHash
      * Funcao: associar uma entrada a um enlance de saida adjacente com custo associado
      * Parametros: int entrada, int custo  
      * Retorno: vaio  
      ********************************************************************************************/
      public void definirHash(int entrada, int custo) {
            if(Hash != null) {
               Hash.inserirNaTabela(entrada, custo); 
            }
      }//fim do metodo definirHash 

      /********************************************************************************************
      * Metodo: mostrarTabela
      * Funcao: formular a informacao de saida da tabela
      * Parametros: int entrada, int custo  
      * Retorno: vazio  
      ********************************************************************************************/ 
      public String mostrarTabela() {
           String res = "";
           
           for(int i = 0; i < ptr; i++) {
              if((vetorDistancia[i].getSaida()) == null) { res += "        " + (vetorDistancia[i].getEntrada()+1) + "      |     --     |  " + vetorDistancia[i].getCusto() + "\n"; } else {
                 res += "        " + (vetorDistancia[i].getEntrada()+1) + "      |     " + (vetorDistancia[i].getSaida()+1) + "     |  " + vetorDistancia[i].getCusto() + "\n";
              }
           }   
           return res;
      }// fim do metodo mostrarTabela
      
      /********************************************************************************************
      * Metodo: mostrarTabelaNoTermianl
      * Funcao: formular a informacao de saida da tabela no terminal
      * Parametros: vazio  
      * Retorno: vazio  
      ********************************************************************************************/
      public String mostrarTabelaNoTermianl() {//exibe a tabela no terminal para fins tecnicos
           String res = "Roteador " + (id+1) + ": \n" +"Roteador Entrada | Roteador Saida | Custo\n";
           
           for(int i = 0; i < ptr; i++) {
              if((vetorDistancia[i].getSaida()) == null) { res += "        " + (vetorDistancia[i].getEntrada()+1) + "        |     " + (vetorDistancia[i].getSaida()) + "       |  " + vetorDistancia[i].getCusto() + "\n"; } else {
                 res += "        " + (vetorDistancia[i].getEntrada()+1) + "        |        " + (vetorDistancia[i].getSaida()+1) + "       |  " + vetorDistancia[i].getCusto() + "\n";
              }
           }   
           return res;
      }//fim do metodo mostrarTabelaNoTermianl

      
      /********************************************************************************************
      * Metodo: relaxacaoDasEntradas
      * Funcao: atualizar a tabela com base nas informacao da tabela recebida, aplicando o algoritimo de belman-ford.
                Para esse caso, seja a tabela recebida H1, o algoritmo pecorre todas a estradas e a trada em tres casos diferetnes:
                caso alguma linha nao exista na tabela, ela eh acrescentada na rede. Isso eh fundamental para que todos os roteadores 
                presentes na rede seja descobertos e alocados na tabela. Caso a entrada ja exista, verifica se essa saida eh melhor 
                que o custo da informacao anterior. Caso for, a tabela eh atualizda, senao verifica se a tabela de saida atual eh 
                igual ao roteador de origem da tabela, se for igual, entao atualiza. Para o caso que nao for igual, verifica se 
                aquele enlace de saida atual nao caiu, se caiu, entao atuzliza a tabela. Isso permite que tenhamos um rede resiliente
                que se comporta como um organismo vivo que se readapta ante a um membro perdido. No entanto, tambem introduz 
                desafios, como a contagem ao infinito.      
      * Parametros: vazio  
      * Retorno: vazio  
      ********************************************************************************************/
      public void relaxacaoDasEntradas(TabelaDistancia tabelaRecebida, int id_roteador_adjacente) throws Exception {
                  
           for(int i = 0; i < tabelaRecebida.getPtr(); i++) {
               Integer linha = procurar(tabelaRecebida.retornarLinha(i).getEntrada());//procura para ve se a entrada existe na tabela
                 
               double custoEnalce = Hash.retornarAcesso(id_roteador_adjacente);
               if(linha != null  && tabelaRecebida.retornarLinha(i).getEntrada() != id) {  
                   double DistanciaCandidata = (tabelaRecebida.retornarLinha(i).getCusto() + custoEnalce);
                   
                   if(vetorDistancia[linha].getCusto() > DistanciaCandidata) {
                           vetorDistancia[linha].setCusto(DistanciaCandidata);
                           vetorDistancia[linha].setSaida(tabelaRecebida.getId());
                   } else if(vetorDistancia[linha].getSaida() == tabelaRecebida.getId()) {
                           vetorDistancia[linha].renovarTempoVida();
                           vetorDistancia[linha].setCusto(DistanciaCandidata); 
                   } else if(!determinarValidadeInterfaceSaida(vetorDistancia[linha].getSaida())) {//determina se a interface de saida adjaccente ainda existe na rede, permitindo uma readaptacao da topologia
                          
                           vetorDistancia[linha].setCusto(DistanciaCandidata);
                           vetorDistancia[linha].setSaida(tabelaRecebida.getId());
                   } 
               } else if(linha == null) {
                   inserirLinha(tabelaRecebida.retornarLinha(i).getEntrada(), id_roteador_adjacente, custoEnalce+tabelaRecebida.retornarLinha(i).getCusto());
               }
           }
      }//fim do metodo relaxacaoDasEntradas

      private boolean determinarValidadeInterfaceSaida(int id_interface_saida) {
          try {
             do {
                  if(id_interface_saida == InterfaceSaida.getRoteadorAtual(id_interface_saida).getId_identificao()) {
                            InterfaceSaida.redefirnir(); 
                            return true;                  
                  }    
             } while(InterfaceSaida.next());
          } catch(Exception ex) {
              ex.printStackTrace();
          }
          return false;
      }

      public int getId() {
           return id;
      }

      public LinhaTabela retornarLinha(int i) {
         return vetorDistancia[i];
      }
      
      public int getPtr() {
          return ptr;
      }
      
      public Integer obterSaida(int entrada) {
            for(int i = 0; i < ptr; i++) {
                if(vetorDistancia[i].getEntrada() == entrada) {
                    return vetorDistancia[i].getSaida();
                }
            } 
            return -1;//numero especial em que identifica de que nenhum host foi encontrado na rede 
      }
       

      public Integer procurar(int linhaTabela) {
             for(int j = 0; j < ptr; j++) {
                   if(vetorDistancia[j].getEntrada().equals(linhaTabela)) {
                       return j;
                   }
             }
             return null;
      }
}
