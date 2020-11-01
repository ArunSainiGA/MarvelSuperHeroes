package com.example.marvelsuperheros.ui

import androidx.fragment.app.Fragment

abstract class BaseFragment: Fragment() {

    abstract fun showLoading()
    abstract fun hideLoading()

}