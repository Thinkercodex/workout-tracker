drop procedure if exists addWorkout;

delimiter $$
create procedure addWorkout(workerId int, workoutNameId int, workoutMaxReps int)
begin
    declare workoutId int;
    
    if ( exists (select 1 from worker
                 where worker_id = workerId
                )
        and exists(select 1 from workout_name where workout_name_id = workoutNameId)
        and workoutMaxReps >= 0
       ) then
        select count(*) into workoutId from workout;
    
        set workoutId = workoutId + 1;
    
        insert into workout values(workoutId, workerId, workoutNameId, workoutMaxReps, curdate());
        
    else
        signal sqlstate '45001'
            set message_text = 'Invalid input.';
    end if;
end$$
delimiter ;

-- A sample call to this procedure
call addWorkout(1, 2, 10);
