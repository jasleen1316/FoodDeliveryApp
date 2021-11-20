package com.jasleen.fooddeliveryapp.Activities.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.jasleen.fooddeliveryapp.Activities.model.Restaurants
import com.jasleen.fooddeliveryapp.R
import com.squareup.picasso.Picasso

class HomeRecyclerAdapter(val context: Context, var itemList: ArrayList<Restaurants>): RecyclerView.Adapter<HomeRecyclerAdapter.HomeViewHolder>() {

    class HomeViewHolder(view: View): RecyclerView.ViewHolder(view){
        val txtRestaurantName: TextView = view.findViewById(R.id.txtRestaurantName)
        val txtRestaurantCost: TextView = view.findViewById(R.id.txtRestaurantCost)
        val txtRestaurantRating: TextView = view.findViewById(R.id.txtRestaurantRating)
        val imgRestaurant: ImageView = view.findViewById(R.id.imgRestaurant)
        val llRestaurantContent: LinearLayout = view.findViewById(R.id.llRestaurantContent)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_home_single_row, parent, false)
        return HomeViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val restaurant = itemList[position]
        holder.txtRestaurantName.text = restaurant.restaurantName
        holder.txtRestaurantCost.text = restaurant.restaurantCost
        holder.txtRestaurantRating.text = restaurant.restaurantRating
        Picasso.get().load(restaurant.restaurantImage).error(R.drawable.ic_restaurant).into(holder.imgRestaurant)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

}