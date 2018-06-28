package org.incoder.security;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * AsymmetricFragment
 * DSA，ECC，RSA
 *
 * @author Jerry xu
 * @date 2018/06/14 10:31.
 */
public class AsymmetricFragment extends Fragment {

    @BindView(R.id.tv_dsa_content)
    TextView tvDsaContent;
    @BindView(R.id.tv_ecc_content)
    TextView tvEccContent;
    @BindView(R.id.tv_rsa_content)
    TextView tvRsaContent;
    @BindView(R.id.btn_public)
    Button btnPublic;
    @BindView(R.id.btn_private)
    Button btnPrivate;
    @BindView(R.id.btn_rsa_send)
    Button btnRsaSend;
    Unbinder unbinder;
    private String rsaContentTextView;
    private String sendRsaContent;

    @OnClick({R.id.btn_public, R.id.btn_private, R.id.btn_rsa_send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_public:
                getPublicKey();
                break;
            case R.id.btn_private:
                getPrivateAndDecode();
                break;
            case R.id.btn_rsa_send:
                sendRsa();
                break;
            default:
                break;
        }
    }

    /**
     * 获取私钥并解密
     */
    private void getPrivateAndDecode() {
        // 添加需要解密的文本
        if (addEncryptText()) {

        }
        RetrofitManage.getInstance().createService(ApiService.class)
                .getPrivateKey().enqueue(new Callback<Byte>() {
            @Override
            public void onResponse(@NonNull Call<Byte> call, @NonNull Response<Byte> response) {
                if (response.body() != null) {
                    rsaContentTextView = rsaContentTextView + "\n获取私钥：" + String.valueOf(response.body());
                    tvRsaContent.setText(rsaContentTextView);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Byte> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "获取公钥失败", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private boolean addEncryptText() {
        return false;
    }

    /**
     * 发送加密内容
     */
    private void sendRsa() {
        if (addEncryptText()) {

        }
    }

    /**
     * 获取公钥
     */
    private void getPublicKey() {
        RetrofitManage.getInstance().createService(ApiService.class)
                .getPublicKey().enqueue(new Callback<Byte>() {
            @Override
            public void onResponse(@NonNull Call<Byte> call, @NonNull Response<Byte> response) {
                if (response.body() != null) {
                    rsaContentTextView = "获取公钥：" + String.valueOf(response.body());
                    tvRsaContent.setText(rsaContentTextView);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Byte> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "获取公钥失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public AsymmetricFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_asymmetric, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
