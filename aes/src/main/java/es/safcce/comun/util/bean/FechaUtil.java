package es.safcce.comun.util.bean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.time.DateUtils;

/**
 * Utilidades de Fecha
 * 
 */
/**
 * @author agonzalezl
 * 
 */
public class FechaUtil {

	public static final String FECHA_USUARIO = "dd 'de' MMMM 'de' yyyy";
	public static final String FECHA_HORA_ESTANDAR = "yyyy-MM-dd HH:mm:ss";
	public static final String FECHA_HORA = "dd/MM/yyyy HH:mm:ss";
	public static final String FECHA = "dd/MM/yyyy";
	public static final String FECHA_ESTANDAR = "yyyy-MM-dd";
	public static final String FECHA_MES_DIA = "MM/dd/yyyy";	
	public static final String HORA = "HH:mm:ss";
	public static final String HORA_TIMEZONE = "HH:mm:ss z";

	private FechaUtil() {
		throw new IllegalStateException("Clase de Utilidad");
	}

	/**
	 * Suma los días o resta si el valor de días es negativo a la fecha
	 * 
	 * @param fecha
	 *            La fecha
	 * @param dias
	 *            Los días a sumar, o a restar si es negativo
	 * @return La nueva fecha
	 */
	public static Date sumarRestarDiasFecha(Date fecha, int dias) {
		return DateUtils.addDays(fecha, dias);
	}

	/**
	 * Suma las horas o resta si el valor de horas es negativo a la fecha
	 * 
	 * @param fecha
	 *            La fecha
	 * @param horas
	 *            Las horas a sumar, o a restar si es negativo
	 * @return La nueva fecha
	 */
	public static Date sumarRestarHorasFecha(Date fecha, int horas) {
		return DateUtils.addHours(fecha, horas);
	}

	/**
	 * Devuelve el numero de días que transcurren entre dos fechas
	 * 
	 * @param fechaInicial
	 *            La fecha inicial
	 * @param fechaFinal
	 *            La fecha final
	 * @return la diferencia de días entre dos fechas
	 */
	public static int numeroDiasEntreDosFechas(Date fechaInicial,
			Date fechaFinal) {
		long startTime = fechaInicial.getTime();
		long endTime = fechaFinal.getTime();
		long diffTime = endTime - startTime;
		return (int) TimeUnit.DAYS.convert(diffTime, TimeUnit.MILLISECONDS);
	}
	
	public static SimpleDateFormat getFormatoFechaUsuario() {
		return new SimpleDateFormat(FECHA_USUARIO);
	}
	
	public static SimpleDateFormat getFormatoFechaEstandar() {
		return new SimpleDateFormat(FECHA_ESTANDAR);
	}

	public static SimpleDateFormat getFormatoFechaHora() {
		return new SimpleDateFormat(FECHA_HORA);
	}

	public static SimpleDateFormat getFormatoFecha() {
		return new SimpleDateFormat(FECHA);
	}

	public static SimpleDateFormat getFormatoHora() {
		return new SimpleDateFormat(HORA);
	}
	
	public static SimpleDateFormat getFormatoFechaHoraEstandar() {
		return new SimpleDateFormat(FECHA_HORA_ESTANDAR);
	}
	
	public static SimpleDateFormat getFormatoHoraGMT() {
		SimpleDateFormat sdf = new SimpleDateFormat(HORA_TIMEZONE);
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		return sdf;
	}
	
	public static SimpleDateFormat getFormatoFechaMesDia() {
		return new SimpleDateFormat(FECHA_MES_DIA);		
	}

}
