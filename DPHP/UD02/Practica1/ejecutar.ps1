param([ValidateSet('serve', 'test')][string]$Modo = 'serve')
Set-Location -LiteralPath $PSScriptRoot
$phpExe = (Get-Command php -ErrorAction Stop).Source
$extDir = Join-Path (Split-Path $phpExe) 'ext'
$opciones = @('-n', '-d', "extension_dir=$extDir", '-d', 'extension=openssl', '-d', 'extension=mbstring', '-d', 'extension=curl', '-d', 'extension=zip', '-d', 'date.timezone=Europe/Madrid')
if ($Modo -eq 'test') {
    & $phpExe @opciones 'vendor/phpunit/phpunit/phpunit'
} else {
    & $phpExe @opciones '-S' '127.0.0.1:8000' '-t' 'public' 'public/index.php'
}

exit $LASTEXITCODE
