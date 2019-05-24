# godot_appodeal

godot_appodeal is a appodeal integration for godot

[![Platform](https://img.shields.io/badge/Platform-Android-green.svg)]()
[![GodotEngine](https://img.shields.io/badge/Godot%20Engine-3.1-blue.svg)](https://github.com/godotengine/godot)
[![PATREON](https://img.shields.io/badge/Patreon-support-yellow.svg)](https://www.patreon.com/dumbcatstudio)

Now only supports android platform

# Available Features
> Show Interstitial

> Show Rewarded Video

> Banners (Show top banner, show bottom banner, hide banner, destroy banner)


# How to build
* Clone repository to modules directory of godot engine
* Copy your `google-services.json` file to `/platform/android/java` folder of godot engine

* Change Google AdMob application id in godot_appodeal/android/AndroidManifestChunk.xml

```
<meta-data
    android:name="com.google.android.gms.ads.APPLICATION_ID"
android:value="[YOUR_ADMOB_APP_ID]"/>
```

# Configuration
Add this strings to your `project.godot` file

```
[appodeal]

app_key="your-key-here"
is_interstitial_ad_enabled=true
is_rewarded_video_ad_enabled=true
is_non_skippable_video_ad_enabled=false
is_banner_ad_enabled=true
is_native_ad_enabled=false
is_debug_enabled=true
is_test_ads_enabled=false
```

And change `your-key-here` to your appodeal key

After following steps you can change this settings from project settings.

# Usage

## Get appodeal singleton

`var appodeal = Engine.get_singleton("GodotAppodeal")`

Available methods:

> show_interstitial()

> is_interstitial_loaded()

> show_rewarded_video()

> is_rewarded_video_loaded()

> get_reward_parameters(string placement_name)

> get_reward_parameters()

> show_bottom_banner()

> show_top_banner()

> hide_banner()

> destroy_banner()

> start_test_activity()

> track_inapp_purchase(double amount, String currency)

> add_callback(String callback_name, int instance_id, String method_name)

> remove_callback(String callback_name, int instance_id, String method_name)

Example:
```
def _ready():
  var appodeal = Engine.get_singleton("GodotAppodeal")

  if appodeal.is_interstitial_loaded():
    appodeal.show_interstitial()
```

## Subscribe to callbacks

`appodeal.add_callback('<callback_name>', get_instance_ID(), '<local_method_name>')`

Available callbacks:
> onInterstitialLoaded(boolean)

> onInterstitialFailedToLoad()

> onInterstitialShown()

> onInterstitialClicked()

> onInterstitialClosed()

> onInterstitialExpired()

> onRewardedVideoLoaded(boolean)

> onRewardedVideoFailedToLoad()

> onRewardedVideoShown()

> onRewardedVideoFinished(double, string)

> onRewardedVideoClosed(boolean)

> onRewardedVideoExpired()

> onBannerLoaded(int, boolean)

> onBannerFailedToLoad()

> onBannerShown()

> onBannerClicked()

> onBannerExpired()


Example:
```

var appodeal = Engine.get_singleton("GodotAppodeal")

def on_rewarded_video_finished(v, s):
  pass

def _ready():
  appodeal.add_callback('onRewardedVideoFinished', get_instance_ID(), 'on_rewarded_video_finished')
  if appodeal.is_rewarded_video_loaded():
    appodeal.show_rewarded_video()
```
  

