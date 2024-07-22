# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile


-keep class com.system.weather.data.** { *; }

-keep class com.system.weatherapp.di.** { *; }
-keep class com.system.weatherapp.utils.** { *;}

#Glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep class * extends com.bumptech.glide.module.AppGlideModule {
 <init>(...);
}
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

# Retrofit
#----------------------Retrofit----------------------
-keepattributes Signature, InnerClasses, EnclosingMethod
-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations
-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}
-dontwarn javax.annotation.**
-dontwarn kotlin.Unit
-dontwarn retrofit2.KotlinExtensions
-dontwarn retrofit2.KotlinExtensions$*
-if interface * { @retrofit2.http.* <methods>; }
-keep,allowobfuscation interface <1>
-keep class retrofit2.Response
-keep class retrofit2.** { *; }
-keep,allowobfuscation,allowshrinking interface retrofit2.Call
-keep,allowobfuscation,allowshrinking class retrofit2.Response
-keep,allowobfuscation,allowshrinking class kotlin.coroutines.Continuation

# Gson
-keepattributes Signature
-keepattributes Exceptions
-keepattributes InnerClasses
-keep class com.google.gson.stream.** { *; }

-keep class androidx.lifecycle.LiveData { *; }
# OkHttp
-keep class okhttp3.** { *; }
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn javax.annotation.**

#-------------  Okhttp3-------------------------
-dontwarn javax.annotation.**
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase
-keep class okhttp3.Response
-keep class okhttp3.ResponseBody
-keep class okhttp3.** { *; }
-dontwarn org.codehaus.mojo.animal_sniffer.*
-dontwarn okhttp3.internal.platform.ConscryptPlatform

# OkHttp Logging Interceptor
-keep class okhttp3.logging.** { *; }

#------------  Databinding------------
 -dontwarn android.databinding.**
 -keep class android.databinding.** { *; }
 -keep class android.databinding.annotationprocessor.** { *; }

 #------------  Sqlcipher------------
 -keep class net.sqlcipher.** { *; }
 -keep class net.sqlcipher.database.* { *; }

#------------  Sqlcipher------------
# SQLCipher
-keep class net.sqlcipher.** {
    *;
}

# SQLite
-keep class androidx.sqlite.** {
    *;
}

# Keep SQLite Custom Functions (modify as needed)
-keep class net.zetetic.database.sqlcipher.** {
    *;
}


# Keep Hilt component methods
-keepclassmembers,allowobfuscation class * {
    @dagger.hilt.android.scopes.ActivityScoped *;
    @dagger.hilt.android.scopes.FragmentScoped *;
    @dagger.hilt.android.scopes.ViewModelScoped *;
}

# Keep Hilt generated factory classes
-keepclassmembers,allowobfuscation class * {
    @dagger.hilt.android.internal.lifecycle.*Factory *;
}

