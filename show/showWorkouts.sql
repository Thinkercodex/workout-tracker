drop procedure if exists showWorkouts;

delimiter $$
create procedure showWorkouts()
begin
    select workout_name as 'Workout', worker_name as 'User', count(*) as 'Number of Repetitions', (workout_max_reps - count(*)) as 'Remaining Number of Reps', workout_date as 'Date'
    from workout natural join workout_name natural join worker natural join rep
    group by workout_id;
end$$
delimiter ;

-- A sample call to this procedure
call showWorkouts();
