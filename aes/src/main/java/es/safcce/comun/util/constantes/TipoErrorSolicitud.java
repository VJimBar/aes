package es.safcce.comun.util.constantes;

import java.util.HashMap;
import java.util.Map;


public enum TipoErrorSolicitud {

	ERROR_ENVIO_CONFIRMACION_EMAIL("CONFEMAIL"),
	ERROR_ENVIO_ACTIVACION_CLAVE("ACTICLAVE");
	
	// Mapa de búsqueda inversa map para obtener un TipoErrorSolicitud a partir de su código
    private static final Map<String, TipoErrorSolicitud> busqueda = new HashMap<>();

    static {
        for (TipoErrorSolicitud tipoError : TipoErrorSolicitud.values()) {
            busqueda.put(tipoError.getCodigo(), tipoError);
        }
    }
	
	private String codigo;

	private TipoErrorSolicitud(String codigo) {
		this.codigo = codigo;
	}

	public String getCodigo() {
		return codigo;
	}
	
	public static TipoErrorSolicitud get(String estado) {
        return busqueda.get(estado);
    }
	
}
