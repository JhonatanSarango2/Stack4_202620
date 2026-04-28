package ec.edu.taller;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.LinkedList;

public class GestorIncidentes {

    private Queue<IncidenteSeguridad> colaIncidentes;
    private int capacidadMaxima;

    public GestorIncidentes(int capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
        this.colaIncidentes = new LinkedList<>();
    }

    public boolean registrarIncidente(IncidenteSeguridad incidente) {
        // Validación de incidente nulo
        if (incidente == null) {
            return false;
        }

        // Validación de código nulo o vacío
        String codigo = incidente.getCodigo();
        if (codigo == null || codigo.trim().isEmpty()) {
            return false;
        }

        // Validación de cola llena
        if (colaIncidentes.size() >= capacidadMaxima) {
            return false;
        }

        // Validación de duplicados
        if (existeIncidente(codigo)) {
            return false;
        }

        // Si pasa todas las validaciones, se encola
        return colaIncidentes.offer(incidente);
    }

    public boolean existeIncidente(String codigo) {
        if (codigo == null || codigo.trim().isEmpty()) {
            return false;
        }

        // Iteramos para buscar si el código ya existe
        for (IncidenteSeguridad incidente : colaIncidentes) {
            if (incidente.getCodigo().equals(codigo)) {
                return true;
            }
        }

        return false;
    }

    public IncidenteSeguridad consultarSiguienteIncidente() {
        // peek() retorna el primero sin eliminarlo (o null si está vacía)
        return colaIncidentes.peek();
    }

    public IncidenteSeguridad atenderSiguienteIncidente() {
        // poll() elimina y retorna el primero (o null si está vacía)
        IncidenteSeguridad incidenteAtendido = colaIncidentes.poll();

        if (incidenteAtendido != null) {
            incidenteAtendido.setEstado("ATENDIDO");
        }

        return incidenteAtendido;
    }

    public int contarIncidentesPendientes() {
        return colaIncidentes.size();
    }

    public int consultarEspaciosDisponibles() {
        return capacidadMaxima - colaIncidentes.size();
    }

    public List<IncidenteSeguridad> listarIncidentes() {
        // Al pasar la cola al constructor de ArrayList, crea una copia
        // manteniendo el orden de iteración (FIFO) sin alterar la cola original
        return new ArrayList<>(colaIncidentes);
    }
}