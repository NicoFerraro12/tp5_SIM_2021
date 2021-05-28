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
    
    private double prob_pedir_lib;
    private double prob_devolver_lib;
    private double prob_consultar;
    private double prob_pedir_lib_acum;
    private double prob_devolver_lib_acum;
    private double prob_consultar_acum;
    
    private double libPrestado_retira;
    private double libPrestado_leer;
    private double libPrestado_retira_acum;
    private double libPrestado_leer_acum;
    
    private int rango_desde;
    private int rango_hasta;
    
    private double tiempo_simulacion;
    private int cantidad_simulacion;
    
    public Configuracion(){
        this.llegada_cliente = 4;
        this.consulta_desde = 2;
        this.consulta_hasta = 5;
        this.promedio_permanencia = 30;
        this.prob_pedir_lib = 0.45;
        this.prob_devolver_lib = 0.45;
        this.prob_consultar = 0.10;
        this.prob_pedir_lib_acum = this.prob_pedir_lib;
        this.prob_devolver_lib_acum = this.prob_pedir_lib_acum + this.prob_devolver_lib;
        this.prob_consultar_acum = this.prob_devolver_lib_acum + this.prob_consultar;
        this.libPrestado_retira = 0.60;
        this.libPrestado_leer = 0.40;
        this.libPrestado_retira_acum = this.libPrestado_retira;
        this.libPrestado_leer_acum = this.libPrestado_retira + this.libPrestado_leer;
        this.tiempo_simulacion = 300;
        this.cantidad_simulacion = 1000;
        this.rango_desde = 0;
        this.rango_hasta = 1000;
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
    
    public void setPromedioPermanencia(int promedio){
        this.promedio_permanencia = promedio;
    }
    
    public double getProbPedirLibro(){
        return this.prob_pedir_lib;
    }
    
    public void setProbPedirLibro(double prob_pedir){
        this.prob_pedir_lib = prob_pedir;
    }
    
    public double getProbDevolverLibro(){
        return this.prob_devolver_lib;
    }
    
    public void setProbDevolverLibro(double prob_dev){
        this.prob_devolver_lib = prob_dev;
    }
    
    public double getProbConsultar(){
        return this.prob_consultar;
    }
    
    public void setProbConsultar(double prob_consulta){
        this.prob_consultar = prob_consulta;
    }
    
    public double getProbPedirLibroAcum(){
        return this.prob_pedir_lib_acum;
    }
    
    public void setProbPedirLibroAcum(double prob_pedir){
        this.prob_pedir_lib_acum = prob_pedir;
    }
    
    public double getProbDevolverLibroAcum(){
        return this.prob_devolver_lib_acum;
    }
    
    public void setProbDevolverLibroAcum(double prob_dev){
        this.prob_devolver_lib_acum = prob_dev;
    }
    
    public double getProbConsultarAcum(){
        return this.prob_consultar_acum;
    }
    
    public void setProbConsultarAcum(double prob_consulta){
        this.prob_consultar_acum = prob_consulta;
    }
          
    public double getPrestamoRetira(){
        return this.libPrestado_retira;
    }
    
    public void setPrestamoRetira(double retira){
        this.libPrestado_retira = retira;
    }
    
    public double getPrestamoQuedar(){
        return this.libPrestado_leer;
    }
    
    public void setPrestamoQuedar(double leer){
        this.libPrestado_leer = leer;
    }
    
     public double getPrestamoRetiraAcum(){
        return this.libPrestado_retira_acum;
    }
    
    public void setPrestamoRetiraAcum(double retira){
        this.libPrestado_retira_acum = retira;
    }
    
    public double getPrestamoQuedarAcum(){
        return this.libPrestado_leer_acum;
    }
    
    public void setPrestamoQuedarAcum(double leer){
        this.libPrestado_leer_acum = leer;
    }
    
    public double getTiempoSimulacion(){
        return this.tiempo_simulacion;
    }
    
    public void setTiempoSimulacion(double tiempo){
        this.tiempo_simulacion = tiempo;
    }
    
    public int getCantidadSimulacion(){
        return this.cantidad_simulacion;
    }
    
    public void setCantidadSimulacion(int cantidad){
        this.cantidad_simulacion = cantidad;
    }
    
    public int getRangoDesde(){
        return this.rango_desde;
    }
    
    public void setRangoDesde(int desde){
        this.rango_desde = desde;
    }
    
    public int getRangoHasta(){
        return this.rango_hasta;
    }
    
    public void setRangoHasta(int hasta){
        this.rango_hasta = hasta;
    }
}
