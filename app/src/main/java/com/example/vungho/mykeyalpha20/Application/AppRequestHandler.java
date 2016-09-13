package com.example.vungho.mykeyalpha20.Application;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.example.vungho.mykeyalpha20.Manager.ImageManager;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Request;
import com.squareup.picasso.RequestHandler;

import java.io.IOException;

/**
 * Created by vungho on 24/03/2016.
 */
public class AppRequestHandler extends RequestHandler {

    /** Uri scheme for app icons */
    public static final String SCHEME_APP_ICON = "app-icon";

    private PackageManager mPackageManager;

    public AppRequestHandler(Context context) {
        mPackageManager = context.getPackageManager();
    }

    @Override
    public boolean canHandleRequest(Request data) {
        // only handle Uris matching our scheme
        return (SCHEME_APP_ICON.equals(data.uri.getScheme()));
    }

    @Override
    public Result load(Request request, int networkPolicy) throws IOException {

        Drawable drawable = null;
        Bitmap bitmap = null;
        try {
            drawable = mPackageManager.getApplicationIcon(request.uri.toString()
                    .replace(SCHEME_APP_ICON + ":", ""));
        } catch (PackageManager.NameNotFoundException ignored) {
        }

        if (drawable != null) {
            bitmap = ((BitmapDrawable) drawable).getBitmap();
        }
        return new Result(bitmap, Picasso.LoadedFrom.DISK);
    }
}
