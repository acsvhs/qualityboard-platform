package dev.andresoeiro.qualityboard.run;

import java.time.Instant;
import java.util.UUID;

public record RunResponse(
        UUID id,
        String name,
        String targetUrl,
        TestFramework framework,
        TestRunStatus status,
        Instant createdAt
) {
    static RunResponse from(TestRun run) {
        return new RunResponse(
                run.getId(), run.getName(), run.getTargetUrl(), run.getFramework(),
                run.getStatus(), run.getCreatedAt()
        );
    }
}
