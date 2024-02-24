package com.email.MailSender.service;

import java.util.List;

import com.email.MailSender.projections.ProgramacionEnvioP;

public interface IProgramacionEnvioService {

    List<ProgramacionEnvioP> getEnvioMensual();
}
