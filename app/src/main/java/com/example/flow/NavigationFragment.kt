package com.example.flow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.example.flow.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.bottomsheet.BottomSheetBehavior

class NavigationFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mapFragment: SupportMapFragment
    private lateinit var googleMap: GoogleMap
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_navigation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. Google Map 초기화
        val fm = childFragmentManager
        mapFragment = fm.findFragmentById(R.id.map_fragment) as? SupportMapFragment
            ?: SupportMapFragment.newInstance().also {
                fm.beginTransaction().replace(R.id.map_fragment, it).commit()
            }

        mapFragment.getMapAsync(this)

        // 2. BottomSheetBehavior 설정
        val bottomSheet = view.findViewById<LinearLayout>(R.id.bottom_sheet)
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet).apply {
            peekHeight = 120  // dp 단위가 아님. 필요시 dimens 사용
            state = BottomSheetBehavior.STATE_COLLAPSED
            isDraggable = true
        }
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map

        val seoul = LatLng(37.5665, 126.9780)
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(seoul, 13f))

        googleMap.uiSettings.isZoomControlsEnabled = true
        googleMap.uiSettings.isMyLocationButtonEnabled = true
    }
}
