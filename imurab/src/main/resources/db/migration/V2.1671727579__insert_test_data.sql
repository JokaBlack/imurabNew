INSERT INTO avp (name, season_end_at, season_start_at) VALUES ('Тамга', '2023-07-31', '2023-02-23');
INSERT INTO avp (name, season_end_at, season_start_at) VALUES ('Тон', '2023-07-31', '2023-02-24');
INSERT INTO avp (name, season_end_at, season_start_at) VALUES ('Торт-Кул', '2023-07-31', '2023-02-25');


INSERT INTO users (first_name, last_name, patronymic, email, phone, avp_id, password, role, locked, enabled) VALUES ('Imurab', 'Imurab', 'Imurab', 'imurab@imurab.com', '+996 999 999 999', 1, '$2y$10$jgwK8xx8pSdcmIBP4Zfrhu36NuiQyiXSici2wlWVi/WSAgDJV73G2', 'ROLE_ADMIN', false, true);

INSERT INTO discussion_topics(title, description, status, user_id)
VALUES ('Посев',
        'Посе́вы — засеянная площадь — то, что посеяно. Различают посевы: по сельскохозяйственной культуре (например, посевы пшеницы, посевы овса и так далее), по количеству культур, высеянных на одной площади: монокультура или смешанные (уплотнённые) посевы.',
        'OPENED', 1);

INSERT INTO departments(name, avp_id)
VALUES ('Участок 1', 1);
INSERT INTO departments(name, avp_id)
VALUES ('Участок 2', 3);
INSERT INTO departments(name, avp_id)
VALUES ('Участок 3', 2);


INSERT INTO field_crops (name, coefficient, img_link)
VALUES ('Зерно', 1, 'wheat.jpg');

INSERT INTO field_crops (name, coefficient, img_link)
VALUES ('Кукуруза', 1.1, 'corn.jpg');

INSERT INTO field_crops (name, coefficient, img_link)
VALUES ('Картофель', 1.2, 'potato.jpg');

