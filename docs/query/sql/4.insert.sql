INSERT INTO users (user_role, username, password, email, registration_date)
VALUES
('ADMIN', 'admin', '$2a$10$shjpHwRS84drlMP40Xg.BeJ7mDfSoUEChEbj8vopO/2GIRBS2cRma', 'admin@gisma.com', 1741597887739),
('STANDARD', 'shayantabatabaee', '$2a$10$iC1ufdBsjzCKaF8YrIRIwuqPpaY67XpXH1k.51UuW9M3QGtWTw/Ja', 'shayan.ta69@gmail.com', 1741597922597),
('STANDARD', 'pouya', '$2a$10$iC1ufdBsjzCKaF8YrIRIwuqPpaY67XpXH1k.51UuW9M3QGtWTw/Ja', 'pouya@gmail.com', 1741597992597);

INSERT INTO competition (name, level, start_time, duration, description)
VALUES
('Two Sum', 'EASY', 1741597922597, 604800000, 'Given an array of integers `nums` and an integer `target`, return indices of the two numbers such that they add up to `target`');

INSERT INTO template (competition_id, class_name, method_name)
VALUES
(1, 'Solution', 'twoSum');

INSERT INTO test_case (test_case_id, competition_id, value, argument_type)
VALUES
(0, 1, '[2,7,11,15]', 'INPUT'),
(0, 1, '9', 'INPUT'),
(0, 1, '[0,1]', 'OUTPUT'),
(1, 1, '[3,2,4]', 'INPUT'),
(1, 1, '6', 'INPUT'),
(1, 1, '[1,2]', 'OUTPUT'),
(2, 1, '[3,3]', 'INPUT'),
(2, 1, '6', 'INPUT'),
(2, 1, '[0,1]', 'OUTPUT');

INSERT INTO leader_board (competition_id, user_id, submission_time, success_test_cases_count, failure_test_cases_count, success, cpu_time)
VALUES
(1, 2, 1741599490214, 3, 0, true, 0.003752),
(1, 3, 1741599690325, 3, 0, true, 0.0047298);


