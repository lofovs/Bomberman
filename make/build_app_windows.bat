@echo off
REM Adjust these variables to match your Maven build outputs
SET APP_PACKAGE=no.uib.inf101.sem2.bomberman
SET APP_VENDOR=My Company
SET APP_VERSION=1.0.0
SET INSTALLER_TYPE=msi
SET JAVA_VERSION=17
SET MAIN_JAR=INF101Sem2-1.0-SNAPSHOT.jar
SET MAIN_CLASS=no.uib.inf101.sem2.bomberman.Main
SET PROJECT_NAME=Bomberman
SET PROJECT_VERSION=1.0.0
SET PATH_TO_MAIN=target\%MAIN_JAR%
SET YEAR=2025

ECHO "setting up directories and files..."
IF EXIST target\java-runtime RMDIR /S /Q target\java-runtime
IF EXIST target\installer RMDIR /S /Q target\installer
MKDIR target\installer\input\libs

REM Copy your main jar
COPY "target\%MAIN_JAR%" "target\installer\input\libs\"

REM If you want dependencies copied, make sure Maven copies them first to target/libs, then uncomment this:
REM XCOPY /S /Q target\libs\* target\installer\input\libs\

ECHO "detecting required modules with jdeps..."
"%JAVA_HOME%\bin\jdeps" --print-module-deps --ignore-missing-deps "%PATH_TO_MAIN%" > target\module-list.txt

ECHO "creating java runtime image with jlink..."
"%JAVA_HOME%\bin\jlink" ^
    --module-path "%JAVA_HOME%\jmods;target\installer\input\libs" ^
    --add-modules @target\module-list.txt ^
    --output target\java-runtime ^
    --compress 2 ^
    --strip-debug

IF ERRORLEVEL 1 (
    ECHO "jlink failed"
    EXIT /B 1
)

ECHO "creating install package with jpackage..."
"%JAVA_HOME%\bin\jpackage" ^
    --type %INSTALLER_TYPE% ^
    --input "target\installer\input\libs" ^
    --name %PROJECT_NAME% ^
    --app-version %PROJECT_VERSION% ^
    --vendor "%APP_VENDOR%" ^
    --icon "%ICON_PATH%" ^
    --main-jar %MAIN_JAR% ^
    --main-class %MAIN_CLASS% ^
    --runtime-image target\java-runtime ^
    --dest target\installer

IF ERRORLEVEL 1 (
    ECHO "jpackage failed"
    EXIT /B 1
)

ECHO "Build and package complete."
