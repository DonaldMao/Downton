package com.cndownton.app.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.cndownton.app.R;
import com.cndownton.app.downton.MyApplication;
import com.cndownton.app.downton.main.MainActivity;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

import java.util.logging.Logger;

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
                startActivity(new Intent(this, MainActivity.class));
                break;

        }

    }
}
