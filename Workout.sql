create table worker
(    worker_id      int unsigned not null primary key,
     worker_name    varchar(20) not null
);

create table workout_name
(    workout_name_id    int unsigned not null primary key,
     workout_name       varChar(40) not null
);

create table workout
(    workout_id          int unsigned not null,
     worker_id           int unsigned not null,
     workout_name_id     int unsigned not null,
     workout_max_reps    int unsigned not null,
     workout_date        date not null,
     primary key(workout_id, worker_id),
     foreign key (worker_id)
        references worker (worker_id),
     foreign key (workout_name_id)
        references workout_name (workout_name_id)
);

create table rep
(    rep_id        int unsigned not null primary key,
     workout_id    int unsigned not null,
     foreign key (workout_id)
        references workout (workout_id)
);

create table PROCEDURE_LIST
(    procedure_id      int unsigned not null primary key,
     procedure_name    varchar(80)
);
