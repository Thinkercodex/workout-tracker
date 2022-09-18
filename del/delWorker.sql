drop procedure if exists delWorker;

delimiter $$
create procedure delWorker(workerId int)
begin
    declare maxWorkerId int;
    declare maxWorkerName varChar(20);
    
    if (exists (select 1 from worker where worker_id = workerId)) then
        delete from worker where worker_id = workerId;
        
        select max(worker_id) into maxWorkerId from worker;
        select worker_name into maxWorkerName from worker where worker_id = maxWorkerId;
        
        delete from worker where worker_id = maxWorkerId;
        
        insert into worker values(workerId, maxWorkerName);
    else
        signal sqlstate '45001'
            set message_text = 'Invalid input.';
    end if;
    
end$$
delimiter ;

-- A sample call to this procedure
call delWorker(1);
