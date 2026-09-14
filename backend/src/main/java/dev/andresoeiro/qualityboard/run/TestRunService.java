package dev.andresoeiro.qualityboard.run;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class TestRunService {
    private final TestRunRepository repository;

    public TestRunService(TestRunRepository repository) {
        this.repository = repository;
    }

    public List<RunResponse> list(String search) {
        String normalized = search == null ? "" : search.trim().toLowerCase(Locale.ROOT);
        return repository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"))
                .stream()
                .filter(run -> normalized.isBlank()
                        || run.getName().toLowerCase(Locale.ROOT).contains(normalized)
                        || run.getFramework().name().toLowerCase(Locale.ROOT).contains(normalized))
                .map(RunResponse::from)
                .toList();
    }

    @Transactional
    public RunResponse create(RunRequest request) {
        TestRun run = new TestRun(request.name(), request.targetUrl(), request.framework());
        return RunResponse.from(repository.save(run));
    }

    @Transactional
    public RunResponse updateStatus(UUID id, TestRunStatus status) {
        TestRun run = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Test run not found"));
        run.setStatus(status);
        return RunResponse.from(run);
    }

    @Transactional
    public void delete(UUID id) {
        if (!repository.existsById(id)) {
            throw new NoSuchElementException("Test run not found");
        }
        repository.deleteById(id);
    }
}
