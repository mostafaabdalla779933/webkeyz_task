package com.example.webkeyz_task.ui.listing

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.webkeyz_task.Data.local.SharedPref
import com.example.webkeyz_task.Data.remote.NetworkManager
import com.example.webkeyz_task.R
import com.example.webkeyz_task.databinding.FragmentListingBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject


@AndroidEntryPoint
class ListingFragment : Fragment() {


    @Inject
    lateinit var shared: SharedPref

    lateinit var binding : FragmentListingBinding
    lateinit var articleAdapte: ArticleAdapter
    lateinit var viewModel: NewsViewModel
    var post  = 0
    var page = 1
    val size = NetworkManager.size
    val maxSize = NetworkManager.maxSize



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListingBinding.inflate(layoutInflater)

        initViewModel()
        initRecycleView()
        observeOnLiveData()

        if(page == 1) {
            viewModel.fetchPosts(1)
        }

        return binding.root
    }

    private fun initViewModel(){
        viewModel = ViewModelProvider(this)[NewsViewModel::class.java]
    }



    // initialize recyclerview and make it support pagination
    private fun initRecycleView() {

        articleAdapte = ArticleAdapter{ article ->
            val bundle = bundleOf("article" to article)
            Navigation.findNavController(this.requireView()).navigate(R.id.detailsFragment,bundle)
        }

        binding.recyclerView.adapter = articleAdapte

        if (viewModel.postsList.value.isNullOrEmpty()){
            shared.putPage(1)
        }

        page = shared.getPage()


        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                post = (Objects.requireNonNull(recyclerView.layoutManager) as GridLayoutManager).findLastVisibleItemPosition()

                if (post >= size * page -1 && post < maxSize -1 ) {
                    binding.progressBar.visibility = View.VISIBLE
                    page ++
                    viewModel.fetchPosts(page)
                }
            }
        })
    }


    private fun observeOnLiveData(){
        viewModel.postsList.observe(viewLifecycleOwner){posts ->
            articleAdapte.submitList(posts)
            binding.progressBar.visibility = View.GONE
        }


        viewModel.error.observe(viewLifecycleOwner){ offline ->
            if(offline){
                binding.progressBar .visibility = View.GONE
                Snackbar.make(binding.root ,"connection failed", Snackbar.LENGTH_INDEFINITE )
                    .setAction("reload") {
                        binding.progressBar.visibility = View.VISIBLE
                        viewModel.fetchPosts(page)
                    }.show()
            }
        }

        viewModel.stateLiveData.observe(viewLifecycleOwner){ message ->
            if (message != null){
                showToast(message)
            }
        }

    }

    private fun showToast(message:String) {
        Toast.makeText(requireContext(),message, Toast.LENGTH_SHORT).show()
    }
}