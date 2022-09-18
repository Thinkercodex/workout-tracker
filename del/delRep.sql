drop procedure if exists delRep;

delimiter $$
create procedure delRep(workoutId int)
begin
    declare lastRepId int;
    
    if (exists (select 1 from rep where workout_id = workoutId)) then
        select max(rep_id) into lastRepId from rep where workout_id = workoutId group by workout_id;
        
        delete from rep where rep_id = lastRepId;
        
    else
        signal sqlstate '45001'
            set message_text = 'Invalid input.';
    end if;
    
end$$
delimiter ;

-- A sample call to this procedure
call delRep(1);


