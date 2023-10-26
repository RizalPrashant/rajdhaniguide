package com.rizalprashant.rajdhaniguide

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.rizalprashant.rajdhaniguide.adapter.OnBoardingViewPagerAdapter
import com.rizalprashant.rajdhaniguide.model.OnBoardingData
import dz.notacompany.el_cous.R

class OnBoardingActivity : AppCompatActivity() {

    var onBoardingViewPagerAdapter: OnBoardingViewPagerAdapter? = null
    var tabLayout: TabLayout? = null
    var onBoardingViewPager: ViewPager? = null
    var next: TextView? = null
    var position = 0
    var sharedPreference: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(restorePrefData()){
            val i = Intent(applicationContext, AllCateogriesActivity::class.java)
            startActivity(i)
            finish()
        }

        setContentView(R.layout.activity_on_boarding)
        //Toast.makeText(this, "Welcome Pheakdey Developer", Toast.LENGTH_SHORT).show()

        tabLayout = findViewById(R.id.tab_indicator)
        next = findViewById(R.id.next)

        // add some data to model
        val onBoardingData:MutableList<OnBoardingData> = ArrayList()
        onBoardingData.add(OnBoardingData("Never get lost!", "Find all direct public routes in the valley", R.drawable.searching))
        onBoardingData.add(OnBoardingData("Win free goodies!", "Enter fun competitions and get rewarded", R.drawable.shopping))
        onBoardingData.add(OnBoardingData("Take awesome pictures!", "Find places to visit around Kathmandu", R.drawable.vlog))

        setOnBoardingViewPagerAdapter(onBoardingData)

        position = onBoardingViewPager!!.currentItem

        next?.setOnClickListener {
            if(position < onBoardingData.size){
                position++
                onBoardingViewPager!!.currentItem = position
            }
            if(position == onBoardingData.size){
                savePrefData()
                val i = Intent(applicationContext, AllCateogriesActivity::class.java)
                startActivity(i)
            }
        }

        tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
               position = tab!!.position
                if(tab.position == onBoardingData.size -1){
                    next!!.text = "Get Started"
                } else {
                    next!!.text = "Next"
                }
            }
        })
    }

    private fun setOnBoardingViewPagerAdapter(onBoardingData: List<OnBoardingData>){
        onBoardingViewPager = findViewById(R.id.screenPager)
        onBoardingViewPagerAdapter = OnBoardingViewPagerAdapter(this, onBoardingData)
        onBoardingViewPager!!.adapter = onBoardingViewPagerAdapter
        tabLayout?.setupWithViewPager(onBoardingViewPager)
    }

    private fun savePrefData(){
        sharedPreference = applicationContext.getSharedPreferences("pref", Context.MODE_PRIVATE)
        val editor : SharedPreferences.Editor = sharedPreference!!.edit()
        editor.putBoolean("isFirstTimeRun", true)
        editor.apply()
    }

    private fun restorePrefData(): Boolean {
        sharedPreference = applicationContext.getSharedPreferences("pref", Context.MODE_PRIVATE)
        return sharedPreference!!.getBoolean("isFirstTimeRun", false)
    }
}