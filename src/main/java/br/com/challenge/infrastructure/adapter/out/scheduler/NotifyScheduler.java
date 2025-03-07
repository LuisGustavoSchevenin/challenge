package br.com.challenge.infrastructure.adapter.out.scheduler;

import br.com.challenge.infrastructure.adapter.in.web.dto.EmailNotification;
import br.com.challenge.infrastructure.adapter.out.service.EmailService;
import br.com.challenge.infrastructure.adapter.persistence.mapper.CadastroMapper;
import br.com.challenge.infrastructure.adapter.persistence.mapper.EmailMapper;
import br.com.challenge.infrastructure.adapter.persistence.entity.CadastroEntity;
import br.com.challenge.infrastructure.adapter.out.repository.CadastroRepository;
import br.com.challenge.domain.model.Cadastro;
import br.com.challenge.utils.ChallengeUtils;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotifyScheduler {

    private static final Logger LOG = LoggerFactory.getLogger(NotifyScheduler.class);

    private final CadastroRepository cadastroRepository;
    private final CadastroMapper cadastroMapper;
    private final EmailMapper emailMapper;
    private final EmailService emailUseCase;
    private final MeterRegistry meterRegistry;

    public NotifyScheduler(CadastroRepository cadastroRepository, CadastroMapper cadastroMapper,
                           EmailMapper emailMapper, EmailService emailUseCase, MeterRegistry meterRegistry) {
        this.cadastroRepository = cadastroRepository;
        this.cadastroMapper = cadastroMapper;
        this.emailMapper = emailMapper;
        this.emailUseCase = emailUseCase;
        this.meterRegistry = meterRegistry;
    }

    @Scheduled(cron = "${cron.notification.time}")
    public void sendNotification() {
        List<CadastroEntity> entityList = cadastroRepository.findByNotifiedFalse();
        LOG.info("{} Cadastros were found to be notified.", entityList.size());

        entityList.forEach(cadastroEntity -> {
            Cadastro cadastro = cadastroMapper.toCadastro(cadastroEntity);
            EmailNotification emailNotification = buildEmailNotification(cadastro);
            emailUseCase.sendEmail(cadastro.getEmail(), emailNotification.subject(), emailNotification.toString());
            updateCadastro(cadastroEntity);
            LOG.info("Notification sent successfully to cadastroId:{}, email:{}", cadastro.getCadastroId(),
                    cadastro.getEmail());

            Counter counter = Counter.builder("notifications_counter")
                    .description("Amount of notifications already sent")
                    .register(meterRegistry);
            counter.increment();
        });
    }

    private EmailNotification buildEmailNotification(final Cadastro cadastro) {
        String emailSubject = ChallengeUtils.getMessage("email.subject");
        String emailMessage = ChallengeUtils.getMessage("email.message");

        return emailMapper.toEmailNotification(emailSubject, emailMessage, cadastro);
    }

    private void updateCadastro(final CadastroEntity cadastroEntity) {
        cadastroEntity.setNotified(true);
        cadastroRepository.save(cadastroEntity);
    }
}
