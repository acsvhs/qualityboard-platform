package dev.andresoeiro.qualityboard.run;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TestRunRepository extends JpaRepository<TestRun, UUID> {
}
