package com.email.MailSender.dao.entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class ProgramacionEnvioId implements Serializable{

	@Column(name = "id_usuario")
    private Integer idUsuario;

    @Column(name = "id_reporte")
    private Integer idReporte;

    @Column(name = "id_corr")
    private Integer idCorr;

    // Getters y setters
}
