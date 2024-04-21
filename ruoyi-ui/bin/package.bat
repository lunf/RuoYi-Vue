@echo off
echo.
echo [Information] InstalledWebProjects，producednode_modulesDocuments。
echo.

%~d0
cd %~dp0

cd ..
npm install --registry=https://registry.npmmirror.com

pause
