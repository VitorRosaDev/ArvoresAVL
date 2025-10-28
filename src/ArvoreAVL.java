
import java.util.ArrayList;
import java.util.List;

public class ArvoreAVL {
    private No raiz;

    // ===================== MÉTODOS PÚBLICOS (contrato) =====================

    // Método extra usado pelo Main. É permitido ter métodos públicos adicionais.
    public boolean contem(int v) {
        No atual = raiz;
        while (atual != null) {
            if (v == atual.getValor()) return true;
            if (v < atual.getValor()) {
                atual = atual.getEsquerda();
            } else {
                atual = atual.getDireita();
            }
        }
        return false;
    }

    public void inserir(int v) {
        raiz = inserirRec(raiz, v);
    }

    public void remover(int v) {
        raiz = removerRec(raiz, v);
    }

    // buscar precisa:
    // 1) imprimir "Caminho: X Y Z"
    // 2) imprimir "Encontrado: sim" / "Encontrado: não"
    // 3) retornar boolean
    public boolean buscar(int v) {
        StringBuilder caminho = new StringBuilder();
        No atual = raiz;
        boolean achou = false;

        while (atual != null) {
            if (caminho.length() > 0) caminho.append(" ");
            caminho.append(atual.getValor());

            if (v == atual.getValor()) {
                achou = true;
                break;
            } else if (v < atual.getValor()) {
                atual = atual.getEsquerda();
            } else {
                atual = atual.getDireita();
            }
        }

        System.out.println("Caminho: " + caminho.toString());
        System.out.println("Encontrado: " + (achou ? "sim" : "não"));
        return achou;
    }

    public String percursoPreOrdem() {
        StringBuilder sb = new StringBuilder();
        pre(raiz, sb);
        return sb.toString().trim();
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

    // Deve devolver SOMENTE o desenho da árvore (sem "Árvore:\n").
    // O Main já imprime "Árvore:" antes.
    public String imprimirArvore() {
        StringBuilder sb = new StringBuilder();
        if (raiz == null) {
            // se a árvore estiver vazia, os testes não mostram nada após "Árvore:"
            return sb.toString();
        }
        // A raiz sempre começa com "└── "
        desenharNo(sb, "", "└── ", raiz, true);
        return sb.toString();
    }

    // ===================== MÉTODOS PRIVADOS AUXILIARES =====================

    private int altura(No n) {
        if (n == null) return 0;
        return n.getAltura();
    }

    private void atualizarAltura(No n) {
        int hE = altura(n.getEsquerda());
        int hD = altura(n.getDireita());
        int maior = (hE > hD) ? hE : hD;
        n.setAltura(maior + 1);
    }

    private int fatorBalanceamento(No n) {
        if (n == null) return 0;
        return altura(n.getEsquerda()) - altura(n.getDireita());
    }

    private No rotacaoDireita(No y) {
        //        y
        //       /
        //      x
        No x = y.getEsquerda();
        No T2 = x.getDireita();

        // Rotação
        x.setDireita(y);
        y.setEsquerda(T2);

        // Atualiza alturas
        atualizarAltura(y);
        atualizarAltura(x);

        return x;
    }

    private No rotacaoEsquerda(No x) {
        //      x
        //       \
        //        y
        No y = x.getDireita();
        No T2 = y.getEsquerda();

        // Rotação
        y.setEsquerda(x);
        x.setDireita(T2);

        // Atualiza alturas
        atualizarAltura(x);
        atualizarAltura(y);

        return y;
    }

    private No balancear(No n) {
        if (n == null) return null;

        atualizarAltura(n);
        int fb = fatorBalanceamento(n);

        // Caso LL: fb > 1 e FB(esquerda) >= 0
        if (fb > 1 && fatorBalanceamento(n.getEsquerda()) >= 0) {
            return rotacaoDireita(n);
        }

        // Caso LR: fb > 1 e FB(esquerda) < 0
        if (fb > 1 && fatorBalanceamento(n.getEsquerda()) < 0) {
            n.setEsquerda(rotacaoEsquerda(n.getEsquerda()));
            return rotacaoDireita(n);
        }

        // Caso RR: fb < -1 e FB(direita) <= 0
        if (fb < -1 && fatorBalanceamento(n.getDireita()) <= 0) {
            return rotacaoEsquerda(n);
        }

        // Caso RL: fb < -1 e FB(direita) > 0
        if (fb < -1 && fatorBalanceamento(n.getDireita()) > 0) {
            n.setDireita(rotacaoDireita(n.getDireita()));
            return rotacaoEsquerda(n);
        }

        return n;
    }

    // INSERÇÃO RECURSIVA AVL
    private No inserirRec(No n, int v) {
        if (n == null) {
            return new No(v);
        }

        if (v < n.getValor()) {
            n.setEsquerda(inserirRec(n.getEsquerda(), v));
        } else if (v > n.getValor()) {
            n.setDireita(inserirRec(n.getDireita(), v));
        } else {
            // valor duplicado -> não insere novamente
            return n;
        }

        return balancear(n);
    }

    // REMOÇÃO RECURSIVA AVL
    private No removerRec(No n, int v) {
        if (n == null) {
            return null;
        }

        if (v < n.getValor()) {
            n.setEsquerda(removerRec(n.getEsquerda(), v));
        } else if (v > n.getValor()) {
            n.setDireita(removerRec(n.getDireita(), v));
        } else {
            // achou nó a remover
            if (n.getEsquerda() == null || n.getDireita() == null) {
                // 0 ou 1 filho
                No temp = (n.getEsquerda() != null) ? n.getEsquerda() : n.getDireita();
                if (temp == null) {
                    // sem filhos
                    n = null;
                } else {
                    // 1 filho: substitui pelo filho
                    n = temp;
                }
            } else {
                // 2 filhos: pegar o sucessor in-order (menor da subárvore direita)
                No sucessor = minValueNode(n.getDireita());
                n.setValor(sucessor.getValor());
                n.setDireita(removerRec(n.getDireita(), sucessor.getValor()));
            }
        }

        if (n == null) {
            return null;
        }

        return balancear(n);
    }

    private No minValueNode(No n) {
        No atual = n;
        while (atual.getEsquerda() != null) {
            atual = atual.getEsquerda();
        }
        return atual;
    }

    // percursos
    private void pre(No n, StringBuilder sb) {
        if (n == null) return;
        sb.append(n.getValor()).append(" ");
        pre(n.getEsquerda(), sb);
        pre(n.getDireita(), sb);
    }

    private void em(No n, StringBuilder sb) {
        if (n == null) return;
        em(n.getEsquerda(), sb);
        sb.append(n.getValor()).append(" ");
        em(n.getDireita(), sb);
    }

    private void pos(No n, StringBuilder sb) {
        if (n == null) return;
        pos(n.getEsquerda(), sb);
        pos(n.getDireita(), sb);
        sb.append(n.getValor()).append(" ");
    }

    // Impressão ASCII da árvore
    //
    // Deve gerar exatamente esse estilo (exemplo esperado):
    //
    // └── 50
    //     ├── 30
    //     └── 70
    //         └── 65
    //
    // Observações:
    // - A raiz usa "└── "
    // - Cada nível adiciona 4 espaços "    "
    // - Não imprimimos barras verticais "│"
    // - Se só existe 1 filho, ele ainda vira "└── "
    //
    private void desenharNo(StringBuilder sb, String prefixo, String conector, No n, boolean isUltimo) {
        sb.append(prefixo);
        sb.append(conector);
        sb.append(n.getValor()).append("\n");

        // filhos existentes, na ordem: esquerda, direita
        List<No> filhos = new ArrayList<No>();
        if (n.getEsquerda() != null) filhos.add(n.getEsquerda());
        if (n.getDireita() != null) filhos.add(n.getDireita());

        for (int i = 0; i < filhos.size(); i++) {
            No filho = filhos.get(i);
            boolean ultimoFilho = (i == filhos.size() - 1);

            // Conforme os testes fornecidos, eles não mantêm linhas verticais.
            // Então o prefixo seguinte é sempre + "    "
            String novoPrefixo = prefixo + "    ";

            String novoConector = ultimoFilho ? "└── " : "├── ";

            desenharNo(sb, novoPrefixo, novoConector, filho, ultimoFilho);
        }
    }
}
