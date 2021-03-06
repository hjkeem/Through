package jm.through.fragment;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leocardz.link.preview.library.LinkPreviewCallback;
import com.leocardz.link.preview.library.SourceContent;
import com.leocardz.link.preview.library.TextCrawler;
import org.jetbrains.annotations.Nullable;
import jm.through.R;
import jm.through.activity.MessageActivity;


public class urlDialogFragment extends DialogFragment {
    static String url;
    static boolean flag = false;
    /**각종 뷰 변수 선언*/
    ImageView web_image_Imageview;
    TextView web_title_textview;
    TextView web_contents_textview;
    TextView web_url_textview;
    Button web_yesbtn_button;
    Button web_nobtn_button;
    View v;
    Context context;

    TextCrawler textCrawler = new TextCrawler();
    Bitmap[] currentImageSet;
    Bitmap currentImage;

    /** mWebViewClient 에서 url을 가져오기 */
    public static void getURL(String text){
        url = text;
    }

    /** urlDialogFragment 작동 확인 */
    public static boolean checkFlag(){
        if (flag == true) // 네
            return true;
        else // 아니요
            return false;
    }

    public static urlDialogFragment newInstance(){
        return new urlDialogFragment();
    }

    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        AlertDialog dialog;

        // 다이얼로그 외부 화면 흐리게 표현
        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        // getWindow().setAttributes(lpWindow);

        v = inflater.inflate(R.layout.fragment_content, null);

        web_image_Imageview = v.findViewById(R.id.web_image);
        web_title_textview = v.findViewById(R.id.web_title);
        web_contents_textview = v.findViewById(R.id.web_contents);
        web_url_textview = v.findViewById(R.id.web_url);

        web_yesbtn_button = v.findViewById(R.id.web_btn_yes);
        web_nobtn_button = v.findViewById(R.id.web_btn_no);

        dialog = builder.create();
        dialog.setView(v);

        textCrawler.makePreview(callback, url);

        web_yesbtn_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                dialog.dismiss();
              // Intent intent = new Intent(Intent.ACTION_VIEW,)
              //  startActivity(intent)
                Log.v("dialogFM","open url");
                flag = true;
            }
        });

        web_nobtn_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                dialog.dismiss();
                Log.v("dialogFM","close url");
                flag = false;
            }
        });

        dialog.show();

        return dialog;
    }


    public interface urlDialogFragmentListener{
        public void urlDialogClick(DialogFragment dialog, String someData);
    }

    urlDialogFragmentListener urlListener;


    private LinkPreviewCallback callback = new LinkPreviewCallback() {
        private View dialogView;
        private ImageView imageView;
        private LinearLayout linearLayout;

        @Override
        public void onPre() {
            //preview를 만들기 전에 해아하는 것으로 보통 커스텀 preview layout을 설정함

        }

        @Override
        public void onPos(SourceContent sourceContent, boolean b) {
            if (sourceContent.getFinalUrl().equals("")){
//                View failed = getLayoutInflater().inflate(R.layout.failed,linearLayout);
            }
            else {
                currentImageSet = new Bitmap[sourceContent.getImages().size()];

                web_url_textview.setText(sourceContent.getFinalUrl());
                web_title_textview.setText(sourceContent.getTitle());
                web_contents_textview.setText(sourceContent.getDescription());
            }
        }
    };
}

