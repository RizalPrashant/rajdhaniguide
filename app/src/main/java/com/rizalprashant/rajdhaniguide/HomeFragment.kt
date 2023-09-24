package com.rizalprashant.rajdhaniguide

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dz.notacompany.el_cous.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(R.layout.fragment_home) {

    private val db = Firebase.firestore
    private lateinit var mainAct : MainActivity

    private lateinit var source: Source

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainAct = activity as MainActivity // Reference to MainActivity

        // Resetting the elements of the top bar
        mainAct.topBarLayout.visibility = View.GONE
        mainAct.topBarTextView.visibility = View.VISIBLE
        mainAct.adminButton.visibility = View.GONE
        mainAct.githubButton.visibility = View.GONE
        mainAct.deleteRouteButton.visibility = View.GONE

        // These lists store all the departures, destinations and their IDs from the database
        val cityArray12 = arrayOf<String>("Hattiban", "Classic Chowk", "Satdobato", "Lagankhel", "Patan", "Kumaripati", "United Academy", "Jawalakhel", "Pulchowk", "Hariharbhawan", "Kupondole", "Thapathali", "Maitighar", "Singha Durbar", "Bhadrakali", "Sundhara",  "Ratnapark")
        val cityArray3 = arrayOf<String>("Gaushala","Gopi Krishna", "Sukhedhara", "Dhumbarahi", "Chappal Karkhana", "Narayan Gopal Chowk", "Basundhara", "Samakhushi","Gongabu",
            "Machhapokhari", "Balaju", "Banasthali", "Dhungedhara", "Sano Bharyang", "Thulo Bharyang", "Swyambhu", "Kalanki", "Khasibazaar", "Balkhu", "Sanepa", "Ekantakuna", "Gwarko", "Koteshwor", "Tinkune", "Sinamangal",
            "Airport", "Pinglasthan")
        val cityArray = cityArray12 + cityArray3
        val departureList = arrayListOf<String>()
        departureList.addAll(cityArray)
        val destinationList = arrayListOf<String>()
        destinationList.addAll(cityArray)
        var departureFrom = String()
        departureFrom = departureList.get(0);
        var destinationTo = String()
        destinationTo = destinationList.get(0);

        var idToLoad = String()

        mainAct.createLoadingDialog()

        departureSpinner.setItems(departureList)
        destinationSpinner.setItems(destinationList)

        mainAct.dismissLoadingDialog()


        // Departure selected
        departureSpinner.setOnItemSelectedListener { view, position, id, item ->
            departureFrom = item.toString()
        }

        // When a destination is selected, all is needed is to save the ID of the corresponding trip
        destinationSpinner.setOnItemSelectedListener { view, position, id, item ->
            destinationTo = item.toString()
        }

        // Search clicked
        searchButton.setOnClickListener {
            if (departureFrom.equals(destinationTo)) {
                MaterialAlertDialogBuilder(requireContext())
                    .setTitle("You are already there!")
                    .setMessage("Please input a destination that is not the same as your departure point")
                    .setNeutralButton(getString(R.string.cancel)) { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
            } else {
                val bundle = Bundle()
                bundle.putString("departureFrom", departureFrom)
                bundle.putString("destinationTo", destinationTo)
                val detailsFragment = DetailsFragment(idToLoad)
                detailsFragment.arguments = bundle
                mainAct.replaceCurrentFragment(detailsFragment, false)

                // Clears the spinners to fix some retarded problem
                departureSpinner.setItems(String())
                destinationSpinner.setItems(String())
            }
        }

        displayHelpButton.setOnClickListener {

            MaterialAlertDialogBuilder(requireContext())
                .setTitle(getString(R.string.help_title))
                .setMessage(getString(R.string.help_description))
                .setPositiveButton(getString(R.string._return), null)
                .show()
        }

    }
}