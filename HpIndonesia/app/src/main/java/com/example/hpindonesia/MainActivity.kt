package com.example.hpindonesia

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import cn.pedant.SweetAlert.SweetAlertDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var sharedPref: SharedPref
    lateinit var hpDao: HpDao
    lateinit var adapter: HpAdapter

    companion object {
        const val REQUEST_ADD = 100
        const val REQUEST_EDIT = 200
        const val REQUEST_REMOVE = 300
        const val REQUEST_DETAIL = 500
        const val KEY_HP = "HP"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        hpDao = AppDatabase.getInstance(this).hpDao()
        sharedPref = SharedPref(this)
        recylerView.layoutManager = LinearLayoutManager(this)
        adapter = HpAdapter(hpDao.selectAll(), sharedPref)
        recylerView.adapter = adapter
        adapter.onItemClickListener = {
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(KEY_HP, it)
            startActivityForResult(intent, REQUEST_DETAIL)
        }
        buttonAdd.setOnClickListener {
            val intent = Intent(this, AddEditActivity::class.java)
            startActivityForResult(intent, REQUEST_ADD)
        }
    }

    override fun onResume() {
        super.onResume()
        recylerView.layoutManager = if (sharedPref.gridLayout && sharedPref.column > 0) {
            GridLayoutManager(this, sharedPref.column)
        } else {
            LinearLayoutManager(this)
        }

        recylerView.layoutManager = if (sharedPref.gridLayout) {
            GridLayoutManager(this, sharedPref.column)
        } else {
            LinearLayoutManager(this)
        }

        if (sharedPref.isHide) {
            recylerView.visibility = View.GONE
        } else {
            recylerView.visibility = View.VISIBLE
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_ADD && resultCode == Activity.RESULT_OK && data != null) {
            val hp = data.getParcelableExtra<Hp>(KEY_HP)
            hp?.apply {
                hpDao.insert(this)
            }
            SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("Success!")
                .setContentText("Data Berhasil Disimpan!")
                .show()
            //Toast.makeText(this, "Data berhasil ditambahkan", Toast.LENGTH_SHORT).show()
        }
        adapter.list = hpDao.selectAll()
        adapter.notifyDataSetChanged()
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
          R.id.menuSettings -> startActivity(Intent(this, SettingsActivity::class.java))
        }
        return  super.onOptionsItemSelected(item)
    }
}