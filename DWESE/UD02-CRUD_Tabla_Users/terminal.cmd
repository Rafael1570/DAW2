@echo off
set "PATHEXT=.COM;.EXE;.BAT;.CMD;.VBS;.VBE;.JS;.JSE;.WSF;.WSH;.MSC"
set "JAVA_HOME=%~dp0.local-tools\jdk-21"
set "PATH=%JAVA_HOME%\bin;%~dp0.local-tools\apache-maven\bin;%LOCALAPPDATA%\Programs\DockerDesktop\resources\bin;%PATH%"
cd /d "%~dp0"
echo Terminal preparada para Java 21, Maven y Docker.

