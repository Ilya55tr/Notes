--liquibase formatted sql

--changeset developer:1-insert-test-notes
INSERT INTO note (id, name) VALUES
                                (NEXTVAL('note_seq'), 'Рабочие задачи'),
                                (NEXTVAL('note_seq'), 'Личные дела'),
                                (NEXTVAL('note_seq'), 'Проект Spring Boot'),
                                (NEXTVAL('note_seq'), 'Изучение технологий'),
                                (NEXTVAL('note_seq'), 'Покупки');

--changeset developer:2-insert-test-tasks
INSERT INTO task (id, task_name, text, note_id) VALUES
                                                    (NEXTVAL('task_seq'), 'Код ревью', 'Проверить pull request от коллеги по новой функциональности', 1),
                                                    (NEXTVAL('task_seq'), 'Встреча с командой', 'Daily standup в 10:00, обсудить прогресс по спринту', 1),
                                                    (NEXTVAL('task_seq'), 'Документация API', 'Обновить документацию для новых эндпоинтов REST API', 1),
                                                    (NEXTVAL('task_seq'), 'Запись к врачу', 'Записаться на плановый осмотр к терапевту на следующую неделю', 2),
                                                    (NEXTVAL('task_seq'), 'Оплатить счета', 'Оплатить коммунальные услуги и интернет до 25 числа', 2),
                                                    (NEXTVAL('task_seq'), 'Настройка Liquibase', 'Создать changelogs для инициализации базы данных и миграций', 3),
                                                    (NEXTVAL('task_seq'), 'Создание REST контроллеров', 'Реализовать CRUD операции для сущностей Note и Task', 3),
                                                    (NEXTVAL('task_seq'), 'Написать тесты', 'Покрыть unit тестами сервисы и контроллеры', 3),
                                                    (NEXTVAL('task_seq'), 'Настройка валидации', 'Добавить валидацию входящих данных через Bean Validation', 3),
                                                    (NEXTVAL('task_seq'), 'Изучить Docker', 'Пройти онлайн курс по контейнеризации приложений', 4),
                                                    (NEXTVAL('task_seq'), 'Kubernetes основы', 'Разобраться с основными концепциями оркестрации контейнеров', 4),
                                                    (NEXTVAL('task_seq'), 'Spring Security', 'Изучить аутентификацию и авторизацию в Spring приложениях', 4),
                                                    (NEXTVAL('task_seq'), 'Продукты', 'Купить молоко, хлеб, яйца, овощи на неделю', 5),
                                                    (NEXTVAL('task_seq'), 'Техника', 'Выбрать и купить новый SSD диск для ноутбука', 5),
                                                    (NEXTVAL('task_seq'), 'Подарок', 'Купить подарок на день рождения другу', 5);