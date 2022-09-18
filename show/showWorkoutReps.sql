drop procedure if exists showWorkoutReps;

delimiter $$
create procedure showWorkoutReps(workoutId int)
begin
    
    if ( exists (select 1 from workout
                 where workout_id = workoutId
                )
       ) then
        select count(*) as 'Number of Repetitions', (workout_max_reps - count(*)) as 'Remaining Number of Reps'
        from rep natural join workout
        where workout_id = workoutId
        group by workout_id;
    
    else
        signal sqlstate '45001'
            set message_text = 'Invalid input.';
    end if;
end$$
delimiter ;

-- A sample call to this procedure
call showWorkoutReps(1);
