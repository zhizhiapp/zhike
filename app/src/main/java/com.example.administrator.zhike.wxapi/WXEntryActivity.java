package com.example.administrator.zhike.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import com.example.administrator.constants.MyConstants;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelmsg.ShowMessageFromWX;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    // IWXAPI是第三方app和微信通信的openapi接口
    private IWXAPI api;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String APP_ID = MyConstants.WEIXIN_APP_ID;
        api = WXAPIFactory.createWXAPI(this, APP_ID, false);
        api.registerApp(APP_ID);
        api.handleIntent(getIntent(), this);

    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    // 微信发送请求到第三方应用时,会回调到该方法.
    public void onReq(BaseReq req) {
        switch (req.getType()) {
            case ConstantsAPI.COMMAND_GETMESSAGE_FROM_WX:
                goToGetMsg();
                break;
            case ConstantsAPI.COMMAND_SHOWMESSAGE_FROM_WX:
                goToShowMsg((ShowMessageFromWX.Req) req);
                break;
            default:
                break;
        }
    }

    // 第三方应用发送到微信的请求处理后的响应结果,会回调到该方法.
    public void onResp(BaseResp resp) {
        String result;
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:

                if (ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX == resp.getType()) {
                    result = "分享成功";
                    Toast.makeText(this, result, Toast.LENGTH_LONG).show();
                    break;
                }
                result = ((SendAuth.Resp) resp).code;// 成功
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:// 取消
                result = "取消操作";
                Toast.makeText(this, result, Toast.LENGTH_LONG).show();
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:// 认证失败
                result = "认证失败";
                Toast.makeText(this, result, Toast.LENGTH_LONG).show();
                break;

            default:
                result = "出现异常";
                Toast.makeText(this, result, Toast.LENGTH_LONG).show();
                break;
        }
        finish();
    }

    private void goToGetMsg() {
        finish();
    }
    private void goToShowMsg(ShowMessageFromWX.Req showReq) {
        finish();
    }
}