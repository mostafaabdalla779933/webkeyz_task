package com.example.webkeyz_task.ui.listing

import android.os.Bundle
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
import com.example.webkeyz_task.Data.remote.NetworkManager
import com.example.webkeyz_task.R
import com.example.webkeyz_task.databinding.FragmentListingBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class ListingFragment : Fragment() {
    lateinit var binding : FragmentListingBinding
    lateinit var articleAdapte: ArticleAdapter
    lateinit var viewModel: NewsViewModel
    var post  = 0
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
        if(viewModel.page == 0) {
            viewModel.fetchPosts()
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
            Navigation.findNavController(this.requireView()).navigate(R.id.action_list_to_detials,bundle)
        }

        binding.recyclerView.adapter = articleAdapte

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                post = (Objects.requireNonNull(recyclerView.layoutManager) as GridLayoutManager).findLastVisibleItemPosition()
                if (post >= size * viewModel.page -1 && post < maxSize -1 ) {
                    binding.progressBar.visibility = View.VISIBLE
                    viewModel.fetchPosts()
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
                Snackbar.make(binding.root ,getString(R.string.connection_failed), Snackbar.LENGTH_INDEFINITE )
                    .setAction(getString(R.string.reload)) {
                        binding.progressBar.visibility = View.VISIBLE
                        viewModel.fetchPosts()
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