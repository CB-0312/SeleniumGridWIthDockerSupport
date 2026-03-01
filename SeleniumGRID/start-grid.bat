@echo off
cd /d "%~dp0"

echo Starting Selenium Hub...
start "Selenium Hub" cmd /k java -jar selenium-server-4.40.0.jar hub

timeout /t 5

echo Starting Selenium Node...
start "Selenium Node" cmd /k java -jar selenium-server-4.40.0.jar node --hub http://localhost:4444

echo Grid started.