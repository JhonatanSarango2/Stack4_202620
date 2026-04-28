import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Ventana {
    private JPanel panelPrincipal; // Este es el nombre del panel principal que le diste en el .form
    private JTextField txtCodigo;
    private JTextField txtNombre;
    private JSpinner spiPrioridad;
    private JLabel lblEstado;
    private JButton btnRegistrar;
    private JButton btnVerSiguiente;
    private JButton btnAtender;
    private JButton btnMostrarTodo;
    private JTextArea txtAreaListado; // Asegúrate de que el nombre coincida exactamente con tu .form

    // Instanciamos el sistema de la clínica para usar su lógica
    private SistemaAtencionClinica sistema = new SistemaAtencionClinica();

    public Ventana() {
        // --- 1. Acción del botón Registrar ---
        btnRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cod = txtCodigo.getText();
                String nom = txtNombre.getText();
                // Obtenemos el valor del spinner y lo convertimos a entero
                int pri = (int) spiPrioridad.getValue();

                boolean exito = sistema.registrarPaciente(cod, nom, pri);

                if (exito) {
                    JOptionPane.showMessageDialog(null, "Paciente registrado con éxito.");
                    limpiarCampos();
                    actualizarPantalla();
                } else {
                    JOptionPane.showMessageDialog(null,
                            "Error al registrar:\n- Verifique que los campos no estén vacíos.\n- El código podría estar duplicado.\n- La clínica podría estar llena.",
                            "Error de Registro",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // --- 2. Acción del botón Ver Siguiente ---
        btnVerSiguiente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String siguiente = sistema.verSiguientePaciente();
                JOptionPane.showMessageDialog(null, "Siguiente paciente en espera:\n" + siguiente);
            }
        });

        btnAtender.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String resultadoAtencion = sistema.atenderSiguientePaciente();
                JOptionPane.showMessageDialog(null, "Resultado de la atención:\n" + resultadoAtencion);
                actualizarPantalla();
            }
        });

        btnMostrarTodo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarPantalla();
            }
        });

        actualizarPantalla();
    }

    private void limpiarCampos() {
        txtCodigo.setText("");
        txtNombre.setText("");
        spiPrioridad.setValue(2);
    }

    private void actualizarPantalla() {
        int espacios = sistema.obtenerEspaciosDisponibles();
        lblEstado.setText("Espacios: " + espacios);

        // Actualizamos el área de texto con la lista actual de pacientes
        txtAreaListado.setText(sistema.mostrarColaPrioridad());
    }

    // --- Método Main para ejecutar la aplicación ---
    public static void main(String[] args) {
        JFrame frame = new JFrame("Gestión de Clínica - Colas de Prioridad");
        // Cargamos el panel principal (panelPrincipal debe ser el nombre del recuadro general en tu .form)
        frame.setContentPane(new Ventana().panelPrincipal);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null); 
        frame.setVisible(true);
    }
}
