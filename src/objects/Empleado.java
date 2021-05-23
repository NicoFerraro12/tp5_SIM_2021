package objects;

/**
 *
 * @author Nico Ferraro
 */
public class Empleado {
    private String estado;
    private double rnd;
    private String tipoAtencion;
    private double duracionAtencion;
    private double finAtencion;
    
    
    public Empleado(){
        this.estado = "Libre";
        this.rnd = 0;
        this.tipoAtencion = "";
        this.duracionAtencion = 0;
        this.finAtencion = 0;
    }
    
    public String getEstado(){
        return this.estado;
    }
    
    public void setEstado(String estado){
        this.estado = estado;
    }
    
    public double getRnd(){
        return this.rnd;
    }
    
    public void setRnd(double rnd){
        this.rnd = rnd;
    }
    
    public String getTipoAtencion(){
        return this.tipoAtencion;
    }
    
    public void setTipoAtencion(String tipoAtencion){
        this.tipoAtencion = tipoAtencion;
    }
    
    public double getDuracionAtencion(){
        return this.duracionAtencion;
    }
    
    public void setDuracionAtencion(double duracionAtencion){
        this.duracionAtencion = duracionAtencion;
    }
    
    public double getFinAtencion(){
        return this.finAtencion;
    }
    
    public void setFinAtencion(double finAtencion){
        this.finAtencion = finAtencion;
    }
}
