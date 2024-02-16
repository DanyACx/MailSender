package com.email.MailSender.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.context.Context;

import com.email.MailSender.dto.EmailDTO;
import com.email.MailSender.dto.EmailFileDTO;
import com.email.MailSender.dto.EmailFileJasperDTO2;
import com.email.MailSender.projections.ProgramacionEnvioP;
import com.email.MailSender.service.IEmailService;

@RestController
@RequestMapping("/v1")
public class MailController {

	@Autowired
	private IEmailService emailService;

	// se retornar cualquier tipo de dato
	@PostMapping("/sendMessage") // convertor json que viene a DTO
	public ResponseEntity<?> receiveRequestEmail(@RequestBody EmailDTO emailDTO) {

		System.out.println("Mensaje Recibido " + emailDTO);

		emailService.sendEmail(emailDTO.getToUser(), emailDTO.getSubject(), emailDTO.getMessage());

		Map<String, String> response = new HashMap<>();
		response.put("estado", "Enviado");

		return ResponseEntity.ok(response);

	}

	@PostMapping("/sendMessageFile")
	public ResponseEntity<?> receiveRequestEmailWithFile(@ModelAttribute EmailFileDTO emailFileDTO) {

		try {
			String fileName = emailFileDTO.getFile().getOriginalFilename();
			Path path = Paths.get("src/mail/resources/files" + fileName);
			Files.createDirectories(path.getParent());
			Files.copy(emailFileDTO.getFile().getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			File file = path.toFile();

			String htmlContent = "________________________________________________________________________________________________________________"
					+ "|   ÁREA   | " + "     SOLICITANTE      " + "   DEPENDENCIA SOLICITANTE  "
					+ "       MOTIVO PARA LA EJECUCIÓN          |" + ""
					+ "<table border='1' style='border-collapse: collapse; width: 100%;'><thead><tr><th>ÁREA</th><th>SOLICITANTE</th><th>DEPENDENCIA SOLICITANTE</th><th>MOTIVO PARA LA EJECUCIÓN</th></tr></thead>"
					+ "<tbody><tr><td>" + emailFileDTO.getArea() + "</td><td>" + emailFileDTO.getSolicitante()
					+ "</td><td>" + emailFileDTO.getDependenciaSolicitante() + "</td><td>" + emailFileDTO.getMotivo()
					+ "</td></tr></tbody>" + "</table>";

			emailService.sendEmailWithFile(emailFileDTO.getToUser(), emailFileDTO.getSubject(), htmlContent, file);

			Map<String, String> response = new HashMap<>();
			response.put("estado", "Enviado");
			response.put("archivo", "fileName");

			return ResponseEntity.ok(response);

		} catch (Exception e) {
			throw new RuntimeException("Error al enviar el email con archivo " + e.getMessage());
		}

	}

	// se retornar cualquier tipo de dato
	@PostMapping("/sendMessageHTML") // convertor json que viene a DTO
	public ResponseEntity<?> sendHtmlEmail(@RequestBody EmailDTO emailDTO) {

		System.out.println("Mensaje Recibido " + emailDTO);
		Context context = new Context();
		context.setVariable("message", emailDTO.getMessage());

		emailService.sendEmailWithHtmlTemplate(emailDTO.getToUser(), emailDTO.getSubject(), "plantilla3", context);

		Map<String, String> response = new HashMap<>();
		response.put("estado", "Enviado");

		return ResponseEntity.ok(response);

	}

	@PostMapping("/sendMessageHTMLWithFile") // convertor json que viene a DTO
	public ResponseEntity<?> sendHtmlEmailWithFile(@ModelAttribute EmailFileDTO emailFileDTO) {

		try {

			String fileName = emailFileDTO.getFile().getOriginalFilename();
			Path path = Paths.get("src/mail/resources/files" + fileName);
			Files.createDirectories(path.getParent());
			Files.copy(emailFileDTO.getFile().getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			File file = path.toFile();

			System.out.println("Mensaje Recibido " + emailFileDTO);
			Context context = new Context();
			context.setVariable("message", emailFileDTO.getMessage());

			emailService.sendEmailWithHtmlTemplateAndFile(emailFileDTO.getToUser(), emailFileDTO.getSubject(),
					"plantilla3", context, file);

			Map<String, String> response = new HashMap<>();
			response.put("estado", "Enviado");

			return ResponseEntity.ok(response);
		} catch (Exception e) {
			throw new RuntimeException("Error al enviar el email con archivo " + e.getMessage());
		}

	}

	@PostMapping("/sendMessageHTMLWithFile2") // convertor json que viene a DTO
	public ResponseEntity<?> sendHtmlEmailWithFile2(@ModelAttribute EmailFileJasperDTO2 emailFileJasperDTO) {

		try {

			// String fileName = emailFileDTO.getFile().getOriginalFilename();
			// Path path = Paths.get("src/mail/resources/files" + fileName);
			// Files.createDirectories(path.getParent());
			// Files.copy(emailFileDTO.getFile().getInputStream(), path,
			// StandardCopyOption.REPLACE_EXISTING);
			// File file = path.toFile();

			System.out.println("Mensaje Recibido " + emailFileJasperDTO);
			Context context = new Context();
			context.setVariable("message", emailFileJasperDTO.getMessage());

			emailService.sendEmailWithHtmlTemplateAndFile1(emailFileJasperDTO.getToUser(),
					emailFileJasperDTO.getSubject(), "plantilla3", context, emailFileJasperDTO.getFechaInicio(),
					emailFileJasperDTO.getFechaFin(), emailFileJasperDTO.getTipo());

			Map<String, String> response = new HashMap<>();
			response.put("estado", "Enviado");

			return ResponseEntity.ok(response);
		} catch (Exception e) {
			throw new RuntimeException("Error al enviar el email con archivo " + e.getMessage());
		}

	}

	@GetMapping("/sendProgramado")
	public List<ProgramacionEnvioP> sendProgramado() {
		return emailService.getEnvioMensual();
	}

	@PostMapping("/sendMessageHTMLWithFile3") // convertor json que viene a DTO
	public ResponseEntity<?> sendHtmlEmailWithFile3(@ModelAttribute EmailFileJasperDTO2 emailFileJasperDTO) {

		try {

			System.out.println("Mensaje Recibido " + emailFileJasperDTO);
			Context context = new Context();
			context.setVariable("message", emailFileJasperDTO.getMessage());

			emailService.sendEmailWithHtmlTemplateAndFile2("plantilla3", context, emailFileJasperDTO.getFechaInicio(),
					emailFileJasperDTO.getFechaFin());

			Map<String, String> response = new HashMap<>();
			response.put("estado", "Enviado");

			return ResponseEntity.ok(response);
		} catch (Exception e) {
			throw new RuntimeException("Error al enviar el email con archivo " + e.getMessage());
		}

	}

}
