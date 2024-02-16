package com.email.MailSender.dao.entities;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "programacion_envio", schema = "public")
public class ProgramacionEnvio {

	 	@EmbeddedId
	    private ProgramacionEnvioId id;

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

	    // Getters y setters
}
