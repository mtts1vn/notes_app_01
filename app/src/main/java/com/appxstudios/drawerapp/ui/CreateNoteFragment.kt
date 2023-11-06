package com.appxstudios.drawerapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.appxstudios.drawerapp.R
import com.appxstudios.drawerapp.databinding.FragmentCreateNoteBinding
import com.appxstudios.drawerapp.datasource.NotesDatasource
import com.appxstudios.drawerapp.model.Note
import java.util.Calendar

class CreateNoteFragment : Fragment() {
    private lateinit var binding: FragmentCreateNoteBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateNoteBinding.inflate(inflater, container, false);

        binding.btnCreateNote.setOnClickListener {
            if (!binding.edtxtContentNote.text.isNullOrBlank() && !binding.edtxtTitleNote.text.isNullOrBlank()) {
                Toast.makeText(binding.root.context, "A nota foi criada!", Toast.LENGTH_SHORT).show();
                createNote(
                    binding.edtxtTitleNote.text.toString(),
                    binding.edtxtContentNote.text.toString()
                );
            } else {
                Toast.makeText(binding.root.context, "Preencha todos!", Toast.LENGTH_SHORT).show();
            }
        }

        binding.btnHome.setOnClickListener {
            val navController = activity?.findNavController(R.id.nav_host_fragment_content_main)
            navController?.navigate(R.id.nav_contato)
        }

        return binding.root;
    }


    fun createNote(title: String, content: String) {
        val calendar = Calendar.getInstance()
        calendar.apply {
            val dia = get(Calendar.DAY_OF_MONTH)
            val mes = get(Calendar.MONTH) + 1
            val ano = get(Calendar.YEAR)
            val data = "$dia/$mes/$ano"
            NotesDatasource.addNote(
                Note(
                    title,
                    content,
                    data
                )
            )
        }
    }
}