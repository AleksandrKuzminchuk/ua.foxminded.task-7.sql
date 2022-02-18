ALTER TABLE public.students DROP CONSTRAINT IF EXISTS st_groups_fk;

DROP TABLE IF EXISTS public.students;

DROP TABLE IF EXISTS public.groups;

DROP TABLE IF EXISTS public.courses;

DROP TABLE IF EXISTS public.students_courses;