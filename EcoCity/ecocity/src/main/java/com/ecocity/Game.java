
package com.ecocity;
import javax.swing.*;

public class Game {

    Cidade cidade = new Cidade();
    boolean jogoAtivo = true;

    public void proximoTurno(JFrame frame) {
        cidade.ano++;
        cidade.populacao += 50;
        cidade.poluicao += 5;
        cidade.meioAmbiente -= 3;

        Evento.executar(cidade, frame);
        cidade.atualizar();
    }

    public void verificarFim(JFrame frame) {

        if (cidade.poluicao >= 100) {
            fim(frame, "Game Over! Poluição extrema.");
        } else if (cidade.meioAmbiente <= 0) {
            fim(frame, "Game Over! Meio ambiente destruído.");
        } else if (cidade.dinheiro < 0) {
            fim(frame, "Game Over! Falência.");
        } else if (cidade.ano >= 100) {
            fim(frame, "Você alcançou o ano 100!\nSua cidade virou uma lenda sustentável.");
        }
    }

    public int calcularPontuacao() {
        int pontoAnos = cidade.ano * 10;
        int pontoAmbiente = cidade.meioAmbiente * 5;
        int pontoPoluicao = (100 - cidade.poluicao) * 3;
        int pontoPopulacao = cidade.populacao / 10;
        int pontoDinheiro = Math.max(cidade.dinheiro, 0) / 20;

        return pontoAnos + pontoAmbiente + pontoPoluicao + pontoPopulacao + pontoDinheiro;
    }

    private void fim(JFrame frame, String msg) {
        jogoAtivo = false;
        int pontuacao = calcularPontuacao();

        String detalhes = msg
                + "\n\n===== PONTUAÇÃO FINAL ====="
                + "\nAnos sobrevividos: " + cidade.ano + " (+" + (cidade.ano * 10) + " pts)"
                + "\nMeio Ambiente: " + cidade.meioAmbiente + "% (+" + (cidade.meioAmbiente * 5) + " pts)"
                + "\nPoluição: " + cidade.poluicao + "% (+" + ((100 - cidade.poluicao) * 3) + " pts)"
                + "\nPopulação: " + cidade.populacao + " (+" + (cidade.populacao / 10) + " pts)"
                + "\nDinheiro: $" + cidade.dinheiro + " (+" + (Math.max(cidade.dinheiro, 0) / 20) + " pts)"
                + "\n\n🏆 Total: " + pontuacao + " pontos";

        JOptionPane.showMessageDialog(frame, detalhes);
    }
}
