package com.manish.recipeapp.ui.identifymeal

import android.graphics.Bitmap
import androidx.camera.core.ImageCapture
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.manish.recipeapp.R
import com.manish.recipeapp.common.ImageCaptureUIState
import com.manish.recipeapp.ui.theme.RecipeAPPTheme
import com.manish.recipeapp.ui.uicomponents.CameraView
import com.manish.recipeapp.ui.uicomponents.ImagePreview
import com.manish.recipeapp.ui.uicomponents.MyLoader
import com.manish.recipeapp.ui.uicomponents.TypeWriterText

@Composable
fun IdentifyRecipeScreen(
    identifyRecipeViewModel: IdentifyRecipeViewModel = viewModel()
) {
    val createRecipeUIState by identifyRecipeViewModel.createRecipeUIState.collectAsStateWithLifecycle()
    val imageCaptureUIState by identifyRecipeViewModel.imageCaptureUIState.collectAsStateWithLifecycle()

    CreateRecipeContent(
        createRecipeUIState = createRecipeUIState,
        imageCaptureUIState = imageCaptureUIState,
        onClickCapture = {
            identifyRecipeViewModel.captureImage(it)
        },
        onClosePreview = {
            identifyRecipeViewModel.activateCameraPreviewMode()
        },
        onPhotoPicked = { bitmap ->
            identifyRecipeViewModel.onPhotoPickedFromGallery(bitmap)
        }
    )

    when(createRecipeUIState) {
        CreateRecipeUIState.Loading -> MyLoader()
        else -> {}
    }
}

@Composable
fun CreateRecipeContent(
    createRecipeUIState: CreateRecipeUIState,
    imageCaptureUIState: ImageCaptureUIState,
    onClickCapture: (imageCapture: ImageCapture) -> Unit,
    onPhotoPicked: (image: Bitmap) -> Unit,
    onClosePreview: () -> Unit,
) {
    val scrollState = rememberScrollState()
    var showImagePreview by rememberSaveable { mutableStateOf(false) }


    LaunchedEffect(imageCaptureUIState) {
        showImagePreview = imageCaptureUIState is ImageCaptureUIState.ImagePreview
    }

    LaunchedEffect(key1 = scrollState.maxValue) {
        scrollState.animateScrollTo(scrollState.maxValue)
    }

    Scaffold { contentPadding ->
        Column(
            modifier = Modifier
                .padding(contentPadding)
                .verticalScroll(scrollState)
                .padding(16.dp)
        ) {
            when (imageCaptureUIState) {
                is ImageCaptureUIState.CameraPreview -> {
                    CameraView(
                        onClickCapture = onClickCapture,
                        onPhotoPicked = { bitmap ->
                            onPhotoPicked(bitmap)
                        }
                    )
                }

                is ImageCaptureUIState.ImagePreview -> {
                    ImagePreview(imageCaptureUIState.imageBitmap, onClosePreview)
                }
            }

            Column(modifier = Modifier.padding(8.dp)){
                if (imageCaptureUIState is ImageCaptureUIState.CameraPreview) {
                    Text(
                        text = stringResource(R.string.identify_recipe_helper_text),
                        style = MaterialTheme.typography.labelLarge,
                        textAlign = TextAlign.Center
                    )
                }

                when(createRecipeUIState) {
                     is CreateRecipeUIState.ContentGenerated -> {
                        Spacer(modifier = Modifier.height(22.dp))
                        TypeWriterText(
                            modifier = Modifier.fillMaxWidth(),
                            text = createRecipeUIState.content
                        )
                    }

                    is CreateRecipeUIState.Error -> {
                        Spacer(modifier = Modifier.height(22.dp))
                        TypeWriterText(
                            modifier = Modifier.fillMaxWidth(),
                            text = createRecipeUIState.errorMessage,
                            textColor = Color.Red
                        )
                    }

                    else -> {}
                }
            }
        }
    }
}

@Preview
@Composable
fun CreateRecipeContentPreview() {
    RecipeAPPTheme {
        CreateRecipeContent(
            createRecipeUIState = CreateRecipeUIState.Idle,
            onClickCapture = {},
            onClosePreview = {},
            imageCaptureUIState = ImageCaptureUIState.CameraPreview,
            onPhotoPicked = {}
        )
    }
}