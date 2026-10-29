package vista;

import javax.swing.*;
import java.awt.*;

/**
 * Ventana principal con pestañas (JTabbedPane) para Productos y Ventas.
 */
public class VentanaPrincipal extends JFrame {
    public PanelProductos panelProductos;
    public PanelVentas panelVentas;

    public VentanaPrincipal() {
        setTitle("TechStore Local - Sistema de Gestión");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        panelProductos = new PanelProductos();
        panelVentas = new PanelVentas();

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("📦 Productos", panelProductos);
        tabs.addTab("🛒 Punto de Venta", panelVentas);

        add(tabs, BorderLayout.CENTER);
    }
}