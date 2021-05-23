package objects;

/**
 *
 * @author Nico Ferraro
 */
public class Cliente {
    private int nroCliente;
    private String estado;
    private String tipoAtencion;
    private double rndPostAtencion;
    private String postAtencion = "";
    private double finLectura = 0;
    
    
    public Cliente(){
        this.nroCliente = 1;
        this.estado = "Atendido";
        this.tipoAtencion = "";
        this.postAtencion = "";
        this.finLectura = 0;
        this.rndPostAtencion = 0;
    }
    
    public int getNroCliente(){
        return this.nroCliente;
    }
    
    public void setNroCliente(int cliente){
        this.nroCliente = cliente;
    }
    
    public String getEstado(){
        return this.estado;
    }
    
    public void setEstado(String estado){
        this.estado = estado;
    }
    
    public String getTipoAtencion(){
        return this.tipoAtencion;
    }
    
    public void setTipoAtencion(String tipoAtencion){
        this.tipoAtencion = tipoAtencion;
    }
    
    public double getRndPost(){
        return this.rndPostAtencion;
    }
    
    public void setRndPost(double rndPostAtencion){
        this.rndPostAtencion = rndPostAtencion;
    }
    
    public String getPostAtencion(){
        return this.postAtencion;
    }
    
    public void setPostAtencion(String postAtencion){
        this.postAtencion = postAtencion;
    }
    
    public double getFinLectura(){
        return this.finLectura;
    }
    
    public void setFinLectura(double finLectura){
        this.finLectura = finLectura;
    }
}
