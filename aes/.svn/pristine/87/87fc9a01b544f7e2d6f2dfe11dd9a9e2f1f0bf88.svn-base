package es.safcce.comun.util.constantes;

import java.util.HashMap;
import java.util.Map;

public enum TipoSolicitud {
	INTERNET("TS001"),
	INTRANET("TS002");
	
	// Mapa de búsqueda inversa para obtener un TipoDocumento a partir de su código
    private static final Map<String, TipoSolicitud> busqueda = new HashMap<>();

    static {
        for (TipoSolicitud tipoDoc : TipoSolicitud.values()) {
            busqueda.put(tipoDoc.getCodigo(), tipoDoc);
        }
    }
	
	private String codigo;

	private TipoSolicitud(String codigo) {
		this.codigo = codigo;
	}

	public String getCodigo() {
		return codigo;
	}
	
	public static TipoSolicitud get(String estado) {
        return busqueda.get(estado);
    }
}
