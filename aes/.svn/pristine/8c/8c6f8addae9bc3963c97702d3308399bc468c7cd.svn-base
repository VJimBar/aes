package es.safcce.comun.util.constantes;

import java.util.HashMap;
import java.util.Map;

public enum ValoresConfiguracion {

	TIEMPO_VIGENCIA_CREDENCIAL_INTERESADO("1"),
	TIEMPO_VIGENCIA_CLAVE_OTP("2"), 
	NUMERO_INTENTOS_FALLIDOS_AUTENTIFICACION("3"),
	MINIMO_DIAS_PARA_AVISOS("4"),
	AVISOS_CADA_X_DIAS("5"),
	PERMITIR_FIRMA_NIVEL_BAJO("6"),
	CLAVE_MAESTRA_SISTEMA("7"),
	IV_SISTEMA("8");

	private String codigo;

	private ValoresConfiguracion(String id) {
		codigo = id;
	}

	public String getCodigo() {
		return codigo;
	}
	
	public Long toLong() {
		return new Long(codigo);
	}
	
	// Mapa de búsqueda inversa map para obtener un EstadosSolicitud a partir de su codigo
    private static final Map<String, ValoresConfiguracion> busqueda = new HashMap<>();

    static {
        for (ValoresConfiguracion valores : ValoresConfiguracion.values()) {
            busqueda.put(valores.getCodigo(), valores);
        }
    }
	
    public static ValoresConfiguracion get(String valores) {
        return busqueda.get(valores);
    }
}
