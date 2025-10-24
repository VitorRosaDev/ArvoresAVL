# Implementação de Árvore AVL em Java

Este projeto é uma implementação acadêmica de uma estrutura de dados de Árvore AVL (Adelson-Velskii e Landis) em Java.

O objetivo principal é demonstrar o funcionamento de uma árvore binária de busca auto-balanceável, garantindo que as operações de inserção e remoção mantenham a árvore balanceada através de rotações (LL, LR, RR, RL). Isso assegura que a complexidade de busca, inserção e remoção seja mantida em $O(\log n)$.

## Funcionalidades

* **Inserção Balanceada**: Insere novos valores inteiros e aplica rotações de balanceamento automaticamente.
* **Remoção Balanceada**: Remove valores (tratando os casos de 0, 1 ou 2 filhos) e rebalanceia a árvore após a remoção.
* **Busca com Caminho**: Busca por um valor e imprime o caminho percorrido desde a raiz até o nó (ou até o ponto de falha).
* **Percursos**: Exibe os percursos da árvore em Pré-Ordem, Em-Ordem e Pós-Ordem.
* **Impressão da Árvore**: Desenha a estrutura da árvore no console.

## Estrutura do Projeto

O projeto está organizado da seguinte forma:

├── .gitignore (Ignora arquivos .class e de saída)
├── src/
    ├── No.java (Define a estrutura do nó da árvore)
    ├── ArvoreAVL.java (Contém a lógica principal da AVL e suas operações)
    ├── Main.java (Controlador que lê comandos do terminal)
    └── Validador.java (Script de teste para validar os requisitos)
└── tests/
    ├── teste1.in (Caso de teste 1: Inserções e percursos)
    ├── teste1.out (Saída esperada para o teste 1)
    ├── teste2.in (Caso de teste 2: Busca (encontrada e não encontrada))
    ├── teste2.out (Saída esperada para o teste 2)
    ├── teste3.in (Caso de teste 3: Duplicatas, remoção e inexistentes)
    └── teste3.out (Saída esperada para o teste 3)


## Como Compilar e Executar

Este projeto foi desenvolvido e testado em um ambiente `bash` (como o Git Bash no Windows) e requer o JDK.

### 1. Compilação

Devido ao uso de caracteres especiais (acentos e símbolos de árvore `└──`), é crucial forçar a codificação `UTF-8` durante a compilação.

```bash
# 1. Navegue até a pasta de código-fonte
cd src

# 2. Compile todos os arquivos .java forçando a leitura como UTF-8
javac -encoding UTF-8 *.java

2. Execução

Após a compilação, você pode executar o Validador (recomendado) ou o Main (manualmente).

Opção A: Rodar o Validador (Recomendado)

O Validador.java executa os 3 casos de teste automaticamente e verifica se a lógica do programa está 100% correta.
Bash

# (Ainda dentro da pasta src)
java Validador

A saída esperada é OK para Contratos e Testes.

Opção B: Execução Manual (Interativa ou com Testes)

Para executar o programa e interagir manualmente ou para rodar um arquivo de teste .in:
Bash

# (Ainda dentro da pasta src)

# É essencial forçar a JVM a ESCREVER em UTF-8
# Este comando executa o teste 1
java -Dfile.encoding=UTF-8 Main < ../tests/teste1.in

Comandos do Programa

O Main.java aceita os seguintes comandos via terminal:
Comando	Exemplo	Descrição
i <num>	i 45	Insere o valor <num> na árvore.
r <num>	r 20	Remove o valor <num> da árvore.
b <num>	b 60	Busca o valor <num> e imprime o caminho.
pre	pre	Imprime o percurso em Pré-Ordem.
em	em	Imprime o percurso em Em-Ordem.
pos	pos	Imprime o percurso em Pós-Ordem.
sair	sair	Encerra o programa.

Desenvolvido por VitorRosaDev
