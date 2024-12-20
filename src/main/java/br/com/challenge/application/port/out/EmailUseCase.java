package br.com.challenge.application.port.out;

public interface EmailUseCase {

    void sendEmail(String to, String subject, String body);
}
