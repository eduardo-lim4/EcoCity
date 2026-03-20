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
                mensagem = "Indústria aumenta dinheiro, mas gera poluição.";
                break;

            case 2:
                mensagem = "Árvores reduzem poluição, mas custam dinheiro.";
                break;

            case 3:
                mensagem = "Se a poluição chegar a 100, você perde!";
                break;

            case 4:
                mensagem = "Se o meio ambiente chegar a 0, você perde!";
                break;

            case 5:
                mensagem = "Tente sobreviver o máximo de anos possível!\nBoa sorte!";
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

        if (opcao == 0 && etapa < 5) {
            etapa++;
            mostrar(frame);
        }
    }
}
