Esse trabalho buscou implementar uma simulação do protocolo OSPF usado na rede para o
roteamento de rotas e encaminhamento de pacotes. Para tal, busquei realizar o prenchimento
da tabela de nextHop com base na arvore de caminho de minimos gerada a partir do algoritmo de
dijkstra. O usuario pode se sentir a vontade para cortar uma aresta o que significa corte de enlace.
Cada roteador é identificado com um id que varia de 1 a n, seja n a quantidade de roteadores.
A representação da rede é flexivel, então o usuario pode se sentir a vontade de escolher a topologia
da rede que lhe bem agradar, seguindo rigorasmante a estrutura sugerido no arquivo backaBone.txt
Sabendo que a estrututra geral é essa: numero_vertices; + "\n" + identificacaoRoteador1;identifficaçãoRoteador2;
