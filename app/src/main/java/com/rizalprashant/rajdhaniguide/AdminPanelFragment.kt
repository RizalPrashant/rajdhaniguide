package com.rizalprashant.rajdhaniguide

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.ktx.Firebase
import dz.notacompany.el_cous.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_admin_panel.*

class AdminPanelFragment : Fragment(R.layout.fragment_admin_panel) {

    private lateinit var mainAct : MainActivity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}