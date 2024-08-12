package es.safcce.comun.util.constantes;

import java.util.HashMap;
import java.util.Map;

public enum TipoDocumento {
	DOCUMENTO_IDENTIFICATIVO("TD001"),
	ACREDITACION("TD002"),
	OTROS("TD003");
	
	// Mapa de búsqueda inversa para obtener un TipoDocumento a partir de su código
    private static final Map<String, TipoDocumento> busqueda = new HashMap<>();

    static {
        for (TipoDocumento tipoDoc : TipoDocumento.values()) {
            busqueda.put(tipoDoc.getCodigo(), tipoDoc);
        }
    }
	
	private String codigo;

	private TipoDocumento(String codigo) {
		this.codigo = codigo;
	}

	public String getCodigo() {
		return codigo;
	}
	
	public static TipoDocumento get(String estado) {
        return busqueda.get(estado);
    }
}
