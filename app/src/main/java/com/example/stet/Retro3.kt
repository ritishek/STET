package com.example.stet


import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part


interface Retro3 {
    @Multipart
    @POST("/upload")
    fun postImage(@Part image: MultipartBody.Part?, @Part("upload") name: RequestBody?): Call<ResponseBody?>?

    @Multipart
    @POST("/uploadmultiple")
    fun postMultipleImage(@Part image1: MultipartBody.Part, @Part image2: MultipartBody.Part): Call<ResponseBody?>?


    /*@Multipart
    @POST("/uploadmultiple")
    fun submitNew(
        @Part files: Array<MultipartBody.Part?>?
    ): Call<Void?>?*/
}