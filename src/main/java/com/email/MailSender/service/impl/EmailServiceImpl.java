package com.email.MailSender.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.email.MailSender.commons.JasperReportManager;
import com.email.MailSender.dao.ProgramacionEnvioRepository;
import com.email.MailSender.dto.ReporteDTO;
import com.email.MailSender.enums.TipoReporteEnum;
import com.email.MailSender.projections.ProgramacionEnvioP;
import com.email.MailSender.service.IEmailService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import net.sf.jasperreports.engine.JRException;

@Service
public class EmailServiceImpl implements IEmailService {

	@Value("${email.sender}")
	private String emailUser;

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private TemplateEngine templateEngine;

	@Autowired
	private JasperReportManager reportManager;

	@Autowired
	private DataSource dataSource;

	@Autowired
	private ProgramacionEnvioRepository programacionEnvioRepository;

	@Override
	public void sendEmail(String[] toUser, String subject, String message) {

		SimpleMailMessage mailMessage = new SimpleMailMessage();

		mailMessage.setFrom(emailUser); // cuenta que envia email
		mailMessage.setTo(toUser);
		mailMessage.setSubject(subject);
		mailMessage.setText(message);

		mailSender.send(mailMessage); // enviando el correo

	}

	@Override
	public void sendEmailWithFile(String[] toUser, String subject, String message, File file) {

		try {
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true,
					StandardCharsets.UTF_8.name());

			mimeMessageHelper.setFrom(emailUser);
			mimeMessageHelper.setTo(toUser);
			mimeMessageHelper.setSubject(subject);
			mimeMessageHelper.setText(message);
			mimeMessageHelper.addAttachment(file.getName(), file); // nombre del archivo y luego el archivo

			mailSender.send(mimeMessage);

		} catch (Exception e) {
			throw new RuntimeException();
		}
	}

	public void sendEmailWithHtmlTemplate(String[] toUser, String subject, String templateName, Context context) {
		MimeMessage mimeMessage = mailSender.createMimeMessage();

		try {
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
			helper.setTo(toUser);
			helper.setSubject(subject);
			String htmlContent = templateEngine.process(templateName, context);
			helper.setText(htmlContent, true);

			// Embed image
			ClassPathResource imageResource = new ClassPathResource("static/images/banner-quipu_3.png");
			helper.addInline("logoImage", imageResource);

			mailSender.send(mimeMessage);
		} catch (MessagingException e) {
			// Handle exception
		}
	}

	public void sendEmailWithHtmlTemplateAndFile(String[] toUser, String subject, String templateName, Context context,
			File file) {
		MimeMessage mimeMessage = mailSender.createMimeMessage();

		try {
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
			helper.setTo(toUser);
			helper.setSubject(subject);
			String htmlContent = templateEngine.process(templateName, context);
			helper.setText(htmlContent, true);
			helper.addAttachment(file.getName(), file); // nombre del archivo y luego el archivo

			// Embed image
			ClassPathResource imageResource = new ClassPathResource("static/images/banner-quipu_3.png");
			helper.addInline("logoImage", imageResource);

			mailSender.send(mimeMessage);
		} catch (MessagingException e) {
			// Handle exception
		}
	}

	public void sendEmailWithHtmlTemplateAndFile1(String[] toUser, String subject, String templateName, Context context,
			String fechaInicio, String fechaFin, String tipoArchivo) throws JRException, IOException, SQLException {
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		Map<String, Object> map = new HashMap<>();

		try {
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
			helper.setTo(toUser);
			helper.setSubject(subject);
			String htmlContent = templateEngine.process(templateName, context);
			helper.setText(htmlContent, true);

			String fileName = "reconocimientoDeuda";
			ReporteDTO dto = new ReporteDTO();
			String extension = tipoArchivo.equalsIgnoreCase(TipoReporteEnum.EXCEL.name()) ? ".xlsx" : ".pdf";

			dto.setFileName(fileName + extension);

			map.put("FECHAINICIO", fechaInicio);
			map.put("FECHAFIN", fechaFin);
			map.put("tipo", tipoArchivo);

			ByteArrayOutputStream stream = reportManager.export(fileName, tipoArchivo, map, dataSource.getConnection());

			byte[] bs = stream.toByteArray();
			dto.setStream(new ByteArrayInputStream(bs));
			dto.setLength(bs.length);

			ByteArrayResource byteArrayResource = new ByteArrayResource(bs, dto.getFileName());

			helper.addAttachment(dto.getFileName(), byteArrayResource); // nombre del archivo y luego el archivo

			// Embed image
			ClassPathResource imageResource = new ClassPathResource("static/images/banner-quipu_3.png");
			helper.addInline("logoImage", imageResource);

			mailSender.send(mimeMessage);
		} catch (MessagingException e) {
			// Handle exception
		}
	}

	@Override
	public List<ProgramacionEnvioP> getEnvioMensual() {
		List<ProgramacionEnvioP> resultados = programacionEnvioRepository.obtenerInformacionEnvioMensual();

		return resultados;
	}

	public void sendEmailWithHtmlTemplateAndFile2(String templateName, Context context, String fechaInicio,
			String fechaFin) throws JRException, IOException, SQLException {
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		List<ProgramacionEnvioP> resultados = programacionEnvioRepository.obtenerInformacionEnvioMensual();
		Map<String, Object> map = new HashMap<>();
		String fileName = "reconocimientoDeuda";
		String htmlContent = templateEngine.process(templateName, context);

		resultados.forEach(resultado -> {

			try {

				MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

				helper.setTo(resultado.getEmail());

				helper.setSubject("Reporte: " + resultado.getApellidoPaterno() + " " + resultado.getApellidoMaterno() + " " + resultado.getNombrePersona());

				helper.setText(htmlContent, true);

				ReporteDTO dto = new ReporteDTO();
				String extension = ".".concat(resultado.getTipoFormato().toLowerCase());
				dto.setFileName(fileName + extension);
				map.put("FECHAINICIO", fechaInicio);
				map.put("FECHAFIN", fechaFin);
				map.put("tipo", resultado.getTipoFormato().toUpperCase());
				ByteArrayOutputStream stream;

				stream = reportManager.export(fileName, resultado.getTipoFormato().toUpperCase(), map,
						dataSource.getConnection());

				byte[] bs = stream.toByteArray();

				dto.setStream(new ByteArrayInputStream(bs));
				dto.setLength(bs.length);

				ByteArrayResource byteArrayResource = new ByteArrayResource(bs, dto.getFileName());

				helper.addAttachment(dto.getFileName(), byteArrayResource);

				ClassPathResource imageResource = new ClassPathResource("static/images/banner-quipu_3.png");

				helper.addInline("logoImage", imageResource);

				mailSender.send(mimeMessage);
				System.out.println("Se envio para: " + resultado.getEmail());
			} catch (Exception e) {

			}
		});

	}

}
