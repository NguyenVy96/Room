package com.vynguyen.room.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vynguyen.room.databinding.NoteItemBinding
import com.vynguyen.room.model.Note

class NoteAdapter(
    private val onClick: (Note) -> Unit,
    private val onDelete: (Note) -> Unit
) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    class NoteViewHolder(val noteItemBinding: NoteItemBinding) :
        RecyclerView.ViewHolder(noteItemBinding.root) {
    }

    private var notes: List<Note> = listOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setNotes(notes: List<Note>) {
        this.notes = notes
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val noteItemBinding: NoteItemBinding =
            NoteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(noteItemBinding)
    }

    override fun getItemCount(): Int = notes.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.noteItemBinding.tvTitle.text = note.title
        holder.noteItemBinding.tvDescription.text = note.description

        holder.noteItemBinding.textLayout.setOnClickListener {
            onClick(note)
        }

        holder.noteItemBinding.imgDelete.setOnClickListener {
            onDelete(note)
        }
    }
}