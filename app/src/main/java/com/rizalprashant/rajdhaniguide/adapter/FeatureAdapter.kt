package com.rizalprashant.rajdhaniguide.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rizalprashant.rajdhaniguide.PlaceDetailActivity
import com.rizalprashant.rajdhaniguide.model.Place
import dz.notacompany.el_cous.R
import kotlinx.android.synthetic.main.all_places_card_design.view.*
import kotlinx.android.synthetic.main.featured_card_design.view.*

class FeatureAdapter: RecyclerView.Adapter<FeatureAdapter.MyViewHolder>() {

    lateinit var context: Context

    private var placeList = emptyList<Place>()

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.featured_card_design, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = placeList[position]
        holder.itemView.featured_title.text = currentItem.place_name.toString()
        holder.itemView.featured_desc.text = currentItem.place_desc.toString()
        holder.itemView.featured_rating.rating = currentItem.star_point.toFloat()

        val urlImage = currentItem.image_url.toString()
        Glide.with(holder.itemView.context).load(urlImage).into(holder.itemView.featured_image)

        holder.itemView.feature_card_id.setOnClickListener {
            var intent = Intent(holder.itemView.context, PlaceDetailActivity::class.java)
            intent.putExtra("PLACE_NAME", currentItem.place_name.toString())
            intent.putExtra("PLACE_DESC", currentItem.place_desc.toString())
            intent.putExtra("PLACE_PHONE", currentItem.phone.toString())
            intent.putExtra("PLACE_ADDRESS", currentItem.address.toString())
            intent.putExtra("PLACE_STAR", currentItem.star_point)
            intent.putExtra("OPEN_TIME", currentItem.open_time)
            intent.putExtra("CLOSE_TIME", currentItem.close_time)
            intent.putExtra("DAY_OPEN", currentItem.day_open)
            intent.putExtra("DAY_CLOSE", currentItem.day_close)
            intent.putExtra("IMAGE_URL", currentItem.image_url)
            intent.putExtra("MAP_URL", currentItem.map_url)
            intent.putExtra("WEBSITE", currentItem.website)
            intent.putExtra("REVIEW", currentItem.review)
            holder.itemView.context.startActivity(intent)
            Toast.makeText(holder.itemView.context, currentItem.place_name.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return placeList.size
    }

    fun setData(place: List<Place>) {
        this.placeList = place
        notifyDataSetChanged()
    }
}
