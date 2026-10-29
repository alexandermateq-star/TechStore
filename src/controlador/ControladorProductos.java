package controlador;

import modelo.Inventario;
import modelo.Producto;
import vista.PanelProductos;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controlador de productos: conecta la vista con el modelo.
 * Aplica manejo de excepciones try-catch-finally.
 */
public class ControladorProductos implements ActionListener {
    private PanelProductos vista;
    private Inventario inventario;

    public ControladorProductos(PanelProductos vista, Inventario inventario) {
        this.vista = vista;
        this.inventario = inventario;

        vista.btnAgregar.addActionListener(this);
        vista.btnBuscar.addActionListener(this);
        vista.btnEliminar.addActionListener(this);
        vista.btnLimpiar.addActionListener(this);

        vista.actualizarTabla(inventario.getProductos());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if (src == vista.btnAgregar)     agregarProducto();
        else if (src == vista.btnBuscar) buscarProducto();
        else if (src == vista.btnEliminar) eliminarProducto();
        else if (src == vista.btnLimpiar)  vista.limpiarCampos();
    }

    private void agregarProducto() {
        try {
            int id = Integer.parseInt(vista.txtId.getText().trim());
            String nombre = vista.txtNombre.getText().trim();
            double precio = Double.parseDouble(vista.txtPrecio.getText().trim());
            int stock = Integer.parseInt(vista.txtStock.getText().trim());

            // Validaciones con condicionales
            if (nombre.isEmpty()) {
                throw new IllegalArgumentException("El nombre no puede estar vacío.");
            }
            if (precio <= 0) {
                throw new IllegalArgumentException("El precio debe ser mayor a 0.");
            }
            if (stock < 0) {
                throw new IllegalArgumentException("El stock no puede ser negativo.");
            }

            Producto p = new Producto(id, nombre, precio, stock);
            if (inventario.agregarProducto(p)) {
                vista.mostrarMensaje("✅ Producto agregado correctamente.");
                vista.actualizarTabla(inventario.getProductos());
                vista.limpiarCampos();
            } else {
                vista.mostrarMensaje("⚠️ Ya existe un producto con ese ID.");
            }
        } catch (NumberFormatException ex) {
            vista.mostrarMensaje("❌ Error: Ingrese valores numéricos válidos en ID, Precio y Stock.");
        } catch (IllegalArgumentException ex) {
            vista.mostrarMensaje("❌ " + ex.getMessage());
        } catch (Exception ex) {
            vista.mostrarMensaje("❌ Error inesperado: " + ex.getMessage());
        } finally {
            // Bloque finally: siempre se ejecuta
            System.out.println("[LOG] Intento de registro de producto finalizado.");
        }
    }

    private void buscarProducto() {
        try {
            int id = Integer.parseInt(vista.txtId.getText().trim());
            Producto p = inventario.buscarProducto(id);
            if (p != null) {
                vista.txtNombre.setText(p.getNombre());
                vista.txtPrecio.setText(String.valueOf(p.getPrecio()));
                vista.txtStock.setText(String.valueOf(p.getStock()));
                vista.mostrarMensaje("🔍 Producto encontrado:\n" + p);
            } else {
                vista.mostrarMensaje("⚠️ No se encontró producto con ID: " + id);
            }
        } catch (NumberFormatException ex) {
            vista.mostrarMensaje("❌ Ingrese un ID numérico válido para buscar.");
        }
    }

    private void eliminarProducto() {
        try {
            int id = Integer.parseInt(vista.txtId.getText().trim());
            if (inventario.eliminarProducto(id)) {
                vista.mostrarMensaje("🗑️ Producto eliminado.");
                vista.actualizarTabla(inventario.getProductos());
                vista.limpiarCampos();
            } else {
                vista.mostrarMensaje("⚠️ No existe producto con ese ID.");
            }
        } catch (NumberFormatException ex) {
            vista.mostrarMensaje("❌ Ingrese un ID numérico válido.");
        }
    }
}