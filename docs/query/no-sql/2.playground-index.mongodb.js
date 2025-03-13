use("acm_gisma")

db.user_codes.createIndex({"userId": 1}, {"name": "idx_user_id"})

db.user_codes.getIndexes()