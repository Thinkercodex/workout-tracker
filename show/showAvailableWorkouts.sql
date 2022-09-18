drop procedure if exists showAvailableWorkouts;

delimiter $$
create procedure showAvailableWorkouts()
begin
    select workout_name as 'Available Workouts'
    from workout_name;
end$$
delimiter ;

-- A sample call to this procedure
call showAvailableWorkouts();
