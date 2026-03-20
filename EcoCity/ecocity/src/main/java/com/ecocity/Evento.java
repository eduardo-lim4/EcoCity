package com.ecocity;
import javax.swing.*;
import java.util.Random;

public class Evento {

    public static void executar(Cidade cidade, JFrame frame) {

        Random rand = new Random();

        // chance base de evento
        int chance = rand.nextInt(100);

        if (chance > 50) return; // 50% de chance de acontecer algo

        // ===== EVENTOS BASEADOS NO ESTADO =====

        // POLUIÇÃO ALTA = DESASTRES
        if (cidade.poluicao >= 70) {

            int tipo = rand.nextInt(2);

            if (tipo == 0) {
                cidade.meioAmbiente -= 20;
                cidade.poluicao += 15;
                JOptionPane.showMessageDialog(frame,
                        "Queimadas! A poluição está fora de controle.");
            } else {
                cidade.populacao -= 150;
                JOptionPane.showMessageDialog(frame,
                        "Ar tóxico! Pessoas estão deixando a cidade.");
            }

            return;
        }

        // MEIO AMBIENTE ALTO = EVENTO BOM
        if (cidade.meioAmbiente >= 70) {

            int tipo = rand.nextInt(2);

            if (tipo == 0) {
                cidade.poluicao -= 15;
                JOptionPane.showMessageDialog(frame,
                        "A natureza está se recuperando!");
            } else {
                cidade.dinheiro += 100;
                JOptionPane.showMessageDialog(frame,
                        "Turismo ecológico! A cidade lucrou!");
            }

            return;
        }

        // DINHEIRO BAIXO = CRISE
        if (cidade.dinheiro < 100) {

            cidade.populacao -= 100;
            JOptionPane.showMessageDialog(frame,
                    "Crise econômica! Pessoas estão indo embora.");

            return;
        }

        // EVENTOS NEUTROS
        int evento = rand.nextInt(3);

        switch (evento) {

            case 0:
                cidade.dinheiro -= 80;
                JOptionPane.showMessageDialog(frame,
                        "Enchente leve causou prejuízos.");
                break;

            case 1:
                cidade.populacao += 50;
                JOptionPane.showMessageDialog(frame,
                        "Crescimento populacional!");
                break;

            case 2:
                cidade.meioAmbiente -= 10;
                JOptionPane.showMessageDialog(frame,
                        "Expansão urbana afetou o ambiente.");
                break;
        }
    }
}