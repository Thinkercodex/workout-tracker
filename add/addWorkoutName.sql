drop procedure if exists addWorkoutName;

delimiter $$
create procedure addWorkoutName(workoutName varchar(40))
begin
    declare workoutNameId int;
    select count(*) into workoutNameId from workout_name;
    
    set workoutNameId = workoutNameId + 1;
    
    insert into workout_name values(workoutNameId, workoutName);
end$$
delimiter ;

-- A sample call to this procedure
call addWorkoutName('A');
