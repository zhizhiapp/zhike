package fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.administrator.zhike.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import base.BaseApplication;

public class InvitingFriends2Fragment extends Fragment implements View.OnLongClickListener {
    private View fragmentView;
    private View containerView;
    private boolean isSavePhoto = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (fragmentView == null) {
            fragmentView = inflater.inflate(R.layout.fl_inviting_friends_2, container, false);
            initView(fragmentView);
        }
        return fragmentView;
    }

    private void initView(View view) {
        if (view != null) {
            containerView = view.findViewById(R.id.fl_container);
            containerView.setOnLongClickListener(this);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isSavePhoto = true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (fragmentView != null) {
            ViewGroup viewGroup = (ViewGroup) fragmentView.getParent();
            if (viewGroup != null) {
                viewGroup.removeView(fragmentView);
            }
        }
    }


    public Bitmap createViewBitmap(View v) {
        Bitmap bitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        v.draw(canvas);
        return bitmap;
    }


    @Override
    public boolean onLongClick(View view) {
        if (view.getId() == R.id.fl_container) {
            if (isSavePhoto) {
                try {
                    String path = BaseApplication.getInvitingFriendsImgPath();
                    if (!TextUtils.isEmpty(path)) {
                        File pathFile = new File(path);
                        if (!pathFile.exists()) {
                            pathFile.mkdir();
                        }
                        File fileName = new File(path, "QR_CODE.JPG");
                        Bitmap bitmap = createViewBitmap(containerView);
                        FileOutputStream stream = new FileOutputStream(fileName);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                        stream.flush();
                        stream.close();
                        if (fileName.exists()) {
                            Toast.makeText(getActivity(), "保存成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "保存失败", Toast.LENGTH_SHORT).show();
                        }
                        //更新系统相册
                        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                        Uri uri = Uri.fromFile(fileName);
                        intent.setData(uri);
                        getActivity().sendBroadcast(intent);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }
}
