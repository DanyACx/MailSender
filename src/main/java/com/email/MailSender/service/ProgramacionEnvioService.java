package com.email.MailSender.service;

import java.util.List;

import com.email.MailSender.projections.ProgramacionEnvioP;

public interface ProgramacionEnvioService {

    List<ProgramacionEnvioP> getEnvioMensual();
}
