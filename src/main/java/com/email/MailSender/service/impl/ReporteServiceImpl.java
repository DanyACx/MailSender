package com.email.MailSender.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.email.MailSender.commons.JasperReportManager;
import com.email.MailSender.dto.ReporteDTO;
import com.email.MailSender.enums.TipoReporteEnum;
import com.email.MailSender.service.IReporteService;

import javax.sql.DataSource;
import net.sf.jasperreports.engine.JRException;

@Service
public class ReporteServiceImpl implements IReporteService{


	@Autowired
	private JasperReportManager reportManager;

	@Autowired
	private DataSource dataSource;

	@Override
	public ReporteDTO obtenerReporte(Map<String, Object> params)
			throws JRException, IOException, SQLException {
		String fileName = "reconocimientoDeuda";
		ReporteDTO dto = new ReporteDTO();
		String extension = params.get("tipo").toString().equalsIgnoreCase(TipoReporteEnum.EXCEL.name()) ? ".xlsx"
				: ".pdf";
		dto.setFileName(fileName + extension);

		ByteArrayOutputStream stream = reportManager.export(fileName, params.get("tipo").toString(), params,
				dataSource.getConnection());

		byte[] bs = stream.toByteArray();
		dto.setStream(new ByteArrayInputStream(bs));
		dto.setLength(bs.length);

		return dto;
	}
}
