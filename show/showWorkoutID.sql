drop procedure if exists showWorkoutID;

delimiter $$
create procedure showWorkoutID(workoutName varchar(40))
begin
    
    if ( exists (select 1 from workout_name
                 where workout_name = workoutName
                )
       ) then
        select workout_name_id as 'ID'
        from workout_name
        where workout_name = workoutName;
    
    else
        signal sqlstate '45001'
            set message_text = 'Invalid input.';
    end if;
end$$
delimiter ;

-- A sample call to this procedure
call showWorkoutID('Workout');
