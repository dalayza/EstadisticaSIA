package com.bdv.sia.monitoreo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

public class EstadisticasService {
	public static String QUERY_TABLA_ACTIVA = " select AKX_TAB_ACT_OPE_NU from tta753 where AKX_ELM_SIA_CD=?";

	public static String QUERY_APROBADAS_MC_TABLA_ACTIVA_1 = "select  count (*) from ttav0opev1 where   substr(V0V1_LOG_GRB_TS,0,12) >  ? and V0V1_RSL_SIA_CD = 1 ";

	public static String QUERY_NEGADAS_MC_TABLA_ACTIVA_1 = "select  count (*) from ttav0opev1 where  substr(V0V1_LOG_GRB_TS,0,12) >  ? and V0V1_RSL_SIA_CD <> 1 ";

	public static String QUERY_APROBADAS_MC_TABLA_ACTIVA_2 = "select  count (*) from ttav0opev2 where   substr(V0V2_LOG_GRB_TS,0,12) >  ? and V0V2_RSL_SIA_CD = 1 ";

	public static String QUERY_NEGADAS_MC_TABLA_ACTIVA_2 = "select  count (*) from ttav0opev2 where  substr(V0V2_LOG_GRB_TS,0,12) >  ? and V0V2_RSL_SIA_CD <> 1 ";

	public static String QUERY_APROBADAS_VISA_TABLA_ACTIVA_1 = "select  count (*) from ttaw0opev1 where   substr(W0V1_LOG_GRB_TS,0,12) >  ? and W0V1_RSL_SIA_CD = 1 ";

	public static String QUERY_NEGADAS_VISA_TABLA_ACTIVA_1 = "select  count (*) from ttaw0opev1 where  substr(W0V1_LOG_GRB_TS,0,12) >  ? and W0V1_RSL_SIA_CD <> 1 ";

	public static String QUERY_APROBADAS_VISA_TABLA_ACTIVA_2 = "select  count (*) from ttaw0opev2 where   substr(W0V2_LOG_GRB_TS,0,12) >  ? and W0V2_RSL_SIA_CD = 1 ";

	public static String QUERY_NEGADAS_VISA_TABLA_ACTIVA_2 = "select  count (*) from ttaw0opev2 where  substr(W0V2_LOG_GRB_TS,0,12) >  ? and W0V2_RSL_SIA_CD <> 1 ";

	public static String QUERY_APROBADAS_CCR_TABLA_ACTIVA_1 = "select  count (*) from ttal0opev1 where   substr(L0V1_LOG_GRB_TS,0,12) >  ? and L0V1_RSL_SIA_CD = 1 ";

	public static String QUERY_NEGADAS_CCR_TABLA_ACTIVA_1 = "select  count (*) from ttal0opev1 where  substr(L0V1_LOG_GRB_TS,0,12) >  ? and L0V1_RSL_SIA_CD <> 1 ";

	public static String QUERY_APROBADAS_CCR_TABLA_ACTIVA_2 = "select  count (*) from ttal0opev2 where   substr(L0V2_LOG_GRB_TS,0,12) >  ? and L0V2_RSL_SIA_CD = 1 ";

	public static String QUERY_NEGADAS_CCR_TABLA_ACTIVA_2 = "select  count (*) from ttal0opev2 where  substr(L0V2_LOG_GRB_TS,0,12) >  ? and L0V2_RSL_SIA_CD <> 1 ";
	private Connection conexion;
	PreparedStatement preparedStatement;

	public void init(String usuario, String password, String sid) {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			String bdURL = "jdbc:oracle:thin:@localhost:1545:" + sid;
			this.conexion = DriverManager.getConnection(bdURL, usuario,
					password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private Connection getConection() {
		return this.conexion;
	}

	public long consultarUltimos30MinPorElemento(String elementoSIA,
			int tipoResolucion) {
		long ad = 0;

		ResultSet rs = null;
		String QUERY_A_EJECUTAR_APROBADAS = null;
		try {
			this.preparedStatement = getConection().prepareStatement(
					QUERY_TABLA_ACTIVA);
			this.preparedStatement.setString(1, elementoSIA);
			rs = this.preparedStatement.executeQuery();
			rs.next();
			int tablaActiva = Integer.parseInt(rs.getString(1));
			// consulto si es mastercard y aprobada
			if ((elementoSIA == ConstantesSIA.ID_ELE_MASTER_CARD)
					&& (tipoResolucion == ConstantesSIA.RESOLUCION_APROBADA))
				switch (tablaActiva) {
				case 1:
					QUERY_A_EJECUTAR_APROBADAS = QUERY_APROBADAS_MC_TABLA_ACTIVA_1;
					break;
				case 2:
					QUERY_A_EJECUTAR_APROBADAS = QUERY_APROBADAS_MC_TABLA_ACTIVA_2;
					break;
				default:
					break;
				}
			// consulto si es mastercard y denegada
			if ((elementoSIA == ConstantesSIA.ID_ELE_MASTER_CARD)
					&& (tipoResolucion == ConstantesSIA.RESOLUCION_DENEGADA))

				switch (tablaActiva) {
				case 1:
					QUERY_A_EJECUTAR_APROBADAS = QUERY_NEGADAS_MC_TABLA_ACTIVA_1;
					break;
				case 2:
					QUERY_A_EJECUTAR_APROBADAS = QUERY_NEGADAS_MC_TABLA_ACTIVA_2;
				default:
					break;
				}
			// consulto si es aprobada y ccr
			if ((elementoSIA == ConstantesSIA.ID_ELE_CONSORCIO_CREDICARD)
					&& (tipoResolucion == ConstantesSIA.RESOLUCION_APROBADA))
				switch (tablaActiva) {
				case 1:
					QUERY_A_EJECUTAR_APROBADAS = QUERY_APROBADAS_CCR_TABLA_ACTIVA_1;
					break;
				case 2:
					QUERY_A_EJECUTAR_APROBADAS = QUERY_APROBADAS_CCR_TABLA_ACTIVA_2;
				default:
					break;
				}
			if ((elementoSIA == ConstantesSIA.ID_ELE_CONSORCIO_CREDICARD)
					&& (tipoResolucion == ConstantesSIA.RESOLUCION_DENEGADA))
				;
			switch (tablaActiva) {
			case 1:
				QUERY_A_EJECUTAR_APROBADAS = QUERY_NEGADAS_CCR_TABLA_ACTIVA_1;
				break;
			case 2:
				QUERY_A_EJECUTAR_APROBADAS = QUERY_NEGADAS_CCR_TABLA_ACTIVA_2;
				break;
			default:
				break;
			}
			if ((elementoSIA == ConstantesSIA.ID_ELE_VISA)
					&& (tipoResolucion == ConstantesSIA.RESOLUCION_APROBADA))
				;
			switch (tablaActiva) {
			case 1:
				QUERY_A_EJECUTAR_APROBADAS = QUERY_APROBADAS_CCR_TABLA_ACTIVA_1;
				break;
			case 2:
				QUERY_A_EJECUTAR_APROBADAS = QUERY_APROBADAS_CCR_TABLA_ACTIVA_2;
			default:
				break;
			}
			if ((elementoSIA != ConstantesSIA.ID_ELE_VISA)
					|| (tipoResolucion != ConstantesSIA.RESOLUCION_DENEGADA))
				switch (tablaActiva) {
				case 1:
					QUERY_A_EJECUTAR_APROBADAS = QUERY_NEGADAS_VISA_TABLA_ACTIVA_1;
					break;
				case 2:
					QUERY_A_EJECUTAR_APROBADAS = QUERY_NEGADAS_VISA_TABLA_ACTIVA_2;
				default:
					break;
				}
			this.preparedStatement = getConection().prepareStatement(
					QUERY_A_EJECUTAR_APROBADAS);

			DateTime fFin = EstadisticasUtil.capturarFechaSistemaUnix();
			DateTime fInicio = new DateTime(fFin.getMillis() - 1800000);
			
			this.preparedStatement
					.setString(1, fInicio.toString(DateTimeFormat
							.forPattern("YYYYMMddHHmm")));

			rs = this.preparedStatement.executeQuery();
			rs.next();

			ad = rs.getLong(1);
			this.preparedStatement.close();
			rs.close();
			return ad;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ad;
	}

	public long consultarAprobadasUltimos30MinPorElemento(String elementoSIA) {
		return consultarUltimos30MinPorElemento(elementoSIA,
				ConstantesSIA.RESOLUCION_APROBADA);
	}

	public long consultarDenegadasUltimos30MinPorElemento(String elementoSIA) {
		return consultarUltimos30MinPorElemento(elementoSIA,
				ConstantesSIA.RESOLUCION_DENEGADA);
	}
}