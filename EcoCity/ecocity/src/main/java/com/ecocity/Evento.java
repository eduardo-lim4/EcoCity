package com.ecocity;
import javax.swing.*;
import java.util.Random;

public class Evento {

    private static final Random RAND = new Random();

    public static void executar(Cidade cidade, JFrame frame) {

        // chance base de evento
        int chance = RAND.nextInt(100);

        if (chance > 50) return; // 50% de chance de acontecer algo

        // Multiplicador de dificuldade: aumenta a cada 10 anos
        int nivel = cidade.ano / 10;
        double mult = 1.0 + (nivel * 0.2); // +20% a cada 10 anos

        // ===== EVENTOS BASEADOS NO ESTADO =====

        // POLUIÇÃO ALTA = DESASTRES
        if (cidade.poluicao >= 70) {

            int tipo = RAND.nextInt(2);

            if (tipo == 0) {
                int danoAmb = (int) (20 * mult);
                int danoPol = (int) (15 * mult);
                cidade.meioAmbiente -= danoAmb;
                cidade.poluicao += danoPol;
                JOptionPane.showMessageDialog(frame,
                        "Queimadas! A poluição está fora de controle.\n\n"
                        + "💡 Dica: Queimadas liberam CO₂ e destroem habitats.\n"
                        + "Investir em prevenção e reflorestamento é essencial.");
            } else {
                int danoPop = (int) (150 * mult);
                cidade.populacao -= danoPop;
                JOptionPane.showMessageDialog(frame,
                        "Ar tóxico! Pessoas estão deixando a cidade.\n\n"
                        + "💡 Dica: A poluição do ar causa doenças respiratórias.\n"
                        + "Segundo a OMS, 7 milhões de pessoas morrem por ano por isso.");
            }

            return;
        }

        // MEIO AMBIENTE ALTO = EVENTO BOM
        if (cidade.meioAmbiente >= 70) {

            int tipo = RAND.nextInt(2);

            if (tipo == 0) {
                int bonus = (int) (15 * mult);
                cidade.poluicao -= bonus;
                JOptionPane.showMessageDialog(frame,
                        "A natureza está se recuperando!\n\n"
                        + "💡 Dica: Áreas verdes filtram o ar e absorvem CO₂.\n"
                        + "Uma única árvore pode absorver até 22 kg de CO₂ por ano.");
            } else {
                int bonus = (int) (100 * mult);
                cidade.dinheiro += bonus;
                JOptionPane.showMessageDialog(frame,
                        "Turismo ecológico! A cidade lucrou!\n\n"
                        + "💡 Dica: Cidades sustentáveis atraem turismo e investimento.\n"
                        + "O ecoturismo cresce 20% ao ano no mundo.");
            }

            return;
        }

        // DINHEIRO BAIXO = CRISE
        if (cidade.dinheiro < 100) {

            int danoPop = (int) (100 * mult);
            cidade.populacao -= danoPop;
            JOptionPane.showMessageDialog(frame,
                    "Crise econômica! Pessoas estão indo embora.\n\n"
                    + "💡 Dica: Economia e meio ambiente estão conectados.\n"
                    + "Investimentos verdes geram empregos e estabilidade.");

            return;
        }

        // EVENTOS NEUTROS
        int evento = RAND.nextInt(3);

        switch (evento) {

            case 0:
                int danoDin = (int) (80 * mult);
                cidade.dinheiro -= danoDin;
                JOptionPane.showMessageDialog(frame,
                        "Enchente leve causou prejuízos.\n\n"
                        + "💡 Dica: Enchentes são agravadas pelo desmatamento\n"
                        + "e pela impermeabilização do solo nas cidades.");
                break;

            case 1:
                int bonusPop = (int) (50 * mult);
                cidade.populacao += bonusPop;
                JOptionPane.showMessageDialog(frame,
                        "Crescimento populacional!\n\n"
                        + "💡 Dica: Mais pessoas = mais consumo de recursos.\n"
                        + "Planejamento urbano sustentável é fundamental.");
                break;

            case 2:
                int danoAmb = (int) (10 * mult);
                cidade.meioAmbiente -= danoAmb;
                JOptionPane.showMessageDialog(frame,
                        "Expansão urbana afetou o ambiente.\n\n"
                        + "💡 Dica: A expansão urbana desordenada destrói\n"
                        + "ecossistemas. Cidades compactas são mais eficientes.");
                break;
        }
    }
}
