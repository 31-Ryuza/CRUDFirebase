package com.example.crudfirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {
    private lateinit var tambah : FloatingActionButton
    private lateinit var listDnt : ListView
    private lateinit var ref : DatabaseReference
    private lateinit var dntlist : MutableList<Kelas>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ref = FirebaseDatabase.getInstance().getReference("data_siswa")

        tambah = findViewById(R.id.btn_tambah)

        listDnt = findViewById(R.id.lv_data)
        tambah.setOnClickListener{
            this.startActivity(Intent(this,TambahActivity::class.java))
        }

        dntlist = mutableListOf()

        ref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    dntlist.clear()
                    for (h in snapshot.children){
                        val siswa = h.getValue(Kelas::class.java)
                        if (siswa != null){
                            dntlist.add(siswa)
                        }
                    }
                    val adapter = SiswaAdapter(this@MainActivity, R.layout.item_siswa, dntlist)
                    listDnt.adapter = adapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}