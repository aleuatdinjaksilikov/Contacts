package com.example.contacts.fragments

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.contacts.R
import com.example.contacts.adapter.RvAdapter
import com.example.contacts.databinding.FragmentInfoBinding

class InfoFragment:Fragment(R.layout.fragment_info){
    private lateinit var binding: FragmentInfoBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentInfoBinding.bind(view)

        val arguments = requireArguments()

        val name = arguments.getString("name").toString()
        val phoneNumber = arguments.getString("phoneNumber").toString()
        val bundle = arguments
        val image = bundle.getParcelable<Bitmap>("image")

        binding.tvName.text = name
        binding.tvPhoneNumber.text = phoneNumber

        if (image!=null){
            binding.infoImageView.setImageBitmap(image)
        }else{
            binding.infoImageView.setImageResource(R.drawable.ic_user2)
        }

        binding.btnCall.setOnClickListener {
            val intent = Intent(Intent.ACTION_CALL)
            intent.data = Uri.parse("tel: $phoneNumber")
            startActivity(intent)
        }

        binding.btnBack.setOnClickListener {
            requireFragmentManager().popBackStack()
        }

    }


}