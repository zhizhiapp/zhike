package base;

import android.app.Application;
import android.content.Context;
import android.support.annotation.Nullable;

import com.example.administrator.entity.SubjectEntity;
import com.example.administrator.utils.DescendingEncryption;
import com.example.administrator.utils.RxjavaRetrofitUtils;
import com.example.administrator.utils.SystemUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BaseApplication extends Application {
    private static Context context;

    public static final String TOKEN = "bf5401299b6491e521c210c2910fc6f99f6094fc";

    public static final String API_KEY = "3bdb25d93535b66fd13c16379d26f46fgzzzwh";


    /**
     * 存储所有科目信息
     */
    private static SubjectEntity entity;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
    /**
     * 获取邀请好友自定义个人二维码照片存放路径
     */
    public static String getInvitingFriendsImgPath() {
        File file = context.getExternalFilesDir(null);
        if (file != null && file.exists()) {
            return file.getAbsolutePath() + File.separator + "inviting_friends_qr_code";
        }
        return null;
    }

    /**
     * 获取科目列表
     *
     * @return
     */
    public static void getChoiceSubjects(@Nullable final SubjectListener listener) {
        if (entity == null) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", 1);
            DescendingEncryption.init(map);
            Call<SubjectEntity> call = RxjavaRetrofitUtils.getApi().getSubject(map, SystemUtils.getHeader(getContext()));
            call.enqueue(new Callback<SubjectEntity>() {
                @Override
                public void onResponse(Call<SubjectEntity> call, Response<SubjectEntity> response) {
                    entity = response.body();
                    if (listener != null) {
                        listener.onData(entity);
                    }
                }

                @Override
                public void onFailure(Call<SubjectEntity> call, Throwable t) {

                }
            });
        } else {
            if (listener != null) {
                listener.onData(entity);
            }
        }
    }


    public interface SubjectListener {
        void onData(SubjectEntity entity);
    }
}
