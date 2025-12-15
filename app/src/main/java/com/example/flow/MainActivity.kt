package com.example.flow

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.flow.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 첫 시작 프래그먼트 설정
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frame_layout, MainFragment()) // ← MainFragment로 지도 표시
            .commit()

        // 하단 네비게이션 클릭 리스너가 있다면 여기에 추가
        binding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.mainFragment -> switchFragment(MainFragment())
                R.id.navigationFragment -> switchFragment(NavigationFragment())
                R.id.transportFragment -> switchFragment(TransportFragment())
                R.id.myFragment -> switchFragment(MyFragment())
                R.id.gameFragment -> switchFragment(GameFragment())
            }
            true
        }
    }

    private fun switchFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frame_layout, fragment)
            .commit()
    }
}
