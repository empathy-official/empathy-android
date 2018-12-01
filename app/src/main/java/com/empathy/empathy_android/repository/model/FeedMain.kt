package com.empathy.empathy_android.repository.model

//{
//    "enumStr": "string",
//    "imageURL": "string",
//    "isFirst": "string",
//    "mainText": "string",
//    "otherPeopleList": [
//    {
//        "imageUrl": "string",
//        "journeyId": 0,
//        "ownerName": "string",
//        "ownerProfileUrl": "string"
//    }
//    ],
//    "weekday": "string"
//}
internal data class FeedMain(
        val enumStr: String,
        val imageURL: String,
        val isFirst: String,
        val mainText: String,
        val otherPeopleList: MutableList<Feed>,
        val weekday: String
)