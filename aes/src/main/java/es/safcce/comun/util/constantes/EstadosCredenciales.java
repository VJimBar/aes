package es.safcce.comun.util.constantes;

import java.util.HashMap;
import java.util.Map;

public enum EstadosCredenciales {

	PENDIENTE_ENVIO_ACTIVACION("EC001"),
	PENDIENTE_ACTIVACION("EC002"),
	PENDIENTE_GENERACION("EC003"),
	ACTIVADA("EC004"),	
	CADUCADA("EC005"),
	BLOQUEADA("EC006"),
	CANCELADA("EC007");

	// Mapa de búsqueda inversa map para obtener un EstadosSolicitud a partir de su codigo
    private static final Map<String, EstadosCredenciales> busqueda = new HashMap<>();

    static {
        for (EstadosCredenciales estado : EstadosCredenciales.values()) {
            busqueda.put(estado.getCodigo(), estado);
        }
    }

	private String codigo;

	private EstadosCredenciales(String id) {
		codigo = id;
	}

	public String getCodigo() {
		return codigo;
	}
	
	public static EstadosCredenciales get(String estado) {
        return busqueda.get(estado);
    }
}
