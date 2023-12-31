package com.rizalprashant.rajdhaniguide.webview

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.rizalprashant.rajdhaniguide.Helper.ConnectivityHelper
import com.rizalprashant.rajdhaniguide.UserDashboardActivity
import dz.notacompany.el_cous.R
import kotlinx.android.synthetic.main.activity_e_commerce.*

class ECommerceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_e_commerce)

        val helper = ConnectivityHelper()
        if(helper.isConnectedToNetwork(this)){
            loadWebPage()
            //Toast.makeText(this, "Have Internet Connection", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_LONG).show()
            showCustomDialog()
        }
    }

    private fun showCustomDialog() {
        MaterialAlertDialogBuilder(applicationContext)
            .setMessage("Please connect to the internet to proceed further")
            .setCancelable(false)
            .setPositiveButton(
                "Connect"
            ) { dialogInterface, i -> startActivity(Intent(Settings.ACTION_WIFI_SETTINGS)) }
            .setNegativeButton(
                "Cancel"
            ) { dialogInterface, i ->
                startActivity(Intent(applicationContext, UserDashboardActivity::class.java))
                finish()
            }.show()
    }

    fun loadWebPage() {
        wview.settings.javaScriptEnabled = true;
        //wview.settings.builtInZoomControls = true;
        val url = intent.getStringExtra("LOAD_URL")
        wview.webViewClient = WebViewClient()
        //wview.loadUrl("http://lukpheakdey.com/")
        wview.loadUrl(url.toString())
        wview.webViewClient = object : WebViewClient() {

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                progressBar.setVisibility(View.VISIBLE)
                super.onPageStarted(view, url, favicon)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                progressBar.setVisibility(View.GONE)
                super.onPageFinished(view, url)
            }
        }
    }

}