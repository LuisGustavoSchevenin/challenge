package br.com.challenge.adapter.out;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {

    private String from;

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private EmailService emailService;


    @BeforeEach
    void setUp() {
        from = "test@gmail.com";
        ReflectionTestUtils.setField(emailService, "from", from);
    }

    @Test
    void shouldSendEmail() {
        emailService.sendEmail(from, "Email title", "Email body");
        verify(mailSender).send(any(SimpleMailMessage.class));
    }

}