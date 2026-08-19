package com.addtext.textonphoto.textart.TART_features.TART_picker.TART_fragment;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.ListPopupWindow;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import com.addtext.textonphoto.textart.TART_features.TART_picker.TART_PhotoPicker;
import com.addtext.textonphoto.textart.TART_features.TART_picker.TART_adapter.TART_PhotoGridAdapter;
import com.addtext.textonphoto.textart.TART_features.TART_picker.TART_adapter.TART_PopupDirectoryListAdapter;
import com.addtext.textonphoto.textart.TART_features.TART_picker.TART_entity.TART_Photo;
import com.addtext.textonphoto.textart.TART_features.TART_picker.TART_entity.TART_PhotoDirectory;
import com.addtext.textonphoto.textart.TART_features.TART_picker.TART_utils.TART_AndroidLifecycleUtils;
import com.addtext.textonphoto.textart.TART_features.TART_picker.TART_utils.TART_ImageCaptureManager;
import com.addtext.textonphoto.textart.TART_features.TART_picker.TART_utils.TART_MediaStoreHelper;
import com.addtext.textonphoto.textart.TART_features.TART_picker.TART_utils.TART_PermissionsUtils;
import com.addtext.textonphoto.textart.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TART_PhotoPickerFragment extends Fragment {
    public static int COUNT_MAX = 4;
    private static final String EXTRA_CAMERA = "camera";
    private static final String EXTRA_COLUMN = "column";
    private static final String EXTRA_COUNT = "count";
    private static final String EXTRA_GIF = "gif";
    private static final String EXTRA_ORIGIN = "origin";

    public int SCROLL_THRESHOLD = 30;
    private TART_ImageCaptureManager captureManager;
    int column;

    public List<TART_PhotoDirectory> directories;
    private TART_PopupDirectoryListAdapter listAdapter;

    public ListPopupWindow listPopupWindow;

    public RequestManager mGlideRequestManager;
    private ArrayList<String> originalPhotos;

    public TART_PhotoGridAdapter photoGridAdapter;

    public static TART_PhotoPickerFragment newInstance(boolean z, boolean z2, boolean z3, int i, int i2, ArrayList<String> arrayList) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(EXTRA_CAMERA, z);
        bundle.putBoolean(EXTRA_GIF, z2);
        bundle.putBoolean(TART_PhotoPicker.EXTRA_PREVIEW_ENABLED, z3);
        bundle.putInt("column", i);
        bundle.putInt(EXTRA_COUNT, i2);
        bundle.putStringArrayList("origin", arrayList);
        TART_PhotoPickerFragment photoPickerFragment = new TART_PhotoPickerFragment();
        photoPickerFragment.setArguments(bundle);
        return photoPickerFragment;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setRetainInstance(true);
        this.mGlideRequestManager = Glide.with(this);
        this.directories = new ArrayList();
        this.originalPhotos = getArguments().getStringArrayList("origin");
        this.column = getArguments().getInt("column", 3);
        boolean z = getArguments().getBoolean(EXTRA_CAMERA, true);
        boolean z2 = getArguments().getBoolean(TART_PhotoPicker.EXTRA_PREVIEW_ENABLED, true);
        this.photoGridAdapter = new TART_PhotoGridAdapter(getActivity(), this.mGlideRequestManager, this.directories, this.originalPhotos, this.column);
        this.photoGridAdapter.setShowCamera(z);
        this.photoGridAdapter.setPreviewEnable(z2);
        this.listAdapter = new TART_PopupDirectoryListAdapter(this.mGlideRequestManager, this.directories);
        Bundle bundle2 = new Bundle();
        bundle2.putBoolean(TART_PhotoPicker.EXTRA_SHOW_GIF, getArguments().getBoolean(EXTRA_GIF));
        TART_MediaStoreHelper.getPhotoDirs(getActivity(), bundle2, new TART_MediaStoreHelper.PhotosResultCallback() {
            public final void onResultCallback(List list) {
                directories.clear();
                directories.addAll(list);
                photoGridAdapter.notifyDataSetChanged();
                listAdapter.notifyDataSetChanged();
                adjustHeight();
            }
        });
        this.captureManager = new TART_ImageCaptureManager(getActivity());
    }


    public void onResume() {
        super.onResume();
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.knack___picker_fragment_photo_picker, viewGroup, false);
       /* if (Constants.SHOW_ADS) {
            AdmobAds.loadBanner(getActivity(), inflate);
        } else {
            inflate.findViewById(R.id.adsContainer).setVisibility(View.GONE);
        }*/
        RecyclerView recyclerView = inflate.findViewById(R.id.rv_photos);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(this.column, 1);
        staggeredGridLayoutManager.setGapStrategy(2);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.setAdapter(this.photoGridAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        LinearLayout linearLayout = inflate.findViewById(R.id.wrap_folder);
        final ImageView imageView = inflate.findViewById(R.id.directIcon);
        final TextView textView = inflate.findViewById(R.id.folder);
        this.listPopupWindow = new ListPopupWindow(getActivity());
        this.listPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            public void onDismiss() {
                imageView.setImageResource(R.drawable.knack_arrow_up);
            }
        });
        this.listPopupWindow.setWidth(-1);
        this.listPopupWindow.setAnchorView(linearLayout);
        this.listPopupWindow.setAdapter(this.listAdapter);
        this.listPopupWindow.setModal(true);
        this.listPopupWindow.setDropDownGravity(80);
        this.listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                TART_PhotoPickerFragment.this.listPopupWindow.dismiss();
                textView.setText((TART_PhotoPickerFragment.this.directories.get(i)).getName());
                TART_PhotoPickerFragment.this.photoGridAdapter.setCurrentDirectoryIndex(i);
                TART_PhotoPickerFragment.this.photoGridAdapter.notifyDataSetChanged();
            }
        });
        this.photoGridAdapter.setOnCameraClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (TART_PermissionsUtils.checkCameraPermission(TART_PhotoPickerFragment.this) && TART_PermissionsUtils.checkWriteStoragePermission((Fragment) TART_PhotoPickerFragment.this)) {
                    TART_PhotoPickerFragment.this.openCamera();
                }
            }
        });
        linearLayout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (TART_PhotoPickerFragment.this.listPopupWindow.isShowing()) {
                    TART_PhotoPickerFragment.this.listPopupWindow.dismiss();
                    imageView.setImageResource(R.drawable.knack_arrow_up);
                } else if (!TART_PhotoPickerFragment.this.getActivity().isFinishing()) {
                    TART_PhotoPickerFragment.this.adjustHeight();
                    imageView.setImageResource(R.drawable.knack_arrow);
                    TART_PhotoPickerFragment.this.listPopupWindow.show();
                }
            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            public void onScrolled(RecyclerView recyclerView, int i, int i2) {
                super.onScrolled(recyclerView, i, i2);
                if (Math.abs(i2) > TART_PhotoPickerFragment.this.SCROLL_THRESHOLD) {
                    TART_PhotoPickerFragment.this.mGlideRequestManager.pauseRequests();
                } else {
                    TART_PhotoPickerFragment.this.resumeRequestsIfNotDestroyed();
                }
            }

            public void onScrollStateChanged(RecyclerView recyclerView, int i) {
                if (i == 0) {
                    TART_PhotoPickerFragment.this.resumeRequestsIfNotDestroyed();
                }
            }
        });
        return inflate;
    }


    public void openCamera() {
        try {
            startActivityForResult(this.captureManager.dispatchTakePictureIntent(), 1);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ActivityNotFoundException e2) {
            Log.e("PhotoPickerFragment", "No Activity Found to handle Intent", e2);
        }
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        if (i == 1 && i2 == -1) {
            if (this.captureManager == null) {
                this.captureManager = new TART_ImageCaptureManager(getActivity());
            }
            this.captureManager.galleryAddPic();
            if (this.directories.size() > 0) {
                String currentPhotoPath = this.captureManager.getCurrentPhotoPath();
                TART_PhotoDirectory photoDirectory = this.directories.get(0);
                photoDirectory.getPhotos().add(0, new TART_Photo(currentPhotoPath.hashCode(), currentPhotoPath));
                photoDirectory.setCoverPath(currentPhotoPath);
                this.photoGridAdapter.notifyDataSetChanged();
            }
        }
    }

    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        if (iArr.length > 0 && iArr[0] == 0) {
            if ((i == 1 || i == 3) && TART_PermissionsUtils.checkWriteStoragePermission((Fragment) this) && TART_PermissionsUtils.checkCameraPermission((Fragment) this)) {
                openCamera();
            }
        }
    }

    public TART_PhotoGridAdapter getPhotoGridAdapter() {
        return this.photoGridAdapter;
    }

    public void onSaveInstanceState(Bundle bundle) {
        this.captureManager.onSaveInstanceState(bundle);
        super.onSaveInstanceState(bundle);
    }

    public void onViewStateRestored(Bundle bundle) {
        this.captureManager.onRestoreInstanceState(bundle);
        super.onViewStateRestored(bundle);
    }

    public void adjustHeight() {
        if (this.listAdapter != null) {
            int count = this.listAdapter.getCount();
            if (count >= COUNT_MAX) {
                count = COUNT_MAX;
            }
            if (this.listPopupWindow != null) {
                this.listPopupWindow.setHeight(count * getResources().getDimensionPixelOffset(R.dimen.__picker_item_directory_height));
            }
        }
    }

    public void onDestroy() {
        super.onDestroy();
        if (this.directories != null) {
            for (TART_PhotoDirectory next : this.directories) {
                next.getPhotoPaths().clear();
                next.getPhotos().clear();
                next.setPhotos(null);
            }
            this.directories.clear();
            this.directories = null;
        }
    }


    public void resumeRequestsIfNotDestroyed() {
        if (TART_AndroidLifecycleUtils.canLoadImage((Fragment) this)) {
            this.mGlideRequestManager.resumeRequests();
        }
    }
}
