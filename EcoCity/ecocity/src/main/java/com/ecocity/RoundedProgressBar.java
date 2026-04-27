package com.ecocity;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class RoundedProgressBar extends JProgressBar {

    private Color barColor = Color.GREEN;
    private final int arc = 16;

    public RoundedProgressBar(int min, int max) {
        super(min, max);
        setOpaque(false);
        setBorderPainted(false);
        setStringPainted(true);
        setPreferredSize(new Dimension(100, 20));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 22));
        setFont(new Font("Segoe UI", Font.BOLD, 11));
        setForeground(Color.WHITE);
    }

    public void setBarColor(Color color) {
        this.barColor = color;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        // Fundo arredondado
        g2.setColor(new Color(50, 50, 50, 180));
        g2.fill(new RoundRectangle2D.Float(0, 0, w, h, arc, arc));

        // Barra de preenchimento
        double porcentagem = (double) getValue() / getMaximum();
        int preenchimento = (int) (w * porcentagem);

        if (preenchimento > 0) {
            g2.setColor(barColor);
            g2.fill(new RoundRectangle2D.Float(0, 0, preenchimento, h, arc, arc));
        }

        // Texto centralizado
        String texto = getString();
        if (texto != null) {
            g2.setFont(getFont());
            FontMetrics fm = g2.getFontMetrics();
            int textoX = (w - fm.stringWidth(texto)) / 2;
            int textoY = (h + fm.getAscent() - fm.getDescent()) / 2;

            // Sombra do texto
            g2.setColor(new Color(0, 0, 0, 120));
            g2.drawString(texto, textoX + 1, textoY + 1);

            // Texto
            g2.setColor(Color.WHITE);
            g2.drawString(texto, textoX, textoY);
        }

        g2.dispose();
    }
}
