package objects;

import java.util.ArrayList;

/**
 *
 * @author Nico Ferraro
 */
public class ColaEspera {
    private ArrayList<Cliente> cola = new ArrayList<Cliente>();
    
    
    public Cliente obtenerProximo()
    {
        return cola.get(0);
    }
    
    public void agregarACola(Cliente c)
    {
        cola.add(c);
    }
    
    public int getLargoCola()
    {
        return cola.size();
    }
    
    public boolean hayCola()
    {
        if(cola.size() > 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
  
    public void reducirCola()
    {
        try
        {
            cola.remove(0);
        }
        catch(Exception e){};
    }
    
    public void vaciarCola()
    {
        cola.clear();
    }
}
