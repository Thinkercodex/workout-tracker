drop procedure if exists addWorker;

delimiter $$
create procedure addWorker(workerName varchar(20))
begin
    declare workerId int;
    select count(*) into workerId from worker;
    
    set workerId = workerId + 1;
    
    insert into worker values(workerId, workerName);
end$$
delimiter ;

-- A sample call to this procedure
call addWorker('A');
