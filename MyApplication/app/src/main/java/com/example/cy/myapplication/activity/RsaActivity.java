package com.example.cy.myapplication.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.cy.myapplication.R;
import com.example.cy.myapplication.util.RSAUtils;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

public class RsaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rsa);

        TextView textView3 = (TextView) findViewById(R.id.textView3);

        KeyPair keyPair = generateRSAKeyPair(2048);

        PublicKey 公钥 = null;
        try {
            公钥 = RSAUtils.loadPublicKey(RSAUtils.Base64Utils.encode(keyPair.getPublic().getEncoded()));
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        PrivateKey 私钥 = keyPair.getPrivate();

        //textView3.setText("公钥："+ RSAUtils.printPublicKeyInfo(公钥)+"\n\n"+"私钥："+RSAUtils.printPrivateKeyInfo(私钥));

        String pass = "你妈批你妈批，尼玛笑嘻嘻";

        byte[] bts = RSAUtils.encryptData(pass.getBytes(),公钥);
        bts = RSAUtils.decryptData(bts,私钥);
        textView3.setText(new String(bts));
    }


    /**
     * 随机生成RSA密钥对
     *
     * @param keyLength 密钥长度，范围：512～2048<br>
     *                  一般1024
     * @return
     */
    public static KeyPair generateRSAKeyPair(int keyLength) {
        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            kpg.initialize(keyLength);
            return kpg.genKeyPair();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
