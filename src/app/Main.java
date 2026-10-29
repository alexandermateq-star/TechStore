package app;

import controlador.ControladorProductos;
import controlador.ControladorVentas;
import modelo.Inventario;
import modelo.Producto;
import vista.VentanaPrincipal;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Punto de entrada del sistema TechStore.
 * Inicializa el modelo, la vista y los controladores (MVC).
 */
public class Main {
    public static void main(String[] args) {
        // Look and Feel del sistema operativo (opcional, mejora apariencia)
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) { }

        SwingUtilities.invokeLater(() -> {
            // 1. MODELO
            Inventario inventario = new Inventario();
            // Datos de prueba opcionales
            inventario.agregarProducto(new Producto(1, "Laptop HP", 2500.0, 10));
            inventario.agregarProducto(new Producto(2, "Mouse Logitech", 50.0, 50));
            inventario.agregarProducto(new Producto(3, "Teclado Mecánico", 150.0, 30));

            // 2. VISTA
            VentanaPrincipal ventana = new VentanaPrincipal();

// Declara explícitamente el tipo para forzar al compilador
            vista.PanelProductos pp = ventana.panelProductos;
            vista.PanelVentas pv = ventana.panelVentas;

            new ControladorProductos(pp, inventario);
            new ControladorVentas(pv, inventario);

            // Mostrar ventana
            ventana.setVisible(true);
        });
    }
}
