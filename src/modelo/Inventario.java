package modelo;

import java.util.ArrayList;

/**
 * Clase Inventario: gestiona la colección de productos y ventas.
 * Única responsabilidad: administrar los datos del negocio.
 */
public class Inventario {
    private ArrayList<Producto> productos;
    private ArrayList<Venta> ventas;
    private int contadorVentas;

    public Inventario() {
        productos = new ArrayList<>();
        ventas = new ArrayList<>();
        contadorVentas = 1;
    }

    // Agregar producto (valida ID duplicado)
    public boolean agregarProducto(Producto p) {
        if (buscarProducto(p.getId()) != null) {
            return false; // ID ya existe
        }
        productos.add(p);
        return true;
    }

    // Buscar producto por ID (bucle while)
    public Producto buscarProducto(int id) {
        int i = 0;
        while (i < productos.size()) {
            if (productos.get(i).getId() == id) {
                return productos.get(i);
            }
            i++;
        }
        return null;
    }

    // Actualizar stock
    public boolean actualizarStock(int id, int nuevaCantidad) {
        Producto p = buscarProducto(id);
        if (p != null) {
            p.setStock(nuevaCantidad);
            return true;
        }
        return false;
    }

    // Eliminar producto
    public boolean eliminarProducto(int id) {
        Producto p = buscarProducto(id);
        if (p != null) {
            productos.remove(p);
            return true;
        }
        return false;
    }

    // Registrar venta (genera ID automático y descuenta stock)
    public Venta registrarVenta(Venta v) {
        v.setIdVenta(contadorVentas++);
        // Descontar stock de cada ítem
        for (Producto p : v.getItems()) {
            Producto original = buscarProducto(p.getId());
            if (original != null) {
                original.setStock(original.getStock() - 1);
            }
        }
        ventas.add(v);
        return v;
    }

    public ArrayList<Producto> getProductos() { return productos; }
    public ArrayList<Venta> getVentas() { return ventas; }
}
