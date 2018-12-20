package com.empathy.empathy_android.ui.camera

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.app.Fragment
import android.content.Context
import android.content.Context.WINDOW_SERVICE
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.graphics.*
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraCaptureSession
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraDevice
import android.hardware.camera2.CameraManager
import android.hardware.camera2.CaptureRequest
import android.hardware.camera2.CaptureResult
import android.hardware.camera2.TotalCaptureResult
import android.media.ImageReader
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.util.DisplayMetrics
import android.util.Log
import android.util.Size
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.legacy.app.FragmentCompat
import com.bumptech.glide.Glide
import com.empathy.empathy_android.R
import com.facebook.FacebookSdk.getApplicationContext
import kotlinx.android.synthetic.main.content_camera_bottom.*
import kotlinx.android.synthetic.main.fragment_camera_basic.*
import org.jetbrains.anko.runOnUiThread
import java.io.IOException
import java.util.ArrayList
import java.util.Arrays
import java.util.Collections
import java.util.Comparator
import java.util.concurrent.Semaphore
import java.util.concurrent.TimeUnit
import kotlin.coroutines.experimental.coroutineContext

class CameraBasicFragment : Fragment(),FragmentCompat.OnRequestPermissionsResultCallback {

    private val lock = Any()
    private var runClassifier = false
    private var checkedPermissions = false
    private var isFlashOn = false
    private var textureView: AutoFitTextureView? = null
    private var layoutFrame: AutoFitFrameLayout? = null
    private var isFacingFront : Boolean = false

    /**
     * [TextureView.SurfaceTextureListener] TextureView의 라이프사이클을 관리합니다.
     */

    private val surfaceTextureListener = object : TextureView.SurfaceTextureListener {

        override fun onSurfaceTextureAvailable(
                texture: SurfaceTexture,
                width: Int,
                height: Int
        ) {
            openCamera(width, height)
        }

        override fun onSurfaceTextureSizeChanged(
                texture: SurfaceTexture,
                width: Int,
                height: Int
        ) {
            configureTransform(width, height)
        }

        override fun onSurfaceTextureDestroyed(texture: SurfaceTexture): Boolean {
            return true
        }

        override fun onSurfaceTextureUpdated(texture: SurfaceTexture) {}
    }
    /**
     * 현재 [CameraDevice]의 ID 값.
     */
    private var cameraId: String? = null

    /**
     * 카메라 프리뷰 화면의 [CameraCaptureSession].
     */
    private var captureSession: CameraCaptureSession? = null

    /**
     * 현재 열려있는 [CameraDevice]를 참조합니다.
     */
    private var cameraDevice: CameraDevice? = null

    /**
     * [android.util.Size] 카메라 프리뷰 사이즈
     */
    private var previewSize: Size? = null

    /**
     * [CameraDevice]의 상태값이 바뀔 때, [CameraDevice.StateCallback] 이 호출됩니다.
     */
    private val stateCallback = object : CameraDevice.StateCallback() {

        override fun onOpened(currentCameraDevice: CameraDevice) {
            // This method is called when the camera is opened.  We start camera preview here.
            cameraOpenCloseLock.release()
            cameraDevice = currentCameraDevice
            createCameraPreviewSession()
        }

        override fun onDisconnected(currentCameraDevice: CameraDevice) {
            cameraOpenCloseLock.release()
            currentCameraDevice.close()
            cameraDevice = null
        }

        override fun onError(
                currentCameraDevice: CameraDevice,
                error: Int
        ) {
            cameraOpenCloseLock.release()
            currentCameraDevice.close()
            cameraDevice = null
            val activity = activity
            activity?.finish()
        }
    }

    /**
     * mobilenet-ssd 추론을 정기적으로 실행하기 위한 추가 스레드 할당
     */
    private var backgroundThread: HandlerThread? = null

    /**
     * 백그라운드 스레드에서 태스크를 실행하기 위한 [Handler]
     */
    private var backgroundHandler: Handler? = null

    /**
     * 이미지 캡처본(사진 결과물)을 관리하는 [ImageReader]
     */
    private var imageReader: ImageReader? = null

    /**
     * 카메라 프리뷰의 [CaptureRequest.Builder]
     */
    private var previewRequestBuilder: CaptureRequest.Builder? = null

    /**
     * [.previewRequestBuilder]에 의해 생성된 [CaptureRequest]
     */
    private var previewRequest: CaptureRequest? = null

    /**
     * [Semaphore] 카메라를 닫기 전에 앱이 종료되지 않도록 합니다.
     */
    private val cameraOpenCloseLock = Semaphore(1)

    /**
     * 캡처와 관련된 이벤트를 관리해주는 [CameraCaptureSession.CaptureCallback]
     */
    private val captureCallback = object : CameraCaptureSession.CaptureCallback() {

        override fun onCaptureProgressed(
                session: CameraCaptureSession,
                request: CaptureRequest,
                partialResult: CaptureResult
        ) {
        }

        override fun onCaptureCompleted(
                session: CameraCaptureSession,
                request: CaptureRequest,
                result: TotalCaptureResult
        ) {
        }
    }

    private val requiredPermissions: Array<String>
        get() {
            val activity = activity
            return try {
                val info = activity!!
                        .packageManager
                        .getPackageInfo(activity.packageName, PackageManager.GET_PERMISSIONS)
                val ps = info.requestedPermissions
                if (ps != null && ps.isNotEmpty()) {
                    ps
                } else {
                    arrayOf()
                }
            } catch (e: Exception) {
                arrayOf()
            }

        }

    /**
     * 사진을 찍은 후에 mobilenet-ssd에서 classifyFrame()
     */
    private val periodicClassify = object : Runnable {
        override fun run() {
            synchronized(lock) {
                // TODO : If
            }
            backgroundHandler!!.post(this)
        }
    }

    /**
     * UI 스레드를 통해 [Toast] 메세지를 보여줍니다.
     *
     * @param text 보여줄 메세지
     */
    private fun showToast(text: String) {
        val activity = activity
        activity?.runOnUiThread {

        }
    }

    /**
     * 카메라 프리뷰와 버튼 레이아웃을 보여줍니다.
     */
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_camera_basic, container, false)
    }

    /**
     * 화면 위의 View Group 요소들을 연결합니다
     */
    override fun onViewCreated(
            view: View,
            savedInstanceState: Bundle?
    ) {
        textureView = view.findViewById(R.id.texture)
        layoutFrame = view.findViewById(R.id.layout_frame)

        setOnClickForTopHolder()
        setOnClickForBottomHolder()
        setOnClickForRatioBtns()

    }

    /**
     * mobilenet-ssd 추론을 실행하기 위한 백그라운드 스레드 실행 시작
     */
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        try {

        } catch (e: IOException) {
            Log.e(TAG, "Failed to initialize an image classifier.", e)
        }

        startBackgroundThread()
    }

    override fun onResume() {
        super.onResume()
        startBackgroundThread()

        /**
         * 화면을 껐다가 다시 켜면 SurfaceTextureTextureTexture가 이미 사용 가능하며,
         * "OnSurfaceTextureAvailable"은 호출되지 않습니다.
         * 이 경우 카메라를 열고 여기서 미리 보기를 시작할 수 있습니다
         * (또는 표면이 SurfaceTextureListener에서 준비될 때까지 기다림)
         */
        if (textureView!!.isAvailable) {
            openCamera(textureView!!.width, textureView!!.height)
        } else {
            textureView!!.surfaceTextureListener = surfaceTextureListener
        }
    }

    private fun setOnClickForTopHolder(){
        imgBtnFlash.setOnClickListener {
            if(isFlashOn) {
                imgBtnFlash.setImageDrawable(resources.getDrawable(R.drawable.ic_flash_off))
                previewRequestBuilder?.set(CaptureRequest.FLASH_MODE,CaptureRequest.FLASH_MODE_OFF)
                captureSession?.setRepeatingRequest(previewRequestBuilder!!.build(), null, null)
                isFlashOn = false
            }else {
                imgBtnFlash.setImageDrawable(resources.getDrawable(R.drawable.ic_flash_on))
                previewRequestBuilder?.set(CaptureRequest.FLASH_MODE,CaptureRequest.FLASH_MODE_TORCH)
                captureSession?.setRepeatingRequest(previewRequestBuilder!!.build(), null, null)
                isFlashOn = true
            }
        }
        imgBtnRatioSet.setOnClickListener {
            ratioDrawer.visibility = View.VISIBLE
        }
        imgBtnRotatePreview.setOnClickListener {
            isFacingFront = !isFacingFront
            closeCamera()
            openCamera(previewSize!!.width, previewSize!!.height)
        }
        imgBtnTerminate.setOnClickListener {
            closeCamera()
            activity.finish()

        }
    }

    private fun setOnClickForBottomHolder(){
        ivRecentPhoto.setOnClickListener {
        }
        imgBtnMoodFilter.setOnClickListener {

        }
        btnCapture.setOnClickListener {

        }
        imgBtnObjectFilter.setOnClickListener {

        }
        imgBtnPose.setOnClickListener {

        }
    }

    private fun setOnClickForRatioBtns(){
        ratio1_1.setOnClickListener {
            layoutFrame!!.setAspectRatio(1, 1)
            textureView!!.setAspectRatio(1, 1)
        }
        ratio3_4.setOnClickListener {
            layoutFrame!!.setAspectRatio(3, 4)
            textureView!!.setAspectRatio(3, 4)
        }
        ratio3_8.setOnClickListener {
            layoutFrame!!.setAspectRatio(8, 3)
            textureView!!.setAspectRatio(8, 3)
        }
        ratio9_16.setOnClickListener {
            layoutFrame!!.setAspectRatio(9, 16)
            textureView!!.setAspectRatio(9, 16)
        }
        ratioFull.setOnClickListener {
            layoutFrame!!.setAspectRatio(MAX_PREVIEW_HEIGHT, MAX_PREVIEW_WIDTH)
            textureView!!.setAspectRatio(MAX_PREVIEW_HEIGHT, MAX_PREVIEW_WIDTH)

        }
    }


    /**
     *  주어진 [Camera2BasicFragment.cameraId]의 카메라를 엽니다.
     */

    @SuppressLint("MissingPermission")
    private fun openCamera(
            width: Int,
            height: Int
    ) {
        if (!checkedPermissions && !allPermissionsGranted()) {
            FragmentCompat.requestPermissions(this, requiredPermissions, PERMISSIONS_REQUEST_CODE)
            return
        } else {
            checkedPermissions = true
        }
        setUpCameraOutputs(width, height)
        configureTransform(width, height)
        val activity = activity
        val manager = activity.getSystemService(Context.CAMERA_SERVICE) as CameraManager
        try {
            if (!cameraOpenCloseLock.tryAcquire(2500, TimeUnit.MILLISECONDS)) {
                throw RuntimeException("Time out waiting to lock camera opening.")
            }
            manager.openCamera(cameraId!!, stateCallback, backgroundHandler)
        } catch (e: CameraAccessException) {
            Log.e(TAG, "Failed to open Camera", e)
        } catch (e: InterruptedException) {
            throw RuntimeException("Interrupted while trying to lock camera opening.", e)
        }

    }
    /**
     * 카메라와 관련된 멤버 변수들을 정의합니다.
     *
     * @param width  카메라 프리뷰에 적용 가능한 넓이 사이즈
     * @param height 카메라 프리뷰에 적용 가능한 높이 사이즈
     */
    private fun setUpCameraOutputs(
            width: Int,
            height: Int
    ) {
        val activity = activity
        val manager = activity.getSystemService(Context.CAMERA_SERVICE) as CameraManager
        try {
            for (cameraId in manager.cameraIdList) {
                val characteristics = manager.getCameraCharacteristics(cameraId)

                if(isFacingFront){
                    if(characteristics.get(CameraCharacteristics.LENS_FACING)==CameraCharacteristics.LENS_FACING_BACK){
                        continue
                    }
                }else{
                    if(characteristics.get(CameraCharacteristics.LENS_FACING)==CameraCharacteristics.LENS_FACING_FRONT){
                        continue
                    }
                }
                val map =
                        characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP) ?: continue

                // // For still image captures, we use the largest available size.
                val largest = Collections.max(
                        Arrays.asList(*map.getOutputSizes(ImageFormat.JPEG)), CompareSizesByArea()
                )
                imageReader = ImageReader.newInstance(
                        largest.width, largest.height, ImageFormat.JPEG, /*maxImages*/ 2
                )

                // Find out if we need to swap dimension to get the preview size relative to sensor
                // coordinate.
                val displayRotation = activity.windowManager.defaultDisplay.rotation

                /* Orientation of the camera sensor */
                val sensorOrientation = characteristics.get(CameraCharacteristics.SENSOR_ORIENTATION)!!
                var swappedDimensions = false
                when (displayRotation) {
                    Surface.ROTATION_0, Surface.ROTATION_180 -> if (sensorOrientation == 90 || sensorOrientation == 270) {
                        swappedDimensions = true
                    }
                    Surface.ROTATION_90, Surface.ROTATION_270 -> if (sensorOrientation == 0 || sensorOrientation == 180) {
                        swappedDimensions = true
                    }
                    else -> Log.e(TAG, "Display rotation is invalid: $displayRotation")
                }

                val displaySize = Point()
                activity.windowManager.defaultDisplay.getSize(displaySize)
                var rotatedPreviewWidth = width
                var rotatedPreviewHeight = height
                var maxPreviewWidth = displaySize.x
                var maxPreviewHeight = displaySize.y

                if (swappedDimensions) {
                    rotatedPreviewWidth = height
                    rotatedPreviewHeight = width
                    maxPreviewWidth = displaySize.y
                    maxPreviewHeight = displaySize.x
                }

                if (maxPreviewWidth > MAX_PREVIEW_WIDTH) {
                    maxPreviewWidth = MAX_PREVIEW_WIDTH
                }

                if (maxPreviewHeight > MAX_PREVIEW_HEIGHT) {
                    maxPreviewHeight = MAX_PREVIEW_HEIGHT
                }

                previewSize = chooseOptimalSize(
                        map.getOutputSizes(SurfaceTexture::class.java),
                        rotatedPreviewWidth,
                        rotatedPreviewHeight,
                        maxPreviewWidth,
                        maxPreviewHeight,
                        largest
                )

                // We fit the aspect ratio of TextureView to the size of preview we picked.
                val orientation = resources.configuration.orientation
                if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    layoutFrame!!.setAspectRatio(previewSize!!.width, previewSize!!.height)
                    textureView!!.setAspectRatio(previewSize!!.width, previewSize!!.height)
                    //drawView!!.setAspectRatio(previewSize!!.width, previewSize!!.height)
                } else {
                    layoutFrame!!.setAspectRatio(previewSize!!.height, previewSize!!.width)
                    textureView!!.setAspectRatio(previewSize!!.height, previewSize!!.width)
                    //drawView!!.setAspectRatio(previewSize!!.height, previewSize!!.width)
                }

                this.cameraId = cameraId
                return
            }
        } catch (e: CameraAccessException) {
            Log.e(TAG, "Failed to access Camera", e)
        } catch (e: NullPointerException) {
            // Currently an NPE is thrown when the Camera2API is used but not supported on the
            // device this code runs.
            ErrorDialog.newInstance(getString(R.string.camera_error))
                    .show(childFragmentManager, FRAGMENT_DIALOG)
        }

    }
    /**
     *  현재 실행되고 있는 [CameraDevice]의 세션을 닫습니다.
     */
    private fun closeCamera() {
        try {
            cameraOpenCloseLock.acquire()
            if (null != captureSession) {
                captureSession!!.close()
                captureSession = null
            }
            if (null != cameraDevice) {
                cameraDevice!!.close()
                cameraDevice = null
            }
            if (null != imageReader) {
                imageReader!!.close()
                imageReader = null
            }
        } catch (e: InterruptedException) {
            throw RuntimeException("Interrupted while trying to lock camera closing.", e)
        } finally {
            cameraOpenCloseLock.release()
        }
    }
    private fun allPermissionsGranted(): Boolean {
        for (permission in requiredPermissions) {
            if (ContextCompat.checkSelfPermission(
                            activity, permission
                    ) != PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
        }
        return true
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<String>,
            grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
    /**
     * [Handler]와 함께 백그라운드 스레드를 실행합니다.
     */
    private fun startBackgroundThread() {
        backgroundThread = HandlerThread(HANDLE_THREAD_NAME)
        backgroundThread!!.start()
        backgroundHandler = Handler(backgroundThread!!.looper)
        synchronized(lock) {
            runClassifier = true
        }
        backgroundHandler!!.post(periodicClassify)
    }

    /**
     * [Handler]와 함께 백그라운드 스레드 실행을 중단합니다..
     */
    private fun stopBackgroundThread() {
        backgroundThread!!.quitSafely()
        try {
            backgroundThread!!.join()
            backgroundThread = null
            backgroundHandler = null
            synchronized(lock) {
                runClassifier = false
            }
        } catch (e: InterruptedException) {
            Log.e(TAG, "Interrupted when stopping background thread", e)
        }

    }

    /**
     * 카메라 프리뷰를 위한 [CameraCaptureSession] 을 생성합니다.
     */
    private fun createCameraPreviewSession() {
        try {
            val texture = textureView!!.surfaceTexture!!

            // We configure the size of default buffer to be the size of camera preview we want.
            texture.setDefaultBufferSize(previewSize!!.width, previewSize!!.height)

            // This is the output Surface we need to start preview.
            val surface = Surface(texture)

            // We set up a CaptureRequest.Builder with the output Surface.
            previewRequestBuilder = cameraDevice!!.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)
            previewRequestBuilder!!.addTarget(surface)

            // Here, we create a CameraCaptureSession for camera preview.
            cameraDevice!!.createCaptureSession(
                    Arrays.asList(surface),
                    object : CameraCaptureSession.StateCallback() {

                        override fun onConfigured(cameraCaptureSession: CameraCaptureSession) {
                            // The camera is already closed
                            if (null == cameraDevice) {
                                return
                            }

                            // When the session is ready, we start displaying the preview.
                            captureSession = cameraCaptureSession
                            try {
                                // Auto focus should be continuous for camera preview.
                                previewRequestBuilder!!.set(
                                        CaptureRequest.CONTROL_AF_MODE,
                                        CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE
                                )

                                // Finally, we start displaying the camera preview.
                                previewRequest = previewRequestBuilder!!.build()
                                captureSession!!.setRepeatingRequest(
                                        previewRequest!!, captureCallback, backgroundHandler
                                )
                            } catch (e: CameraAccessException) {
                                Log.e(TAG, "Failed to set up config to capture Camera", e)
                            }

                        }

                        override fun onConfigureFailed(cameraCaptureSession: CameraCaptureSession) {
                            showToast("Failed")
                        }
                    }, null
            )
        } catch (e: CameraAccessException) {
            Log.e(TAG, "Failed to preview Camera", e)
        }

    }

    /**
     * 필요한 [android.graphics.Matrix] textureView로 변환.
     * 이 메소드는 setUpCameraOutputs를 통해 카메라 프리뷰 사이즈가 정의 되고, texureView의 사이즈가 정해진 이후에 호출되어야 합니다.
     *
     * @param viewWidth  textureView 넓이
     * @param viewHeight textureView 높이
     */
    private fun configureTransform(
            viewWidth: Int,
            viewHeight: Int
    ) {
        val activity = activity
        if (null == textureView || null == previewSize || null == activity) {
            return
        }
        val rotation = activity.windowManager.defaultDisplay.rotation
        val matrix = Matrix()
        val viewRect = RectF(0f, 0f, viewWidth.toFloat(), viewHeight.toFloat())
        val bufferRect = RectF(0f, 0f, previewSize!!.height.toFloat(), previewSize!!.width.toFloat())
        val centerX = viewRect.centerX()
        val centerY = viewRect.centerY()
        if (Surface.ROTATION_90 == rotation || Surface.ROTATION_270 == rotation) {
            bufferRect.offset(centerX - bufferRect.centerX(), centerY - bufferRect.centerY())
            matrix.setRectToRect(viewRect, bufferRect, Matrix.ScaleToFit.FILL)
            val scale = Math.max(
                    viewHeight.toFloat() / previewSize!!.height,
                    viewWidth.toFloat() / previewSize!!.width
            )
            matrix.postScale(scale, scale, centerX, centerY)
            matrix.postRotate((90 * (rotation - 2)).toFloat(), centerX, centerY)
        } else if (Surface.ROTATION_180 == rotation) {
            matrix.postRotate(180f, centerX, centerY)
        }
        textureView!!.setTransform(matrix)
    }


    /**
     * 두 개의 'Size'를 비교합니다.
     */
    private class CompareSizesByArea : Comparator<Size> {

        override fun compare(
                lhs: Size,
                rhs: Size
        ): Int {
            // We cast here to ensure the multiplications won't overflow
            return java.lang.Long.signum(
                    lhs.width.toLong() * lhs.height - rhs.width.toLong() * rhs.height
            )
        }
    }

    /**
     * Error 메시지 다이얼로그를 띄어줍니다.
     */
    class ErrorDialog : DialogFragment() {

        override fun onCreateDialog(savedInstanceState: Bundle): Dialog {
            val activity = activity
            return AlertDialog.Builder(activity)
                    .setMessage(arguments.getString(ARG_MESSAGE))
                    .setPositiveButton(
                            android.R.string.ok
                    ) { dialogInterface, i -> activity.finish() }
                    .create()
        }

        companion object {

            private val ARG_MESSAGE = "message"

            fun newInstance(message: String): ErrorDialog {
                val dialog = ErrorDialog()
                val args = Bundle()
                args.putString(ARG_MESSAGE, message)
                dialog.arguments = args
                return dialog
            }
        }
    }


    companion object {

        /**
         * Tag [Log].
         */
        private const val TAG = "CameraBasicFragment"

        private const val FRAGMENT_DIALOG = "dialog"

        private const val HANDLE_THREAD_NAME = "CameraBackground"

        private const val PERMISSIONS_REQUEST_CODE = 1

        /**
         * Camera2 API에서 지원하는 최대 프리뷰 넓이
         */
        private const val MAX_PREVIEW_WIDTH = 2560

        /**
         * Camera2 API에서 지원하는 최대 프리뷰 높이
         */
        private const val MAX_PREVIEW_HEIGHT = 1440

        /**
         *
         * 이미지의 크기를 조정합니다.
         * 미리 보기 크기가 너무 크면 카메라 버스의 대역폭 제한을 초과할 수 있으므로 멋진 미리 보기가 제공되지만 가비지 캡처 데이터는 저장될 수 있습니다.
         * 카메라에서 지원하는 사이즈의 선택권을 감안해 각 텍스처 뷰 크기만큼 크고, 최대 크기만큼 크고, 가로 세로 비율과 일치하는 최소값을 선택합니다.
         * 이러한 크기가 존재하지 않는 경우, 최대 크기에 해당하는 최대 크기를 선택하고 가로 세로 비율이 지정된 값과 일치하는 최대 크기를 선택합니다.
         *
         * @param choices           카메라에서 원하는 출력 클래스에 대해 지원하는 크기 목록
         * @param textureViewWidth  센서 좌표를 기준으로 한 텍스처 뷰의 폭
         * @param textureViewHeight 센서 좌표를 기준으로 한 텍스처 뷰의 높이
         * @param maxWidth          선택할 수 있는 최대 너비
         * @param maxHeight         선택할 수 있는 최대 높이
         * @param aspectRatio       가로 세로 비율
         * @return 적정 'Size', 즉 어느 것도 충분히 크지 않을 경우 임의 크기
         */
        private fun chooseOptimalSize(
                choices: Array<Size>,
                textureViewWidth: Int,
                textureViewHeight: Int,
                maxWidth: Int,
                maxHeight: Int,
                aspectRatio: Size
        ): Size {

            // Collect the supported resolutions that are at least as big as the preview Surface
            val bigEnough = ArrayList<Size>()
            // Collect the supported resolutions that are smaller than the preview Surface
            val notBigEnough = ArrayList<Size>()
            val w = aspectRatio.width
            val h = aspectRatio.height
            for (option in choices) {
                if (option.width <= maxWidth
                        && option.height <= maxHeight
                        && option.height == option.width * h / w
                ) {
                    if (option.width >= textureViewWidth && option.height >= textureViewHeight) {
                        bigEnough.add(option)
                    } else {
                        notBigEnough.add(option)
                    }
                }
            }
            /**
             * 충분히 큰 것들 중에서 가장 작은 것을 골라라. 만약 충분히 큰 사람이 없다면,
             * 충분히 크지 않은 사람 중 가장 큰 것을 고를 것.
            */
            return when {
                bigEnough.size > 0 -> Collections.min(bigEnough, CompareSizesByArea())
                notBigEnough.size > 0 -> Collections.max(notBigEnough, CompareSizesByArea())
                else -> {
                    Log.e(TAG, "Couldn't find any suitable preview size")
                    choices[0]
                }
            }
        }

        fun newInstance(): CameraBasicFragment {
            return CameraBasicFragment()
        }
    }
}