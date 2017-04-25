package android.novica.shoppinglist.constants;

import android.net.Uri;
import android.novica.shoppinglist.R;

/**
 * Created by Novica on 4/22/2017.
 */

public class PathConstants {
    public static final String ROOT_PACKAGE_NAME = "android.resource://android.novica.shoppinglist/";
    public static final String ACTIVE_BASKET_PHOTO_PATH = Uri.parse(ROOT_PACKAGE_NAME + R.drawable.active_basket).toString();
    public static final String COMPLETE_BASKET_PHOTO_PATH = Uri.parse(ROOT_PACKAGE_NAME + R.drawable.complete_basket).toString();
    public static final String PREF_NAME = "MyPrefsFile";
}