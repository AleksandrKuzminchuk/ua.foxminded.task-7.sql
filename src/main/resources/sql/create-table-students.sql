CREATE TABLE public.students
(
    student_id SERIAL PRIMARY KEY,
    group_id   integer,
    first_name varchar(100) NOT NULL,
    last_name  varchar(100) NOT NULL
)