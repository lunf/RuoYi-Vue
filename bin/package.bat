@echo off
echo.
echo [Information] PackageWebProjects，producedwar/jarPackage of documents。
echo.

%~d0
cd %~dp0

cd ..
call mvn clean package -Dmaven.test.skip=true

pause