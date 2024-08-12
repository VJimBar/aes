package es.safcce.comun.util.constantes;

import java.util.HashMap;
import java.util.Map;

public enum EstadosSolicitud {

	CREADA("ES001"),
	PENDIENTE_SUBIR_DOCUMENTOS("ES002"),
	PENDIENTE_APROBACION("ES003"), 
	APROBADA("ES004"),
	DENEGADA("ES005"),
	CANCELADA("ES006");
	
	
	// Mapa de búsqueda inversa map para obtener un EstadosSolicitud a partir de su codigo
    private static final Map<String, EstadosSolicitud> busqueda = new HashMap<>();

    static {
        for (EstadosSolicitud estado : EstadosSolicitud.values()) {
            busqueda.put(estado.getCodigo(), estado);
        }
    }

	private String codigo;

	private EstadosSolicitud(String id) {
		codigo = id;
	}

	public String getCodigo() {
		return codigo;
	}
	
	public static EstadosSolicitud get(String estado) {
        return busqueda.get(estado);
    }
}
