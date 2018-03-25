Nahu is an Kotlin android library which helps to easily integrate dangerous permission to your app in a DSL style.

## Importing library to your project
Add this in your root build.gradle at the end of repositories:
``` gradle
allprojects{
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```
Step 2. Add the dependency (module gradle file):
```gradle
dependencies {
    compile 'com.github.PauloEnoque:Nahu:beta'
}
```
**That's it!**

## How to use in your code
1. Add the permission to your Manifest
```xml
<uses-permission android:name="android.permission.CALL_PHONE" />
```
2. Initialize a request code for that permission request:
```kotlin
val REQUESTCODE = 1
```
3. Call the function in the same activity
```kotlin
permissinaManager {
                activity = this@MainActivity
                permission = Manifest.permission.CALL_PHONE
                permissionExplaination = "without this permission you cant call anyone"
                requestCode = REQUESTCODE
                then {
                    openDialerApp()
                }
            }
```

4. Oviride the onRequestPermissionsResult function
```kotlin
 override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(grantResults[0]){
            PackageManager.PERMISSION_GRANTED -> openDialerApp()
        }
    }
```
and thats it
see the full activity code:
```kotlin
package ability.co.mz.nahuexample

import ability.co.mz.nahu.permissinaManager
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
            permissinaManager {
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
```
this library makes your code cleaner and much more readable, its an open-source project and of course it will just get better
