package com.aero.droid.dutyfree.talent.util;

import android.content.Context;
import android.text.TextUtils;


import com.aero.droid.dutyfree.talent.R;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TimeFormatUtil {

	private static final long ONE_DAY = 1000 * 60 * 60 * 24;

	public static long getCurrentTime() {
		Calendar c = new GregorianCalendar();
		long lTime = c.getTimeInMillis();
		return lTime;
	}

	public static String getCurrentTimeString() {
		Calendar c = new GregorianCalendar();
		long lTime = c.getTimeInMillis();
		return String.valueOf(lTime);
	}


	public static String getTimestampFull(String format, long time) {
		if (time == 0) {
			return "";
		}
		Date date = new Date(time);
		SimpleDateFormat dateformat = new SimpleDateFormat(format);
		String timeLabelStr = dateformat.format(date);
		return timeLabelStr;
	}

	public static int getCurrentHour() {
		Calendar currentTime = Calendar.getInstance();
		return currentTime.get(Calendar.HOUR_OF_DAY);
	}

	public static String getTimestamp(Context context, String strMilliseconds) {
		if (TextUtils.isEmpty(strMilliseconds)) {
			return "";
		}
		long milliseconds = 0;
		try {
			milliseconds = Long.parseLong(strMilliseconds);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getTimestamp(context, milliseconds);
	}

	public static String getTimestamp(Context context, long milliseconds) {
		if (milliseconds == 0) {
			return "";
		}

		Calendar currentTime = Calendar.getInstance();
		int currentYear = currentTime.get(Calendar.YEAR);
		int currentMonth = currentTime.get(Calendar.MONTH);
		int currentDay = currentTime.get(Calendar.DAY_OF_YEAR);
		Calendar targetTime = Calendar.getInstance();
		targetTime.setTimeInMillis(milliseconds);
		int targetYear = targetTime.get(Calendar.YEAR);
		int targetMonth = targetTime.get(Calendar.MONTH);
		int targetDay = targetTime.get(Calendar.DAY_OF_YEAR);

		long current = System.currentTimeMillis();
		long cDay = current / ONE_DAY;
		long tDay = milliseconds / ONE_DAY;

		String format = context.getString(R.string.time_format_date);
		if (targetDay == currentDay) {
			format = context.getString(R.string.time_format_today);
		} else if (cDay == tDay + 1) {
			format = context.getString(R.string.time_format_yesterday);
		} else if (cDay == tDay + 2) {
			format = context.getString(R.string.time_format_pre_day);
		} else if (targetYear != currentYear) {
			format = context.getString(R.string.time_format_year);
		} else if (targetMonth != currentMonth) {
			format = context.getString(R.string.time_format_month);
		}

		Date date = new Date(milliseconds);
		SimpleDateFormat dateformat = new SimpleDateFormat(format);
		String timeLabelStr = dateformat.format(date);

		return timeLabelStr;
	}

	public static int[] formatCurrentTime() {
		int[] time = new int[2];
		Calendar currentTime = Calendar.getInstance();
		int hour = currentTime.get(Calendar.HOUR_OF_DAY);
		int minute = currentTime.get(Calendar.MINUTE);
		time[0] = hour;
		time[1] = minute;
		return time;
	}

	static SimpleDateFormat PROTOCAL_FORMAT = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	public static long getTime(String str) {
		Timestamp t = null;
		try {
			if (TextUtils.isEmpty(str)) {
				return 0;
			}
			t = new Timestamp(PROTOCAL_FORMAT.parse(str).getTime());
			return t.getTime();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 获取现在时间
	 * @return 返回短时间字符串格式yyyy-MM-dd HH:mm:ss
	 */
	public static String getStringDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(new Date());
	}
	/**
	 * 获取现在时间
	 * @return 返回短时间字符串格式yyyy-MM-dd
	 */
	public static String getStringDate1() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(new Date());
	}

	/**
	 * 获取现在时间
	 * @return 返回短时间字符串格式 HH:mm:ss
	 */
	public static String getStringDate3() {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		return sdf.format(new Date());
	}
	/**
	 * @param regex 日期格式
	 * @return  指定日期格式的当期日期
	 * @throws Exception 格式异常
	 */
	public static String formatDate(String regex) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat(regex);
		return sdf.format(new Date());
	}
	/**
	 *
	 * @return 返回短时间字符串格式yyyy-MM-dd HH:mm
	 */
	public static String translateDate(long date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}
	/**
	 *
	 * @return 返回短时间字符串格式yyyy/MM/dd
	 */
	public static String translateDate1(long date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		return sdf.format(new Date(date));
	}


	public static String formatDigist(String value) {
		DecimalFormat format = new DecimalFormat("##,###,###.00");
		float newValue = Float.parseFloat(value);
		return  format.format(newValue);
	}

	/**
	 * 将xxxx-xx-xx时间格式转为 时间戳
	 * @param value
	 * @return
	 */
	public static long formatStringDate(String value) {
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date  = myFormatter.parse(value);
		} catch (ParseException e) {
			e.printStackTrace();
			return 0;
		}
		return  date.getTime();
	}

	/**
	 * 得到两个日期的间隔天数
	 * @param sj1
	 * @param sj2
	 * @return
	 */
	public static String getTwoDay(String sj1, String sj2) {
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		long day = 0;
		try {
			java.util.Date date = myFormatter.parse(sj1);
			java.util.Date mydate = myFormatter.parse(sj2);
			day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
		} catch (Exception e) {
			return "";
		}
		return Math.abs(day) + "";
	}

	public static String getCameraPhotoName(){
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String name = format.format(new Date());
		return "camera_photo_"+name+".jpg";
	}



}
