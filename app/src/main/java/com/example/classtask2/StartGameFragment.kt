package com.example.classtask2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.classtask2.databinding.FragmentStartGameBinding


class StartGameFragment : Fragment() {

    private lateinit var binding:FragmentStartGameBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=FragmentStartGameBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnStartGame.setOnClickListener {
            val score=binding.etScore.text.toString().trim()
            val time=binding.etTime.text.toString().trim()
            if (score.isEmpty()) {
                binding.etScore.error = "Please enter the score"
            } else if (time.isEmpty()) {
                binding.etTime.error = "Please enter the time"
            }else{
                findNavController().navigate(StartGameFragmentDirections.actionStartGameFragment2ToGameFragment(time,score))
            }

        }

    }


}