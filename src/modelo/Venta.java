package modelo;

import java.util.ArrayList;

/**
 * Clase Venta: gestiona los ítems de una transacción y el cálculo del total.
 */
public class Venta {
    private int idVenta;
    private ArrayList<Producto> items;
    private double total;

    public Venta(int idVenta) {
        this.idVenta = idVenta;
        this.items = new ArrayList<>();
        this.total = 0.0;
    }

    public void agregarItem(Producto p) {
        items.add(p);
        calcularTotal();
    }

    // Cálculo del total con bucle for-each
    public double calcularTotal() {
        total = 0.0;
        for (Producto p : items) {
            total += p.getPrecio();
        }
        return total;
    }

    public int getIdVenta() { return idVenta; }
    public void setIdVenta(int idVenta) { this.idVenta = idVenta; }

    public ArrayList<Producto> getItems() { return items; }
    public double getTotal() { return total; }
}