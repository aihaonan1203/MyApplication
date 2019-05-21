package com.gaotai.baselib.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.text.TextUtils;

/**
 * 日期工具。
 * 
 */
public class DcDateUtils {
	/** yyyy-MM-dd. */
	public static final String FORMAT_YMD_1 = "yyyy-MM-dd";

	/** yyyy/MM/dd. */
	public static final String FORMAT_YMD_2 = "yyyy/MM/dd";

	/** yyyyMMdd. */
	public static final String FORMAT_YMD_3 = "yyyyMMdd";

	/** yy/MM/dd. */
	public static final String FORMAT_YMD_4 = "yy/MM/dd";

	/** yyyy/M/d. */
	public static final String FORMAT_YMD_5 = "yyyy/M/d";

	/** yyMMdd. */
	public static final String FORMAT_YMD_6 = "yyMMdd";

	/** yyyy.MM.dd. */
	public static final String FORMAT_YMD_7 = "yyyy.MM.dd";

	/** yyyyMM. */
	public static final String FORMAT_YM_1 = "yyyyMM";

	/** yyyy/MM. */
	public static final String FORMAT_YM_2 = "yyyy/MM";

	/** yyyy-MM-dd HH:mm:ss. */
	public static final String FORMAT_DATE_TIME_1 = "yyyy-MM-dd HH:mm:ss";

	/** yyyy/MM/dd HH:mm:ss. */
	public static final String FORMAT_DATE_TIME_2 = "yyyy/MM/dd HH:mm:ss";

	/** yyyyMMddHHmmss. */
	public static final String FORMAT_DATE_TIME_3 = "yyyyMMddHHmmss";

	/** yyyy-MM-dd HH:mm. */
	public static final String FORMAT_DATE_TIME_4 = "yyyy-MM-dd HH:mm";

	/** yyyy/MM/dd HH:mm. */
	public static final String FORMAT_DATE_TIME_5 = "yyyy/MM/dd HH:mm";

	/** yyyy-MM-dd HH:mm:ss.SSS. */
	public static final String FORMAT_DATE_TIME_7 = "yyyy-MM-dd HH:mm:ss.SSS";

	/** yyyyMMddHHmm. */
	public static final String FORMAT_DATE_TIME_8 = "yyyyMMddHHmm";
	
	/** yyyyMMddHHmmssSSS. */
	public static final String FORMAT_DATE_TIME_9 = "yyyyMMddHHmmssSSS";

	/** yyyyMMddHHmmssSSS. */
	public static final String FORMAT_DATE_TIME_10 = "MMddHHmmyyyy.ss";
	
	/** yyyy. */
	public static final String FORMAT_YEAR_1 = "yyyy";

	/** MM. */
	public static final String FORMAT_MONTH_1 = "MM";

	/** HH:mm:ss. */
	public static final String FORMAT_TIME_1 = "HH:mm:ss";

	/** HH:mm. */
	public static final String FORMAT_HM_1 = "HH:mm";

	/** HHmm. */
	public static final String FORMAT_HM_2 = "HHmm";
	
	/** MM-dd HH:mm */
	public static final String FORMAT_HM_3 = "MM-dd HH:mm";

	/** MM-dd */
	public static final String FORMAT_HM_4 = "MM-dd";
	
	/** MM-dd */
	public static final String FORMAT_HM_5 = "MM-dd HH:mm:ss";

	/** dd. */
	public static final String FORMAT_DATE_1 = "dd";

	/** HH. */
	public static final String FORMAT_HOUR_1 = "HH";

	/** yyyy年MM月dd日 */
	public static final String FORMAT_YMD_str_1 = "yyyy年MM月dd日";

	/** MM月dd日 */
	public static final String FORMAT_YMD_str_2 = "MM月dd日";

	private DcDateUtils() {
	}
	
	/** 
     * 返回当前日期时间字符串<br> 
     * 默认格式:yyyymmddhhmmss 
     *  
     * @return String 返回当前字符串型日期时间 
     */  
    public static String getCurrentTimeAsString1() {  
        String returnStr = null;  
        SimpleDateFormat f = new SimpleDateFormat("yyyyMMddHHmmss");  
        Date date = new Date();  
        returnStr = f.format(date);  
        return returnStr;  
    }  
    
    /** 
     * 返回当前日期时间字符串<br> 
     * 默认格式:yyyymmddhhmm 
     *  
     * @return String 返回当前字符串型日期时间 
     */  
    public static String getCurrentTimeAsString2() {  
        String returnStr = null;  
        SimpleDateFormat f = new SimpleDateFormat("yyyyMMddHHmm");  
        Date date = new Date();  
        returnStr = f.format(date);  
        return returnStr;  
    } 

    /** 
     * 返回当前日期时间字符串<br> 
     * 默认格式:yyyymmddhhmmssSSS
     *  
     * @return String 返回当前字符串型日期时间 
     */  
    public static String getCurrentTimeAsString() {  
        String returnStr = null;  
        SimpleDateFormat f = new SimpleDateFormat("yyyyMMddHHmmssSSS");  
        Date date = new Date();  
        returnStr = f.format(date);  
        return returnStr;  
    }  

    
	/**
	 * 获取给定日期月的最后一天。
	 * 
	 * @param date
	 *            日期
	 * @return int 最后一天的日期值
	 */
	public static int getLastDayOfMonth(Date date) {

		Calendar calender = Calendar.getInstance();
		calender.setTime(date);
		return calender.getActualMaximum(DateField.DAY_OF_WEEK.value());
	}

	/**
	 * 取得给定日期，在指定字段上的值。
	 * 
	 * @param date
	 *            日期
	 * @param field
	 *            日期字段类型
	 * @return int 值
	 */
	public static int getField(Date date, DateField field) {

		Calendar calender = Calendar.getInstance();
		calender.setTime(date);
		return calender.get(field.value());
	}

	/**
	 * 将日期从一种格式转为另外一种格式。
	 * 
	 * @param strDate
	 *            日期
	 * @param formatFrom
	 *            原格式
	 * @param formatTo
	 *            目标格式
	 * @return String 转换后的日期
	 * @throws ParseException
	 */
	public static String formatDate(String strDate, String formatFrom,
			String formatTo) throws ParseException {
		if (TextUtils.isEmpty(strDate)) {
			return strDate;
		}
		SimpleDateFormat ff = new SimpleDateFormat(formatFrom);
		ff.setLenient(false);

		SimpleDateFormat ft = new SimpleDateFormat(formatTo);
		ft.setLenient(false);

		return ft.format(ff.parse(strDate));

	}

	/**
	 * 将字符串日期按指定格式，转换为日期类型。
	 * 
	 * @param date
	 *            字符串日期
	 * @param strFormat
	 *            目标日期格式
	 * @return Date 日期
	 */
	public static Date toDate(String date, String strFormat) {
		if (TextUtils.isEmpty(date)) {
			return null;
		}
		try {
			SimpleDateFormat df = new SimpleDateFormat(strFormat);
			df.setLenient(false);
			Date objDate = df.parse(date);
			return objDate;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 将日期（Date类型），按照指定的格式，转换为字符串日期。
	 * 
	 * @param date
	 *            日期
	 * @param toFormat
	 *            目标格式
	 * @return String 字符串日期
	 */
	public static String toString(Date date, String toFormat) {
		if (date == null) {
			return "";
		}
		SimpleDateFormat df = new SimpleDateFormat(toFormat);
		df.setLenient(false);
		return df.format(date);
	}

	/**
	 * 将日期（Date类型），按照指定的格式，转换为字符串日期。
	 * 
	 * @param date
	 *            日期
	 * @param toFormat
	 *            目标格式
	 * @return String 字符串日期
	 */
	public static String toString(Date date, String[] toFormats) {
		if (date == null) {
			return "";
		}
		String dateStr = null;
		for (String toFormat : toFormats) {
			SimpleDateFormat df = new SimpleDateFormat(toFormat);
			df.setLenient(false);
			dateStr = df.format(date);
			if (!TextUtils.isEmpty(dateStr)) {
				return dateStr;
			}
		}
		return null;
	}

	/**
	 * 计算开始日期到结束日期，在指定日期字段上的差值。
	 * 
	 * @param begin
	 *            开始日期
	 * @param end
	 *            结束日期
	 * @param field
	 *            日期字段
	 * @return long 日期差值
	 */
	public static long difference(Date begin, Date end, DateField field) {
		return field.difference(begin, end);
	}

	/**
	 * 在日期的指定日期字段上，进行加减操作。
	 * 
	 * @param date
	 *            日期
	 * @param field
	 *            日期字段
	 * @param amount
	 *            加减操作的值
	 * @return Date 处理后的日期
	 */
	public static Date add(Date date, DateField field, int amount) {

		Calendar calender = Calendar.getInstance();
		calender.setTime(date);

		calender.add(field.value(), amount);
		return calender.getTime();
	}

	/**
	 * 当前时间。
	 * 
	 * @return Date
	 */
	public static Date now() {
		return Calendar.getInstance().getTime();
	}

	/**
	 * 将目标日期设置到0点。
	 * 
	 * @param target
	 *            目标日期
	 * @return Date
	 */
	public static Date setToZero(Date target) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(target.getTime());
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * 将目标日期设置到午夜23点59分59秒。
	 * 
	 * @param target
	 *            目标日期
	 * @return Date
	 */
	public static Date setToNight(Date target) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(target.getTime());
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		return cal.getTime();
	}

	/**
	 * 将目标日期加一天。
	 * 
	 * @param target
	 *            目标日期
	 * @return Date
	 */
	public static Date addDay(Date target) {
		return modifyDay(target, 1);
	}

	/**
	 * 将目标日期加days天。
	 * 
	 * @param target
	 *            目标日期
	 * @param days
	 *            增加的天数
	 * @return Date
	 */
	public static Date addDay(Date target, int days) {
		return modifyDay(target, days);
	}

	/**
	 * 将目标日期减一天。
	 * 
	 * @param target
	 *            目标日期
	 * @return Date
	 */
	public static Date minusDay(Date target) {
		return modifyDay(target, -1);
	}

	/**
	 * 将目标日期减少days天。
	 * 
	 * @param target
	 *            目标日期
	 * @param days
	 *            减少的天数（绝对值）
	 * @return Date
	 */
	public static Date minusDay(Date target, int days) {
		return modifyDay(target, -days);
	}

	/**
	 *
	 * @param target	目标日期
	 * @param hourse	减少的小时
     * @return
     */
	public static Date minusHourse(Date target,int hourse){
		return modifyHourse(target,-hourse);
	}

	/**
	 * 取得当前天日期，并指定时、分、秒
	 * 
	 * @param hours
	 *            时
	 * @param minutes
	 *            分
	 * @param seconds
	 *            秒
	 * @return Date
	 */
	public static Date getTimeByInt(int hours, int minutes, int seconds) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, hours);
		cal.set(Calendar.MINUTE, minutes);
		cal.set(Calendar.SECOND, seconds);
		return cal.getTime();
	}

	// 调整日期天数
	private static Date modifyDay(Date target, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(target.getTime());
		cal.add(Calendar.DATE, days);
		return cal.getTime();
	}

	// 调整日期小时数
	private static Date modifyHourse(Date target, int hourse) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(target.getTime());
		cal.add(Calendar.HOUR, hourse);
		return cal.getTime();
	}

	/**
	 * <p>
	 * 根据给定的月份,对当前月份进行加减操作
	 * </p>
	 * 
	 * @param target
	 * @param months
	 *            需要变动的月份数
	 * @return
	 * @update: [updatedate] [changer][change description]
	 */
	public static Date modifyMonth(Date target, int months) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(target.getTime());
		cal.add(Calendar.MONTH, months);
		return cal.getTime();
	}

	public static String getShowtime(String strdate)
	{
		String showtime = "";		
		try 
		{
			
			if(!TextUtils.isEmpty(strdate))
			{
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date d = new Date(System.currentTimeMillis());
				//String showTime =;							
				//得到当前时间 是同一天
				if(DcDateUtils.formatDate(strdate, "yyyyMMddHHmmss","yyyy-MM-dd").equals( dateFormat.format(d)))
				{
					//返回 HH:mm
					showtime = DcDateUtils.formatDate(strdate, "yyyyMMddHHmmss","HH:mm");
				}
				else
				{
					//返回 MM月dd日
					showtime = DcDateUtils.formatDate(strdate, "yyyyMMddHHmmss","MM月dd日");
				}
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return showtime;
	}
	
	/**
	 * 判断是否日期
	 * 
	 * @param strdate
	 *            字符串日期
	 * @param toFormat
	 *            目标格式
	 * @return boolean 是否
	 */
	public static boolean isValidDate(String strdate, String toFormat) {
	  boolean convertSuccess=true;
	  // 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；
	  SimpleDateFormat format = new SimpleDateFormat(toFormat);
	    try {
	    	  // 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
	          format.setLenient(false);
	          format.parse(strdate);
	       } catch (ParseException e) {
	          // e.printStackTrace();
	    	   // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
	           convertSuccess=false;
	    } 
	    return convertSuccess;
	}

	/**
	 * 获取当前日期是星期几<br>
	 *
	 * @param date
	 * @return 当前日期是星期几
	 */
	public static String getWeekOfDate(Date date) {
		String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0)
			w = 0;
		return weekDays[w];
	}

	/**
	 * 获取当前日期是星期几<br>
	 *
	 * @param date
	 * @return 当前日期是星期几
	 */
	public static String getStrWeekOfDate(Date date) {
		String[] weekDays = {"0", "1", "2", "3", "4", "5", "6"};
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0)
			w = 0;
		return weekDays[w];
	}

	/**
	 * 获取当前日期是星期几<br>
	 * 0-6
	 * @param date
	 * @return 当前日期是星期几 0 - 6
	 */
	public static int getWeekIntOfDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0)
			w = 0;
		return w;
	}


	/**
	 * 计算两个日期之间相差的天数
	 * @param smdate 较小的时间
	 * @param bdate  较大的时间
	 * @return 相差天数
	 * @throws ParseException
	 */
	public static int daysBetween(Date smdate,Date bdate) throws ParseException
	{
		SimpleDateFormat sdf=new SimpleDateFormat(FORMAT_YMD_1);
		smdate=sdf.parse(sdf.format(smdate));
		bdate=sdf.parse(sdf.format(bdate));
		Calendar cal = Calendar.getInstance();
		cal.setTime(smdate);
		long time1 = cal.getTimeInMillis();
		cal.setTime(bdate);
		long time2 = cal.getTimeInMillis();
		long between_days=(time2-time1)/(1000*3600*24);

		return Integer.parseInt(String.valueOf(between_days));
	}

	/**
	 * 获取日期相差的小时 或分钟数
	 * @param startTime
	 * @param endTime
	 * @param type "h"小时  其他分钟
	 * @return
	 */
	public static Long dateDiff(Date startTime, Date endTime,String type) {
		long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
		long nh = 1000 * 60 * 60;// 一小时的毫秒数
		long nm = 1000 * 60;// 一分钟的毫秒数
		long ns = 1000;// 一秒钟的毫秒数
		long diff;
		long day = 0;
		long hour = 0;
		long min = 0;
		//long sec = 0;
		// 获得两个时间的毫秒时间差异
		try {
			diff = endTime.getTime() - startTime.getTime();
			day = diff / nd;// 计算差多少天
			hour = (diff-day*(1000 * 60 * 60 * 24))/nh;
			min = (diff-day*(1000 * 60 * 60 * 24)-hour*(1000* 60 * 60))/(1000* 60);
//			hour = diff % nd / nh + day * 24;// 计算差多少小时
//			min = diff % nd % nh / nm + day * 24 * 60;// 计算差多少分钟
//			sec = diff % nd % nh % nm / ns;// 计算差多少秒
			// 输出结果
//			System.out.println("时间相差：" + day + "天" +  hour + "小时"  + min + "分钟");
			//System.out.println("hour=" + hour + ",min=" + min);
			if (type.equalsIgnoreCase("h")) {
				return hour + day*24;
			} else {
				return min + (hour + day*24)*60;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (type.equalsIgnoreCase("h")) {
			return hour;
		} else {
			return min;
		}
	}


	/**
	 * 获取空间显示的时间
	 * 当天显示分钟前  小时前
	 * 一月内显示 多少天前
	 * 超出一月显示 多少月前
	 * 超出一年  显示 yyyy年MM月dd日
	 *
	 * @param time
	 * @return
	 */
	public static String getShowTime(Date time) {
		try {
			if (time != null) {
				Date nowdate = new Date(System.currentTimeMillis());
				long t = (nowdate.getTime() - time.getTime()) / 1000 / 60;
				if (t <= 0) {
					return "刚刚";
				} else if (t < 60) {
					return t + "分钟前";
				} else {
					t = t / 60;
					if (t < 24) {
						return t + "小时前";
					} else {
						t = t - (int) (nowdate.getHours());
						t = t % 24 > 0 ? t / 24 + 1 : t / 24;
						if (t == 1) {
							return "昨天";
						} else if (t <= 29) {
							return t + "天前";
						} else {
							int month = getMonthSpace(time, nowdate);
							if (month < 1) {
								return t + "天前";
							} else if (month >= 12) {
								return DcDateUtils.toString(time, DcDateUtils.FORMAT_YMD_str_1);
							} else {
								return month + "月前";
							}
						}
					}
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return "";
	}


	/**
	 * 获取日期相差月份
	 *
	 * @param date1 <String>
	 * @param date2 <String>
	 */
	private static int getMonthSpace(Date date1, Date date2) {
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(date1);
		c2.setTime(date2);
		int result = c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH);
		int month = (c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR)) * 12;
		//result = c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH);
		return Math.abs(month + result);
	}
}
