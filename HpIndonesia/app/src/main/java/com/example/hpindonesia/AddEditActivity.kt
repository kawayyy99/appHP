package com.example.hpindonesia

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_add_edit.*

class AddEditActivity : AppCompatActivity() {
    var hp: Hp? = null
    companion object {
        const val REQUEST_IMAGE = 400
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit)
        hp = intent.getParcelableExtra(MainActivity.KEY_HP)
        supportActionBar?.apply {
            title = if (hp == null) "Add HP " else "Edit HP"
            hp?.apply {
                subtitle = nama
            }
            setDisplayHomeAsUpEnabled(true)
        }
        hp?.apply{
            addEditNama.setText(nama)
            addEditLatin.setText(latin)
            addEditKeterengan.setText(keterangan)
            addEditUrl.setText(url)
            addEditGambar.setText(gambar)
        }
        buttonBrowse.setOnClickListener {
            val intent = Intent()
            intent.setType("image/*")
            intent.setAction(Intent.ACTION_GET_CONTENT)
            startActivityForResult(Intent.createChooser(intent, "Pilih Gambar"), REQUEST_IMAGE)
        }
        buttonSave.setOnClickListener {
            if(hp == null) hp = Hp()
            hp?.apply {
                nama = addEditNama.text.toString()
                latin = addEditLatin.text.toString()
                keterangan = addEditKeterengan.text.toString()
                url = addEditUrl.text.toString()
                gambar = addEditGambar.text.toString()
                val intent = Intent()
                intent.putExtra(MainActivity.KEY_HP, hp)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE && resultCode == Activity.RESULT_OK && data != null){
            val imageUrl = data.data?.toString()
            addEditGambar.setText(imageUrl)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return super.onOptionsItemSelected(item)
    }
}