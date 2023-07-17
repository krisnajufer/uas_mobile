package com.example.uas_mobile.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.uas_mobile.R
import com.example.uas_mobile.adapterClass
import com.example.uas_mobile.crud.CrudFragment
import com.example.uas_mobile.dataClass
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase


class HomeFragment : Fragment() {
    var db = Firebase.firestore
    lateinit var recyclerView: RecyclerView
    lateinit var fab: FloatingActionButton
    var listData: ArrayList<dataClass> = ArrayList()
    lateinit var adapter: adapterClass
    lateinit var root: View
    lateinit var tvGrandTotal: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        root = inflater.inflate(R.layout.fragment_home, container, false)
        return root
    }

    companion object {
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recyclerView)
        fab = view.findViewById(R.id.floatingActionButton)
        tvGrandTotal = view.findViewById(R.id.tvGrandT)

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)

        display()
        hitungGrandTotal()


        fab.setOnClickListener {
            val fragmentMngr = parentFragmentManager
            fragmentMngr.beginTransaction()
                .replace(R.id.container, CrudFragment())
                .addToBackStack(null).commit()
        }
    }

    fun display() {
        db = FirebaseFirestore.getInstance()

        db.collection("data").get().addOnSuccessListener {
            listData.clear()
            if (!it.isEmpty) {
                for (data in it.documents) {
                    val dataCls: dataClass? = data.toObject(dataClass::class.java)
                    if (dataCls != null) {
                        listData.add(dataCls)
                    }
                }
                adapter = adapterClass(listData)
                recyclerView.adapter = adapter
                adapter.notifyDataSetChanged()
            }
        }
            .addOnFailureListener {
                Toast.makeText(context, "Failed", Toast.LENGTH_LONG).show()
            }
    }

    fun hitungGrandTotal(){

        db = FirebaseFirestore.getInstance()

        db.collection("data").get().addOnSuccessListener {

            if (!it.isEmpty) {
                var total:Int = 0
                for (data in it.documents) {
                    total += data.get("grand").toString().toInt()
                }
                Log.d("total", total.toString())
                tvGrandTotal.text = total.toString()
            }
        }
//        return total
            .addOnFailureListener {
                Toast.makeText(context, "Failed", Toast.LENGTH_LONG).show()
            }

    }

}