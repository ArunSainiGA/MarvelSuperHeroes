package com.example.marvelsuperheros.ui

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.marvelsuperheros.DependencyInjector
import com.example.marvelsuperheros.MarvelViewModelFactory
import com.example.marvelsuperheros.R
import com.example.marvelsuperheros.databinding.FragmentAddHeroBinding
import com.example.marvelsuperheros.ext.displayShortToast

class AddSuperHeroFragment: BaseFragment() {
    private lateinit var viewModel: AddSuperHeroViewModel
    private lateinit var binding: FragmentAddHeroBinding

    companion object{
        fun createInstance() = AddSuperHeroFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this, MarvelViewModelFactory {
            AddSuperHeroViewModel(DependencyInjector.getAddHeroUseCase())
        }).get(AddSuperHeroViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_add_hero, container, false)

        binding.viewModel = viewModel

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setTextChangeListeners()

        viewModel.viewStateObservable.observe(viewLifecycleOwner, {
            if(it != null)
                render(it)
        })
    }

    private fun setTextChangeListeners() {
        binding.nameET.doOnTextChanged { text, start, before, count ->
            binding.nameTIL.error = null
        }

        binding.profilePictureET.doOnTextChanged { text, start, before, count ->
            binding.profilePictureTIL.error = null
        }

        binding.superPowersET.doOnTextChanged { text, start, before, count ->
            binding.superPowersTIL.error = null
        }
    }

    private fun render(viewState: AddSuperHeroViewModel.ViewState) {
        when(viewState){
            is AddSuperHeroViewModel.ViewState.Loading -> {
                if(viewState.isLoading)
                    showLoading()
                else
                    hideLoading()
            }
            AddSuperHeroViewModel.ViewState.SuperHeroAdded -> {
                targetFragment?.onActivityResult(targetRequestCode, Activity.RESULT_OK, null)
                requireActivity().onBackPressed()
            }
            is AddSuperHeroViewModel.ViewState.Error -> displayShortToast(viewState.message)
            is AddSuperHeroViewModel.ViewState.InvalidName -> binding.nameTIL.error = viewState.message
            is AddSuperHeroViewModel.ViewState.InvalidProfilePictureUrl -> binding.profilePictureTIL.error = viewState.message
            is AddSuperHeroViewModel.ViewState.InvalidSuperpowers -> binding.superPowersTIL.error = viewState.message
        }
    }

    override fun showLoading() {
        binding.includedLoadingView.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        binding.includedLoadingView.visibility = View.GONE
    }
}