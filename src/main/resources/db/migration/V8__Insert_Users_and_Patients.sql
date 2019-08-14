insert into users (login, password, name, role)
values ('admin', 'admin', 'Admin', 'ADMIN'),
       ('doctor', 'doctor', 'Gregory House', 'DOCTOR'),
       ('cox', 'cox', 'Perry Cox', 'DOCTOR');

insert into patients (name, diagnosis, insurancenumber, doctorname, patientstatus)
values ('Ivan Petrov', 'Aortic aneurysm', '123-4567-890', 'Gregory House', 'ON_TREATMENT'),
       ('Alexey Ivanov', 'Conjunctivitis', '555-5555-555', 'Gregory House', 'DISCHARGED'),
       ('Maxim Semenov', 'Retropharyngeal absces', '333-5555-999', 'Perry Cox', 'ON_TREATMENT'),
       ('Petr Sidorov', 'Meningeal carcinomatosis', '111-3333-112', 'Perry Cox', 'DISCHARGED');