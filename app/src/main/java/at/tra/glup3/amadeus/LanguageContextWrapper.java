package at.tra.glup3.amadeus;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.preference.PreferenceManager;

import java.util.Locale;

// SOLUTION from http://stackoverflow.com/a/40704077


public class LanguageContextWrapper extends ContextWrapper {

    public LanguageContextWrapper(Context base) {
        super(base);
    }


    @SuppressWarnings("deprecation")
    public static ContextWrapper wrap(Context context) {

        String standardLanguage = "en";
        SharedPreferences mySettings = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());

        String selectedLanguage = mySettings.getString("pref_language_key", standardLanguage);

        Configuration config = context.getResources().getConfiguration();
        Locale sysLocale;

        // Get System Language (Device Language)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            sysLocale = getSystemLocale(config);
        }
        else {
            sysLocale = getSystemLocaleLegacy(config);
        }

        // Change Language
        if (!selectedLanguage.equals("") && !sysLocale.getLanguage().equals(selectedLanguage)) {
            Locale locale = new Locale(selectedLanguage);
            Locale.setDefault(locale);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                setSystemLocale(config, locale);
            }
            else {
                setSystemLocaleLegacy(config, locale);
            }

        }

        // Apply Changes
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            context = context.createConfigurationContext(config);
        }
        else {
            context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
        }

        return new LanguageContextWrapper(context);
    }

    @SuppressWarnings("deprecation")
    public static Locale getSystemLocaleLegacy(Configuration config){
        return config.locale;
    }

    @TargetApi(Build.VERSION_CODES.N)
    public static Locale getSystemLocale(Configuration config){
        return config.getLocales().get(0);
    }

    @SuppressWarnings("deprecation")
    public static void setSystemLocaleLegacy(Configuration config, Locale locale){
        config.locale = locale;
    }

    @TargetApi(Build.VERSION_CODES.N)
    public static void setSystemLocale(Configuration config, Locale locale){
        config.setLocale(locale);
    }


}
