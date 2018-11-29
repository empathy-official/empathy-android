package com.empathy.empathy_android.repository.model


//{
//    "contents": "string",
//    "creationTime": "string",
//    "imageUrl": "string",
//    "journeyId": 0,
//    "location": "string",
//    "ownerProfileUrl": "string",
//    "title": "string"
//}

internal data class FeedDetail(
        val contents: String,
        val creationTime: String,
        val imageUrl: String,
        val journeyId: Int,
        val location: String,
        val ownerProfileUrl: String,
        val title: String
)