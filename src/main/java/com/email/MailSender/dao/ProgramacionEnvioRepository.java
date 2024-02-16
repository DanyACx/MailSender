package com.email.MailSender.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.email.MailSender.dao.entities.ProgramacionEnvio;
import com.email.MailSender.dao.entities.ProgramacionEnvioId;
import com.email.MailSender.projections.ProgramacionEnvioP;

@Repository
public interface ProgramacionEnvioRepository extends JpaRepository<ProgramacionEnvio, ProgramacionEnvioId> {

    @Query(value = "SELECT u.nombre as nombrePersona, u.apellido_paterno as apellidoPaterno, u.apellido_materno as apellidoMaterno, u.email, r.nombre as nombreReporte, r.codigo, r.tipo_formato as tipoFormato, pe.periodo, pe.acumulado, pe.estado, pe.fase, pe.rubro, pe.unidad, pe.meta_siaf as metaSiaf " +
                   "FROM programacion_envio pe " +
                   "INNER JOIN usuarios u ON u.id_usuario = pe.id_usuario " +
                   "INNER JOIN reportes r ON pe.id_reporte = r.id_reporte " +
                   "WHERE pe.estado = 1 AND pe.periodo = 'M'", nativeQuery = true)
    List<ProgramacionEnvioP> obtenerInformacionEnvioMensual(); // este utiliza


	/*@Query("SELECT u.nombre, u.apellidoPaterno, u.apellidoMaterno, u.email, "
			+ "r.nombre, r.codigo, r.tipoFormato, pe.periodo, pe.acumulado, pe.estado, "
			+ "pe.fase, pe.rubro, pe.unidad, pe.metaSiaf " + "FROM programacion_envio pe " + "INNER JOIN pe.usuario u "
			+ "INNER JOIN reporte r " + "WHERE pe.estado = 1 AND pe.periodo = 'D'")
	List<InformacionEnvioDTO> obtenerInformacionEnvioDiario();*/
	
	/*@Query("SELECT u.nombre, u.apellidoPaterno, u.apellidoMaterno, u.email, "
			+ "r.nombre, r.codigo, r.tipoFormato, pe.periodo, pe.acumulado, pe.estado, "
			+ "pe.fase, pe.rubro, pe.unidad, pe.metaSiaf " + "FROM programacion_envio pe " + "INNER JOIN pe.usuario u "
			+ "INNER JOIN pe.reporte r " + "WHERE pe.estado = 1 AND pe.periodo = 'Q'")
	List<InformacionEnvioDTO> obtenerInformacionEnvioQuincenal();*/
	
	/*@Query("SELECT u.nombre, u.apellidoPaterno, u.apellidoMaterno, u.email, "
			+ "r.nombre, r.codigo, r.tipoFormato, pe.periodo, pe.acumulado, pe.estado, "
			+ "pe.fase, pe.rubro, pe.unidad, pe.metaSiaf " + "FROM programacion_envio pe " + "INNER JOIN pe.usuario u "
			+ "INNER JOIN reporte r " + "WHERE pe.estado = 1 AND pe.periodo = 'S'")
	List<InformacionEnvioDTO> obtenerInformacionEnvioSemanal();*/
}
