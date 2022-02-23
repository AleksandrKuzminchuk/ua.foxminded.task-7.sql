CREATE TABLE public.students
(
    student_id SERIAL PRIMARY KEY,
    group_id   integer,
    first_name varchar(100) NOT NULL,
    last_name  varchar(100) NOT NULL
--     FOREIGN KEY (group_id) REFERENCES public.groups (group_id) ON DELETE CASCADE
)