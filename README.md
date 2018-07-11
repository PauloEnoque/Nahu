# Nahu
Nahu is a Kotlin Android library that helps you easily request dangerous permissions in a DSL style.

## Importing the library to your project
Add this to your project's build.gradle file:
``` gradle
allprojects{
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```
Step 2. Add the dependency to your app's build.gradle file:
```gradle
dependencies {
    compile 'com.github.PauloEnoque:Nahu:beta'
}
```
**That's it!**

## How to use in your code
1. Add the permission to your Manifest:
```xml
<uses-permission android:name="android.permission.CALL_PHONE" />
```
2. Initialize a request code for that permission request:
```kotlin
val REQUESTCODE = 1
```
3. Call the function in the same activity:
```kotlin
permissionManager {
    activity = this@MainActivity
    permissions = arrayOf(Manifest.permission.CALL_PHONE)
    permissionExplanation = "Without this permission you cant call anyone"
    requestCode = REQUESTCODE
    then {
        openDialerApp()
    }
}
```

4. Override the `onRequestPermissionsResult()` function:
```kotlin
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
```
And thats it!

See the full activity code below:
```kotlin
package ability.co.mz.nahuexample

import ability.co.mz.nahu.handlePermissionsResult
import ability.co.mz.nahu.permissionManager
import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
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
```
This library makes your code cleaner and much more readable. It's an open-source project and, of course, it will get better with time.

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details
