package com.rizalprashant.rajdhaniguide

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.GravityCompat
import dz.notacompany.el_cous.R
import kotlinx.android.synthetic.main.activity_all_cateogries.*
import kotlinx.android.synthetic.main.activity_user_dashboard.drawer_layout

class AllCateogriesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_cateogries)

        //Back to user dashborad
        back_pressed.setOnClickListener {
            if(drawer_layout.isDrawerVisible(GravityCompat.START)){
                drawer_layout.closeDrawer(GravityCompat.START)
            } else
                super.onBackPressed()
        }

        // example
        press_restaurant.setOnClickListener {
            intentActionGo(1)
        }

        press_car_rent.setOnClickListener {
            val i = Intent(applicationContext, MainActivity::class.java)
            startActivity(i)
            finish()
        }

        // TODO Soon to be implemented
//        press_hotel.setOnClickListener {
//            intentActionGo(3)
//        }

        // TODO soon to be implemented
//        press_education.setOnClickListener {
//            intentActionGo(4)
//        }

        press_shops.setOnClickListener {
            val i = Intent(applicationContext, CompetitionFragment::class.java)
            startActivity(i)
            finish()
        }

    }

    fun intentActionGo(categoryId: Int) {
        var intent = Intent(applicationContext, AllPlacesActivity::class.java)
        intent.putExtra("CALL_FROM_CATEGORY", "call_from_category")
        intent.putExtra("CATEOGRY_ID", categoryId)
        startActivity(intent)
    }
}