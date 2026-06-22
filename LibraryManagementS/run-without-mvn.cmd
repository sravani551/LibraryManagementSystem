@echo off
REM Download a portable Maven (if not already present) and run the Spring Boot app
REM This script uses PowerShell to download and extract Apache Maven to a local .maven folder

setlocal enabledelayedexpansion

REM Maven version to use - change if you want a different version
set MAVEN_VERSION=3.9.4
set MAVEN_DIR=%~dp0.maven\apache-maven-%MAVEN_VERSION%

nREM Normalize path (remove trailing backslash from %~dp0)
set ROOT_DIR=%~dp0
n
nif not exist "%MAVEN_DIR%" (
  echo Local Maven not found. Downloading Apache Maven %MAVEN_VERSION%...
  powershell -Command "[Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12; Invoke-WebRequest -Uri 'https://dlcdn.apache.org/maven/maven-3/%MAVEN_VERSION%/binaries/apache-maven-%MAVEN_VERSION%-bin.zip' -OutFile 'apache-maven.zip'"
  if exist "apache-maven.zip" (
    echo Extracting...
    powershell -Command "Expand-Archive -Path 'apache-maven.zip' -DestinationPath '.maven' -Force"
    del apache-maven.zip
  ) else (
    echo Failed to download Maven. Please ensure you have internet access or install Maven manually.
    exit /b 1
  )
)

necho Using Maven at %MAVEN_DIR%\bin\mvn.cmd

n"%MAVEN_DIR%\bin\mvn.cmd" -f "%~dp0pom.xml" spring-boot:run

nendlocal

