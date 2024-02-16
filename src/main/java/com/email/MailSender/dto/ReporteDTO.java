package com.email.MailSender.dto;

import java.io.ByteArrayInputStream;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ReporteDTO {

	private String fileName;
	private ByteArrayInputStream stream;
	private int length;
}



 