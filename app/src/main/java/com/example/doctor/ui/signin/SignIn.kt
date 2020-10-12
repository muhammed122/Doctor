package com.example.doctor.ui.signin

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.doctor.R
import com.example.doctor.data.helper.FirebaseHelper
import kotlinx.android.synthetic.main.fragment_sign_in.*

class SignIn : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }



    @SuppressLint("RestrictedApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.hide()


        btn_signin.setOnClickListener {

            val mail = username_signin.text?.toString()?.trim()
            val pass =pass_signin.text?.toString()?.trim()
            if (pass != null) {
                if (!mail?.equals("")!! && pass != ""){
                    parent_signIn.visibility=View.GONE
                    signIn_prog.visibility=View.VISIBLE

                    signInWithEmailAndPassword(mail,pass)
                }
                else
                    Toast.makeText(requireContext(), "Enter all data.",
                        Toast.LENGTH_SHORT).show()


            }
        }

        signUp_move.setOnClickListener {
            findNavController().popBackStack()
            view.findNavController().navigate(R.id.signUp)

        }


    }



    fun signInWithEmailAndPassword( mail: String, pass: String) {

        FirebaseHelper.firebaseAuth.signInWithEmailAndPassword(mail, pass)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    findNavController().popBackStack()
                    view?.findNavController()?.navigate(R.id.home2)

                    print("dola task ${task.result}")

                } else {

                    parent_signIn.visibility=View.VISIBLE
                    signIn_prog.visibility=View.GONE
                    Toast.makeText(
                        requireContext(),
                        "Error ${task.exception?.localizedMessage}", Toast.LENGTH_LONG
                    ).show()
                }
            }

    }
}