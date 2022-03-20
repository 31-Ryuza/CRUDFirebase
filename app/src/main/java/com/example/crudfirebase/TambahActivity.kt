package com.example.crudfirebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.firebase.database.FirebaseDatabase

class TambahActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var etnama : EditText
    private lateinit var etalamat : EditText
    private lateinit var etemail : EditText
    private lateinit var rbtkj : RadioButton
    private lateinit var rbtja : RadioButton
    private lateinit var rbrpl : RadioButton
    private lateinit var rb10 : RadioButton
    private lateinit var rb11 : RadioButton
    private lateinit var rb12 : RadioButton
    private lateinit var btnsimpan : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah)

        etnama = findViewById(R.id.et_nama)
        etalamat = findViewById(R.id.et_alamat)
        etemail = findViewById(R.id.et_email)
        rbtkj = findViewById(R.id.rb_tkj)
        rbtja = findViewById(R.id.rb_tja)
        rbrpl = findViewById(R.id.rb_rpl)
        rb10 = findViewById(R.id.rb_10)
        rb11 = findViewById(R.id.rb_11)
        rb12 = findViewById(R.id.rb_12)
        btnsimpan = findViewById(R.id.btn_simpan)

        btnsimpan.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        var tkj : String = ""
        var tja : String = ""
        var rpl : String = ""
        var sepuluh : String = ""
        var sebelas : String = ""
        var duabelas : String = ""

        val nama = etnama.text.toString().trim()
        val alamat = etalamat.text.toString().trim()
        val email = etemail.text.toString().trim()

        if (rbtkj.isChecked){
            tkj = rbtkj.text.toString().trim()
        }else if (rbtja.isChecked){
            tja = rbtja.text.toString().trim()
        }else if (rbrpl.isChecked){
            rpl = rbrpl.text.toString().trim()
        }

        if (rb10.isChecked){
            sepuluh = rb10.text.toString().trim()
        }else if (rb11.isChecked){
            sebelas = rb11.text.toString().trim()
        }else if (rb12.isChecked){
            duabelas = rb12.text.toString().trim()
        }


        if (nama.isEmpty()){
            etnama.error = "Nama anda harus diisi"
            return
        }

        if (alamat.isEmpty()){
            etalamat.error = "Alamat anda harus diisi"
            return
        }

        if (email.isEmpty()){
            etemail.error = "Email anda harus diisi"
            return
        }

        val ref = FirebaseDatabase.getInstance().getReference("data_siswa")

        val pesanId = ref.push().key

        val dnt = Kelas(pesanId,nama,alamat,email,tkj+tja+rpl,sepuluh+sebelas+duabelas)

        if (pesanId != null) {
            ref.child(pesanId).setValue(dnt).addOnCompleteListener{
                Toast.makeText(applicationContext, "Data Berhasil Ditambahkan", Toast.LENGTH_SHORT).show()
            }
        }
        clearText()
    }

    private fun clearText() {
        etnama.setText("")
        etalamat.setText("")
        etemail.setText("")
        rbtkj.isChecked = false
        rbtja.isChecked = false
        rbrpl.isChecked = false
        rb10.isChecked = false
        rb11.isChecked = false
        rb12.isChecked = false
    }
}