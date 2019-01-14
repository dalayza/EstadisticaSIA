package com.bdv.sia.monitoreo;

//import java.net.Socket;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

public class Test {
	public static void main(String[] args) {
		// DateTime fFin = new DateTime();
		// DateTime fInicio = new DateTime(fFin.getMillis() - 5400000L);
		//
		// System.out.println("Fecha:"
		// + fInicio.toString(DateTimeFormat.forPattern("YYYYMMddHHmm")));
		pruebasFechasDelSistema();
	}

	private static void pruebasFechasDelSistema() {
		String salidaComando = "14/11/11_14:36:56";

		//activar que se comento
		//Socket socket = null;
		
		
		
		// DateTime fecha = new
		// DateTime(Integer.parseInt(salidaComando.substring(
		// 0, 2)+2000), Integer.parseInt(salidaComando.substring(4, 5)),
		// Integer.parseInt(salidaComando.substring(7, 8)),
		// Integer.parseInt(salidaComando.substring(10, 11)),
		// Integer.parseInt(salidaComando.substring(13, 14)), 0, 0);
		//
		DateTime fecha = new DateTime(Integer.parseInt(salidaComando.substring(
				0, 2)) + 2000, Integer.parseInt(salidaComando.substring(3, 5)),
				Integer.parseInt(salidaComando.substring(6, 8)),
				Integer.parseInt(salidaComando.substring(9, 11)), Integer.parseInt(salidaComando.substring(12,14)),0, 0);

		System.out
				.println(Integer.parseInt(salidaComando.substring(0, 2)) + 2000);

		System.out.println(fecha.toString(DateTimeFormat
				.forPattern("YYYYMMddHHmm")));
		
		System.out.println(fecha.toString(DateTimeFormat
				.forPattern("HH:mm")));
	}
}