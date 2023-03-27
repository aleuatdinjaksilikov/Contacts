package com.example.contacts.model

import android.graphics.Bitmap

data class Contact(
    val name: String,
    val phoneNumber: String,
    var image: Bitmap?
)