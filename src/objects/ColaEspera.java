package objects;

import java.util.ArrayList;
import model.Configuracion;

/**
 *
 * @author Nico Ferraro
 */
public class ColaEspera {

    private ArrayList<Cliente> cola;
    private Configuracion config = Configuracion.getConfiguracion();

    public ColaEspera(Configuracion config) {
        this.cola = new ArrayList<Cliente>();
        this.config = config;
    }

    public Cliente obtenerProximo() {
        return cola.get(0);
    }

    public void agregarACola(Cliente c) {
        cola.add(c);
    }

    public int getLargoCola() {
        return cola.size();
    }

    public boolean hayCola() {
        if (cola.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public void reducirCola() {
        try {
            cola.remove(0);
        } catch (Exception e) {
        };
    }

    public void vaciarCola() {
        cola.clear();
    }

    public Cliente finalizacionLectura() {
        double minimo = 0;
        Cliente aux = new Cliente(0,0, config);

        for (int i = 0; i < cola.size(); i++) {
            if (cola.get(i).getNroCliente() != 0 && cola.get(i).getFinLectura() != 0) {
                minimo = cola.get(i).getFinLectura();
                aux = cola.get(i);
                break;
            }
        }

        for (int i = 0; i < cola.size(); i++) {
            if (cola.get(i).getFinLectura() < minimo && cola.get(i).getNroCliente() != 0 && cola.get(i).getFinLectura() != 0) {
                minimo = cola.get(i).getFinLectura();
                aux = cola.get(i);
            }
        }

        return aux;
    }

    public String toString() {
        String linea = "";
        for (int i = 0; i < cola.size(); i++) {
            if (cola.get(i).getNroCliente() != 0) {
                linea = linea + " || Cliente nº:" + cola.get(i).getNroCliente() + ", Estado:" + cola.get(i).getEstado() + ", Fin Lectura:" + cola.get(i).getFinLectura();

            }
        }
        return linea;
    }
}
