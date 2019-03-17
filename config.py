
def can_build(env, platform):
    return platform == "android"

def configure(env):
    if env['platform'] == 'android':
        env.android_add_default_config("multiDexEnabled true")
        env.android_add_java_dir("android/src")
        env.android_add_flat_dir("../../../modules/appodeal/android/libs/")
        env.android_add_to_manifest("android/AndroidManifestChunk.xml")
        env.android_add_to_permissions("android/AndroidPermissionsChunk.xml")
        env.android_add_res_dir("android/res")
        env.android_add_dependency("implementation fileTree(dir: '../../../modules/appodeal/android/libs', include: '*.jar')")
        env.android_add_dependency("implementation 'com.android.support:recyclerview-v7:28.0.0'")
        env.android_add_dependency("implementation 'com.android.support:support-v4:28.0.0'")
        env.android_add_dependency("implementation 'com.google.android.gms:play-services-ads:17.2.0'")
        env.android_add_dependency("implementation 'com.google.android.gms:play-services-location:16.0.0'")
        env.android_add_dependency("implementation 'com.squareup.picasso:picasso:2.5.2' //for Inmobi")
        env.android_add_dependency("implementation name: 'adcolony-3.3.4', ext: 'aar'")
        env.android_add_dependency("implementation name: 'mobvista-9.0.0-alphab', ext: 'aar'")
        env.android_add_dependency("implementation name: 'mobvista-9.0.0-common', ext: 'aar'")
        env.android_add_dependency("implementation name: 'mobvista-9.0.0-interstitial', ext: 'aar'")
        env.android_add_dependency("implementation name: 'mobvista-9.0.0-interstitialvideo', ext: 'aar'")
        env.android_add_dependency("implementation name: 'mobvista-9.0.0-mtgdownloads', ext: 'aar'")
        env.android_add_dependency("implementation name: 'mobvista-9.0.0-mtgjscommon', ext: 'aar'")
        env.android_add_dependency("implementation name: 'mobvista-9.0.0-mtgnative', ext: 'aar'")
        env.android_add_dependency("implementation name: 'mobvista-9.0.0-nativeex', ext: 'aar'")
        env.android_add_dependency("implementation name: 'mobvista-9.0.0-playercommon', ext: 'aar'")
        env.android_add_dependency("implementation name: 'mobvista-9.0.0-reward', ext: 'aar'")
        env.android_add_dependency("implementation name: 'mobvista-9.0.0-videocommon', ext: 'aar'")
        env.android_add_dependency("implementation name: 'mobvista-9.0.0-videojs', ext: 'aar'")
        env.android_add_dependency("implementation name: 'ogury-3.0.13', ext: 'aar'")