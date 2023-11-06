package com.appxstudios.drawerapp.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.appxstudios.drawerapp.R
import com.appxstudios.drawerapp.adapter.NotesListAdapter
import com.appxstudios.drawerapp.databinding.FragmentHomeBinding
import com.appxstudios.drawerapp.datasource.NotesDatasource
import com.appxstudios.drawerapp.model.Note


class HomeFragment : Fragment(), NotesListAdapter.ItemClickListener {
    private lateinit var binding: FragmentHomeBinding;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        binding.txtWelcome.text = defineTextWelcome();

        binding.btnCreateNote.setOnClickListener {
            val navController = requireActivity().findNavController(R.id.nav_host_fragment_content_main)
            navController.navigate(R.id.nav_create)
        }

        binding.rcNotesRecycler.layoutManager =
            StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        val adapter = NotesListAdapter()
        adapter.itemClickListener = this
        binding.rcNotesRecycler.adapter = adapter

        return binding.root;
    }

    private fun defineTextWelcome(): String {
        return "${NotesDatasource.getNotes().size} Notas"
    }

    override fun onItemClick(note: Note) {
        val navController = requireActivity().findNavController(R.id.nav_host_fragment_content_main)
        val bundle: Bundle = Bundle()
        bundle.putSerializable("nota", note)

        navController.navigate(R.id.nav_create, bundle)
    }
}