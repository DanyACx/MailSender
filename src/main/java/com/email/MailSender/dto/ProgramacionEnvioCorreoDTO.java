package com.email.MailSender.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "programacion_envio", schema = "public")
public class ProgramacionEnvioCorreoDTO {
	
		@Id
		@Column(name = "id_producto")
		private Integer idUsuario;
		
		@Id
		@Column(name = "id_reporte")
		private Integer idReporte;
		
		@Id
		@Column(name = "id_corr")
		private Integer idCorr;
		
		@Column(name = "periodo")
		private String periodo;
		
		@Column(name = "acumulado")
		private Integer acumulado;
		
		@Column(name = "estado")
		private Integer estado;
		
		@Column(name = "fase")
		private String fase;
		
		@Column(name = "rubro")
		private String rubro;
		
		@Column(name = "unidad")
		private Integer unidad;
		
		@Column(name = "meta_siaf")
		private Integer metaSiaf;


}
