package com.appxstudios.drawerapp.ui

import android.os.Bundle
import android.text.Editable
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

class NoteFragment : Fragment() {
    private lateinit var binding: FragmentCreateNoteBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateNoteBinding.inflate(inflater, container, false);
        val note: Note? = arguments?.getSerializable("nota") as Note?;

        if (note != null) {
            binding.edtxtTitleNote.text = Editable.Factory.getInstance().newEditable(note.title);
            binding.edtxtContentNote.text =
                Editable.Factory.getInstance().newEditable(note.content);
        }

        binding.btnCreateNote.setOnClickListener {
            if (!binding.edtxtContentNote.text.isNullOrBlank() && !binding.edtxtTitleNote.text.isNullOrBlank()) {
                when (note) {
                    null -> saveNote(null, true);
                    else -> saveNote(note, false);
                }
            } else {
                Toast.makeText(binding.root.context, "Preencha todos!", Toast.LENGTH_SHORT).show();
            }
        }

        binding.btnHome.setOnClickListener {
            val navController = activity?.findNavController(R.id.nav_host_fragment_content_main)
            navController?.navigate(R.id.nav_home)
        }

        return binding.root;
    }

    private fun saveNote(note: Note?, isNewNote: Boolean) {
        val title = binding.edtxtTitleNote.text.toString()
        val content = binding.edtxtContentNote.text.toString()

        when {
            isNewNote -> {
                showToast("A nota foi criada!")
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
            note != null -> {
                showToast("A nota foi salva!")
                val index = note?.let { NotesDatasource.getIndex(it) }
                val newNote = note.copy(title = title, content = content)
                index?.let { NotesDatasource.replace(newNote, it) }
            }
            else -> {
                showToast("Erro ao salvar a nota!")
            }
        }
    }


    private fun showToast(text: String) {
        Toast.makeText(binding.root.context, text, Toast.LENGTH_SHORT).show();
    }
}