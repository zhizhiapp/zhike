package com.example.administrator.adapter;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.constants.MyConstants;
import com.example.administrator.entity.IntelligentTestEntity;
import com.example.administrator.zhike.R;

import java.text.DecimalFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import base.BaseApplication;

/**
 * 管理类联考问题列表适配器
 */
public class IntelligentTestAdapter extends BaseQuickAdapter<IntelligentTestEntity, BaseViewHolder> implements View.OnClickListener {
    private Listener listener;
    private DecimalFormat decimalFormat;
    private int[] optionTextId = {R.id.tv_option_A, R.id.tv_option_B, R.id.tv_option_C, R.id.tv_option_D, R.id.tv_option_E};
    private int[] optionItemId = {R.id.ll_option_A, R.id.ll_option_B, R.id.ll_option_C, R.id.ll_option_D, R.id.ll_option_E};
    private int[] optionIcId = {R.id.fl_A, R.id.fl_B, R.id.fl_C, R.id.fl_D, R.id.fl_E};
    private int[] optionId = {R.id.tv_A, R.id.tv_B, R.id.tv_C, R.id.tv_D, R.id.tv_E};
    private int optionTvColor;

    public IntelligentTestAdapter(@Nullable List<IntelligentTestEntity> data, Listener listener) {
        super(R.layout.item_recycler_intelligent_test, data);
        this.listener = listener;
        decimalFormat = new DecimalFormat("0.00");
        optionTvColor = ContextCompat.getColor(BaseApplication.getContext(), R.color.homeText);
    }

    private Spanned html(String s, TextView textView, int position) {
        if (!TextUtils.isEmpty(s) && textView != null) {
            return Html.fromHtml(s, new URLImageParser(textView, position), null);
        }
        return null;

    }

    /**
     * 把html字符里面的大于> 小于<转义
     */
    private String escape(String s) {
        if (!TextUtils.isEmpty(s)) {
            return s.replace("<img", "html1").replace("jpg\">", "html2")
                    .replace("<", "&gt;").replace(">", "&lt;")
                    .replace("html1", "<img").replace("html2", "jpg\">");
        }
        return s;
    }

    /**
     * 判断是否小数
     */
    private static boolean isDecimal(String str) {
        return isMatch("[-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+", str);
    }

    private static boolean isMatch(String regex, String orginal) {
        if (orginal == null || orginal.trim().equals("")) {
            return false;
        }
        Pattern pattern = Pattern.compile(regex);
        Matcher isNum = pattern.matcher(orginal);
        return isNum.matches();
    }


    @Override
    protected void convert(BaseViewHolder helper, IntelligentTestEntity item) {
        setText(helper, item);
        changeColor(helper, item);
        setListener(helper);
    }

    private void setText(BaseViewHolder helper, IntelligentTestEntity item) {
        //问题
        String question = item.getQuestion();
        int position = helper.getAdapterPosition();
        String s = position + " . " + question;
        TextView view = helper.getView(R.id.tv_question);
        view.setText(html(escape(s), view, position));

        //选项
        for (int i = 0; i < optionTextId.length; i++) {
            String option = item.getOptions().get(i);
            if (isDecimal(option)) {
                option = decimalFormat.format(Double.parseDouble(option));
            }
            helper.setText(optionTextId[i], option);
        }
    }

    private void setListener(BaseViewHolder helper) {
        for (int id : optionItemId) {
            helper.getView(id).setTag(helper.getAdapterPosition());
            helper.getView(id).setOnClickListener(this);
        }
    }

    private void changeColor(BaseViewHolder helper, IntelligentTestEntity item) {
        int select = item.getSelect();
        switch (select) {
            case 0:
                for (int i = 0; i < optionId.length; i++) {
                    if (i == 0) {
                        helper.setTextColor(optionId[i], optionTvColor);
                        helper.setBackgroundRes(optionIcId[i], R.drawable.ic_option_on);
                    } else {
                        helper.setTextColor(optionId[i], Color.BLACK);
                        helper.setBackgroundRes(optionIcId[i], R.drawable.ic_option_un);
                    }
                }
                break;
            case 1:
                for (int i = 0; i < optionId.length; i++) {
                    if (i == 1) {
                        helper.setTextColor(optionId[i], optionTvColor);
                        helper.setBackgroundRes(optionIcId[i], R.drawable.ic_option_on);
                    } else {
                        helper.setTextColor(optionId[i], Color.BLACK);
                        helper.setBackgroundRes(optionIcId[i], R.drawable.ic_option_un);
                    }
                }
                break;
            case 2:
                for (int i = 0; i < optionId.length; i++) {
                    if (i == 2) {
                        helper.setTextColor(optionId[i], optionTvColor);
                        helper.setBackgroundRes(optionIcId[i], R.drawable.ic_option_on);
                    } else {
                        helper.setTextColor(optionId[i], Color.BLACK);
                        helper.setBackgroundRes(optionIcId[i], R.drawable.ic_option_un);
                    }
                }

                break;
            case 3:
                for (int i = 0; i < optionId.length; i++) {
                    if (i == 3) {
                        helper.setTextColor(optionId[i], optionTvColor);
                        helper.setBackgroundRes(optionIcId[i], R.drawable.ic_option_on);
                    } else {
                        helper.setTextColor(optionId[i], Color.BLACK);
                        helper.setBackgroundRes(optionIcId[i], R.drawable.ic_option_un);
                    }
                }
                break;
            case 4:
                for (int i = 0; i < optionId.length; i++) {
                    if (i == 4) {
                        helper.setTextColor(optionId[i], optionTvColor);
                        helper.setBackgroundRes(optionIcId[i], R.drawable.ic_option_on);
                    } else {
                        helper.setTextColor(optionId[i], Color.BLACK);
                        helper.setBackgroundRes(optionIcId[i], R.drawable.ic_option_un);
                    }
                }
                break;
            default:
                for (int i = 0; i < optionId.length; i++) {
                    helper.setTextColor(optionId[i], Color.BLACK);
                    helper.setBackgroundRes(optionIcId[i], R.drawable.ic_option_un);
                }
                break;
        }
    }

    private static boolean isNumber(String str) {
        for (int i = str.length(); --i >= 0; ) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        if (listener != null) {
            int position = (int) view.getTag();
            switch (view.getId()) {
                case R.id.ll_option_A:
                    listener.selectOption(0, position);
                    break;
                case R.id.ll_option_B:
                    listener.selectOption(1, position);
                    break;
                case R.id.ll_option_C:
                    listener.selectOption(2, position);
                    break;
                case R.id.ll_option_D:
                    listener.selectOption(3, position);
                    break;
                case R.id.ll_option_E:
                    listener.selectOption(4, position);
                    break;
            }
        }
    }


    private class URLImageParser implements Html.ImageGetter {
        private TextView textView;
        private int actX;
        private int actY;
        private int position;

        URLImageParser(TextView textView, int position) {
            this.position = position;
            this.textView = textView;
            //获取全屏大小
            DisplayMetrics metrics = BaseApplication.getContext().getResources().getDisplayMetrics();
            actX = metrics.widthPixels;
            //actY = metrics.heightPixels;
            actY = metrics.heightPixels / 2;
        }

        @Override
        public Drawable getDrawable(String source) {
            final String url = MyConstants.Service_URL + source;
            final URLDrawable urlDrawable = new URLDrawable();
            Glide.with(BaseApplication.getContext()).load(url).asBitmap().into(new SimpleTarget<Bitmap>() {
                @Override
                public void onLoadFailed(Exception e, Drawable errorDrawable) {
                    super.onLoadFailed(e, errorDrawable);
                    //  textView.setText(position + 1 + " . " + "图片加载失败");
                }

                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    int x = resource.getWidth();
                    int y = resource.getHeight();
                    if (x > actX || y > actY) {
                        //进行等比例缩放程序
                        Matrix matrix = new Matrix();
                        // matrix.postScale((float) (actX * 1.0 / x), (float) (actX * 1.0 / x));
                        matrix.postScale((float) (actX * 0.7 / x), (float) (actY * 0.5 / y));
                        //长和宽放大缩小的比例
                        resource = Bitmap.createBitmap(resource, 0, 0, x, y, matrix, true);
                    }/* else if (x <= actX / 3 || y <= actY / 3) {
                        Matrix matrix = new Matrix();
                        matrix.postScale((float) (2.3), (float) (2.3));
                        resource = Bitmap.createBitmap(resource, 0, 0, x, y, matrix, true);
                    }*/
                    urlDrawable.bitmap = resource;
                    urlDrawable.setBounds(0, 0, resource.getWidth(), resource.getHeight());
                    textView.invalidate();
                    textView.setText(textView.getText());
                }
            });
            return urlDrawable;
        }
    }

    private class URLDrawable extends BitmapDrawable {
        Bitmap bitmap;

        @Override
        public void draw(Canvas canvas) {
            if (bitmap != null) {
                canvas.drawBitmap(bitmap, 0, 0, getPaint());
            }
        }
    }

    public interface Listener {
        void selectOption(int option, int position);
    }

}
