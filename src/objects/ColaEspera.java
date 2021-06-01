package objects;

import java.text.DecimalFormat;
import java.util.ArrayList;
import static jdk.nashorn.internal.objects.NativeString.toUpperCase;
import model.Configuracion;

/**
 *
 * @author Nico Ferraro
 */
public class ColaEspera {

    private final ArrayList<Cliente> cola;
    private Configuracion config = Configuracion.getConfiguracion();
    private final DecimalFormat formato = new DecimalFormat("0.00");

    public ColaEspera(Configuracion config) {
        this.cola = new ArrayList<>();
        this.config = config;
    }

    public Cliente obtenerProximo() {
        return cola.get(0);
    }
    
    public Cliente obtenerCliente(int c)
    {
        return cola.get(c);
    }

    public void agregarACola(Cliente c) {
        cola.add(c);
    }

    public int getLargoCola() {
        return cola.size();
    }

    public boolean hayCola() {
        return cola.size() > 0;
    }

    public void reducirCola() {
        try {
            cola.remove(0);
        } catch (Exception e) {
        }
    }

    public void vaciarCola() {
        cola.clear();
    }

    public Cliente finalizacionLectura() {
        double minimo = 0;
        Cliente aux = new Cliente(0, 0, config);

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

    @Override
    public String toString() {
        String linea = "";
        for (int i = 0; i < cola.size(); i++) {
            if (cola.get(i).getNroCliente() != 0) {
                linea = linea + " || Cliente nº: " + cola.get(i).getNroCliente() + ", Estado: " + toUpperCase(cola.get(i).getEstado());
                if (cola.get(i).getRndPost() != 0) {
                    linea = linea + ", PostA: " + toUpperCase(cola.get(i).getPostAtencion()) + ", RND: " + formato.format(cola.get(i).getRndPost());
                }

            }
        }
        return linea;
    }
}
