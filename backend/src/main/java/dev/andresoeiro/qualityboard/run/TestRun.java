package dev.andresoeiro.qualityboard.run;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "test_runs")
public class TestRun {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 120)
    private String name;

    @Column(name = "target_url", nullable = false, length = 500)
    private String targetUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private TestFramework framework;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private TestRunStatus status;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    protected TestRun() {
    }

    public TestRun(String name, String targetUrl, TestFramework framework) {
        this.name = name;
        this.targetUrl = targetUrl;
        this.framework = framework;
        this.status = TestRunStatus.QUEUED;
        this.createdAt = Instant.now();
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public TestFramework getFramework() {
        return framework;
    }

    public TestRunStatus getStatus() {
        return status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setStatus(TestRunStatus status) {
        this.status = status;
    }
}
