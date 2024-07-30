@rem
@rem Copyright 2015 the original author or authors.
@rem
@rem Licensed under the Apache License, Version 2.0 (the "License");
@rem you may not use this file except in compliance with the License.
@rem You may obtain a copy of the License at
@rem
@rem      https://www.apache.org/licenses/LICENSE-2.0
@rem
@rem Unless required by applicable law or agreed to in writing, software
@rem distributed under the License is distributed on an "AS IS" BASIS,
@rem WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
@rem See the License for the specific language governing permissions and
@rem limitations under the License.
@rem

@if "%DEBUG%"=="" @echo off
@rem ##########################################################################
@rem
@rem  new startup script for Windows
@rem
@rem ##########################################################################

@rem Set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" setlocal

set DIRNAME=%~dp0
if "%DIRNAME%"=="" set DIRNAME=.
@rem This is normally unused
set APP_BASE_NAME=%~n0
set APP_HOME=%DIRNAME%..

@rem Resolve any "." and ".." in APP_HOME to make it shorter.
for %%i in ("%APP_HOME%") do set APP_HOME=%%~fi

@rem Add default JVM options here. You can also use JAVA_OPTS and NEW_OPTS to pass JVM options to this script.
set DEFAULT_JVM_OPTS=

@rem Find java.exe
if defined JAVA_HOME goto findJavaFromJavaHome

set JAVA_EXE=java.exe
%JAVA_EXE% -version >NUL 2>&1
if %ERRORLEVEL% equ 0 goto execute

echo.
echo ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:findJavaFromJavaHome
set JAVA_HOME=%JAVA_HOME:"=%
set JAVA_EXE=%JAVA_HOME%/bin/java.exe

if exist "%JAVA_EXE%" goto execute

echo.
echo ERROR: JAVA_HOME is set to an invalid directory: %JAVA_HOME%
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:execute
@rem Setup the command line

set CLASSPATH=%APP_HOME%\lib\new-1.0.0-SNAPSHOT.jar;%APP_HOME%\lib\vertx-auth-jwt-4.5.9.jar;%APP_HOME%\lib\vertx-web-4.5.9.jar;%APP_HOME%\lib\vertx-web-client-4.5.9.jar;%APP_HOME%\lib\vertx-auth-common-4.5.9.jar;%APP_HOME%\lib\vertx-web-common-4.5.9.jar;%APP_HOME%\lib\vertx-bridge-common-4.5.9.jar;%APP_HOME%\lib\vertx-uri-template-4.5.9.jar;%APP_HOME%\lib\vertx-core-4.5.9.jar;%APP_HOME%\lib\converter-gson-2.9.0.jar;%APP_HOME%\lib\retrofit-2.9.0.jar;%APP_HOME%\lib\gson-2.9.0.jar;%APP_HOME%\lib\jjwt-jackson-0.11.5.jar;%APP_HOME%\lib\jackson-annotations-2.16.1.jar;%APP_HOME%\lib\jackson-core-2.16.1.jar;%APP_HOME%\lib\jackson-databind-2.16.1.jar;%APP_HOME%\lib\okhttp-4.9.0.jar;%APP_HOME%\lib\ebean-12.12.1.jar;%APP_HOME%\lib\ebean-querybean-12.12.1.jar;%APP_HOME%\lib\mysql-connector-java-8.0.28.jar;%APP_HOME%\lib\HikariCP-4.0.3.jar;%APP_HOME%\lib\jbcrypt-0.4.jar;%APP_HOME%\lib\jjwt-impl-0.11.5.jar;%APP_HOME%\lib\jjwt-api-0.11.5.jar;%APP_HOME%\lib\ebean-agent-12.11.1.jar;%APP_HOME%\lib\netty-handler-proxy-4.1.111.Final.jar;%APP_HOME%\lib\netty-codec-http2-4.1.111.Final.jar;%APP_HOME%\lib\netty-codec-http-4.1.111.Final.jar;%APP_HOME%\lib\netty-resolver-dns-4.1.111.Final.jar;%APP_HOME%\lib\netty-handler-4.1.111.Final.jar;%APP_HOME%\lib\netty-transport-native-unix-common-4.1.111.Final.jar;%APP_HOME%\lib\netty-codec-socks-4.1.111.Final.jar;%APP_HOME%\lib\netty-codec-dns-4.1.111.Final.jar;%APP_HOME%\lib\netty-codec-4.1.111.Final.jar;%APP_HOME%\lib\netty-transport-4.1.111.Final.jar;%APP_HOME%\lib\netty-buffer-4.1.111.Final.jar;%APP_HOME%\lib\netty-resolver-4.1.111.Final.jar;%APP_HOME%\lib\netty-common-4.1.111.Final.jar;%APP_HOME%\lib\ebean-core-12.12.1.jar;%APP_HOME%\lib\ebean-core-type-12.12.1.jar;%APP_HOME%\lib\ebean-api-12.12.1.jar;%APP_HOME%\lib\avaje-config-1.5.jar;%APP_HOME%\lib\slf4j-api-2.0.7.jar;%APP_HOME%\lib\okio-jvm-2.8.0.jar;%APP_HOME%\lib\kotlin-stdlib-1.4.10.jar;%APP_HOME%\lib\ebean-datasource-7.3.jar;%APP_HOME%\lib\protobuf-java-3.11.4.jar;%APP_HOME%\lib\kotlin-stdlib-common-1.4.10.jar;%APP_HOME%\lib\annotations-13.0.jar;%APP_HOME%\lib\ebean-datasource-api-7.3.jar;%APP_HOME%\lib\persistence-api-2.2.5.jar;%APP_HOME%\lib\ebean-annotation-7.3.jar;%APP_HOME%\lib\ebean-types-2.2.jar;%APP_HOME%\lib\ebean-ddl-runner-1.2.jar;%APP_HOME%\lib\classpath-scanner-6.2.jar;%APP_HOME%\lib\ebean-migration-auto-1.1.jar;%APP_HOME%\lib\ebean-externalmapping-api-12.12.1.jar;%APP_HOME%\lib\antlr4-runtime-4.8-1.jar;%APP_HOME%\lib\classpath-scanner-api-6.2.jar


@rem Execute new
"%JAVA_EXE%" %DEFAULT_JVM_OPTS% %JAVA_OPTS% %NEW_OPTS%  -classpath "%CLASSPATH%" io.vertx.core.Launcher %*

:end
@rem End local scope for the variables with windows NT shell
if %ERRORLEVEL% equ 0 goto mainEnd

:fail
rem Set variable NEW_EXIT_CONSOLE if you need the _script_ return code instead of
rem the _cmd.exe /c_ return code!
set EXIT_CODE=%ERRORLEVEL%
if %EXIT_CODE% equ 0 set EXIT_CODE=1
if not ""=="%NEW_EXIT_CONSOLE%" exit %EXIT_CODE%
exit /b %EXIT_CODE%

:mainEnd
if "%OS%"=="Windows_NT" endlocal

:omega
