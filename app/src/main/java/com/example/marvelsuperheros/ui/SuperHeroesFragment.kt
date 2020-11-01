package com.example.marvelsuperheros.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ernestoyaquello.dragdropswiperecyclerview.DragDropSwipeRecyclerView
import com.ernestoyaquello.dragdropswiperecyclerview.listener.OnItemSwipeListener
import com.example.domain.model.SuperHeroModel
import com.example.marvelsuperheros.DependencyInjector
import com.example.marvelsuperheros.adapter.ListAdapter
import com.example.marvelsuperheros.MarvelViewModelFactory
import com.example.marvelsuperheros.R
import com.example.marvelsuperheros.databinding.FragmentSuperHeroesBinding
import com.example.marvelsuperheros.ext.displayShortToast

class SuperHeroesFragment: BaseFragment() {
    private lateinit var viewModel: SuperHeroesViewModel
    private lateinit var adapter: ListAdapter
    private lateinit var binding: FragmentSuperHeroesBinding

    companion object{
        fun createInstance() = SuperHeroesFragment()
        private const val REQ_CODE_ADD_HERO = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this, MarvelViewModelFactory {
            SuperHeroesViewModel(DependencyInjector.getAllHeroUseCase(), DependencyInjector.getDeleteHeroUseCase())
        }).get(SuperHeroesViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_super_heroes, container, false)

        binding.viewModel = viewModel

        setupList()

        return binding.root
    }

    private fun setupList() {
        adapter = ListAdapter(/*items*/)
        binding.list.layoutManager = LinearLayoutManager(requireContext())
        binding.list.adapter = adapter
        binding.list.orientation = DragDropSwipeRecyclerView.ListOrientation.VERTICAL_LIST_WITH_VERTICAL_DRAGGING
        binding.list.disableSwipeDirection(DragDropSwipeRecyclerView.ListOrientation.DirectionFlag.RIGHT)

        binding.list.swipeListener = object: OnItemSwipeListener<SuperHeroModel>{
            override fun onItemSwiped(position: Int, direction: OnItemSwipeListener.SwipeDirection, item: SuperHeroModel): Boolean {
                viewModel.removeSuperHero(item, position)
                // Returning true so that the library does not remove the item from list on its own. We will remove the item once it is removed from the db
                return true
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.viewStateObservable.observe(viewLifecycleOwner, {
            if(it != null)
                render(it)
        })
        viewModel.viewActionsObservable.observe(viewLifecycleOwner, {
            when(it){
                SuperHeroesViewModel.ViewActions.AddHero -> {
                    val frag = AddSuperHeroFragment.createInstance()
                    frag.setTargetFragment(this, REQ_CODE_ADD_HERO)
                    val transaction = requireActivity().supportFragmentManager.beginTransaction()
                    transaction.replace(R.id.container, frag, "HERO_LIST")
                    transaction.addToBackStack("HERO_LIST")
                    transaction.commit()
                }
            }
        })
    }

    private fun render(viewState: SuperHeroesViewModel.ViewState) {
        when(viewState){
            is SuperHeroesViewModel.ViewState.Loading -> {
                if(viewState.isLoading)
                    showLoading()
                else
                    hideLoading()
            }
            is SuperHeroesViewModel.ViewState.SuperHeroLoaded -> adapter.dataSet = viewState.data
            is SuperHeroesViewModel.ViewState.HeroDeleted -> {
                displayShortToast(R.string.hero_deleted)
                adapter.removeItem(viewState.position)
            }
            is SuperHeroesViewModel.ViewState.Error -> displayShortToast(viewState.message)
        }
    }

    override fun showLoading() {
        binding.includedLoadingView.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        binding.includedLoadingView.visibility = View.GONE
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQ_CODE_ADD_HERO && resultCode == Activity.RESULT_OK){
            displayShortToast(R.string.hero_added)
            viewModel.fetchHeroes()
        }
    }
}