package com.example.hpindonesia

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import cn.pedant.SweetAlert.SweetAlertDialog
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detail.*
import java.nio.file.Files.delete

class DetailActivity : AppCompatActivity() {
    lateinit var hpDao: HpDao
    var hp: Hp? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        hpDao = AppDatabase.getInstance(this).hpDao()
        hp = intent.getParcelableExtra<Hp>(MainActivity.KEY_HP)
        populate(hp)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }
    }

    fun populate(hp: Hp?){
        hp?.apply {
            Glide.with(this@DetailActivity).load(gambar).into(detailImage)
            detailName.text = nama
            detailLatin.text = latin
            detailKeterangan.text = keterangan
            detailUrl.text = url
        }
        supportActionBar?.apply {
            title = hp?.nama
            subtitle = hp?.latin
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menuEdit -> {
                val intent = Intent(this, AddEditActivity::class.java)
                intent.putExtra(MainActivity.KEY_HP, hp)
                startActivityForResult(intent, MainActivity.REQUEST_EDIT)
            }
            R.id.menuRemove -> {
                SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Apa Anda Yakin?")
                    .setContentText("data yang dihapus tidak dapat dikembalikan!")
                    .setConfirmText("Ya, hapus ini!")
                    .setCancelText("batal")
                    .setConfirmClickListener {
                        hp?.apply {
                            hpDao.delete(this)
                        }
                        SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Success!")
                            .setContentText("Data Berhasil Dihapus!")
                            .setConfirmText("OK")
                            .setConfirmClickListener {
                                finish()
                            }
                            .show()
                    }
                .show()
//                AlertDialog.Builder(this)
//                    .setMessage("Apakah anda yakin menghapus item ini?")
//                    .setPositiveButton("Ya"){_,_ ->
//                        hp?.apply {
//                            hpDao.delete(this)
//                        }
//                        Toast.makeText(this,"Data berhasil dihapus", Toast.LENGTH_SHORT).show()
//                        finish()
//                    }
//                    .setNegativeButton("Tidak", null)
//                    .show()
            }

            else ->{
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == MainActivity.REQUEST_EDIT && resultCode == Activity.RESULT_OK && data != null){
            hp = data.getParcelableExtra(MainActivity.KEY_HP)
            populate(hp)
            hp?.apply {
                hpDao.update(this)
            }
            SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("Success!")
                .setContentText("Data Berhasil Diedit!")
                .show()
            //Toast.makeText(this, "Data Berhasil Diedit", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        return  super.onCreateOptionsMenu(menu)
    }
}