public class ArvoreAVL {
    private No raiz;

    // --- MÉTODOS PÚBLICOS OBRIGATÓRIOS ---

    /**
     * Método público para inserir um valor.
     * A verificação de duplicata é feita pelo Main.java (via contem(v)),
     * então este método apenas insere.
     */
    public void inserir(int v) {
        raiz = inserirRec(raiz, v);
    }

    /**
     * Método público para remover um valor.
     * A verificação de existência é feita pelo Main.java (via contem(v)),
     * então este método apenas remove.
     */
    public void remover(int v) {
        raiz = removerRec(raiz, v);
    }

    /**
     * Busca um valor e imprime o caminho (obrigatório pelo contrato).
     */
    public boolean buscar(int v) {
        No atual = raiz;
        StringBuilder caminho = new StringBuilder();
        boolean encontrado = false;

        while (atual != null) {
            // Adiciona o valor ao caminho, com espaço se não for o primeiro
            if (caminho.length() > 0) {
                caminho.append(" ");
            }
            caminho.append(atual.getValor());

            if (v == atual.getValor()) {
                encontrado = true;
                break; // Achou, para o laço
            }

            if (v < atual.getValor()) {
                atual = atual.getEsquerda();
            } else {
                atual = atual.getDireita();
            }
        }

        // Impressão obrigatória (Contrato Oficial)
        System.out.println("Caminho: " + caminho.toString());
        System.out.println("Encontrado: " + (encontrado ? "sim" : "não"));

        return encontrado;
    }

    /**
     * Método público usado pelo Main para verificar duplicatas/existência.
     */
    public boolean contem(int v) {
        return contemRec(raiz, v);
    }

    public String percursoPreOrdem() {
        StringBuilder sb = new StringBuilder();
        pre(raiz, sb);
        return sb.toString().trim(); // .trim() remove espaços extras no final
    }

    public String percursoEmOrdem() {
        StringBuilder sb = new StringBuilder();
        em(raiz, sb);
        return sb.toString().trim();
    }

    public String percursoPosOrdem() {
        StringBuilder sb = new StringBuilder();
        pos(raiz, sb);
        return sb.toString().trim();
    }

    /**
     * Método público que inicia a impressão da árvore (formato gabarito).
     */
    public String imprimirArvore() {
        if (raiz == null) {
            // Nota: O gabarito não testa árvore vazia, mas isso é uma boa prática.
            // O teste3.out remove 40, deixando 60 e 20, não fica vazia.
            // Vamos retornar uma string que não quebre o Validador.
            return ""; // String vazia é mais seguro.
        }
        
        StringBuilder sb = new StringBuilder();
        // Começa a impressão recursiva a partir da raiz
        imprimirRec(raiz, "", sb); 
        return sb.toString();
    }


    // --- MÉTODOS PRIVADOS OBRIGATÓRIOS (AUXILIARES) ---

    /**
     * Auxiliar recursivo para o contem(v).
     */
    private boolean contemRec(No no, int v) {
        if (no == null) {
            return false; // Chegou numa folha e não achou
        }

        if (v == no.getValor()) {
            return true; // Achou
        }

        if (v < no.getValor()) {
            return contemRec(no.getEsquerda(), v); // Procura à esquerda
        } else {
            return contemRec(no.getDireita(), v); // Procura à direita
        }
    }

    /**
     * Retorna a altura do nó. (altura(null) == 0)
     */
    private int altura(No n) {
        if (n == null) {
            return 0;
        }
        return n.getAltura(); // Retorna o valor armazenado no nó
    }

    /**
     * Retorna o Fator de Balanceamento do nó (hE - hD).
     */
    private int fatorBalanceamento(No n) {
        if (n == null) {
            return 0;
        }
        return altura(n.getEsquerda()) - altura(n.getDireita());
    }

    // --- ROTAÇÕES ---

    /**
     * Rotação Simples à Direita (Caso LL).
     */
    private No rotacaoDireita(No y) {
        No x = y.getEsquerda();
        No T2 = x.getDireita(); 

        // Realiza a rotação
        x.setDireita(y);
        y.setEsquerda(T2);

        // Atualiza alturas
        y.setAltura(Math.max(altura(y.getEsquerda()), altura(y.getDireita())) + 1);
        x.setAltura(Math.max(altura(x.getEsquerda()), altura(x.getDireita())) + 1);

        return x; // Retorna a nova raiz
    }

    /**
     * Rotação Simples à Esquerda (Caso RR).
     */
    private No rotacaoEsquerda(No x) {
        No y = x.getDireita();
        No T2 = y.getEsquerda(); 

        // Realiza a rotação
        y.setEsquerda(x);
        x.setDireita(T2);

        // Atualiza alturas
        x.setAltura(Math.max(altura(x.getEsquerda()), altura(x.getDireita())) + 1);
        y.setAltura(Math.max(altura(y.getEsquerda()), altura(y.getDireita())) + 1);

        return y; // Retorna a nova raiz
    }

    // --- RECURSIVOS DE INSERÇÃO/REMOÇÃO ---

    private No inserirRec(No no, int v) {
        // 1. Inserção normal de BST
        if (no == null) {
            return (new No(v)); // Nó criado com altura 1 (ver No.java)
        }

        // 2. Tratar Duplicata (ignora, conforme Main.java)
        if (v == no.getValor()) {
            return no; 
        }

        // 3. Descida recursiva
        if (v < no.getValor()) {
            no.setEsquerda(inserirRec(no.getEsquerda(), v));
        } else {
            no.setDireita(inserirRec(no.getDireita(), v));
        }

        // 4. Atualiza a altura
        no.setAltura(1 + Math.max(altura(no.getEsquerda()), altura(no.getDireita())));

        // 5. Calcula o Fator de Balanceamento (FB)
        int fb = fatorBalanceamento(no);

        // 6. Rebalanceamento (4 casos)

        // Caso LL (FB > 1 e novo nó à esquerda do filho esquerdo)
        if (fb > 1 && v < no.getEsquerda().getValor()) {
            return rotacaoDireita(no);
        }

        // Caso RR (FB < -1 e novo nó à direita do filho direito)
        if (fb < -1 && v > no.getDireita().getValor()) {
            return rotacaoEsquerda(no);
        }

        // Caso LR (FB > 1 e novo nó à direita do filho esquerdo)
        if (fb > 1 && v > no.getEsquerda().getValor()) {
            no.setEsquerda(rotacaoEsquerda(no.getEsquerda()));
            return rotacaoDireita(no);
        }

        // Caso RL (FB < -1 e novo nó à esquerda do filho direito)
        if (fb < -1 && v < no.getDireita().getValor()) {
            no.setDireita(rotacaoDireita(no.getDireita()));
            return rotacaoEsquerda(no);
        }

        return no; // Retorna o nó (balanceado)
    }

    private No removerRec(No no, int v) {
        // 1. Busca pelo nó (ou nó nulo)
        if (no == null) {
            return no; // Valor não encontrado
        }

        // 2. Descida recursiva
        if (v < no.getValor()) {
            no.setEsquerda(removerRec(no.getEsquerda(), v));
        } else if (v > no.getValor()) {
            no.setDireita(removerRec(no.getDireita(), v));
        }
        
        // 3. Nó encontrado (v == no.getValor())
        else {
            // Caso 0 ou 1 Filho
            if (no.getEsquerda() == null || no.getDireita() == null) {
                No temp = (no.getEsquerda() != null) ? no.getEsquerda() : no.getDireita();
                if (temp == null) { // 0 filhos
                    no = null; 
                } else { // 1 filho
                    no = temp; 
                }
            } 
            // Caso 2 Filhos
            else {
                // Encontra o sucessor (menor da direita)
                No sucessor = minValorNo(no.getDireita());
                // Copia o valor
                no.setValor(sucessor.getValor());
                // Remove o sucessor (que agora é duplicado)
                no.setDireita(removerRec(no.getDireita(), sucessor.getValor()));
            }
        }

        // Se a árvore ficou vazia (só tinha 1 nó)
        if (no == null) {
            return no;
        }

        // 4. Atualiza a altura
        no.setAltura(1 + Math.max(altura(no.getEsquerda()), altura(no.getDireita())));

        // 5. Calcula o Fator de Balanceamento
        int fb = fatorBalanceamento(no);

        // 6. Rebalanceamento (4 casos)

        // Caso LL (FB > 1 e FB(filho esq) >= 0)
        if (fb > 1 && fatorBalanceamento(no.getEsquerda()) >= 0) {
            return rotacaoDireita(no);
        }

        // Caso LR (FB > 1 e FB(filho esq) < 0)
        if (fb > 1 && fatorBalanceamento(no.getEsquerda()) < 0) {
            no.setEsquerda(rotacaoEsquerda(no.getEsquerda()));
            return rotacaoDireita(no);
        }

        // Caso RR (FB < -1 e FB(filho dir) <= 0)
        if (fb < -1 && fatorBalanceamento(no.getDireita()) <= 0) {
            return rotacaoEsquerda(no);
        }

        // Caso RL (FB < -1 e FB(filho dir) > 0)
        if (fb < -1 && fatorBalanceamento(no.getDireita()) > 0) {
            no.setDireita(rotacaoDireita(no.getDireita()));
            return rotacaoEsquerda(no);
        }

        return no; // Retorna o nó (balanceado)
    }

    /**
     * Encontra o nó com o menor valor na subárvore (o sucessor).
     */
    private No minValorNo(No no) {
        No atual = no;
        while (atual.getEsquerda() != null) {
            atual = atual.getEsquerda();
        }
        return atual;
    }

    // --- RECURSIVOS DE PERCURSO ---

    // Pré-Ordem: Raiz, Esquerda, Direita
    private void pre(No n, StringBuilder sb) {
        if (n != null) {
            sb.append(n.getValor()).append(" "); // Visita a Raiz
            pre(n.getEsquerda(), sb);           // Visita Esquerda
            pre(n.getDireita(), sb);            // Visita Direita
        }
    }

    // Em-Ordem: Esquerda, Raiz, Direita
    private void em(No n, StringBuilder sb) {
        if (n != null) {
            em(n.getEsquerda(), sb);            // Visita Esquerda
            sb.append(n.getValor()).append(" "); // Visita a Raiz
            em(n.getDireita(), sb);             // Visita Direita
        }
    }

    // Pós-Ordem: Esquerda, Direita, Raiz
    private void pos(No n, StringBuilder sb) {
        if (n != null) {
            pos(n.getEsquerda(), sb);           // Visita Esquerda
            pos(n.getDireita(), sb);            // Visita Direita
            sb.append(n.getValor()).append(" "); // Visita a Raiz
        }
    }

    // --- AUXILIAR DE IMPRESSÃO (Formato Gabarito) ---

    /**
     * Método recursivo para imprimir a árvore no formato "upright" (de pé).
     */
    private void imprimirRec(No no, String prefixo, StringBuilder sb) {
        if (no == null) return;

        // 1. Imprime o nó atual
        if (prefixo.isEmpty()) {
            sb.append("└── ").append(no.getValor()).append("\n");
        } else {
            String prefixoPai = prefixo.substring(0, prefixo.length() - 4);
            sb.append(prefixoPai).append(prefixo.substring(prefixo.length() - 4)).append(no.getValor()).append("\n");
        }

        // 2. Prepara os prefixos para os filhos
        String prefixoBaseFilhos = prefixo.isEmpty() ? "    " : prefixo.substring(0, prefixo.length() - 4) +"    ";
        
        // 3. Decide os "galhos" corretos
        if (no.getEsquerda() != null && no.getDireita() != null) {
            // Ambos os filhos
            imprimirRec(no.getEsquerda(), prefixoBaseFilhos + "├── ", sb);
            imprimirRec(no.getDireita(), prefixoBaseFilhos + "└── ", sb);
        } else if (no.getEsquerda() != null) {
            // Só filho da esquerda
            imprimirRec(no.getEsquerda(), prefixoBaseFilhos + "└── ", sb);
        } else if (no.getDireita() != null) {
            // Só filho da direita
            imprimirRec(no.getDireita(), prefixoBaseFilhos + "└── ", sb);
        }
    }
}