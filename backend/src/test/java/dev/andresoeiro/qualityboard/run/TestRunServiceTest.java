package dev.andresoeiro.qualityboard.run;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TestRunServiceTest {
    @Mock
    private TestRunRepository repository;

    @Test
    void createsQueuedRun() {
        TestRunService service = new TestRunService(repository);
        when(repository.save(org.mockito.ArgumentMatchers.any(TestRun.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        RunResponse response = service.create(
                new RunRequest("Checkout smoke", "https://example.test", TestFramework.PLAYWRIGHT)
        );

        ArgumentCaptor<TestRun> captor = ArgumentCaptor.forClass(TestRun.class);
        verify(repository).save(captor.capture());
        assertEquals(TestRunStatus.QUEUED, response.status());
        assertEquals("Checkout smoke", captor.getValue().getName());
    }
}
