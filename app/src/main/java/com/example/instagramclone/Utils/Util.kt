package com.example.instagramclone.Utils

import android.app.ProgressDialog
import android.content.Context
import android.net.Uri
import com.google.firebase.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

fun uploadImage(uri: Uri, folderName: String ,callBack:(String)->Unit){
    var imageUrl: String? =null
    val addOnSuccessListener =
        FirebaseStorage.getInstance().getReference(folderName).child(UUID.randomUUID().toString())
            .putFile(uri)
            .addOnSuccessListener {
                it.storage.downloadUrl.addOnSuccessListener {
                    imageUrl = it.toString()
                    callBack(imageUrl!!)
                }
            }
}

fun uploadVideo(uri: Uri, folderName: String ,progressDialog :ProgressDialog,callBack:(String)->Unit){
    var imageUrl: String? =null
     progressDialog.setTitle("Reel Uploading . . .")
    progressDialog.show()
        FirebaseStorage.getInstance().getReference(folderName).child(UUID.randomUUID().toString())
            .putFile(uri)
            .addOnSuccessListener {
                it.storage.downloadUrl.addOnSuccessListener {
                    imageUrl = it.toString()
                    progressDialog.dismiss()
                    callBack(imageUrl!!)
                }
            }
            .addOnProgressListener {
                val uploadedValue :Long =(it.bytesTransferred/it.totalByteCount)*100
                progressDialog.setMessage("Uploaded $uploadedValue %")
            }
}