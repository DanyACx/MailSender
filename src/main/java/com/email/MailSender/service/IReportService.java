package com.email.MailSender.service;

import org.thymeleaf.context.Context;

import com.email.MailSender.projections.ProgramacionEnvioP;

public interface IReportService {

	public void generateReportAndSendEmail(ProgramacionEnvioP resultado, String templateName, Context context, String fechaInicio, String fechaFin, Integer anio);
}
