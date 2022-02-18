
CREATE TABLE public.students
(
    student_id SERIAL PRIMARY KEY ,
    group_id integer CONSTRAINT st_groups_fk REFERENCES public.groups(group_id),
    first_name varchar(100) NOT NULL ,
    last_name varchar(100) NOT NULL
)