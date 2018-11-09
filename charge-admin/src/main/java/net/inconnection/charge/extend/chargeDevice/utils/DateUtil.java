package net.inconnection.charge.extend.chargeDevice.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 
 * 功能描述：
 * 
 * @author Administrator
 * @Date Jul 19, 2008
 * @Time 9:47:53 AM
 * @version 1.0
 */
public class DateUtil {

	public static final String PATTERN_STANDARD12W = "yyyyMMddHHmm";
	public static final String PATTERN_STANDARD14W = "yyyyMMddHHmmss";
	public static final String PATTERN_STANDARD17W = "yyyyMMddHHmmssSSS";
	public static final String PATTERN_STANDARD10H = "yyyy-MM-dd";
	public static final String PATTERN_STANDARD16H = "yyyy-MM-dd HH:mm";
	public static final String PATTERN_STANDARD19H = "yyyy-MM-dd HH:mm:ss";
	public static final String PATTERN_STANDARD10X = "yyyy/MM/dd";
	public static final String PATTERN_STANDARD16X = "yyyy/MM/dd HH:mm";
	public static final String PATTERN_STANDARD19X = "yyyy/MM/dd HH:mm:ss";
	public static final String PATTERN_STANDARD08W = "yyyyMMdd";
	public static Date date = null;

	public static DateFormat dateFormat = null;

	public static Calendar calendar = null;

	/**
	 * 日期转换格式
	 * @return date
	 */
	public static Date DateToTodayZero() {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = StrToDate(sdf.format(getTodayDateZeroTime()));
		return date;
	}

	/**
	 * 日期转换格式
	 * @return date
	 */
	public static Date DateToTomorrowZero() {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = StrToDate(sdf.format(getTomorrowDateZeroTime()));
		return date;
	}

	/**
	 * 日期转换成字符串
	 * @param date
	 * @return str
	 */
	public static String DateToStr(Date date) {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = format.format(date);
		return str;
	}


	public static String DateOnlyYearMonthDayToStr(Date date) {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String str = format.format(date);
		return str;
	}

	/**
	 * 字符串转换成日期
	 * @param str
	 * @return date
	 */
	public static Date StrToDate(String str) {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = format.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}


	public static Date yyyyMMddHHmmssStrToDate(String str) {

		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = null;
		try {
			date = format.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static Date StrYearMonthToDate(String str) {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		Date date = null;
		try {
			date = format.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static Date YearMonthStrToDate(String str) {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		Date date = null;
		try {
			date = format.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 功能描述：格式化日期
	 * 
	 * @param dateStr
	 *            String 字符型日期
	 * @param format
	 *            String 格式
	 * @return Date 日期
	 */
	public static Date parseDate(String dateStr, String format) {
		try {
			dateFormat = new SimpleDateFormat(format);
			String dt = dateStr.replaceAll("-", "/");
			if ((!dt.equals("")) && (dt.length() < format.length())) {
				dt += format.substring(dt.length()).replaceAll("[YyMmDdHhSs]",
						"0");
			}
			date = (Date) dateFormat.parse(dt);
		} catch (Exception e) {
		}
		return date;
	}


	public static Date getDate2(String time){
		return parseDate(time, "yyyy—MM—dd");
	}


	public static void main(String[] args) {

//		int year = DateUtil.getYear(new Date());
//		int month = DateUtil.getMonth(new Date());
//		int day = DateUtil.getDay(new Date());
//		String date1 = year+"—"+month+"—"+day;
//		System.out.println(date1);
//		Date date2 = DateUtil.parseDate(date1,"yyyy—MM—dd");
//		System.out.println(date2);

		Date startDate = StrToDate("2017-11-16 00:00:00");
		Date endDate = StrToDate("2017-11-16 23:59:59");

		List list = getDatesBetweenTwoDate(startDate,endDate);
		System.out.println("得到的时间:"+list);
//        int hour = (int)Math.ceil(700 / 60);
        System.out.println((int)Math.ceil(700 / 60.0));
        System.out.println(new Double(800 / 60.0 * 40).intValue());
//        int totalMoney = new Double(Double.parseDouble("800") / 60.0 * price).intValue();

        System.out.println(new Double(2.6).intValue());

	}



	/**
	 * 功能描述：格式化日期
	 * 
	 * @param dateStr
	 *            String 字符型日期：YYYY-MM-DD 格式
	 * @return Date
	 */
	public static Date parseDate(String dateStr) {
		return parseDate(dateStr, "yyyy/MM/dd");
	}

	/**
	 * 功能描述：格式化输出日期
	 * 
	 * @param date
	 *            Date 日期
	 * @param format
	 *            String 格式
	 * @return 返回字符型日期
	 */
	public static String format(Date date, String format) {
		String result = "";
		try {
			if (date != null) {
				dateFormat = new SimpleDateFormat(format);
				result = dateFormat.format(date);
			}
		} catch (Exception e) {
		}
		return result;
	}

	/**
	 * 功能描述：
	 * 
	 * @param date
	 *            Date 日期
	 * @return
	 */
	public static String format(Date date) {
		return format(date, "yyyy/MM/dd");
	}

	/**
	 * 功能描述：返回年份
	 * 
	 * @param date
	 *            Date 日期
	 * @return 返回年份
	 */
	public static int getYear(Date date) {
		calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * 功能描述：返回月份
	 * 
	 * @param date
	 *            Date 日期
	 * @return 返回月份
	 */
	public static int getMonth(Date date) {
		calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MONTH) + 1;
	}

	//获取当前时间的前一个月的时间
	public static Date getMonthFew(Date date) {
		Calendar time = Calendar.getInstance();
		time.setTime(date);
		time.add(Calendar.MONTH, -1);//当前时间前去一个月，即一个月前的时间
		return time.getTime();
	}
	/**
	 * 功能描述：返回日份
	 * 
	 * @param date
	 *            Date 日期
	 * @return 返回日份
	 */
	public static int getDay(Date date) {
		calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 功能描述：返回小时
	 * 
	 * @param date
	 *            日期
	 * @return 返回小时
	 */
	public static int getHour(Date date) {
		calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 功能描述：返回分钟
	 * 
	 * @param date
	 *            日期
	 * @return 返回分钟
	 */
	public static int getMinute(Date date) {
		calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MINUTE);
	}

	/**
	 * 返回秒钟
	 * 
	 * @param date
	 *            Date 日期
	 * @return 返回秒钟
	 */
	public static int getSecond(Date date) {
		calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.SECOND);
	}

	/**
	 * 功能描述：返回毫秒
	 * 
	 * @param date
	 *            日期
	 * @return 返回毫秒
	 */
	public static long getMillis(Date date) {
		calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.getTimeInMillis();
	}

	/**
	 * 功能描述：返回字符型日期
	 * 
	 * @param date
	 *            日期
	 * @return 返回字符型日期 yyyy/MM/dd 格式
	 */
	public static String getDate(Date date) {
		return format(date, "yyyy/MM/dd");
	}

	/**
	 * 功能描述：返回字符型时间
	 * 
	 * @param date
	 *            Date 日期
	 * @return 返回字符型时间 HH:mm:ss 格式
	 */
	public static String getTime(Date date) {
		return format(date, "HH:mm:ss");
	}

	/**
	 * 功能描述：返回字符型日期时间
	 * 
	 * @param date
	 *            Date 日期
	 * @return 返回字符型日期时间 yyyy/MM/dd HH:mm:ss 格式
	 */
	public static String getDateTime(Date date) {
		return format(date, "yyyy/MM/dd HH:mm:ss");
	}

	/**
	 * 功能描述：日期相加
	 * 
	 * @param date
	 *            Date 日期
	 * @param day
	 *            int 天数
	 * @return 返回相加后的日期
	 */
	public static Date addDate(Date date, int day) {
		calendar = Calendar.getInstance();
		long millis = getMillis(date) + ((long) day) * 24 * 3600 * 1000;
		calendar.setTimeInMillis(millis);
		return calendar.getTime();
	}

	/**
	 * 功能描述：日期相减
	 * 
	 * @param date
	 *            Date 日期
	 * @param date1
	 *            Date 日期
	 * @return 返回相减后的日期
	 */
	public static int diffDate(Date date, Date date1) {
		return (int) ((getMillis(date) - getMillis(date1)) / (24 * 3600 * 1000));
	}



	//时间相差的秒数
	public static int diffSecondDate(Date date, Date date1) {
		return (int) ((getMillis(date) - getMillis(date1)) / 1000);
	}


	/**
	 * 功能描述：取得指定月份的第一天
	 * 
	 * @param strdate
	 *            String 字符型日期
	 * @return String yyyy-MM-dd 格式
	 */
	public static String getMonthBegin(String strdate) {
		date = parseDate(strdate);
		return format(date, "yyyy-MM") + "-01";
	}


	/**
	 根据开始时间和结束时间返回时间段内的时间集合
	 @param beginDate
	 @param endDate
	 @return List */
	@SuppressWarnings("unchecked")
	public static List getDatesBetweenTwoDate(Date beginDate, Date endDate) {
		List lDate = new ArrayList();
		lDate.add(beginDate);//把开始时间加入集合
		Calendar cal = Calendar.getInstance(); //使用给定的 Date 设置此 Calendar 的时间
		cal.setTime(beginDate);
		boolean bContinue = true;
		while (bContinue) { //根据日历的规则，为给定的日历字段添加或减去指定的时间量
			cal.add(Calendar.HOUR_OF_DAY, 1); // 测试此日期是否在指定日期之后
			if (endDate.after(cal.getTime())) {
				lDate.add(cal.getTime());
			} else {
				break;
			}
		}
		lDate.add(endDate);//把结束时间加入集合
		return lDate;
	}

	/**
	 * 功能描述：取得指定月份的最后一天
	 * 
	 * @param strdate
	 *            String 字符型日期
	 * @return String 日期字符串 yyyy-MM-dd格式
	 */
	public static String getMonthEnd(String strdate) {
		date = parseDate(getMonthBegin(strdate));
		calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, 1);
		calendar.add(Calendar.DAY_OF_YEAR, -1);
		return formatDate(calendar.getTime());
	}



	/**
	 * 功能描述：常用的格式化日期
	 * 
	 * @param date
	 *            Date 日期
	 * @return String 日期字符串 yyyy-MM-dd格式
	 */
	public static String formatDate(Date date) {
		return formatDateByFormat(date, "yyyy-MM-dd");
	}

	/**
	 * 功能描述：以指定的格式来格式化日期
	 * 
	 * @param date
	 *            Date 日期
	 * @param format
	 *            String 格式
	 * @return String 日期字符串
	 */
	public static String formatDateByFormat(Date date, String format) {
		String result = "";
		if (date != null) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat(format);
				result = sdf.format(date);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}


	/**
	 * Long类型的时间转换为String（yyyy-MM-dd HH:mm:ss）类型
	 * 黄顺意
	 * @param current
	 * @return
	 */
	public static String LongToString(Long current){
		SimpleDateFormat simpleDateFormat    = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return simpleDateFormat.format(current);

	}

	/**
	 * Date类型
	 * 当天零点值
	 * 黄顺意
	 * @return
	 */
	public static long currentTodayZeroTime(){
		SimpleDateFormat simpleDateFormat    = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long current = System.currentTimeMillis();
		long todayZero = current/(1000*3600*24)*(1000*3600*24) - TimeZone.getDefault().getRawOffset();

		return todayZero;

	}

	/**
	 * Date类型
	 * 返回今天零点的值
	 * 黄顺意
	 * @return
	 */
	public static Date getTodayDateZeroTime(){
		return  StrToDate(LongToString(currentTodayZeroTime()));

	}

	/**
	 * Date类型
	 * 返回明天零点的值
	 * 黄顺意
	 * @return
	 */
	public static Date getTomorrowDateZeroTime(){
		return  StrToDate(LongToString(currentTodayZeroTime()+24*60*60*1000));

	}

	/**
	 * Date类型
	 * 返回昨天零点的值
	 * 黄顺意
	 * @return
	 */
	public static Date getYesterdayDateZeroTime(){
		return  StrToDate(LongToString(currentTodayZeroTime()-24*60*60*1000));

	}


	/**
	 * 获取到某月月初零点的Date类型
	 * 黄顺意
	 * @param strdate
	 * @return
	 */

	public static Date getDateMonthBegin(String strdate) {
		date = parseDate(strdate);
		String monthBegin = format(date, "yyyy-MM") + "-"+"01 00:00:00";

		return StrToDate(monthBegin);
	}

	/**
	 * 获取到某月次月零点的Date类型
	 * 黄顺意
	 * @param strdate
	 * @return
	 */

	public static Date getDateSecomdMonthBegin(String strdate) {
		strdate = format(date, "yyyy-MM") + "-"+"01 00:00:00";
		date = parseDate(strdate);
		calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, 1);
		return calendar.getTime() ;
	}

	/**
	 * @Title: date2String
	 * @Description: 日期格式的时间转化成字符串格式的时间
	 * @author YFB
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String date2String(Date date, String pattern) {
		if (date == null) {
			throw new IllegalArgumentException("timestamp null illegal");
		}
		pattern = (pattern == null || pattern.equals(""))? PATTERN_STANDARD19H:pattern;
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}

	/**
	 * @Title: string2Date
	 * @Description: 字符串格式的时间转化成日期格式的时间
	 * @author YFB
	 * @param strDate
	 * @param pattern
	 * @return
	 */
	public static Date string2Date(String strDate, String pattern) {
		if (strDate == null || strDate.equals("")) {
			throw new RuntimeException("strDate is null");
		}
		pattern = (pattern == null || pattern.equals(""))? PATTERN_STANDARD19H:pattern;
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Date date = null;
		try {
			date = sdf.parse(strDate);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return date;
	}

	/**
	 * @Title: getCurrentTime
	 * @Description: 取得当前系统时间
	 * @author YFB
	 * @param format 格式 17位(yyyyMMddHHmmssSSS) (14位:yyyyMMddHHmmss) (12位:yyyyMMddHHmm) (8位:yyyyMMdd)
	 * @return
	 */
	public static String getCurrentTime(String format) {
		SimpleDateFormat formatDate = new SimpleDateFormat(format);
		String date = formatDate.format(new Date());
		return date;
	}



	/**
	 * 字符串转换成日期
	 * @param str
	 * @return date
	 */
	public static Date StrToDateDay(String str) {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = format.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 字符串转换成日期
	 * @param str
	 * @return date
	 */
	public static Date StrToTime(String str) {

		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		Date date = null;
		try {
			date = format.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static String dateIntoString(Date date ){
		SimpleDateFormat sdf =   new SimpleDateFormat( " yyyy-MM-dd HH:mm:ss " );
		String str = sdf.format(date);

		return str;

	}

	public static Date getFirstDayOfMonth(){

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, 0);
		cal.set(Calendar.DAY_OF_MONTH,1);
		//将小时至0
        cal.set(Calendar.HOUR_OF_DAY, 0);
        //将分钟至0
        cal.set(Calendar.MINUTE, 0);
        //将秒至0
        cal.set(Calendar.SECOND,0);
        //将毫秒至0
        cal.set(Calendar.MILLISECOND, 0);
		Date firstDayTime = cal.getTime();

		return firstDayTime;
	}


    /**
     * 返回当前时间之前hour小时的时间
     * @param hour
     * @return
     */
    public static String getBeforeByHourTime(int hour){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - hour);
        SimpleDateFormat df = new SimpleDateFormat(PATTERN_STANDARD19H);
        return df.format(calendar.getTime());
    }

    /**
     * 返回指定格式的时间
     * @param str 时间字符串
     * @param pattern 返回的时间格式，默认 yyyy-MM-dd HH:mm:ss
     * @return 返回的时间
     */
    public static Date dateStringToDate(String str,String pattern) {
        SimpleDateFormat format = null;
        Date date = new Date();
        try {
            if(pattern != null){
                format = new SimpleDateFormat(pattern);
            }else{
                format = new SimpleDateFormat(PATTERN_STANDARD19H);
            }
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


    /**
     *  返回minute分钟后的时间
     * @param date 时间
     * @param minute 分钟
     * @return
     */
    public static Date getAfterDateByMinute(Date date,int minute) {
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, minute);
        SimpleDateFormat df = new SimpleDateFormat(PATTERN_STANDARD19H);
        return dateStringToDate(df.format(calendar.getTime()), null);
    }

}
