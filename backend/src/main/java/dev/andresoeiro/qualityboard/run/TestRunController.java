package dev.andresoeiro.qualityboard.run;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/runs")
@Tag(name = "Test runs")
public class TestRunController {
    private final TestRunService service;

    public TestRunController(TestRunService service) {
        this.service = service;
    }

    @GetMapping
    List<RunResponse> list(@RequestParam(defaultValue = "") String search) {
        return service.list(search);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    RunResponse create(@Valid @RequestBody RunRequest request) {
        return service.create(request);
    }

    @PatchMapping("/{id}/status")
    RunResponse updateStatus(@PathVariable UUID id, @RequestParam TestRunStatus value) {
        return service.updateStatus(id, value);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}
