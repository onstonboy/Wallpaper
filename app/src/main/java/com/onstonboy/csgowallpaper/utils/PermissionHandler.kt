package com.onstonboy.csgowallpaper.utils

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

interface PermissionHandler {
    fun isPermissionGalleryGranted(): Boolean
    fun isPermissionCameraGranted(): Boolean
    fun isPermissionStorageGranted(): Boolean
    fun isPermissionSetWallpaperGranted(): Boolean
    fun isPermissionGranted(permission: String, requestCode: Int): Boolean
}

class PermissionHandlerImpl(private val activity: Activity) : PermissionHandler {

    override fun isPermissionGalleryGranted(): Boolean {
        val resultExternalStorage: Int =
            ContextCompat.checkSelfPermission(
                activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )

        if (resultExternalStorage == PackageManager.PERMISSION_GRANTED) {
            return true
        } else {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                Constant.RequestCode.REQUEST_CODE_PERMISSION_GALLERY
            )
        }
        return false
    }

    override fun isPermissionCameraGranted(): Boolean {
        val resultCamera = ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA)
        val resultExternalStorage = ContextCompat.checkSelfPermission(
            activity,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        if (resultCamera == PackageManager.PERMISSION_GRANTED && resultExternalStorage == PackageManager.PERMISSION_GRANTED) {
            return true
        } else {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE),
                Constant.RequestCode.REQUEST_CODE_PERMISSION_CAMERA
            )
        }
        return false
    }

    override fun isPermissionStorageGranted(): Boolean {
        val resultExternalStorage = ContextCompat.checkSelfPermission(
            activity,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        if (resultExternalStorage == PackageManager.PERMISSION_GRANTED) {
            return true
        } else {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                Constant.RequestCode.REQUEST_CODE_PERMISSION_STORAGE
            )
        }
        return false
    }

    override fun isPermissionSetWallpaperGranted(): Boolean {
        val resultExternalStorage = ContextCompat.checkSelfPermission(
            activity,
            Manifest.permission.SET_WALLPAPER
        )
        if (resultExternalStorage == PackageManager.PERMISSION_GRANTED) {
            return true
        } else {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(Manifest.permission.SET_WALLPAPER),
                Constant.RequestCode.REQUEST_CODE_PERMISSION_SET_WALLPAPER
            )
        }
        return false
    }

    override fun isPermissionGranted(permission: String, requestCode: Int): Boolean {
        if (ContextCompat.checkSelfPermission(
                activity,
                permission
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            ActivityCompat.requestPermissions(
                activity,
                arrayOf(permission), requestCode
            )
            return false
        }
        return true
    }
}
