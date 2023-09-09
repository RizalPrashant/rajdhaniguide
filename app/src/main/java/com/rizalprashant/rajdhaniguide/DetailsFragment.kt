package com.rizalprashant.rajdhaniguide

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dz.notacompany.el_cous.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_details.*

class DetailsFragment(private val documentID : String) : Fragment(R.layout.fragment_details) {

    private val db = Firebase.firestore
    private lateinit var auth: FirebaseAuth
    private lateinit var mainAct : MainActivity

    private lateinit var source: Source
    var departureFrom: String? = ""
    var destinationTo: String? = ""


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth

        mainAct = activity as MainActivity // Reference to MainActivity

        mainAct.currentDocument = documentID // Used to delete Routes if admin is user

        departureFrom = arguments?.getString("departureFrom")
        destinationTo = arguments?.getString("destinationTo")

        currentDepartureTextView.text = departureFrom
        currentDestinationTextView.text = destinationTo

        val schedulesList = mutableListOf<ScheduleItem>()

        storeAllRoutes(departureFrom, destinationTo, schedulesList)


        // Resetting the elements of the top bar
        mainAct.topBarLayout.visibility = View.VISIBLE
        mainAct.topBarTextView2.text = getString(R.string.top_bar_schedules)
        mainAct.topBarTextView.visibility = View.GONE
        mainAct.adminButton.visibility = View.GONE
        mainAct.githubButton.visibility = View.GONE
        if (mainAct.isAdmin) mainAct.deleteRouteButton.visibility = View.VISIBLE


        // From a data source, extract route id, route number, destination, arrival


        // Function will be passed to the adapter to run stuff that can't be run inside it otherwise
        // Runs when the schedule items are clicked
        fun onScheduleClick(position : Int, textView : TextView) {
            // Depending on if the user has already reported a delay on this schedule
            // They will either add a report, or remove their report if done previously

            // Initialize stuff depending on the above
            var thisTitle = "Viable Route"
            var dialogMessage = schedulesList[position].transport + " option available from " + schedulesList[position].startPoint + " to " + schedulesList[position].endPoint + " and vice versa."

            // Ask for confirmation
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(thisTitle)
                .setMessage(dialogMessage)
                .setNeutralButton(getString(R.string.cancel)) { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }

        mainAct.createLoadingDialog()

        // Initializes the RecyclerView with the adapter
        schedulesRecyclerView.adapter = SchedulesAdapter(requireContext(), schedulesList, { position, textView -> onScheduleClick(position, textView)})
        schedulesRecyclerView.layoutManager = LinearLayoutManager(context)

        mainAct.dismissLoadingDialog()

    }

    private fun storeAllRoutes(
        departureFrom: String?,
        destinationTo: String?,
        schedulesList: MutableList<ScheduleItem>
    ) {
        val hashMap: HashMap<ArrayList<String>, HashSet<String>> = HashMap()

        // Keep all routes here
        val routeEndpoint1 = arrayListOf("Bus", "Hattiban", "Ratnapark")
        val route1 = hashSetOf("Hattiban", "Classic Chowk", "Satdobato", "Lagankhel", "Kupondole", "Ratnapark")

        val routeEndpoint2 = arrayListOf("Bus", "Lagankhel", "Ratnapark")
        val route2 = hashSetOf("Lagankhel", "Kupondole", "Ratnapark")

        // Put data into the HashMap
        hashMap[routeEndpoint1] = route1
        hashMap[routeEndpoint2] = route2

        addDirectRouteList(hashMap, schedulesList)
    }

    private fun addDirectRouteList(
        hashMap: HashMap<ArrayList<String>, HashSet<String>>,
        schedulesList: MutableList<ScheduleItem>
    ) {
        for ((key, value) in hashMap) {
            if (value.contains(departureFrom) && value.contains(destinationTo)) {
                schedulesList.add(ScheduleItem(key[0], key[1], key[2]))
            }
        }
    }
}