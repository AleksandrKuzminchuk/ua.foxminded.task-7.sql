CREATE TABLE public.courses
(
    course_id          SERIAL PRIMARY KEY,
    course_name        varchar(100) NOT NULL,
    course_description varchar(100) NOT NULL
);