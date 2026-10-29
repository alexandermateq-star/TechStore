package controlador;

import modelo.Inventario;
import modelo.Producto;
import modelo.Venta;
import vista.PanelVentas;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controlador del punto de venta.
 */
public class ControladorVentas implements ActionListener {
    private PanelVentas vista;
    private Inventario inventario;
    private Venta ventaActual;

    public ControladorVentas(PanelVentas vista, Inventario inventario) {
        this.vista = vista;
        this.inventario = inventario;
        this.ventaActual = new Venta(0);

        vista.btnAgregarItem.addActionListener(this);
        vista.btnFinalizar.addActionListener(this);
        vista.btnLimpiar.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src == vista.btnAgregarItem)  agregarItem();
        else if (src == vista.btnFinalizar) finalizarVenta();
        else if (src == vista.btnLimpiar)   limpiarVenta();
    }

    private void agregarItem() {
        try {
            int id = Integer.parseInt(vista.txtIdProducto.getText().trim());
            int cantidad = Integer.parseInt(vista.txtCantidad.getText().trim());

            if (cantidad <= 0) {
                throw new IllegalArgumentException("La cantidad debe ser mayor a 0.");
            }

            Producto p = inventario.buscarProducto(id);
            if (p == null) {
                vista.mostrarMensaje("⚠️ Producto no encontrado (ID: " + id + ").");
                return;
            }
            // Validar stock (condicional)
            if (p.getStock() < cantidad) {
                vista.mostrarMensaje("⚠️ Stock insuficiente. Disponible: " + p.getStock());
                return;
            }

            // Agregar ítems (bucle for)
            for (int i = 0; i < cantidad; i++) {
                ventaActual.agregarItem(p);
            }

            vista.actualizarTabla(ventaActual.getItems());
            vista.mostrarTotal(ventaActual.calcularTotal());
            vista.txtIdProducto.setText("");
            vista.txtCantidad.setText("");
        } catch (NumberFormatException ex) {
            vista.mostrarMensaje("❌ Ingrese valores numéricos válidos.");
        } catch (IllegalArgumentException ex) {
            vista.mostrarMensaje("❌ " + ex.getMessage());
        }
    }

    private void finalizarVenta() {
        try {
            if (ventaActual.getItems().isEmpty()) {
                vista.mostrarMensaje("⚠️ No hay ítems en la venta actual.");
                return;
            }
            double total = ventaActual.calcularTotal();
            inventario.registrarVenta(ventaActual);

            vista.mostrarMensaje(String.format(
                    "✅ Venta N° %d registrada.\nTotal: S/ %.2f",
                    ventaActual.getIdVenta(), total));
            vista.actualizarTabla(inventario.getProductos() != null
                    ? new java.util.ArrayList<>() : new java.util.ArrayList<>());
            // Refrescar tabla de productos en caso de otra vista abierta
            ventaActual = new Venta(0);
            vista.limpiarCampos();
        } catch (Exception ex) {
            vista.mostrarMensaje("❌ Error al finalizar venta: " + ex.getMessage());
        }
    }

    private void limpiarVenta() {
        ventaActual = new Venta(0);
        vista.limpiarCampos();
    }
}
