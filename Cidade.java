public class Cidade {
    int populacao = 1000;
    int dinheiro = 500;
    int poluicao = 20;
    int meioAmbiente = 80;
    int ano = 1;

    public int limitar(int valor) {
        if (valor < 0) return 0;
        if (valor > 100) return 100;
        return valor;
    }

    public void atualizar() {
        poluicao = limitar(poluicao);
        meioAmbiente = limitar(meioAmbiente);
    }
}