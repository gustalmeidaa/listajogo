package com.example.listajogos.ui.home

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.listajogos.DBHelper
import com.example.listajogos.R
import com.example.listajogos.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout using View Binding
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Initialize the Spinner
        val listaPlataformas: Spinner = binding.listaPlataformas // Use View Binding to access the Spinner

        // Create an ArrayAdapter using the string array
        val adapter = ArrayAdapter.createFromResource(
            requireContext(), // Use requireContext() to get the context
            R.array.lista_plataformas,
            android.R.layout.simple_spinner_item
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Set the adapter to the Spinner
        listaPlataformas.adapter = adapter

        // Set an item selected listener
        listaPlataformas.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                // Get the selected item
                val selectedItem = parent.getItemAtPosition(position).toString()
                // Show a toast message or handle the selection
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do nothing
            }
        }

        binding.btnCadastrar.setOnClickListener {
            cadastrar()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun cadastrar(){
        val db = DBHelper(this.requireContext(), null)
        val nomeJogo = binding.nomeJogo.text.toString()
        val plataforma = binding.listaPlataformas.selectedItem.toString()
        db.addGame(nomeJogo, plataforma)
        Toast.makeText(this.requireContext(),nomeJogo + " adicionado no banco!",Toast.LENGTH_LONG).show()
        binding.nomeJogo.text.clear()
    }
}
