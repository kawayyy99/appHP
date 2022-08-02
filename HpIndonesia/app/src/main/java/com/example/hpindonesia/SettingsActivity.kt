package com.example.hpindonesia

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.widget.addTextChangedListener
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        supportActionBar?.apply {
            title = "Settings"
            setDisplayHomeAsUpEnabled(true)
        }


        val appSettingPref: SharedPreferences = getSharedPreferences("AppSettingPrefs",0)
        val SharedPrefEdit: SharedPreferences.Editor = appSettingPref.edit()
        val isNightModeOn:Boolean = appSettingPref.getBoolean("NightMode",false)

        val sharedPref = SharedPref(this)
        settingGrid.isChecked = sharedPref.gridLayout
        settingGrid.setOnCheckedChangeListener{ buttonView, isChecked ->
            sharedPref.gridLayout = isChecked
        }

        settingLatin.isChecked = sharedPref.latin
        settingLatin.setOnCheckedChangeListener{ buttonView, isChecked ->
            sharedPref.gridLayout = isChecked
        }

        settingAppName.setText(sharedPref.appName)
        settingAppName.addTextChangedListener {
            sharedPref.appName = it.toString()
        }

        settingColumn.setText(sharedPref.column.toString())
        settingColumn.addTextChangedListener{
            var cols = if (it.toString().length == 0) 1 else it.toString().toInt()
            cols = if (cols > 3){
                3
            }else if (cols < 1){
                1
            }else {
                cols
            }
            sharedPref.column = cols
        }

        if (isNightModeOn){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        darkmode.setOnClickListener {
            if (isNightModeOn) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                SharedPrefEdit.putBoolean("NightMode", false)
                SharedPrefEdit.apply()
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                SharedPrefEdit.putBoolean("NightMode", true)
                SharedPrefEdit.apply()
            }
        }

        settinghide.isChecked = sharedPref.isHide
        settinghide.setOnCheckedChangeListener{ buttonView, isChecked ->
            sharedPref.isHide = isChecked
        }

        settingHorizontal.isChecked = sharedPref.row
        settingHorizontal.setOnCheckedChangeListener{ buttonView, isChecked ->
            sharedPref.row = isChecked
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return super.onOptionsItemSelected(item)
    }
}