package com.example.hpindonesia

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_hp.view.*

class HpAdapter(var list: List<Hp>, val sharedPref: SharedPref) : RecyclerView.Adapter<HpAdapter.ViewHolder>() {
    var onItemClickListener: ((Hp) -> Unit)? = null
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(hp: Hp){
            with(itemView){
                Glide.with(this).load(hp.gambar).into(itemImage)
                itemNama.text = hp.nama
                itemLatin.text = hp.latin
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView= LayoutInflater.from(parent.context).inflate(R.layout.item_hp, parent, false)
        return  ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val hp = list.get(position)
        holder.bind(hp)
        holder.itemView.setOnClickListener{
            onItemClickListener?.invoke(hp)
        }
        holder.itemView.itemLatin.visibility = if (sharedPref.latin) View.VISIBLE else View.GONE
    }
}

