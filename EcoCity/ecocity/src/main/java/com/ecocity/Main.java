package com.ecocity;

import javax.swing.*;
import java.awt.*;

public class Main {

    // Controla se é a primeira atualização (sem animação das barras)
    static boolean primeiraAtualizacao = true;

    public static void main(String[] args) {

        // ===== ÁUDIO =====

        AudioPlayer player = new AudioPlayer();
        player.tocarLoop("Flash-Casanova-Lease.wav");

        // ===== LÓGICA DO JOGO =====
        Game game = new Game();

        // ===== CONFIGURAÇÃO DA JANELA =====
        JFrame frame = new JFrame("EcoCity");
        frame.setSize(450, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Fonte e cor padrão dos textos
        Font fonteInfo = new Font("Segoe UI", Font.BOLD, 14);
        Color corTexto = Color.WHITE;

        // ===== FUNDO (IMAGEM) =====
        // JLabel usado como "container" para permitir imagem de fundo
        JLabel fundo = new JLabel();
        fundo.setLayout(new BorderLayout());
        frame.setContentPane(fundo);

        // ===== TOPO (TÍTULO) =====
        JLabel titulo = new JLabel("EcoCity", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titulo.setForeground(Color.WHITE);
        fundo.add(titulo, BorderLayout.NORTH);

        // ===== CENTRO (HUD DO JOGO)=====
        JPanel painel = new JPanel(new GridLayout(0, 1, 10, 10));
        painel.setBackground(new Color(0, 0, 0, 120));

        // Labels de informação
        JLabel lblAno = criarLabel(fonteInfo, corTexto);
        JLabel lblPop = criarLabel(fonteInfo, corTexto);
        JLabel lblDinheiro = criarLabel(fonteInfo, corTexto);

        // Barras de status
        JProgressBar polBar = criarBarra();
        JProgressBar ambBar = criarBarra();

        // Painéis das barras (para dar fundo e destaque)
        JPanel painelPol = criarPainelBarra(polBar);
        JPanel painelAmb = criarPainelBarra(ambBar);

        // Adiciona elementos ao painel
        painel.add(lblAno);
        painel.add(lblPop);
        painel.add(lblDinheiro);

        painel.add(criarLabelTexto("Poluição:", fonteInfo));
        painel.add(painelPol);

        painel.add(criarLabelTexto("Meio Ambiente:", fonteInfo));
        painel.add(painelAmb);

        fundo.add(painel, BorderLayout.CENTER);

        // ===== RESPONSIVIDADE ===== 
        // // Atualiza a UI sempre que a janela muda de tamanho
        frame.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                atualizarUI(game, lblAno, lblPop, lblDinheiro, polBar, ambBar, fundo);
            }
        });

        // ===== BOTÕES =====
        JPanel botoes = new JPanel(new GridLayout(2, 1, 5, 5));

        RoundedButton btnIndustria = new RoundedButton("Construir indústria", Color.LIGHT_GRAY);
        RoundedButton btnArvore = new RoundedButton("Plantar árvores", new Color(144, 238, 144));

        botoes.add(btnIndustria);
        botoes.add(btnArvore);

        fundo.add(botoes, BorderLayout.SOUTH);

        // ===== AÇÕES DOS BOTÕES =====
        // Botão indústria
        btnIndustria.addActionListener(e -> {
            if (!game.jogoAtivo) return;

            game.cidade.dinheiro += 200;
            game.cidade.poluicao += 15;
            game.cidade.populacao += 100;

            game.proximoTurno(frame);
            atualizarUI(game, lblAno, lblPop, lblDinheiro, polBar, ambBar, fundo);
            game.verificarFim(frame);
        });

        // Botão árvores
        btnArvore.addActionListener(e -> {
            if (!game.jogoAtivo) return;

            game.cidade.dinheiro -= 50;
            game.cidade.poluicao -= 10;
            game.cidade.meioAmbiente += 10;

            game.proximoTurno(frame);
            atualizarUI(game, lblAno, lblPop, lblDinheiro, polBar, ambBar, fundo);
            game.verificarFim(frame);
        });

        // Primeira atualização da tela
        atualizarUI(game, lblAno, lblPop, lblDinheiro, polBar, ambBar, fundo);

        frame.setVisible(true);

        // Tutorial inicial
        Tutorial.iniciar(frame);
    }

    // ===== MÉTODOS AUXILIARES DE UI =====

    // Cria label padrão (usado para informações)
    public static JLabel criarLabel(Font fonte, Color cor) {
        JLabel lbl = new JLabel();
        lbl.setFont(fonte);
        lbl.setForeground(cor);
        return lbl;
    }

    // Cria label com texto fixo (ex: "Poluição")
    public static JLabel criarLabelTexto(String texto, Font fonte) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(fonte);
        lbl.setForeground(Color.WHITE);
        return lbl;
    }   

    // Cria barra de progresso estilizada
    public static JProgressBar criarBarra() {
        JProgressBar bar = new JProgressBar(0, 100);
        bar.setStringPainted(true);
        bar.setPreferredSize(new Dimension(100, 25));
        bar.setOpaque(true);
        bar.setBorderPainted(false);
        bar.setBackground(Color.DARK_GRAY);
        return bar;
    }

    // Cria painel com fundo para a barra (melhor contraste)
    public static JPanel criarPainelBarra(JProgressBar bar) {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setBackground(new Color(0, 0, 0, 150));
        painel.add(bar);
        return painel;
    }

    // ===== ATUALIZA UI =====

    public static void atualizarUI(Game game, JLabel ano, JLabel pop, JLabel din,
                                   JProgressBar pol, JProgressBar amb,
                                   JLabel fundo) {

        // Atualiza valores da cidade
        game.cidade.atualizar();
        
        // Atualiza textos
        ano.setText("Ano: " + game.cidade.ano);
        pop.setText("População: " + game.cidade.populacao);
        din.setText("Dinheiro: $" + game.cidade.dinheiro);

        // Primeira atualização sem animação
        if (primeiraAtualizacao) {
            pol.setValue(game.cidade.poluicao);
            amb.setValue(game.cidade.meioAmbiente);
            primeiraAtualizacao = false;
        } else {
            animarBarra(pol, game.cidade.poluicao);
            animarBarra(amb, game.cidade.meioAmbiente);
        }

        // Atualiza texto das barras
        pol.setString(pol.getValue() + "%");
        amb.setString(amb.getValue() + "%");

        // Cores dinâmicas
        pol.setForeground(game.cidade.poluicao > 50 ? Color.RED : Color.ORANGE);
        amb.setForeground(game.cidade.meioAmbiente > 50 ? Color.GREEN : Color.RED);

        // Define imagem de fundo baseada no estado da cidade
        String caminho;

        if (game.cidade.poluicao > 80) {
            caminho = "cidade_poluida.jpg";
        } else if (game.cidade.meioAmbiente > 70) {
            caminho = "cidade_verde.jpg";
        } else {
            caminho = "cidade_media.jpg";
        }

        fadeImagem(fundo, caminho);
    }

    // ===== ANIMAÇÃO DAS BARRAS=====

    public static void animarBarra(JProgressBar barra, int valorFinal) {

        Timer timer = new Timer(15, null);

        timer.addActionListener(e -> {
            int atual = barra.getValue();

            if (atual < valorFinal) {
                barra.setValue(atual + 1);
            } else if (atual > valorFinal) {
                barra.setValue(atual - 1);
            } else {
                timer.stop();
            }

            barra.setString(barra.getValue() + "%");
        });

        timer.start();
    }

    // ===== IMAGEM RESPONSIVA =====

    public static void fadeImagem(JLabel fundo, String caminho) {

        int largura = fundo.getWidth();
        int altura = fundo.getHeight();

        // fallback inicial
        if (largura == 0 || altura == 0) {
            largura = 450;
            altura = 500;
        }

        Image img = new ImageIcon(caminho).getImage()
                .getScaledInstance(largura, altura, Image.SCALE_SMOOTH);

        fundo.setIcon(new ImageIcon(img));
    }
}