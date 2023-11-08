package com.vynguyen.room.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.vynguyen.room.R
import com.vynguyen.room.adapter.NoteAdapter
import com.vynguyen.room.databinding.FragmentHomeBinding
import com.vynguyen.room.model.Note
import com.vynguyen.room.viewmodel.NoteViewModel

class HomeFragment : Fragment() {

    private lateinit var viewBinding: FragmentHomeBinding
    private lateinit var controller: NavController
    private val noteViewModel by lazy {
        ViewModelProvider(
            this,
            NoteViewModel.NoteViewModelFactory(requireActivity().application)
        )[NoteViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        viewBinding = FragmentHomeBinding.inflate(inflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        controller = findNavController()
        val addBundle = bundleOf("TYPE" to "add")
        viewBinding.imgAdd.setOnClickListener {
            controller.navigate(R.id.handleNoteFragment, addBundle)
        }

        val searchBundle = bundleOf("TYPE" to "search")
        viewBinding.imgSearch.setOnClickListener {
            controller.navigate(R.id.handleNoteFragment, searchBundle)
        }

        val adapter = NoteAdapter(onItemClick, onDelete)
        viewBinding.rvNotes.setHasFixedSize(true)
        viewBinding.rvNotes.layoutManager = LinearLayoutManager(context)
        viewBinding.rvNotes.adapter = adapter

        noteViewModel.getAllNote().observe(viewLifecycleOwner) {
            adapter.setNotes(it)
        }
    }

    private val onItemClick: (Note) -> Unit = {
        val bundle = Bundle()
        bundle.putString("TYPE", "update")
        bundle.putSerializable("NOTE", it)
        controller.navigate(R.id.handleNoteFragment, bundle)
    }

    private val onDelete: (Note) -> Unit = {
        noteViewModel.delete(it)
    }
}