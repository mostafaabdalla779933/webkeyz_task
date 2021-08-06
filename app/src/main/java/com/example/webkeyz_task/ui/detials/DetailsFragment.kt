package com.example.webkeyz_task.ui.detials

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.example.webkeyz_task.R
import com.example.webkeyz_task.databinding.FragmentDetialsBinding
import com.example.webkeyz_task.model.ArticleModel
import com.example.webkeyz_task.ui.MainActivity
import com.example.webkeyz_task.util.getLoading

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.news_layout.view.*

@AndroidEntryPoint
class DetailsFragment : Fragment() {


    lateinit var binding: FragmentDetialsBinding

    lateinit var article :ArticleModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetialsBinding.inflate(layoutInflater)


        arguments?.let { bundele ->

            (bundele.get("article") as ArticleModel).let { art ->
                article = art

                binding.apply {
                    tvContent.text = article.content
                    tvTitle.text = article.title
                    tvDate.text = article.publishedAt

                    Glide.with(requireContext())
                        .load(article.urlToImage)
                        .placeholder(getLoading(requireContext()))
                        .error(requireContext().getDrawable(R.drawable.ic_failed))
                        .into(binding.ivNews)
                }
            }
        }

        binding.ivNews.setOnClickListener {

            val bundle = bundleOf("image" to article.urlToImage)
            Navigation.findNavController(this.requireView()).navigate(R.id.imageFragment,bundle)

        }

        (requireActivity() as MainActivity).binding.ivShare.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, article.url)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }

        (requireActivity() as MainActivity).binding.ivBack.setOnClickListener {
            Navigation.findNavController(this.requireView()).popBackStack()
        }

        (requireActivity() as MainActivity).binding.tvTitle.text = article.title



        binding.btnReadMore.setOnClickListener {
            try {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(article.url))
                startActivity(browserIntent)
            }catch (e: Exception){
                Toast.makeText(requireContext(),"something went wrong", Toast.LENGTH_SHORT).show()
            }
        }



        return binding.root
    }


}