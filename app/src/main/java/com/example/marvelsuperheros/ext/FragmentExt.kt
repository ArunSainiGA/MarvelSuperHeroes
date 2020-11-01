package com.example.marvelsuperheros.ext

import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

inline fun Fragment.displayShortToast(text: String?){
    if(!text.isNullOrBlank())
        Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
}

inline fun Fragment.displayShortToast(resId: Int){
    Toast.makeText(requireContext(), getString(resId), Toast.LENGTH_SHORT).show()
}