@echo off
:: ----------------------------------------------------------------------------
:: Maven Wrapper - Windows script
::
:: This script calls the maven wrapper jar located at .mvn\wrapper\maven-wrapper.jar
:: and falls back to the system 'java' command. It will download the maven distribution
:: as configured in .mvn\wrapper\maven-wrapper.properties.
:: ----------------------------------------------------------------------------

setlocal
set MAVEN_PROJECTBASEDIR=%~dp0
set MAVEN_PROJECTBASEDIR=%MAVEN_PROJECTBASEDIR:~0,-1%

if exist "%MAVEN_PROJECTBASEDIR%\.mvn\wrapper\maven-wrapper.jar" (
  java -classpath "%MAVEN_PROJECTBASEDIR%\.mvn\wrapper\maven-wrapper.jar" -Dmaven.multiModuleProjectDirectory="%MAVEN_PROJECTBASEDIR%" org.apache.maven.wrapper.MavenWrapperMain %*
  exit /b %errorlevel%
) else (
  echo Maven wrapper jar not found: %MAVEN_PROJECTBASEDIR%\.mvn\wrapper\maven-wrapper.jar
  echo Please run "download-maven-wrapper.cmd" to fetch the required wrapper jar, or install Maven globally and run 'mvn'.
  exit /b 1
)

