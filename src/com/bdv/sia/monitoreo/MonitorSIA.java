package com.bdv.sia.monitoreo;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

public class MonitorSIA {
	public static void main(String[] args) {
		EstadisticasService eService = new EstadisticasService();
		// TODO acomdar esta chapuzada

		// eService.init(args[0], args[1], args[2]);
		eService.init("usuplus", "JU6cezFU", "SIAQ");

		FileWriter fichero = null;
		PrintWriter pw = null;
		DateTime fFin = EstadisticasUtil.capturarFechaSistemaUnix();
		// TODO LA FRECUENCIA PASARLA COMO ARGUMENTO O UN ARCHIVO DE PROPIEDADES
		DateTime fIni = new DateTime(fFin.getMillis() - 1800000);

		long aprobadasMC = eService
				.consultarAprobadasUltimos30MinPorElemento(ConstantesSIA.ID_ELE_MASTER_CARD);
		long denegadasMC = eService
				.consultarDenegadasUltimos30MinPorElemento(ConstantesSIA.ID_ELE_MASTER_CARD);
		long aprobadasCCR = eService
				.consultarAprobadasUltimos30MinPorElemento(ConstantesSIA.ID_ELE_CONSORCIO_CREDICARD);
		long denegadasCCR = eService
				.consultarDenegadasUltimos30MinPorElemento(ConstantesSIA.ID_ELE_CONSORCIO_CREDICARD);
		long aprobadasVisa = eService
				.consultarAprobadasUltimos30MinPorElemento(ConstantesSIA.ID_ELE_VISA);
		long denegadasVisa = eService
				.consultarDenegadasUltimos30MinPorElemento(ConstantesSIA.ID_ELE_VISA);
		try {
			long divisorPorcentajeMC = aprobadasMC + denegadasMC;
			long divisorPorcentajeVisa = aprobadasVisa + denegadasVisa;
			long divisorPorcentajeCCR = aprobadasCCR + denegadasCCR;

			if (divisorPorcentajeMC == 0L) {
				divisorPorcentajeMC = 1L;
			}
			if (divisorPorcentajeVisa == 0L) {
				divisorPorcentajeVisa = 1L;
			}
			if (divisorPorcentajeCCR == 0L) {
				divisorPorcentajeCCR = 1L;
			}
			fichero = new FileWriter("./estadistica_monitoreo.txt");
			pw = new PrintWriter(fichero);
			pw.println("MONITOREO AUTORIZACIONES TDC POS (NACIONAL e INTERNACIONAL)");

			pw.println("\tDESDE "
					+ fIni.toString(DateTimeFormat.forPattern("HH:mm"))
					+ " HASTA "
					+ fFin.toString(DateTimeFormat.forPattern("HH:mm")));

			fIni.toString(DateTimeFormat.forPattern("HH:mm"));
			pw.println();
			pw.println();
			pw.println("OPERACIONES PROCESADAS MASTERCARD");
			pw.println();
			pw.println("APROBADAS \t\t\t\t" + aprobadasMC + "\t\t" + aprobadasMC
					* 100 / divisorPorcentajeMC + "%");

			pw.println("DENEGADAS FUNCIONALES \t\t\t" + denegadasMC + "\t\t" + denegadasMC
					* 100 / divisorPorcentajeMC + "%");
			
			pw.println("DENEGADAS TECNICAS \t\t\t" + denegadasMC + "\t\t" + denegadasMC
					* 100 / divisorPorcentajeMC + "%");

			pw.println("TOTAL TX PROCESADAS MASTERCARD\t\t\t\t"
					+ (aprobadasMC + denegadasMC));
			pw.println();
			pw.println();
			pw.println("OPERACIONES PROCESADAS VISA");
			pw.println();
			pw.println("APROBADAS \t\t\t\t" + aprobadasVisa + "\t\t"
					+ aprobadasVisa * 100 / divisorPorcentajeVisa + "%");

			pw.println("DENEGADAS FUNCIONALES \t\t\t" + denegadasVisa + "\t\t"
					+ denegadasVisa * 100 / divisorPorcentajeVisa + "%");
			pw.println("DENEGADAS TECNICAS \t\t\t" + denegadasMC + "\t\t" + denegadasMC
					* 100 / divisorPorcentajeMC + "%");

			pw.println("TOTAL TX PROCESADAS VISA \t\t\t\t"
					+ (aprobadasVisa + denegadasVisa));
			pw.println();
			pw.println("OPERACIONES PROCESADAS CONSORCIO CREDICARD");
			pw.println();
			pw.println("APROBADAS \t\t\t\t" + aprobadasCCR + "\t\t" + aprobadasCCR
					* 100 / divisorPorcentajeCCR + "%");

			pw.println("DENEGADAS FUNCIONALES \t\t\t" + denegadasCCR + "\t\t" + denegadasCCR
					* 100 / divisorPorcentajeCCR + "%");
			pw.println("DENEGADAS TECNICAS \t\t\t" + denegadasMC + "\t\t" + denegadasMC
					* 100 / divisorPorcentajeMC + "%");

			pw.println("TOTAL TX PROCESADAS CONSORCIO\t\t\t\t"
					+ (aprobadasCCR + denegadasCCR));
			pw.println();

			fichero.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}