package com.jasleen.fooddeliveryapp.Activities.Fragments

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.jasleen.fooddeliveryapp.Activities.Adapter.HomeRecyclerAdapter
import com.jasleen.fooddeliveryapp.Activities.model.Restaurants
import com.jasleen.fooddeliveryapp.Activities.util.ConnectionManager
import com.jasleen.fooddeliveryapp.R
import org.json.JSONException

// add pictures in front of menu items
//implement logout function in navigation drawer
//implement options on action bar
//customize user info on navigation drawer

class HomeFragment : Fragment() {

    lateinit var homeRecyclerView: RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var homeRecyclerAdapter:HomeRecyclerAdapter
    var restaurantInfoList = arrayListOf<Restaurants>()

    lateinit var progressLayout: RelativeLayout
    lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        homeRecyclerView = view.findViewById(R.id.homeRecyclerView)
        layoutManager = LinearLayoutManager(activity)

        progressLayout = view.findViewById(R.id.progressLayout)
        progressBar = view.findViewById(R.id.progressBar)
        progressLayout.visibility = View.VISIBLE

        val queue = Volley.newRequestQueue(activity as Context)
        val url = "http://13.235.250.119/v2/restaurants/fetch_result/"

        if(ConnectionManager().checkConnectivity(activity as Context)) {
            val jsonObjectRequest = object : JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                Response.Listener {
                    // response handling
                try{
                    progressLayout.visibility = View.GONE
                    val res = it.getJSONObject("data")
                    val success = it.getBoolean("success")
                    if (success) {

                        val data = res.getJSONArray("data")
                        for (i in 0 until data.length()) {
                            val restaurantJsonObject = data.getJSONObject(i)
                            val restaurantObject = Restaurants(
                                restaurantJsonObject.getString("id"),
                                restaurantJsonObject.getString("name"),
                                restaurantJsonObject.getString("rating"),
                                restaurantJsonObject.getString("cost_for_one"),
                                restaurantJsonObject.getString("image_url")
                            )
                            restaurantInfoList.add(restaurantObject)
                        }

                        homeRecyclerAdapter =
                            HomeRecyclerAdapter(activity as Context, restaurantInfoList)
                        homeRecyclerView.adapter = homeRecyclerAdapter
                        homeRecyclerView.layoutManager = layoutManager
//                        homeRecyclerView.addItemDecoration(
//                            DividerItemDecoration(
//                                homeRecyclerView.context,
//                                (layoutManager as LinearLayoutManager).orientation
//                            )
//                        )
                    }else{
                        Toast.makeText(activity as Context, "Some Error Occured", Toast.LENGTH_SHORT).show()
                    }
                }
                    catch(e: JSONException){
                        Toast.makeText(activity as Context, "Some error occurred", Toast.LENGTH_LONG).show()
                    }
                },
                Response.ErrorListener {
                    // error handling
                    if(activity != null) {
                        Toast.makeText(
                            activity as Context,
                            "Volley error occurred",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            ) {
                override fun getHeaders(): MutableMap<String, String> {
                    val headers = HashMap<String, String>()
                    headers["Content-type"] = "application/json"
                    headers["token"] = "046a59f668ea81"
                    return headers
                }
            }
            queue.add(jsonObjectRequest)
        }else{
            val dialog = AlertDialog.Builder(activity as Context)
            dialog.setTitle("Failure")
            dialog.setMessage("Internet Connection Not Found")
            dialog.setPositiveButton("Open Settings"){
                    text, listener ->
                val settingsIntent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                startActivity(settingsIntent)
                activity?.finish()
            }
            dialog.setNegativeButton("Cancel"){
                    text, listener ->
                ActivityCompat.finishAffinity(activity as Activity)
            }
            dialog.create()
            dialog.show()
        }



        return view
    }
}