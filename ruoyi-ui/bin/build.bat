@echo off
echo.
echo [Information] PackageWebProjects，produceddistDocuments。
echo.

%~d0
cd %~dp0

cd ..
npm run build:prod

pause
