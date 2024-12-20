package br.com.challenge.adapter.in.scheduler;

import br.com.challenge.adapter.out.persistence.CadastroEntity;
import br.com.challenge.adapter.out.persistence.CadastroRepository;
import br.com.challenge.utils.Fixture;
import org.awaitility.Durations;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.verify;

@SpringBootTest
class NotifySchedulerTest {

    @MockitoSpyBean
    private CadastroRepository cadastroRepository;

    @MockitoSpyBean
    private NotifyScheduler scheduledTask;

    @Test
    void shouldInvokeScheduledTask() {
        CadastroEntity entity = insertCadastro("123.555.332-12");

        await().atMost(Durations.TWO_MINUTES).untilAsserted(() -> {
            verify(scheduledTask).sendNotification();
            verify(cadastroRepository).findByNotifiedFalse();
            verify(cadastroRepository).save(entity);
        });
    }

    private CadastroEntity insertCadastro(final String cpf) {
        CadastroEntity entity = Fixture.buildCadastroEntity(cpf);
        return cadastroRepository.save(entity);
    }
}