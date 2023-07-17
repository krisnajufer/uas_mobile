package com.example.uas_mobile

import com.google.firebase.firestore.DocumentId

data class dataClass(
    @DocumentId
    val documentId: String = "",
    val nama_barang: String = "",
    val harga_barang: Int=0,
    val jumlah_barang: Int=0,
    val grand: Int=0
)

