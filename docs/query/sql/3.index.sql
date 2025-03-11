-- test_case
CREATE INDEX idx_testcase_competition_id ON test_case (competition_id);

-- leader_board
CREATE INDEX idx_leaderboard_competition_id ON leader_board (competition_id);
CREATE INDEX idx_leaderboard_user_id ON leader_board (user_id);