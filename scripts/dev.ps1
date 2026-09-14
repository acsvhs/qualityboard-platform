$ErrorActionPreference = "Stop"
$Root = Split-Path -Parent $PSScriptRoot

Write-Host "Starting PostgreSQL..."
docker compose -f (Join-Path $Root "docker-compose.yml") up -d db

Write-Host "Starting Spring Boot API..."
$backend = Start-Process -FilePath "mvn.cmd" -ArgumentList "spring-boot:run" -WorkingDirectory (Join-Path $Root "backend") -PassThru

Write-Host "Installing frontend dependencies..."
Push-Location (Join-Path $Root "frontend")
try {
    npm.cmd install
    Write-Host "Starting Vite. Stop this window with Ctrl+C."
    npm.cmd run dev
}
finally {
    Pop-Location
    if (!$backend.HasExited) {
        Stop-Process -Id $backend.Id
    }
}
