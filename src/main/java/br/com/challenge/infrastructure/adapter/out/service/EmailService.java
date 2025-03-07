package br.com.challenge.infrastructure.adapter.out.service;

public interface EmailService {

    void sendEmail(final String to, final String subject, final String body);
}
