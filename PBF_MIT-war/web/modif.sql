-- Column: regional
-- ALTER TABLE public.service DROP COLUMN regional;

ALTER TABLE public.service ADD COLUMN regional boolean;
ALTER TABLE public.service ALTER COLUMN regional SET DEFAULT false;
UPDATE public.service SET regional = false;


-- Column: observationutilisateur

-- ALTER TABLE public.programmation DROP COLUMN observationutilisateur;

ALTER TABLE public.programmation ADD COLUMN observationutilisateur text;
ALTER TABLE public.programmation ALTER COLUMN observationutilisateur SET DEFAULT '-'::text;
UPDATE public.programmation SET observationutilisateur = '-';
