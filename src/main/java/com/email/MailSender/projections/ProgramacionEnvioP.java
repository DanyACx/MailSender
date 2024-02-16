package com.email.MailSender.projections;

// proyeccion cerrada
public interface ProgramacionEnvioP {

	String getNombrePersona();
	String getApellidoPaterno();
	String getApellidoMaterno();
	String getEmail();
	String getNombreReporte();
	String getCodigo();
	String getTipoFormato();
	String getPeriodo();
	Integer getAcumulado();
	Integer getEstado();
	String getFase();
	String getRubro();
	Integer getUnidad();
	Integer getMetaSiaf();
	
}
