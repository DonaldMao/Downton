package com.cndownton.app.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.cndownton.app.R;
import com.cndownton.app.downton.MyApplication;
import com.cndownton.app.downton.data.bean.Access_tokenCode;
import com.cndownton.app.downton.main.MainActivity;
import com.cndownton.app.downton.util.JsonUitl;
import com.cndownton.app.downton.util.SharedPreferencesUtil;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.logging.Logger;

import okhttp3.Call;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wxentry);
        MyApplication.api.handleIntent(getIntent(),this);
    }

    @Override
    public void onReq(BaseReq baseReq) {
    }

    @Override
    public void onResp(BaseResp baseResp) {
        switch (baseResp.errCode){
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                Toast.makeText(this,"登录失败",Toast.LENGTH_LONG).show();

                break;
            case BaseResp.ErrCode.ERR_OK:
                Toast.makeText(this,"登录成功",Toast.LENGTH_LONG).show();
                String url="https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx5269ea51c51983b1&" +
                        "secret=14619a7548b2f56fdc3c20cf52d01a36&code="+((SendAuth.Resp)baseResp).code+"&grant_type=authorization_code";
                OkHttpUtils.get().url(url).build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
        
                    }
    
                    @Override
                    public void onResponse(String response, int id) {
                    Access_tokenCode code= (Access_tokenCode) JsonUitl.INSTANCE.stringToObject(response,Access_tokenCode.class);
                        SharedPreferencesUtil util =new SharedPreferencesUtil(WXEntryActivity.this,"data_wx");
                        util.put("token",code.getAccess_token());
                        util.put("refreshToken",code.getRefresh_token());
                        util.put("expires",code.getExpires_in());
                        
                    }
                });
                
                startActivity(new Intent(this, MainActivity.class));
                break;

        }

    }
}
