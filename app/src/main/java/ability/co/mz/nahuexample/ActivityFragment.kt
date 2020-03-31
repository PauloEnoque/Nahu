package ability.co.mz.nahuexample

import ability.co.mz.nahu.handlePermissionsResult
import ability.co.mz.nahu.requestNahuPermissions
import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast

/**
 * A placeholder fragment containing a simple view.
 */
class ActivityFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val fragmentView = inflater.inflate(R.layout.fragment_fragmented, container, false)
        val openDialer = fragmentView.findViewById<Button>(R.id.openDialer)
        openDialer.setOnClickListener {
            requestNahuPermissions {
                permissions = arrayOf(Manifest.permission.CALL_PHONE)
                then {
                    openDialerApp()
                }
            }
        }

        return fragmentView
    }

    private fun openDialerApp() {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:823389250")
        startActivity(intent)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
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
                        Toast.makeText(this@ActivityFragment.context,
                                "Cant open dialler without permission",
                                Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }
    }
}
