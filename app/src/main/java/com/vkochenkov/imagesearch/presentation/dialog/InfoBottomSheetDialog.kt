package com.vkochenkov.imagesearch.presentation.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.vkochenkov.imagesearch.R

class InfoBottomSheetDialog: BottomSheetDialogFragment() {

    lateinit var infoSizeTv: TextView
    lateinit var infoUserTv: TextView

    var userName = "no data"
    var imageSizeInfo = "no data"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.bottom_sheet_info, container, false)
        infoUserTv = root.findViewById(R.id.sheet_info_user)
        infoSizeTv = root.findViewById(R.id.sheet_info_size)
        infoUserTv.text = userName
        infoSizeTv.text = imageSizeInfo
        return root
    }
}