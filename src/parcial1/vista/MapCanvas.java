package parcial1.vista;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import parcial1.modelo.Ecosistema;
import parcial1.modelo.Entidad;
import parcial1.modelo.Planta;
import parcial1.modelo.PlantaVenenosa;
import parcial1.modelo.Conejo;
import parcial1.modelo.Lobo;

public class MapCanvas extends JPanel {
    private Ecosistema eco;
    private final int CELDAS = 15;

    public MapCanvas() {
        setPreferredSize(new Dimension(500, 500));
        setBackground(new Color(245, 245, 220)); // Fondo beige de tierra
        setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(209, 213, 219)), "Mapa de Ecosistema (15x15)",
            TitledBorder.LEFT, TitledBorder.TOP, new Font("SansSerif", Font.BOLD, 12)
        ));
    }

    public void setEcosistema(Ecosistema eco) {
        this.eco = eco;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth() - 20;
        int height = getHeight() - 30;
        int offsetLeft = 10;
        int offsetTop = 20;

        int cellW = width / CELDAS;
        int cellH = height / CELDAS;

        if (width <= 0 || height <= 0 || cellW <= 0 || cellH <= 0) return;

        System.out.println("MapCanvas paintComponent called: width=" + getWidth() + ", height=" + getHeight() + ", eco=" + (eco != null) + ", entities=" + (eco != null ? eco.getEntidadesVivas().size() : 0));

        // Dibujar cuadrícula de fondo
        g2.setColor(new Color(229, 231, 235));
        for (int i = 0; i <= CELDAS; i++) {
            g2.drawLine(offsetLeft + i * cellW, offsetTop, offsetLeft + i * cellW, offsetTop + CELDAS * cellH);
            g2.drawLine(offsetLeft, offsetTop + i * cellH, offsetLeft + CELDAS * cellW, offsetTop + i * cellH);
        }

        if (eco == null) return;

        // Dibujar entidades en sus respectivas celdas
        for (Entidad e : eco.getEntidadesVivas()) {
            int r = e.getFila();
            int c = e.getColumna();
            if (r >= 0 && r < CELDAS && c >= 0 && c < CELDAS) {
                int x = offsetLeft + c * cellW + 2;
                int y = offsetTop + r * cellH + 2;
                int w = cellW - 4;
                int h = cellH - 4;

                if (e instanceof PlantaVenenosa) {
                    g2.setColor(new Color(22, 101, 52)); // Verde oscuro para Planta Venenosa
                    g2.fillRect(x, y, w, h);
                } else if (e instanceof Planta) {
                    g2.setColor(new Color(34, 197, 94)); // Verde para Planta
                    g2.fillRect(x, y, w, h);
                } else if (e instanceof Conejo) {
                    g2.setColor(new Color(249, 115, 22)); // Naranja para Conejo
                    g2.fillRect(x, y, w, h);
                } else if (e instanceof Lobo) {
                    g2.setColor(new Color(239, 68, 68)); // Rojo para Lobo
                    g2.fillRect(x, y, w, h);
                }
            }
        }
    }
}
