package com.example.uas_mobile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.uas_mobile.crud.CrudFragment

class adapterClass(var listData: ArrayList<dataClass>) :
    RecyclerView.Adapter<adapterClass.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.tvNBarang)
        val harga = itemView.findViewById<TextView>(R.id.tvHBarang)
        val jumlah = itemView.findViewById<TextView>(R.id.tvJBarang)
        val grand = itemView.findViewById<TextView>(R.id.tvGbarang)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.items, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = listData[position].nama_barang
        holder.harga.text = listData[position].harga_barang.toString()
        holder.jumlah.text = listData[position].jumlah_barang.toString()
        holder.grand.text = listData[position].grand.toString()
        val id = listData[position].documentId



        holder.itemView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val activity = v!!.context as AppCompatActivity
                val crudFragment = CrudFragment()
                val bundle = Bundle()

                bundle.putString("nama", holder.name.text.toString())
                bundle.putString("id", id)
                bundle.putString("harga", holder.harga.text.toString())
                bundle.putString("jumlah", holder.jumlah.text.toString())

                crudFragment.arguments = bundle
                activity.supportFragmentManager.beginTransaction().apply {
                    replace(R.id.container, crudFragment, CrudFragment::class.java.simpleName)
                    addToBackStack(null)
                    commit()
                }
            }
        })
    }

}