drop procedure if exists addRep;

delimiter $$
create procedure addRep(workoutId int)
begin
    declare repNum int;
    
    if ( exists (select 1 from workout
                 where workout_id = workoutId
                ) ) then
        select count(*) into repNum from rep;
    
        set repNum = repNum + 1;
    
        insert into rep values(repNum, workoutId);
        
    else
        signal sqlstate '45001'
            set message_text = 'Invalid workoutId.';
    end if;
end$$
delimiter ;

-- A sample call to this procedure
call addRep(1);
