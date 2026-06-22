@echo off
REM Download maven-wrapper jar into .mvn\wrapper\maven-wrapper.jar using available tools (curl or powershell)
setlocal
set WRAPPER_DIR=%~dp0.mvn\wrapper
if not exist "%WRAPPER_DIR%" mkdir "%WRAPPER_DIR%"
set WRAPPER_JAR=%WRAPPER_DIR%\maven-wrapper.jar
set WRAPPER_URL=https://repo1.maven.org/maven2/io/takari/maven-wrapper/0.5.6/maven-wrapper-0.5.6.jar

necho Attempting to download maven-wrapper from %WRAPPER_URL%

n:: Try curl first (Windows 10+)
where curl >nul 2>&1
nif %errorlevel%==0 (
  echo Using curl to download wrapper...
  curl -sSL "%WRAPPER_URL%" -o "%WRAPPER_JAR%"
  if exist "%WRAPPER_JAR%" (
    echo Download succeeded.
    exit /b 0
  ) else (
    echo curl failed to download wrapper.
  )
)

n:: Try PowerShell if available
where powershell >nul 2>&1
nif %errorlevel%==0 (
  echo Using PowerShell to download wrapper...
  powershell -Command "[Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12; Invoke-WebRequest -Uri '%WRAPPER_URL%' -OutFile '%WRAPPER_JAR%'"
  if exist "%WRAPPER_JAR%" (
    echo Download succeeded.
    exit /b 0
  ) else (
    echo PowerShell failed to download wrapper.
  )
)

n:: Try bitsadmin (older systems)
where bitsadmin >nul 2>&1
nif %errorlevel%==0 (
  echo Using bitsadmin to download wrapper...
  bitsadmin /transfer wrapperDownload /download /priority normal "%WRAPPER_URL%" "%WRAPPER_JAR%"
  if exist "%WRAPPER_JAR%" (
    echo Download succeeded.
    exit /b 0
  ) else (
    echo bitsadmin failed to download wrapper.
  )
)

necho Failed to download the maven wrapper automatically.
echo Please download the file manually from:
echo %WRAPPER_URL%
echo Save it as: %WRAPPER_JAR%
exit /b 1

