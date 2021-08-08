package com.vkochenkov.imagesearch.presentation.dialog

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.vkochenkov.imagesearch.R
import com.vkochenkov.imagesearch.presentation.service.WallpaperService

class WallpaperBottomSheetDialog() : BottomSheetDialogFragment() {

    lateinit var wallpaperHomeTv: TextView
    lateinit var wallpaperLockTv: TextView
    lateinit var wallpaperBothTv: TextView
    lateinit var animationWhenPressed: Animation

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.bottom_sheet_wallpaper, container, false)

        wallpaperBothTv = root.findViewById(R.id.set_wallpaper_both_screens_tv)
        wallpaperHomeTv = root.findViewById(R.id.set_wallpaper_home_screen_tv)
        wallpaperLockTv = root.findViewById(R.id.set_wallpaper_lock_screen_tv)
        animationWhenPressed = AnimationUtils.loadAnimation(activity, R.anim.decreases_when_pressed)

        setOnClickListeners()
        return root
    }

    private fun setOnClickListeners() {
        val intent = Intent(activity, WallpaperService::class.java)

        wallpaperHomeTv.setOnClickListener {
            it.startAnimation(animationWhenPressed)
            intent.putExtra(WallpaperService.WALLPAPER_MODE, WallpaperService.HOME)
            activity?.startService(intent)
            this.dismiss()
        }

        wallpaperLockTv.setOnClickListener {
            it.startAnimation(animationWhenPressed)
            intent.putExtra(WallpaperService.WALLPAPER_MODE, WallpaperService.LOCK)
            activity?.startService(intent)
            this.dismiss()
        }

        wallpaperBothTv.setOnClickListener {
            it.startAnimation(animationWhenPressed)
            intent.putExtra(WallpaperService.WALLPAPER_MODE, WallpaperService.BOTH)
            activity?.startService(intent)
            this.dismiss()
        }
    }
}