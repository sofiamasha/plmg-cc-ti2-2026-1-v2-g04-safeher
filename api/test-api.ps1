$ErrorActionPreference = 'Continue'
$base = 'http://localhost:3000'

function Show($title, $obj) {
  Write-Host "`n=== $title ===" -ForegroundColor Cyan
  $obj | ConvertTo-Json -Depth 6
}

# 1) Health
try {
  $h = Invoke-RestMethod "$base/health"
  Show '1) GET /health' $h
} catch { Write-Host "Health falhou: $_" -ForegroundColor Red }

# 2) Cadastro (email unico)
$email = "teste_$([int][double]::Parse((Get-Date -UFormat %s)))@safeher.com"
$senha = 'senha123'
$cadBody = @{ nome='Teste Usuaria'; email=$email; senha=$senha } | ConvertTo-Json
$token = $null
try {
  $cad = Invoke-RestMethod -Method POST "$base/api/auth/cadastro" -ContentType 'application/json' -Body $cadBody
  Show "2) POST /api/auth/cadastro ($email)" $cad
  $token = $cad.token
} catch {
  Write-Host "Cadastro falhou: $($_.ErrorDetails.Message)" -ForegroundColor Yellow
}

# 3) Login
$logBody = @{ email=$email; senha=$senha } | ConvertTo-Json
try {
  $log = Invoke-RestMethod -Method POST "$base/api/auth/login" -ContentType 'application/json' -Body $logBody
  Show '3) POST /api/auth/login' $log
  $token = $log.token
} catch { Write-Host "Login falhou: $($_.ErrorDetails.Message)" -ForegroundColor Red }

$H = @{ Authorization = "Bearer $token" }

# 4) Perfil
try { Show '4) GET /api/auth/perfil' (Invoke-RestMethod "$base/api/auth/perfil" -Headers $H) }
catch { Write-Host "Perfil falhou: $($_.ErrorDetails.Message)" -ForegroundColor Red }

# 5) Listar empresas
try { Show '5) GET /api/empresas' (Invoke-RestMethod "$base/api/empresas" -Headers $H) }
catch { Write-Host "Listar empresas falhou: $($_.ErrorDetails.Message)" -ForegroundColor Red }

# 6) Criar empresa
$nomeEmp = "Empresa Teste $(Get-Random)"
$empBody = @{ nome=$nomeEmp; cnpj='00.000.000/0001-00'; email='contato@empresateste.com'; telefone='(31) 99999-9999' } | ConvertTo-Json
$empresaId = $null
try {
  $emp = Invoke-RestMethod -Method POST "$base/api/empresas" -Headers $H -ContentType 'application/json' -Body $empBody
  Show '6) POST /api/empresas' $emp
  if ($emp.id) { $empresaId = $emp.id } elseif ($emp.empresa.id) { $empresaId = $emp.empresa.id }
  Write-Host "empresaId capturado: $empresaId" -ForegroundColor Green
} catch { Write-Host "Criar empresa falhou: $($_.ErrorDetails.Message)" -ForegroundColor Red }

# 7) Buscar por nome (rota da extensao)
try { Show '7) GET /api/empresas/buscar?nome=Empresa' (Invoke-RestMethod "$base/api/empresas/buscar?nome=Empresa" -Headers $H) }
catch { Write-Host "Buscar empresa falhou: $($_.ErrorDetails.Message)" -ForegroundColor Red }

# 8) Score
if ($empresaId) {
  try { Show "8) GET /api/empresas/$empresaId/score" (Invoke-RestMethod "$base/api/empresas/$empresaId/score" -Headers $H) }
  catch { Write-Host "Score falhou: $($_.ErrorDetails.Message)" -ForegroundColor Red }
}

# 9) Criar avaliacao
if ($empresaId) {
  $avBody = @{ nota=5; comentario='Ambiente respeitoso e inclusivo.'; nomeEmpresa=$nomeEmp; empresaId=$empresaId } | ConvertTo-Json
  try {
    $av = Invoke-RestMethod -Method POST "$base/api/avaliacoes" -Headers $H -ContentType 'application/json' -Body $avBody
    Show '9) POST /api/avaliacoes' $av
  } catch { Write-Host "Criar avaliacao falhou: $($_.ErrorDetails.Message)" -ForegroundColor Red }
}

# 10) Listar avaliacoes da empresa
if ($empresaId) {
  try { Show "10) GET /api/avaliacoes/empresa/$empresaId" (Invoke-RestMethod "$base/api/avaliacoes/empresa/$empresaId" -Headers $H) }
  catch { Write-Host "Listar avaliacoes falhou: $($_.ErrorDetails.Message)" -ForegroundColor Red }
}

Write-Host "`n=== Fim ===" -ForegroundColor Cyan
