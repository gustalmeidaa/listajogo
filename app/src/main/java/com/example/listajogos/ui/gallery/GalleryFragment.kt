package com.example.listajogos.ui.gallery

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.listajogos.DBHelper
import com.example.listajogos.databinding.FragmentGalleryBinding

class GalleryFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        listarJogos()

        return root
    }

    private fun listarJogos() {
        binding.listaJogos.removeAllViews()  // Limpa a lista antes de carregar

        val db = DBHelper(requireContext(), null)
        val cursor = db.getName()

        if (cursor != null && cursor.moveToFirst()) {
            do {
                val nome = cursor.getString(cursor.getColumnIndexOrThrow("nome"))
                val plataforma = cursor.getString(cursor.getColumnIndexOrThrow("plataforma"))

                // Cria um layout horizontal para cada item
                val itemLayout = LinearLayout(requireContext()).apply {
                    orientation = LinearLayout.HORIZONTAL
                    setPadding(16, 16, 16, 16)
                }

                // Texto do jogo
                val texto = TextView(requireContext()).apply {
                    text = "$nome - $plataforma"
                    textSize = 18f
                    setPadding(0, 0, 20, 0)
                    layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
                }

                // Botão editar
                val btnEditar = Button(requireContext()).apply {
                    text = "Editar"
                    setOnClickListener {
                        editarJogo(nome, plataforma)
                    }
                }

                // Botão deletar
                val btnDeletar = Button(requireContext()).apply {
                    text = "Excluir"
                    setOnClickListener {
                        deletarJogo(nome)
                    }
                }

                // Adiciona os elementos no layout horizontal
                itemLayout.addView(texto)
                itemLayout.addView(btnEditar)
                itemLayout.addView(btnDeletar)

                // Adiciona o layout na lista principal
                binding.listaJogos.addView(itemLayout)

            } while (cursor.moveToNext())
        } else {
            Toast.makeText(requireContext(), "Nenhum jogo cadastrado", Toast.LENGTH_SHORT).show()
        }

        cursor?.close()
    }

    private fun editarJogo(nomeAntigo: String, plataformaAntiga: String) {
        val db = DBHelper(requireContext(), null)

        // Cria um AlertDialog para editar
        val dialogView = LayoutInflater.from(requireContext()).inflate(
            android.R.layout.simple_list_item_2, null
        )
        val inputNome = EditText(requireContext()).apply {
            hint = "Novo nome"
            setText(nomeAntigo)
        }
        val inputPlataforma = EditText(requireContext()).apply {
            hint = "Nova plataforma"
            setText(plataformaAntiga)
        }

        val layout = LinearLayout(requireContext()).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(50, 40, 50, 10)
            addView(inputNome)
            addView(inputPlataforma)
        }

        AlertDialog.Builder(requireContext())
            .setTitle("Editar Jogo")
            .setView(layout)
            .setPositiveButton("Salvar") { _, _ ->
                db.updateName(nomeAntigo, inputNome.text.toString(), inputPlataforma.text.toString())
                listarJogos()
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun deletarJogo(nome: String) {
        val db = DBHelper(requireContext(), null)

        AlertDialog.Builder(requireContext())
            .setTitle("Excluir Jogo")
            .setMessage("Tem certeza que deseja excluir \"$nome\"?")
            .setPositiveButton("Sim") { _, _ ->
                db.deleteName(nome)
                listarJogos()
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
