package uk.co.massimocarli.livedatabus

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*
import uk.co.massimocarli.livedatabus.location.LocationViewModel

class MainActivity : AppCompatActivity() {

  lateinit var locationViewModel: LocationViewModel
  lateinit var navController: NavController

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    navController = findNavController(R.id.navHostFragment)
    bottomNavigationView.setupWithNavController(navController)
    locationViewModel =
      ViewModelProviders.of(
        this,
        ViewModelProvider.AndroidViewModelFactory.getInstance(application)
      ).get(LocationViewModel::class.java)
    locationViewModel.getLocationLiveData().observe(this, Observer {
      if (it is PermissionRequest) {
        requestLocationPermission()
      }
    })
  }

  companion object {
    const val LOCATION_PERMISSION_REQUEST_ID = 1
    const val REQUIRED_PERMISSION = Manifest.permission.ACCESS_FINE_LOCATION
  }

  fun requestLocationPermission() {
    if (ContextCompat.checkSelfPermission(
        this,
        REQUIRED_PERMISSION
      ) != PackageManager.PERMISSION_GRANTED
    ) {
      // We check if we have to provide a reason for the Location permission request
      if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSION)) {
        // In this case we have to show a Dialog which explain the permission request to the user
        AlertDialog.Builder(this)
          .setTitle(R.string.location_request_dialog_title)
          .setMessage(R.string.location_request_dialog_reason)
          .setPositiveButton(android.R.string.ok) { dialog, which ->
            ActivityCompat.requestPermissions(
              this,
              arrayOf(REQUIRED_PERMISSION),
              LOCATION_PERMISSION_REQUEST_ID
            )
          }
          .create()
          .show()
      } else {
        ActivityCompat.requestPermissions(
          this,
          arrayOf(REQUIRED_PERMISSION),
          LOCATION_PERMISSION_REQUEST_ID
        )
      }
    }
  }

  override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
    if (requestCode == LOCATION_PERMISSION_REQUEST_ID) {
      // In this case we check if the user has given permission
      if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
        // We can't generate the ON_START event so we notify the PermissionLiveDataDecorator that the permission is now
        // available
        locationViewModel.permissionUpdate()
      } else {
        // We cannot use the app so we explain to the user and exit
        AlertDialog.Builder(this)
          .setTitle(R.string.location_request_dialog_title)
          .setMessage(R.string.location_request_dialog_close)
          .setPositiveButton(android.R.string.ok) { dialog, which ->
            finish()
          }
          .create()
          .show()
      }
    }
  }
}
