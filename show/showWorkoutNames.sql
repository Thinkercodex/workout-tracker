drop procedure if exists showWorkoutNames;

delimiter $$
create procedure showWorkoutNames()
begin
    select workout_name as 'Workout Name'
    from workout_name;
end$$
delimiter ;

-- A sample call to this procedure
call showWorkoutNames();
