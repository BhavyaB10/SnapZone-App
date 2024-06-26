package com.example.instagramclone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.instagramclone.Adapters.SaveAdapter
import com.example.instagramclone.Model.Posts
import com.google.firebase.firestore.*

class SaveActivity : AppCompatActivity() {
    private lateinit var recyclerView : RecyclerView
    private lateinit var saveArrayList : ArrayList<Posts>
    private lateinit var myAdapter : SaveAdapter
    private lateinit var db : FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_save)

        recyclerView=findViewById(R.id.recylerView)
        recyclerView.layoutManager=LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        saveArrayList= arrayListOf()

        myAdapter= SaveAdapter(saveArrayList)

        recyclerView.adapter=myAdapter

        EventChangeListener()
    }

    private fun EventChangeListener() {
        db=FirebaseFirestore.getInstance()
        db.collection("Posts")
            .addSnapshotListener(object : EventListener<QuerySnapshot>{
                override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {

                    if(error!=null){
                        Log.e("FireStore Error",error.message.toString())
                    }

                    for(dc : DocumentChange in value?.documentChanges!!){
                        if(dc.type== DocumentChange.Type.ADDED){
                            saveArrayList.add(dc.document.toObject(Posts::class.java))
                        }
                    }

                    myAdapter.notifyDataSetChanged()
                }

            })
    }
}