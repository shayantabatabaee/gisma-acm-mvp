use("acm_gisma")

// Aggregation Query 1: Total number of competitions with user contributions
print("1. Total number of competitions with contributions:");
db.user_codes.aggregate([
    {$group: {_id: "$competitionId"}},
    {$count: "totalCompetitions"}
]).forEach(printjson);

// Aggregation Query 2: User IDs who contributed in competitions
print("\n2. User IDs who contributed in competitions:");
db.user_codes.aggregate([
    {$group: {_id: "$userId"}},
    {$sort: {_id: 1}},
    {$project: {userId: "$_id", _id: 0}}
]).forEach(printjson);

// Aggregation Query 3: Winner (lowest cpuTime) for each competition
print("\n3. Winner (lowest cpuTime) for each competition:");
db.user_codes.aggregate([
    {$sort: {"cpuTime": 1}},
    {
        $group: {
            _id: "$competitionId",
            winnerUserId: {$first: "$userId"},
            winnerCpuTime: {$min: "$cpuTime"}
        }
    },
    {$sort: {_id: 1}},
    {
        $project: {
            competitionId: "$_id",
            winnerUserId: 1,
            winnerCpuTime: 1,
            _id: 0
        }
    }
]).forEach(printjson);

// Aggregation Query 4: User IDs contributed to each competition
print("\n4. User IDs contributed to each competition:");
db.user_codes.aggregate([
    {
        $group: {
            _id: "$competitionId",
            userIds: {$addToSet: "$userId"}
        }
    },
    {$sort: {_id: 1}},
    {
        $project: {
            competitionId: "$_id",
            userIds: 1,
            _id: 0
        }
    }
]).forEach(printjson);