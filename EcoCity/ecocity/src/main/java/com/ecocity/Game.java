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
        } else if (cidade.ano >= 999) {
            fim(frame, "Você alcançou o ano 999!\nSua cidade virou uma lenda sustentável.");
        }
    }

    private void fim(JFrame frame, String msg) {
        jogoAtivo = false;
       JOptionPane.showMessageDialog(frame, msg + "\nPontuação: " + cidade.ano + " anos");
    }
}