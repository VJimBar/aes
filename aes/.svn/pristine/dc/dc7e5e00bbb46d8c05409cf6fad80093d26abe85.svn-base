package es.safcce.comun.util.constantes;

import java.util.HashMap;
import java.util.Map;

public enum PropiedadDocumento {
	CIUDADANO("PD001"),
	GESTOR("PD002");
	
	// Mapa de búsqueda inversa para obtener un TipoDocumento a partir de su código
    private static final Map<String, PropiedadDocumento> busqueda = new HashMap<>();

    static {
        for (PropiedadDocumento tipoDoc : PropiedadDocumento.values()) {
            busqueda.put(tipoDoc.getCodigo(), tipoDoc);
        }
    }
	
	private String codigo;

	private PropiedadDocumento(String codigo) {
		this.codigo = codigo;
	}

	public String getCodigo() {
		return codigo;
	}
	
	public static PropiedadDocumento get(String estado) {
        return busqueda.get(estado);
    }
}
