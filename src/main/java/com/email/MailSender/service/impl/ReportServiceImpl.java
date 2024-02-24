package com.email.MailSender.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.email.MailSender.commons.JasperReportManager;
import com.email.MailSender.dto.ReporteDTO;
import com.email.MailSender.projections.ProgramacionEnvioP;
import com.email.MailSender.service.IReportService;

import jakarta.mail.internet.MimeMessage;

@Service
public class ReportServiceImpl implements IReportService {

	@Autowired
    private JasperReportManager reportManager;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private TemplateEngine templateEngine;

	@Override
    @Async
    public void generateReportAndSendEmail(ProgramacionEnvioP resultado, String templateName, Context context, String fechaInicio, String fechaFin, Integer anio) {
        try {
            // Generar el informe de manera asíncrona
            Map<String, Object> map = new HashMap<>();
            String fileName = "reconocimientoDeuda2";
            ByteArrayOutputStream stream = reportManager.export(fileName, resultado.getTipoFormato().toUpperCase(), map, dataSource.getConnection());
            
            // Preparar el correo electrónico y enviarlo
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setTo(resultado.getEmail());
            helper.setSubject("Reporte: " + resultado.getApellidoPaterno() + " " + resultado.getApellidoMaterno() + " " + resultado.getNombrePersona());
            String htmlContent = templateEngine.process(templateName, context);
            helper.setText(htmlContent, true);
            ReporteDTO dto = new ReporteDTO();
            String extension = ".".concat(resultado.getTipoFormato().toLowerCase());
            dto.setFileName(fileName + extension);
            byte[] bs = stream.toByteArray();
            dto.setStream(new ByteArrayInputStream(bs));
            dto.setLength(bs.length);
            ByteArrayResource byteArrayResource = new ByteArrayResource(bs, dto.getFileName());
            helper.addAttachment(dto.getFileName(), byteArrayResource);
            ClassPathResource imageResource = new ClassPathResource("static/images/banner-quipu_3.png");
            helper.addInline("logoImage", imageResource);
            mailSender.send(mimeMessage);
            System.out.println("Se envió correo a: " + resultado.getEmail());
        } catch (Exception e) {
            System.out.println("Error al enviar correo a: " + resultado.getEmail() + ". " + e.getMessage());
        }
    }
}
