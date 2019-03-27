package com.guikai.latte.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.guikai.latte.fragments.LatteFragment;
import com.guikai.latte.ui.R;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anding
 * 自定义控件 仿微信添加图片控件
 */

public class AutoPhotoLayout extends LinearLayoutCompat {

    private int mCurrentNum = 0;
    //最大允许个数
    private int mMaxNum = 0;
    //每行最多个数
    private int mMaxLineNum = 0;
    private IconTextView mIConAdd = null;
    private LayoutParams mParams = null;
    //要删除的图片ID
    private int mDeleteId = 0;
    //选中的图片
    private AppCompatImageView mTargetImageView = null;
    private int mImageMargin = 0;
    private LatteFragment mFragment = null;
    private List<View> mLineViews = null;
    private AlertDialog mTargetDialog = null;
    private static final String ICON_TEXT = "{fa-plus}";
    private float mIconSize = 0;

    private static final List<List<View>> ALL_VIEWS = new ArrayList<>();
    //存储每一行的高度
    private static final List<Integer> LINE_HEIGHTS = new ArrayList<>();

    //防止多次的测量和布局过程
    private boolean mIsOnceInitOnMeasure = false;
    private boolean mHasInitOnLayout = false;

    private static final RequestOptions OPTIONS = new RequestOptions()
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.NONE);

    public AutoPhotoLayout(Context context) {
        this(context, null);
    }

    public AutoPhotoLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoPhotoLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //http://blog.csdn.net/xmxkf/article/details/51468648#5-attributeset和typedArray以及declare-styleable
        //attrs其实也可以取出来值，只不过是未转化过的（第五点）
        final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.camera_flow_layout);
        //第二个参数是默认值
        mMaxNum = typedArray.getInt(R.styleable.camera_flow_layout_max_count, 1);
        mMaxLineNum = typedArray.getInt(R.styleable.camera_flow_layout_line_count, 3);
        mImageMargin = typedArray.getInt(R.styleable.camera_flow_layout_item_margin, 0);
        mIconSize = typedArray.getDimension(R.styleable.camera_flow_layout_icon_size, 20);
        //注意回收
        typedArray.recycle();

    }

    public final void setFragment(LatteFragment fragment) {
        this.mFragment = fragment;
    }

    /**
     * 公开的，传入新照片的方法
     */
    public final void onCropTarget(Uri uri) {
        createNewImageView();
        Glide.with(getContext())
                .load(uri)
                .apply(OPTIONS)
                .into(mTargetImageView);
    }

    private void createNewImageView() {
        mTargetImageView = new AppCompatImageView(getContext());
        //不是说id要有专门的xml的么？
        mTargetImageView.setId(mCurrentNum);
        mTargetImageView.setLayoutParams(mParams);
        mTargetImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取要删除的图片ID
                mDeleteId = v.getId();
//                final AppCompatImageView deleteImageView = (AppCompatImageView) v;
                mTargetDialog.show();
                final Window window = mTargetDialog.getWindow();
                if (window != null) {
                    window.setContentView(R.layout.dialog_image_click_panel);
                    window.setGravity(Gravity.BOTTOM);
                    window.setWindowAnimations(R.style.anim_panel_up_from_bottom);
                    window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    final WindowManager.LayoutParams params = window.getAttributes();
                    params.width = WindowManager.LayoutParams.MATCH_PARENT;
                    //让该window后所有的东西都成暗淡（dim）
                    params.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
                    params.dimAmount = 0.5f;
                    window.setAttributes(params);
                    window.findViewById(R.id.dialog_image_clicked_btn_delete)
                            .setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //得到要删除的图片
                                    //直接用上面的deleteImageView  可以的
                                    final AppCompatImageView deleteImageView = findViewById(mDeleteId);
                                    //设置图片逐渐消失的动画
                                    final AlphaAnimation animation = new AlphaAnimation(1, 0);
                                    animation.setDuration(500);
                                    //重复次数
                                    animation.setRepeatCount(0);
                                    //如果为true表示停留在动画执行完后的状态，否则就回去
                                    animation.setFillAfter(true);
                                    //延迟时间
                                    animation.setStartOffset(0);
                                    deleteImageView.setAnimation(animation);
                                    animation.start();
                                    AutoPhotoLayout.this.removeView(deleteImageView);
                                    //当数目达到上限时隐藏添加按钮，不足时显示
                                    if (AutoPhotoLayout.this.getChildCount() - 1 <= mMaxNum) {
                                        mIConAdd.setVisibility(VISIBLE);
                                    }
                                    mTargetDialog.cancel();
                                }
                            });
                    window.findViewById(R.id.dialog_image_clicked_btn_undetermined)
                            .setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mTargetDialog.cancel();
                                }
                            });
                    window.findViewById(R.id.dialog_image_clicked_btn_cancel)
                            .setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mTargetDialog.cancel();
                                }
                            });
                }
            }
        });
        //添加子View的时候传入位置,再减去mIConAdd的位置
        this.addView(mTargetImageView, AutoPhotoLayout.this.getChildCount() - 1);
        mCurrentNum++;
        //当总数减一（去掉mIConAdd）大于mMaxNum时，自动隐藏添加按钮
        if (AutoPhotoLayout.this.getChildCount() - 1 >= mMaxNum) {
            mIConAdd.setVisibility(View.GONE);
        }

    }

    /**
     * 测量这个layout的宽度和高度
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //TODO 以后解决 为什么删掉？视频里说因为我们不是在重写linearLayout的这些属性
        final int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        final int modeWidth = MeasureSpec.getMode(widthMeasureSpec);  //specMode包含UNSPECIFIED,EXACTLY,AT_MOST,表示测量模式
        final int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        final int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        //需要的长宽
        int width = 0;
        int height = 0;
        //记录每一行的宽度和高度
        int lineWidth = 0;
        int lineHeight = 0;
        //得到内部元素个数
        int cCount = getChildCount();
        for (int i = 0; i < cCount; i++) {
            final View child = getChildAt(i);

            //测量子View的宽和高，内部会调用子View的onMeasure，然后child.getLayoutParams()才会有值
            //TODO 去掉试试，看看child.getLayoutParams()还有值么？或者看看源码，测量了后怎么反馈给父布局
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            //得到LayoutParams
            //TODO 解释一波lp
            final MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            //获取子View占据的宽度
            final int childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            //获取子View占据的高度
            final int childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
            //换行
            if (lineWidth + childWidth > sizeWidth - getPaddingLeft() - getPaddingRight()) {
                //对比得到最大宽度
                width = Math.max(width, lineWidth);
                //重置lineWidth
                lineWidth = childWidth;

                height += (lineHeight + mImageMargin);
                lineHeight = childHeight;
            } else {
                //未换行
                //叠加行宽
                lineWidth += childWidth;
                //得到当前最大的高度
                lineHeight = Math.max(lineHeight, childHeight);
            }
            //最后一个子控件
            if (i == cCount - 1) {
                width = Math.max(lineWidth, width);
                //mIConAdd如果不可见，高度不添加
                if (child.getVisibility() == GONE) {
                    continue;
                }
                height += (lineHeight + mImageMargin);
            }
        }

        setMeasuredDimension(
                //如果是精确的，就用原来的值
                modeWidth == MeasureSpec.EXACTLY ? sizeWidth : width + getPaddingLeft() + getPaddingRight(),
                modeHeight == MeasureSpec.EXACTLY ? sizeHeight : height + getPaddingTop() + getPaddingBottom()
        );

        //设置一行所以图片的宽高
        //只初始化一次
        //TODO 为什么在这里设置？？
        if (!mIsOnceInitOnMeasure) {
            final int imageTotal = sizeWidth - (mMaxLineNum - 1) * mImageMargin - getPaddingLeft() - getPaddingRight();
            final int imageSideLen = imageTotal / mMaxLineNum;
            mParams = new LayoutParams(imageSideLen, imageSideLen);
            mIsOnceInitOnMeasure = true;
        }
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        ALL_VIEWS.clear();
        LINE_HEIGHTS.clear();
        //当前ViewGroup的宽度
        //TODO 不是说此处不能使用getWidth和getHeight，这两个方法必须在onLayout执行完，才能正确获取宽高
        final int width = getWidth();
        int lineWidth = 0;
        int lineHeight = 0;
        if (!mHasInitOnLayout) {
            mLineViews = new ArrayList<>();
            mHasInitOnLayout = true;
        }

        final int cCount = getChildCount();
        for (int i = 0; i < cCount; i++) {
            final View child = getChildAt(i);
            final MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            final int childWidth = child.getMeasuredWidth();
            final int childHeight = child.getMeasuredHeight();
            if (childWidth + lineWidth + lp.leftMargin + lp.rightMargin >
                    width - getPaddingLeft() - getPaddingRight()) {
                //记录lineHeight
                LINE_HEIGHTS.add(lineHeight);
                //记录当前一行的Views
                ALL_VIEWS.add(mLineViews);
                //重置宽和高
                lineWidth = 0;
                lineHeight = 0;
                //重置View集合
                mLineViews = new ArrayList<>();
            }
            lineWidth += childWidth + lp.leftMargin + lp.rightMargin;
            lineHeight = Math.max(lineHeight, childHeight + lp.topMargin + lp.bottomMargin);
            mLineViews.add(child);
        }
        //处理最后一行
        LINE_HEIGHTS.add(lineHeight);
        ALL_VIEWS.add(mLineViews);
        //设置子View的位置
        int left = getPaddingLeft();
        int top = getPaddingTop();
        //行数
        final int lineNum = ALL_VIEWS.size();
        for (int i = 0; i < lineNum; i++) {
            //当前行所有的View
            mLineViews = ALL_VIEWS.get(i);
            lineHeight = LINE_HEIGHTS.get(i);
            final int size = mLineViews.size();
            for (int j = 0; j < size; j++) {
                final View child = mLineViews.get(j);
                //判断child的状态
                if (child.getVisibility() == GONE) {
                    continue;
                }
                final MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
                //设置子View的边距
                final int lc = left + lp.leftMargin;
                final int tc = top + lp.topMargin;
                final int rc = lc + child.getMeasuredWidth();
                final int bc = tc + child.getMeasuredHeight();
                //为子View进行布局
                child.layout(lc, tc, rc, bc);
                left += child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin + mImageMargin;
            }
            left = getPaddingLeft();

            top += (lineHeight + mImageMargin);
        }
        //将mIConAdd宽高设入
        mIConAdd.setLayoutParams(mParams);
        //TODO 可以解决：这里为什么要置false？
        mHasInitOnLayout = false;
    }


    private void initAddIcon() {
        mIConAdd = new IconTextView(getContext());
        mIConAdd.setText(ICON_TEXT);
        mIConAdd.setGravity(Gravity.CENTER);
        mIConAdd.setTextSize(mIconSize);
        mIConAdd.setBackgroundResource(R.drawable.border_text);
        mIConAdd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mFragment.startCameraWithCheck();
            }
        });
        this.addView(mIConAdd);
    }

    /**
     * 当我们从xml里Inflate完了我们的layout之后调用
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initAddIcon();
        mTargetDialog = new AlertDialog.Builder(getContext()).create();
    }
}