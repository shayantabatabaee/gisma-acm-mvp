CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    user_role VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    registration_date BIGINT NOT NULL,
    CONSTRAINT unique_user_username UNIQUE (username),
    CONSTRAINT unique_user_email UNIQUE (email)
);

CREATE TABLE competition (
    competition_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    level VARCHAR(255) NOT NULL,
    start_time BIGINT NOT NULL,
    duration BIGINT NOT NULL,
    description LONGTEXT NOT NULL,
    CONSTRAINT unique_competition_name UNIQUE (name)
);

CREATE TABLE template (
    template_id INT AUTO_INCREMENT PRIMARY KEY,
    competition_id INT NOT NULL,
    class_name VARCHAR(255) NOT NULL,
    method_name VARCHAR(255) NOT NULL,
    CONSTRAINT unique_competition_id UNIQUE (competition_id),
    FOREIGN KEY (competition_id) REFERENCES competition(competition_id) ON DELETE CASCADE
);

CREATE TABLE test_case (
    id INT AUTO_INCREMENT PRIMARY KEY,
    test_case_id INT NOT NULL,
    competition_id INT NOT NULL,
    value VARCHAR(255) NOT NULL,
    argument_type VARCHAR(255) NOT NULL,
    FOREIGN KEY (competition_id) REFERENCES competition(competition_id) ON DELETE CASCADE
);

CREATE TABLE leader_board (
    leader_board_id INT AUTO_INCREMENT PRIMARY KEY,
    competition_id INT NOT NULL,
    user_id INT NOT NULL,
    submission_time BIGINT NOT NULL,
    success_test_cases_count INT NOT NULL,
    failure_test_cases_count INT NOT NULL,
    success BOOLEAN NOT NULL,
    cpu_time DOUBLE NOT NULL,
    CONSTRAINT uc_leaderboard_competition_id_user_id UNIQUE (competition_id, user_id),
    FOREIGN KEY (competition_id) REFERENCES competition(competition_id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);