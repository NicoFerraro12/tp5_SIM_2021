package front;

import control.*;
import java.awt.Color;
import java.awt.Component;
import java.text.DecimalFormat;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import model.Configuracion;
import objects.Cliente;
import objects.ColaEspera;
import objects.Empleado;

public class VentanaPrincipal extends javax.swing.JFrame {

    private final Controller controlador;
    Configuracion config = Configuracion.getConfiguracion();
    DefaultTableModel dtmProbAtencion;
    DefaultTableModel dtmProbPostAtencion;
    private final DecimalFormat formato = new DecimalFormat("0.0000");
    private Empleado empleado1 = new Empleado(config);
    private Empleado empleado2 = new Empleado(config);
    private ColaEspera cola = new ColaEspera(config);
    private ColaEspera ListaClientes = new ColaEspera(config);
    private double reloj = 0;
    private String evento = "";
    private double proximaLlegada = 0;
    private double acumuladorPersonasAtendidas = 0;
    private double acumuladorTiempoAtendido = 0;
    private int contadorClientes = 1;
    private DefaultTableModel tabla;
    private final Object[] linea = new Object[20];
    private final Object[] Columnas = {"Id", "Evento", "Cliente nº", "Reloj", "Proxima Llegada", "Estado Emp1", "RND TA", "Tipo Atencion", "RND Duracion Atencion", "Duracion Atencion", "Fin Atencion", "Estado Emp2", "RND TA", "tipo Atencion", "RND Duracion Atencion", "Duracion Atencion", "Fin Atencion", "Cola", "Cantidad Personas atendidas", "Tiempo de permanencia en la biblioteca acumulado"};

    public VentanaPrincipal(Controller controlador) {
        this.controlador = controlador;
        initComponents();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setTitle("Simulación por Colas - Ejercicio N°5: Biblioteca");
        dtmProbAtencion = (DefaultTableModel) jTableProbConsulta.getModel();
        dtmProbPostAtencion = (DefaultTableModel) jTableProbPostAtencion.getModel();
    }

    public void primeraLinea(boolean cargarLinea) {
        reloj = 0;
        evento = "Inicio";
        proximaLlegada = config.getLlegadaCliente();
        cargarLinea(0, cargarLinea, 0);

    }

    private void siguienteEvento(boolean cargarLinea, int index) {
        double PL = proximaLlegada;
        double E1 = empleado1.getFinAtencion();
        double E2 = empleado2.getFinAtencion();
        double FL = ListaClientes.finalizacionLectura().getFinLectura();

        if (E1 == 0)//el empleado esta libre
        {
            E1 = 9999999;
        }
        if (E2 == 0)// esta libre
        {
            E2 = 9999999;
        }
        if (FL == 0)// no hay nadie leyendo;
        {
            FL = 9999999;
        }

        if (PL < E1 && PL < E2 && PL < FL) {
            llegadaPersona(PL, cargarLinea, index);
        } else {
            if (E1 < PL && E1 < E2 && E1 < FL) {
                finAtencion(E1, empleado1, cargarLinea, index);
            } else {
                if (E2 < PL && E2 < E1 && E2 < FL) {
                    finAtencion(E2, empleado2, cargarLinea, index);
                } else {
                    if (FL < PL && FL < E1 && FL < E2) {
                        finPermanenciaLectura(FL, ListaClientes.finalizacionLectura(), cargarLinea, index);
                    } else {
                        System.out.println("problema con decidir proximo evento" + " PL:" + PL + " E1:" + E1 + " E2:" + E2 + " FL:" + FL);

                    }
                }
            }
        }

    }

    public void finAtencion(double r, Empleado e, boolean cargarLinea, int index) {
        evento = "Fin Atencion";
        reloj = r;
        double auxiliar;
        int auxNroCliente = e.getNroClienteAtendido();

        if (cola.hayCola() == true) {
            e.finalizarAtencionConCola(reloj, cola.obtenerProximo());
            cola.reducirCola();

        } else {
            e.finalizarAtencion(reloj);
        }
        if (e.getFlagYaLeyoClienteAtendido() == false) {
            acumuladorPersonasAtendidas++;
            auxiliar = r - e.getHoraClienteAtendido();
            acumuladorTiempoAtendido += auxiliar;
        }
        cargarLinea(auxNroCliente, cargarLinea, index);

    }

    public void finPermanenciaLectura(double r, Cliente c, boolean cargarLinea, int index) {
        evento = "Fin Lectura";
        reloj = r;
        c.setFinLectura(0);
        c.setTipoAtencion("Devolver Libro");
        c.setYaLeyo(false);
        if (!empleado1.getEstado().equals("Ocupado")) {
            empleado1.realizarAtencion(c, reloj);
        } else {
            if (!empleado2.getEstado().equals("Ocupado")) {
                empleado2.realizarAtencion(c, reloj);
            } else {
                cola.agregarACola(c);
                c.setEstado("en cola");
            }

        }
        cargarLinea(c.getNroCliente(), cargarLinea, index);

    }

    public void llegadaPersona(double r, boolean cargarLinea, int index) {

        Cliente c = new Cliente(contadorClientes, r, config);
        ListaClientes.agregarACola(c);
        contadorClientes++;
        evento = "Llegada persona";
        reloj = r;
        proximaLlegada = r + config.getLlegadaCliente();
        if (!empleado1.getEstado().equals("Ocupado")) {
            empleado1.realizarAtencion(c, reloj);
        } else {
            if (!empleado2.getEstado().equals("Ocupado")) {
                empleado2.realizarAtencion(c, reloj);
            } else {
                c.setEstado("en cola");
                cola.agregarACola(c);
            }

        }
        cargarLinea(c.getNroCliente(), cargarLinea, index);

    }

    public void cargarLinea(int nroCliente, boolean cargarLinea, int index) {
        linea[0] = index;
        linea[1] = evento;
        linea[2] = nroCliente;
        linea[3] = formato.format(reloj);
        linea[4] = proximaLlegada;
        linea[5] = empleado1.getEstado();
        linea[6] = formato.format(empleado1.getRNDClienteAtendido());
        linea[7] = empleado1.getTipoAtencion();
        linea[8] = formato.format(empleado1.getRnd());
        linea[9] = formato.format(empleado1.getDuracionAtencion());
        linea[10] = formato.format(empleado1.getFinAtencion());
        linea[11] = empleado2.getEstado();
        linea[12] = formato.format(empleado2.getRNDClienteAtendido());
        linea[13] = empleado2.getTipoAtencion();
        linea[14] = formato.format(empleado2.getRnd());
        linea[15] = formato.format(empleado2.getDuracionAtencion());
        linea[16] = formato.format(empleado2.getFinAtencion());
        linea[17] = cola.getLargoCola();
        linea[18] = acumuladorPersonasAtendidas;
        linea[19] = formato.format(acumuladorTiempoAtendido);
        if (cargarLinea) {
            tabla.addRow(linea);
        }

    }

    private void cambiarColoresColumnas() {
        tablaSimulacion.getColumnModel().getColumn(3).setCellRenderer(
                new DefaultTableCellRenderer() {

                    @Override
                    public Component getTableCellRendererComponent(JTable table,
                            Object value,
                            boolean isSelected,
                            boolean hasFocus,
                            int row,
                            int column) {
                        setText(value.toString());
                        setBackground(isSelected ? Color.GRAY : Color.ORANGE);
                        return this;
                    }
                });

        for (int i = 5; i <= 10; i++) {
            tablaSimulacion.getColumnModel().getColumn(i).setCellRenderer(
                    new DefaultTableCellRenderer() {

                        @Override
                        public Component getTableCellRendererComponent(JTable table,
                                Object value,
                                boolean isSelected,
                                boolean hasFocus,
                                int row,
                                int column) {
                            setText(value.toString());
                            setBackground(isSelected ? Color.GRAY : Color.CYAN);
                            return this;
                        }
                    });
        }
        for (int i = 11; i <= 16; i++) {
            tablaSimulacion.getColumnModel().getColumn(i).setCellRenderer(
                    new DefaultTableCellRenderer() {

                        @Override
                        public Component getTableCellRendererComponent(JTable table,
                                Object value,
                                boolean isSelected,
                                boolean hasFocus,
                                int row,
                                int column) {
                            setText(value.toString());
                            setBackground(isSelected ? Color.GRAY : Color.PINK);
                            return this;
                        }
                    });
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanelConfiguracion = new javax.swing.JPanel();
        gpb_consultas = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txt_consulta_desde = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txt_consulta_hasta = new javax.swing.JTextField();
        jPanelParametros = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        txt_promPermanencia = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txt_llegada_cliente = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txt_tiempo_sim = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txt_cantidad_sim = new javax.swing.JTextField();
        jPanelRango = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txt_reloj_desde = new javax.swing.JTextField();
        txt_cant_filas = new javax.swing.JTextField();
        jPanelProb = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableProbPostAtencion = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableProbConsulta = new javax.swing.JTable();
        btn_configuracion = new javax.swing.JButton();
        btn_simular = new javax.swing.JButton();
        btn_resetSim = new javax.swing.JButton();
        jScrollPaneSimulacion = new javax.swing.JScrollPane();
        tablaSimulacion = new javax.swing.JTable();
        jPanelResultados = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        textAreaClientes = new javax.swing.JTextArea();
        lblResultado = new javax.swing.JLabel();

        gpb_consultas.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Configuracion inicial", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP));
        gpb_consultas.setBorder(javax.swing.BorderFactory.createTitledBorder("Distribucion Consultas"));

        jLabel7.setText("Desde:");

        jLabel8.setText("Hasta: ");

        javax.swing.GroupLayout gpb_consultasLayout = new javax.swing.GroupLayout(gpb_consultas);
        gpb_consultas.setLayout(gpb_consultasLayout);
        gpb_consultasLayout.setHorizontalGroup(
            gpb_consultasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(gpb_consultasLayout.createSequentialGroup()
                .addContainerGap(45, Short.MAX_VALUE)
                .addGroup(gpb_consultasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8)
                    .addComponent(jLabel7))
                .addGap(39, 39, 39)
                .addGroup(gpb_consultasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_consulta_desde, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_consulta_hasta, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30))
        );
        gpb_consultasLayout.setVerticalGroup(
            gpb_consultasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, gpb_consultasLayout.createSequentialGroup()
                .addContainerGap(25, Short.MAX_VALUE)
                .addGroup(gpb_consultasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txt_consulta_desde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(gpb_consultasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_consulta_hasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGap(22, 22, 22))
        );

        jPanelParametros.setBorder(javax.swing.BorderFactory.createTitledBorder("Parametros"));

        jLabel9.setText("Promedio de permanencia: ");

        txt_promPermanencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_promPermanenciaActionPerformed(evt);
            }
        });

        jLabel6.setText("Tiempo llegada entre clientes:");

        jLabel10.setText("Tiempo de simulacion:");

        jLabel11.setText("Cantidad de simulaciones:");

        jPanelRango.setBorder(javax.swing.BorderFactory.createTitledBorder("Mostrar entre"));

        jLabel1.setText("Reloj desde:");

        jLabel2.setText("Cantidad filas:");

        javax.swing.GroupLayout jPanelRangoLayout = new javax.swing.GroupLayout(jPanelRango);
        jPanelRango.setLayout(jPanelRangoLayout);
        jPanelRangoLayout.setHorizontalGroup(
            jPanelRangoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelRangoLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_reloj_desde, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_cant_filas, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7))
        );
        jPanelRangoLayout.setVerticalGroup(
            jPanelRangoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelRangoLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanelRangoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(txt_reloj_desde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_cant_filas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );

        javax.swing.GroupLayout jPanelParametrosLayout = new javax.swing.GroupLayout(jPanelParametros);
        jPanelParametros.setLayout(jPanelParametrosLayout);
        jPanelParametrosLayout.setHorizontalGroup(
            jPanelParametrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelParametrosLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanelParametrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelRango, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanelParametrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(jPanelParametrosLayout.createSequentialGroup()
                            .addComponent(jLabel11)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txt_cantidad_sim, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanelParametrosLayout.createSequentialGroup()
                            .addComponent(jLabel10)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txt_tiempo_sim, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanelParametrosLayout.createSequentialGroup()
                            .addComponent(jLabel6)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txt_llegada_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanelParametrosLayout.createSequentialGroup()
                            .addComponent(jLabel9)
                            .addGap(41, 41, 41)
                            .addComponent(txt_promPermanencia, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(50, 50, 50))
        );
        jPanelParametrosLayout.setVerticalGroup(
            jPanelParametrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelParametrosLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanelParametrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanelParametrosLayout.createSequentialGroup()
                        .addGroup(jPanelParametrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(txt_promPermanencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(42, 42, 42))
                    .addGroup(jPanelParametrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6)
                        .addComponent(txt_llegada_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(20, 20, 20)
                .addGroup(jPanelParametrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_tiempo_sim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addGap(20, 20, 20)
                .addGroup(jPanelParametrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_cantidad_sim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addGap(18, 18, 18)
                .addComponent(jPanelRango, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );

        jPanelProb.setBorder(javax.swing.BorderFactory.createTitledBorder("Probabilidades"));

        jTableProbPostAtencion.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"Se queda", null, null},
                {"Se retira", null, null}
            },
            new String [] {
                "Post Atencion", "Probabilidad", "Probabilidad Acumulada"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTableProbPostAtencion);

        jTableProbConsulta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"Pedir Libros", null, null},
                {"Devolución Libros", null, null},
                {"Consulta", null, null}
            },
            new String [] {
                "Tipo de Atencion", "Probabilidad", "Probabilidad Acumulada"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTableProbConsulta);

        javax.swing.GroupLayout jPanelProbLayout = new javax.swing.GroupLayout(jPanelProb);
        jPanelProb.setLayout(jPanelProbLayout);
        jPanelProbLayout.setHorizontalGroup(
            jPanelProbLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelProbLayout.createSequentialGroup()
                .addGap(75, 75, 75)
                .addGroup(jPanelProbLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 440, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 440, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(75, 75, 75))
        );
        jPanelProbLayout.setVerticalGroup(
            jPanelProbLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelProbLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50))
        );

        btn_configuracion.setText("Setear configuracion por defecto");
        btn_configuracion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_configuracionActionPerformed(evt);
            }
        });

        btn_simular.setText("Simular");
        btn_simular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_simularActionPerformed(evt);
            }
        });

        btn_resetSim.setText("Limpiar Parametros");
        btn_resetSim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_resetSimActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelConfiguracionLayout = new javax.swing.GroupLayout(jPanelConfiguracion);
        jPanelConfiguracion.setLayout(jPanelConfiguracionLayout);
        jPanelConfiguracionLayout.setHorizontalGroup(
            jPanelConfiguracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelConfiguracionLayout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(jPanelConfiguracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelProb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanelConfiguracionLayout.createSequentialGroup()
                        .addComponent(jPanelParametros, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(50, 50, 50)
                        .addComponent(gpb_consultas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelConfiguracionLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_resetSim, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(btn_configuracion)
                .addGap(20, 20, 20)
                .addComponent(btn_simular, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(80, 80, 80))
        );
        jPanelConfiguracionLayout.setVerticalGroup(
            jPanelConfiguracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelConfiguracionLayout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(jPanelProb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelConfiguracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_resetSim)
                    .addComponent(btn_simular)
                    .addComponent(btn_configuracion))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelConfiguracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(gpb_consultas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanelParametros, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        btn_configuracion.getAccessibleContext().setAccessibleName("Setear configuración por defecto");

        jTabbedPane1.addTab("Configuracion", jPanelConfiguracion);

        tablaSimulacion.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPaneSimulacion.setViewportView(tablaSimulacion);

        jTabbedPane1.addTab("Simulacion", jScrollPaneSimulacion);

        textAreaClientes.setEditable(false);
        textAreaClientes.setColumns(20);
        textAreaClientes.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        textAreaClientes.setRows(5);
        jScrollPane3.setViewportView(textAreaClientes);

        javax.swing.GroupLayout jPanelResultadosLayout = new javax.swing.GroupLayout(jPanelResultados);
        jPanelResultados.setLayout(jPanelResultadosLayout);
        jPanelResultadosLayout.setHorizontalGroup(
            jPanelResultadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 874, Short.MAX_VALUE)
        );
        jPanelResultadosLayout.setVerticalGroup(
            jPanelResultadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelResultadosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 764, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Clientes", jPanelResultados);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(lblResultado)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jTabbedPane1)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 803, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(lblResultado)
                .addGap(62, 62, 62))
        );

        jTabbedPane1.getAccessibleContext().setAccessibleName("");
    }// </editor-fold>//GEN-END:initComponents

    private void btn_resetSimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_resetSimActionPerformed
        limpiarSimulacion();
    }//GEN-LAST:event_btn_resetSimActionPerformed

    private void btn_simularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_simularActionPerformed

        if (Integer.parseInt(txt_consulta_desde.getText()) >= Integer.parseInt(txt_consulta_hasta.getText())) {
            JOptionPane.showMessageDialog(null, "El rango desde de distribucion de las consultas no puede ser mayor o igual que el rango hasta.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            dtmProbAtencion = (DefaultTableModel) jTableProbConsulta.getModel();
            dtmProbPostAtencion = (DefaultTableModel) jTableProbPostAtencion.getModel();

            config.setPromedioPermanencia(Integer.parseInt(String.valueOf(txt_promPermanencia.getText())));
            config.setLlegadaCliente(Integer.parseInt(String.valueOf(txt_llegada_cliente.getText())));
            config.setCantidadSimulacion(Integer.parseInt(String.valueOf(txt_cantidad_sim.getText())));
            config.setConsultaDesde(Integer.parseInt(String.valueOf(txt_consulta_desde.getText())));
            config.setConsultaHasta(Integer.parseInt(String.valueOf(txt_consulta_hasta.getText())));
            config.setTiempoSimulacion(Double.parseDouble(String.valueOf(txt_tiempo_sim.getText())));
            config.setRangoDesde(Integer.parseInt(String.valueOf(txt_reloj_desde.getText())));
            config.setRangoHasta(Integer.parseInt(String.valueOf(txt_cant_filas.getText())));
            config.setProbPedirLibro(Double.parseDouble(String.valueOf(dtmProbAtencion.getValueAt(0, 1))));
            config.setProbPedirLibroAcum(Double.parseDouble(String.valueOf(dtmProbAtencion.getValueAt(0, 2))));
            config.setProbDevolverLibro(Double.parseDouble(String.valueOf(dtmProbAtencion.getValueAt(1, 1))));
            config.setProbDevolverLibroAcum(Double.parseDouble(String.valueOf(dtmProbAtencion.getValueAt(1, 2))));
            config.setProbConsultar(Double.parseDouble(String.valueOf(dtmProbAtencion.getValueAt(2, 1))));
            config.setProbConsultarAcum(Double.parseDouble(String.valueOf(dtmProbAtencion.getValueAt(2, 2))));
            config.setPrestamoQuedar(Double.parseDouble(String.valueOf(dtmProbPostAtencion.getValueAt(0, 1))));
            config.setPrestamoQuedarAcum(Double.parseDouble(String.valueOf(dtmProbPostAtencion.getValueAt(0, 2))));
            config.setPrestamoRetira(Double.parseDouble(String.valueOf(dtmProbPostAtencion.getValueAt(1, 1))));
            config.setPrestamoRetiraAcum(Double.parseDouble(String.valueOf(dtmProbPostAtencion.getValueAt(1, 2))));

            if (txt_tiempo_sim.getText().isEmpty() && txt_reloj_desde.getText().isEmpty() && txt_promPermanencia.getText().isEmpty()
                    && txt_llegada_cliente.getText().isEmpty() && txt_consulta_hasta.getText().isEmpty()
                    && txt_consulta_desde.getText().isEmpty() && txt_cantidad_sim.getText().isEmpty()
                    && txt_cant_filas.getText().isEmpty()) {
                empleado1 = new Empleado(config);
                empleado2 = new Empleado(config);
                cola = new ColaEspera(config);
                ListaClientes = new ColaEspera(config);
                tabla = new DefaultTableModel();
                tabla.setColumnIdentifiers(Columnas);
                boolean cargarLinea = false;
                contadorClientes = 1;
                int contadorLineas = 0;
                acumuladorPersonasAtendidas = 0;
                acumuladorTiempoAtendido = 0;
                StringBuilder SB = new StringBuilder("");

                if (config.getRangoDesde() == 0) {
                    cargarLinea = true;
                    contadorLineas++;
                }
                primeraLinea(cargarLinea);

                /*System.out.println(linea[0] + " " + evento + " Reloj:" + formato.format(reloj) +" -> " +ListaClientes.toString());
                 System.out.println("-----------------------------------------------------");*/
                SB.append("\n");
                SB.append(linea[0] + " || " + evento + "|| Reloj: " + formato.format(reloj));
                SB.append("\n");
                SB.append("----------------------------------------------------------------------------------------------------------------------------------------------");

                for (int i = 0; i < config.getCantidadSimulacion(); i++) {
                    if (0 <= reloj && reloj <= config.getTiempoSimulacion()) {
                        if (reloj >= config.getRangoDesde() && contadorLineas < config.getRangoHasta()) {
                            cargarLinea = true;
                            contadorLineas++;
                        } else {
                            cargarLinea = false;
                        }
                        if (i == config.getCantidadSimulacion() - 1) {
                            cargarLinea = true;
                        }
                        siguienteEvento(cargarLinea, i + 1);
                    //System.out.println(linea[0] + " " + evento + " Reloj:" + formato.format(reloj) +" -> " +ListaClientes.toString());
                        //System.out.println("-----------------------------------------------------");
                        SB.append("\n");
                        SB.append(linea[0] + " || " + evento + " " + linea[2] + " || Reloj: " + formato.format(reloj) + " -> " + ListaClientes.toString());
                        SB.append("\n");
                        SB.append("----------------------------------------------------------------------------------------------------------------------------------------------");

                    }
                }
                textAreaClientes.setText(SB.toString());

                tablaSimulacion.setModel(tabla);
                cambiarColoresColumnas();
                lblResultado.setText("Promedio de permanencia de los clientes:" + acumuladorTiempoAtendido / acumuladorPersonasAtendidas);
                SwingUtilities.invokeLater(() -> {
                    jTabbedPane1.setSelectedIndex(1);
                });
            } else {
                JOptionPane.showMessageDialog(null, "Complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }


    }//GEN-LAST:event_btn_simularActionPerformed

    private void btn_configuracionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_configuracionActionPerformed
        limpiarSimulacion();
        config = new Configuracion();
        txt_promPermanencia.setText("" + config.getPromedioPermanencia());
        txt_tiempo_sim.setText("" + config.getTiempoSimulacion());
        txt_cantidad_sim.setText("" + config.getCantidadSimulacion());
        txt_consulta_desde.setText("" + config.getConsultaDesde());
        txt_consulta_hasta.setText("" + config.getConsultaHasta());
        txt_llegada_cliente.setText("" + config.getLlegadaCliente());
        txt_reloj_desde.setText("" + config.getRangoDesde());
        txt_cant_filas.setText("" + config.getRangoHasta());
        jTableProbConsulta.setValueAt(0.45, 0, 1);
        jTableProbConsulta.setValueAt(0.45, 0, 2);
        jTableProbConsulta.setValueAt(0.45, 1, 1);
        jTableProbConsulta.setValueAt(0.90, 1, 2);
        jTableProbConsulta.setValueAt(0.10, 2, 1);
        jTableProbConsulta.setValueAt(1.00, 2, 2);
        jTableProbPostAtencion.setValueAt(0.40, 0, 1);
        jTableProbPostAtencion.setValueAt(0.40, 0, 2);
        jTableProbPostAtencion.setValueAt(0.60, 1, 1);
        jTableProbPostAtencion.setValueAt(1.00, 1, 2);
    }//GEN-LAST:event_btn_configuracionActionPerformed

    private void txt_promPermanenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_promPermanenciaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_promPermanenciaActionPerformed

    private void limpiarSimulacion() {
        txt_promPermanencia.setText("");
        txt_tiempo_sim.setText("");
        txt_cantidad_sim.setText("");
        txt_consulta_desde.setText("");
        txt_consulta_hasta.setText("");
        txt_llegada_cliente.setText("");
        txt_reloj_desde.setText("");
        txt_cant_filas.setText("");
        jTableProbConsulta.setValueAt("", 0, 1);
        jTableProbConsulta.setValueAt("", 0, 2);
        jTableProbConsulta.setValueAt("", 1, 1);
        jTableProbConsulta.setValueAt("", 1, 2);
        jTableProbConsulta.setValueAt("", 2, 1);
        jTableProbConsulta.setValueAt("", 2, 2);
        jTableProbPostAtencion.setValueAt("", 0, 1);
        jTableProbPostAtencion.setValueAt("", 0, 2);
        jTableProbPostAtencion.setValueAt("", 1, 1);
        jTableProbPostAtencion.setValueAt("", 1, 2);

    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_configuracion;
    private javax.swing.JButton btn_resetSim;
    private javax.swing.JButton btn_simular;
    private javax.swing.JPanel gpb_consultas;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanelConfiguracion;
    private javax.swing.JPanel jPanelParametros;
    private javax.swing.JPanel jPanelProb;
    private javax.swing.JPanel jPanelRango;
    private javax.swing.JPanel jPanelResultados;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPaneSimulacion;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTableProbConsulta;
    private javax.swing.JTable jTableProbPostAtencion;
    private javax.swing.JLabel lblResultado;
    private javax.swing.JTable tablaSimulacion;
    private javax.swing.JTextArea textAreaClientes;
    private javax.swing.JTextField txt_cant_filas;
    private javax.swing.JTextField txt_cantidad_sim;
    private javax.swing.JTextField txt_consulta_desde;
    private javax.swing.JTextField txt_consulta_hasta;
    private javax.swing.JTextField txt_llegada_cliente;
    private javax.swing.JTextField txt_promPermanencia;
    private javax.swing.JTextField txt_reloj_desde;
    private javax.swing.JTextField txt_tiempo_sim;
    // End of variables declaration//GEN-END:variables
}
