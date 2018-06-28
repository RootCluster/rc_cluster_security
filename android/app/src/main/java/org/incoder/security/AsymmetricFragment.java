package org.incoder.security;


import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
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
    private AutoCompleteTextView actName;
    private TextInputLayout tiName;
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
        AlertDialog.Builder builder;
        final boolean[] isEditTextContent = new boolean[1];
        builder = new AlertDialog.Builder(getActivityNonNull());
        LayoutInflater inflater = getActivityNonNull().getLayoutInflater();
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.dialog_alert_add, null);
        actName = view.findViewById(R.id.act_name);
        tiName = view.findViewById(R.id.ti_name);
        builder.setView(view)
                .setTitle("文本")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (actName.getText().length() > 0) {
                            isEditTextContent[0] = true;
                        } else {
                            isEditTextContent[0] = false;
                        }
//                        isEditTextContent = actName.getText().length() > 0;
                    }
                })
                .setNegativeButton("取消", null).show();
        return true;
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

        RetrofitManage.getInstance().createService().getPublicKey()
                .enqueue(new Callback<PublicKeyBean>() {
                    @Override
                    public void onResponse(@NonNull Call<PublicKeyBean> call, @NonNull Response<PublicKeyBean> response) {
                        if (response.body() != null) {
                            rsaContentTextView = "获取公钥：" + response.body().getPublicKey().toString();
                            tvRsaContent.setText(rsaContentTextView);
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<PublicKeyBean> call, @NonNull Throwable t) {
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

    protected FragmentActivity getActivityNonNull() {
        if (super.getActivity() != null) {
            return super.getActivity();
        } else {
            throw new RuntimeException("null returned from getActivity()");
        }
    }
}
