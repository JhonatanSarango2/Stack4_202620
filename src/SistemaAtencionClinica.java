import java.util.PriorityQueue;

public class SistemaAtencionClinica {

    private PriorityQueue<Paciente> cola;
    private static final int CAPACIDAD_MAXIMA = 12;

    public SistemaAtencionClinica() {
        this.cola = new PriorityQueue<>();
    }

    public boolean registrarPaciente(String codigo, String nombre, int prioridad) {

        if (cola.size() >= CAPACIDAD_MAXIMA) {
            return false;
        }


        if (codigo == null || codigo.trim().isEmpty()) {
            return false;
        }


        if (nombre == null || nombre.trim().isEmpty()) {
            return false;
        }


        if (prioridad < 1 || prioridad > 3) {
            return false;
        }


        for (Paciente p : cola) {
            if (p.getCodigo().equals(codigo)) {
                return false;
            }
        }


        cola.add(new Paciente(codigo, nombre, prioridad));
        return true;
    }


    public boolean registrarPaciente(String codigo, String nombre) {
        return false;
    }

    public String verSiguientePaciente() {
        if (cola.isEmpty()) {
            return "No hay pacientes";
        }
        return cola.peek().toString();
    }

    public String atenderSiguientePaciente() {
        if (cola.isEmpty()) {
            return "No hay pacientes";
        }
        return cola.poll().toString();
    }

    public int obtenerCantidadPacientes() {
        return cola.size();
    }

    public int obtenerEspaciosDisponibles() {
        return CAPACIDAD_MAXIMA - cola.size();
    }

    public String mostrarColaPrioridad() {
        if (cola.isEmpty()) {
            return "Cola vacía";
        }

        PriorityQueue<Paciente> copiaParaMostrar = new PriorityQueue<>(cola);
        StringBuilder listado = new StringBuilder();

        while (!copiaParaMostrar.isEmpty()) {
            listado.append(copiaParaMostrar.poll().toString()).append("\n");
        }

        return listado.toString().trim();
    }
}
