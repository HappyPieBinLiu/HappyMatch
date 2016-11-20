package com.happypiebinliu.happymatch.Fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.happypiebinliu.happymatch.R;
import com.happypiebinliu.happymatch.activity.TabAddActivity;
import com.happypiebinliu.happymatch.common.FileUtil;
import com.happypiebinliu.happymatch.common.ITabClickListener;
import com.happypiebinliu.happymatch.common.LogUtil;
import com.happypiebinliu.happymatch.common.NetUtil;
import com.happypiebinliu.happymatch.view.SelectPicPopupWindow;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.SimpleTimeZone;

import static android.app.Activity.RESULT_CANCELED;
import static com.happypiebinliu.happymatch.common.LogUtil.TAG_DEBUG;

/**
 * Created by B.Liu on 2016/10/27.
 */

public class MatchAddFragment extends BaseFragment implements ITabClickListener, View.OnClickListener {

    LogUtil logUtil;
    private View view ;
    private Button selectBtn;
    private Button uploadBtn;
    private Button changeBtn;
    private Button addLineBtn;
    private ImageView imageView;
    private SelectPicPopupWindow popupWindow;
    private Context mContext;
    private String urlpath;
    private String resultStr = "";
    private static ProgressDialog pd;

    public static final int REQUEST_CODE_PICK = 0;
    public static final int REQUEST_CODE_TAKE = 1;
    public static final int REQUEST_CODE_CUTTING = 3;

    // The permission of camera
    public static final int PERMISSION_CAMERA = 10;
    // The permission of storage
    public static final int PERMISSION_STORAGE = 11;

    private static final String IMAGE_FILE_NAME = "avatarImage.jpg";
    // 拍照后的照片存储Ｕｒｉ
    private Uri takePhotoUri;
    // 照片的Path
     private String currentTakePath;

    private String picPath = "";
    private String imgUrl = "";

    @Override
    public void fetchData() {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        logUtil.debug(TAG_DEBUG, "onCreateView-Start--------");
        view = inflater.inflate(R.layout.tab_add_layout, container, false);

        selectBtn = (Button) view.findViewById(R.id.btnSelectTop);
        uploadBtn = (Button) view.findViewById(R.id.btnUploadTop);
        changeBtn = (Button) view.findViewById(R.id.btnChangeTop);
        addLineBtn = (Button) view.findViewById(R.id.addLineTopBtn);
        selectBtn.setOnClickListener(this);
        uploadBtn.setOnClickListener(this);
        changeBtn.setOnClickListener(this);
        addLineBtn.setOnClickListener(this);

        // imageView
        imageView = (ImageView) view.findViewById(R.id.TopImage);

        mContext = getContext();
        logUtil.info(TAG_DEBUG,"onCreateView-End--------");
        return view;
    }

    @Override
    public void onMenuItemClick() {
    }

    @Override
    public BaseFragment getFragment() {
        return this;
    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
    }

    @Override
    public void onClick(View view) {
        Intent intent;

        switch (view.getId()){
            case R.id.btnSelectTop:
                // 选择 按钮按下，选择图片的取得方式 相册或者相机
                popupWindow = new SelectPicPopupWindow(mContext, itemsOnClick);
                // 这个地方一定要写getActivity().findViewById
                popupWindow.showAtLocation(getActivity().findViewById(R.id.TabAddLayout),
                        Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.btnUploadTop:
                // 当前图片上传并且重命名
                uploadPicToMatch();
                break;
            case R.id.btnChangeTop:
                intent = new Intent(getContext(), TabAddActivity.class);
                startActivity(intent);
                break;
            case R.id.addLineTopBtn:
                break;
            default:
                break;
        }
    }

    /**
     * 1，进行图片的保存和重命名
     * 2，对于原来图片的删除处理
     * 3，跳转MatchTAB
     */
    private void uploadPicToMatch() {
        Intent intent = new Intent(getContext(), TabAddActivity.class);
        startActivity(intent);
    }

    private View.OnClickListener itemsOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            popupWindow.dismiss();

            switch (v.getId()) {
                // 拍照
                case R.id.takePhotoBtn:
                    // 进行相机权限的Grant
                    checkPermission(PERMISSION_CAMERA);
                    break;
                // 相册选择
                case R.id.pickPhotoBtn:
                    // 选择照片处理
                    pickPhoto();
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * check for the permission.
     * SDK > 23
     * @param permission
     */
    private void checkPermission(int permission) {
        logUtil.debug(TAG_DEBUG,"checkPermission-Start--------");
        int isHasPermission;
        switch (permission){
            case PERMISSION_CAMERA:
                logUtil.debug(TAG_DEBUG,"checkPermission-PERMISSION_CAMERA--------");
                isHasPermission = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA);
                if (isHasPermission != PackageManager.PERMISSION_GRANTED) {
                    logUtil.debug(TAG_DEBUG,"checkPermission-　Has not Permission--------");
                    // Fragment 自身的shouldShowRequestPermissionRationale函数
                    if (!shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {

                        showRationaleDialog(getResources().getString(R.string.permission_camera_rationale),
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        ActivityCompat.requestPermissions(getActivity(),
                                                new String[]{Manifest.permission.CAMERA},
                                                PERMISSION_CAMERA);
                                    }
                                });
                        return;
                    }
                    // Fragment 自身的shouldShowRequestPermissionRationale函数
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, PERMISSION_CAMERA);
                    return;
                }
                // 存储权限的判断
                checkPermission(PERMISSION_STORAGE);
                break;
            case PERMISSION_STORAGE:
                logUtil.debug(TAG_DEBUG,"checkPermission-PERMISSION_STORAGE--------");
                isHasPermission = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if (isHasPermission != PackageManager.PERMISSION_GRANTED) {
                    if (!shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        logUtil.debug(TAG_DEBUG,"checkPermission-　Has not Permission--------");
                        showRationaleDialog(getResources().getString(R.string.permission_camera_rationale),
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        ActivityCompat.requestPermissions(getActivity(),
                                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                                PERMISSION_CAMERA);
                                    }
                                });
                        return;
                    }
                    // Fragment 自身的shouldShowRequestPermissionRationale函数
                    requestPermissions(
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            PERMISSION_STORAGE);
                    return;
                }
                // 已经获得权限的，直接进行拍照
                takePhoto();
                break;
            default:
                break;
        }
        logUtil.debug(TAG_DEBUG, "checkPermission-END--------");
        return;
    }

    /**
     * 创建照片的存放路径
     *
     * @return  takeImage 图片File
     */
    private File createImageFile() throws Exception{

        // 取得一个时间 防止命名的冲突
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "TEMP_" + timeStamp + "_";
        // 这个函数的数据，会在用户卸载的时候删除
        File fileStorageDir = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File takeImage = File.createTempFile(imageFileName, ".jpg", fileStorageDir);
        // 得到绝对路径，
        currentTakePath = takeImage.getAbsolutePath();
        return takeImage;

    }

    /**
     * 拍照获取图片
     * 需要说明一下，以下操作使用照相机拍照，拍照后的图片会存放在相册中的
     * 这里使用的这种方式有一个好处就是获取的图片是拍照后的原图
     * 如果不使用ContentValues存放照片路径的话，拍照后获取的图片为缩略图不清晰
     */
    private void takePhoto() {

        logUtil.debug(TAG_DEBUG, "takePhoto-Start--------");

        // 执行拍照前，应该先判断SD是否存在
        String SDState = Environment.getExternalStorageState();

        if (SDState.equals(Environment.MEDIA_MOUNTED)) {

            Intent takeIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

            if (takeIntent.resolveActivity(getContext().getPackageManager()) != null){
                File takeFile = null;
                try{
                    takeFile =  createImageFile();
                } catch (Exception e) {
                    LogUtil.error(TAG_DEBUG, "createImageFile is failed!!");
                }
                if (takeFile != null){
                    takePhotoUri = FileProvider.getUriForFile(getContext(),
                            "com.happypiebinliu.happymatch.fileprovider", takeFile);
                    takeIntent.putExtra(MediaStore.EXTRA_OUTPUT, takePhotoUri);
                    startActivityForResult(takeIntent, REQUEST_CODE_TAKE);

                }
            }
            /*ContentValues values = new ContentValues(1);
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpg");
            takePhotoUri = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION
                    | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

            intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, takePhotoUri);
            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);*/

        } else {
            Toast.makeText(mContext, "内存卡不存在", Toast.LENGTH_LONG).show();
        }
        logUtil.debug(TAG_DEBUG, "takePhoto-End--------");
    }

    /***
     * 从相册中取图片
     */
    private void pickPhoto() {

        logUtil.debug(TAG_DEBUG, "pickPhoto-Start--------");
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_CODE_PICK);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        logUtil.debug(TAG_DEBUG, "onRequestPermissionsResult-Start--------");
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case PERMISSION_CAMERA:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    checkPermission(PERMISSION_STORAGE);
                }
                break;
            case PERMISSION_STORAGE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    takePhoto();
                }
                break;
            default:
                break;
        }
        logUtil.debug(TAG_DEBUG, "onRequestPermissionsResult-End--------");
        return;
    }

    /**
     *  读取照片后的返回
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        logUtil.debug(TAG_DEBUG, "onActivityResult-Start--------");
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_CANCELED){
            return;
        }
        switch (requestCode) {
            case REQUEST_CODE_PICK :
                try {
                    // 取得照片的裁切
                    startSimplePhotoZoom(data.getData());
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                break;
            case REQUEST_CODE_TAKE :

                if (takePhotoUri != null){
                    // 取得照片的裁切
                    setPicToView(data, true);
                }
                break;
            case REQUEST_CODE_CUTTING:
                if (data != null) {
                    // 裁切后照片Ｖｉｅｗ上的显示
                    setPicToView(data, false);
                }
                break;
            default:
                break;
        }
        logUtil.debug(TAG_DEBUG, "onActivityResult-End--------");
    }

    private void showRationaleDialog(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(getContext())
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }
    /**
     *　简单裁切处理
     * @param uri
     */
    public void startSimplePhotoZoom(Uri uri) {

        logUtil.debug(TAG_DEBUG, "startSimplePhotoZoom-Start--------");

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, REQUEST_CODE_CUTTING);
        logUtil.debug(TAG_DEBUG, "startSimplePhotoZoom-End--------");
    }

    /**
     * 保存裁切后的图片
     * @param data
     */
    private void setPicToView(Intent data, boolean isTaking) {
        logUtil.debug(TAG_DEBUG, "setPicToView-Start--------");
        if (isTaking){

            // Get the dimensions of the View
            int targetW = imageView.getWidth();
            int targetH = imageView.getHeight();

            // Get the dimensions of the bitmap
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(currentTakePath, bmOptions);
            int photoW = bmOptions.outWidth;
            int photoH = bmOptions.outHeight;

            // Determine how much to scale down the image
            int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

            // Decode the image file into a Bitmap sized to fill the View
            bmOptions.inJustDecodeBounds = false;
            bmOptions.inSampleSize = scaleFactor;
            bmOptions.inPurgeable = true;

            Bitmap bitmap = BitmapFactory.decodeFile(currentTakePath, bmOptions);
            imageView.setImageBitmap(bitmap);
        } else {

            Bundle extras = data.getExtras();
            if (extras != null) {
                Bitmap photo = extras.getParcelable("data");
                Drawable drawable = new BitmapDrawable(null, photo);
                urlpath = FileUtil.saveFile(mContext, "avatarImage.jpg", photo);

                imageView.setImageDrawable(drawable);
                logUtil.debug(TAG_DEBUG, "setPicToView-End--------");
               //pd = ProgressDialog.show(mContext, null, "正在上传图片，请稍候...");
                //new Thread(uploadImageRunnable).start();
            }
        }

    }
    /**
     * 使用HttpUrlConnection模拟post表单进行文件
     * 上传平时很少使用，比较麻烦
     * 原理是： 分析文件上传的数据格式，然后根据格式构造相应的发送给服务器的字符串。
     */
    Runnable uploadImageRunnable = new Runnable() {
        @Override
        public void run() {

            /*if(TextUtils.isEmpty(imgUrl)){
                Toast.makeText(mContext, "还没有设置上传服务器的路径！", Toast.LENGTH_SHORT).show();
                return;
            }*/

            Map<String, String> textParams = new HashMap<String, String>();
            Map<String, File> fileparams = new HashMap<String, File>();

            try {
                // 创建一个URL对象
                URL url = new URL(imgUrl);
                textParams = new HashMap<String, String>();
                fileparams = new HashMap<String, File>();
                // 要上传的图片文件
                File file = new File(picPath);
                fileparams.put("image", file);
                // 利用HttpURLConnection对象从网络中获取网页数据
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                // 设置连接超时（记得设置连接超时,如果网络不好,Android系统在超过默认时间会收回资源中断操作）
                conn.setConnectTimeout(5000);
                // 设置允许输出（发送POST请求必须设置允许输出）
                conn.setDoOutput(true);
                // 设置使用POST的方式发送
                conn.setRequestMethod("POST");
                // 设置不使用缓存（容易出现问题）
                conn.setUseCaches(false);
                // 在开始用HttpURLConnection对象的setRequestProperty()设置,就是生成HTML文件头
                conn.setRequestProperty("ser-Agent", "Fiddler");
                // 设置contentType
                conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + NetUtil.BOUNDARY);
                OutputStream os = conn.getOutputStream();
                DataOutputStream ds = new DataOutputStream(os);
                NetUtil.writeStringParams(textParams, ds);
                NetUtil.writeFileParams(fileparams, ds);
                NetUtil.paramsEnd(ds);
                // 对文件流操作完,要记得及时关闭
                os.close();
                // 服务器返回的响应吗
                int code = conn.getResponseCode(); // 从Internet获取网页,发送请求,将网页以流的形式读回来
                // 对响应码进行判断
                if (code == 200) {// 返回的响应码200,是成功
                    // 得到网络返回的输入流
                    InputStream is = conn.getInputStream();
                    resultStr = NetUtil.readString(is);
                } else {
                    //Toast.makeText(mContext, "请求URL失败！", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            handler.sendEmptyMessage(0);// 执行耗时的方法之后发送消给handler
        }
    };

    Handler handler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    pd.dismiss();

                    try {
                        JSONObject jsonObject = new JSONObject(resultStr);
                        // 服务端以字符串“1”作为操作成功标记
                        if (jsonObject.optString("status").equals("1")) {

                            // 用于拼接发布说说时用到的图片路径
                            // 服务端返回的JsonObject对象中提取到图片的网络URL路径
                            String imageUrl = jsonObject.optString("imageUrl");
                            // 获取缓存中的图片路径
                            Toast.makeText(mContext, imageUrl, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(mContext, jsonObject.optString("statusMessage"), Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }
            return false;
        }
    });
}
