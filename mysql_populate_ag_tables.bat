:: TO load the data in Windows BAT

:: Database connection parameters - edit these variables to reflect your environment
::
:: set MYSQL_BIN_PATH="C:\Program Files\MySQL\MySQL Workbench 8.0"\;
:: set MYSQL_EXECUTABLE=mysql.exe
:: set u=admin
:: set p=September2022
:: set dbserver=database-1.cjpquxunb8do.us-east-2.rds.amazonaws.com
:: set db_name=newschema
:: 
:: set path=%path%;%MYSQL_BIN_PATH%;



set logfile=mysql_ag.log
del %logfile%

echo See %logfile% for output

echo Starting AccessGUDID MySQL Import!




echo MYSQL EXECUTABLE: >> %logfile% 2>&1
echo %MYSQL_EXECUTABLE% >> %logfile% 2>&1

echo Credentials: >> %logfile% 2>&1
echo -u=%u% -p=%p% --host=%dbserver% --database=%db_name% >> %logfile% 2>&1

set MYSQL_EXEC_W_CRED=%MYSQL_EXECUTABLE% -u=%u% -p=%p% --host=%dbserver% --database=%db_name% 

echo %MYSQL_EXEC_W_CRED%

echo ----- >> %logfile% 2>&1

echo BUILDING TABLES >> %logfile% 2>&1
%MYSQL_EXEC_W_CRED%  --local-infile=1  <  mysql_build_ag_tables.sql >> %logfile% 2>&1

echo STARTING IMPORT >> %logfile% 2>&1


echo IMPORTING: Contacts >> %logfile% 2>&1
%MYSQL_EXEC_W_CRED%  --local-infile=1 < mysql_ag_contacts.sql >> %logfile% 2>&1



echo Finished Importing >> %logfile% 2>&1



echo -----
echo DONE!
