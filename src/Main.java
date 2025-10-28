
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {

    private static void printlnLF(String texto) {
        System.out.print(texto + "\n");
    }

    public static void main(String[] args) throws Exception {
        ArvoreAVL arvore = new ArvoreAVL();

        // 1. Ler TODO o stdin bruto (mesmo que venha tudo em uma linha só)
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
        StringBuilder bruto = new StringBuilder();
        String tmp;
        while ((tmp = br.readLine()) != null) {
            if (bruto.length() > 0) {
                // readLine() remove \n reais. Se tiver múltiplas linhas reais, recolocamos quebra.
                bruto.append("\n");
            }
            bruto.append(tmp);
        }

        // Agora "bruto" pode estar em dois formatos:
        // (a) vários comandos separados por \n reais  -> i 45\ni 20\n...
        // (b) UMA linha longa contendo "\n" LITERAL   -> i 45\n i 20\n i 60\n...

        // 2. Normalizar: transformar as sequências literais "\n" em quebras reais de linha
        String normalizado = bruto.toString().replace("\\n", "\n");

        // 3. Agora podemos processar comando por comando
        String[] linhas = normalizado.split("\\r?\\n");

        for (String linha : linhas) {
            linha = linha.trim();
            if (linha.isEmpty()) continue;

            String[] parts = linha.split("\\s+");
            String cmd = parts[0].toLowerCase();

            if (cmd.equals("sair")) {
                printlnLF("Encerrado.");
                break;
            }

            try {
                switch (cmd) {
                    case "i": {
                        if (parts.length != 2) break;
                        int v = Integer.parseInt(parts[1]);
                        if (arvore.contem(v)) {
                            printlnLF("Ignorado: duplicado " + v);
                        } else {
                            arvore.inserir(v);
                            printlnLF("OK: inserido " + v);
                        }
                        printlnLF("Árvore:");
                        System.out.print(arvore.imprimirArvore());
                        break;
                    }

                    case "r": {
                        if (parts.length != 2) break;
                        int v = Integer.parseInt(parts[1]);
                        if (!arvore.contem(v)) {
                            printlnLF("Ignorado: inexistente " + v);
                        } else {
                            arvore.remover(v);
                            printlnLF("OK: removido " + v);
                        }
                        printlnLF("Árvore:");
                        System.out.print(arvore.imprimirArvore());
                        break;
                    }

                    case "b": {
                        if (parts.length != 2) break;
                        int v = Integer.parseInt(parts[1]);
                        arvore.buscar(v); // já imprime caminho + Encontrado: sim/não
                        break;
                    }

                    case "pre": {
                        printlnLF("Pré-Ordem: " + arvore.percursoPreOrdem());
                        break;
                    }

                    case "em": {
                        printlnLF("Em-Ordem: " + arvore.percursoEmOrdem());
                        break;
                    }

                    case "pos": {
                        printlnLF("Pós-Ordem: " + arvore.percursoPosOrdem());
                        break;
                    }

                    default:
                        // comando desconhecido -> ignora
                        break;
                }
            } catch (NumberFormatException e) {
                // ignora entradas inválidas tipo "i abc"
            }
        }
    }
}
