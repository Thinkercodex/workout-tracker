drop procedure if exists showUserWorkouts;

delimiter $$
create procedure showUserWorkouts(workerId int)
begin
    
    if ( exists (select 1 from worker
                 where worker_id = workerId
                )
       ) then
        select workout_name as 'Workout', workout_date as 'Date', workout_max_reps as "Max Num of Reps"
        from workout natural join workout_name
        where worker_id = workerId;
    
    else
        signal sqlstate '45001'
            set message_text = 'Invalid input.';
    end if;
end$$
delimiter ;

-- A sample call to this procedure
call showUserWorkouts(1);
