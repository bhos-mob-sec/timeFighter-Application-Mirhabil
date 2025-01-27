package com.example.classtask2

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.classtask2.databinding.FragmentGameBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GameFragment : Fragment() {
    private val args by navArgs<GameFragmentArgs>()

    private lateinit var binding: FragmentGameBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGameBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tapCount = 0
        val numberOfTaps = args.score.toInt()
        val leftSeconds = args.time.toInt()

        binding.tvLeftSeconds.text="Left Seconds: $leftSeconds"
        binding.btnTapMe.setOnClickListener {
            binding.tvNumberOfTaps.text="Your Score: 1"
            gameLogic(tapCount, leftSeconds, numberOfTaps)
        }
    }

    fun showAlertDialog(title: String) {

        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle(title)
        builder.setMessage("Do you want to play again?")
        builder.setPositiveButton("Play Again") { dialog, _ ->
            playAgain()
        }
        builder.setNegativeButton("Cancel") { dialog, _ ->
            cancelButtonClick()
        }
        builder.create().show()

    }

    fun gameLogic(tapCountInput: Int, leftSecondsInput: Int, numberOfTaps: Int) {
        var leftSeconds = leftSecondsInput
        var tapCount = tapCountInput

        val textOfTaps = binding.tvNumberOfTaps.text
        val words = textOfTaps.split(" ")
        binding.btnTapMe.setOnClickListener {
            tapCount += 1
            val newText = words.dropLast(1).joinToString(" ") + " $tapCount"
            binding.tvNumberOfTaps.text = newText

        }

        CoroutineScope(Dispatchers.Main).launch {
            val textOfLeftSeconds=binding.tvLeftSeconds.text
            val words=textOfLeftSeconds.split(" ")
            while (leftSeconds > 0) {
                leftSeconds -= 1
                delay(1000)
                val newText=words.dropLast(1).joinToString (" ")+ " $leftSeconds"
                binding.tvLeftSeconds.text = newText
                if (leftSeconds == 0) {
                    binding.btnTapMe.isEnabled = false
                    when {
                        tapCount >= numberOfTaps -> showAlertDialog("You won!")
                        tapCount < numberOfTaps -> showAlertDialog("You lose!")
                    }
                }
            }

        }
    }

    fun playAgain() {
        val tapCountInput = 0
        val leftSecondsInput = args.time.toInt()
        val numberOfTaps = args.score.toInt()
        binding.tvNumberOfTaps.text = "Your Score: $tapCountInput"
        binding.tvLeftSeconds.text = "Time Left: $leftSecondsInput"
        binding.btnTapMe.isEnabled = true
        binding.btnTapMe.setOnClickListener {
            binding.tvNumberOfTaps.text="Your Score: 1"
            gameLogic(tapCountInput, leftSecondsInput, numberOfTaps)
        }
    }

    fun cancelButtonClick(){

        findNavController().navigate(GameFragmentDirections.actionGameFragmentToStartGameFragment2())
    }


}