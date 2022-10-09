package com.mazurenka.vivaldi.data.model

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class AudioModel(
    @Json(name = "id") val serverId: String,
    @Json(name = "title") val title: String,
    @Json(name = "author_id") val authorId: Int,
    @Json(name = "author_name") val authorName: String,
    @Json(name = "album_id") val albumId: Int,
    @Json(name = "album_name") val albumName: String,
    @Json(name = "album_cover_uri") val albumCoverUri: String,
    @Json(name = "length") val length: Int,
    @Json(name = "image_uri") val imageUri: String,
    @Json(name = "uri") val audioUri: String
) : Parcelable

