package com.manish.recipeapp.common

import android.graphics.Bitmap

sealed interface ImageCaptureUIState {
    data object CameraPreview: ImageCaptureUIState
    data class ImagePreview(val imageBitmap: Bitmap): ImageCaptureUIState
}