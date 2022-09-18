drop procedure if exists showAllUsers;

delimiter $$
create procedure showAllUsers()
begin
    select worker_name as Name from worker;
end$$
delimiter ;

-- A sample call to this procedure
call showAllUsers();
