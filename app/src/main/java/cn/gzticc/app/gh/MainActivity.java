package cn.gzticc.app.gh;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import cn.gzticc.app.gh.constant.AppConstants;
import cn.gzticc.app.gh.qq.QQLogin;
import com.tencent.connect.common.Constants;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.Tencent;

import cn.gzticc.app.gh.R;

public class MainActivity extends AppCompatActivity{

    private QQLogin qqLogin;
    private IWXAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        qqLogin = new QQLogin(this);
        api = WXAPIFactory.createWXAPI(this, AppConstants.WEIXIN_APP_ID, false);
    }
    public void qqLogin(View view){
        if (!QQLogin.mTencent.isSessionValid()) {
            //QQLogin.mTencent.login(this, "all", qqLogin);
            QQLogin.mTencent.login(this, "get_simple_userinfo", qqLogin);
        }
    }

    public void wxLogin(View view){
/*        if (api == null) {
            api = WXAPIFactory.createWXAPI(this, AppConstants.WEIXIN_APP_ID, false);
        }*/
        if(api != null){
            api.registerApp(AppConstants.WEIXIN_APP_ID);
            SendAuth.Req req = new SendAuth.Req();
            req.scope = "snsapi_userinfo";
            req.state = "wechat_sdk_demo";
            api.sendReq(req);
        }

    }




/*    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {
        switch (baseReq.getType()) {

            case ConstantsAPI.COMMAND_GETMESSAGE_FROM_WX:
                break;
            case ConstantsAPI.COMMAND_SHOWMESSAGE_FROM_WX:
                break;
            default:
                break;
        }
    }

    @Override
    public void onResp(BaseResp baseResp) {
        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                String code = ((SendAuth.Resp) baseResp).code;
                new WeiXinLogin(this).getWeiXinInfo(code);
                Toast.makeText(this, R.string.auth_success, Toast.LENGTH_SHORT)
                        .show();
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                Toast.makeText(this, R.string.auth_cancel, Toast.LENGTH_SHORT)
                        .show();
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                Toast.makeText(this, R.string.auth_failure, Toast.LENGTH_SHORT)
                        .show();
                break;
            default:
                break;
        }
    }*/


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //适配低端手机
        if(requestCode == Constants.REQUEST_LOGIN){
            Tencent.onActivityResultData(requestCode, resultCode, data,qqLogin);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
