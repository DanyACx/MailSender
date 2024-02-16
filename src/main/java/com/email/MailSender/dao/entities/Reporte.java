package com.email.MailSender.dao.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "reportes", schema = "public")
public class Reporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reporte")
    private Integer idReporte;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "codigo", unique = true)
    private String codigo;

    @Column(name = "tipo_formato")
    private String tipoFormato;

    @Column(name = "descripcion")
    private String descripcion;

    // Getters y setters
}