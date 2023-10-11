package com.rizalprashant.rajdhaniguide

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.ktx.Firebase
import dz.notacompany.el_cous.R
import kotlinx.android.synthetic.main.activity_main.adminButton
import kotlinx.android.synthetic.main.activity_main.deleteRouteButton
import kotlinx.android.synthetic.main.activity_main.githubButton
import kotlinx.android.synthetic.main.activity_main.topBarLayout
import kotlinx.android.synthetic.main.activity_main.topBarTextView
import kotlinx.android.synthetic.main.activity_main.topBarTextView2


class CompetitionFragment : Fragment(R.layout.fragment_competition) {

    private lateinit var mainAct : MainActivity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainAct = activity as MainActivity // Reference to MainActivity

        // Resetting the elements of the top bar
        mainAct.topBarLayout.visibility = View.VISIBLE
        mainAct.topBarTextView2.text = getString(R.string.competition_top_bar_text)
        mainAct.topBarTextView.visibility = View.GONE
        mainAct.adminButton.visibility = View.GONE
        mainAct.githubButton.visibility = View.GONE
    }
}