package com.happypiebinliu.happymatch.Fragment;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
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
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_CANCELED;

/**
 * Created by B.Liu on 2016/10/27.
 */

public class MatchAddFragment extends BaseFragment implements ITabClickListener, View.OnClickListener {
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
    public static final int REQUESTCODE_PICK = 0;
    public static final int REQUESTCODE_TAKE = 1;
    private static final String IMAGE_FILE_NAME = "avatarImage.jpg";

    private Uri photoUri;
    public static final int SELECT_PIC_BY_TACK_PHOTO = 1;
    public static final int SELECT_PIC_BY_PICK_PHOTO = 2;
    public static final int REQUESTCODE_CUTTING = 3;
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
        Drawable drawable = getResources().getDrawable(R.drawable.upload);
        imageView.setBackground(drawable);

        mContext = getContext();
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
        Drawable drawable;
        switch (view.getId()){
            case R.id.btnSelectTop:
                // 选择 按钮按下，选择图片的取得方式 相册或者相机
                popupWindow = new SelectPicPopupWindow(mContext, itemsOnClick);
                // 这个地方一定要写getActivity().findViewById
                popupWindow.showAtLocation(getActivity().findViewById(R.id.TabAddLayout),
                        Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.btnUploadTop:
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

    private View.OnClickListener itemsOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            popupWindow.dismiss();
            switch (v.getId()) {
                // 拍照
                case R.id.takePhotoBtn:
                   /* Intent takeIntent = new Intent();
                    File file = new File(Environment.getExternalStorageDirectory().getPath(), IMAGE_FILE_NAME);
                    takeIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                    takeIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                    startActivityForResult(takeIntent, REQUESTCODE_TAKE);*/
                    takePhoto();
                    break;
                // 相册选择
                case R.id.pickPhotoBtn:
                    Intent pickIntent = new Intent(Intent.ACTION_GET_CONTENT, null);
                    pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                    startActivityForResult(pickIntent, REQUESTCODE_PICK);
                    //pickPhoto();
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 拍照获取图片
     */
    private void takePhoto() {
        // 执行拍照前，应该先判断SD是否存在
        String SDState = Environment.getExternalStorageState();
        if (SDState.equals(Environment.MEDIA_MOUNTED)) {

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            /***
             * 需要说明一下，以下操作使用照相机拍照，拍照后的图片会存放在相册中的
             * 这里使用的这种方式有一个好处就是获取的图片是拍照后的原图
             * 如果不使用ContentValues存放照片路径的话，拍照后获取的图片为缩略图不清晰
             */
            ContentValues values = new ContentValues(1);
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpg");
            photoUri = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION
                    | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

            intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, photoUri);
            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);

            this.startActivityForResult(intent, REQUESTCODE_TAKE);
        } else {
            Toast.makeText(mContext, "内存卡不存在", Toast.LENGTH_LONG).show();
        }
    }

    /***
     * 从相册中取图片
     */
    private void pickPhoto() {
        Intent intent = new Intent();
        // 如果要限制上传到服务器的图片类型时可以直接写如："image/jpeg 、 image/png等的类型"
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, SELECT_PIC_BY_PICK_PHOTO);
    }
    /**
     *  读取照片后的返回
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_CANCELED){
            return;
        }
        switch (requestCode) {
            case REQUESTCODE_PICK :
                try {
                    startSimplePhotoZoom(data.getData());
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                break;
            case REQUESTCODE_TAKE :
                File temp = new File(Environment.getExternalStorageDirectory().getPath(), IMAGE_FILE_NAME);
                startSimplePhotoZoom(data.getData());
                break;
            case REQUESTCODE_CUTTING:
                if (data != null) {
                    setPicToView(data);
                }
                break;
            default:
                break;
        }
    }


    /**
     *　简单裁切处理
     * @param uri
     */
    public void startSimplePhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, REQUESTCODE_CUTTING);
    }

    /**
     * 保存裁切后的图片
     * @param picdata
     */
    private void setPicToView(Intent picdata) {
        Bundle extras = picdata.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            Drawable drawable = new BitmapDrawable(null, photo);
            urlpath = FileUtil.saveFile(mContext, "avatarImage.jpg", photo);
            imageView.setImageDrawable(drawable);

           //pd = ProgressDialog.show(mContext, null, "正在上传图片，请稍候...");
            //new Thread(uploadImageRunnable).start();
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
