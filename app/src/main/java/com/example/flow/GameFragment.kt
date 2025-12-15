package com.example.flow

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

class GameFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_game, container, false)

        val startBtn = view.findViewById<Button>(R.id.btnTetris)
        val quitBtn = view.findViewById<Button>(R.id.btnQuit)

        startBtn.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .add(R.id.main_frame_layout, GameOverlayFragment())
                .addToBackStack(null)
                .commit()

            // 60초 후 알림창 띄우기 위해 Handler 사용
            Handler(Looper.getMainLooper()).postDelayed({
                showBusArrivalDialog()
            }, 30000)
        }

        quitBtn.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        return view
    }

    private fun showBusArrivalDialog() {
        val builder = android.app.AlertDialog.Builder(requireContext())
        builder.setTitle("버스 도착 1분 전입니다.")
        builder.setMessage("탑승할 준비를 해주세요.")

        builder.setPositiveButton("게임 계속하기") { dialog, _ ->
            dialog.dismiss()
        }

        builder.setNegativeButton("게임 나가기") { _, _ ->
            parentFragmentManager.popBackStack()
        }

        builder.show()
    }
}