package com.ecocity;
import javax.swing.*;

public class Tutorial {
    static int etapa = 0;

    public static void iniciar(JFrame frame) {
        mostrar(frame);
    }

    private static void mostrar(JFrame frame) {

        String mensagem = "";

        switch (etapa) {

            case 0:
                mensagem = "Bem-vindo ao EcoCity!\n\nVocê é responsável por cuidar da cidade.";
                break;

            case 1:
                mensagem = "Ações como Indústria e Mineração geram dinheiro,\nmas aumentam a poluição e prejudicam o ambiente.";
                break;

            case 2:
                mensagem = "Ações como Árvores, Parque e Energia Solar\nmelhoram o ambiente, mas custam dinheiro.";
                break;

            case 3:
                mensagem = "Rodovias atraem população, mas também\naumentam poluição. Equilibre suas escolhas!";
                break;

            case 4:
                mensagem = "Se a poluição chegar a 100% ou o meio\nambiente a 0%, você perde!";
                break;

            case 5:
                mensagem = "A cada 10 anos, os eventos ficam mais intensos.\nSua pontuação considera anos, ambiente, população e dinheiro.";
                break;

            case 6:
                mensagem = "Fique atento às dicas educativas nos eventos!\nBoa sorte, prefeito!";
                break;
        }

        int opcao = JOptionPane.showOptionDialog(
                frame,
                mensagem,
                "Tutorial",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                new String[]{"Próximo", "Pular"},
                "Próximo"
        );

        if (opcao == 0 && etapa < 6) {
            etapa++;
            mostrar(frame);
        }
    }
}
