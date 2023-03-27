package com.example.contacts.fragments

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.contacts.MainActivity
import com.example.contacts.R
import com.example.contacts.adapter.RvAdapter
import com.example.contacts.databinding.FragmentHomeBinding
import com.example.contacts.model.Contact


class HomeFragment:Fragment(R.layout.fragment_home) {
    private lateinit var binding: FragmentHomeBinding
    private var adapter = RvAdapter()
    private var contactsList = mutableListOf<Contact>()

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){ isGranted ->
        if (isGranted){
            Toast.makeText(requireContext(),"Is Granted",Toast.LENGTH_SHORT)
        }else{

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        init()

    }
    private fun init(){
        getAllContacts2()
        binding.rvHome.adapter = adapter
        adapter.list = contactsList

        adapter.setOnUserClickListener {
            (requireActivity() as MainActivity).selectedFragment(newInstance(it.name,it.phoneNumber,it.image))
        }
    }

  /* private fun getAllContacts(){
       val contentResolver:ContentResolver = (requireActivity() as MainActivity).contentResolver
       val cursor: Cursor? =
           contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null)

       if (cursor!!.moveToFirst()) {
           do {
               // Get the contact ID
               val id = cursor.getColumnIndex(ContactsContract.Contacts._ID)
                   .let { cursor.getString(it) }

               // Get the contact name
               val name =
                   cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
                       .let { cursor.getString(it) }




               // Get the contact phone number
               val phoneCursor: Cursor? = contentResolver.query(
                   ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                   ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", arrayOf(id), null
               )
               var phoneNumber = ""
               if (phoneCursor != null) {
                   if (phoneCursor.moveToFirst()) {
                       if (phoneCursor != null) {
                           phoneNumber =
                               phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                                   .let { phoneCursor.getString(it) }.toString()
                       }
                   }
               }
               phoneCursor?.close()

               contactsList.add(Contact(name,phoneNumber,null))

           } while (cursor.moveToNext())
       }

       cursor.close()
   } */

    @SuppressLint("Range")
    private fun getAllContacts2(){
        val contact = context?.contentResolver?.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            null,
            null,
            null
        )

        if (contact != null) {
            while (contact.moveToNext()) {
                val name =
                    contact.getString(contact.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))

                val number =
                    contact.getString(contact.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))

                val profile =
                    contact.getString(contact.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI))

                var cont = Contact(name,number,null)

                if (profile != null) {
                    val bitmapImage =
                        MediaStore.Images.Media.getBitmap(context?.contentResolver, Uri.parse(profile))
                    cont.image = bitmapImage
                }

                contactsList.add(cont)
            }
        }
        contact?.close()
    }


    companion object {
        fun newInstance(name: String, phoneNumber: String,image:Bitmap?): InfoFragment {
            val fragment = InfoFragment()
            val bundle = Bundle()
            bundle.putString("name", name)
            bundle.putString("phoneNumber", phoneNumber)
            bundle.putParcelable("image",image)
            fragment.arguments = bundle
            return fragment
        }
    }

}

