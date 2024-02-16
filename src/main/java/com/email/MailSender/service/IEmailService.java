package com.email.MailSender.service;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.thymeleaf.context.Context;

import com.email.MailSender.projections.ProgramacionEnvioP;

import net.sf.jasperreports.engine.JRException;

public interface IEmailService {

	// solo mensajes
	void sendEmail(String[] toUser, String subject, String message);

	// mensaje con archivo
	void sendEmailWithFile(String[] toUser, String subject, String message, File file);

	// mensaje con HTML
	void sendEmailWithHtmlTemplate(String[] toUser, String subject, String templateName, Context context);

	// mensaje con HTML y archivo
	void sendEmailWithHtmlTemplateAndFile(String[] toUser, String subject, String templateName, Context context,
			File file);

	void sendEmailWithHtmlTemplateAndFile1(String[] toUser, String subject, String templateName, Context context,
			String fechaInicio, String fechaFin, String tipo) throws JRException, IOException, SQLException;

	public void sendEmailWithHtmlTemplateAndFile2(String templateName, Context context, String fechaInicio,
			String fechaFin) throws JRException, IOException, SQLException;

	// public List<Object[]> sendEmailProgramado();

	List<ProgramacionEnvioP> getEnvioMensual();
}
