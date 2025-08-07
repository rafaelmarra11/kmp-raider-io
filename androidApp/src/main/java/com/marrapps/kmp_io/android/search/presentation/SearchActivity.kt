package com.marrapps.kmp_io.android.search.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import com.marrapps.kmp_io.android.MyApplicationTheme
import com.marrapps.kmp_io.android.R

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                CompositionLocalProvider(
                    LocalTextStyle provides LocalTextStyle.current.merge(
                        TextStyle(
                            color = colorResource(R.color.default_text_color)
                        )
                    )
                ) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = colorResource(R.color.default_background_color)
                    ) {
                        Column {
                            SearchView()
                            ResultView()
                        }

                    }
                }
            }
        }
    }
}
