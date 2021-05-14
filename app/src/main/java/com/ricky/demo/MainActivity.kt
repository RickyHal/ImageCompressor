package com.ricky.demo

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Matrix
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.provider.MediaStore
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.ricky.imagecompressor.CompressUtil
import java.io.File
import java.util.*


class MainActivity : AppCompatActivity() {
    companion object {
        const val RESULT_PICKER = 6
        const val RESULT_PERMISSION = 7
    }

    private val compressBtn: Button by lazy { findViewById(R.id.btn_compress) }
    private val chooseBtn: Button by lazy { findViewById(R.id.btn_choose) }
    private val saveBtn: Button by lazy { findViewById(R.id.btn_save) }
    private val imageView: ImageView by lazy { findViewById(R.id.image) }
    private val seekBar: SeekBar by lazy { findViewById(R.id.seek_bar) }
    private val progressBar: ProgressBar by lazy { findViewById(R.id.progress) }
    private val tvSize: TextView by lazy { findViewById(R.id.tv_size) }
    private var imageUri: Uri? = null
    private val cachePath: String by lazy { "${applicationContext.cacheDir.absolutePath}/result.jpg" }
    private var hasPermission: Boolean = false
    private var quality = 85
    private var originSize = 0
    private var resultSize = 0
    private val handler = Handler()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestPermission()
        seekBar.progress = quality
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                quality = progress
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
        chooseBtn.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )
            intent.type = "image/*"
            startActivityForResult(intent, RESULT_PICKER)
        }
        compressBtn.setOnClickListener {
            when {
                !hasPermission -> {
                    Toast.makeText(
                        this,
                        "Without storage permission",
                        Toast.LENGTH_SHORT
                    ).show()
                    requestPermission()
                }
                imageUri == null -> Toast.makeText(this, "path=null", Toast.LENGTH_SHORT).show()
                else -> {
                    progressBar.visibility = View.VISIBLE
                    handler.post {
                        val bitmap =
                            BitmapFactory.decodeStream(contentResolver.openInputStream(imageUri!!))
                        val startTime = System.currentTimeMillis()
                        CompressUtil.compressBitmap(bitmap, quality, cachePath)
                        val endTime = System.currentTimeMillis()

                        val options = BitmapFactory.Options()
                        val bitmap1 =
                            BitmapFactory.decodeStream(
                                contentResolver.openInputStream(
                                    Uri.fromFile(
                                        File(cachePath)
                                    )
                                ), null, options
                            ) ?: return@post
                        val matrix = Matrix()
                        val inSampleSize =
                            calculateInSampleSize(options, imageView.width, imageView.height)
                        val w = (options.outWidth / inSampleSize)
                        val h = (options.outHeight / inSampleSize)
                        matrix.postScale(w * 1f / options.outWidth, h * 1f / options.outHeight)
                        val realBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.RGB_565)
                        val canvas = Canvas(realBitmap)
                        canvas.drawBitmap(bitmap1, matrix, null)

                        imageView.setImageBitmap(bitmap1)
                        Toast.makeText(
                            this,
                            "quality=${quality}   compress success",
                            Toast.LENGTH_SHORT
                        ).show()
                        resultSize = File(cachePath).readBytes().size
                        tvSize.text = String.format(
                            "Quality:%d%%\nOrigin:%.2fKB/%.2fMB\nResult:%.2fKB/%.2fMB\nCost Time:${endTime - startTime}ms",
                            quality,
                            originSize / 1024f,
                            originSize / 1024f / 1024,
                            resultSize / 1024f,
                            resultSize / 1024f / 1024f
                        )

                        progressBar.visibility = View.GONE
                    }
                }
            }
        }
        saveBtn.setOnClickListener {
            val path =
                "${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)}/${
                    Random().nextInt(10000)
                }.jpg"
            File(cachePath).copyTo(File(path))
            Toast.makeText(
                this,
                "Save success",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun requestPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
            || ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ),
                RESULT_PERMISSION
            )
        } else {
            hasPermission = true
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            RESULT_PERMISSION -> hasPermission =
                if (grantResults.isNotEmpty() && grantResults.all { it == PackageManager.PERMISSION_GRANTED }) { //同意权限申请
                    true
                } else {
                    Toast.makeText(this, "Need storage Permission!", Toast.LENGTH_SHORT).show()
                    false
                }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RESULT_PICKER) {
            data?.data?.let {
                imageUri = it
                val options = BitmapFactory.Options()
                val bitmap =
                    BitmapFactory.decodeStream(contentResolver.openInputStream(it), null, options)
                        ?: return
                originSize = contentResolver.openInputStream(it)?.readBytes()?.size ?: 0
                tvSize.text = String.format(
                    "Origin:%.2fKB/%.2fMB",
                    originSize / 1024f,
                    originSize / 1024f / 1024
                )
                val matrix = Matrix()
                val inSampleSize = calculateInSampleSize(options, imageView.width, imageView.height)
                val w = (options.outWidth / inSampleSize)
                val h = (options.outHeight / inSampleSize)
                matrix.postScale(w * 1f / options.outWidth, h * 1f / options.outHeight)
                val realBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.RGB_565)
                val canvas = Canvas(realBitmap)
                canvas.drawBitmap(bitmap, matrix, null)
                imageView.setImageBitmap(realBitmap)
            }
        }
    }

    private fun calculateInSampleSize(
        options: BitmapFactory.Options,
        reqWidth: Int,
        reqHeight: Int
    ): Int {
        return options.outWidth / imageView.width
    }
}