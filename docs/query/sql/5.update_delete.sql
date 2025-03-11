-- Update user password
UPDATE users
SET password = "$2a$10$iC1ufdBsjzCKaF8YrIRIwuqPpaY67XpXH1k.51UuW9M3QGtWTw/Ja"
WHERE user_id=1;

-- Update leader_board
UPDATE leader_board
SET submission_time=1741597922597, cpu_time=0.0004, success=false, success_test_cases_count=2, failure_test_cases_count=1
WHERE user_id=2 AND competition_id=1;

-- Delete Competition
DELETE FROM competition
WHERE competition_id=1;

