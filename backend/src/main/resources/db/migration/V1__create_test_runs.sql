CREATE TABLE test_runs (
    id UUID PRIMARY KEY,
    name VARCHAR(120) NOT NULL,
    target_url VARCHAR(500) NOT NULL,
    framework VARCHAR(30) NOT NULL,
    status VARCHAR(30) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE INDEX idx_test_runs_created_at ON test_runs (created_at DESC);
CREATE INDEX idx_test_runs_framework ON test_runs (framework);
