
CREATE TABLE public.students_courses
(
    course_id integer NOT NULL CONSTRAINT courses_fk REFERENCES public.courses(courses_id),
    student_id integer NOT NULL  CONSTRAINT student_fk REFERENCES public.students(student_id),

    CONSTRAINT courses_students_pkey PRIMARY KEY (course_id, student_id)
);