package com.rizalprashant.rajdhaniguide

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import dz.notacompany.el_cous.R
import kotlinx.android.synthetic.main.activity_all_cateogries.*
import kotlinx.android.synthetic.main.activity_place_detail.*
import kotlinx.android.synthetic.main.activity_place_detail.back_pressed

class PlaceDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_detail)

        val name = intent.getStringExtra("PLACE_NAME")
        val star = intent.getStringExtra("PLACE_STAR")
        val desc = intent.getStringExtra("PLACE_DESC")
        val address = intent.getStringExtra("PLACE_ADDRESS")
        val phone = intent.getStringExtra("PLACE_PHONE")
        val open_time = intent.getStringExtra("OPEN_TIME")
        val close_time = intent.getStringExtra("CLOSE_TIME")
        val day_open = intent.getStringExtra("DAY_OPEN")
        val day_close = intent.getStringExtra("DAY_CLOSE")
        val map_url = intent.getStringExtra("MAP_URL")
        val image_url = intent.getStringExtra("IMAGE_URL")
        val open_day = "Open : ${open_time} | Close: ${close_time} | Day Open: ${day_open} | Day Close: ${day_close}"
        val website = intent.getStringExtra("WEBSITE")
        val review = intent.getStringExtra("REVIEW")

        place_detail_title.text = name.toString()
        if (star != null) {
            all_place_rating.rating = star.toFloat()
        }
        place_detail_address.text = address.toString()
        place_detail_desc.text = desc.toString()
        place_detail_phone.text = phone.toString()
        place_detail_hour_open.text = open_day.toString()
        place_detail_website.text = website.toString()
        place_detail_review.text = review.toString()
        Glide.with(this).load(image_url.toString()).into(place_detail_image)

        //back_pressed.setVisibility(View.VISIBLE)
        back_pressed.setOnClickListener {
            super.onBackPressed()
        }

        detail_phone.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL);
            intent.data = Uri.parse("tel:$phone")
            startActivity(intent)
        }

        detail_website.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(website.toString()))
            startActivity(browserIntent)
        }

        detail_map.setOnClickListener {
            val mapUri = Uri.parse("geo:0,0?q=" + Uri.encode(address))
            val mapIntent = Intent(Intent.ACTION_VIEW, mapUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }
    }
}