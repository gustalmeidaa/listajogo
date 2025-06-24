package com.example.listajogos.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
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
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Custom adapter com texto branco
        val plataformas = listOf("PlayStation", "Xbox", "PC", "Switch")
        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.spinner_item_white, // layout customizado
            plataformas
        )
        adapter.setDropDownViewResource(R.layout.spinner_item_white)

        val listaPlataformas: Spinner = binding.listaPlataformas
        listaPlataformas.adapter = adapter

        listaPlataformas.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val selectedItem = parent.getItemAtPosition(position).toString()
                Toast.makeText(requireContext(), "Selecionado: $selectedItem", Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
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

    private fun cadastrar() {
        val db = DBHelper(this.requireContext(), null)
        val nomeJogo = binding.nomeJogo.text.toString()
        val plataforma = binding.listaPlataformas.selectedItem.toString()
        db.addName(nomeJogo, plataforma)
        Toast.makeText(requireContext(), "$nomeJogo adicionado no banco!", Toast.LENGTH_LONG).show()
        binding.nomeJogo.text.clear()
    }
}
