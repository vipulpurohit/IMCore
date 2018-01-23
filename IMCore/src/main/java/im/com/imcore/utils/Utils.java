package im.com.imcore.utils;

import android.Manifest;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.ResultReceiver;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresPermission;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.TouchDelegate;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.Locale;

import static android.os.Build.BRAND;
import static android.os.Build.DEVICE;
import static android.os.Build.MANUFACTURER;
import static android.os.Build.MODEL;
import static android.os.Build.PRODUCT;
import static android.os.Build.VERSION;
import static android.os.Build.VERSION_CODES;

@SuppressWarnings("unused")
public final class Utils {

    public static final class App {

        public static final String TAG = "Info.App";

        @Nullable
        public static String name(final Context context) {
            try {
                return context.getString(context.getPackageManager().getPackageInfo(context.getPackageName(), 0).applicationInfo.labelRes);
            } catch (Exception e) {
                Log.e(TAG, "Failed to get application name", e);
                return null;
            }
        }

        @Nullable
        public static String packageName(final Context context) {
            return context.getPackageName();
        }

        @Nullable
        public static String versionName(@NonNull Context context) {
            try {
                return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
            } catch (Exception e) {
                Log.e(TAG, "Failed to get application version name", e);
                return null;
            }
        }

        public static int versionCode(@NonNull Context context) {
            try {
                return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
            } catch (Exception e) {
                Log.e(TAG, "Failed to get application version code", e);
                return 0;
            }
        }
    }

    public static final class Device {

        public static String name() {
            final String manufacturer = MANUFACTURER;
            final String model = MODEL;
            return model.startsWith(manufacturer) ? model : manufacturer + " " + model;
        }

        public static String manufacturer() {
            return MANUFACTURER;
        }

        public static String model() {
            return MODEL;
        }

        public static String product() {
            return PRODUCT;
        }

        public static String brand() {
            return BRAND;
        }

        public static String device() {
            return DEVICE;
        }

        @RequiresPermission(Manifest.permission.READ_PHONE_STATE)
        public static String phoneNumber(final Context context) {
            return ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getLine1Number();
        }

        public static boolean isEmulator() {
            return "sdk".equals(MODEL) || "google_sdk".equals(MODEL);
        }

        /**
         * @param feature from PackageManager.FEATURE_*
         */
        public static boolean hasFeature(@NonNull Context context, @NonNull String feature) {
            return context.getPackageManager().hasSystemFeature(feature);
        }

        public static String country() {
            return Locale.getDefault().getCountry();
        }

        public static String language() {
            return Locale.getDefault().getLanguage();
        }
    }

    public static final class Os {

        public static String release() {
            return VERSION.RELEASE;
        }

        public static int sdk() {
            return VERSION.SDK_INT;
        }

        public static boolean hasCupcake() {
            return VERSION.SDK_INT >= VERSION_CODES.CUPCAKE;
        }

        public static boolean hasDonut() {
            return VERSION.SDK_INT >= VERSION_CODES.DONUT;
        }

        public static boolean hasEclair() {
            return VERSION.SDK_INT >= VERSION_CODES.ECLAIR;
        }

        public static boolean hasFroyo() {
            return VERSION.SDK_INT >= VERSION_CODES.FROYO;
        }

        public static boolean hasGingerbread() {
            return VERSION.SDK_INT >= VERSION_CODES.GINGERBREAD;
        }

        public static boolean hasGingerbreadMR1() {
            return VERSION.SDK_INT >= VERSION_CODES.GINGERBREAD_MR1;
        }

        public static boolean hasHoneycomb() {
            return VERSION.SDK_INT >= VERSION_CODES.HONEYCOMB;
        }

        public static boolean hasHoneycombMR1() {
            return VERSION.SDK_INT >= VERSION_CODES.HONEYCOMB_MR1;
        }

        public static boolean hasHoneycombMR2() {
            return VERSION.SDK_INT >= VERSION_CODES.HONEYCOMB_MR2;
        }

        public static boolean hasIceCreamSandwich() {
            return VERSION.SDK_INT >= VERSION_CODES.ICE_CREAM_SANDWICH;
        }

        public static boolean hasIceCreamSandwichMR1() {
            return VERSION.SDK_INT >= VERSION_CODES.ICE_CREAM_SANDWICH_MR1;
        }

        public static boolean hasJellyBean() {
            return VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN;
        }

        public static boolean hasJellyBeanMR1() {
            return VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN_MR1;
        }

        public static boolean hasJellyBeanMR2() {
            return VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN_MR2;
        }

        public static boolean hasKitKat() {
            return VERSION.SDK_INT >= VERSION_CODES.KITKAT;
        }

        public static boolean hasLollipop() {
            return VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP;
        }

        public static boolean hasLollipopMR1() {
            return VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP_MR1;
        }

        public static boolean hasMarshmallow() {
            return VERSION.SDK_INT >= VERSION_CODES.M;
        }

    }


    public static final class Intent {

        public static boolean isSafe(final Context context, final android.content.Intent intent) {
            return !context.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY).isEmpty();
        }

        public static android.content.Intent browse(final Context context, final String url) {
            return new android.content.Intent(android.content.Intent.ACTION_VIEW, Uri.parse(url));
        }

        public static android.content.Intent share(final Context context, final String subject, final String message) {
            final android.content.Intent intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(android.content.Intent.EXTRA_TEXT, message);
            intent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
            intent.setFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK);
            return intent;
        }

        public static android.content.Intent dial(final Context context, final String number) {
            return new android.content.Intent(android.content.Intent.ACTION_DIAL, Uri.parse("tel:" + number));
        }

        @RequiresPermission(Manifest.permission.CALL_PHONE)
        public static android.content.Intent call(final Context context, final String number) {
            return new android.content.Intent(android.content.Intent.ACTION_CALL, Uri.parse("tel:" + number));
        }

        public static android.content.Intent sms(final Context context, final String number, final String message) {
            final Uri uri = Uri.parse("smsto:" + number);
            final android.content.Intent intent = new android.content.Intent(android.content.Intent.ACTION_SENDTO, uri);
            intent.putExtra("sms_body", message);
            return intent;
        }

        public static android.content.Intent mms(final Context context, final String number, final String subject, final String message, final Uri attachment) {
            final Uri uri = Uri.parse("mmsto:" + number);
            final android.content.Intent intent = new android.content.Intent(android.content.Intent.ACTION_SENDTO, uri);
            intent.putExtra("subject", subject);
            intent.putExtra("sms_body", message);
            if (attachment != null)
                intent.putExtra(android.content.Intent.EXTRA_STREAM, attachment);
            return intent;
        }

        public static android.content.Intent email(final Context context, final String[] to, final String[] cc, final String[] bcc, final String subject, final String body, final Uri attachment) {
            final android.content.Intent intent = new android.content.Intent(android.content.Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:"));
            if (to != null)
                intent.putExtra(android.content.Intent.EXTRA_EMAIL, to);
            if (cc != null)
                intent.putExtra(android.content.Intent.EXTRA_CC, cc);
            if (bcc != null)
                intent.putExtra(android.content.Intent.EXTRA_BCC, bcc);
            if (body != null)
                intent.putExtra(android.content.Intent.EXTRA_TEXT, body);
            if (subject != null)
                intent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
            if (attachment != null)
                intent.putExtra(android.content.Intent.EXTRA_STREAM, attachment);
            intent.setFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK);
            return intent;
        }

        public static android.content.Intent maps(final Context context, final double lat, final double lng) {
            return new android.content.Intent(android.content.Intent.ACTION_VIEW, Uri.parse("geo:" + lat + "," + lng));
        }

        public static android.content.Intent maps(final Context context, final double lat, final double lng, final int zoom) {
            return new android.content.Intent(android.content.Intent.ACTION_VIEW, Uri.parse("geo:" + lat + "," + lng + "?z=" + zoom));
        }

        public static android.content.Intent maps(final Context context, final double lat, final double lng, final String label) throws UnsupportedEncodingException {
            return new android.content.Intent(android.content.Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=" + lat + "," + lng + "(" + URLEncoder.encode(label, "UTF-8") + ")"));
        }

        public static android.content.Intent maps(final Context context, final String query) throws UnsupportedEncodingException {
            return new android.content.Intent(android.content.Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=" + URLEncoder.encode(query, "UTF-8")));
        }

        /**
         * @param mode d: Driving, w: Walking, r: Public transit, b: Biking
         */
        public static android.content.Intent navigation(final Context context, final String address, final String mode) throws UnsupportedEncodingException {
            return new android.content.Intent(android.content.Intent.ACTION_VIEW, Uri.parse("google.navigation:q=" + URLEncoder.encode(address, "UTF-8") + (mode == null ? "" : ("&mode=" + mode))));
        }

        /**
         * @param mode d: Driving, w: Walking, r: Public transit, b: Biking
         */
        public static android.content.Intent navigate(final Context context, final double lat, final double lng, final String mode) {
            return new android.content.Intent(android.content.Intent.ACTION_VIEW, Uri.parse("google.navigation:q=" + lat + "," + lng + (mode == null ? "" : ("&mode=" + mode))));
        }

        public static android.content.Intent install(final Context context, final Uri file) {
            final android.content.Intent intent = new android.content.Intent(android.content.Intent.ACTION_VIEW);
            intent.setDataAndType(file, "application/vnd.android.package-archive");
            return intent;
        }

        public static android.content.Intent uninstall(final Context context, final String packageName) {
            return new android.content.Intent(android.content.Intent.ACTION_DELETE, Uri.parse("package:" + packageName));
        }

        public static android.content.Intent playStore(final Context context, final String packageName) {
            return new android.content.Intent(android.content.Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName));
        }

        public static android.content.Intent playStorePublisher(final Context context, final String publisherName) {
            return new android.content.Intent(android.content.Intent.ACTION_VIEW, Uri.parse("market://search?q=pub:" + publisherName));
        }

        /**
         * @param category apps, movies, music, newsstand, devices
         */
        public static android.content.Intent playStoreCategory(final Context context, final String search, final String category) {
            return new android.content.Intent(android.content.Intent.ACTION_VIEW, Uri.parse("market://search?q=" + search + (category == null ? "" : ("&c=" + category))));
        }

        /**
         * @param collection featured, editors_choice, topselling_paid, topselling_free, topselling_new_free, topselling_new_paid, topgrossing, movers_shakers, topselling_paid_game
         */
        public static android.content.Intent playStoreCollection(final Context context, final String collection) {
            return new android.content.Intent(android.content.Intent.ACTION_VIEW, Uri.parse("market://apps/collection/" + collection));
        }

        public static android.content.Intent selectContact(final Context context) {
            final android.content.Intent intent = new android.content.Intent(android.content.Intent.ACTION_PICK);
            intent.setType(ContactsContract.Contacts.CONTENT_TYPE);
            return intent;
        }

        public static android.content.Intent picture(final Context context, final File file) {
            final android.content.Intent intent = new android.content.Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
            return intent;
        }

        public static android.content.Intent video(final Context context, final File file) {
            final android.content.Intent intent = new android.content.Intent(MediaStore.ACTION_VIDEO_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
            return intent;
        }

        public static android.content.Intent wifi(final Context context) {
            return new android.content.Intent(Settings.ACTION_WIFI_SETTINGS);
        }
    }

    public static final class Ui {

        public static void keepScreenOn(final Activity activity, final boolean keep) {
            if (keep) {
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            } else {
                activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            }
        }

        public static float px2dp(final float px, final Context context) {
            final DisplayMetrics metrics = context.getResources().getDisplayMetrics();
            return px / (metrics.densityDpi / 160f);
        }

        public static float dp2px(final float dp, final Context context) {
            final DisplayMetrics metrics = context.getResources().getDisplayMetrics();
            return dp * metrics.densityDpi / 160f;
        }

        public static void showSoftKeyboard(final View view, final ResultReceiver resultReceiver) {
            final Configuration config = view.getContext().getResources().getConfiguration();
            if (config.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_YES) {
                final InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (resultReceiver != null) {
                    imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT, resultReceiver);
                } else {
                    imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
                }
            }
        }

        public static void hideSoftKeyboard(final View view) {
            final InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        public static int actionBarHeight(final Activity activity) {
            final TypedValue value = new TypedValue();
            activity.getTheme().resolveAttribute(android.R.attr.actionBarSize, value, true);
            return activity.getResources().getDimensionPixelSize(value.resourceId);
        }

        public static void increaseHitRect(final int top, final int left, final int bottom, final int right, final View delegate) {
            final View parent = (View) delegate.getParent();
            parent.post(new Runnable() {
                public void run() {
                    final Rect r = new Rect();
                    delegate.getHitRect(r);
                    r.top -= top;
                    r.left -= left;
                    r.bottom += bottom;
                    r.right += right;
                    parent.setTouchDelegate(new TouchDelegate(r, delegate));
                }
            });
        }

        public static Bitmap captureView(final View view) {
            final Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
            view.draw(new Canvas(bitmap));
            return bitmap;
        }

        public static Bitmap captureLayout(final Context context, final int width, final int height, final int layoutResId) {
            final Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            final Canvas canvas = new Canvas(bitmap);
            final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View view = inflater.inflate(layoutResId, null);
            view.setDrawingCacheEnabled(true);
            view.measure(View.MeasureSpec.makeMeasureSpec(canvas.getWidth(), View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(canvas.getHeight(), View.MeasureSpec.EXACTLY));
            view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
            canvas.drawBitmap(view.getDrawingCache(), 0, 0, new Paint());
            return bitmap;
        }

        public static Drawable getColoredDrawable(final Context context, final int id, final int color) {
            Drawable drawable;
            if (Build.VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
                drawable = context.getDrawable(id);
            } else {
                //noinspection deprecation
                drawable = context.getResources().getDrawable(id);
            }
            if (drawable != null) {
                drawable.setColorFilter(color, PorterDuff.Mode.SRC_IN);
            }
            return drawable;
        }
    }

    public static final class Network {

        @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
        public static boolean isOnline(final Context context) {
            final NetworkInfo ni = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
            return (ni != null && ni.isAvailable() && ni.isConnected());
        }

        /**
         * {@link ConnectivityManager#TYPE_WIFI},
         * {@link ConnectivityManager#TYPE_ETHERNET},
         * {@link ConnectivityManager#TYPE_MOBILE}, ...
         */
        @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
        public static boolean isOnline(final Context context, final int type) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (VERSION.SDK_INT >= VERSION_CODES.M) {
                android.net.Network[] allNetworks = connectivityManager.getAllNetworks();
                if (allNetworks == null || allNetworks.length == 0) {
                    return false;
                }
                for (android.net.Network network : allNetworks) {
                    NetworkInfo ni = connectivityManager.getNetworkInfo(network);
                    if (ni != null && ni.isAvailable() && ni.isConnected()) {
                        return true;
                    }
                }
                return false;
            } else {
                //noinspection deprecation
                final NetworkInfo ni = connectivityManager.getNetworkInfo(type);
                return ni != null && ni.isAvailable() && ni.isConnected();
            }
        }

        @RequiresPermission(Manifest.permission.CHANGE_WIFI_STATE)
        public static boolean enableWifi(final Context context, final boolean enable) {
            return ((WifiManager) context.getSystemService(Context.WIFI_SERVICE)).setWifiEnabled(enable);
        }
    }

    public static final class Logcat {

        public static final String TAG = "Info.Logcat";

        /**
         * @param args More intel on <a href="http://developer.android.com/tools/debugging/debugging-log.html">developer.android.com</a>
         */
        public static String dump(final String[] args) {
            try {
                final Process process = Runtime.getRuntime().exec(args != null ? args : new String[]{"logcat", "-d"});
                final BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
                final String separator = System.getProperty("line.separator");
                final StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line).append(separator);
                }
                return sb.toString();
            } catch (Exception e) {
                Log.e(TAG, "Failed to capture logcat " + e.getMessage());
                return null;
            }
        }

        public static void clear() {
            try {
                Runtime.getRuntime().exec(new String[]{"logcat", "-c"});
            } catch (Exception e) {
                Log.e(TAG, "Failed to clear logcat " + e.getMessage());
            }
        }
    }

    public static final class Animation {
        public static ObjectAnimator flash(final View view, final float alphaFactor) {
            final PropertyValuesHolder pvhAlpha = PropertyValuesHolder.ofKeyframe(android.view.View.ALPHA, Keyframe.ofFloat(0f, 1f), Keyframe.ofFloat(.25f, alphaFactor), Keyframe.ofFloat(0.5f, 1f), Keyframe.ofFloat(.75f, alphaFactor), Keyframe.ofFloat(1f, 1f));
            return ObjectAnimator.ofPropertyValuesHolder(view, pvhAlpha).setDuration(800);
        }

        public static ObjectAnimator nope(final View view, final int delta) {
            final PropertyValuesHolder pvhTranslateX = PropertyValuesHolder.ofKeyframe(View.TRANSLATION_X, Keyframe.ofFloat(0f, 0),
                    Keyframe.ofFloat(.10f, -delta), Keyframe.ofFloat(.26f, delta), Keyframe.ofFloat(.42f, -delta), Keyframe.ofFloat(.58f, delta),
                    Keyframe.ofFloat(.74f, -delta), Keyframe.ofFloat(.90f, delta), Keyframe.ofFloat(1f, 0f));
            return ObjectAnimator.ofPropertyValuesHolder(view, pvhTranslateX).setDuration(500);
        }

        public static ObjectAnimator pulse(final View view, final float pulseFactor) {
            final PropertyValuesHolder pvhScaleX = PropertyValuesHolder.ofKeyframe(android.view.View.SCALE_X, Keyframe.ofFloat(0f, 1f), Keyframe.ofFloat(.5f, pulseFactor * 1f), Keyframe.ofFloat(1f, 1f));
            final PropertyValuesHolder pvhScaleY = PropertyValuesHolder.ofKeyframe(android.view.View.SCALE_Y, Keyframe.ofFloat(0f, 1f), Keyframe.ofFloat(.5f, pulseFactor * 1f), Keyframe.ofFloat(1f, 1f));
            return ObjectAnimator.ofPropertyValuesHolder(view, pvhScaleX, pvhScaleY).setDuration(500);
        }

        public static ObjectAnimator spring(final View view, final float springFactor) {
            final PropertyValuesHolder pvhScaleX = PropertyValuesHolder.ofKeyframe(android.view.View.SCALE_X, Keyframe.ofFloat(0f, 1f),
                    Keyframe.ofFloat(0.25f, springFactor * 1.35f), Keyframe.ofFloat(0.5f, 0.65f / springFactor),
                    Keyframe.ofFloat(0.75f, springFactor * 1.15f), Keyframe.ofFloat(1f, 1f));
            final PropertyValuesHolder pvhScaleY = PropertyValuesHolder.ofKeyframe(android.view.View.SCALE_Y, Keyframe.ofFloat(0f, 1f),
                    Keyframe.ofFloat(0.25f, 0.65f / springFactor), Keyframe.ofFloat(0.5f, springFactor * 1.35f),
                    Keyframe.ofFloat(0.75f, 0.85f / springFactor), Keyframe.ofFloat(1f, 1f));
            return ObjectAnimator.ofPropertyValuesHolder(view, pvhScaleX, pvhScaleY).setDuration(600);
        }

        public static ObjectAnimator tada(final View view, final float shakeFactor) {
            final PropertyValuesHolder pvhScaleX = PropertyValuesHolder.ofKeyframe(View.SCALE_X, Keyframe.ofFloat(0f, 1f),
                    Keyframe.ofFloat(.1f, .9f), Keyframe.ofFloat(.2f, .9f), Keyframe.ofFloat(.3f, 1.1f), Keyframe.ofFloat(.4f, 1.1f),
                    Keyframe.ofFloat(.5f, 1.1f), Keyframe.ofFloat(.6f, 1.1f), Keyframe.ofFloat(.7f, 1.1f), Keyframe.ofFloat(.8f, 1.1f),
                    Keyframe.ofFloat(.9f, 1.1f), Keyframe.ofFloat(1f, 1f));

            final PropertyValuesHolder pvhScaleY = PropertyValuesHolder.ofKeyframe(View.SCALE_Y, Keyframe.ofFloat(0f, 1f),
                    Keyframe.ofFloat(.1f, .9f), Keyframe.ofFloat(.2f, .9f), Keyframe.ofFloat(.3f, 1.1f), Keyframe.ofFloat(.4f, 1.1f),
                    Keyframe.ofFloat(.5f, 1.1f), Keyframe.ofFloat(.6f, 1.1f), Keyframe.ofFloat(.7f, 1.1f), Keyframe.ofFloat(.8f, 1.1f),
                    Keyframe.ofFloat(.9f, 1.1f), Keyframe.ofFloat(1f, 1f));

            final PropertyValuesHolder pvhRotate = PropertyValuesHolder.ofKeyframe(View.ROTATION, Keyframe.ofFloat(0f, 0f),
                    Keyframe.ofFloat(.1f, -3f * shakeFactor), Keyframe.ofFloat(.2f, -3f * shakeFactor), Keyframe.ofFloat(.3f, 3f * shakeFactor),
                    Keyframe.ofFloat(.4f, -3f * shakeFactor), Keyframe.ofFloat(.5f, 3f * shakeFactor), Keyframe.ofFloat(.6f, -3f * shakeFactor),
                    Keyframe.ofFloat(.7f, 3f * shakeFactor), Keyframe.ofFloat(.8f, -3f * shakeFactor), Keyframe.ofFloat(.9f, 3f * shakeFactor),
                    Keyframe.ofFloat(1f, 0));

            return ObjectAnimator.ofPropertyValuesHolder(view, pvhScaleX, pvhScaleY, pvhRotate).setDuration(1000);
        }
    }

    public static final class Other {
        public static String bytes2size(final long size) {
            if (size <= 0)
                return "0";
            final String[] units = new String[]{"B", "KB", "MB", "GB", "TB", "PB", "EB"};
            final int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
            return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
        }
    }
}