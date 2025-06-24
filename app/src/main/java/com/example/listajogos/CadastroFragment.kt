package com.example.listajogos.ui.cadastro

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.listajogos.DBHelper
import com.example.listajogos.R

class CadastroFragment : Fragment() {

    private lateinit var email: EditText
    private lateinit var senha: EditText
    private lateinit var cadastrar: Button
    private lateinit var buttonVoltarLogin: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_cadastro, container, false)

        email = view.findViewById(R.id.email)
        senha = view.findViewById(R.id.senha)
        cadastrar = view.findViewById(R.id.cadastrar)
        //buttonVoltarLogin = view.findViewById(R.id.buttonVoltarLogin)

        cadastrar.setOnClickListener {
            realizarCadastro()
        }



        return view
    }

    private fun realizarCadastro() {
        val email = email.text.toString()
        val senha = senha.text.toString()

        if (email.isEmpty() || senha.isEmpty()) {
            Toast.makeText(requireContext(), "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            return
        }

        val db = DBHelper(requireContext(), null)
        val sucesso = db.cadastrarUsuario(email, senha)

        if (sucesso) {
            Toast.makeText(requireContext(), "Usuário cadastrado com sucesso!", Toast.LENGTH_SHORT).show()
            //findNavController().navigate(R.id.action_fragment_cadastro_to_nav_login)
        } else {
            Toast.makeText(requireContext(), "Erro: Usuário já existe!", Toast.LENGTH_SHORT).show()
        }
    }
}
