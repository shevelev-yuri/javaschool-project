insert into users (login, password, name, role)
values ('admin', '$2a$07$li3R11QLBX2ov/Gq.gPm9e7WWqRBUe8US9lkz9qa90QFX7RTqpYxa', 'Admin', 'ADMIN'),
       ('doctor', '$2a$07$RuZ4r5mMEIV0AQiTVnNZc.ft8jrqqt0OTbNNRsbJns/Xs.S.Zi7gq', 'Doctor 1', 'DOCTOR'),
       ('doc', '$2a$07$mwItjnmDW9vs2qFDqWeDW.Tik3.PN.XWzxzGZzhQ7hufmtuv2mAcy', 'Doctor 2', 'DOCTOR'),
       ('nurse', '$2a$07$vJ4pjy57QvOfebdI7OmrbuRefDA3ey5KIWHq/pAHe2eQ2iAjzNkHq', 'Medical Nurse', 'NURSE');

insert into patients (name, diagnosis, insurancenumber, doctorname, patientstatus)
values ('Ivan Petrov', 'Aortic aneurysm', '123-4567-890', 'Doctor 1', 'ON_TREATMENT');