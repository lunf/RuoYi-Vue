@echo off
echo.
echo [Information] The cleaning project.targetCreate the path.。
echo.

%~d0
cd %~dp0

cd ..
call mvn clean

pause