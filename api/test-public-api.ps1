$base = 'http://localhost:3000/api/public'
$key  = 'sh_demo_publica_123'

Write-Host "`n=== 1) Sem chave (deve falhar 401) ===" -ForegroundColor Cyan
try { Invoke-RestMethod "$base/empresas/buscar?nome=Empresa" | ConvertTo-Json } catch { Write-Host $_.ErrorDetails.Message -ForegroundColor Yellow }

Write-Host "`n=== 2) Chave invalida (401) ===" -ForegroundColor Cyan
try { Invoke-RestMethod "$base/empresas/buscar?nome=Empresa&api_key=errada" | ConvertTo-Json } catch { Write-Host $_.ErrorDetails.Message -ForegroundColor Yellow }

Write-Host "`n=== 3) Buscar via header X-API-Key ===" -ForegroundColor Cyan
Invoke-RestMethod "$base/empresas/buscar?nome=Empresa" -Headers @{ 'X-API-Key' = $key } | ConvertTo-Json -Depth 5

Write-Host "`n=== 4) score-por-nome (rota da badge) ===" -ForegroundColor Cyan
Invoke-RestMethod "$base/empresas/score-por-nome?nome=Empresa%20Teste&api_key=$key" | ConvertTo-Json -Depth 5

Write-Host "`n=== 5) score-por-nome com empresa inexistente ===" -ForegroundColor Cyan
Invoke-RestMethod "$base/empresas/score-por-nome?nome=NaoExiste&api_key=$key" | ConvertTo-Json -Depth 5

Write-Host "`n=== 6) Rate limit headers ===" -ForegroundColor Cyan
$r = Invoke-WebRequest "$base/empresas/buscar?nome=Empresa&api_key=$key"
Write-Host ("Limit: " + $r.Headers.'X-RateLimit-Limit')
Write-Host ("Remaining: " + $r.Headers.'X-RateLimit-Remaining')
