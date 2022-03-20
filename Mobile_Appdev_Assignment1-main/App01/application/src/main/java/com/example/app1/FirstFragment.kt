package com.example.app1



import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.navigation.fragment.findNavController

import com.example.app1.api.UserApiService
import com.example.app1.databinding.FragmentFirstBinding
import com.example.app1.model.User


import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val userApiService = UserApiService.create()

    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
           val editText = binding.inputtextid.editableText
           val user = userApiService.getUser(editText.toString());



         //   val user = userApiService.getUser("1");
            user.enqueue(object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {

                    val body = response.body()
                    body?.let {
                        Log.i("FirstFragment", it.name)
                        binding.textviewName.text = it.name
                        binding.textviewEmail.text = it.email
                        binding.textviewUsername.text=it.username;
                        binding.textviewId.text= it.id.toString()
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    Log.i("FirstFragment", "error")
                }

            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

