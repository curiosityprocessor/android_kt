package fastcampus.aop.part2.ch5.album

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private val addPhotoButton : Button by lazy {
        findViewById(R.id.addPhotoButton)
    }

    private val runAlbumButton : Button by lazy {
        findViewById(R.id.runAlbumButton)
    }
    private val imageViewList : List<ImageView> by lazy {
        mutableListOf<ImageView>().apply {
            findViewById<ImageView>(R.id.imageView1_1)
            findViewById<ImageView>(R.id.imageView1_2)
            findViewById<ImageView>(R.id.imageView1_3)
            findViewById<ImageView>(R.id.imageView2_1)
            findViewById<ImageView>(R.id.imageView2_2)
            findViewById<ImageView>(R.id.imageView2_3)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initAddPhotoButton()
        initRunAlbumButton()
    }

    private fun initAddPhotoButton() {
        addPhotoButton.setOnClickListener {
            when {
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED -> {
                    //권한 잘 부여되어있을 때 갤러리에서 사진 선택
                    navigatePhotos()
                }
                shouldShowRequestPermissionRationale(android.Manifest.permission.READ_EXTERNAL_STORAGE) -> {
                    //교육용 팝업 호출 -> 확인 후 권한 팝업 호출
                    showPermissionContextPopup()
                }
                else -> {
                    //권한 요청 팝업 호출
                    requestPermission()
                }
            }
        }
    }

    private fun showPermissionContextPopup() {
        AlertDialog.Builder(this)
            .setTitle("권한이 필요합니다.")
            .setMessage("전자액자 앱에서 사진을 불러오기 위해 권한이 필요합니다.")
            .setPositiveButton("동의하기", {_, _ -> requestPermission()})
            .setNegativeButton("취소하기", {_, _ -> })
            .create().show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode) {
            1000 -> {
                if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //권한이 정상적으로 부여됨
                    navigatePhotos()
                } else {
                    //권한이 부여되지 않음
                    Toast.makeText(this, "권한이 거부되었습니다.", Toast.LENGTH_SHORT).show()
                }
            }
            else -> {
                //정의한 적 없으므로 구현 대상 없음
            }
        }
    }

    private fun navigatePhotos() {
        var intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, 2000)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode != Activity.RESULT_OK) {
            return
        }
        when(requestCode) {
            2000 -> {
                val selectedImageUri: Uri?
            }
            else -> {
                Toast.makeText(this, "사진을 가져오지 못했습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun requestPermission() {
        requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 1000)
    }

    private fun initRunAlbumButton() {
        TODO("Not yet implemented")
    }
}
