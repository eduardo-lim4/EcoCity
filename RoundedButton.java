// CÓDIGO PARA DEIXAR OS BOTÕES REDONDOS

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RoundedButton extends JButton {

    private Color normalColor;
    private Color hoverColor;
    private Color clickColor;

    public RoundedButton(String text, Color color) {
        super(text);

        normalColor = color;
        hoverColor = color.brighter();
        clickColor = color.darker();

        setFocusPainted(false);
        setContentAreaFilled(false);
        setForeground(Color.BLACK);
        setFont(new Font("Segoe UI", Font.BOLD, 14));

        // Eventos de animação
        addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                setBackground(hoverColor);
                repaint();
            }

            public void mouseExited(MouseEvent e) {
                setBackground(normalColor);
                repaint();
            }

            public void mousePressed(MouseEvent e) {
                setBackground(clickColor);
                repaint();
            }

            public void mouseReleased(MouseEvent e) {
                setBackground(hoverColor);
                repaint();
            }
        });

        setBackground(normalColor);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        // remove borda padrão
    }
}