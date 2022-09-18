--ADD
insert into PROCEDURE_LIST values(1, 'addRep(<WorkoutId>)');
insert into PROCEDURE_LIST values(2, 'addWorker(<WorkerName>)');
insert into PROCEDURE_LIST values(3, 'addWorkout(<WorkerId>, <WorkoutNameId>, <WorkoutMaxRepNum>)');
insert into PROCEDURE_LIST values(4, 'addWorkoutName(<WorkoutName>)');

--DEL
insert into PROCEDURE_LIST values(5, 'delRep(<WorkoutId>)');

--SHOW
insert into PROCEDURE_LIST values(6, 'showAllUsers()');
insert into PROCEDURE_LIST values(7, 'showWorkouts()');
insert into PROCEDURE_LIST values(8, 'showUserWorkouts(<WorkerId>)');
insert into PROCEDURE_LIST values(9, 'showWorkoutReps(<WorkoutId>)');
insert into PROCEDURE_LIST values(10, 'showAvailableWorkouts()');
insert into PROCEDURE_LIST values(11, 'showUserID(<WorkerName>)');
insert into PROCEDURE_LIST values(12, 'showNumUser()');
insert into PROCEDURE_LIST values(13, 'showWorkoutID(<WorkoutName>)');
insert into PROCEDURE_LIST values(14, 'showWorkoutNames(<WorkoutName>)');
