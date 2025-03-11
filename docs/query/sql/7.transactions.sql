-- Transaction for creating competition
START TRANSACTION;

INSERT INTO competition (name, level, start_time, duration, description)
VALUES('Two Sum 2', 'EASY', 1741597922597, 604800000, 'Given an array of integers `nums` and an integer `target`, return indices of the two numbers such that they add up to `target`');

INSERT INTO template (competition_id, class_name, method_name)
VALUES (LAST_INSERT_ID(),'Solution','twoSum');

INSERT INTO test_case (test_case_id, competition_id, value, argument_type)
VALUES 
    (0, LAST_INSERT_ID(), '[2,7,11,15]', 'INPUT'),
    (0, LAST_INSERT_ID(), '9', 'INPUT'),
    (0, LAST_INSERT_ID(), '[0,1]', 'OUTPUT'),
    (1, LAST_INSERT_ID(), '[3,2,4]', 'INPUT'),
    (1, LAST_INSERT_ID(), '6', 'INPUT'),
    (1, LAST_INSERT_ID(), '[1,2]', 'OUTPUT'),
    (2, LAST_INSERT_ID(), '[3,3]', 'INPUT'),
    (2, LAST_INSERT_ID(), '6', 'INPUT'),
    (2, LAST_INSERT_ID(), '[0,1]', 'OUTPUT');

COMMIT;
-- End

-- Transaction for createOrUpdate LeaderBoard
START TRANSACTION;

INSERT INTO leader_board (competition_id, user_id, submission_time, success_test_cases_count, failure_test_cases_count, success, cpu_time)
VALUES (1, 2, 1741539490214, 2, 1, true, 0.001752)
ON DUPLICATE KEY UPDATE
    submission_time = VALUES(submission_time),
    success_test_cases_count = VALUES(success_test_cases_count),
    failure_test_cases_count = VALUES(failure_test_cases_count),
    success = VALUES(success),
    cpu_time = VALUES(cpu_time);
    
COMMIT;


