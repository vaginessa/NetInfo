/*
 * Copyright 2014 ultrafunk.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ultrafunk.network_info.receiver;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import java.lang.reflect.Method;

public class MobileDataUtils
{
	public static boolean getMobileDataEnabled(Context context)
	{
		try
		{
			ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			Method method = connectivityManager.getClass().getDeclaredMethod("getMobileDataEnabled");
			return (Boolean) method.invoke(connectivityManager);
		}
		catch (Exception exception)
		{
			Toast.makeText(context, "Failed to get Mobile data enabled state!", Toast.LENGTH_LONG).show();
		}

		return false;
	}

	public static void setMobileDataEnabled(Context context, boolean enable)
	{
		try
		{
			ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			Method setMobileDataEnabledMethod = ConnectivityManager.class.getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);
			setMobileDataEnabledMethod.setAccessible(true);
			setMobileDataEnabledMethod.invoke(connectivityManager, enable);
		}
		catch (Exception exception)
		{
			Toast.makeText(context, "Failed to enable or disable Mobile data!", Toast.LENGTH_LONG).show();
		}
	}

	public static boolean getAirplaneModeOn(Context context)
	{
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1)
			return Settings.System.getInt(context.getContentResolver(),	Settings.System.AIRPLANE_MODE_ON, 0) != 0;
		else
			return Settings.Global.getInt(context.getContentResolver(),	Settings.Global.AIRPLANE_MODE_ON, 0) != 0;
	}

	public static String getNetworkTypeString(TelephonyManager telephonyManager)
	{
		switch (telephonyManager.getNetworkType())
		{
			case TelephonyManager.NETWORK_TYPE_GPRS:	return "GPRS 2G";
			case TelephonyManager.NETWORK_TYPE_EDGE:	return "EDGE 2G";
			case TelephonyManager.NETWORK_TYPE_UMTS:	return "UMTS 3G";
			case TelephonyManager.NETWORK_TYPE_CDMA:	return "CDMA 3G";
			case TelephonyManager.NETWORK_TYPE_EVDO_0:	return "EVDO 0 3G";
			case TelephonyManager.NETWORK_TYPE_EVDO_A:	return "EVDO A 3G";
			case TelephonyManager.NETWORK_TYPE_EVDO_B:	return "EVDO B 3G";
			case TelephonyManager.NETWORK_TYPE_1xRTT:	return "1xRTT 3G";
			case TelephonyManager.NETWORK_TYPE_HSDPA:	return "HSDPA 3G";
			case TelephonyManager.NETWORK_TYPE_HSUPA:	return "HSUPA 3G";
			case TelephonyManager.NETWORK_TYPE_HSPA:	return "HSPA 3G";
			case TelephonyManager.NETWORK_TYPE_LTE:		return "LTE 4G";
			case TelephonyManager.NETWORK_TYPE_EHRPD:	return "eHRPD 3/4G";
			case TelephonyManager.NETWORK_TYPE_HSPAP:	return "HSPA+ 3G";
		}

		return "Unknown";
	}
}