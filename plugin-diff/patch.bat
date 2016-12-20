@echo off

call :Begin %1 %2 
echo Finished.
goto :eof 

:Begin 
echo Batch file begins. 
call mvn clean compile assembly:single
call :Recurse %1 %2
goto :eof 

:Recurse 
echo This is a recursive call. 
java -jar target\plugin-diff-1.0-SNAPSHOT-jar-with-dependencies.jar %1 %2
echo The parameters received were %1 and %2 
goto :eof 
