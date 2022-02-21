package com.example.crudfirebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.firebase.database.FirebaseDatabase

class TambahActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var etnama : EditText
    private lateinit var etalamat : EditText
    private lateinit var rbbiasa : RadioButton
    private lateinit var rbmini : RadioButton
    private lateinit var cbkacang : CheckBox
    private lateinit var cbseres : CheckBox
    private lateinit var cbselai : CheckBox
    private lateinit var btnsimpan : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah)

        etnama = findViewById(R.id.et_nama)
        etalamat = findViewById(R.id.et_alamat)
        rbbiasa = findViewById(R.id.rb_biasa)
        rbmini = findViewById(R.id.rb_mini)
        cbkacang = findViewById(R.id.cb_kacang)
        cbseres = findViewById(R.id.cb_seres)
        cbselai = findViewById(R.id.cb_selai)
        btnsimpan = findViewById(R.id.btn_simpan)

        btnsimpan.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        var biasa : String = ""
        var mini : String = ""
        var kacang : String = ""
        var selai : String = ""
        var seres : String = ""

        val nama = etnama.text.toString().trim()
        val alamat = etalamat.text.toString().trim()

        if (rbbiasa.isChecked){
            biasa = rbbiasa.text.toString().trim()
        }else if (rbmini.isChecked){
            mini = rbmini.text.toString().trim()
        }

        if (cbkacang.isChecked){
            kacang = cbkacang.text.toString().trim()
        }else{
            kacang = ""
        }

        if (cbselai.isChecked){
            selai = cbselai.text.toString().trim()
        }else{
            selai = ""
        }

        if (cbseres.isChecked){
            seres = cbseres.text.toString().trim()
        }else{
            seres = ""
        }

        if (nama.isEmpty()){
            etnama.error = "Nama anda harus diisi"
            return
        }

        if (alamat.isEmpty()){
            etalamat.error = "Alamat anda harus diisi"
            return
        }

        val ref = FirebaseDatabase.getInstance().getReference("pesanan")

        val pesanId = ref.push().key

        val dnt = Donat(pesanId,nama,alamat,biasa+mini,kacang+" "+seres+" "+selai+"")

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
        rbbiasa.isChecked = false
        rbmini.isChecked = false
        cbseres.isChecked = false
        cbselai.isChecked = false
        cbkacang.isChecked = false
    }
}