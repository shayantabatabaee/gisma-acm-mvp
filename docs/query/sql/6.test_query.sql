-- users table
SELECT * FROM users u WHERE u.username = 'admin';
SELECT (count(*) > 0) AS user_exists FROM users WHERE username = 'shayantabatabaee';

-- competition table
SELECT * FROM competition WHERE competition_id=1;
SELECT * FROM competition WHERE name='Two Sum';
SELECT 
    c.competition_id,
    c.name,
    c.level,
    c.start_time,
    c.duration,
    c.description,
    t.template_id,
    t.class_name,
    t.method_name,
    COUNT(DISTINCT tc.test_case_id) AS test_case_count
FROM 
    competition c
LEFT JOIN 
    template t ON c.competition_id = t.competition_id
LEFT JOIN 
    test_case tc ON c.competition_id = tc.competition_id
GROUP BY 
    c.competition_id,
    c.name,
    c.level,
    c.start_time,
    c.duration,
    c.description,
    t.template_id,
    t.class_name,
    t.method_name;

-- template table
SELECT * FROM template WHERE competition_id=1;

-- test_case table
SELECT * FROM test_case WHERE competition_id=1;
SELECT COUNT(DISTINCT test_case_id) as test_case_count FROM test_case WHERE competition_id=1;
SELECT competition_id, 
	SUM(CASE WHEN argument_type = 'INPUT' THEN 1 ELSE 0 END) AS input_count,
    SUM(CASE WHEN argument_type = 'OUTPUT' THEN 1 ELSE 0 END) AS output_count
FROM 
    test_case
WHERE 
    test_case_id = (
        SELECT MIN(test_case_id) 
        FROM test_case t2 
        WHERE t2.competition_id = test_case.competition_id
    )
GROUP BY 
    competition_id;
    
-- leader_board tabel
SELECT * FROM leader_board;
SELECT COUNT(DISTINCT user_id) as total_competitors FROM leader_board;
SELECT competition_id, user_id as winner_user_id, cpu_time FROM leader_board lb1 WHERE 
    cpu_time = (
        SELECT MIN(cpu_time)
        FROM leader_board lb2
        WHERE lb2.competition_id = lb1.competition_id
    );


