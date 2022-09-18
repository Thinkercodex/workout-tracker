drop procedure if exists showUserID;

delimiter $$
create procedure showUserID(workerName varchar(20))
begin
    
    if ( exists (select 1 from worker
                 where worker_name = workerName
                )
       ) then
        select worker_id as 'ID'
        from worker
        where worker_name = workerName;
    
    else
        signal sqlstate '45001'
            set message_text = 'Invalid input.';
    end if;
end$$
delimiter ;

-- A sample call to this procedure
call showUserID('John');
