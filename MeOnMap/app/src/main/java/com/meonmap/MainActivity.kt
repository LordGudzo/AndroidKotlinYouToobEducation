package com.meonmap

import android.Manifest
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        enableEdgeToEdge()
        setContent {
            val viewModel = PositionViewModel()
            val context = LocalContext.current
            val utils = Utils(context)

            Surface(modifier = Modifier.fillMaxSize()) {
                Screen(utils, context, viewModel)
            }
        }
    }
}

@Composable
fun Screen(utils: Utils, context: Context, viewModel: PositionViewModel) {

    val position = viewModel.positon.value

    val rememberLauncherForActivityResult = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->

            if (permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true &&
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
            ) {
                utils.requestUpdatePosition(viewModel)
            }
            //<editor-fold desc="If user didn't adds permissions">
            else {
                val bool: Boolean = ActivityCompat.shouldShowRequestPermissionRationale(
                    context as MainActivity,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) || ActivityCompat.shouldShowRequestPermissionRationale(
                    context as MainActivity,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )

                if (bool) {
                    Toast.makeText(
                        context,
                        "You need turn on Location in this app",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    Toast.makeText(
                        context,
                        "You must turn on Location for this app in Settings",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
            //</editor-fold>

        }
    )


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Location is ${position?.latitude}  ${position?.longitude}")
        Spacer(modifier = Modifier.padding(8.dp))
        position?.let { Text("${utils.getAddress(it)}") }
        Spacer(modifier = Modifier.padding(8.dp))
        Button(onClick = {

            if (utils.hasPermission(context)) {
                utils.requestUpdatePosition(viewModel)
            } else {
                rememberLauncherForActivityResult.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                )
            }

        }) {
            Text("Check my location")
        }
    }
}
