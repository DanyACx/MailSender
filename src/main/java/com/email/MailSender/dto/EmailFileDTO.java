package com.email.MailSender.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
public class EmailFileDTO {

	private String[] toUser;
	private String subject;
	private String message;
	private String area;
	private String solicitante;
	private String dependenciaSolicitante;
	private String motivo;
	
	private MultipartFile file;
}
