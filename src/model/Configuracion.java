package model;

/**
 *
 * @author Nico Ferraro
 */
public class Configuracion {
    
    private static Configuracion instancia;
    
    private int llegada_cliente;
    private int consulta_desde;
    private int consulta_hasta;
    private int promedio_permanencia;
    
    private int prob_pedir_lib;
    private int prob_devolver_lib;
    private int prob_consultar;
    
    private int libPrestado_retira;
    private int libPrestado_leer;
    
    private double tiempo_simulacion;
    private int cantidad_simulacion;
    
    public Configuracion(){
        this.llegada_cliente = 4;
        this.consulta_desde = 2;
        this.consulta_hasta = 5;
        this.promedio_permanencia = 30;
        this.prob_pedir_lib = 45;
        this.prob_devolver_lib = 45;
        this.prob_consultar = 10;
        this.libPrestado_retira = 60;
        this.libPrestado_leer = 40;
        this.tiempo_simulacion = 300;
        this.cantidad_simulacion = 100000;
    }
    public static Configuracion getConfiguracion()
    {
        if (instancia == null)
        {
            instancia = new Configuracion();
        }
        return instancia;
    }
    
    public int getLlegadaCliente(){
        return this.llegada_cliente;
    }
    
    public void setLlegadaCliente(int llegada){
        this.llegada_cliente = llegada;
    }
    
    public int getConsultaDesde(){
        return this.consulta_desde;
    }
    
    public void setConsultaDesde(int desde){
        this.consulta_desde = desde;
    }
    
    public int getConsultaHasta(){
        return this.consulta_hasta;
    }
    
    public void setConsultaHasta(int hasta){
        this.consulta_hasta = hasta;
    }
    
    public int getPromedioPermanencia(){
        return this.promedio_permanencia;
    }
    
    public void SetPromedioPermanencia(int promedio){
        this.promedio_permanencia = promedio;
    }
    
    public int getProbPedirLibro(){
        return this.prob_pedir_lib;
    }
    
    public void setProbPedirLibro(int prob_pedir){
        this.prob_pedir_lib = prob_pedir;
    }
    
    public int getProbDevolverLibro(){
        return this.prob_devolver_lib;
    }
    
    public void setProbDevolverLibro(int prob_dev){
        this.prob_devolver_lib = prob_dev;
    }
    
    public int getProbConsultar(){
        return this.prob_consultar;
    }
    
    public void setProbConsultar(int prob_consulta){
        this.prob_consultar = prob_consulta;
    }
    
    public int getPrestamoRetira(){
        return this.libPrestado_retira;
    }
    
    public void setPrestamoRetira(int retira){
        this.libPrestado_retira = retira;
    }
    
    public int getPrestamoQuedar(){
        return this.libPrestado_leer;
    }
    
    public void setPrestamoQuedar(int leer){
        this.libPrestado_leer = leer;
    }
    
    public double getTiempoSimulacion(){
        return this.tiempo_simulacion;
    }
    
    public void setTiempoSimulacion(int tiempo){
        this.tiempo_simulacion = tiempo;
    }
    
    public int getCantidadSimulacion(){
        return this.cantidad_simulacion;
    }
    
    public void setCantidadSimulacion(int cantidad){
        this.cantidad_simulacion = cantidad;
    }
}
