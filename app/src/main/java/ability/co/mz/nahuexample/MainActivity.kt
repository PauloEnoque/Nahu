package ability.co.mz.nahuexample

import ability.co.mz.nahu.handlePermissionsResult
import ability.co.mz.nahu.permissionManager
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val REQUESTCODE = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        openDialer.setOnClickListener {
            permissionManager {
                activity = this@MainActivity
                permissions = arrayOf(Manifest.permission.CALL_PHONE)
                permissionExplanation = "Without this permission you cant call anyone"
                requestCode = REQUESTCODE
                then {
                    openDialerApp()
                }
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {

        handlePermissionsResult {
            this.requestCode = requestCode
            this.permissions = permissions
            this.grantResults = grantResults

            onPermissionGranted { permission ->
                when (permission) {
                    Manifest.permission.CALL_PHONE -> openDialerApp()
                }
            }

            onPermissionDenied { permission ->
                when (permission) {
                    Manifest.permission.CALL_PHONE -> {
                        Toast.makeText(applicationContext, "Cant open dialler without permission",
                                Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }

    }

    private fun openDialerApp() {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:823389250")
        startActivity(intent)
    }
}
