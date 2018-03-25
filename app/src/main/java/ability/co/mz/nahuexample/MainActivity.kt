package ability.co.mz.nahuexample

import ability.co.mz.nahu.permissionManager
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val REQUESTCODE = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        openDialer.setOnClickListener {
            permissionManager {
                activity = this@MainActivity
                permission = Manifest.permission.CALL_PHONE
                permissionExplaination = "without this permission you cant call anyone"
                requestCode = REQUESTCODE
                then {
                    openDialerApp()
                }
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(grantResults[0]){
            PackageManager.PERMISSION_GRANTED -> openDialerApp()
        }
    }

    private fun openDialerApp() {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:823389250")
        startActivity(intent)
    }
}
