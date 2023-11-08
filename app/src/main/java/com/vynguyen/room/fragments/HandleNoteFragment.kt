package com.vynguyen.room.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.vynguyen.room.R
import com.vynguyen.room.databinding.FragmentHandleNoteBinding
import com.vynguyen.room.model.Note
import com.vynguyen.room.viewmodel.NoteViewModel

class HandleNoteFragment : Fragment() {

    private lateinit var viewBinding: FragmentHandleNoteBinding
    private lateinit var controller: NavController
    private var bundle: Bundle? = null

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

        viewBinding = FragmentHandleNoteBinding.inflate(inflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        controller = findNavController()

        bundle = arguments
        when (bundle?.getString("TYPE")) {
            "add" -> handleAdd()
            "search" -> handleSearch()
            "update" -> handleUpdate()
        }
    }

    private fun handleAdd() {
        viewBinding.imgHandle.setImageResource(R.drawable.ic_add)
        viewBinding.edtTitle.setText("")
        viewBinding.edtDescription.setText("")

        viewBinding.imgHandle.setOnClickListener {
            noteViewModel.insert(
                Note(
                    title = viewBinding.edtTitle.text.toString().trim(),
                    description = viewBinding.edtDescription.text.toString().trim()
                )
            )
            finish()
        }
    }

    private fun handleSearch() {
        viewBinding.imgHandle.setImageResource(R.drawable.ic_search)

        viewBinding.imgHandle.setOnClickListener {
            val title = viewBinding.edtTitle.text.toString().trim()
            val des = viewBinding.edtDescription.text.toString().trim()

            if (title.isEmpty() && des.isEmpty()) {
                Toast.makeText(context, "Please enter text", Toast.LENGTH_SHORT).show()
            } else if (title.isEmpty()) {
                noteViewModel.searchByDescription(des)
            } else if (des.isEmpty()) {
                noteViewModel.searchByTitle(title)
            } else {
                noteViewModel.searchByTitleAndDes(title, des)
            }
            finish()
        }
    }

    private fun handleUpdate() {
        viewBinding.imgHandle.setImageResource(R.drawable.ic_update)
        val note = bundle!!.getSerializable("NOTE") as Note
        viewBinding.edtTitle.setText(note.title)
        viewBinding.edtDescription.setText(note.description)

        viewBinding.imgHandle.setOnClickListener {
            note.title = viewBinding.edtTitle.text.toString().trim()
            note.description = viewBinding.edtDescription.text.toString().trim()
            noteViewModel.update(note)
            finish()
        }
    }

    private fun finish() {
        controller.navigate(R.id.homeFragment)
        controller.popBackStack(R.id.handleNoteFragment, true)
    }
}