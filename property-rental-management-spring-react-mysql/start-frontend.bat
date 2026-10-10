@echo off
cd /d "%~dp0frontend"
echo Installing frontend packages...
call npm install
echo Starting React frontend...
call npm run dev
pause
