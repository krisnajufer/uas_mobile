package com.example.uas_mobile.crud

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.uas_mobile.R
import com.example.uas_mobile.home.HomeFragment
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class CrudFragment : Fragment(), View.OnClickListener {
    var db = Firebase.firestore
    lateinit var edNBarang: EditText
    lateinit var edHBarang: EditText
    lateinit var edJBarang: EditText

    lateinit var btnSave: Button
    lateinit var btnBack: Button
    lateinit var btnUpdate: Button
    lateinit var btnDelete: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_crud, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnBack = view.findViewById(R.id.btnBack)
        edNBarang = view.findViewById(R.id.edNBarang)
        edHBarang = view.findViewById(R.id.edHbarang)
        edJBarang = view.findViewById(R.id.edJbarang)
        btnSave = view.findViewById(R.id.btnSave)
        btnUpdate = view.findViewById(R.id.btnUpdate)
        btnDelete = view.findViewById(R.id.btnDelete)

        if (arguments != null) {
            val nama = arguments?.getString("nama")
            val harga = arguments?.getString("harga")
            val jumlah = arguments?.getString("jumlah")
            edNBarang.setText(nama.toString())
            edHBarang.setText(harga.toString())
            edJBarang.setText(jumlah.toString())
        }

        btnBack.setOnClickListener(this)
        btnSave.setOnClickListener(this)
        btnUpdate.setOnClickListener(this)
        btnDelete.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnBack -> {
                replaceFragment(HomeFragment())
            }

            R.id.btnSave -> {
                insert()
            }

            R.id.btnUpdate -> {
                update()
            }

            R.id.btnDelete -> {
                delete()
            }
        }
    }

    fun insert() {
        val Nbarang = edNBarang.text.toString()
        val Hbarang = edHBarang.text.toString().toInt()
        val Jbarang = edJBarang.text.toString().toInt()
        val Grand = Hbarang * Jbarang
        val data = hashMapOf(
            "nama_barang" to Nbarang,
            "harga_barang" to Hbarang,
            "jumlah_barang" to Jbarang,
            "grand" to Grand
        )

        db.collection("data").add(data)
            .addOnSuccessListener {
                Toast.makeText(context, "OK", Toast.LENGTH_LONG).show()
                replaceFragment(HomeFragment())
            }
    }

    fun update() {
        db = FirebaseFirestore.getInstance()
        val Nbarang = edNBarang.text.toString()
        val Hbarang = edHBarang.text.toString().toInt()
        val Jbarang = edJBarang.text.toString().toInt()
        val Grand = Hbarang * Jbarang
        val updateData = hashMapOf<String, Any>(
            "nama_barang" to Nbarang,
            "harga_barang" to Hbarang,
            "jumlah_barang" to Jbarang,
            "grand" to Grand
        )

        db.collection("data").document(arguments?.getString("id").toString()).update(updateData)
            .addOnCompleteListener {
                Toast.makeText(context, "OK", Toast.LENGTH_LONG).show()
                replaceFragment(HomeFragment())
            }
    }

    fun delete() {
        db = FirebaseFirestore.getInstance()
        db.collection("data").document(arguments?.getString("id").toString()).delete()
            .addOnCompleteListener {
                Toast.makeText(context, "OK", Toast.LENGTH_LONG).show()
                replaceFragment(HomeFragment())
            }
    }

    fun replaceFragment(fragment: Fragment) {
        val fragmentManager = parentFragmentManager
        fragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .addToBackStack(null)
            .commit()
    }

    companion object {
    }
}