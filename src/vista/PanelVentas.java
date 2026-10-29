package vista;

import modelo.Producto;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

/**
 * Vista del punto de venta.
 */
public class PanelVentas extends JPanel {
    public JTextField txtIdProducto, txtCantidad;
    public JButton btnAgregarItem, btnFinalizar, btnLimpiar;
    public JTable tablaVenta;
    public DefaultTableModel modeloTabla;
    public JLabel lblTotal;

    public PanelVentas() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // ---------- Formulario ----------
        JPanel panelForm = new JPanel(new GridLayout(2, 2, 5, 5));
        panelForm.add(new JLabel("ID Producto:"));
        txtIdProducto = new JTextField();
        panelForm.add(txtIdProducto);

        panelForm.add(new JLabel("Cantidad:"));
        txtCantidad = new JTextField();
        panelForm.add(txtCantidad);

        // ---------- Botones ----------
        JPanel panelBotones = new JPanel(new FlowLayout());
        btnAgregarItem = new JButton("Agregar Ítem");
        btnFinalizar = new JButton("Finalizar Venta");
        btnLimpiar = new JButton("Limpiar");
        panelBotones.add(btnAgregarItem);
        panelBotones.add(btnFinalizar);
        panelBotones.add(btnLimpiar);

        // ---------- Total ----------
        JPanel panelTotal = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        lblTotal = new JLabel("TOTAL: S/ 0.00");
        lblTotal.setFont(new Font("Arial", Font.BOLD, 16));
        panelTotal.add(lblTotal);

        // ---------- Tabla ----------
        modeloTabla = new DefaultTableModel(
                new Object[]{"ID", "Nombre", "Precio", "Cant."}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tablaVenta = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tablaVenta);

        // ---------- Ensamblado ----------
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.add(panelForm, BorderLayout.NORTH);
        panelSuperior.add(panelBotones, BorderLayout.CENTER);
        panelSuperior.add(panelTotal, BorderLayout.SOUTH);

        add(panelSuperior, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
    }

    public void actualizarTabla(ArrayList<Producto> items) {
        modeloTabla.setRowCount(0);
        for (Producto p : items) {
            modeloTabla.addRow(new Object[]{
                    p.getId(), p.getNombre(), p.getPrecio(), 1
            });
        }
    }

    public void mostrarTotal(double total) {
        lblTotal.setText(String.format("TOTAL: S/ %.2f", total));
    }

    public void limpiarCampos() {
        txtIdProducto.setText("");
        txtCantidad.setText("");
        modeloTabla.setRowCount(0);
        lblTotal.setText("TOTAL: S/ 0.00");
    }

    public void mostrarMensaje(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }
}
