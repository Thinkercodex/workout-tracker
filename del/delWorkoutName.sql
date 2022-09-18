drop procedure if exists delWorkoutName;

delimiter $$
create procedure delWorkoutName(workoutNameId int)
begin
    declare maxWorkoutNameId int;
    declare maxWorkoutName varChar(40);
    
    if (exists (select 1 from workout_name where workout_name_id = workoutNameId)) then
        delete from workout_name where workout_name_id = workoutNameId;
        
        select max(workout_name_id) into maxWorkoutNameId from workout_name;
        select workout_name into maxWorkoutName from workout_name where workout_name_id = maxWorkoutNameId;
        
        delete from workout_name where workout_name_id = maxWorkoutNameId;
        
        insert into workout_name values(maxWorkoutNameId, maxWorkoutName);
    else
        signal sqlstate '45001'
            set message_text = 'Invalid input.';
    end if;
    
end$$
delimiter ;

-- A sample call to this procedure
call delWorker(1);
