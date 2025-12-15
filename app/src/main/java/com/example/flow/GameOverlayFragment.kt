package com.example.flow

import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.example.flow.player.PlayerImpl
import com.example.flow.tetrisgame.BoardProfile
import com.example.flow.tetrisgame.TetrisView

class GameOverlayFragment : Fragment() {

    private lateinit var tetrisView: TetrisView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val frame = FrameLayout(requireContext()).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            setBackgroundColor(Color.BLACK)
        }

        val screenWidth = resources.displayMetrics.widthPixels
        val screenHeight = resources.displayMetrics.heightPixels - navBarHeight() // ✅ 조정된 높이

        val profile = BoardProfile(screenWidth, screenHeight)
        val player = PlayerImpl(profile)

        tetrisView = TetrisView(requireContext(), player, profile)
        frame.addView(tetrisView)
        tetrisView.startGame()

        return frame
    }

    override fun onPause() {
        super.onPause()
        if (::tetrisView.isInitialized) tetrisView.pauseGame()
    }

    override fun onResume() {
        super.onResume()
        if (::tetrisView.isInitialized) tetrisView.resumeGame()
    }

    // ✅ 네비게이션 바 높이 측정 함수
    private fun navBarHeight(): Int {
        val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
        return if (resourceId > 0) resources.getDimensionPixelSize(resourceId) else 0
    }
}
