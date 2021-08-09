package com.example.webkeyz_task.ui.detials

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.example.webkeyz_task.R
import com.example.webkeyz_task.databinding.FragmentImageBinding
import com.example.webkeyz_task.util.getLoading



class ImageFragment : Fragment() {

    lateinit var binding : FragmentImageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentImageBinding.inflate(layoutInflater)




        arguments?.let {
            Glide.with(requireContext())
                .load(it.get("image") )
                .placeholder(getLoading(requireContext()))
                .error(AppCompatResources.getDrawable(requireContext(),R.drawable.ic_failed))
                .into(binding.iv)
        }


        binding.iv.setOnClickListener {
            Navigation.findNavController(this.requireView()).popBackStack()
        }


        return binding.root
    }


}