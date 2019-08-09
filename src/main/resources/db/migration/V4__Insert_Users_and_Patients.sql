insert into users (login, password, name, role)
values ('admin', 'admin', 'Admin', 'ADMIN'),
       ('doctor', 'doctor', 'Gregory House', 'DOCTOR'),
       ('cox', 'cox', 'Perry Cox', 'DOCTOR');

insert into patients
values (default, 'Ivan Petrov', 'Aortic aneurysm', '123-4567-890', 'Gregory House', 'ON_TREATMENT'),
       (default, 'Alexey Ivanov', 'Conjunctivitis', '555-5555-555', 'Gregory House', 'DISCHARGED'),
       (default, 'Maxim Semenov', 'Retropharyngeal absces', '333-5555-999', 'Perry Cox', 'ON_TREATMENT'),
       (default, 'Petr Sidorov', 'Meningeal carcinomatosis', '111-3333-112', 'Perry Cox', 'DISCHARGED');