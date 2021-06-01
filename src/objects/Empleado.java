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
    private Cliente clienteAtendido = new Cliente(0, 0, configuracion);
    private double horaClienteAtendido = 0;
    private double[] duracionAtencionNormal = {0,0};
    private double rndNormal1;
    private double rndNormal2;
    private boolean primeroUsado;
    private boolean segundoUsado;

    public Empleado(Configuracion config) {
        this.estado = "Libre";
        this.rnd = 0;
        this.tipoAtencion = "";
        this.duracionAtencion = 0;
        this.finAtencion = 0;
        configuracion = config;
        this.rndNormal1 = 0;
        this.rndNormal2 = 0;
        this.primeroUsado = false;
        this.segundoUsado = false;
    }

    public void realizarAtencion(Cliente c, double reloj) {
        clienteAtendido = c;
        //rnd = c.getRndTipoAtencion();
        rnd = Math.random();

        if (clienteAtendido.getTipoAtencion().equals("Pedir Libro")) {
            tipoAtencion = "Pedir Libro";
            estado = "Ocupado";
            c.setEstado("Atendido");
            double lambda = 0.1666;
            duracionAtencion = Calculator.calcular_exponencial(rnd, lambda);
            finAtencion = reloj + duracionAtencion;

        } else {
            if (clienteAtendido.getTipoAtencion().equals("Devolver Libro")) {
                tipoAtencion = "Devolver Libro";
                estado = "Ocupado";
                c.setEstado("Atendido");
                if (primeroUsado == false && segundoUsado == false) {
                    rndNormal1 = Math.random();
                    rndNormal2 = Math.random();
                    duracionAtencionNormal = Calculator.calcular_normal(rndNormal1, rndNormal2, 2, 0.5);
                    duracionAtencion = duracionAtencionNormal[0];
                    primeroUsado = true;
                } else {
                    if (primeroUsado == true && segundoUsado == false) {
                        duracionAtencion = duracionAtencionNormal[1];
                        primeroUsado = false;
                        segundoUsado = false;

                    }
                }

                finAtencion = reloj + duracionAtencion;

            } else {
                if (clienteAtendido.getTipoAtencion().equals("Consulta"))// en el caso que salga el 1 por eso <=
                {
                    tipoAtencion = "Consulta";
                    estado = "Ocupado";
                    c.setEstado("Atendido");
                    duracionAtencion = Calculator.calcular_uniforme(rnd, configuracion.getConsultaDesde(), configuracion.getConsultaHasta());
                    finAtencion = reloj + duracionAtencion;
                } else {
                    System.out.println("error calculando la atencion");
                }
            }
        }

    }

    public void finalizarAtencion(double reloj) {
        if (tipoAtencion.equals("Pedir Libro")) {
            clienteAtendido.PostAtencion(reloj);

        } else {
            clienteAtendido.setEstado("Finalizado");
            //clienteAtendido.setPostAtencion("Retirarse");
            //clienteAtendido.setNroCliente(0);
        }

        horaClienteAtendido = clienteAtendido.getHoraLlegada();
        estado = "Libre";
        tipoAtencion = "";
        rnd = 0;
        duracionAtencion = 0;
        finAtencion = 0;

    }

    public void finalizarAtencionConCola(double reloj, Cliente c) {
        if (tipoAtencion.equals("Pedir Libro")) {
            clienteAtendido.PostAtencion(reloj);
            horaClienteAtendido = clienteAtendido.getHoraLlegada();
        }
        //clienteAtendido.setNroCliente(0);
        else
        {
                    //clienteAtendido.setPostAtencion("Retirarse");
                    clienteAtendido.setEstado("Finalizado");
        }

        horaClienteAtendido = clienteAtendido.getHoraLlegada();

        realizarAtencion(c, reloj);

    }

    public String getEstado() {
        return this.estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public double getRnd() {
        if (clienteAtendido.getTipoAtencion().equals("Devolver Libro")) 
        {
            return this.rndNormal1;

        }
        else
        {
            return this.rnd;
        }
        
    }

    public double getRndNormal2() {
        if (clienteAtendido.getTipoAtencion().equals("Devolver Libro"))
        {
            return this.rndNormal2;

        }
        else
        {
            return 0;
        }
    }
    
    

    public void setRnd(double rnd) {

        this.rnd = rnd;
    }

    public String getTipoAtencion() {
        return this.tipoAtencion;
    }

    public void setTipoAtencion(String tipoAtencion) {
        this.tipoAtencion = tipoAtencion;
    }

    public double getDuracionAtencion() {
        return this.duracionAtencion;
    }

    public void setDuracionAtencion(double duracionAtencion) {
        this.duracionAtencion = duracionAtencion;
    }

    public double getFinAtencion() {
        return this.finAtencion;
    }

    public void setFinAtencion(double finAtencion) {
        this.finAtencion = finAtencion;
    }

    public int getNroClienteAtendido() {
        return clienteAtendido.getNroCliente();
    }

    public double getRNDClienteAtendido() {
        return clienteAtendido.getRndTipoAtencion();
    }

    public boolean getFlagYaLeyoClienteAtendido() {
        return clienteAtendido.getYaLeyo();
    }

    public double getHoraClienteAtendido() {
        return horaClienteAtendido;
    }
}
