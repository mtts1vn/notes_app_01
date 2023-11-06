package com.appxstudios.drawerapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.appxstudios.drawerapp.R
import com.appxstudios.drawerapp.datasource.NotesDatasource
import com.appxstudios.drawerapp.model.Note

class NotesListAdapter : RecyclerView.Adapter<NotesListAdapter.ViewHolder>() {

    private val notes: List<Note> = NotesDatasource.getNotes()
    var itemClickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note: Note = notes[position]

        holder.itemView.setOnClickListener {
            itemClickListener?.onItemClick(note)
        }

        holder.bind(note)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(note: Note) {
            itemView.findViewById<TextView>(R.id.txt_preview_content).text = note.content
            itemView.findViewById<TextView>(R.id.txt_note_title).text = note.title
            itemView.findViewById<TextView>(R.id.txt_note_date).text = note.date
        }
    }

    interface ItemClickListener {
        fun onItemClick(note: Note)
    }
}

