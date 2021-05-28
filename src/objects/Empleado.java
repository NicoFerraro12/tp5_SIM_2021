package objects;

import model.Configuracion;

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
    private Configuracion configuracion = Configuracion.getConfiguracion();
    private Cliente clienteAtendido = new Cliente(0,0, configuracion);
    private double horaClienteAtendido = 0;
    
    
    public Empleado(Configuracion config){
        this.estado = "Libre";
        this.rnd = 0;
        this.tipoAtencion = "";
        this.duracionAtencion = 0;
        this.finAtencion = 0;
        configuracion = config;
    }
    
    public void realizarAtencion(Cliente c, double reloj)
    {
        Calculator calculadora = new Calculator();
        clienteAtendido = c;
        rnd = c.getRndTipoAtencion();
        //rnd = Math.random();
        
        if (clienteAtendido.getTipoAtencion() == "Pedir Libro")
        {
            tipoAtencion = "Pedir Libro";
            estado = "Ocupado";
            c.setEstado("Atendido");
            double lambda = 0.1666;
            duracionAtencion = calculadora.calcular_exponencial(rnd, lambda);
            finAtencion = reloj + duracionAtencion;
                       
        }
        else
        {
            if(clienteAtendido.getTipoAtencion() == "Devolver Libro")
            {
                tipoAtencion = "Devolver Libro";
                estado = "Ocupado";
                c.setEstado("Atendido");
                duracionAtencion = calculadora.calcular_normal(rnd, 2, 0.5);
                finAtencion = reloj + duracionAtencion;
                /*if(c.getYaLeyo() == true)
                {
                    c.setNroCliente(0);
                }*/
            }
            else
            {
                if(clienteAtendido.getTipoAtencion() == "Consulta")// en el caso que salga el 1 por eso <=
                {
                    tipoAtencion = "Consulta";
                    estado = "Ocupado";
                    c.setEstado("Atendido");
                    duracionAtencion = calculadora.calcular_uniforme(rnd, 2,5);
                    finAtencion = reloj + duracionAtencion;
                }
                else
                {
                    System.out.println("error calculando la atencion");
                }
            }
        }
        
        
    }
    
    public void finalizarAtencion(double reloj)
    {
        if(tipoAtencion == "Pedir Libro")
        {
            clienteAtendido.PostAtencion(reloj);
        }
        else
        {
            clienteAtendido.setEstado("Finalizado");
            clienteAtendido.setPostAtencion("Retirarse");
            clienteAtendido.setNroCliente(0);
            horaClienteAtendido = clienteAtendido.getHoraLlegada();
        }
        
        estado = "Libre";
        tipoAtencion = "";
        rnd = 0;
        duracionAtencion = 0;
        finAtencion = 0;
        
        
    }
    
    public void finalizarAtencionConCola(double reloj, Cliente c)
    {
        if(tipoAtencion == "Pedir Libro")
        {
            clienteAtendido.PostAtencion(reloj);
        }
        clienteAtendido.setNroCliente(0);
        clienteAtendido.setPostAtencion("Retirarse");
        horaClienteAtendido = c.getHoraLlegada();
        
        realizarAtencion(c, reloj);
        
        
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
    
    public int getNroClienteAtendido()
    {
        return clienteAtendido.getNroCliente();
    }
    
    public boolean getFlagYaLeyoClienteAtendido()
    {
        return clienteAtendido.getYaLeyo();
    }
    
    public double getHoraClienteAtendido()
    {
        return horaClienteAtendido;
    }
}
