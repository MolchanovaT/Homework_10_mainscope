package ru.netology.nmedia.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ru.netology.nmedia.databinding.FragmentImageBinding
import ru.netology.nmedia.view.load
import ru.netology.nmedia.viewmodel.PostViewModel

class ImageFragment : Fragment() {

    private val viewModel: PostViewModel by viewModels(
        ownerProducer = ::requireParentFragment,
    )

    private var fragmentBinding: FragmentImageBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentImageBinding.inflate(
            inflater,
            container,
            false
        )
        fragmentBinding = binding

        viewModel.photo.observe(viewLifecycleOwner) {
            binding.photo.load(it.uri.toString())
        }

        return binding.root
    }
}