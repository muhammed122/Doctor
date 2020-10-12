package com.example.doctor.ui.signup

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.doctor.R
import com.example.doctor.data.helper.FirebaseHelper
import com.example.doctor.data.model.UserModel
import kotlinx.android.synthetic.main.fragment_sign_up.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


class SignUp : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }


    @SuppressLint("RestrictedApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        login_move.setOnClickListener {
            findNavController().popBackStack()
            view.findNavController().navigate(R.id.signIn)

        }


        btn_signup.setOnClickListener {
          val email = username_signup.text.toString().trim()
          val password = pass_signup.text.toString().trim()
          val phone = phone_signup.text.toString().trim()
          val address = address_signup.text.toString().trim()
            val gender = when {
                male_radio.isChecked -> {
                    "male"
                }
                female_radio.isChecked -> "female"

                else -> ""
            }
            if (email == "" ||password=="" || phone==""||address==""||gender=="")

                Toast.makeText(requireContext(), "Enter all data.",
                    Toast.LENGTH_SHORT).show()
            else {
                parent_signUp.visibility=View.GONE
                signup_prog.visibility=View.VISIBLE
                signUpWithEmailAndPassword(email, password, phone, address, gender)
            }
        }

    }

    fun signUpWithEmailAndPassword( mail:String , password:String,phone:String,address:String,gender:String){

        FirebaseHelper.firebaseAuth.createUserWithEmailAndPassword(mail, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {


//                   val user=UserModel(task.result?.user?.uid,mail,phone,address,gender)
//                        task.result?.user?.uid?.let {
//                            FirebaseHelper.firebaseReference.child("users").child(
//                                it
//                            ).setValue(user)
//                        }

                    val bundle = Bundle()
                    bundle.putString("mail",mail)
                    bundle.putString("pass",password)
                    view?.findNavController()?.navigate(R.id.signIn,bundle)
                 //   findNavController().popBackStack()


                } else {
                    parent_signUp.visibility=View.VISIBLE
                    signup_prog.visibility=View.GONE

                    Toast.makeText(requireContext(), "Authentication failed."+
                            task.exception?.localizedMessage,
                        Toast.LENGTH_SHORT).show()

                }


            }
    }

}
