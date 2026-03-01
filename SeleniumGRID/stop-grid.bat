@echo off
REM This script gracefully stops the Selenium Grid Hub and Node

echo Stopping Selenium Grid...

REM Kill java processes running selenium-server (this will kill both Hub and Node)
taskkill /F /IM java.exe /T

REM Close any cmd windows with "Selenium" in the title
taskkill /F /FI "WINDOWTITLE eq Selenium*"

echo Selenium Grid stopped.
pause

