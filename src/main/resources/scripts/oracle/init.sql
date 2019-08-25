alter session set "_ORACLE_SCRIPT"=true;
create user student identified by student;
grant connect, resource to student;
exit;