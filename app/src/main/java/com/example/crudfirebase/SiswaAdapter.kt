package com.example.crudfirebase

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.firebase.database.FirebaseDatabase

class SiswaAdapter(val mCtx : Context, val layoutResid : Int, val dntList : List<Kelas>) : ArrayAdapter<Kelas> (mCtx,layoutResid,dntList){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater : LayoutInflater = LayoutInflater.from(mCtx)

        val view : View = layoutInflater.inflate(layoutResid,null)

        val tvnama : TextView = view.findViewById(R.id.tv_nama)
        val tvalamat : TextView = view.findViewById(R.id.tv_alamat)
        val tvemail : TextView = view.findViewById(R.id.tv_email)
        val tvkelas : TextView = view.findViewById(R.id.tv_kelas)
        val tvjurusan : TextView = view.findViewById(R.id.tv_jurusan)
        val btnedit : Button = view.findViewById(R.id.btn_edit)
        val btnview : Button = view.findViewById(R.id.btn_view)

        val siswa = dntList[position]

        btnedit.setOnClickListener{
            showUpdateDialog(siswa)
        }
        btnview.setOnClickListener {
            showShowDialog(siswa)
        }

        tvnama.text = siswa.nama
        tvalamat.text = siswa.alamat
        tvemail.text = siswa.email
        tvkelas.text = siswa.kelas
        tvjurusan.text = siswa.jurusan

        return view
    }

    private fun showUpdateDialog(kelas: Kelas) {
        val builder = AlertDialog.Builder(mCtx)
        builder.setTitle("Setting Data")

        val inflater = LayoutInflater.from(mCtx)
        val view = inflater.inflate(R.layout.update_dialog,null)

        val etnama = view.findViewById<EditText>(R.id.et_nama)
        val etalamat = view.findViewById<EditText>(R.id.et_alamat)
        val etemail = view.findViewById<EditText>(R.id.et_email)
        val rbtkj = view.findViewById<RadioButton>(R.id.rb_tkj)
        val rbtja = view.findViewById<RadioButton>(R.id.rb_tja)
        val rbrpl = view.findViewById<RadioButton>(R.id.rb_rpl)
        val rb10 = view.findViewById<RadioButton>(R.id.rb_10)
        val rb11 = view.findViewById<RadioButton>(R.id.rb_11)
        val rb12 = view.findViewById<RadioButton>(R.id.rb_12)

        etnama.setText(kelas.nama)
        etalamat.setText(kelas.alamat)
        etemail.setText(kelas.email)

        builder.setView(view)

        builder.setPositiveButton("Update"){ p0,p1->
            var tkjdb : String = ""
            var tjadb : String = ""
            var rpldb : String = ""
            var sepuluhdb : String = ""
            var sebelasdb : String = ""
            var duabelasdb : String = ""

            val dbDnt = FirebaseDatabase.getInstance().getReference("data_siswa")

            val namadb = etnama.text.toString().trim()
            val alamatdb = etalamat.text.toString().trim()
            val emaildb = etemail.text.toString().trim()
            if (rbtja.isChecked){
                tjadb = rbtja.text.toString().trim()
            }else if (rbtkj.isChecked){
                tkjdb = rbtkj.text.toString().trim()
            }else if (rbrpl.isChecked){
                rpldb = rbrpl.text.toString().trim()
            }

            if (rb10.isChecked){
                sepuluhdb = rb10.text.toString().trim()
            }else if (rb11.isChecked){
                sebelasdb = rb11.text.toString().trim()
            }else if (rb12.isChecked){
                duabelasdb = rb12.text.toString().trim()
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

            if (emaildb.isEmpty()){
                etalamat.error = "Email masih kosong!"
                etalamat.requestFocus()
                return@setPositiveButton
            }

            val donat = Kelas(kelas.id,namadb,alamatdb, emaildb,tjadb+tkjdb+rpldb,sepuluhdb+sebelasdb+duabelasdb)

            dbDnt.child(donat.id!!).setValue(donat)

            Toast.makeText(mCtx,"Data berhasil diupdate",Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("Delete"){ p0,p1->
            val dbDnt = FirebaseDatabase.getInstance().getReference("data_siswa").child(kelas.id.toString())
            dbDnt.removeValue()

            Toast.makeText(mCtx,"Data berhasil dihapus",Toast.LENGTH_SHORT).show()

            return@setNegativeButton
        }
        builder.setNeutralButton("No"){ p0,p1->
        }
        val alert = builder.create()
        alert.show()
    }
    private fun showShowDialog(kelas: Kelas){
        val builder = AlertDialog.Builder(mCtx)
        builder.setTitle("View Data")

        val inflater = LayoutInflater.from(mCtx)
        val view = inflater.inflate(R.layout.view_data,null)

        val vwnama = view.findViewById<TextView>(R.id.vw_nama)
        val vwalamat = view.findViewById<TextView>(R.id.vw_alamat)
        val vwemail = view.findViewById<TextView>(R.id.vw_email)
        val vwjurusan = view.findViewById<TextView>(R.id.vw_jurusan)
        val vwkelas = view.findViewById<TextView>(R.id.vw_kelas)

        vwnama.setText(kelas.nama)
        vwalamat.setText(kelas.alamat)
        vwemail.setText(kelas.email)
        vwjurusan.setText(kelas.jurusan)
        vwkelas.setText(kelas.kelas)

        builder.setView(view)

        builder.setNeutralButton("Kembali"){ p0,p1->
        }
        val alert = builder.create()
        alert.show()
    }
}