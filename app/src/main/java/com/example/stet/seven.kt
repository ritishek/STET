package com.example.stet

import android.Manifest
import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.stet.R
import kotlinx.android.synthetic.main.page_7.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.*


class seven : AppCompatActivity() {
    private val PICK_IMAGE_REQUEST = 1
    private val CLICK_PHOTO = 2
    private var bit: Bitmap? = null
    private var filePath: Uri? = null
    private var URL: String = "http://192.168.43.114:3000"
    private var bit1: Bitmap? = null
    private var bit2: Bitmap? = null
    var s: Int = 0
    var t: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.page_7)
        page_7_progress_bar.progress = 90
        val phone: String = intent.getStringExtra("phone")
        page_7_next.setOnClickListener {
            if (page_7_checkbox.isChecked) {

                val i = Intent(this, Register::class.java)
                i.putExtra("phone", phone)
                startActivity(i)
            } else {
                Toast.makeText(this, "Please Accept the T&C for Registration", Toast.LENGTH_SHORT)
                    .show()
            }

        }
        page_7_back.setOnClickListener {
            val i = Intent(this, Register::class.java)
            i.putExtra("phone", phone)
            startActivity(i)


        }



        page_7_select_aadhar.setOnClickListener {
            t = 1
            page_7_aadhar_upload.visibility = View.VISIBLE
            storage()

        }
        page_7_select_10th.setOnClickListener {
            t = 2
            page_7_10th_upload.visibility = View.VISIBLE
            storage()

        }
        page_7_select_12th.setOnClickListener {
            t = 3
            page_7_12th_upload.visibility = View.VISIBLE
            storage()

        }
        page_7_select_birth.setOnClickListener {
            t = 4
            page_7_birth_upload.visibility = View.VISIBLE
            storage()

        }
        page_7_select_community.setOnClickListener {
            t = 5
            page_7_community_upload.visibility = View.VISIBLE
            storage()

        }
        page_7_select_graduationC.setOnClickListener {
            t = 6
            page_7_graduationC_uplaod.visibility = View.VISIBLE
            storage()

        }
        page_7_select_graduationM.setOnClickListener {
            t = 7
            page_7_GraduationM_upload.visibility = View.VISIBLE
            storage()

        }
        page_7_select_photo.setOnClickListener {
            t = 8
            page_7_photo_upload.visibility = View.VISIBLE
            storage()

        }
        page_7_select_signature.setOnClickListener {
            t = 9
            page_7_signature_upload.visibility = View.VISIBLE
            storage()

        }
        page_7_select_subject.setOnClickListener {
            t = 10
            page_7_subject_upload.visibility = View.VISIBLE
            storage()

        }
        page_7_aadhar_cam.setOnClickListener {
            t = 1
            page_7_aadhar_upload.visibility = View.VISIBLE
            camera()

        }
        page_7_10th_cam.setOnClickListener {
            t = 2
            page_7_10th_upload.visibility = View.VISIBLE
            camera()

        }
        page_7_12th_cam.setOnClickListener {
            t = 3
            page_7_12th_upload.visibility = View.VISIBLE
            camera()

        }
        page_7_birth_cam.setOnClickListener {
            t = 4
            page_7_birth_upload.visibility = View.VISIBLE
            camera()

        }
        page_7_community_cam.setOnClickListener {
            t = 5
            page_7_community_upload.visibility = View.VISIBLE
            camera()

        }
        page_7_graduationC_cam.setOnClickListener {
            t = 6
            page_7_graduationC_uplaod.visibility = View.VISIBLE
            camera()

        }
        page_7_graduationM_cam.setOnClickListener {
            t = 7
            page_7_GraduationM_upload.visibility = View.VISIBLE
            camera()

        }
        page_7_photo_cam.setOnClickListener {
            t = 8
            page_7_photo_upload.visibility = View.VISIBLE
            camera()

        }
        page_7_signature_cam.setOnClickListener {
            t = 9
            page_7_signature_upload.visibility = View.VISIBLE
            camera()

        }
        page_7_subject_cam.setOnClickListener {
            t = 10
            page_7_subject_upload.visibility = View.VISIBLE
            camera()

        }

    }

    private fun camera() {
        val permission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.CAMERA
        )
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                10
            )
        } else {

            click()
        }

    }

    private fun storage() {
        val permission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                11
            )
        } else {

            showFileChooser()
        }


    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        if (requestCode == 11) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                showFileChooser()
            } else {
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG)
                    .show()

            }
        } else if (requestCode == 10) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                click()

            } else {
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG)
                    .show()

            }
        }
    }

    private fun showFileChooser() {
        val intent: Intent = intent
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(
            Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.INTERNAL_CONTENT_URI
            ),
            PICK_IMAGE_REQUEST
        )
    }

    private fun click() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            startActivityForResult(takePictureIntent, CLICK_PHOTO)
        }

    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            PICK_IMAGE_REQUEST -> {


                if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
                    filePath = data.data
                    try {

                        bit = MediaStore.Images.Media.getBitmap(contentResolver, filePath)
                        s = t
                        when (t) {
                            1 -> {
                                bit1 = bit
                            }
                            2 -> {
                                bit2 = bit
                            }
                            3 -> {

                            }
                            4 -> {

                            }
                            5 -> {

                            }
                            6 -> {

                            }
                            7 -> {

                            }
                            8 -> {

                            }
                            9 -> {

                            }
                            10 -> {

                            }

                        }
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }

            CLICK_PHOTO -> {

                if (requestCode == CLICK_PHOTO && resultCode == Activity.RESULT_OK) {
                    try {


                        val extras = data?.extras
                        val bitmap = extras?.get("data") as Bitmap
                        bit = bitmap
                        s = t
                        when (t) {
                            1 -> {
                                bit1 = bit
                            }
                            2 -> {
                                bit2 = bit
                            }
                            3 -> {

                            }
                            4 -> {

                            }
                            5 -> {

                            }
                            6 -> {

                            }
                            7 -> {

                            }
                            8 -> {

                            }
                            9 -> {

                            }
                            10 -> {

                            }

                        }

                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                }
            }
        }
        page_7_aadhar_upload.setOnClickListener {
            if (s == 1) {
                val retrofit: Retrofit = Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                var retrofitInterface2: Retro3? = retrofit.create(Retro3::class.java)
                multipartImageUpload(bit, retrofitInterface2, "aadhar", page_7_aadhar_upload)
            }
        }
        page_7_10th_upload.setOnClickListener {
            if (s == 2) {
                /*val retrofit: Retrofit = Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                var retrofitInterface2: Retro3? = retrofit.create(Retro3::class.java)
                multipartImageUpload(bit, retrofitInterface2, "tenth", page_7_10th_upload)*/
                val retrofit: Retrofit = Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                var retrofitInterface2: Retro3? = retrofit.create(Retro3::class.java)
                multipartMultipleImageUpload(
                    bit,
                    bit,
                    retrofitInterface2,
                    "aadhar",
                    "tenth",
                    page_7_10th_upload
                )

            }
        }
        page_7_12th_upload.setOnClickListener {
            if (s == 3) {
                val retrofit: Retrofit = Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                var retrofitInterface2: Retro3? = retrofit.create(Retro3::class.java)
                multipartImageUpload(bit, retrofitInterface2, "12th", page_7_12th_upload)
            }
        }
        page_7_birth_upload.setOnClickListener {
            if (s == 4) {
                val retrofit: Retrofit = Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                var retrofitInterface2: Retro3? = retrofit.create(Retro3::class.java)
                multipartImageUpload(bit, retrofitInterface2, "birth", page_7_birth_upload)
            }

        }
        page_7_community_upload.setOnClickListener {
            if (s == 5) {
                val retrofit: Retrofit = Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                var retrofitInterface2: Retro3? = retrofit.create(Retro3::class.java)
                multipartImageUpload(bit, retrofitInterface2, "community", page_7_community_upload)
            }
        }
        page_7_graduationC_uplaod.setOnClickListener {
            if (s == 6) {
                val retrofit: Retrofit = Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                var retrofitInterface2: Retro3? = retrofit.create(Retro3::class.java)
                multipartImageUpload(
                    bit,
                    retrofitInterface2,
                    "graduationcertificate",
                    page_7_graduationC_uplaod
                )
            }
        }
        page_7_GraduationM_upload.setOnClickListener {
            if (s == 7) {
                val retrofit: Retrofit = Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                var retrofitInterface2: Retro3? = retrofit.create(Retro3::class.java)
                multipartImageUpload(
                    bit,
                    retrofitInterface2,
                    "graduationmerit",
                    page_7_GraduationM_upload
                )
            }
        }
        page_7_photo_upload.setOnClickListener {
            if (s == 8) {
                val retrofit: Retrofit = Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                var retrofitInterface2: Retro3? = retrofit.create(Retro3::class.java)
                multipartImageUpload(bit, retrofitInterface2, "photo", page_7_photo_upload)
            }
        }
        page_7_signature_upload.setOnClickListener {
            if (s == 9) {
                val retrofit: Retrofit = Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                var retrofitInterface2: Retro3? = retrofit.create(Retro3::class.java)
                multipartImageUpload(bit, retrofitInterface2, "signature", page_7_signature_upload)
            }
        }
        page_7_subject_upload.setOnClickListener {
            if (s == 10) {
                val retrofit: Retrofit = Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                var retrofitInterface2: Retro3? = retrofit.create(Retro3::class.java)
                multipartImageUpload(bit, retrofitInterface2, "subject", page_7_subject_upload)
            }
        }


    }


    private fun multipartImageUpload(
        mBitmap: Bitmap?,
        retrofitInterface2: Retro3?,
        str: String,
        bt: Button
    ) {
        try {
            val progress2 = ProgressDialog(this)
            progress2.setMessage("Uploading $str.png  :) ")
            progress2.setProgressStyle(ProgressDialog.STYLE_SPINNER)
            progress2.isIndeterminate = true
            progress2.show()
            val filesDir = applicationContext.filesDir
            val file = File(filesDir, "$str.png")
            val bos = ByteArrayOutputStream()
            mBitmap?.compress(Bitmap.CompressFormat.PNG, 0, bos)
            val bitmapdata = bos.toByteArray()
            val fos = FileOutputStream(file)
            fos.write(bitmapdata)
            fos.flush()
            fos.close()
            val reqFile = RequestBody.create(MediaType.parse("image/*"), file)
            val body =
                MultipartBody.Part.createFormData("upload", file.name, reqFile)
            val name = RequestBody.create(MediaType.parse("text/plain"), "upload")
            val req: Call<ResponseBody?>? = retrofitInterface2?.postImage(body, name)
            req!!.enqueue(object : Callback<ResponseBody?> {
                override fun onResponse(
                    call: Call<ResponseBody?>?,
                    response: Response<ResponseBody?>
                ) {
                    if (response.code() == 200) {
                        Toast.makeText(
                            applicationContext,
                            "file uploaded",
                            Toast.LENGTH_SHORT
                        ).show()
                        bt.text = "UPLOADED"
                        bt.background = getDrawable(R.drawable.button_shape2)
                        progress2.dismiss()
                    }
                    progress2.dismiss()
                }

                override fun onFailure(call: Call<ResponseBody?>?, t: Throwable) {

                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT)
                        .show()
                    t.printStackTrace()
                    progress2.dismiss()
                }
            })
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }


    private fun multipartMultipleImageUpload(
        mBitmap1: Bitmap?,
        mBitmap2: Bitmap?,
        retrofitInterface2: Retro3?,
        str1: String,
        str2: String,
        bt: Button
    ) {
        try {
            val progress2 = ProgressDialog(this)
            progress2.setMessage("Uploading $str1.png  :) ")
            progress2.setProgressStyle(ProgressDialog.STYLE_SPINNER)
            progress2.isIndeterminate = true
            progress2.show()
            val filesDir = applicationContext.filesDir
            val file = File(filesDir, "$str1.png")
            val bos = ByteArrayOutputStream()
            mBitmap1?.compress(Bitmap.CompressFormat.PNG, 0, bos)
            val bitmapdata = bos.toByteArray()
            val fos = FileOutputStream(file)
            fos.write(bitmapdata)
            fos.flush()
            fos.close()
            val reqFile = RequestBody.create(MediaType.parse("image/*"), file)
            val body =
                MultipartBody.Part.createFormData("upload", file.name, reqFile)
            val filesDir2 = applicationContext.filesDir
            val file2 = File(filesDir2, "$str2.png")
            val bos2 = ByteArrayOutputStream()
            mBitmap2?.compress(Bitmap.CompressFormat.PNG, 0, bos2)
            val bitmapdata2 = bos2.toByteArray()
            val fos2 = FileOutputStream(file2)
            fos2.write(bitmapdata2)
            fos2.flush()
            fos2.close()
            val reqFile2 = RequestBody.create(MediaType.parse("image/*"), file2)
            val body2 =
                MultipartBody.Part.createFormData("upload", file2.name, reqFile2)
            val name = RequestBody.create(MediaType.parse("text/plain"), "upload")
            val req: Call<ResponseBody?>? = retrofitInterface2?.postMultipleImage(body, body2)
            req!!.enqueue(object : Callback<ResponseBody?> {
                override fun onResponse(
                    call: Call<ResponseBody?>?,
                    response: Response<ResponseBody?>
                ) {
                    if (response.code() == 200) {
                        Toast.makeText(
                            applicationContext,
                            "file uploaded",
                            Toast.LENGTH_SHORT
                        ).show()
                        bt.text = "UPLOADED"
                        bt.background = getDrawable(R.drawable.button_shape2)
                        progress2.dismiss()
                    }
                    progress2.dismiss()
                }

                override fun onFailure(call: Call<ResponseBody?>?, t: Throwable) {

                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT)
                        .show()
                    t.printStackTrace()
                    progress2.dismiss()
                }
            })
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    @NonNull
    private fun createPartFromString(descriptionString: String): RequestBody {
        return RequestBody.create(
            MultipartBody.FORM, descriptionString
        )
    }

    @NonNull
    private fun prepareFilePart(partName: String, fileUri: Uri): MultipartBody.Part {
        val file = File(fileUri.toString())
        val requestFile =
            RequestBody.create(
                MediaType.parse(contentResolver.getType(fileUri)),
                file
            )
        return MultipartBody.Part.createFormData(partName, file.name, requestFile)
    }


}
