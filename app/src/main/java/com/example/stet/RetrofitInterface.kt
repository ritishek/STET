package com.example.stet


import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part


interface RetrofitInterface {

    @POST("/login")
    fun executeLogin(@Body map: HashMap<String?, String?>?): Call<Void?>?

    @POST("/signup")
    fun executeSignup(@Body map: HashMap<String, String>): Call<Void?>?

    @POST("/details")
    fun executeDetail(@Body map: HashMap<String, String>): Call<Void?>?

    @POST("/education")
    fun executeEducation(@Body map: HashMap<String, String>): Call<Void?>?

    @POST("/preference")
    fun executePreference(@Body map: HashMap<String, String>): Call<Void?>?

    @Multipart
    @POST("/upload")
    fun postImage(@Part image: MultipartBody.Part?, @Part("upload") name: RequestBody?): Call<ResponseBody?>?

    @POST("/phone")
    fun getDetails(@Body map: HashMap<String?, String?>?): Call<Important?>?

    @POST("/isPhoneAlreadyExists")
    fun checkPhone(@Body map: HashMap<String?, String?>?): Call<Void?>?

    @POST("/isEmailAlreadyExists")
    fun checkEmail(@Body map: HashMap<String?, String?>?): Call<Void?>?

    @POST("/isAadharAlreadyExists")
    fun checkAadhar(@Body map: HashMap<String?, String?>?): Call<Void?>?

    @POST("/check")
    fun check(@Body map: HashMap<String?, String?>?): Call<Void?>?

    @POST("/getpersonal")
    fun getPersonal(@Body map: HashMap<String?, String?>?): Call<Personal?>?

    @POST("/getpreferences")
    fun getPreferences(@Body map: HashMap<String?, String?>?): Call<PreferenceClass?>?

    @POST("/getEducation")
    fun getEducation(@Body map: HashMap<String?, String?>?): Call<Education?>?

    @POST("/updatedetails")
    fun updateDetail(@Body map: HashMap<String, String>): Call<Void?>?

    @POST("/timing")
    fun timings(@Body map: HashMap<String?, String?>?): Call<Void?>?


}