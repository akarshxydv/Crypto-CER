package com.example.cryptocer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptocer.domain.model.CombinedCoinInfo
import com.squareup.picasso.Picasso

class CoinAdapter(private var coinList:List<CombinedCoinInfo>):RecyclerView.Adapter<CoinAdapter.viewHolder>() {

    inner class viewHolder(view: View):RecyclerView.ViewHolder(view){

        val coinImage: ImageView = view.findViewById(R.id.imgCoinImage)
        val coinName: TextView = view.findViewById(R.id.txtCoinName)
        val coinRate:TextView=view.findViewById(R.id.txtCoinRate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.coin_recycler_view,parent,false)
        return viewHolder(view)

    }

    override fun getItemCount(): Int {
        return coinList.size
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val coinDetails=coinList[position]
        holder.coinName.text=coinDetails.coin.name_full
        holder.coinRate.text=coinDetails.rate
        Picasso.get().load(coinDetails.coin.icon_url).into(holder.coinImage)


    }
    fun update(recoinList:List<CombinedCoinInfo>){
        this.coinList=recoinList

    }
}