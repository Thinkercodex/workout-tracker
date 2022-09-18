drop procedure if exists showNumUser;

delimiter $$
create procedure showNumUser()
begin
    
    select count(*) as 'Number of Users'
    from worker;
    
end$$
delimiter ;

-- A sample call to this procedure
call showNumUser();
