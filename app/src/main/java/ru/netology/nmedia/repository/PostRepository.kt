package ru.netology.nmedia.repository

import kotlinx.coroutines.flow.Flow
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.dto.Media
import ru.netology.nmedia.dto.MediaUpload

interface PostRepository {
    val data: Flow<List<Post>>
    suspend fun getAll()
    suspend fun showNewPosts()
    fun getNewerCount(id: Long): Flow<Int>
    suspend fun save(post: Post, upload: MediaUpload?)
    //suspend fun saveWithAttachment(post: Post, upload: MediaUpload)
    suspend fun removeById(id: Long)
    suspend fun likeById(id: Long)
    suspend fun unLikeById(id: Long)
    suspend fun upload(upload: MediaUpload): Media
}


