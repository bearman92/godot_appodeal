/* */

package org.godotengine.godot;

import com.appodeal.ads.Appodeal;
import com.appodeal.ads.BannerCallbacks;
import com.appodeal.ads.InterstitialCallbacks;
import com.appodeal.ads.RewardedVideoCallbacks;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.app.Activity;
import android.util.Log;
import android.util.Pair;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class GodotCallback {
	public int instanceId;
	public String methodName;

	public GodotCallback(int p_instanceId, String p_methodName) {
		instanceId = p_instanceId;
		methodName = p_methodName;
	}

	public boolean equals(GodotCallback other) {
		return instanceId == other.instanceId && methodName.equals(other.methodName);
	}

	public boolean equals(int p_instanceId, String p_methodName){
		return instanceId == p_instanceId && methodName == p_methodName;
	}
}

public class GodotAppodeal extends Godot.SingletonBase {
	private Activity m_activity;

	private Map<String, ArrayList<GodotCallback>> m_godotCallbacks = new HashMap<String, ArrayList<GodotCallback>>();

    static public Godot.SingletonBase initialize(Activity p_activity) {
        return new GodotAppodeal(p_activity);
    }

    public GodotAppodeal(Activity p_activity) {
        registerClass("GodotAppodeal", new String[]{
        		"show_interstitial",
				"is_interstitial_loaded",
				"show_rewarded_video",
				"is_rewarded_video_loaded",
				"get_reward_parameters",
				"show_bottom_banner",
				"show_top_banner",
				"hide_banner",
				"destroy_banner",
				"set_debug_mode",
				"start_test_activity",
				"track_inapp_purchase",
				"add_callback",
				"remove_callback"
        });

        m_activity = p_activity;

        p_activity.runOnUiThread(new Runnable() {
            public void run() {
            	//Получаем ключ приложения из настроек проекта
				String appKey = GodotLib.getGlobal("appodeal/app_key");

				//Собираем флаги для активации необходимых рекламных блоков
				int adFlags = 0;
				if(GodotLib.getGlobal("appodeal/is_interstitial_ad_enabled").equals("true")) {
					adFlags |= Appodeal.INTERSTITIAL;
					Log.d("GodotAppodeal", "Interstitial initialized");
				}
				if(GodotLib.getGlobal("appodeal/is_rewarded_video_ad_enabled").equals("true")) {
					adFlags |= Appodeal.REWARDED_VIDEO;
					Log.d("GodotAppodeal", "Rewarded video initialized");
				}
				else if(GodotLib.getGlobal("appodeal/is_non_skippable_video_ad_enabled").equals("true")) {
					adFlags |= Appodeal.NON_SKIPPABLE_VIDEO;
					Log.d("GodotAppodeal", "Non skippable video initialized");
				}
				if(GodotLib.getGlobal("appodeal/is_banner_ad_enabled").equals("true")) {
					adFlags |= Appodeal.BANNER;
					Log.d("GodotAppodeal", "Banner initialized");
				}
				if(GodotLib.getGlobal("appodeal/is_native_ad_enabled").equals("true")) {
					adFlags |= Appodeal.NATIVE;
					Log.d("GodotAppodeal", "Native initialized");
				}

				//TODO: Run callbacks

				Appodeal.initialize(m_activity, appKey, adFlags);
				Appodeal.setInterstitialCallbacks(new InterstitialCallbacks() {
					@Override
					public void onInterstitialLoaded(boolean b) {
						Log.d("GodotAppodeal", "onInterstitialLoaded");
						broadcastCallbacks("onInterstitialLoaded", new Object[] { b });
					}

					@Override
					public void onInterstitialFailedToLoad() {
						Log.d("GodotAppodeal", "onInterstitialFailedToLoad");
						broadcastCallbacks("onInterstitialFailedToLoad", new Object[] {});
					}

					@Override
					public void onInterstitialShown() {
						Log.d("GodotAppodeal", "onInterstitialShown");
						broadcastCallbacks("onInterstitialShown", new Object[] {});
					}

					@Override
					public void onInterstitialClicked() {
						Log.d("GodotAppodeal", "onInterstitialClicked");
						broadcastCallbacks("onInterstitialClicked", new Object[] {});
					}

					@Override
					public void onInterstitialClosed() {
						Log.d("GodotAppodeal", "onInterstitialClosed");
						broadcastCallbacks("onInterstitialClosed", new Object[] {});
					}

					@Override
					public void onInterstitialExpired() {
						Log.d("GodotAppodeal", "onInterstitialExpired");
						broadcastCallbacks("onInterstitialExpired", new Object[] {});
					}
				});
				Appodeal.setRewardedVideoCallbacks(new RewardedVideoCallbacks() {
					@Override
					public void onRewardedVideoLoaded(boolean b) {
						Log.d("GodotAppodeal", "onRewardedVideoLoaded");
						broadcastCallbacks("onRewardedVideoLoaded", new Object[] { b });
					}

					@Override
					public void onRewardedVideoFailedToLoad() {
						Log.d("GodotAppodeal", "onRewardedVideoFailedToLoad");
						broadcastCallbacks("onRewardedVideoFailedToLoad", new Object[] {});
					}

					@Override
					public void onRewardedVideoShown() {
						Log.d("GodotAppodeal", "onRewardedVideoShown");
						broadcastCallbacks("onRewardedVideoShown", new Object[] {});
					}

					@Override
					public void onRewardedVideoFinished(double v, String s) {
						Log.d("GodotAppodeal", "onRewardedVideoFinished");
						broadcastCallbacks("onRewardedVideoFinished", new Object[] { v, s });
					}

					@Override
					public void onRewardedVideoClosed(boolean b) {
						Log.d("GodotAppodeal", "onRewardedVideoClosed");
						broadcastCallbacks("onRewardedVideoClosed", new Object[] { b });
					}

					@Override
					public void onRewardedVideoExpired() {
						Log.d("GodotAppodeal", "onRewardedVideoExpired");
						broadcastCallbacks("onRewardedVideoExpired", new Object[] {});
					}
				});
				Appodeal.setBannerCallbacks(new BannerCallbacks() {
					@Override
					public void onBannerLoaded(int i, boolean b) {
						Log.d("GodotAppodeal", "onBannerLoaded");
						broadcastCallbacks("onBannerLoaded", new Object[] { i, b });
					}

					@Override
					public void onBannerFailedToLoad() {
						Log.d("GodotAppodeal", "onBannerFailedToLoad");
						broadcastCallbacks("onBannerFailedToLoad", new Object[] {});
					}

					@Override
					public void onBannerShown() {
						Log.d("GodotAppodeal", "onBannerShown");
						broadcastCallbacks("onBannerShown", new Object[] {});
					}

					@Override
					public void onBannerClicked() {
						Log.d("GodotAppodeal", "onBannerClicked");
						broadcastCallbacks("onBannerClicked", new Object[] {});
					}

					@Override
					public void onBannerExpired() {
						Log.d("GodotAppodeal", "onBannerExpired");
						broadcastCallbacks("onBannerExpired", new Object[] {});
					}
				});

				//Appodeal.muteVideosIfCallsMuted(true);
            }
        });
    }

    private void broadcastCallbacks(String p_callbackName, Object[] p_params) {
    	if(!m_godotCallbacks.containsKey(p_callbackName))
    		return;

		ArrayList<GodotCallback> callbacks = m_godotCallbacks.get(p_callbackName);

		GodotCallback callback = null;
		for(int i = 0; i < callbacks.size(); i++) {
			callback = callbacks.get(i);
			GodotLib.calldeferred(callback.instanceId, callback.methodName, p_params);
		}
	}

    // Interstitital
    public void show_interstitial() {
		Appodeal.show(m_activity, Appodeal.INTERSTITIAL);
	}

	public boolean is_interstitial_loaded() {
		return Appodeal.isLoaded(Appodeal.INTERSTITIAL);
	}

	// Rewarded video
	public void show_rewarded_video() {
    	Appodeal.show(m_activity, Appodeal.REWARDED_VIDEO);
	}

	public boolean is_rewarded_video_loaded() {
    	return Appodeal.isLoaded(Appodeal.REWARDED_VIDEO);
	}

	public Dictionary get_reward_parameters(String placement_name) {
		Pair<Double, String> RewardParameters = Appodeal.getRewardParameters(placement_name);

		Dictionary result = new Dictionary();
		result.put(RewardParameters.second, RewardParameters.first);
		return result;
	}

	public Dictionary get_reward_parameters() {
		Pair<Double, String> RewardParameters = Appodeal.getRewardParameters();

		Dictionary result = new Dictionary();
		result.put(RewardParameters.second, RewardParameters.first);
		return result;
	}

	public void show_bottom_banner() {
    	Appodeal.show(m_activity, Appodeal.BANNER_BOTTOM);
	}

	public void show_top_banner() {
    	Appodeal.show(m_activity, Appodeal.BANNER_TOP);
	}

	public void hide_banner() {
    	Appodeal.hide(m_activity, Appodeal.BANNER);
	}

	public void destroy_banner() {
    	Appodeal.destroy(Appodeal.BANNER);
	}

	public void set_debug_mode() {
		Appodeal.setLogLevel(com.appodeal.ads.utils.Log.LogLevel.debug);
	}

	public void start_test_activity() {
    	Appodeal.startTestActivity(m_activity);
	}

	public void track_inapp_purchase(double amount, String currency) {
    	Appodeal.trackInAppPurchase(m_activity, amount, currency);
	}

	public void add_callback(String callback_name, int instance_id, String method_name) {
    	ArrayList<GodotCallback> callbacksArray = null;

    	if(!m_godotCallbacks.containsKey(callback_name)) {
    		callbacksArray = m_godotCallbacks.put(callback_name, new ArrayList<GodotCallback>());
		}

		if(callbacksArray == null){
    		callbacksArray = m_godotCallbacks.get(callback_name);
		}

		GodotCallback newCallback = new GodotCallback(instance_id, method_name);

		for(int i = 0; i < callbacksArray.size(); i++) {
			if(callbacksArray.get(i).equals(newCallback)) {
				return;
			}
		}

		callbacksArray.add(newCallback);
	}

	public void remove_callback(String callback_name, int instance_id, String method_name) {

    	if(!m_godotCallbacks.containsKey(callback_name))
    		return;

		ArrayList<GodotCallback> callbacksArray = m_godotCallbacks.get(callback_name);

		if(callbacksArray.size() == 0)
			return;

		for(int i = 0; i < callbacksArray.size(); i++) {
			if(callbacksArray.get(i).equals(instance_id, method_name)) {
				callbacksArray.remove(i);
				return;
			}
		}
	}

    protected void onMainActivityResult(int requestCode, int resultCode, Intent data) {}

    protected void onMainPause() {}

    @Override
    protected void onMainResume() {
		super.onMainResume();

		Appodeal.onResume(m_activity, Appodeal.BANNER);
	}

    protected void onMainDestroy() {}
}
