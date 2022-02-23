
CREATE TABLE public.students_courses
(
    course_id integer NOT NULL,
    student_id integer NOT NULL

--     FOREIGN KEY (course_id) REFERENCES public.courses (course_id) ON DELETE CASCADE ,
--     FOREIGN KEY (student_id) REFERENCES public.students(student_id) ON DELETE CASCADE
);