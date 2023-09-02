alter table public.student add constraint age_constraint check (age > 16);
alter table public.student alter column name set not null;
alter table public.student add constraint name_unique unique (name);
alter table public.faculty add constraint name_color_unique unique (name, color);
alter table public.student alter column age set default 20;