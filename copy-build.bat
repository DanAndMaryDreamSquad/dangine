set p="C:\Users\Daniel\starrunnable"
set lp="\star_lib"

if not exist %p%%lp% mkdir %p%%lp%
XCOPY "buildscripts\run-stardust.bat" "%p%" /Y
XCOPY "lib\*.jar" "%p%%lp%" /Y
XCOPY "input\*.jar" "%p%%lp%" /Y
XCOPY "lib\*.so" "%p%%lp%" /Y
XCOPY "input\*.so" "%p%%lp%" /Y
XCOPY "lib\*.dll" "%p%" /Y
XCOPY "input\*.dll" "%p%" /Y

"C:\Program Files\Java\jdk1.7.0_03\bin\jar.exe" -cf %p%\star.jar src\assets -C bin dangine

echo done
pause
echo The batch file is complete.