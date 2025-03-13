use("acm_gisma")

db.user_codes.insertMany([
    {
        "userId": 2,
        "competitionId": 1,
        "submissionTime": 1741433397540,
        "cpuTime": 0.004425916,
        "code": "import java.util.HashMap;import java.util.Map;public class Solution {public Integer[] twoSum(Integer[] nums, Integer target) {Map<Integer, Integer> numMap = new HashMap<>();for (int i = 0; i < nums.length; i++) {int complement = target - nums[i];if (numMap.containsKey(complement)) {return new Integer[] { numMap.get(complement), i };}numMap.put(nums[i], i);}throw new IllegalArgumentException(\"No two sum solution\");}}",

    },
    {
        "userId": 2,
        "competitionId": 1,
        "submissionTime": 1741442166523,
        "cpuTime": 0.00321,
        "code": "SAMPLE_CODE",

    },
    {
        "userId": 2,
        "competitionId": 2,
        "submissionTime": 1742314645689,
        "cpuTime": 0.02642,
        "code": "SAMPLE_CODE",

    },
    {
        "userId": 3,
        "competitionId": 1,
        "submissionTime": 1721333457740,
        "cpuTime": 1.003,
        "code": "SAMPLE_CODE",

    },
    {
        "userId": 4,
        "competitionId": 1,
        "submissionTime": 1741433397540,
        "cpuTime": 0.00612,
        "code": "SAMPLE_CODE",

    },
    {
        "userId": 4,
        "competitionId": 3,
        "submissionTime": 1756721397899,
        "cpuTime": 2.003,
        "code": "SAMPLE_CODE",

    },
    {
        "userId": 5,
        "competitionId": 1,
        "submissionTime": 1756912494510,
        "cpuTime": 4.001,
        "code": "SAMPLE_CODE",

    }
])