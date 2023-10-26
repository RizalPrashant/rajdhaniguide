package com.rizalprashant.rajdhaniguide

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dz.notacompany.el_cous.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_user_dashboard.drawer_layout
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket
import java.net.SocketAddress
import java.util.concurrent.LinkedBlockingQueue

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private lateinit var loading: AlertDialog
    var isAdmin = false
    lateinit var mAdView : AdView


    lateinit var currentDocument: String // Used to get the Route ID from DetailsFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_ELCous) // Sets theme to override splash screen theme
        super.onCreate(savedInstanceState)

        auth = Firebase.auth
        MobileAds.initialize(this) {}
        setContentView(R.layout.activity_main)

        mAdView = findViewById<View>(R.id.adView) as AdView
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        replaceCurrentFragment(HomeFragment(),true)

        topBarLayout.setOnClickListener {
            if (supportFragmentManager.backStackEntryCount == 0){
                val i = Intent(applicationContext, AllCateogriesActivity::class.java)
                startActivity(i)
                finish()
            } else{
                supportFragmentManager.popBackStack()
            }
        }

        adminButton.setOnClickListener {
            replaceCurrentFragment(CompetitionFragment(),false)
        }

        githubButton.setOnClickListener {
            val openURL = Intent(Intent.ACTION_VIEW)
            openURL.data = Uri.parse("https://github.com/50t0r25/EL-Cous")
            startActivity(openURL)
        }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and sign them up if they aren't.
        if (auth.currentUser == null) {
            auth.signInAnonymously()
        }
    }
    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 0){
            val i = Intent(applicationContext, AllCateogriesActivity::class.java)
            startActivity(i)
            finish()
        } else{
            supportFragmentManager.popBackStack()
        }
    }

    // Replaces fragment while either clearing or adding to BackStack
    fun replaceCurrentFragment(fragment: Fragment, clearBackStack : Boolean) =
        supportFragmentManager.beginTransaction().apply {

            // Sliding from bottom animation
            setCustomAnimations(
                R.anim.slide_in,
                R.anim.fade_out,
                R.anim.fade_in,
                R.anim.slide_out
            )
            replace(R.id.flFragment, fragment)

            // If clearBackStack is true, clear the backstack and open the new fragment
            // Else don't clear it and add the new fragment to it
            if (clearBackStack) {
                supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            } else {
                addToBackStack(null)
            }
            commit()
        }

    // ------------------- LOADING DIALOG START ------------------------
    // Loading dialog that restricts user from pressing anything while it's displayed

    fun createLoadingDialog() {
        loading = MaterialAlertDialogBuilder(this)
            .setView(this.layoutInflater.inflate(R.layout.dialog_loading, null))
            .setCancelable(false)
            .create()
        loading.show()
    }

    fun dismissLoadingDialog() {
        loading.dismiss()
    }
    // ------------------- LOADING DIALOG END ---------------------------


    // Function checks if user has internet access
    fun isOnline(): Boolean {
        val queue = LinkedBlockingQueue<Boolean>()

        // Start a thread to run check on a non-UI thread
        // prevents freezing on networks with no internet
        Thread {
            try {
                val timeoutMs = 1500
                val sock = Socket()
                val sockaddr: SocketAddress = InetSocketAddress("8.8.8.8", 53)
                sock.connect(sockaddr, timeoutMs)
                sock.close()
                queue.add(true)
            } catch (e: IOException) {
                queue.add(false)
            }
        }.start()
        return queue.take()
    }
}