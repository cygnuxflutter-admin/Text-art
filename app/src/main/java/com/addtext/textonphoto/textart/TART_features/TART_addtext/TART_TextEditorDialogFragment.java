package com.addtext.textonphoto.textart.TART_features.TART_addtext;

import android.app.Dialog;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.BitmapShader;
import android.graphics.Color;
import android.graphics.Shader;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.SwitchCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.addtext.textonphoto.textart.TART_features.TART_CarouselPicker;
import com.addtext.textonphoto.textart.TART_features.TART_addtext.TART_adapter.TART_FontAdapter;
import com.addtext.textonphoto.textart.TART_features.TART_addtext.TART_adapter.TART_ShadowAdapter;
import com.addtext.textonphoto.textart.TART_utils.TART_FontUtils;
import com.addtext.textonphoto.textart.TART_utils.TART_SharePreferenceUtil;
import com.addtext.textonphoto.textart.TART_utils.TART_SystemUtil;
import com.addtext.textonphoto.textart.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TART_TextEditorDialogFragment extends DialogFragment implements View.OnClickListener, TART_FontAdapter.ItemClickListener, TART_ShadowAdapter.ShadowItemClickListener {
    public static final String EXTRA_COLOR_CODE = "extra_color_code";
    public static final String EXTRA_INPUT_TEXT = "extra_input_text";
    public static final String TAG = "TextEditorDialogFragment";
    LinearLayout addTextBottomToolbar;

    public TART_AddTextProperties addTextProperties;
    ImageView arrowBackgroundColorDown;
    ImageView arrowTextTexture;
    SeekBar backgroundBorder;
    TART_CarouselPicker backgroundColorCarousel;
    AppCompatCheckBox backgroundFullScreen;
    SeekBar backgroundHeight;
    SeekBar backgroundTransparent;
    SeekBar backgroundWidth;
    ImageView changeAlign;
    ImageView changeColor;
    ScrollView changeColorLayout;
    ImageView changeFont;
    ScrollView changeFontLayout;
    ImageView colorArrow;

    public List<TART_CarouselPicker.PickerItem> colorItems;
    TART_CarouselPicker colorPicker;
    private TART_FontAdapter fontAdapter;
    View highlightBackgroundColor;
    View highlightColor;
    View highlightTextTexture;
    LinearLayout layoutPreview;
    RecyclerView lstFonts;
    RecyclerView lstShadows;
    TART_CustomEditText mAddTextEditText;
    private InputMethodManager mInputMethodManager;
    TextView previewText;
    ImageView saveChange;
    private TART_ShadowAdapter shadowAdapter;
    ImageView showKeyboard;
    SwitchCompat switchBackgroundTexture;
    private TextEditor textEditor;
    private List<ImageView> textFunctions;
    SeekBar textSize;

    public List<TART_CarouselPicker.PickerItem> textTextureItems;
    TART_CarouselPicker textTexturePicker;
    SeekBar textTransparent;

    public interface TextEditor {
        void onBackButton();

        void onDone(TART_AddTextProperties addTextProperties);
    }

    public void initView(View view) {
        this.mAddTextEditText = view.findViewById(R.id.add_text_edit_text);
        this.showKeyboard = view.findViewById(R.id.showKeyboard);
        this.changeFont = view.findViewById(R.id.changeFont);
        this.changeColor = view.findViewById(R.id.changeColor);
        this.changeAlign = view.findViewById(R.id.changeAlign);
        this.saveChange = view.findViewById(R.id.saveChange);
        this.changeFontLayout = view.findViewById(R.id.change_font_layout);
        this.addTextBottomToolbar = view.findViewById(R.id.add_text_toolbar);
        this.lstFonts = view.findViewById(R.id.fonts);
        this.lstShadows = view.findViewById(R.id.shadows);
        this.changeColorLayout = view.findViewById(R.id.changeColorLayout);
        this.colorPicker = view.findViewById(R.id.colorCarousel);
        this.textTexturePicker = view.findViewById(R.id.textTextureSlider);
        this.arrowTextTexture = view.findViewById(R.id.arrow_text_texture);
        this.colorArrow = view.findViewById(R.id.arrow_color_down);
        this.highlightColor = view.findViewById(R.id.highlightColor);
        this.highlightTextTexture = view.findViewById(R.id.highlightTextTexture);
        this.textTransparent = view.findViewById(R.id.textTransparent);
        this.previewText = view.findViewById(R.id.previewEffectText);
        this.layoutPreview = view.findViewById(R.id.layoutPreview);
        this.switchBackgroundTexture = view.findViewById(R.id.switchBackgroundTexture);
        this.arrowBackgroundColorDown = view.findViewById(R.id.arrowBackgroundColorDown);
        this.highlightBackgroundColor = view.findViewById(R.id.highlightBackgroundColor);
        this.backgroundColorCarousel = view.findViewById(R.id.backgroundColorCarousel);
        this.backgroundWidth = view.findViewById(R.id.backgroundWidth);
        this.backgroundHeight = view.findViewById(R.id.backgroundHeight);
        this.backgroundFullScreen = view.findViewById(R.id.backgroundFullScreen);
        this.backgroundTransparent = view.findViewById(R.id.backgroundTransparent);
        this.textSize = view.findViewById(R.id.textSize);
        this.backgroundBorder = view.findViewById(R.id.backgroundBorderRadius);
    }

    public void onItemClick(View view, int i) {
        TART_FontUtils.setFontByName(Objects.requireNonNull(getContext()), this.previewText, TART_FontUtils.getListFonts().get(i));
        this.addTextProperties.setFontName(TART_FontUtils.getListFonts().get(i));
        this.addTextProperties.setFontIndex(i);
    }

    public void onShadowItemClick(View view, int i) {
        TART_AddTextProperties.TextShadow textShadow = TART_AddTextProperties.getLstTextShadow().get(i);
        this.previewText.setShadowLayer((float) textShadow.getRadius(), (float) textShadow.getDx(), (float) textShadow.getDy(), textShadow.getColorShadow());
        this.previewText.invalidate();
        this.addTextProperties.setTextShadow(textShadow);
        this.addTextProperties.setTextShadowIndex(i);
    }

    public static TART_TextEditorDialogFragment show(@NonNull AppCompatActivity appCompatActivity, @NonNull String str, @ColorInt int i) {
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_INPUT_TEXT, str);
        bundle.putInt(EXTRA_COLOR_CODE, i);
        TART_TextEditorDialogFragment textEditorDialogFragment = new TART_TextEditorDialogFragment();
        textEditorDialogFragment.setArguments(bundle);
        textEditorDialogFragment.show(appCompatActivity.getSupportFragmentManager(), TAG);
        return textEditorDialogFragment;
    }

    public static TART_TextEditorDialogFragment show(@NonNull AppCompatActivity appCompatActivity, TART_AddTextProperties addTextProperties2) {
        TART_TextEditorDialogFragment textEditorDialogFragment = new TART_TextEditorDialogFragment();
        textEditorDialogFragment.setAddTextProperties(addTextProperties2);
        textEditorDialogFragment.show(appCompatActivity.getSupportFragmentManager(), TAG);
        return textEditorDialogFragment;
    }

    public static TART_TextEditorDialogFragment show(@NonNull AppCompatActivity appCompatActivity) {
        return show(appCompatActivity, "Test", ContextCompat.getColor(appCompatActivity, R.color.white));
    }

    public void setAddTextProperties(TART_AddTextProperties addTextProperties2) {
        this.addTextProperties = addTextProperties2;
    }

    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(-1, -1);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
    }

    @Nullable
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        getDialog().getWindow().requestFeature(1);
        getDialog().getWindow().setFlags(1024, 1024);
        return layoutInflater.inflate(R.layout.knack_add_text_dialog, viewGroup, false);
    }

    public void dismissAndShowSticker() {
        if (this.textEditor != null) {
            this.textEditor.onBackButton();
        }
        dismiss();
    }

    public void onActivityCreated(@Nullable Bundle bundle) {
        super.onActivityCreated(bundle);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        initView(view);
        if (this.addTextProperties == null) {
            this.addTextProperties = TART_AddTextProperties.getDefaultProperties();
        }
        this.mAddTextEditText.setDialogFragment(this);
        initAddTextLayout();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(new DisplayMetrics());
        this.mInputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        setDefaultStyleForEdittext();
        this.mInputMethodManager.toggleSoftInput(2, 0);
        highlightFunction(this.showKeyboard);
        this.lstFonts.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        this.fontAdapter = new TART_FontAdapter(getContext(), TART_FontUtils.getListFonts());
        this.fontAdapter.setClickListener(this);
        this.lstFonts.setAdapter(this.fontAdapter);
        this.lstShadows.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        this.shadowAdapter = new TART_ShadowAdapter(getContext(), TART_AddTextProperties.getLstTextShadow());
        this.shadowAdapter.setClickListener(this);
        this.lstShadows.setAdapter(this.shadowAdapter);
        this.colorPicker.setAdapter(new TART_CarouselPicker.CarouselViewAdapter(getContext(), this.colorItems, 0));
        this.colorPicker.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int i) {
            }

            public void onPageSelected(int i) {
            }

            public void onPageScrolled(int i, float f, int i2) {
                if (f > 0.0f) {
                    if (TART_TextEditorDialogFragment.this.colorArrow.getVisibility() == View.INVISIBLE) {
                        TART_TextEditorDialogFragment.this.colorArrow.setVisibility(View.VISIBLE);
                        TART_TextEditorDialogFragment.this.highlightColor.setVisibility(View.VISIBLE);
                        TART_TextEditorDialogFragment.this.arrowTextTexture.setVisibility(View.INVISIBLE);
                        TART_TextEditorDialogFragment.this.highlightTextTexture.setVisibility(View.GONE);
                    }
                    TART_TextEditorDialogFragment.this.previewText.getPaint().setShader(null);
                    int i3 = -1;
                    float f2 = ((float) i) + f;
                    if (Math.round(f2) < TART_TextEditorDialogFragment.this.colorItems.size()) {
                        i3 = Color.parseColor((TART_TextEditorDialogFragment.this.colorItems.get(Math.round(f2))).getColor());
                    }
                    TART_TextEditorDialogFragment.this.previewText.setTextColor(i3);
                    TART_TextEditorDialogFragment.this.addTextProperties.setTextColorIndex(Math.round(f2));
                    TART_TextEditorDialogFragment.this.addTextProperties.setTextColor(i3);
                    TART_TextEditorDialogFragment.this.addTextProperties.setTextShader(null);
                }
            }
        });
        this.textTexturePicker.setAdapter(new TART_CarouselPicker.CarouselViewAdapter(getContext(), this.textTextureItems, 0));
        this.textTexturePicker.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int i) {
            }

            public void onPageSelected(int i) {
            }

            public void onPageScrolled(int i, float f, int i2) {
                if (f > 0.0f) {
                    if (TART_TextEditorDialogFragment.this.arrowTextTexture.getVisibility() == View.INVISIBLE) {
                        TART_TextEditorDialogFragment.this.arrowTextTexture.setVisibility(View.VISIBLE);
                        TART_TextEditorDialogFragment.this.highlightTextTexture.setVisibility(View.VISIBLE);
                        TART_TextEditorDialogFragment.this.colorArrow.setVisibility(View.INVISIBLE);
                        TART_TextEditorDialogFragment.this.highlightColor.setVisibility(View.GONE);
                    }
                    float f2 = ((float) i) + f;
                    BitmapShader bitmapShader = new BitmapShader((TART_TextEditorDialogFragment.this.textTextureItems.get(Math.round(f2))).getBitmap(), Shader.TileMode.MIRROR, Shader.TileMode.MIRROR);
                    TART_TextEditorDialogFragment.this.previewText.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                    TART_TextEditorDialogFragment.this.previewText.getPaint().setShader(bitmapShader);
                    TART_TextEditorDialogFragment.this.addTextProperties.setTextShader(bitmapShader);
                    TART_TextEditorDialogFragment.this.addTextProperties.setTextShaderIndex(Math.round(f2));
                }
            }
        });
        this.textTransparent.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                int i2 = 255 - i;
                TART_TextEditorDialogFragment.this.addTextProperties.setTextAlpha(i2);
                TART_TextEditorDialogFragment.this.previewText.setTextColor(Color.argb(i2, Color.red(TART_TextEditorDialogFragment.this.addTextProperties.getTextColor()), Color.green(TART_TextEditorDialogFragment.this.addTextProperties.getTextColor()), Color.blue(TART_TextEditorDialogFragment.this.addTextProperties.getTextColor())));
            }
        });
        this.mAddTextEditText.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                TART_TextEditorDialogFragment.this.previewText.setText(charSequence.toString());
                TART_TextEditorDialogFragment.this.addTextProperties.setText(charSequence.toString());
            }
        });
        this.switchBackgroundTexture.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                if (!z) {
                    TART_TextEditorDialogFragment.this.addTextProperties.setShowBackground(false);
                    TART_TextEditorDialogFragment.this.previewText.setBackgroundResource(0);
                    TART_TextEditorDialogFragment.this.previewText.setLayoutParams(new LinearLayout.LayoutParams(-2, -2));
                } else if (TART_TextEditorDialogFragment.this.switchBackgroundTexture.isPressed() || TART_TextEditorDialogFragment.this.addTextProperties.isShowBackground()) {
                    TART_TextEditorDialogFragment.this.addTextProperties.setShowBackground(true);
                    TART_TextEditorDialogFragment.this.initPreviewText();
                } else {
                    TART_TextEditorDialogFragment.this.switchBackgroundTexture.setChecked(false);
                    TART_TextEditorDialogFragment.this.addTextProperties.setShowBackground(false);
                    TART_TextEditorDialogFragment.this.initPreviewText();
                }
            }
        });
        this.backgroundColorCarousel.setAdapter(new TART_CarouselPicker.CarouselViewAdapter(getContext(), this.colorItems, 0));
        this.backgroundColorCarousel.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int i) {
            }

            public void onPageSelected(int i) {
            }

            public void onPageScrolled(int i, float f, int i2) {
                if (f > 0.0f) {
                    int i3 = 0;
                    if (TART_TextEditorDialogFragment.this.arrowBackgroundColorDown.getVisibility() == View.INVISIBLE) {
                        TART_TextEditorDialogFragment.this.arrowBackgroundColorDown.setVisibility(View.VISIBLE);
                        TART_TextEditorDialogFragment.this.highlightBackgroundColor.setVisibility(View.VISIBLE);
                    }
                    TART_TextEditorDialogFragment.this.addTextProperties.setShowBackground(true);
                    if (!TART_TextEditorDialogFragment.this.switchBackgroundTexture.isChecked()) {
                        TART_TextEditorDialogFragment.this.switchBackgroundTexture.setChecked(true);
                    }
                    float f2 = ((float) i) + f;
                    int round = Math.round(f2);
                    if (round >= TART_TextEditorDialogFragment.this.colorItems.size()) {
                        i3 = TART_TextEditorDialogFragment.this.colorItems.size() - 1;
                    } else if (round >= 0) {
                        i3 = round;
                    }
                    int parseColor = Color.parseColor((TART_TextEditorDialogFragment.this.colorItems.get(i3)).getColor());
                    int red = Color.red(parseColor);
                    int green = Color.green(parseColor);
                    int blue = Color.blue(parseColor);
                    GradientDrawable gradientDrawable = new GradientDrawable();
                    gradientDrawable.setColor(Color.argb(TART_TextEditorDialogFragment.this.addTextProperties.getBackgroundAlpha(), red, green, blue));
                    gradientDrawable.setCornerRadius((float) TART_SystemUtil.dpToPx(Objects.requireNonNull(TART_TextEditorDialogFragment.this.getContext()), TART_TextEditorDialogFragment.this.addTextProperties.getBackgroundBorder()));
                    TART_TextEditorDialogFragment.this.previewText.setBackground(gradientDrawable);
                    TART_TextEditorDialogFragment.this.addTextProperties.setBackgroundColor(parseColor);
                    TART_TextEditorDialogFragment.this.addTextProperties.setBackgroundColorIndex(Math.round(f2));
                    TART_TextEditorDialogFragment.this.backgroundBorder.setEnabled(true);
                }
            }
        });
        this.backgroundWidth.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                TART_TextEditorDialogFragment.this.previewText.setPadding(TART_SystemUtil.dpToPx(Objects.requireNonNull(TART_TextEditorDialogFragment.this.getContext()), i), TART_TextEditorDialogFragment.this.previewText.getPaddingTop(), TART_SystemUtil.dpToPx(TART_TextEditorDialogFragment.this.getContext(), i), TART_TextEditorDialogFragment.this.previewText.getPaddingBottom());
                TART_TextEditorDialogFragment.this.addTextProperties.setPaddingWidth(i);
            }
        });
        this.backgroundHeight.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                TART_TextEditorDialogFragment.this.previewText.setPadding(TART_TextEditorDialogFragment.this.previewText.getPaddingLeft(), TART_SystemUtil.dpToPx(Objects.requireNonNull(TART_TextEditorDialogFragment.this.getContext()), i), TART_TextEditorDialogFragment.this.previewText.getPaddingRight(), TART_SystemUtil.dpToPx(TART_TextEditorDialogFragment.this.getContext(), i));
                TART_TextEditorDialogFragment.this.addTextProperties.setPaddingHeight(i);
            }
        });
        this.backgroundFullScreen.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                if (z) {
                    TART_TextEditorDialogFragment.this.previewText.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
                } else {
                    TART_TextEditorDialogFragment.this.previewText.setLayoutParams(new LinearLayout.LayoutParams(-2, -2));
                }
                TART_TextEditorDialogFragment.this.addTextProperties.setFullScreen(z);
            }
        });
        this.backgroundTransparent.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                TART_TextEditorDialogFragment.this.addTextProperties.setBackgroundAlpha(255 - i);
                if (TART_TextEditorDialogFragment.this.addTextProperties.isShowBackground()) {
                    int red = Color.red(TART_TextEditorDialogFragment.this.addTextProperties.getBackgroundColor());
                    int green = Color.green(TART_TextEditorDialogFragment.this.addTextProperties.getBackgroundColor());
                    int blue = Color.blue(TART_TextEditorDialogFragment.this.addTextProperties.getBackgroundColor());
                    GradientDrawable gradientDrawable = new GradientDrawable();
                    gradientDrawable.setColor(Color.argb(TART_TextEditorDialogFragment.this.addTextProperties.getBackgroundAlpha(), red, green, blue));
                    gradientDrawable.setCornerRadius((float) TART_SystemUtil.dpToPx(Objects.requireNonNull(TART_TextEditorDialogFragment.this.getContext()), TART_TextEditorDialogFragment.this.addTextProperties.getBackgroundBorder()));
                    TART_TextEditorDialogFragment.this.previewText.setBackground(gradientDrawable);
                }
            }
        });
        this.textSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                int i2 = 15;
                if (i >= 15) {
                    i2 = i;
                }
                TART_TextEditorDialogFragment.this.previewText.setTextSize((float) i2);
                TART_TextEditorDialogFragment.this.addTextProperties.setTextSize(i2);
            }
        });
        this.backgroundBorder.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                TART_TextEditorDialogFragment.this.addTextProperties.setBackgroundBorder(i);
                if (TART_TextEditorDialogFragment.this.addTextProperties.isShowBackground()) {
                    GradientDrawable gradientDrawable = new GradientDrawable();
                    gradientDrawable.setCornerRadius((float) TART_SystemUtil.dpToPx(Objects.requireNonNull(TART_TextEditorDialogFragment.this.getContext()), i));
                    gradientDrawable.setColor(Color.argb(TART_TextEditorDialogFragment.this.addTextProperties.getBackgroundAlpha(), Color.red(TART_TextEditorDialogFragment.this.addTextProperties.getBackgroundColor()), Color.green(TART_TextEditorDialogFragment.this.addTextProperties.getBackgroundColor()), Color.blue(TART_TextEditorDialogFragment.this.addTextProperties.getBackgroundColor())));
                    TART_TextEditorDialogFragment.this.previewText.setBackground(gradientDrawable);
                }
            }
        });
        if (TART_SharePreferenceUtil.getHeightOfKeyboard(Objects.requireNonNull(getContext())) > 0) {
            updateAddTextBottomToolbarHeight(TART_SharePreferenceUtil.getHeightOfKeyboard(getContext()));
        }
        initPreviewText();
    }


    public void initPreviewText() {
        if (this.addTextProperties.isFullScreen()) {
            this.previewText.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
        }
        if (this.addTextProperties.isShowBackground()) {
            if (this.addTextProperties.getBackgroundColor() != 0) {
                this.previewText.setBackgroundColor(this.addTextProperties.getBackgroundColor());
            }
            if (this.addTextProperties.getBackgroundAlpha() < 255) {
                this.previewText.setBackgroundColor(Color.argb(this.addTextProperties.getBackgroundAlpha(), Color.red(this.addTextProperties.getBackgroundColor()), Color.green(this.addTextProperties.getBackgroundColor()), Color.blue(this.addTextProperties.getBackgroundColor())));
            }
            if (this.addTextProperties.getBackgroundBorder() > 0) {
                GradientDrawable gradientDrawable = new GradientDrawable();
                gradientDrawable.setCornerRadius((float) TART_SystemUtil.dpToPx(Objects.requireNonNull(getContext()), this.addTextProperties.getBackgroundBorder()));
                gradientDrawable.setColor(Color.argb(this.addTextProperties.getBackgroundAlpha(), Color.red(this.addTextProperties.getBackgroundColor()), Color.green(this.addTextProperties.getBackgroundColor()), Color.blue(this.addTextProperties.getBackgroundColor())));
                this.previewText.setBackground(gradientDrawable);
            }
        }
        if (this.addTextProperties.getPaddingHeight() > 0) {
            this.previewText.setPadding(this.previewText.getPaddingLeft(), this.addTextProperties.getPaddingHeight(), this.previewText.getPaddingRight(), this.addTextProperties.getPaddingHeight());
            this.backgroundHeight.setProgress(this.addTextProperties.getPaddingHeight());
        }
        if (this.addTextProperties.getPaddingWidth() > 0) {
            this.previewText.setPadding(this.addTextProperties.getPaddingWidth(), this.previewText.getPaddingTop(), this.addTextProperties.getPaddingWidth(), this.previewText.getPaddingBottom());
            this.backgroundWidth.setProgress(this.addTextProperties.getPaddingWidth());
        }
        if (this.addTextProperties.getText() != null) {
            this.previewText.setText(this.addTextProperties.getText());
            this.mAddTextEditText.setText(this.addTextProperties.getText());
        }
        if (this.addTextProperties.getTextShader() != null) {
            this.previewText.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            this.previewText.getPaint().setShader(this.addTextProperties.getTextShader());
        }
        if (this.addTextProperties.getTextAlign() == 4) {
            this.changeAlign.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.knack_img_alignment_center));
        } else if (this.addTextProperties.getTextAlign() == 3) {
            this.changeAlign.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.knack_img_alignment_right));
        } else if (this.addTextProperties.getTextAlign() == 2) {
            this.changeAlign.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.knack_img_alignment_left));
        }
        this.previewText.setPadding(TART_SystemUtil.dpToPx(getContext(), this.addTextProperties.getPaddingWidth()), this.previewText.getPaddingTop(), TART_SystemUtil.dpToPx(getContext(), this.addTextProperties.getPaddingWidth()), this.previewText.getPaddingBottom());
        this.previewText.setTextColor(this.addTextProperties.getTextColor());
        this.previewText.setTextAlignment(this.addTextProperties.getTextAlign());
        this.previewText.setTextSize((float) this.addTextProperties.getTextSize());
        TART_FontUtils.setFontByName(getContext(), this.previewText, this.addTextProperties.getFontName());
        if (this.addTextProperties.getTextShadow() != null) {
            TART_AddTextProperties.TextShadow textShadow = this.addTextProperties.getTextShadow();
            this.previewText.setShadowLayer((float) textShadow.getRadius(), (float) textShadow.getDx(), (float) textShadow.getDy(), textShadow.getColorShadow());
        }
        this.previewText.invalidate();
    }

    private void setDefaultStyleForEdittext() {
        this.mAddTextEditText.requestFocus();
        this.mAddTextEditText.setTextSize(20.0f);
        this.mAddTextEditText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        this.mAddTextEditText.setTextColor(Color.parseColor("#424949"));
    }

    private void initAddTextLayout() {
        this.textFunctions = getTextFunctions();
        this.showKeyboard.setOnClickListener(this);
        this.changeFont.setOnClickListener(this);
        this.changeColor.setOnClickListener(this);
        this.changeAlign.setOnClickListener(this);
        this.saveChange.setOnClickListener(this);
        this.changeFontLayout.setVisibility(View.GONE);
        this.changeColorLayout.setVisibility(View.GONE);
        this.arrowTextTexture.setVisibility(View.INVISIBLE);
        this.highlightTextTexture.setVisibility(View.GONE);
        this.backgroundWidth.setProgress(this.addTextProperties.getPaddingWidth());
        this.colorItems = getColorItems();
        this.textTextureItems = getTextTextures();
    }

    public void onResume() {
        super.onResume();
        ViewCompat.setOnApplyWindowInsetsListener(getDialog().getWindow().getDecorView(), new OnApplyWindowInsetsListener() {
            public final WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
                return ViewCompat.onApplyWindowInsets(
                        TART_TextEditorDialogFragment.this.getDialog().getWindow().getDecorView(),
                        windowInsetsCompat.inset(windowInsetsCompat.getSystemWindowInsetLeft(), 0, windowInsetsCompat.getSystemWindowInsetRight(), windowInsetsCompat.getSystemWindowInsetBottom()));
            }
        });

    }

    public void updateAddTextBottomToolbarHeight(final int i) {
        new Handler().post(new Runnable() {
            public void run() {
                ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) TART_TextEditorDialogFragment.this.addTextBottomToolbar.getLayoutParams();
                layoutParams.bottomMargin = i;
                TART_TextEditorDialogFragment.this.addTextBottomToolbar.setLayoutParams(layoutParams);
                TART_TextEditorDialogFragment.this.addTextBottomToolbar.invalidate();
                ConstraintLayout.LayoutParams layoutParams2 = (ConstraintLayout.LayoutParams) TART_TextEditorDialogFragment.this.changeFontLayout.getLayoutParams();
                layoutParams2.height = i;
                TART_TextEditorDialogFragment.this.changeFontLayout.setLayoutParams(layoutParams2);
                TART_TextEditorDialogFragment.this.changeFontLayout.invalidate();
                ConstraintLayout.LayoutParams layoutParams3 = (ConstraintLayout.LayoutParams) TART_TextEditorDialogFragment.this.changeColorLayout.getLayoutParams();
                layoutParams3.height = i;
                TART_TextEditorDialogFragment.this.changeColorLayout.setLayoutParams(layoutParams3);
                TART_TextEditorDialogFragment.this.changeColorLayout.invalidate();
                Log.i("HIHIH", i + "");
            }
        });
    }

    public void setOnTextEditorListener(TextEditor textEditor2) {
        this.textEditor = textEditor2;
    }

    private void highlightFunction(ImageView imageView) {
        for (ImageView next : this.textFunctions) {
            if (next == imageView) {
                imageView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.knack_highlight));
            } else {
                next.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.knack_fake_highlight));
            }
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.changeAlign:
                if (this.addTextProperties.getTextAlign() == 4) {
                    this.addTextProperties.setTextAlign(3);
                    this.changeAlign.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.knack_img_alignment_right));
                } else if (this.addTextProperties.getTextAlign() == 3) {
                    this.addTextProperties.setTextAlign(2);
                    this.changeAlign.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.knack_img_alignment_left));
                } else if (this.addTextProperties.getTextAlign() == 2) {
                    this.addTextProperties.setTextAlign(4);
                    this.changeAlign.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.knack_img_alignment_center));
                }
                this.previewText.setTextAlignment(this.addTextProperties.getTextAlign());
                TextView textView = this.previewText;
                textView.setText(this.previewText.getText().toString().trim() + " ");
                this.previewText.setText(this.previewText.getText().toString().trim());
                return;
            case R.id.changeColor:
                this.mInputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                this.changeColorLayout.setVisibility(View.VISIBLE);
                toggleTextEditEditable(false);
                highlightFunction(this.changeColor);
                this.changeFontLayout.setVisibility(View.GONE);
                this.mAddTextEditText.setVisibility(View.GONE);
                this.colorPicker.setCurrentItem(this.addTextProperties.getTextColorIndex());
                this.textTexturePicker.setCurrentItem(this.addTextProperties.getTextShaderIndex());
                this.textTransparent.setProgress(255 - this.addTextProperties.getTextAlpha());
                this.switchBackgroundTexture.setChecked(this.addTextProperties.isShowBackground());
                this.backgroundColorCarousel.setCurrentItem(this.addTextProperties.getBackgroundColorIndex());
                this.backgroundTransparent.setProgress(255 - this.addTextProperties.getBackgroundAlpha());
                this.backgroundFullScreen.setChecked(this.addTextProperties.isFullScreen());
                this.backgroundBorder.setProgress(this.addTextProperties.getBackgroundBorder());
                this.backgroundWidth.setProgress(this.addTextProperties.getPaddingWidth());
                this.backgroundHeight.setProgress(this.addTextProperties.getPaddingHeight());
                this.switchBackgroundTexture.setChecked(this.addTextProperties.isShowBackground());
                if (this.addTextProperties.getTextShader() != null && this.arrowTextTexture.getVisibility() == View.INVISIBLE) {
                    this.arrowTextTexture.setVisibility(View.VISIBLE);
                    this.highlightTextTexture.setVisibility(View.VISIBLE);
                    this.colorArrow.setVisibility(View.INVISIBLE);
                    this.highlightColor.setVisibility(View.GONE);
                    return;
                }
                return;
            case R.id.changeFont:
                this.mInputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                this.changeFontLayout.setVisibility(View.VISIBLE);
                this.changeColorLayout.setVisibility(View.GONE);
                this.mAddTextEditText.setVisibility(View.GONE);
                toggleTextEditEditable(false);
                highlightFunction(this.changeFont);
                this.textSize.setProgress(this.addTextProperties.getTextSize());
                this.fontAdapter.setSelectedItem(this.addTextProperties.getFontIndex());
                this.shadowAdapter.setSelectedItem(this.addTextProperties.getTextShadowIndex());
                return;
            case R.id.saveChange:
                if (this.addTextProperties.getText() == null || this.addTextProperties.getText().length() == 0) {
                    this.mInputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    this.textEditor.onBackButton();
                    dismiss();
                    return;
                }
                this.addTextProperties.setTextWidth(this.previewText.getMeasuredWidth());
                this.addTextProperties.setTextHeight(this.previewText.getMeasuredHeight());
                this.mInputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                this.textEditor.onDone(this.addTextProperties);
                dismiss();
                return;
            case R.id.showKeyboard:
                toggleTextEditEditable(true);
                this.mAddTextEditText.setVisibility(View.VISIBLE);
                this.mAddTextEditText.requestFocus();
                highlightFunction(this.showKeyboard);
                this.changeFontLayout.setVisibility(View.GONE);
                this.changeColorLayout.setVisibility(View.GONE);
                this.addTextBottomToolbar.invalidate();
                this.mInputMethodManager.toggleSoftInput(2, 0);
                return;
            default:
        }
    }

    private void toggleTextEditEditable(boolean z) {
        this.mAddTextEditText.setFocusable(z);
        this.mAddTextEditText.setFocusableInTouchMode(z);
        this.mAddTextEditText.setClickable(z);
    }

    private List<ImageView> getTextFunctions() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(this.showKeyboard);
        arrayList.add(this.changeFont);
        arrayList.add(this.changeColor);
        arrayList.add(this.changeAlign);
        arrayList.add(this.saveChange);
        return arrayList;
    }

    public List<TART_CarouselPicker.PickerItem> getTextTextures() {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < 15; i++) {
            try {
                AssetManager assets = getContext().getAssets();
                arrayList.add(new TART_CarouselPicker.DrawableItem(Drawable.createFromStream(assets.open("text_texture/" + (i + 1) + ".jpg"), (String) null)));
            } catch (Exception e) {
            }
        }
        return arrayList;
    }

    public List<TART_CarouselPicker.PickerItem> getColorItems() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new TART_CarouselPicker.ColorItem("#f1948a"));
        arrayList.add(new TART_CarouselPicker.ColorItem("#e74c3c"));
        arrayList.add(new TART_CarouselPicker.ColorItem("#DC143C"));
        arrayList.add(new TART_CarouselPicker.ColorItem("#FF0000"));
        arrayList.add(new TART_CarouselPicker.ColorItem("#bb8fce"));
        arrayList.add(new TART_CarouselPicker.ColorItem("#8e44ad"));
        arrayList.add(new TART_CarouselPicker.ColorItem("#6c3483"));
        arrayList.add(new TART_CarouselPicker.ColorItem("#FF00FF"));
        arrayList.add(new TART_CarouselPicker.ColorItem("#3498db"));
        arrayList.add(new TART_CarouselPicker.ColorItem("#2874a6"));
        arrayList.add(new TART_CarouselPicker.ColorItem("#1b4f72"));
        arrayList.add(new TART_CarouselPicker.ColorItem("#0000FF"));
        arrayList.add(new TART_CarouselPicker.ColorItem("#73c6b6"));
        arrayList.add(new TART_CarouselPicker.ColorItem("#16a085"));
        arrayList.add(new TART_CarouselPicker.ColorItem("#117a65"));
        arrayList.add(new TART_CarouselPicker.ColorItem("#0b5345"));
        arrayList.add(new TART_CarouselPicker.ColorItem("#ffffff"));
        arrayList.add(new TART_CarouselPicker.ColorItem("#d7dbdd"));
        arrayList.add(new TART_CarouselPicker.ColorItem("#bdc3c7"));
        arrayList.add(new TART_CarouselPicker.ColorItem("#909497"));
        arrayList.add(new TART_CarouselPicker.ColorItem("#626567"));
        arrayList.add(new TART_CarouselPicker.ColorItem("#000000"));
        arrayList.add(new TART_CarouselPicker.ColorItem("#239b56"));
        arrayList.add(new TART_CarouselPicker.ColorItem("#186a3b"));
        arrayList.add(new TART_CarouselPicker.ColorItem("#f8c471"));
        arrayList.add(new TART_CarouselPicker.ColorItem("#f39c12"));
        arrayList.add(new TART_CarouselPicker.ColorItem("#FFA500"));
        arrayList.add(new TART_CarouselPicker.ColorItem("#FFFF00"));
        arrayList.add(new TART_CarouselPicker.ColorItem("#7e5109"));
        arrayList.add(new TART_CarouselPicker.ColorItem("#e59866"));
        arrayList.add(new TART_CarouselPicker.ColorItem("#d35400"));
        arrayList.add(new TART_CarouselPicker.ColorItem("#a04000"));
        arrayList.add(new TART_CarouselPicker.ColorItem("#6e2c00"));
        arrayList.add(new TART_CarouselPicker.ColorItem("#808b96"));
        arrayList.add(new TART_CarouselPicker.ColorItem("#2c3e50"));
        arrayList.add(new TART_CarouselPicker.ColorItem("#212f3d"));
        arrayList.add(new TART_CarouselPicker.ColorItem("#17202a"));
        return arrayList;
    }
}
