package com.email.MailSender.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import com.email.MailSender.dto.ReporteDTO;

import net.sf.jasperreports.engine.JRException;

public interface IReporteService {

	ReporteDTO obtenerReporte(Map<String, Object> params) throws JRException, IOException, SQLException;
}
