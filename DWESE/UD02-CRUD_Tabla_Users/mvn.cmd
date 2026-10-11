@echo off
setlocal
set "JAVA_HOME=%~dp0.local-tools\jdk-21"
set "MAVEN_SKIP_RC=1"
echo Java del proyecto: %JAVA_HOME%
if not exist "%JAVA_HOME%\bin\java.exe" (
    echo ERROR: No se encuentra Java en la ruta indicada.
    exit /b 1
)
call "%~dp0.local-tools\apache-maven\bin\mvn.cmd" %*
exit /b %ERRORLEVEL%
