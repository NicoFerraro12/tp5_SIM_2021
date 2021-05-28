package objects;

import model.Configuracion;

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
    private double rndTipoAtencion = 0;
    private Boolean yaLeyo = false;
    private double horaLlegada = 0;
    private Configuracion configuracion = Configuracion.getConfiguracion();

    public Cliente(int nroc, double hora, Configuracion config) {
        this.nroCliente = nroc;
        this.estado = "Atendido";
        this.tipoAtencion = "";
        this.postAtencion = "";
        this.finLectura = 0;
        this.rndPostAtencion = 0;
        this.rndTipoAtencion = 0;
        this.yaLeyo = false;
        this.horaLlegada = hora;
        configuracion = config;
        TipoAtencion();
    }

    public void PostAtencion(double reloj) {

        rndPostAtencion = Math.random();
        if (rndPostAtencion <= configuracion.getPrestamoQuedarAcum()) {
            yaLeyo = true;
            finLectura = configuracion.getPromedioPermanencia() + reloj;
            postAtencion = "Lectura";
            estado = "Leyendo";

        } else {
            nroCliente = 0;
            estado = "Finalizado";
            postAtencion = "Retirarse";
        }

    }

    private void TipoAtencion() {
        rndTipoAtencion = Math.random();

        if (rndTipoAtencion < configuracion.getProbPedirLibroAcum()) {
            tipoAtencion = "Pedir Libro";

        } else {
            if (rndTipoAtencion < configuracion.getProbDevolverLibroAcum()) {
                tipoAtencion = "Devolver Libro";

            } else {
                if (rndTipoAtencion <= configuracion.getProbConsultarAcum())// en el caso que salga el 1 por eso <=
                {
                    tipoAtencion = "Consulta";

                } else {
                    System.out.println("error calculando la atencion");
                }
            }
        }
    }

    public Boolean getYaLeyo() {
        return yaLeyo;
    }

    public int getNroCliente() {
        return this.nroCliente;
    }

    public void setNroCliente(int cliente) {
        this.nroCliente = cliente;
    }

    public String getEstado() {
        return this.estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTipoAtencion() {
        return this.tipoAtencion;
    }

    public void setTipoAtencion(String tipoAtencion) {
        this.tipoAtencion = tipoAtencion;
    }

    public double getRndPost() {
        return this.rndPostAtencion;
    }

    public void setRndPost(double rndPostAtencion) {
        this.rndPostAtencion = rndPostAtencion;
    }

    public String getPostAtencion() {
        return this.postAtencion;
    }

    public void setPostAtencion(String postAtencion) {
        this.postAtencion = postAtencion;
    }

    public double getFinLectura() {
        return this.finLectura;
    }

    public void setFinLectura(double finLectura) {
        this.finLectura = finLectura;
    }

    public double getRndTipoAtencion() {
        return rndTipoAtencion;
    }

    public void setYaLeyo(Boolean yaLeyo) {
        this.yaLeyo = yaLeyo;
    }

    public double getHoraLlegada() {
        return horaLlegada;
    }
}
