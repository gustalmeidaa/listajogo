package com.example.listajogos.ui.login

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

class LoginFragment : Fragment() {

    private lateinit var email: EditText
    private lateinit var senha: EditText
    private lateinit var logar: Button
    private lateinit var cadastro: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        email = view.findViewById(R.id.email)
        senha = view.findViewById(R.id.senha)
        logar = view.findViewById(R.id.entrar)
        cadastro = view.findViewById(R.id.cadastrar)

        logar.setOnClickListener {
            realizarLogin()
        }

        cadastro.setOnClickListener {
            findNavController().navigate(R.id.nav_cadastro)
        }

        return view
    }

    private fun realizarLogin() {
        val usuario = email.text.toString()
        val senhaUsuario = senha.text.toString()

        if (usuario.isEmpty() || senhaUsuario.isEmpty()) {
            Toast.makeText(requireContext(), "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            return
        }

        val db = DBHelper(requireContext(), null)

        val loginValido = db.verificarLogin(usuario, senhaUsuario)

        if (loginValido) {
            Toast.makeText(requireContext(), "Login bem-sucedido", Toast.LENGTH_SHORT).show()
            // Navega para a Home
            findNavController().navigate(R.id.nav_home)
        } else {
            Toast.makeText(requireContext(), "Usuário ou senha incorretos", Toast.LENGTH_SHORT).show()
        }
    }

}
