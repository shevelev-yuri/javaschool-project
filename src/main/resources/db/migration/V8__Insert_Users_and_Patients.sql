insert into users (login, password, name, role)
values ('admin', 'admin', 'Admin', 'ADMIN'),
       ('doctor', 'doctor', 'Gregory House', 'DOCTOR'),
       ('cox', 'cox', 'Perry Cox', 'DOCTOR');

insert into patients (name, diagnosis, insurancenumber, doctorname, patientstatus)
values ('Ivan Petrov', 'Aortic aneurysm', '123-4567-890', 'Gregory House', 'ON_TREATMENT');