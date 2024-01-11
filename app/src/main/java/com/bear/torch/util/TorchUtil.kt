package com.bear.torch.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Camera
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraManager
import android.os.Build


object TorchUtil {
    private var camera: Camera? = null
    private  var cameraManager : CameraManager? = null
    var status = false
    var mContext : Context? = null

    fun initData(context : Context) {
        this.mContext = context
        try {
            if (cameraManager == null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
            }
        } catch (e: Exception) {
        }
    }

    //打开手电筒
    @SuppressLint("StaticFieldLeak")
    fun open() {
        if (status) { //如果已经是打开状态，不需要打开
            return
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                cameraManager?.setTorchMode("0", true)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
//            val packageManager = mContext?.packageManager
//            val features = packageManager?.systemAvailableFeatures
//            for (featureInfo in features) {
//                if (PackageManager.FEATURE_CAMERA_FLASH == featureInfo.name) { // 判断设备是否支持闪光灯
////                    val parameters = cameraManager.parameters
////                    parameters.flashMode = Camera.Parameters.FLASH_MODE_TORCH
////                    cameraManager.parameters = parameters
////                    cameraManager.startPreview()
//                }
//            }
        }
        status = true //记录手电筒状态为打开
    }

    //关闭手电筒
    fun close() {
        if (!status) { //如果已经是关闭状态，不需要打开
            return
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                cameraManager?.setTorchMode("0", false)
            } catch (e: CameraAccessException) {
                e.printStackTrace()
            }
        } else {

        }
        status = false //记录手电筒状态为关闭
    }
}