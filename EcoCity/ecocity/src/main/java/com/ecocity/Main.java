
package com.ecocity;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Main {

    // Controla se é a primeira atualização (sem animação das barras)
    static boolean primeiraAtualizacao = true;

    // Timers ativos das barras (evita múltiplos simultâneos)
    static Timer timerPol;
    static Timer timerAmb;

    // Timer de debounce para resize
    static Timer timerResize;

    // Cache das imagens originais (carregadas uma vez)
    static final Map<String, Image> cacheImagens = new HashMap<>();
    static String imagemAtual = "";

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> iniciarJogo());
    }

    private static void iniciarJogo() {

        // ===== PRÉ-CARREGAMENTO DE IMAGENS =====
        carregarImagensCache();

        // ===== ÁUDIO =====

        AudioPlayer player = new AudioPlayer();
        player.tocarLoop("Flash-Casanova-Lease.wav");

        // ===== LÓGICA DO JOGO =====
        Game game = new Game();

        // ===== CONFIGURAÇÃO DA JANELA =====
        JFrame frame = new JFrame("EcoCity");
        frame.setSize(500, 620);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Fonte e cor padrão dos textos
        Font fonteInfo = new Font("Segoe UI", Font.BOLD, 14);
        Color corTexto = Color.WHITE;

        // ===== FUNDO (IMAGEM) =====
        JLabel fundo = new JLabel();
        fundo.setLayout(new BorderLayout());
        frame.setContentPane(fundo);

        // ===== TOPO (TÍTULO) =====
        JLabel titulo = new JLabel("EcoCity", SwingConstants.CENTER) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                g2.setFont(getFont());
                FontMetrics fm = g2.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2.setColor(new Color(0, 0, 0, 150));
                g2.drawString(getText(), x + 2, y + 2);
                g2.setColor(getForeground());
                g2.drawString(getText(), x, y);
                g2.dispose();
            }
        };
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titulo.setForeground(Color.WHITE);
        titulo.setPreferredSize(new Dimension(0, 40));
        fundo.add(titulo, BorderLayout.NORTH);

        // ===== CENTRO (HUD DO JOGO)=====
        JPanel centroWrapper = new JPanel(new GridBagLayout());
        centroWrapper.setOpaque(false);

        JPanel painel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(0, 0, 0, 140));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2.dispose();
            }
        };
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setOpaque(false);
        painel.setBorder(BorderFactory.createEmptyBorder(12, 16, 12, 16));

        // Labels de informação
        JLabel lblAno = criarLabel(fonteInfo, corTexto);
        JLabel lblPop = criarLabel(fonteInfo, corTexto);
        JLabel lblDinheiro = criarLabel(fonteInfo, corTexto);

        // Barras de status (arredondadas)
        RoundedProgressBar polBar = criarBarra();
        RoundedProgressBar ambBar = criarBarra();

        // Painéis das barras com label inline
        JPanel painelPol = criarPainelBarra("Poluição:", polBar, fonteInfo);
        JPanel painelAmb = criarPainelBarra("Meio Ambiente:", ambBar, fonteInfo);

        // Adiciona elementos ao painel com espaçamento controlado
        painel.add(lblAno);
        painel.add(Box.createVerticalStrut(4));
        painel.add(lblPop);
        painel.add(Box.createVerticalStrut(4));
        painel.add(lblDinheiro);
        painel.add(Box.createVerticalStrut(10));
        painel.add(painelPol);
        painel.add(Box.createVerticalStrut(6));
        painel.add(painelAmb);

        centroWrapper.add(painel);
        fundo.add(centroWrapper, BorderLayout.CENTER);

        // ===== RESPONSIVIDADE (com debounce) =====
        frame.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                if (timerResize != null) timerResize.stop();
                timerResize = new Timer(150, e -> {
                    timerResize.stop();
                    fadeImagem(fundo, imagemAtual);
                });
                timerResize.setRepeats(false);
                timerResize.start();
            }
        });

        // ===== BOTÕES =====
        JPanel botoes = new JPanel(new GridLayout(3, 2, 5, 5));
        botoes.setOpaque(false);

        RoundedButton btnIndustria = new RoundedButton("Construir indústria", Color.LIGHT_GRAY);
        RoundedButton btnMineracao = new RoundedButton("Expandir mineração", new Color(205, 170, 125));
        RoundedButton btnRodovia = new RoundedButton("Construir rodovia", new Color(200, 200, 200));
        RoundedButton btnArvore = new RoundedButton("Plantar árvores", new Color(144, 238, 144));
        RoundedButton btnParque = new RoundedButton("Construir parque", new Color(100, 200, 120));
        RoundedButton btnSolar = new RoundedButton("Energia solar", new Color(255, 223, 100));

        botoes.add(btnIndustria);
        botoes.add(btnArvore);
        botoes.add(btnMineracao);
        botoes.add(btnParque);
        botoes.add(btnRodovia);
        botoes.add(btnSolar);

        fundo.add(botoes, BorderLayout.SOUTH);

        // Array dos botões custosos para controle de estado
        RoundedButton[] botoesCustosos = { btnRodovia, btnArvore, btnParque, btnSolar };
        int[] custos = { 80, 50, 120, 200 };

        // ===== AÇÕES DOS BOTÕES =====

        // Construir indústria: +$200, +15 poluição, +100 população
        btnIndustria.addActionListener(e -> {
            if (!game.jogoAtivo) return;
            game.cidade.dinheiro += 200;
            game.cidade.poluicao += 15;
            game.cidade.populacao += 100;
            finalizarTurno(game, frame, lblAno, lblPop, lblDinheiro, polBar, ambBar, fundo, botoesCustosos, custos);
        });

        // Expandir mineração: +$300, +20 poluição, -15 meioAmbiente
        btnMineracao.addActionListener(e -> {
            if (!game.jogoAtivo) return;
            game.cidade.dinheiro += 300;
            game.cidade.poluicao += 20;
            game.cidade.meioAmbiente -= 15;
            finalizarTurno(game, frame, lblAno, lblPop, lblDinheiro, polBar, ambBar, fundo, botoesCustosos, custos);
        });

        // Construir rodovia: +150 população, +10 poluição, -5 meioAmbiente, -$80
        btnRodovia.addActionListener(e -> {
            if (!game.jogoAtivo) return;
            game.cidade.dinheiro -= 80;
            game.cidade.populacao += 150;
            game.cidade.poluicao += 10;
            game.cidade.meioAmbiente -= 5;
            finalizarTurno(game, frame, lblAno, lblPop, lblDinheiro, polBar, ambBar, fundo, botoesCustosos, custos);
        });

        // Plantar árvores: -$50, -10 poluição, +10 meioAmbiente
        btnArvore.addActionListener(e -> {
            if (!game.jogoAtivo) return;
            game.cidade.dinheiro -= 50;
            game.cidade.poluicao -= 10;
            game.cidade.meioAmbiente += 10;
            finalizarTurno(game, frame, lblAno, lblPop, lblDinheiro, polBar, ambBar, fundo, botoesCustosos, custos);
        });

        // Construir parque: -$120, -5 poluição, +15 meioAmbiente, +50 população
        btnParque.addActionListener(e -> {
            if (!game.jogoAtivo) return;
            game.cidade.dinheiro -= 120;
            game.cidade.poluicao -= 5;
            game.cidade.meioAmbiente += 15;
            game.cidade.populacao += 50;
            finalizarTurno(game, frame, lblAno, lblPop, lblDinheiro, polBar, ambBar, fundo, botoesCustosos, custos);
        });

        // Energia solar: -$200, -20 poluição, +10 meioAmbiente
        btnSolar.addActionListener(e -> {
            if (!game.jogoAtivo) return;
            game.cidade.dinheiro -= 200;
            game.cidade.poluicao -= 20;
            game.cidade.meioAmbiente += 10;
            finalizarTurno(game, frame, lblAno, lblPop, lblDinheiro, polBar, ambBar, fundo, botoesCustosos, custos);
        });

        // Primeira atualização da tela
        atualizarUI(game, lblAno, lblPop, lblDinheiro, polBar, ambBar, fundo, botoesCustosos, custos);

        frame.setVisible(true);

        // Tutorial inicial
        Tutorial.iniciar(frame);
    }

    // ===== PRÉ-CARREGAMENTO DE IMAGENS =====

    private static void carregarImagensCache() {
        String[] nomes = { "cidade_poluida.jpg", "cidade_verde.jpg", "cidade_media.jpg" };
        for (String nome : nomes) {
            java.net.URL recurso = Main.class.getResource("/" + nome);
            if (recurso != null) {
                cacheImagens.put(nome, new ImageIcon(recurso).getImage());
            }
        }
    }

    // ===== FINALIZAR TURNO (DRY) =====

    private static void finalizarTurno(Game game, JFrame frame,
                                       JLabel lblAno, JLabel lblPop, JLabel lblDinheiro,
                                       RoundedProgressBar polBar, RoundedProgressBar ambBar,
                                       JLabel fundo,
                                       RoundedButton[] botoesCustosos, int[] custos) {
        game.proximoTurno(frame);
        atualizarUI(game, lblAno, lblPop, lblDinheiro, polBar, ambBar, fundo, botoesCustosos, custos);
        game.verificarFim(frame);
    }

    // ===== MÉTODOS AUXILIARES DE UI =====

    public static JLabel criarLabel(Font fonte, Color cor) {
        JLabel lbl = new JLabel();
        lbl.setFont(fonte);
        lbl.setForeground(cor);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        lbl.setMaximumSize(new Dimension(Integer.MAX_VALUE, 22));
        return lbl;
    }

    public static JLabel criarLabelTexto(String texto, Font fonte) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(fonte);
        lbl.setForeground(Color.WHITE);
        return lbl;
    }

    public static RoundedProgressBar criarBarra() {
        return new RoundedProgressBar(0, 100);
    }

    public static JPanel criarPainelBarra(String texto, RoundedProgressBar bar, Font fonte) {
        JPanel painel = new JPanel(new BorderLayout(8, 0));
        painel.setOpaque(false);
        painel.setAlignmentX(Component.LEFT_ALIGNMENT);
        painel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 24));
        painel.setPreferredSize(new Dimension(300, 24));

        JLabel lbl = new JLabel(texto);
        lbl.setFont(fonte);
        lbl.setForeground(Color.WHITE);
        lbl.setPreferredSize(new Dimension(120, 24));

        painel.add(lbl, BorderLayout.WEST);
        painel.add(bar, BorderLayout.CENTER);
        return painel;
    }

    // ===== ATUALIZA UI =====

    public static void atualizarUI(Game game, JLabel ano, JLabel pop, JLabel din,
                                   RoundedProgressBar pol, RoundedProgressBar amb,
                                   JLabel fundo,
                                   RoundedButton[] botoesCustosos, int[] custos) {

        game.cidade.atualizar();

        ano.setText("Ano: " + game.cidade.ano);
        pop.setText("População: " + game.cidade.populacao);
        din.setText("Dinheiro: $" + game.cidade.dinheiro);

        if (primeiraAtualizacao) {
            pol.setValue(game.cidade.poluicao);
            amb.setValue(game.cidade.meioAmbiente);
            primeiraAtualizacao = false;
        } else {
            animarBarra(pol, game.cidade.poluicao, true);
            animarBarra(amb, game.cidade.meioAmbiente, false);
        }

        pol.setString(pol.getValue() + "%");
        amb.setString(amb.getValue() + "%");

        pol.setBarColor(game.cidade.poluicao > 50 ? new Color(220, 50, 50) : new Color(255, 165, 0));
        amb.setBarColor(game.cidade.meioAmbiente > 50 ? new Color(60, 180, 75) : new Color(220, 50, 50));

        // Desabilita botões custosos quando dinheiro é insuficiente
        for (int i = 0; i < botoesCustosos.length; i++) {
            botoesCustosos[i].setEnabled(game.cidade.dinheiro >= custos[i]);
        }

        // Define imagem de fundo baseada no estado da cidade
        String caminho;
        if (game.cidade.poluicao > 80) {
            caminho = "cidade_poluida.jpg";
        } else if (game.cidade.meioAmbiente > 70) {
            caminho = "cidade_verde.jpg";
        } else {
            caminho = "cidade_media.jpg";
        }

        imagemAtual = caminho;
        fadeImagem(fundo, caminho);
    }

    // ===== ANIMAÇÃO DAS BARRAS =====

    public static void animarBarra(RoundedProgressBar barra, int valorFinal, boolean isPoluicao) {

        if (isPoluicao && timerPol != null) timerPol.stop();
        if (!isPoluicao && timerAmb != null) timerAmb.stop();

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

        if (isPoluicao) timerPol = timer;
        else timerAmb = timer;

        timer.start();
    }

    // ===== IMAGEM RESPONSIVA (com cache) =====

    public static void fadeImagem(JLabel fundo, String caminho) {

        if (caminho == null || caminho.isEmpty()) return;

        int largura = fundo.getWidth();
        int altura = fundo.getHeight();

        if (largura == 0 || altura == 0) {
            largura = 500;
            altura = 620;
        }

        Image imgOriginal = cacheImagens.get(caminho);
        if (imgOriginal == null) {
            System.err.println("Imagem não encontrada no cache: " + caminho);
            return;
        }

        Image img = imgOriginal.getScaledInstance(largura, altura, Image.SCALE_SMOOTH);
        fundo.setIcon(new ImageIcon(img));
    }
}
