package com.example.crudfirebase

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.firebase.database.FirebaseDatabase

class DonatAdapter(val mCtx : Context, val layoutResid : Int, val dntList : List<Donat>) : ArrayAdapter<Donat> (mCtx,layoutResid,dntList){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater : LayoutInflater = LayoutInflater.from(mCtx)

        val view : View = layoutInflater.inflate(layoutResid,null)

        val tvnama : TextView = view.findViewById(R.id.tv_nama)
        val tvalamat : TextView = view.findViewById(R.id.tv_alamat)
        val tvjenis : TextView = view.findViewById(R.id.tv_jenis)
        val tvtoping : TextView = view.findViewById(R.id.tv_toping)
        val btnedit : Button = view.findViewById(R.id.btn_edit)

        val donat = dntList[position]

        btnedit.setOnClickListener{
            showUpdateDialog(donat)
        }

        tvnama.text = donat.nama
        tvalamat.text = donat.alamat
        tvjenis.text = donat.jenis
        tvtoping.text = donat.toping

        return view
    }

    private fun showUpdateDialog(donat: Donat) {
        val builder = AlertDialog.Builder(mCtx)
        builder.setTitle("Edit Title")

        val inflater = LayoutInflater.from(mCtx)
        val view = inflater.inflate(R.layout.update_dialog,null)

        val etnama = view.findViewById<EditText>(R.id.et_nama)
        val etalamat = view.findViewById<EditText>(R.id.et_alamat)
        val rbbiasa = view.findViewById<RadioButton>(R.id.rb_biasa)
        val rbmini = view.findViewById<RadioButton>(R.id.rb_mini)
        val cbkacang = view.findViewById<CheckBox>(R.id.cb_kacang)
        val cbseres = view.findViewById<CheckBox>(R.id.cb_seres)
        val cbselai = view.findViewById<CheckBox>(R.id.cb_selai)

        etnama.setText(donat.nama)
        etalamat.setText(donat.alamat)

        builder.setView(view)

        builder.setPositiveButton("Update"){ p0,p1->
            var biasadb : String = ""
            var minidb : String = ""
            var kacangdb : String = ""
            var selaidb : String = ""
            var seresdb : String = ""

            val dbDnt = FirebaseDatabase.getInstance().getReference("pesanan")

            val namadb = etnama.text.toString().trim()
            val alamatdb = etalamat.text.toString().trim()
            if (rbbiasa.isChecked){
                biasadb = rbbiasa.text.toString().trim()
            }else if (rbmini.isChecked){
                minidb = rbmini.text.toString().trim()
            }

            if (cbkacang.isChecked){
                kacangdb = cbkacang.text.toString().trim()
            }else{
                kacangdb = ""
            }

            if (cbselai.isChecked){
                selaidb = cbselai.text.toString().trim()
            }else{
                selaidb = ""
            }

            if (cbseres.isChecked){
                seresdb = cbseres.text.toString().trim()
            }else{
                seresdb = ""
            }

            if (namadb.isEmpty()){
                etnama.error = "Nama masih kosong!"
                etnama.requestFocus()
                return@setPositiveButton
            }

            if (alamatdb.isEmpty()){
                etalamat.error = "Alamat masih kosong!"
                etalamat.requestFocus()
                return@setPositiveButton
            }

            val donat = Donat(donat.id,namadb,alamatdb,biasadb+minidb,kacangdb+" "+seresdb+" "+selaidb)

            dbDnt.child(donat.id!!).setValue(donat)

            Toast.makeText(mCtx,"Data berhasil diupdate",Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("Delete"){ p0,p1->
            val dbDnt = FirebaseDatabase.getInstance().getReference("pesanan").child(donat.id.toString())
            dbDnt.removeValue()

            Toast.makeText(mCtx,"Data berhasil dihapus",Toast.LENGTH_SHORT).show()

            return@setNegativeButton
        }
        builder.setNeutralButton("No"){ p0,p1->
        }
        val alert = builder.create()
        alert.show()
    }
}