package com.bdv.sia.monitoreo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.joda.time.DateTime;

public class EstadisticasUtil {
	public static DateTime capturarFechaSistemaUnix() {
		try {
			//ALIAS DE UNIX alias fechaJSia="date '+%y/%m/%d_%H:%M:%S'"
			Process p = Runtime.getRuntime().exec("/aplicacion/pampa/src/fechaJSia");
			p.getInputStream();
			InputStream is = p.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String salidaComando = br.readLine();
			is.close();
			br.close();

			// Date fecha1=new Date()
			DateTime fecha = new DateTime(Integer.parseInt(salidaComando
					.substring(0, 2)) + 2000, Integer.parseInt(salidaComando
					.substring(3, 5)), Integer.parseInt(salidaComando
					.substring(6, 8)), Integer.parseInt(salidaComando
					.substring(9, 11)), Integer.parseInt(salidaComando
					.substring(12, 14)), 0, 0);

			return fecha;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}